package user;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import messages.UserQueryMinionBasicInfo;

public class GUI  extends JFrame implements ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
    private JScrollPane scrollpane;
    private static final String[] columnames= {"ID","Hostname","Tag","Public IP","IP","CPU","RAM","Online","Select minion"};
    DefaultTableModel tableModel = new DefaultTableModel(columnames, 0);
    private Button ask_minion_list = new Button("Ask for minion list");
	private Button ask_information=new Button("Ask for information");
public GUI() 
{
	
	/***********************************/
    ask_minion_list.addActionListener(this);
    ask_information.addActionListener(this);
    table=new JTable(tableModel);
    table.setPreferredScrollableViewportSize(new Dimension(700, 300));
    table.setFillsViewportHeight(true);
    TableColumn tc=table.getColumnModel().getColumn(8);
    tc.setCellEditor(table.getDefaultEditor(Boolean.class));
    tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    scrollpane =new JScrollPane(table);
    this.add(scrollpane,BorderLayout.WEST);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(750,750);
    /*******************/
    JPanel button_panel=new JPanel();
    button_panel.add(ask_minion_list);
    ask_information.setSize(20, 200);
    this.add(button_panel,BorderLayout.CENTER);
    this.setVisible(true);
    /********************/
    JPanel button_panel1=new JPanel();
    button_panel1.add(ask_information);
    this.add(button_panel1,BorderLayout.EAST);
    this.pack();
    this.setResizable(false);
    this.setVisible(true);
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	ArrayList<Integer>array;
	User user=new User();
	int counter=0;
	if (e.getSource()==ask_minion_list) {
		deleteTable();
				array=user.getMinionList().getMinionList();
		for (Integer integer : array) {
			Object[] row= {integer,"","","","","","","",false};
			tableModel.addRow(row);
		}
	array.clear();
	}
	else if(e.getSource()==ask_information) {
		int num_rows=table.getRowCount();
		ArrayList<UserQueryMinionBasicInfo> query2=new ArrayList<>();
		Boolean checked;
		array=new ArrayList<>();
		ArrayList<Integer> array2=new ArrayList<>();
		for (int i = 0; i < num_rows; i++) {//Checking if the check box is selected in each column
			checked=Boolean.valueOf(tableModel.getValueAt(i, 8).toString());
			if(checked) {
				array.add(Integer.parseInt(String.valueOf(table.getValueAt(i,0))));
				array2.add(i);
			}
		}
		for (int i = 0; i < array.size(); i++) {
			query2.add(user.getBasicInfo(array.get(i)));//Here I receive the information of each minion and I storage it into the array
		}
		for (UserQueryMinionBasicInfo userQueryMinionBasicInfo : query2) {
			
			Object[] row= {userQueryMinionBasicInfo.getMinionId(),userQueryMinionBasicInfo.getHostname(),userQueryMinionBasicInfo.getTag(),userQueryMinionBasicInfo.getPublicIP(),userQueryMinionBasicInfo.getIP(),userQueryMinionBasicInfo.getCPULoad(),userQueryMinionBasicInfo.getRAM(),userQueryMinionBasicInfo.isOnline(),false};
				tableModel.removeRow(array2.get(counter));
				tableModel.insertRow(array2.get(counter), row);
				counter++;
		}	
	 }
	else {
		
	}	
}

public void deleteTable() {
	int num_rows=table.getRowCount();
	System.out.println(num_rows);
	for (int i = num_rows; i > 0; i--) {
		tableModel.removeRow(i-1);
		System.out.println(table.getRowCount());
	}
}
} 
