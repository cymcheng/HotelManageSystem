import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

/*查询符合条件的客房信息*/
public class RoomInformationQuery extends JFrame implements ActionListener {
	
	JPanel jpanel1,jpanel2;
	JLabel label_HotelName,label_RoomType;
	JComboBox list_HotelName,list_RoomType;
	JButton button_Query,button_Cancel;
	JTable jtable;
	
	Object cols[]= {"客房编号","剩余房间数","单价","押金"};
	Object rows[][];
	
	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=HotelManagement;";
	String user = "sa";
	String pwd = "cym331s09";
	
	Connection con = null;
	Statement sql = null;
	
	ResultSet result_SelectRoomInformation = null;

	public RoomInformationQuery() {
		
		jpanel1 = new JPanel();
		jpanel2 = new JPanel();
		
		jpanel1.setBorder(new TitledBorder("输入要查询的客房信息"));
		
		label_HotelName = new JLabel("酒店名");
		list_HotelName = new JComboBox();
		label_RoomType = new JLabel("客房类型");
		list_RoomType = new JComboBox();
		
		button_Query = new JButton("查询");
		button_Query.addActionListener(this);
		button_Cancel = new JButton("取消");
		button_Cancel.addActionListener(this);
		
		jpanel1.add(label_HotelName);
		jpanel1.add(list_HotelName);
		list_RoomType.addItem("(NULL)");
		list_RoomType.addItem("标准间");
		list_RoomType.addItem("商务间");
		list_RoomType.addItem("豪华间");
		list_RoomType.addItem("大床房");
		list_RoomType.addItem("家庭房");
		
		jpanel1.add(label_RoomType);
		jpanel1.add(list_RoomType);
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
		
		jpanel2.add(button_Query);
		jpanel2.add(button_Cancel);

		add(jpanel1, BorderLayout.NORTH);
		add(jpanel2, BorderLayout.SOUTH);
		
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
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
			String hotelName = list_HotelName.getSelectedItem().toString().trim();
			String roomType = list_RoomType.getSelectedItem().toString().trim();
			
			if (hotelName.equals("(NULL)") || roomType.equals("(NULL)")) {
				JOptionPane.showMessageDialog(this, "酒店名和客房类型均不能为空", "WARNING",JOptionPane.PLAIN_MESSAGE);
			} else {
				/*查询客房状态*/
			    String SQL_SelectRoomInformation;
			    SQL_SelectRoomInformation = "EXEC SelectRoom '" + hotelName + "','" + roomType + "'";
			    result_SelectRoomInformation = sql.executeQuery(SQL_SelectRoomInformation);
			    result_SelectRoomInformation.last(); 
			    int currentRow = result_SelectRoomInformation.getRow();
			
			    if (currentRow == 0) {
				    JOptionPane.showMessageDialog(this, "没有对应的客房信息", "提示", JOptionPane.WARNING_MESSAGE);
				
			    } else {
			    	/*用row[][]接收符合条件的客房信息*/
                    int rowCount = currentRow;
                    rows = new Object[rowCount][cols.length];
		            int flag = 0; 
		            result_SelectRoomInformation.beforeFirst();
		            while(result_SelectRoomInformation.next()) {
		                rows[flag][0] = result_SelectRoomInformation.getString(1);
		                rows[flag][1] = result_SelectRoomInformation.getString(2);
		                rows[flag][2] = result_SelectRoomInformation.getString(3);
		                rows[flag][3] = result_SelectRoomInformation.getString(4);
		                flag++;
		            }

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
		Select frame_select = new Select();
		frame_select.setVisible(true);
		frame_select.setBounds(220, 160, 900, 200);
		frame_select.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button_Query) {
			button_Query_Click();
		} else if (e.getSource() == button_Cancel) {
		    button_Cancel_Click();
		}
	}
	
}
