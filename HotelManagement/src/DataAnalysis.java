import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import javax.swing.border.*;
import java.util.Date;

public class DataAnalysis extends Enter_Manager implements ActionListener {

	JLabel label_HotelName,label_StartDate,label_EndDate,label_SumCharge;
	JComboBox list_HotelName;
	JTextField txt_SumCharge;
	JPanel jpanel1,jpanel2,jpanel3;
	
	JButton button_Query,button_Cancel;
	
	JSpinner spinner_Calendar_StartDate = new JSpinner(new SpinnerDateModel());
	JSpinner.DateEditor dateEditor_StartDate = new JSpinner.DateEditor(spinner_Calendar_StartDate,"MM-dd");
	
	JSpinner spinner_Calendar_EndDate = new JSpinner(new SpinnerDateModel());
	JSpinner.DateEditor dateEditor_EndDate = new JSpinner.DateEditor(spinner_Calendar_EndDate,"MM-dd");
	
	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=HotelManagement;";
	String user = "sa", pwd = "cym331s09";
	
	Connection con = null;
	Statement sql = null;
	
	ResultSet result_SumCharge = null;
	ResultSet result_CSerialNumber = null;
	ResultSet result_HSerialNumber = null;
	ResultSet result_CustomerName = null;
	ResultSet result_tempSQL = null;

	public DataAnalysis() {
		
		jpanel1 = new JPanel();
		jpanel2 = new JPanel();
		jpanel3 = new JPanel();
		
		label_HotelName = new JLabel("�Ƶ���");
		list_HotelName = new JComboBox();
		label_StartDate = new JLabel("��ʼʱ��");
		label_EndDate = new JLabel("��ֹʱ��");
		label_SumCharge = new JLabel("������");
		txt_SumCharge = new JTextField(8);	
		
		button_Query = new JButton("����������");
		button_Query.addActionListener(this);
		button_Cancel = new JButton("�˳�");
        button_Cancel.addActionListener(this);

		jpanel1.setBorder(new TitledBorder("����������:"));
		
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
		
		jpanel1.add(label_StartDate);
		spinner_Calendar_StartDate.setEditor(dateEditor_StartDate);
		spinner_Calendar_StartDate.setValue(new Date());
		jpanel1.add(spinner_Calendar_StartDate);
		
		jpanel1.add(label_EndDate);
		spinner_Calendar_EndDate.setEditor(dateEditor_EndDate);
		spinner_Calendar_EndDate.setValue(new Date());
		jpanel1.add(spinner_Calendar_EndDate);
		
		jpanel3.add(label_SumCharge);
		jpanel3.add(txt_SumCharge);
		
		jpanel2.add(button_Query);
		jpanel2.add(button_Cancel);
		
	    add(jpanel1, BorderLayout.NORTH);
	    add(jpanel3, BorderLayout.CENTER);
		add(jpanel2, BorderLayout.SOUTH);
		 
		try {
			Class.forName("com.sqlserver.jdbc.Driver");
		} catch (ClassNotFoundException ee) {
			System.out.println("test" + ee);
		}
		
	}
	
    public void button_Query_Click() {
    	try {
    		con = DriverManager.getConnection(url, user, pwd);
 		    sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);;			
    		
    		String hotelName = list_HotelName.getSelectedItem().toString().trim();
			/*��ʼʱ��*/
    		String tempStartDate = spinner_Calendar_StartDate.getValue().toString().trim();
			String StartDate = TransDateString.transDateString(tempStartDate);
            /*����ʱ��*/
			String tempEndDate = spinner_Calendar_EndDate.getValue().toString().trim();
			String EndDate = TransDateString.transDateString(tempEndDate);

		    if (list_HotelName.equals("(NULL)")) {
		    	JOptionPane.showMessageDialog(this, "�Ƶ�������Ϊ��", "WARNING",JOptionPane.PLAIN_MESSAGE);
		    } else {
		        String SQL_SelectCountCharge;
		        SQL_SelectCountCharge = "SELECT SUM(RealityHotelCharge) AS SUM_Charge FROM CheckOut,Hotel WHERE Hotel.HSerialNumber=CheckOut.HSerialNumber AND HotelName='" + hotelName + "' AND  DateOfCheckOut>'" + StartDate + "' AND DateOfCheckOut<'" + EndDate + "'";
			    result_SumCharge = sql.executeQuery(SQL_SelectCountCharge);
			    String sumCharge = "";
    		    result_SumCharge.beforeFirst();
    		    while (result_SumCharge.next()) {
    			    sumCharge = String.valueOf(result_SumCharge.getInt(1)).trim();
        		    //System.out.println(sumCharge);
    		    }
			    txt_SumCharge.setText(sumCharge);	
		    }
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
		frame_Select.setBounds(220, 160, 900, 200);
		frame_Select.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
    public void actionPerformed(ActionEvent e) {		
		if(e.getSource() == button_Query) {
			button_Query_Click();
		} else if(e.getSource() == button_Cancel) {
			button_Cancel_Click();
		}
	}
    
}
