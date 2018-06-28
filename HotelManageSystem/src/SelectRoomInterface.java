import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

/*在选定一行预订信息并点击“查询”后用于选择客房的界面*/
public class SelectRoomInterface extends JFrame implements ActionListener {
	
	JPanel jpanel;
	
	JButton button_Cancel;
	JTable jtable;
	
	Object cols[]= {"客房编号","单价","VIP优惠","老用户优惠","押金"};
	Object rows[][];

	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=HotelManagement;";
	String user = "sa", pwd = "cym331s09";
	
	Connection con = null;
	Statement sql = null;
	ResultSet result_SelectRSerialNumber = null;

	public SelectRoomInterface(String customerName) {
		
		jpanel = new JPanel();
		
		button_Cancel = new JButton("关闭");
		button_Cancel.addActionListener(this);
		
		jpanel.add(button_Cancel);
	    
		add(jpanel, BorderLayout.SOUTH);
		
        try {
			con = DriverManager.getConnection(url, user, pwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);			
			
			/*查询客房编号*/
			String SQL_SelectRSerialNumber;
			SQL_SelectRSerialNumber = "SELECT Room.RSerialNumber,UnitPrice,VIPDiscount,RegularDiscount,CashPledge FROM RoomType,Customer,Reservation,Room WHERE Reservation.CSerialNumber=Customer.CSerialNumber AND RoomType.TypeName=Reservation.TypeName AND Customer.Name='" + customerName + "' AND RoomStatus='已预订' AND Reservation.HSerialNumber=Room.HSerialNumber AND Room.RSerialNumber=RoomType.RSerialNumber GROUP BY Room.RSerialNumber,UnitPrice,VIPDiscount,RegularDiscount,CashPledge";
			result_SelectRSerialNumber = sql.executeQuery(SQL_SelectRSerialNumber);
			result_SelectRSerialNumber.last();
			int currentRow = result_SelectRSerialNumber.getRow();
			if (currentRow == 0) {
				JOptionPane.showMessageDialog(this, "没有可入住的房间", "提示", JOptionPane.WARNING_MESSAGE);	
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
