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
	
	/*�������ڵ��ڿؼ�*/
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
		
		label_HotelName = new JLabel("�Ƶ���");
		list_HotelName = new JComboBox();	
		label_RoomType = new JLabel("�ͷ�����");
		list_RoomType = new JComboBox();
		label_ExpectedNumberOfCheckIn = new JLabel("Ԥ����ס����");
		txt_ExpectedNumberOfCheckIn = new JTextField(6);

		button_Add = new JButton("ȷ��Ԥ��");
		button_Add.addActionListener(this);
        button_Clear = new JButton("���");
        button_Clear.addActionListener(this);
        button_Cancel = new JButton("�˳�");
        button_Cancel.addActionListener(this);

		jpanel1.setBorder(new TitledBorder("��ʼԤ��:"));
		
		jpanel1.add(label_HotelName);
		jpanel1.add(list_HotelName);
		list_HotelName.addItem("(NULL)");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨��������բ·��");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨б����·��");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨��ɽ��̫��");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨����Է����");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨��;������վ��");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨��̲���ʿ������ģ�");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨����·��");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨�ӳ�·����վ��");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨�ӳ�·��");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨����·)");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨����·��");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨�������");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨����·��");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨�����·����վ��");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨���ֵ꣩");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨����·��");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨����·��");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨����·��");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨��ǳ���");
		list_HotelName.addItem("���ֺ�̩�Ƶ꣨ԥ԰�꣩");
		
		jpanel1.add(label_RoomType);
		jpanel1.add(list_RoomType);
		list_RoomType.addItem("(NULL)");
		list_RoomType.addItem("��׼��");
		list_RoomType.addItem("�����");
		list_RoomType.addItem("������");
		list_RoomType.addItem("�󴲷�");
		list_RoomType.addItem("��ͥ��");
		
		label_ExpectedDateOfCheckIn = new JLabel("Ԥ����סʱ��");
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
		
    public void button_Add_Click() {
    	try {
    		con = DriverManager.getConnection(url, userName, password);
		    sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);		
    		
			String HotelName = list_HotelName.getSelectedItem().toString().trim();
			String TypeName = list_RoomType.getSelectedItem().toString().trim();
            String tempExpectedDateOfCheckIn = spinner_Calendar.getValue().toString().trim();
			String ExpectedDateOfCheckIn = TransDateString.transDateString(tempExpectedDateOfCheckIn);
			String ExpectedNumberOfCheckIn = txt_ExpectedNumberOfCheckIn.getText().trim();
			
			/*�����ж������Ԥ����ס����*/
			if(!(ExpectedNumberOfCheckIn.matches("[1-9]+"))) {
				JOptionPane.showMessageDialog(this, "Ԥ����סʱ��ӦΪ������", "ERROR",JOptionPane.PLAIN_MESSAGE);
				txt_ExpectedNumberOfCheckIn.setText("");
			}
			
		    if (HotelName.equals("(NULL)") || TypeName.equals("(NULL)")) {
		    	JOptionPane.showMessageDialog(this, "�Ƶ����뷿�����;�����Ϊ��", "WARNING",JOptionPane.PLAIN_MESSAGE);
		    } else {
		    	/*�õ���ǰ������currentRow*/
		        String SQL_SelectCurrentRow;
		        SQL_SelectCurrentRow = "SELECT * FROM Reservation";
			    result_CurrentRow = sql.executeQuery(SQL_SelectCurrentRow);
			    result_CurrentRow.last();
			    int currentRow = result_CurrentRow.getRow();
			    
			    /*����Ƿ��п�Ԥ���ķ���*/
			    String SQL_SelectRoomIsRemaining;
			    SQL_SelectRoomIsRemaining = "SELECT COUNT(Room.RSerialNumber) FROM Hotel,Room,RoomType WHERE Hotel.HSerialNumber=Room.HSerialNumber AND RoomStatus='������' AND RoomType.RSerialNumber=Room.RSerialNumber AND TypeName='" + TypeName + "' AND HotelName='" + HotelName + "'";
			    result_SelectRoomIsRemaining = sql.executeQuery(SQL_SelectRoomIsRemaining);
			    boolean roomIsRemaining = result_SelectRoomIsRemaining.next();
			    if (roomIsRemaining == false) {
			        JOptionPane.showMessageDialog(this, "�Ѿ�û�п���Ԥ���ķ�����", "WARNING", JOptionPane.WARNING_MESSAGE);
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
						 JOptionPane.showMessageDialog(this, "δ֪���������²���", "WARNING", JOptionPane.WARNING_MESSAGE);
					 } else {
					     CSerialNumber = result_SelectHotelAndCustomerInformation.getString(1).trim();
						 HSerialNumber = result_SelectHotelAndCustomerInformation.getString(2).trim();
						 customerName = result_SelectHotelAndCustomerInformation.getString(3).trim();
						 //System.out.println(CSerialNumber);
						 //System.out.println(HSerialNumber);
						 //System.out.println(customerName);
						 /*����ReservationԤ����*/
						 SQL_InsertNewReservation = "INSERT INTO Reservation(ReservationNumber,CSerialNumber,HSerialNumber,Name,TypeName,ExpectedDateOfCheckIn,ExpectedNumberOfCheckIn,HotelName) VALUES(" + (20000001+currentRow) + "," + CSerialNumber + "," + HSerialNumber + ",'" + customerName + "','" + TypeName + "','" + ExpectedDateOfCheckIn + "','" + ExpectedNumberOfCheckIn + "','" + HotelName + "')";			
						 //System.out.println(SQL_InsertNewReservation);
						
						 int flag = sql.executeUpdate(SQL_InsertNewReservation);
						 if (flag == 1) {
							 JOptionPane.showMessageDialog(this, "Ԥ���ɹ�", "CONGRATULATION", JOptionPane.PLAIN_MESSAGE);
						 }
						 
						 /*Ԥ���ɹ���ʹ��Ӧ�����е�һ�����ڡ���Ԥ����״̬*/
						 SQL_ExecReservationRoom = "EXEC ReservationRoom '" + HotelName + "','" + TypeName + "'";
						 //System.out.println(SQL_ExecReservationRoom);
						 sql.executeQuery(SQL_ExecReservationRoom);
						
					 }
					 con.close();	
				 }
			 }
  
		 } catch (SQLException ee) {
			    System.out.println(ee);
		 }  	
	   
	}
    
    public void button_Clear_Click() {
    	list_HotelName.setSelectedIndex(0);
    	list_RoomType.setSelectedIndex(0);
		txt_ExpectedNumberOfCheckIn.setText("");
	}
	
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
