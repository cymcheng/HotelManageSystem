import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;


/*查询入住客人的费用*/
public class CustomerExpenseQuery extends JFrame implements ActionListener {
	
	JPanel jp1,jp2;
	JButton button_Query,button_Cancel;
	JTable jtable;
	
	Object cols[]= {"用户姓名","当前入住天数","押金金额","预交房费","当前住宿费","当前余额"};
	Object rows[][];
	
	JLabel label_CustomerName;
	JTextField txt_CustomerName;

	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=HotelManagement;";
	String user = "sa";
	String pwd = "cym331s09";
	
	Connection con = null;
	Statement sql = null;
	ResultSet resultSQL = null;
	
	ResultSet result_isVIP = null;
	ResultSet result_isRegular = null;
	ResultSet result_Expense = null;
	ResultSet result_RealityDateOfCheckIn = null;

	public CustomerExpenseQuery() {
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		
		jp1.setBorder(new TitledBorder("用户住宿信息查询"));
		
		label_CustomerName = new JLabel("客户姓名：");
		txt_CustomerName = new JTextField(10);
		
		button_Query = new JButton("查询");
		button_Query.addActionListener(this);
		button_Cancel = new JButton("取消");
		button_Cancel.addActionListener(this);		
		
		jp1.add(label_CustomerName);
		jp1.add(txt_CustomerName);    
		jp2.add(button_Query);
		jp2.add(button_Cancel);
		
		add(jp1, BorderLayout.NORTH);
		add(jp2, BorderLayout.SOUTH);
		
		try {
				Class.forName("com.sqlserver.jdbc.Driver");
			} catch (ClassNotFoundException ee) {
				System.out.println("test" + ee);
			}		
	}
	
	/*查询按钮*/
	public void button_Query_Click() {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);;			
		
			String customerName = txt_CustomerName.getText().trim();
			/*获取当前日期*/
			String DateNow = SystemTime.SystemTimeNow();
			/*查询实际入住日期*/
			String SQL_SelectRealityDateOfCheckIn;
			SQL_SelectRealityDateOfCheckIn = "SELECT RealityDateOfCheckIn FROM CheckIn,Customer WHERE CheckIn.CSerialNumber=Customer.CSerialNumber AND Name='" + customerName + "'";
    		result_RealityDateOfCheckIn = sql.executeQuery(SQL_SelectRealityDateOfCheckIn);
    		String RealityDateOfCheckIn = "";
    		result_RealityDateOfCheckIn.beforeFirst();
    		while (result_RealityDateOfCheckIn.next()) {
    			RealityDateOfCheckIn = result_RealityDateOfCheckIn.getString(1).trim();
        		System.out.println(RealityDateOfCheckIn);
    		}
    		/*计算当前入住天数*/
    		int currentNumberOfCheckIn = DateDiff.Datediff(RealityDateOfCheckIn, DateNow);
			/*查询是否是VIP*/
    		String SQL_SelectisVIP;
    		SQL_SelectisVIP = "SELECT VIP FROM Customer WHERE Name='" + customerName + "'";
    		result_isVIP = sql.executeQuery(SQL_SelectisVIP);
    		String isVIP = "";
    		result_isVIP.beforeFirst();
    		while (result_isVIP.next()) {
    			isVIP = result_isVIP.getString(1).trim();
    		}
    		/*查询是否是老客户*/
    		String SQL_SelectisRegular;
    		SQL_SelectisRegular = "SELECT Regular FROM Customer WHERE Name='" + customerName + "'";
    		result_isRegular = sql.executeQuery(SQL_SelectisRegular);
    		String isRegular = "";
    		result_isRegular.beforeFirst();
    		while (result_isRegular.next()) {
    			isRegular = result_isRegular.getString(1).trim();
    		}
    		/*查询单价，VIP优惠，老用户优惠*/
    		int tempUnitPrice = 0;
    		double tempVIPDiscount = 0;
    		double tempRegularDiscount = 0;
    		String SQL_SelectExpense = "";
    		SQL_SelectExpense = "SELECT UnitPrice,VIPDiscount,RegularDiscount FROM RoomType,CheckIn,Customer WHERE CheckIn.CSerialNumber=Customer.CSerialNumber AND RoomType.RSerialNumber=CheckIn.RSerialNumber AND Name='" + customerName + "'";
    		result_Expense = sql.executeQuery(SQL_SelectExpense);
    		result_Expense.beforeFirst();
    		while (result_Expense.next()) {
    			tempUnitPrice = result_Expense.getInt(1);
        		tempVIPDiscount = result_Expense.getDouble(2);
        		tempRegularDiscount = result_Expense.getDouble(3);
    		}
    		/*当前房费*/
            int currentHotelCharge = 0;
    		if (isVIP.equals("NO") && isRegular.equals("NO")) {
    			currentHotelCharge = (tempUnitPrice * currentNumberOfCheckIn);
    		} else if (isVIP.equals("YES") && isRegular.equals("NO")) {
    			currentHotelCharge = (int)(tempUnitPrice * currentNumberOfCheckIn * tempVIPDiscount);
    		} else if (isVIP.equals("NO") && isRegular.equals("YES")) {
    			currentHotelCharge = (int)(tempUnitPrice * currentNumberOfCheckIn * tempRegularDiscount);
    		} else if (isVIP.equals("YES") && isRegular.equals("YES")) {
    			currentHotelCharge = (int)(tempUnitPrice * currentNumberOfCheckIn * tempVIPDiscount * tempRegularDiscount);
    		}
			/*查询押金与预付房费*/
			String SQL_SelectExpenseFromCheckIn;
			SQL_SelectExpenseFromCheckIn = "SELECT Name,CashPledgeAmount,PrepayHotelCharge FROM CheckIn,Customer WHERE CheckIn.CSerialNumber=Customer.CSerialNumber AND Name='" + customerName + "'";;
			resultSQL = sql.executeQuery(SQL_SelectExpenseFromCheckIn);
			resultSQL.last(); 
			int currentRow = resultSQL.getRow();
			if (currentRow == 0) {
				JOptionPane.showMessageDialog(this, "没有对应的客户信息", "提示", JOptionPane.WARNING_MESSAGE);
			} else {
                 int rowCount = currentRow;
                 rows = new Object[rowCount][cols.length];
		         int count = 0; 
		         resultSQL.beforeFirst();
		         while(resultSQL.next()) {
		             rows[count][0] = resultSQL.getString(1);/*客户姓名*/
		             rows[count][1] = currentNumberOfCheckIn;/*当前入住天数*/
		             rows[count][2] = resultSQL.getInt(2);/*押金*/
		             rows[count][3] = resultSQL.getInt(3);/*预付房费*/
		             rows[count][4] = currentHotelCharge;/*当前房费*/
		             rows[count][5] = resultSQL.getInt(2) + resultSQL.getInt(3) - currentHotelCharge;/*当前余额*/
		             count++;
		         } 
		    }
		    con.close();
		} catch (SQLException ee) {
			System.out.println(ee);
		}
		jtable = new JTable(rows,cols);
		add(new JScrollPane(jtable), BorderLayout.CENTER);
		validate();
    }
	
	/*退出*/
	public void button_Cancel_Click() {
		this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		Select frame_Select = new Select();
		frame_Select.setVisible(true);
		frame_Select.setBounds(220, 160, 900, 200);
		frame_Select.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button_Query) {
			button_Query_Click();
		} else if (e.getSource() == button_Cancel) {
		    button_Cancel_Click();
		}
	}

}
