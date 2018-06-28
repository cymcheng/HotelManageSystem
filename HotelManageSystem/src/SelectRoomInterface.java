import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

/*��ѡ��һ��Ԥ����Ϣ���������ѯ��������ѡ��ͷ��Ľ���*/
public class SelectRoomInterface extends JFrame implements ActionListener {
	
	JPanel jpanel;
	
	JButton button_Cancel;
	JTable jtable;
	
	Object cols[]= {"�ͷ����","����","VIP�Ż�","���û��Ż�","Ѻ��"};
	Object rows[][];

	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=HotelManagement;";
	String user = "sa", pwd = "cym331s09";
	
	Connection con = null;
	Statement sql = null;
	ResultSet result_SelectRSerialNumber = null;

	public SelectRoomInterface(String customerName) {
		
		jpanel = new JPanel();
		
		button_Cancel = new JButton("�ر�");
		button_Cancel.addActionListener(this);
		
		jpanel.add(button_Cancel);
	    
		add(jpanel, BorderLayout.SOUTH);
		
        try {
			con = DriverManager.getConnection(url, user, pwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);			
			
			/*��ѯ�ͷ����*/
			String SQL_SelectRSerialNumber;
			SQL_SelectRSerialNumber = "SELECT Room.RSerialNumber,UnitPrice,VIPDiscount,RegularDiscount,CashPledge FROM RoomType,Customer,Reservation,Room WHERE Reservation.CSerialNumber=Customer.CSerialNumber AND RoomType.TypeName=Reservation.TypeName AND Customer.Name='" + customerName + "' AND RoomStatus='��Ԥ��' AND Reservation.HSerialNumber=Room.HSerialNumber AND Room.RSerialNumber=RoomType.RSerialNumber GROUP BY Room.RSerialNumber,UnitPrice,VIPDiscount,RegularDiscount,CashPledge";
			result_SelectRSerialNumber = sql.executeQuery(SQL_SelectRSerialNumber);
			result_SelectRSerialNumber.last();
			int currentRow = result_SelectRSerialNumber.getRow();
			if (currentRow == 0) {
				JOptionPane.showMessageDialog(this, "û�п���ס�ķ���", "��ʾ", JOptionPane.WARNING_MESSAGE);	
			} else {
                int rowCount = currentRow;
		        rows = new Object[rowCount][cols.length];
		        int count = 0; 
		        result_SelectRSerialNumber.beforeFirst();
		        while (result_SelectRSerialNumber.next()) {
		                rows[count][0] = result_SelectRSerialNumber.getString(1);
		                rows[count][1] = result_SelectRSerialNumber.getString(2);  
		                rows[count][2] = result_SelectRSerialNumber.getString(3);
		                rows[count][3] = result_SelectRSerialNumber.getString(4);
		                rows[count][4] = result_SelectRSerialNumber.getString(5);
		                count++;
		           }
			}
			jtable = new JTable(rows,cols);
			add(new JScrollPane(jtable), BorderLayout.CENTER);
			validate();
			con.close();
		} catch (SQLException ee) {
			System.out.println(ee);
		}  
	}
	
	public void actionPerformed(ActionEvent e) {		
		if (e.getSource() == button_Cancel) {
			this.dispose();
		} 
	}

}
