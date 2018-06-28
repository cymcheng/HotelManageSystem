import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.border.*;
import java.util.Date;

public class AddNewCheckOut extends Enter_Manager implements ActionListener {

	JLabel label_DateOfCheckOut;
	
	JPanel jpanel1,jpanel2;

	JButton button_AddCheckOut,button_Cancel;
	
	/*日期编辑器*/
	JSpinner spinner_Calendar = new JSpinner(new SpinnerDateModel());
	JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner_Calendar,"MM-dd");
	
	JTable jtable;
	
	Object cols[]= {"订单编号","酒店编号","用户编号","用户名","客房编号"};
	Object rows[][];

	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=HotelManagement;";
	String user = "sa", pwd = "cym331s09";
	
	Connection con = null;
	Statement sql = null;
	
	ResultSet result_SelectCheckIn = null;
	ResultSet result_RealityDateOfCheckIn = null;
	ResultSet result_isVIP = null;
	ResultSet result_isRegular = null;
	ResultSet result_ExpectedNumberOfCheckIn = null;
	ResultSet result_Expense = null;
	ResultSet result_cashPledgeAmount = null;
	ResultSet result_PrepayHotelCharge = null;
	
	int reservationNumber_CheckOut = 0;
	int HSerialNumber_CheckOut = 0;
	int CSerialNumber_CheckOut = 0;
	String customerName_CheckOut = "";
	int RSerialNumber_CheckOut = 0;
	

	public AddNewCheckOut() {
		
		label_DateOfCheckOut = new JLabel("退房日期");
		
		jpanel1 = new JPanel();		 
	    jpanel2 = new JPanel();
	    
	    jpanel1.setBorder(new TitledBorder("确认离店信息:"));
	    
	    jpanel1.add(label_DateOfCheckOut);
		spinner_Calendar.setEditor(dateEditor);
		spinner_Calendar.setValue(new Date());
		jpanel1.add(spinner_Calendar);
	    
	    button_AddCheckOut = new JButton("确认离店");
	    button_AddCheckOut.addActionListener(this);
	    button_Cancel = new JButton("退出");
	    button_Cancel.addActionListener(this);

		jpanel2.add(button_AddCheckOut);
		jpanel2.add(button_Cancel);
		
	    add(jpanel1, BorderLayout.NORTH);
		add(jpanel2, BorderLayout.SOUTH);		
		 
		try {
			Class.forName("com.sqlserver.jdbc.Driver");
		} catch (ClassNotFoundException ee) {
			System.out.println("test" + ee);
		}
		
		rows = QueryCheckIn();
		jtable = new JTable(rows,cols);
		
		jtable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				showSelectedResrvationInformation();
			}
		});
		
		add(new JScrollPane(jtable), BorderLayout.CENTER);
		validate();
	}
	
    public Object[][] QueryCheckIn() {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);			
			
			/*查询入住信息*/
			String SQL_SelectCheckIn;
			SQL_SelectCheckIn = "SELECT ReservationNumber,HSerialNumber,Customer.CSerialNumber,Name,RSerialNumber FROM CheckIn,Customer WHERE CheckIn.CSerialNumber=Customer.CSerialNumber";                           
			//System.out.println(SQL_SelectCheckIn);
			result_SelectCheckIn = sql.executeQuery(SQL_SelectCheckIn);
			result_SelectCheckIn.last();
			int currentRow = result_SelectCheckIn.getRow();
			//System.out.println(currentRow);
			if (currentRow == 0) {
				JOptionPane.showMessageDialog(this, "目前没有住宿信息", "提示", JOptionPane.WARNING_MESSAGE);
			} else {				
                int rowCount = currentRow; 
				//System.out.println(rowCount+" the rows");
		        rows = new Object[rowCount][cols.length];
		        int count = 0; 
		        result_SelectCheckIn.beforeFirst();
		        while (result_SelectCheckIn.next()) {
		            //System.out.println(count + "," + result_SelectCheckIn.getString(1));
		            rows[count][0] = result_SelectCheckIn.getString(1);
		            rows[count][1] = result_SelectCheckIn.getString(2);  
		            rows[count][2] = result_SelectCheckIn.getString(3);
		            rows[count][3] = result_SelectCheckIn.getString(4);
		            rows[count][4] = result_SelectCheckIn.getString(5);	            
		            count++;
		        }
		    }
			con.close();
		} catch (SQLException ee) {
			System.out.println(ee);
		}  
		return rows;
    }
    
    public void button_AddCheckOut_Click() {
    	try {
    		con = DriverManager.getConnection(url, user, pwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);;
    		//int RSerialNumber = Integer.valueOf(txt_RSerialNumber.getText().trim());
			/*退房日期*/
			String tempDateOfCheckOut = spinner_Calendar.getValue().toString().trim();
			String DateOfCheckOut = TransDateString.transDateString(tempDateOfCheckOut);
			/*查询实际入住日期*/
			String SQL_SelectRealityDateOfCheckIn;
    		//System.out.println(customerName_CheckOut);
			SQL_SelectRealityDateOfCheckIn = "SELECT RealityDateOfCheckIn FROM CheckIn,Customer WHERE CheckIn.CSerialNumber=Customer.CSerialNumber AND Name='" + customerName_CheckOut + "'";
    		result_RealityDateOfCheckIn = sql.executeQuery(SQL_SelectRealityDateOfCheckIn);
    		//System.out.println(SQL_SelectRealityDateOfCheckIn);
    		String RealityDateOfCheckIn = "";
    		result_RealityDateOfCheckIn.beforeFirst();
    		while (result_RealityDateOfCheckIn.next()) {
    			RealityDateOfCheckIn = result_RealityDateOfCheckIn.getString(1).trim();
        		//System.out.println(RealityDateOfCheckIn);
    		}
    		/*实际入住天数*/
    		int RealityNumberOfCheckIn = DateDiff.Datediff(RealityDateOfCheckIn, DateOfCheckOut);
    		/*押金*/
    		String SQL_SelectCashPledgeAmount;
    		SQL_SelectCashPledgeAmount = "SELECT cashPledgeAmount FROM CheckIn,Customer WHERE CheckIn.CSerialNumber=Customer.CSerialNumber AND Name='" + customerName_CheckOut + "'";
    		result_cashPledgeAmount = sql.executeQuery(SQL_SelectCashPledgeAmount);
    		//System.out.println(SQL_SelectCashPledgeAmount);
    		int cashPledgeAmount = 0;
    		result_cashPledgeAmount.beforeFirst();
    		while (result_cashPledgeAmount.next()) {
    			cashPledgeAmount = result_cashPledgeAmount.getInt(1);
        		System.out.println(cashPledgeAmount);
    		}
    		/*预付房费*/
    		String SQL_SelectPrepayHotelCharge;
    		SQL_SelectPrepayHotelCharge = "SELECT prepayHotelCharge FROM CheckIn,Customer WHERE CheckIn.CSerialNumber=Customer.CSerialNumber AND Name='" + customerName_CheckOut + "'";
    		result_PrepayHotelCharge = sql.executeQuery(SQL_SelectPrepayHotelCharge);
    		//System.out.println(SQL_SelectPrepayHotelCharge);
    		int prepayHotelCharge = 0;
    		result_PrepayHotelCharge.beforeFirst();
    		while (result_PrepayHotelCharge.next()) {
    			prepayHotelCharge = result_PrepayHotelCharge.getInt(1);
        		System.out.println(prepayHotelCharge);
    		}
    		/*VIP*/
    		String SQL_SelectIsVIP;
    		//System.out.println(customerName_CheckOut);
    		SQL_SelectIsVIP = "SELECT VIP FROM Customer WHERE Name='" + customerName_CheckOut + "'";
    		result_isVIP = sql.executeQuery(SQL_SelectIsVIP);
    		System.out.println(SQL_SelectIsVIP);
    		String isVIP = "";
    		result_isVIP.beforeFirst();
    		while (result_isVIP.next()) {
    			isVIP = result_isVIP.getString(1).trim();
        		//System.out.println(isVIP);
    		}
    		/*老客户*/
    		String SQL_SelectisRegular;
    		SQL_SelectisRegular = "SELECT Regular FROM Customer WHERE Name='" + customerName_CheckOut + "'";
    		result_isRegular = sql.executeQuery(SQL_SelectisRegular);
    		//System.out.println(SQL_SelectisRegular);
    		String isRegular = "";
    		result_isRegular.beforeFirst();
    		while (result_isRegular.next()) {
    			isRegular = result_isRegular.getString(1).trim();
    			System.out.println(isRegular);
    		}
    		/*查询单价、VIP折扣、老用户折扣*/
    		int tempUnitPrice = 0;
    		double tempVIPDiscount = 0;
    		double tempRegularDiscount = 0;
    		String SQL_SelectExpense = "";
    		SQL_SelectExpense = "SELECT UnitPrice,VIPDiscount,RegularDiscount FROM RoomType WHERE RSerialNumber=" + RSerialNumber_CheckOut;
    		result_Expense = sql.executeQuery(SQL_SelectExpense);
    		//System.out.println(SQL_SelectExpense);
    		result_Expense.beforeFirst();
    		while (result_Expense.next()) {
    			tempUnitPrice = result_Expense.getInt(1);
        		tempVIPDiscount = result_Expense.getDouble(2);
        		tempRegularDiscount = result_Expense.getDouble(3);
    		    //System.out.println("UnitPrice        " + tempUnitPrice);
    		    //System.out.println("VIPDiscount      " + tempVIPDiscount);
    		    //System.out.println("RegularDiscount  " + tempRegularDiscount);
    		}
    		/*计算实际的房费*/
    		int realityHotelCharge = 0;
    		if (isVIP.equals("NO") && isRegular.equals("NO")) {
    			realityHotelCharge = (tempUnitPrice * RealityNumberOfCheckIn);
    		} else if (isVIP.equals("YES") && isRegular.equals("NO")) {
    			realityHotelCharge = (int)(tempUnitPrice * RealityNumberOfCheckIn * tempVIPDiscount);
    		} else if (isVIP.equals("NO") && isRegular.equals("YES")) {
    			realityHotelCharge = (int)(tempUnitPrice * RealityNumberOfCheckIn * tempRegularDiscount);
    		} else if (isVIP.equals("YES") && isRegular.equals("YES")) {
    			realityHotelCharge = (int)(tempUnitPrice * RealityNumberOfCheckIn * tempVIPDiscount * tempRegularDiscount);
    		}
    		/*添加退房信息*/
		    String SQL_InsertNewCheckOut;
			SQL_InsertNewCheckOut = "INSERT INTO CheckOut(CSerialNumber,HSerialNumber,RSerialNumber,ReservationNumber,RealityDateOfCheckIn,DateOfCheckOut,RealityHotelCharge) VALUES(" + CSerialNumber_CheckOut + "," + HSerialNumber_CheckOut + "," + RSerialNumber_CheckOut + "," + reservationNumber_CheckOut+ ",'" + RealityDateOfCheckIn + "','" + DateOfCheckOut + "','" + realityHotelCharge + "')";
			//System.out.println(SQL_InsertNewCheckOut);
			int flag = sql.executeUpdate(SQL_InsertNewCheckOut);
			if (flag == 1) {
				JFrame.setDefaultLookAndFeelDecorated(true);
				PaymentInterface frame_PaymentInterface = new PaymentInterface(realityHotelCharge);
				frame_PaymentInterface.setVisible(true);
				frame_PaymentInterface.setBounds(200, 200, 500, 100);
				frame_PaymentInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			rows = QueryCheckIn();		     
		 	jtable.setModel(new DefaultTableModel(rows, cols));
		 	con.close();
		} catch (SQLException ee) {
			System.out.println(ee);
		}  	
    }
	
    public void button_Cancel_Click() {
		this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		Select frame_Select = new Select();
		frame_Select.setVisible(true);
		frame_Select.setBounds(200, 160, 900, 200);
		frame_Select.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
    public void showSelectedResrvationInformation() {
    	int row = jtable.getSelectedRow();
		int colCount = jtable.getColumnCount();
		String[] data = new String[colCount];
		//System.out.println(row + "," + colCount);
		for (int i = 0; i < colCount; i++) {
			data[i] = jtable.getModel().getValueAt(row, i).toString();
			//System.out.println("***" + data[i]);
		}
		reservationNumber_CheckOut = Integer.valueOf(data[0].trim());
		HSerialNumber_CheckOut = Integer.valueOf(data[1].trim());
		CSerialNumber_CheckOut = Integer.valueOf(data[2].trim());
		customerName_CheckOut = data[3].trim();
		RSerialNumber_CheckOut = Integer.valueOf(data[4].trim());
    }
    
    public void actionPerformed(ActionEvent e) {		
		if (e.getSource() == button_Cancel) {
			button_Cancel_Click();
		} else if (e.getSource() == button_AddCheckOut) {
			button_AddCheckOut_Click();
		}
	}

}