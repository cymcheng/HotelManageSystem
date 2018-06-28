import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.border.*;

public class AddNewCheckIn extends Enter_Manager implements ActionListener {

	JTextField txt_RSerialNumber;
	JPanel jpanel1,jpanel2;
	JButton button_Query,button_AddCheckIn,button_Clear,button_Cancel;
	JTable jtable;
	
	Object cols[]= {"�������","�û����","�Ƶ���","�Ƶ���","�û���","��������","Ԥ����סʱ��","Ԥ����ס����","Ԥ��ʱ��"};
	Object rows[][];

	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=HotelManagement;";
	String user = "sa", pwd = "cym331s09";
	
	Connection con = null;
	Statement sql = null;
	
	ResultSet result_SelectReservation = null;
	ResultSet result_isVIP = null;
	ResultSet result_isRegular = null;
	ResultSet result_ExpectedNumberOfCheckIn = null;
	ResultSet result_Expense = null;
	
	int reservationNumber_CheckIn = 0;
	int CSerialNumber_CheckIn = 0;
	int HSerialNumber_CheckIn = 0;
	String hotelName_CheckIn = "";
	String customerName_CheckIn = "";
	int RSerialNumber_CheckIn = 0;
	String typeName_Checkin = "";
	String expectedDateOfCheckIn_CheckIn = "";
	int expectedNumberOfCheckIn_CheckIn = 0;
	String reservationTime_CheckIn = "";
	

