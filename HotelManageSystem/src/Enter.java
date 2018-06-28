import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.awt.*;

/*登录界面-选择登录身份*/
public class Enter extends JFrame implements ActionListener {
	
	static String final_AccountNumber = "";
	static String final_Password = "";
	static int final_ManageLevel = 0;/*管理员等级*/

	JButton button_Enter_Customer,button_Enter_Manager,button_Cancel;
	
	JPanel jpanel1,jpanel2;
	
	public Enter() {
		
		jpanel1 = new JPanel();
		jpanel2 = new JPanel();
		
		button_Enter_Customer = new JButton("用户登录");
		button_Enter_Customer.addActionListener(this);
		
		button_Enter_Manager = new JButton("管理员登录");
		button_Enter_Manager.addActionListener(this);
		
		button_Cancel = new JButton("退出");
                button_Cancel.addActionListener(this);

		jpanel1.setBorder(new TitledBorder("选择登录身份"));

		jpanel2.add(button_Enter_Customer);
		jpanel2.add(button_Enter_Manager);
		jpanel2.add(button_Cancel);
		
	    add(jpanel1, BorderLayout.NORTH);
		add(jpanel2, BorderLayout.SOUTH);	

	}
	
	/*用户登录*/
	public void button_Enter_Customer_Click() {
		this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		Enter_Customer frame_Enter_Customer = new Enter_Customer();
		frame_Enter_Customer.setVisible(true);
		frame_Enter_Customer.setBounds(220, 160, 900, 150);
		frame_Enter_Customer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/*管理员登录*/
    public void button_Enter_Manager_Click() {
    	this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		Enter_Manager frame_Enter_Manager = new Enter_Manager();
		frame_Enter_Manager.setVisible(true);
		frame_Enter_Manager.setBounds(220, 160, 900, 150);
		frame_Enter_Manager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
    /*退出*/
	public void button_Cancel_Click() {
		this.dispose();
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == button_Enter_Customer) {
			button_Enter_Customer_Click();
		} else if (e.getSource() == button_Enter_Manager) {
			button_Enter_Manager_Click();
		} else if (e.getSource() == button_Cancel) {
			button_Cancel_Click();
		}
	}

}
