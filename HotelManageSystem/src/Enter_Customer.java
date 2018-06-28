import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

/*µÇÂ¼½çÃæ-ÓÃ»§*/
public class Enter_Customer extends Enter implements ActionListener {
	
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
	
	public Enter_Customer() {
		
		jpanel1 = new JPanel();
		jpanel2 = new JPanel();
		
		label_AccountNumber = new JLabel("ÕËºÅ£º");
		txt_AccountNumber = new JTextField(10);
		
		label_Password = new JLabel("ÃÜÂë£º");
		txt_Password = new JPasswordField(10);
		
		button_Enter = new JButton("µÇÂ¼");
		button_Enter.addActionListener(this);
		
		button_Cancel = new JButton("·µ»Ø");
        button_Cancel.addActionListener(this);

		jpanel1.setBorder(new TitledBorder("ÓÃ»§µÇÂ¼:"));
		
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
	
	/*µÇÂ¼°´Å¥*/
	public void button_Enter_Click() {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String accountNumber = txt_AccountNumber.getText().trim();
			String password = String.valueOf(txt_Password.getPassword());

			String selectAccountNumberAndPassword;
			selectAccountNumberAndPassword = "SELECT * FROM Customer WHERE AccountNumber='" + accountNumber + "' AND PassWord='" + password +"'";
			
			result_AccountNumberAndPassword = sql.executeQuery(selectAccountNumberAndPassword);
			boolean accountNumberAndPasswordIsHavingData = result_AccountNumberAndPassword.next();
			
			if (accountNumberAndPasswordIsHavingData == false) {
				JOptionPane.showMessageDialog(this, "ÕËºÅ»òÃÜÂë´íÎó", "ÌáÊ¾", JOptionPane.WARNING_MESSAGE);
				txt_AccountNumber.setText("");
				txt_Password.setText("");
			} else {
				final_AccountNumber = accountNumber;
				final_Password = password;
				
				this.dispose();
				JFrame.setDefaultLookAndFeelDecorated(true);
				AddNewReservation frame_AddNewReservation = new AddNewReservation();
				frame_AddNewReservation.setVisible(true);
				frame_AddNewReservation.setBounds(220, 160, 900, 200);
				frame_AddNewReservation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
            con.close();
		} catch (SQLException ee) {
			System.out.println(ee);
		}  
	}
	
	/*ÍË³ö*/
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
