import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;


/*客人信息查询*/
public class CustomerInformationQuery extends JFrame implements ActionListener {

	JLabel label_CSerialNumber;
	JTextField txt_CSerialNumber;
	JLabel label_RSerialNumber;
	JTextField txt_RSerialNumber;
	JLabel label_CustomerName;
	JTextField txt_CustomerName;
	JLabel label_CustomerTelephone;
	JTextField txt_CustomerTelephone;
	JLabel label_CustomerIDNumber;
	JTextField txt_CustomerIDNumber;
	JLabel label_CustomerAddress;
	JTextField txt_CustomerAddress;
	JLabel label_CustomerCompany;
	JTextField txt_CustomerCompany;
	
	JPanel jpanel1,jpanel2;
	
	JButton button_Query,button_Cancel;
	
	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=HotelManagement;";
	String user = "sa";
	String pwd = "cym331s09";
	
	Connection con = null;
	Statement sql = null;
	ResultSet result_ExecCustomerInformationQuery = null;

	public CustomerInformationQuery() {

		jpanel1 = new JPanel();
		jpanel2 = new JPanel();
		
		jpanel1.setBorder(new TitledBorder("输入要查询的用户信息"));
		
		label_CSerialNumber = new JLabel("客户编号：");
		txt_CSerialNumber = new JTextField(10);
		label_RSerialNumber = new JLabel("所住客房编号：");
		txt_RSerialNumber = new JTextField(10);
		label_CustomerName = new JLabel("客户姓名：");
		txt_CustomerName = new JTextField(10);
		label_CustomerTelephone = new JLabel("联系电话");
		txt_CustomerTelephone = new JTextField(10);
		label_CustomerIDNumber = new JLabel("身份证号");
		txt_CustomerIDNumber = new JTextField(10);
		label_CustomerAddress = new JLabel("家庭住址");
		txt_CustomerAddress = new JTextField(10);
		label_CustomerCompany = new JLabel("工作单位");
		txt_CustomerCompany = new JTextField(10);
		
		button_Query = new JButton("查询");
		button_Query.addActionListener(this);
		button_Cancel = new JButton("取消");
		button_Cancel.addActionListener(this);
		
		jpanel1.add(label_CSerialNumber);
		jpanel1.add(txt_CSerialNumber);
		txt_CustomerName.addActionListener(this);
		jpanel1.add(label_RSerialNumber);
		jpanel1.add(txt_RSerialNumber);
		txt_CustomerName.addActionListener(this);
		jpanel1.add(label_CustomerName);
		jpanel1.add(txt_CustomerName);
		txt_CustomerName.addActionListener(this);
		jpanel1.add(label_CustomerTelephone);
		jpanel1.add(txt_CustomerTelephone);	 
		txt_CustomerTelephone.addActionListener(this);
		jpanel1.add(label_CustomerIDNumber);
		jpanel1.add(txt_CustomerIDNumber);
		txt_CustomerIDNumber.addActionListener(this);
		jpanel1.add(label_CustomerAddress);
		jpanel1.add(txt_CustomerAddress);
		txt_CustomerAddress.addActionListener(this);
		jpanel1.add(label_CustomerCompany);
		jpanel1.add(txt_CustomerCompany);
		txt_CustomerCompany.addActionListener(this);
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
			
			String CSerialNumber = txt_CSerialNumber.getText().trim();
			String RSerialNumber = txt_RSerialNumber.getText().trim();
			String customerName = txt_CustomerName.getText().trim();
			String customerTelephone = txt_CustomerTelephone.getText().trim();
			String customerIDNumber = txt_CustomerIDNumber.getText().trim();
			String customerAddress = txt_CustomerAddress.getText().trim();
			String customerCompany = txt_CustomerCompany.getText().trim();
			
			/*不定条件查询用户信息*/
			String SQL_ExecCustomerInformationQuery;
			SQL_ExecCustomerInformationQuery = "EXEC CustomerInformationQuery ";
			if (!(CSerialNumber.equals(""))) {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + CSerialNumber + ",";
			} else if (CSerialNumber.equals("")) {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + "null,";
			}
			if (!(RSerialNumber.equals(""))) {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + "'" + RSerialNumber + "',";
			} else {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + "null,";
			}
			if (!(customerName.equals(""))) {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + "'" + customerName + "',";
			} else {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + "null,";
			}
			if (!(customerTelephone.equals(""))) {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + "'" + customerTelephone + "',";
			} else {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + "null,";
			}
			if (!(customerIDNumber.equals(""))) {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + "'" + customerIDNumber + "',";
			} else {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + "null,";
			}
			if (!(customerAddress.equals(""))) {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + "'" + customerAddress + "',";
			} else {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + "null,";
			}
			if (!(customerCompany.equals(""))) {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + "'" + customerCompany + "',";
			} else {
				SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery + "null,";
			}
			/*裁剪SQL语句最后的“,”以符合语法规则*/
			SQL_ExecCustomerInformationQuery = SQL_ExecCustomerInformationQuery.substring(0, SQL_ExecCustomerInformationQuery.length()-1);
			result_ExecCustomerInformationQuery = sql.executeQuery(SQL_ExecCustomerInformationQuery);
			boolean isHavingData = result_ExecCustomerInformationQuery.next();
			if (isHavingData == false) {
				JOptionPane.showMessageDialog(this, "没有对应的客户信息", "提示", JOptionPane.WARNING_MESSAGE);
				txt_CSerialNumber.setText("");
				txt_RSerialNumber.setText("");
				txt_CustomerName.setText("");
				txt_CustomerTelephone.setText("");
				txt_CustomerIDNumber.setText("");
				txt_CustomerAddress.setText("");
				txt_CustomerCompany.setText("");
			} else {
				txt_CSerialNumber.setText(result_ExecCustomerInformationQuery.getString(1).trim());
				txt_RSerialNumber.setText(result_ExecCustomerInformationQuery.getString(2).trim());
				txt_CustomerName.setText(result_ExecCustomerInformationQuery.getString(3).trim());
				txt_CustomerTelephone.setText(result_ExecCustomerInformationQuery.getString(4).trim());
				txt_CustomerIDNumber.setText(result_ExecCustomerInformationQuery.getString(5).trim());
				txt_CustomerAddress.setText(result_ExecCustomerInformationQuery.getString(6).trim());
				txt_CustomerCompany.setText(result_ExecCustomerInformationQuery.getString(7).trim());
			}
               con.close();
		} catch (SQLException ee) {
			System.out.println(ee);
		}  
	}
	
	/*退出*/
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
		} else if (e.getSource() == button_Cancel) {
		    button_Cancel_Click();
		}
	}

}
