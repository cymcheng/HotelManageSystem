import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import javax.swing.border.*;

public class CustomerOvertimeQuery extends Enter_Manager implements ActionListener {
	
	JPanel jpanel1,jpanel2;
	JLabel label_RSerialNumber;
	JButton button_Cancel;
	JTable jtable;
	
	Object cols[]= {"�û����","�ͷ����","�û���","ʵ����סʱ��","Ԥ����ס����","��ǰ��ס����"};
	Object rows[][];

	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=HotelManagement;";
	String user = "sa", pwd = "cym331s09";
	Connection con = null;
	Statement sql = null;
	
	ResultSet result_SelectCheckIn = null;
	ResultSet result_ExpectedNumberOfCheckIn = null;
	ResultSet result_RealityDateOfCheckIn = null;

	public CustomerOvertimeQuery() {
		
		jpanel1 = new JPanel();		 
	    jpanel2 = new JPanel();
	    
	    jpanel1.setBorder(new TitledBorder("���������Ķ�����Ϣ:"));
	    
	    label_RSerialNumber = new JLabel("�ͷ����");
	    
        button_Cancel = new JButton("�˳�");
        button_Cancel.addActionListener(this);
		
		jpanel1.add(label_RSerialNumber);
		jpanel2.add(button_Cancel);
		
	    add(jpanel1, BorderLayout.NORTH);
		add(jpanel2, BorderLayout.SOUTH);		
		 
		try {
			Class.forName("com.sqlserver.jdbc.Driver");
		} catch (ClassNotFoundException ee) {
			System.out.println("test" + ee);
		}
		
		rows = QueryReservation();
		jtable = new JTable(rows, cols);
		
		add(new JScrollPane(jtable), BorderLayout.CENTER);
		validate();
	}
	
    public Object[][] QueryReservation() {	
		try {
			con = DriverManager.getConnection(url, user, pwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);			
			/*��õ�ǰ����*/
			String DateNow = SystemTime.SystemTimeNow();
			//System.out.println(DateNow);
			/*��ѯ��ʱ����*/
			String SQL_SelectCheckIn;
			SQL_SelectCheckIn = "SELECT * FROM CustomerOvertimeQueryView";
			//System.out.println(SQL_SelectCheckIn);
			result_SelectCheckIn = sql.executeQuery(SQL_SelectCheckIn);
			result_SelectCheckIn.last();
			int currentRow = result_SelectCheckIn.getRow();
			//System.out.println(currentRow);
			if (currentRow == 0) {
				JOptionPane.showMessageDialog(this, "Ŀǰû�г�ʱ����", "��ʾ", JOptionPane.WARNING_MESSAGE);
			} else {
                int rowCount = currentRow; 
				//System.out.println(rowCount+" the rows");
		        rows = new Object[rowCount][cols.length];
		        int count = 0; 
		        result_SelectCheckIn.beforeFirst();
		        while(result_SelectCheckIn.next()){
		            //System.out.println(count + "," + result_SelectCheckIn.getString(1));
		            rows[count][0] = result_SelectCheckIn.getInt(1);/*�û����*/
		            rows[count][1] = result_SelectCheckIn.getInt(2);/*�ͷ����*/
		            rows[count][2] = result_SelectCheckIn.getString(3);/*�û���*/
		            rows[count][3] = result_SelectCheckIn.getString(4);/*ʵ����סʱ��*/
		            //System.out.println(rows[count][3]);
		            rows[count][4] = result_SelectCheckIn.getInt(5);/*Ԥ����ס����*/
		            rows[count][5] = Integer.parseInt(String.valueOf(DateDiff.Datediff(result_SelectCheckIn.getString(4), DateNow)));/*ʵ����ס����*/
		            //System.out.println(DateDiff.Datediff(result_SelectCheckIn.getString(4), DateNow));
		            //System.out.println(rows[count][5]);
		            count++;
		        }
			}		
			con.close();
		} catch (SQLException ee) {
			System.out.println(ee);
		}  
	    return rows; 
    }
    
    public void button_Cancel_Click() {
		this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		Select frame_Select = new Select();
		frame_Select.setVisible(true);
		frame_Select.setBounds(200, 160, 900, 200);
		frame_Select.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
