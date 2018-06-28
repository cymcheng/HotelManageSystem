import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.*;

/*功能选择界面-普通管理员*/
public class Select extends JFrame implements ActionListener {

	JPanel jpanel1,jpanel2;
	
	JButton button_AddNewCheckIn,button_AddNewCheckOut,button_RoomInformationQuery,button_CustomerInformationQuery,button_CustomerExpenseQuery,button_CustomerOvertimeQuery;
	JButton button_Cancel;

	public Select() {

	    jpanel1 = new JPanel();		 
	    jpanel2 = new JPanel();
	    
	    jpanel1.setBorder(new TitledBorder("请选择要使用的功能："));
	   
	    button_AddNewCheckIn = new JButton("客人入住");
	    button_AddNewCheckIn.addActionListener(this);
	    button_AddNewCheckOut = new JButton("客人退房");
	    button_AddNewCheckOut.addActionListener(this);
	    button_RoomInformationQuery = new JButton("查询客房信息");
	    button_RoomInformationQuery.addActionListener(this);
	    button_CustomerInformationQuery = new JButton("查询客人信息");
            button_CustomerInformationQuery.addActionListener(this);
            button_CustomerExpenseQuery = new JButton("查询客人费用");
            button_CustomerExpenseQuery.addActionListener(this);
            button_CustomerOvertimeQuery = new JButton("查询超时客人");
            button_CustomerOvertimeQuery.addActionListener(this);
            button_Cancel = new JButton("退出");
            button_Cancel.addActionListener(this);

		jpanel1.add(button_AddNewCheckIn);
		jpanel1.add(button_AddNewCheckOut);
		jpanel1.add(button_RoomInformationQuery);
		jpanel1.add(button_CustomerInformationQuery);
		jpanel1.add(button_CustomerExpenseQuery);	
		jpanel1.add(button_CustomerOvertimeQuery);	
		jpanel2.add(button_Cancel);
		
	    add(jpanel1, BorderLayout.NORTH);
		add(jpanel2, BorderLayout.SOUTH);		
		 
		try {
			Class.forName("com.sqlserver.jdbc.Driver");
		} catch (ClassNotFoundException ee) {
			System.out.println("test" + ee);
		}
		
	}
	
	/*退出*/
	public void button_Cancel_Click() {
		this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		Enter_Manager frame_Enter_Manager = new Enter_Manager();
		frame_Enter_Manager.setVisible(true);
		frame_Enter_Manager.setBounds(220, 160, 900, 200);
		frame_Enter_Manager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/*查询客房信息*/
	public void button_RoomInformationQuery_Click() {
    	this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		RoomInformationQuery frame_RoomInformationQuery = new RoomInformationQuery();
		frame_RoomInformationQuery.setVisible(true);
		frame_RoomInformationQuery.setBounds(220, 160, 600, 300);
		frame_RoomInformationQuery.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
    /*查询用户信息*/
    public void button_CustomerInformationQuery_Click() {
    	this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		CustomerInformationQuery frame_CustomerInformationQuery = new CustomerInformationQuery();
		frame_CustomerInformationQuery.setVisible(true);
		frame_CustomerInformationQuery.setBounds(220, 160, 1500, 200);
		frame_CustomerInformationQuery.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
    /*查询客人费用*/
    public void button_CustomerExpenseQuery_Click() {
    	this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		CustomerExpenseQuery frame_CustomerExpenseQuery = new CustomerExpenseQuery();
		frame_CustomerExpenseQuery.setVisible(true);
		frame_CustomerExpenseQuery.setBounds(220, 160, 300, 200);
		frame_CustomerExpenseQuery.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
    /*查询到期/超时客人*/
    public void button_CustomerOvertime_Click() {
    	this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		CustomerOvertimeQuery frame_CustomerOvertimeQuery = new CustomerOvertimeQuery();
		frame_CustomerOvertimeQuery.setVisible(true);
		frame_CustomerOvertimeQuery.setBounds(220, 160, 300, 150);
		frame_CustomerOvertimeQuery.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
    /*添加入住信息*/
    public void button_AddNewCheckIn_Click() {
    	this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		AddNewCheckIn frame_AddNewCheckIn = new AddNewCheckIn();
		frame_AddNewCheckIn.setVisible(true);
		frame_AddNewCheckIn.setBounds(220, 160, 1600, 400);
		frame_AddNewCheckIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
    /*添加退房信息*/
    public void button_AddNewCheckOut_Click() {
    	this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		AddNewCheckOut frame_AddNewCheckOut = new AddNewCheckOut();
		frame_AddNewCheckOut.setVisible(true);
		frame_AddNewCheckOut.setBounds(220, 160, 300, 150);
		frame_AddNewCheckOut.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent e) {		
		if(e.getSource() == button_Cancel) {
			button_Cancel_Click();
		} else if (e.getSource() == button_RoomInformationQuery) {
			button_RoomInformationQuery_Click();	
		} else if (e.getSource() == button_CustomerInformationQuery) {
			button_CustomerInformationQuery_Click();	
		} else if (e.getSource() == button_CustomerExpenseQuery) {
			button_CustomerExpenseQuery_Click();	
		} else if (e.getSource() == button_CustomerOvertimeQuery) {
			button_CustomerOvertime_Click();	
		} else if (e.getSource() == button_AddNewCheckIn) {
			button_AddNewCheckIn_Click();
		} else if (e.getSource() == button_AddNewCheckOut) {
			button_AddNewCheckOut_Click();
		}
	}

}
