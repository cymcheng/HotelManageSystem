import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class PaymentInterface extends JFrame implements ActionListener {
	
	JPanel jpanel;
	JLabel label_PaymentReminder;
	JTextField txt_PaymentReminder;
	
	JButton button_Cancel;

	public PaymentInterface(int money) {
		
		String moneyStr = Integer.toString(money);
		
		jpanel = new JPanel();	
		
		label_PaymentReminder = new JLabel("�ͻ���֧�����Ϊ��");
		txt_PaymentReminder = new JTextField(8);
		txt_PaymentReminder.setText(moneyStr);
		txt_PaymentReminder.setEditable(false);
		
		button_Cancel = new JButton("�ͻ������֧����ȷ���˷�");
		button_Cancel.addActionListener(this);

		jpanel.add(label_PaymentReminder);
		jpanel.add(txt_PaymentReminder);
		jpanel.add(button_Cancel);
	    
		add(jpanel, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent e) {		
		if (e.getSource() == button_Cancel) {
			this.dispose();
			JOptionPane.showMessageDialog(this, "�˷��ɹ�", "�ɹ�", JOptionPane.PLAIN_MESSAGE);
		} 
	}

}
