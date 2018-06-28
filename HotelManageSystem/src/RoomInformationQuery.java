import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

/*��ѯ���������Ŀͷ���Ϣ*/
public class RoomInformationQuery extends JFrame implements ActionListener {
	
	JPanel jpanel1,jpanel2;
	JLabel label_HotelName,label_RoomType;
	JComboBox list_HotelName,list_RoomType;
	JButton button_Query,button_Cancel;
	JTable jtable;
	
	Object cols[]= {"�ͷ����","ʣ�෿����","����","Ѻ��"};
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
		
		jpanel1.setBorder(new TitledBorder("����Ҫ��ѯ�Ŀͷ���Ϣ"));
		
		label_HotelName = new JLabel("�Ƶ���");
		list_HotelName = new JComboBox();
		label_RoomType = new JLabel("�ͷ�����");
		list_RoomType = new JComboBox();
		
		button_Query = new JButton("��ѯ");
		button_Query.addActionListener(this);
		button_Cancel = new JButton("ȡ��");
		button_Cancel.addActionListener(this);
		
		jpanel1.add(label_HotelName);
		jpanel1.add(list_HotelName);
		list_RoomType.addItem("(NULL)");
		list_RoomType.addItem("��׼��");
		list_RoomType.addItem("�����");
		list_RoomType.addItem("������");
		list_RoomType.addItem("�󴲷�");
		list_RoomType.addItem("��ͥ��");
		
		jpanel1.add(label_RoomType);
		jpanel1.add(list_RoomType);
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
	
	/*��ѯ��ť*/
	public void button_Query_Click() {
	    try {
			con = DriverManager.getConnection(url, user, pwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
			String hotelName = list_HotelName.getSelectedItem().toString().trim();
			String roomType = list_RoomType.getSelectedItem().toString().trim();
			
			if (hotelName.equals("(NULL)") || roomType.equals("(NULL)")) {
				JOptionPane.showMessageDialog(this, "�Ƶ����Ϳͷ����;�����Ϊ��", "WARNING",JOptionPane.PLAIN_MESSAGE);
			} else {
				/*��ѯ�ͷ�״̬*/
			    String SQL_SelectRoomInformation;
			    SQL_SelectRoomInformation = "EXEC SelectRoom '" + hotelName + "','" + roomType + "'";
			    result_SelectRoomInformation = sql.executeQuery(SQL_SelectRoomInformation);
			    result_SelectRoomInformation.last(); 
			    int currentRow = result_SelectRoomInformation.getRow();
			
			    if (currentRow == 0) {
				    JOptionPane.showMessageDialog(this, "û�ж�Ӧ�Ŀͷ���Ϣ", "��ʾ", JOptionPane.WARNING_MESSAGE);
				
			    } else {
			    	/*��row[][]���շ��������Ŀͷ���Ϣ*/
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
	
	/*�˳�*/
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
