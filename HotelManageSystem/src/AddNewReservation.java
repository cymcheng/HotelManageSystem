import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import javax.swing.border.*;
import java.util.Date;

public class AddNewReservation extends Enter_Manager implements ActionListener {

	JPanel jpanel1;
	JPanel jpanel2;
	
	JLabel label_HotelName,label_RoomType,label_ExpectedDateOfCheckIn,label_ExpectedNumberOfCheckIn;
	JComboBox list_HotelName,list_RoomType;
	JTextField txt_ExpectedNumberOfCheckIn;
	
	JButton button_Add,button_Clear,button_Cancel;
	
	/*创建日期调节控件*/
	JSpinner spinner_Calendar = new JSpinner(new SpinnerDateModel());
	JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner_Calendar,"MM-dd");
	
	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=HotelManagement;";
	String userName = "sa", password = "cym331s09";
	
	Connection con = null;
	Statement sql = null;
	
	ResultSet result_CurrentRow = null;
	ResultSet result_SelectRoomIsRemaining = null;
	ResultSet result_CSerialNumber = null;
	ResultSet result_HSerialNumber = null;
	ResultSet result_CustomerName = null;
	ResultSet result_SelectHotelAndCustomerInformation = null;
	ResultSet result_ExecReservationRoom = null;
	
	public AddNewReservation() {
		
		jpanel1 = new JPanel();
		jpanel2 = new JPanel();
		
		label_HotelName = new JLabel("酒店名");
		list_HotelName = new JComboBox();	
		label_RoomType = new JLabel("客房类型");
		list_RoomType = new JComboBox();
		label_ExpectedNumberOfCheckIn = new JLabel("预计入住天数");
		txt_ExpectedNumberOfCheckIn = new JTextField(6);

		button_Add = new JButton("确认预订");
		button_Add.addActionListener(this);
        button_Clear = new JButton("清除");
        button_Clear.addActionListener(this);
        button_Cancel = new JButton("退出");
        button_Cancel.addActionListener(this);

		jpanel1.setBorder(new TitledBorder("开始预订:"));
		
		jpanel1.add(label_HotelName);
		jpanel1.add(list_HotelName);
		list_HotelName.addItem("(NULL)");
		list_HotelName.addItem("格林豪泰酒店（静安区新闸路）");
		list_HotelName.addItem("格林豪泰酒店（斜土东路）");
		list_HotelName.addItem("格林豪泰酒店（中山沪太）");
		list_HotelName.addItem("格林豪泰酒店（智荟苑西）");
		list_HotelName.addItem("格林豪泰酒店（长途客运总站）");
		list_HotelName.addItem("格林豪泰酒店（外滩国际客运中心）");
		list_HotelName.addItem("格林豪泰酒店（武宁路）");
		list_HotelName.addItem("格林豪泰酒店（延长路地铁站）");
		list_HotelName.addItem("格林豪泰酒店（延长路）");
		list_HotelName.addItem("格林豪泰酒店（光新路)");
		list_HotelName.addItem("格林豪泰酒店（七浦路）");
		list_HotelName.addItem("格林豪泰酒店（大柏树）");
		list_HotelName.addItem("格林豪泰酒店（场中路）");
		list_HotelName.addItem("格林豪泰酒店（殷高西路地铁站）");
		list_HotelName.addItem("格林豪泰酒店（江浦店）");
		list_HotelName.addItem("格林豪泰酒店（邯郸路）");
		list_HotelName.addItem("格林豪泰酒店（共康路）");
		list_HotelName.addItem("格林豪泰酒店（长阳路）");
		list_HotelName.addItem("格林豪泰酒店（五角场）");
		list_HotelName.addItem("格林豪泰酒店（豫园店）");
		
		jpanel1.add(label_RoomType);
		jpanel1.add(list_RoomType);
		list_RoomType.addItem("(NULL)");
		list_RoomType.addItem("标准间");
		list_RoomType.addItem("商务间");
		list_RoomType.addItem("豪华间");
		list_RoomType.addItem("大床房");
		list_RoomType.addItem("家庭房");
		
		label_ExpectedDateOfCheckIn = new JLabel("预计入住时间");
		jpanel1.add(label_ExpectedDateOfCheckIn);
		spinner_Calendar.setEditor(dateEditor);
		spinner_Calendar.setValue(new Date());
		jpanel1.add(spinner_Calendar);
		
		jpanel1.add(label_ExpectedNumberOfCheckIn);
		jpanel1.add(txt_ExpectedNumberOfCheckIn);
		
		jpanel2.add(button_Add);
		jpanel2.add(button_Clear);
		jpanel2.add(button_Cancel);
		
	    add(jpanel1, BorderLayout.NORTH);
		add(jpanel2, BorderLayout.SOUTH);
		 
		try {
			Class.forName("com.sqlserver.jdbc.Driver");
		} catch (ClassNotFoundException ee) {
			System.out.println("test" + ee);
		}
		
	}
	
	/*预订按钮*/
    public void button_Add_Click() {
    	try {
    		con = DriverManager.getConnection(url, userName, password);
		    sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);		
    		
			String HotelName = list_HotelName.getSelectedItem().toString().trim();
			String TypeName = list_RoomType.getSelectedItem().toString().trim();
            String tempExpectedDateOfCheckIn = spinner_Calendar.getValue().toString().trim();
			String ExpectedDateOfCheckIn = TransDateString.transDateString(tempExpectedDateOfCheckIn);
			String ExpectedNumberOfCheckIn = txt_ExpectedNumberOfCheckIn.getText().trim();
			
			/*正则判断输入的预计入住天数*/
			if(!(ExpectedNumberOfCheckIn.matches("[1-9]+"))) {
				JOptionPane.showMessageDialog(this, "预计入住时间应为正整数", "ERROR",JOptionPane.PLAIN_MESSAGE);
				txt_ExpectedNumberOfCheckIn.setText("");
			}
			
		    if (HotelName.equals("(NULL)") || TypeName.equals("(NULL)")) {
		    	JOptionPane.showMessageDialog(this, "酒店名与房间类型均不能为空", "WARNING",JOptionPane.PLAIN_MESSAGE);
		    } else {
		    	/*得到当前订单数currentRow*/
		        String SQL_SelectCurrentRow;
		        SQL_SelectCurrentRow = "SELECT * FROM Reservation";
			    result_CurrentRow = sql.executeQuery(SQL_SelectCurrentRow);
			    result_CurrentRow.last();
			    int currentRow = result_CurrentRow.getRow();
			    
			    /*检查是否还有可预订的房间*/
			    String SQL_SelectRoomIsRemaining;
			    SQL_SelectRoomIsRemaining = "SELECT COUNT(Room.RSerialNumber) FROM Hotel,Room,RoomType WHERE Hotel.HSerialNumber=Room.HSerialNumber AND RoomStatus='空闲中' AND RoomType.RSerialNumber=Room.RSerialNumber AND TypeName='" + TypeName + "' AND HotelName='" + HotelName + "'";
			    result_SelectRoomIsRemaining = sql.executeQuery(SQL_SelectRoomIsRemaining);
			    boolean roomIsRemaining = result_SelectRoomIsRemaining.next();
			    if (roomIsRemaining == false) {
			        JOptionPane.showMessageDialog(this, "已经没有可以预订的房间了", "WARNING", JOptionPane.WARNING_MESSAGE);
			    } else {
			    	 String SQL_SelectHotelAndCustomerInformation;
					 SQL_SelectHotelAndCustomerInformation = "SELECT CSerialNumber,HSerialNumber,Name FROM Customer,Hotel WHERE HotelName='" + HotelName + "' AND AccountNumber='" + final_AccountNumber + "'";
					 //System.out.println(SQL_SelectHotelAndCustomerInformation);
					 result_SelectHotelAndCustomerInformation = sql.executeQuery(SQL_SelectHotelAndCustomerInformation);
					 boolean isHavingData = result_SelectHotelAndCustomerInformation.next();
					
					 String CSerialNumber = "";
					 String HSerialNumber = "";
					 String customerName = "";  
					 String SQL_InsertNewReservation = "";
					 String SQL_ExecReservationRoom = "";
					    
					 if (isHavingData == false) {
						 JOptionPane.showMessageDialog(this, "未知错误，请重新操作", "WARNING", JOptionPane.WARNING_MESSAGE);
					 } else {
					     CSerialNumber = result_SelectHotelAndCustomerInformation.getString(1).trim();
						 HSerialNumber = result_SelectHotelAndCustomerInformation.getString(2).trim();
						 customerName = result_SelectHotelAndCustomerInformation.getString(3).trim();
						 SQL_InsertNewReservation = "INSERT INTO Reservation(ReservationNumber,CSerialNumber,HSerialNumber,Name,TypeName,ExpectedDateOfCheckIn,ExpectedNumberOfCheckIn,HotelName) VALUES(" + (20000001+currentRow) + "," + CSerialNumber + "," + HSerialNumber + ",'" + customerName + "','" + TypeName + "','" + ExpectedDateOfCheckIn + "','" + ExpectedNumberOfCheckIn + "','" + HotelName + "')";			
						
						 int flag = sql.executeUpdate(SQL_InsertNewReservation);
						 if (flag == 1) {
							 JOptionPane.showMessageDialog(this, "预订成功", "CONGRATULATION", JOptionPane.PLAIN_MESSAGE);
						 }
						 
						 /*预订成功后使对应房间中的一个处于“已预订”状态*/
						 SQL_ExecReservationRoom = "EXEC ReservationRoom '" + HotelName + "','" + TypeName + "'";
						 sql.executeQuery(SQL_ExecReservationRoom);
						
					 }
					 con.close();	
				 }
			 }
  
		 } catch (SQLException ee) {
			    System.out.println(ee);
		 }  	
	   
	}
    
    /*清除*/
    public void button_Clear_Click() {
    	list_HotelName.setSelectedIndex(0);
    	list_RoomType.setSelectedIndex(0);
		txt_ExpectedNumberOfCheckIn.setText("");
	}
	
    /*退出*/
    public void button_Cancel_Click() {
		this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		Enter_Customer frame_Enter_Customer = new Enter_Customer();
		frame_Enter_Customer.setVisible(true);
		frame_Enter_Customer.setBounds(220, 160, 900, 150);
		frame_Enter_Customer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
    public void actionPerformed(ActionEvent e) {		
		if(e.getSource() == button_Add) {
			button_Add_Click();
		} else if(e.getSource() == button_Clear) {
		    button_Clear_Click();	
		} else if(e.getSource() == button_Cancel) {
			button_Cancel_Click();
		}
	}

}
