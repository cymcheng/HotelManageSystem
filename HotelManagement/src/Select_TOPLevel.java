import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.*;

public class Select_TOPLevel extends JFrame implements ActionListener {

	JPanel jpanel1,jpanel2;
	
	JButton button_SumCharge;
	JButton button_Cancel;

	public Select_TOPLevel() {

		jpanel1 = new JPanel();		 
	    jpanel2 = new JPanel();
	    
	    jpanel1.setBorder(new TitledBorder("请选择要使用的功能："));
	    
	    button_SumCharge = new JButton("统计总收入");
	    button_SumCharge.addActionListener(this);
        button_Cancel = new JButton("退出");
        button_Cancel.addActionListener(this);
        
		jpanel1.add(button_SumCharge);
		jpanel2.add(button_Cancel);
		
	    add(jpanel1, BorderLayout.NORTH);
		add(jpanel2, BorderLayout.SOUTH);		
		 
		try {
			Class.forName("com.sqlserver.jdbc.Driver");
		} catch (ClassNotFoundException ee) {
			System.out.println("test" + ee);
		}
		
	}
	
	public void button_Cancel_Click() {
		this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		Enter frame_Enter = new Enter();
		frame_Enter.setVisible(true);
		frame_Enter.setBounds(220, 160, 300, 150);
		frame_Enter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
    public void button_SumCharge_Click() {
    	this.dispose();
		JFrame.setDefaultLookAndFeelDecorated(true);
		DataAnalysis frame_DataAnalysis = new DataAnalysis();
		frame_DataAnalysis.setVisible(true);
		frame_DataAnalysis.setBounds(220, 160, 300, 150);
		frame_DataAnalysis.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent e) {		
		if(e.getSource() == button_Cancel) {
			button_Cancel_Click();
		} else if (e.getSource() == button_SumCharge) {
			button_SumCharge_Click();
		}
	}
	

}