	public AddNewCheckIn() {

		jpanel1 = new JPanel();		 
	    jpanel2 = new JPanel();
	    
	    jpanel1.setBorder(new TitledBorder("���������Ķ�����Ϣ:"));
	    
	    txt_RSerialNumber = new JTextField(8);
		
		button_Query = new JButton("��ѯ");
	    button_Query.addActionListener(this);
		button_AddCheckIn = new JButton("������ס��Ϣ");
		button_AddCheckIn.addActionListener(this);
        button_Clear = new JButton("���");
        button_Clear.addActionListener(this);
        button_Cancel = new JButton("�˳�");
        button_Cancel.addActionListener(this); 
		
		jpanel1.add(new JLabel("�ͷ����"));
		jpanel1.add(txt_RSerialNumber);
		jpanel2.add(button_Query);
		jpanel2.add(button_AddCheckIn);
		jpanel2.add(button_Clear);
		jpanel2.add(button_Cancel);
		
	    add(jpanel1, BorderLayout.NORTH);
		add(jpanel2, BorderLayout.SOUTH);		
		 
		try {
			Class.forName("com.sqlserver.jdbc.Driver");
		} catch (ClassNotFoundException ee) {
			System.out.println("test" + ee);
		}
		
		rows = QueryReservation();
		jtable = new JTable(rows,cols);
		jtable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				showSelectedResrvationInformation();
			}
		});
		
		add(new JScrollPane(jtable), BorderLayout.CENTER);
		validate();
	}
	
    public Object[][] QueryReservation() {
    		
		try {
			con = DriverManager.getConnection(url, user, pwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);			
		
			String SQL_SelectReservation;
			SQL_SelectReservation = "SELECT * FROM Reservation";
			//System.out.println(SQL_SelectReservation);
			result_SelectReservation = sql.executeQuery(SQL_SelectReservation);
			result_SelectReservation.last();
			int currentRow = result_SelectReservation.getRow();
			//System.out.println(currentRow);
			if (currentRow == 0) {
				JOptionPane.showMessageDialog(this, "Ŀǰû��Ԥ����Ϣ", "��ʾ", JOptionPane.WARNING_MESSAGE);
			} else {
                 int rowCount = currentRow; 
				 System.out.println(rowCount+" the rows");
		            rows = new Object[rowCount][cols.length];
		            int count = 0; 
		            result_SelectReservation.beforeFirst();
		            while(result_SelectReservation.next()){
		            	System.out.println(count+","+result_SelectReservation.getString(1));
		                 rows[count][0] = result_SelectReservation.getString(1);
		                 rows[count][1] = result_SelectReservation.getString(2);  
		                 rows[count][2] = result_SelectReservation.getString(3);
		                 rows[count][3] = result_SelectReservation.getString(9);
		                 rows[count][4] = result_SelectReservation.getString(4);
		                 rows[count][5] = result_SelectReservation.getString(5);
		                 rows[count][6] = result_SelectReservation.getString(8);
		                 rows[count][7] = result_SelectReservation.getString(6);
		                 rows[count][8] = result_SelectReservation.getString(7);
		                 count++;
		           }
		           con.close();
			}		

		} catch (SQLException ee) {
			System.out.println(ee);
		}  
	    return rows;
    }
    
    public void button_AddCheckIn_Click() {
    	try {
    		con = DriverManager.getConnection(url, user, pwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);;
    		
    		int RSerialNumber = Integer.valueOf(txt_RSerialNumber.getText().trim());
    		
    		int cashPledgeAmount = 0;
    		int prepayHotelCharge = 0;
    		/*��ѯ�Ƿ���VIP*/
    		String SQL_SelectIsVIP;
    		//System.out.println(customerName_CheckIn);
    		SQL_SelectIsVIP = "SELECT VIP FROM Customer WHERE Name='" + customerName_CheckIn + "'";
    		result_isVIP = sql.executeQuery(SQL_SelectIsVIP);
    		//System.out.println(SQL_SelectIsVIP);
    		String isVIP = "";
    		result_isVIP.beforeFirst();
    		while (result_isVIP.next()) {
    			isVIP = result_isVIP.getString(1).trim();//��toString��ΪgetString(1)
        		System.out.println(isVIP);
    		}
    		/*��ѯ�Ƿ������û�*/
    		String SQL_SelectisRegular;
    		SQL_SelectisRegular = "SELECT Regular FROM Customer WHERE Name='" + customerName_CheckIn + "'";
    		result_isRegular = sql.executeQuery(SQL_SelectisRegular);
    		//System.out.println(SQL_SelectisRegular);
    		String isRegular = "";
    		result_isRegular.beforeFirst();
    		while (result_isRegular.next()) {
    			isRegular = result_isRegular.getString(1).trim();
    			//System.out.println(isRegular);
    		}
    		/*���Ԥ����ס������*/
    		int tempExpectedNumberOfCheckIn = 0;
    		String SQL_SelectExpectedNumberOfCheckIn;
    		SQL_SelectExpectedNumberOfCheckIn = "SELECT ExpectedNumberOfCheckIn FROM Reservation,Customer WHERE Reservation.CSerialNumber=Customer.CSerialNumber AND Customer.Name='" + customerName_CheckIn + "'";
    		result_ExpectedNumberOfCheckIn = sql.executeQuery(SQL_SelectExpectedNumberOfCheckIn);
    		//System.out.println(SQL_SelectExpectedNumberOfCheckIn);
    		result_ExpectedNumberOfCheckIn.beforeFirst();
    		while (result_ExpectedNumberOfCheckIn.next()) {
    			tempExpectedNumberOfCheckIn = result_ExpectedNumberOfCheckIn.getInt(1);//��Integer.valueOf(.toString().trim())��Ϊ.getInt(index)
    		    //System.out.println(tempExpectedNumberOfCheckIn);
    		}
    		
    		int tempCashPledge = 0;
    		int tempUnitPrice = 0;
    		double tempVIPDiscount = 0;
    		double tempRegularDiscount = 0;
    		String SQL_SelectExpense = "";
    		SQL_SelectExpense = "SELECT UnitPrice,VIPDiscount,RegularDiscount,CashPledge FROM RoomType,Customer,Reservation,Room WHERE Reservation.CSerialNumber=Customer.CSerialNumber  AND RoomType.TypeName=Reservation.TypeName  AND Customer.Name='" + customerName_CheckIn + "'AND Reservation.HSerialNumber=Room.HSerialNumber AND Room.RSerialNumber=RoomType.RSerialNumber GROUP BY Room.RSerialNumber,UnitPrice,VIPDiscount,RegularDiscount,CashPledge HAVING Room.RSerialNumber=" + RSerialNumber;
    		result_Expense = sql.executeQuery(SQL_SelectExpense);
    		//System.out.println(SQL_SelectExpense);
    		result_Expense.beforeFirst();
    		while (result_Expense.next()) {
    			tempUnitPrice = result_Expense.getInt(1);
        		tempVIPDiscount = result_Expense.getDouble(2);
        		tempRegularDiscount = result_Expense.getDouble(3);
        		tempCashPledge = result_Expense.getInt(4);
    		    //System.out.println("UnitPrice        " + tempUnitPrice);
    		    //System.out.println("VIPDiscount      " + tempVIPDiscount);
    		    //System.out.println("RegularDiscount  " + tempRegularDiscount);
    		    //System.out.println("CashPledge       " + tempCashPledge);
    		}
    		/*����Ѻ���Ԥ������*/
    		if (isVIP.equals("NO") && isRegular.equals("NO")) {
    			prepayHotelCharge = (tempUnitPrice * tempExpectedNumberOfCheckIn);
    			cashPledgeAmount = tempCashPledge;
    		} else if (isVIP.equals("YES") && isRegular.equals("NO")) {
    			prepayHotelCharge = (int)(tempUnitPrice * tempExpectedNumberOfCheckIn * tempVIPDiscount);
    			cashPledgeAmount = (int)(tempCashPledge * tempVIPDiscount);
    		} else if (isVIP.equals("NO") && isRegular.equals("YES")) {
    			prepayHotelCharge = (int)(tempUnitPrice * tempExpectedNumberOfCheckIn * tempRegularDiscount);
    			cashPledgeAmount = (int)(tempCashPledge * tempRegularDiscount);
    		} else if (isVIP.equals("YES") && isRegular.equals("YES")) {
    			prepayHotelCharge = (int)(tempUnitPrice * tempExpectedNumberOfCheckIn * tempVIPDiscount * tempRegularDiscount);
    			cashPledgeAmount = (int)(tempCashPledge * tempVIPDiscount * tempRegularDiscount);
    		}
    		
		    if (reservationNumber_CheckIn == 0 || CSerialNumber_CheckIn == 0 || HSerialNumber_CheckIn == 0 || hotelName_CheckIn.equals("") || typeName_Checkin.equals("") || expectedNumberOfCheckIn_CheckIn == 0 || expectedDateOfCheckIn_CheckIn.equals("")) {
		    	JOptionPane.showMessageDialog(this, "�п����ݣ��޷�����", "WARNING",JOptionPane.PLAIN_MESSAGE);
		    } else {
		    	/*������ס��Ϣ*/
			    String SQL_InsertNewCheckIn;
			    SQL_InsertNewCheckIn = "INSERT INTO CheckIn(CSerialNumber,HSerialNumber,RSerialNumber,ReservationNumber,ExpectedDateOfCheckIn,CurrentNumberOfCheckIn,CashPledgeAmount,PrepayHotelCharge) VALUES('" + CSerialNumber_CheckIn + "','" + HSerialNumber_CheckIn + "','" + RSerialNumber + "','" + reservationNumber_CheckIn+ "','" + expectedDateOfCheckIn_CheckIn + "',1,'" + cashPledgeAmount + "','" + prepayHotelCharge + "')";
			    //System.out.println(SQL_InsertNewCheckIn);
			    
			    int flag = sql.executeUpdate(SQL_InsertNewCheckIn);
			    if (flag == 1) {
				    JOptionPane.showMessageDialog(this, "��ס�ɹ�", "�ɹ�", JOptionPane.PLAIN_MESSAGE);
			    }
			    
			    //rows = QueryReservation();		     
		 	    jtable.setModel(new DefaultTableModel(rows, cols));
		        }
		    con.close();
		    } catch (SQLException ee) {
			    System.out.println(ee);
		}  	
    }
    
    public void button_Query_Click() {
    	if (customerName_CheckIn.equals("")) {
    		JOptionPane.showMessageDialog(this, "����ѡ��һ�����ݣ�", "WARNING", JOptionPane.PLAIN_MESSAGE);
    	} else {
    		JFrame.setDefaultLookAndFeelDecorated(true);
    		SelectRoomInterface frame_tempForSelectRoom = new SelectRoomInterface(customerName_CheckIn);
    		frame_tempForSelectRoom.setVisible(true);
    		frame_tempForSelectRoom.setBounds(200, 200, 600, 300);
    		frame_tempForSelectRoom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		
    	}
    }
    
    public void button_Clear_Click() {
		txt_RSerialNumber.setText("");
	}
	
    public void button_Cancel_Click() {
		this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		Select frame_select = new Select();
		frame_select.setVisible(true);
		frame_select.setBounds(200, 160, 900, 150);
		frame_select.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		reservationNumber_CheckIn = Integer.valueOf(data[0].trim());
		CSerialNumber_CheckIn = Integer.valueOf(data[1].trim());
		HSerialNumber_CheckIn = Integer.valueOf(data[2].trim());
		hotelName_CheckIn = data[3].trim();
		customerName_CheckIn = data[4].trim();
		typeName_Checkin = data[5].trim();
		expectedDateOfCheckIn_CheckIn = data[6].trim();
		expectedNumberOfCheckIn_CheckIn = Integer.valueOf(data[7].trim());
	    reservationTime_CheckIn = data[8].trim();
    }
    
    public void actionPerformed(ActionEvent e) {		
		if (e.getSource() == button_Clear) {
		    button_Clear_Click();	
		} else if (e.getSource() == button_Cancel) {
			button_Cancel_Click();
		} else if (e.getSource() == button_AddCheckIn) {
			button_AddCheckIn_Click();
		} else if (e.getSource() == button_Query) {
			button_Query_Click();
		}
	}

}