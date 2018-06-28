import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

/*查询客房信息*/
public class RoomInformationQuery extends JFrame implements ActionListener {
	
	JPanel jpanel1,jpanel2;
	JLabel label_HotelName,label_RoomType;
	JComboBox list_HotelName,list_RoomType;
	JButton button_Query,button_Cancel;
	JTable jtable;
	
	Object cols[]= {"와렛긍뵀","假岱렛쇌鑒","데송","紀쏜"};
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
		
		jpanel1.setBorder(new TitledBorder("渴흙狼꿴璂돨와렛斤口"));
		
		label_HotelName = new JLabel("아듦츰");
		list_HotelName = new JComboBox();
		label_RoomType = new JLabel("와렛잚謹");
		list_RoomType = new JComboBox();
		
		button_Query = new JButton("꿴璂");
		button_Query.addActionListener(this);
		button_Cancel = new JButton("혤句");
		button_Cancel.addActionListener(this);
		
		jpanel1.add(label_HotelName);
		jpanel1.add(list_HotelName);
		list_RoomType.addItem("(NULL)");
		list_RoomType.addItem("깃硫쇌");
		list_RoomType.addItem("蛟쇌");
		list_RoomType.addItem("봉빽쇌");
		list_RoomType.addItem("댕눠렛");
		list_RoomType.addItem("소磎렛");
		
		jpanel1.add(label_RoomType);
		jpanel1.add(list_RoomType);
		list_HotelName.addItem("(NULL)");
		list_HotelName.addItem("목주봉揭아듦（쓰갛혐劤螺쨌）");
		list_HotelName.addItem("목주봉揭아듦（閨皐땜쨌）");
		list_HotelName.addItem("목주봉揭아듦（櫓빴格）");
		list_HotelName.addItem("목주봉揭아듦（例俯韜鮫）");
		list_HotelName.addItem("목주봉揭아듦（낀槁와頓悧籃）");
		list_HotelName.addItem("목주봉揭아듦（棍牽벌셥와頓櫓懃）");
		list_HotelName.addItem("목주봉揭아듦（嶠퀼쨌）");
		list_HotelName.addItem("목주봉揭아듦（儺낀쨌뒈屆籃）");
		list_HotelName.addItem("목주봉揭아듦（儺낀쨌）");
		list_HotelName.addItem("목주봉揭아듦（밟劤쨌)");
		list_HotelName.addItem("목주봉揭아듦（펌팻쨌）");
		list_HotelName.addItem("목주봉揭아듦（댕겝疳）");
		list_HotelName.addItem("목주봉揭아듦（끝櫓쨌）");
		list_HotelName.addItem("목주봉揭아듦（凌멕鮫쨌뒈屆籃）");
		list_HotelName.addItem("목주봉揭아듦（쉭팻듦）");
		list_HotelName.addItem("목주봉揭아듦（벳덱쨌）");
		list_HotelName.addItem("목주봉揭아듦（묾영쨌）");
		list_HotelName.addItem("목주봉揭아듦（낀捺쨌）");
		list_HotelName.addItem("목주봉揭아듦（巧실끝）");
		list_HotelName.addItem("목주봉揭아듦（滔蹈듦）");
		
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
	
	/*꿴璂객큐*/
	public void button_Query_Click() {
	    try {
			con = DriverManager.getConnection(url, user, pwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
			String hotelName = list_HotelName.getSelectedItem().toString().trim();
			String roomType = list_RoomType.getSelectedItem().toString().trim();
			
			if (hotelName.equals("(NULL)") || roomType.equals("(NULL)")) {
				JOptionPane.showMessageDialog(this, "아듦츰뵨와렛잚謹엇꼇콘槨왕", "WARNING",JOptionPane.PLAIN_MESSAGE);
			} else {
				/*꿴璂와렛榴檄*/
			    String SQL_SelectRoomInformation;
			    SQL_SelectRoomInformation = "EXEC SelectRoom '" + hotelName + "','" + roomType + "'";
			    result_SelectRoomInformation = sql.executeQuery(SQL_SelectRoomInformation);
			    result_SelectRoomInformation.last(); 
			    int currentRow = result_SelectRoomInformation.getRow();
			
			    if (currentRow == 0) {
				    JOptionPane.showMessageDialog(this, "청唐뚤壇돨와렛斤口", "瓊刻", JOptionPane.WARNING_MESSAGE);
				
			    } else {
			    	/*痰row[][]쌈澗륜북係숭돨와렛斤口*/
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
