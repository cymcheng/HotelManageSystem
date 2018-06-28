import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

/*��¼����-����Ա*/
public class Enter_Manager extends Enter implements ActionListener {
	
	JLabel label_AccountNumber,label_Password;
	JTextField txt_AccountNumber;
	JPasswordField txt_Password;
	
	JButton button_Enter,button_Cancel;
	
	JPanel jpanel1,jpanel2;
	
	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=HotelManagement;";
	String user = "sa";
	String pwd = "cym331s09";
	
	Connection con = null;
	Statement sql = null;
	
	ResultSet result_AccountNumberAndPassword = null;
	ResultSet result_ManageLevel = null;

	public Enter_Manager() {
		
		jpanel1 = new JPanel();
		jpanel2 = new JPanel();
		
		label_AccountNumber = new JLabel("�˺ţ�");
		txt_AccountNumber = new JTextField(10);
		
		label_Password = new JLabel("���룺");
		txt_Password = new JPasswordField(10);
		
		button_Enter = new JButton("��¼");
		button_Enter.addActionListener(this);
		
		button_Cancel = new JButton("����");
        button_Cancel.addActionListener(this);

		jpanel1.setBorder(new TitledBorder("����Ա��¼:"));
		
		jpanel1.add(label_AccountNumber);
		jpanel1.add(txt_AccountNumber);

		jpanel1.add(label_Password);
		jpanel1.add(txt_Password);

		jpanel2.add(button_Enter);
		jpanel2.add(button_Cancel);
		
	    add(jpanel1, BorderLayout.NORTH);
		add(jpanel2, BorderLayout.SOUTH);
		
		try {
				Class.forName("com.sqlserver.jdbc.Driver");
			} catch (ClassNotFoundException ee) {
				System.out.println("test" + ee);
			}		

	}
	
	/*��¼��ť*/
	public void button_Enter_Click() {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String accountNumber = txt_AccountNumber.getText().trim();
			String password = String.valueOf(txt_Password.getPassword());
			
			/*���������˺������Ƿ���ȷ*/
			String SQL_selectAccountNumberAndPassword;
			SQL_selectAccountNumberAndPassword = "SELECT * FROM Manager WHERE AccountNumber='" + accountNumber + "' AND Password='" + password +"'";
			result_AccountNumberAndPassword = sql.executeQuery(SQL_selectAccountNumberAndPassword);
			boolean accountNumberAndPasswordIsHavingData = result_AccountNumberAndPassword.next();
			if (accountNumberAndPasswordIsHavingData == false) {
				JOptionPane.showMessageDialog(this, "�˺Ż��������", "��ʾ", JOptionPane.WARNING_MESSAGE);
				txt_AccountNumber.setText("");
				txt_Password.setText("");
			} else {
				/*��ѯ�ù���Ա��Ȩ�޼���*/
				String SQL_SelectManageLevel;
				SQL_SelectManageLevel = "SELECT ManageLV FROM Manager WHERE AccountNumber='" + accountNumber + "' AND PassWord='" + password +"'";
				System.out.println(SQL_SelectManageLevel);
				result_ManageLevel = sql.executeQuery(SQL_SelectManageLevel);
				result_ManageLevel.next();
				int ManageLevel = result_ManageLevel.getInt(1);
				
				final_AccountNumber = accountNumber;
				final_Password = password;
				final_ManageLevel = ManageLevel;
				
				if (final_ManageLevel == 1) {
					this.dispose();
					JFrame.setDefaultLookAndFeelDecorated(true);
					Select frame_Select = new Select();
					frame_Select.setVisible(true);
					frame_Select.setBounds(220, 160, 900, 200);
					frame_Select.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} else if (final_ManageLevel == 2) {
					this.dispose();
					JFrame.setDefaultLookAndFeelDecorated(true);
					Select_TOPLevel frame_Select_TOPLevel = new Select_TOPLevel();
					frame_Select_TOPLevel.setVisible(true);
					frame_Select_TOPLevel.setBounds(220, 160, 900, 200);
					frame_Select_TOPLevel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				
			}
            con.close();
		} catch (SQLException ee) {
			System.out.println(ee);
		}  
	}
	
	/*�˳�*/
	public void button_Cancel_Click() {
		this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		Enter frame_Enter = new Enter();
		frame_Enter.setVisible(true);
		frame_Enter.setBounds(220, 160, 900, 150);
		frame_Enter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == button_Enter) {
			button_Enter_Click();
		} else if (e.getSource() == button_Cancel) {
			button_Cancel_Click();
		}
	}

}
