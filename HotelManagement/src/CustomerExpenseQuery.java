import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class CustomerExpenseQuery extends JFrame implements ActionListener {
	
	JPanel jp1,jp2;
	JButton button_Query,button_Cancel;
	JTable jtable;
	
	Object cols[]= {"�û�����","��ǰ��ס����","Ѻ����","Ԥ������","��ǰס�޷�","��ǰ���"};
	Object rows[][];
	
	JLabel label_CustomerName;
	JTextField txt_CustomerName;

	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=HotelManagement;";
	String user = "sa";
	String pwd = "cym331s09";
	
	Connection con = null;
	Statement sql = null;
	ResultSet resultSQL = null;
	
	ResultSet result_isVIP = null;
	ResultSet result_isRegular = null;
	ResultSet result_Expense = null;
	ResultSet result_RealityDateOfCheckIn = null;
 
	public CustomerExpenseQuery() {
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		
		jp1.setBorder(new TitledBorder("�û�ס����Ϣ��ѯ"));
		
		label_CustomerName = new JLabel("�ͻ�������");
		txt_CustomerName = new JTextField(10);
		
		button_Query = new JButton("��ѯ");
		button_Query.addActionListener(this);
		button_Cancel = new JButton("ȡ��");
		button_Cancel.addActionListener(this);		
		
		jp1.add(label_CustomerName);
		jp1.add(txt_CustomerName);    
		jp2.add(button_Query);
		jp2.add(button_Cancel);
		
		add(jp1, BorderLayout.NORTH);
		add(jp2, BorderLayout.SOUTH);
		
		try {
				Class.forName("com.sqlserver.jdbc.Driver");
			} catch (ClassNotFoundException ee) {
				System.out.println("test" + ee);
			}		
	}
	
	public void button_Query_Click() {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);;			
		
			String customerName = txt_CustomerName.getText().trim();
			//System.out.println(customerName);
			/*��ȡ��ǰ����*/
			String DateNow = SystemTime.SystemTimeNow();
			//System.out.println(DateNow);
			/*��ѯʵ����ס����*/
			String SQL_SelectRealityDateOfCheckIn;
    		//System.out.println(customerName);
			SQL_SelectRealityDateOfCheckIn = "SELECT RealityDateOfCheckIn FROM CheckIn,Customer WHERE CheckIn.CSerialNumber=Customer.CSerialNumber AND Name='" + customerName + "'";
    		result_RealityDateOfCheckIn = sql.executeQuery(SQL_SelectRealityDateOfCheckIn);
    		//System.out.println(SQL_SelectRealityDateOfCheckIn);
    		String RealityDateOfCheckIn = "";
    		result_RealityDateOfCheckIn.beforeFirst();
    		while (result_RealityDateOfCheckIn.next()) {
    			RealityDateOfCheckIn = result_RealityDateOfCheckIn.getString(1).trim();
        		System.out.println(RealityDateOfCheckIn);
    		}
    		/*���㵱ǰ��ס����*/
    		int currentNumberOfCheckIn = DateDiff.Datediff(RealityDateOfCheckIn, DateNow);
			/*��ѯ�Ƿ���VIP*/
    		String SQL_SelectisVIP;
    		//System.out.println(customerName);
    		SQL_SelectisVIP = "SELECT VIP FROM Customer WHERE Name='" + customerName + "'";
    		result_isVIP = sql.executeQuery(SQL_SelectisVIP);
    		//System.out.println(SQL_SelectisVIP);
    		String isVIP = "";
    		result_isVIP.beforeFirst();
    		while (result_isVIP.next()) {
    			isVIP = result_isVIP.getString(1).trim();
        		System.out.println(isVIP);
    		}
    		/*��ѯ�Ƿ����Ͽͻ�*/
    		String SQL_SelectisRegular;
    		//System.out.println(customerName);
    		SQL_SelectisRegular = "SELECT Regular FROM Customer WHERE Name='" + customerName + "'";
    		result_isRegular = sql.executeQuery(SQL_SelectisRegular);
    		//System.out.println(SQL_SelectisRegular);
    		String isRegular = "";
    		result_isRegular.beforeFirst();
    		while (result_isRegular.next()) {
    			isRegular = result_isRegular.getString(1).trim();
    			//System.out.println(isRegular);
    		}
    		/*��ѯ���ۣ�VIP�Żݣ����û��Ż�*/
    		int tempUnitPrice = 0;
    		double tempVIPDiscount = 0;
    		double tempRegularDiscount = 0;
    		String SQL_SelectExpense = "";
    		SQL_SelectExpense = "SELECT UnitPrice,VIPDiscount,RegularDiscount FROM RoomType,CheckIn,Customer WHERE CheckIn.CSerialNumber=Customer.CSerialNumber AND RoomType.RSerialNumber=CheckIn.RSerialNumber AND Name='" + customerName + "'";
    		result_Expense = sql.executeQuery(SQL_SelectExpense);
    		//System.out.println(SQL_SelectExpense);
    		result_Expense.beforeFirst();
    		while (result_Expense.next()) {
    			tempUnitPrice = result_Expense.getInt(1);
        		tempVIPDiscount = result_Expense.getDouble(2);
        		tempRegularDiscount = result_Expense.getDouble(3);
    		    //System.out.println("UnitPrice        " + tempUnitPrice);
    		    //System.out.println("VIPDiscount      " + tempVIPDiscount);
    		    //System.out.println("RegularDiscount  " + tempRegularDiscount);
    		}
    		/*��ǰ����*/
            int currentHotelCharge = 0;
    		if (isVIP.equals("NO") && isRegular.equals("NO")) {
    			currentHotelCharge = (tempUnitPrice * currentNumberOfCheckIn);
    		} else if (isVIP.equals("YES") && isRegular.equals("NO")) {
    			currentHotelCharge = (int)(tempUnitPrice * currentNumberOfCheckIn * tempVIPDiscount);
    		} else if (isVIP.equals("NO") && isRegular.equals("YES")) {
    			currentHotelCharge = (int)(tempUnitPrice * currentNumberOfCheckIn * tempRegularDiscount);
    		} else if (isVIP.equals("YES") && isRegular.equals("YES")) {
    			currentHotelCharge = (int)(tempUnitPrice * currentNumberOfCheckIn * tempVIPDiscount * tempRegularDiscount);
    		}
			/*��ѯѺ����Ԥ������*/
			String SQL_SelectExpenseFromCheckIn;
			SQL_SelectExpenseFromCheckIn = "SELECT Name,CashPledgeAmount,PrepayHotelCharge FROM CheckIn,Customer WHERE CheckIn.CSerialNumber=Customer.CSerialNumber AND Name='" + customerName + "'";;
			//System.out.println(SQL_SelectExpenseFromCheckIn);
			resultSQL = sql.executeQuery(SQL_SelectExpenseFromCheckIn);
			resultSQL.last(); 
			int currentRow = resultSQL.getRow();
			//System.out.println(currentRow);
			if (currentRow == 0) {
				JOptionPane.showMessageDialog(this, "û�ж�Ӧ�Ŀͻ���Ϣ", "��ʾ", JOptionPane.WARNING_MESSAGE);
			} else {
                 int rowCount = currentRow; 
                 //System.out.println(rowCount + " the rows"); 
                 rows = new Object[rowCount][cols.length];
		         int count = 0; 
		         resultSQL.beforeFirst();
		         while(resultSQL.next()) {
		        	 //System.out.println(count + "," + resultSQL.getString(1));
		             rows[count][0] = resultSQL.getString(1);/*�ͻ�����*/
		             rows[count][1] = currentNumberOfCheckIn;/*��ǰ��ס����*/
		             rows[count][2] = resultSQL.getInt(2);/*Ѻ��*/
		             rows[count][3] = resultSQL.getInt(3);/*Ԥ������*/
		             rows[count][4] = currentHotelCharge;/*��ǰ����*/
		             rows[count][5] = resultSQL.getInt(2) + resultSQL.getInt(3) - currentHotelCharge;/*��ǰ���*/
		             count++;
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
