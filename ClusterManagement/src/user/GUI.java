package user;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import messages.CmdQuery;
import messages.UserQueryMinionBasicInfo;

public class GUI  extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static boolean DOUBLE_QUERY=false;
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane scrollpane;
	private static final String[] columnames= {"ID","Hostname","Tag","Public IP","IP","CPU","RAM","Online","Select minion"};
	private static final String[] processColumns= {"Image name","PID","Session Name","Session#","Mem Usage"};
	DefaultTableModel tableModel = new DefaultTableModel(columnames, 0);
	DefaultTableModel processListTable = new DefaultTableModel(processColumns, 0);
	private JButton ask_minion_list = new JButton("Ask for minion list");
	private JButton ask_information=new JButton("Ask for information");
	private JButton listing_and_ask_for_information=new JButton("Ask for minion list and fill the information");
	private JButton listing_processes=new JButton("Ask for process list");
	ArrayList<Integer>array;
	User user=new User();
	
	public GUI() 
	{

		Container pane=this.getContentPane();
		pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ask_information.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(ask_information);
        listing_and_ask_for_information.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(listing_and_ask_for_information);
        ask_minion_list.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(ask_minion_list);
		/***********************************/
		ask_minion_list.addActionListener(this);
		ask_information.addActionListener(this);
		listing_and_ask_for_information.addActionListener(this);
		listing_processes.addActionListener(this);
		table=new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(800, 300));
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
		/*******************/
		JPanel button_panel2=new JPanel();
		button_panel2.add(listing_and_ask_for_information);
		this.add(button_panel2,BorderLayout.CENTER);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		/*******************/
		JPanel button_panel3=new JPanel();
		button_panel3.add(listing_processes);
		this.add(button_panel3,BorderLayout.CENTER);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==ask_minion_list) {
			askMinonList();
		}
		else if(e.getSource()==ask_information) {
			askForInformation();
		}
		else if(e.getSource()==listing_and_ask_for_information) {
			askMinonList();
			DOUBLE_QUERY=true;
			askForInformation();
		}
		else if (e.getSource()==listing_processes) {
			askForProcessList();
		}
		else {

		}
	}

	public void askMinonList() {
		deleteTable();
		array=user.getMinionList().getMinionList();
		for (Integer integer : array) {
			Object[] row= {integer,"","","","","","","",false};
			tableModel.addRow(row);
		}
		array.clear();
	}


	public void askForProcessList() {		
		int num_rows=table.getRowCount();
		
		ArrayList<CmdQuery> query2=new ArrayList<>();
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
			query2.add(user.getProcessList(array.get(i)));//Here I receive the information of each minion and I storage it into the array
		}
		for (int i = 0; i < query2.size(); i++) {
			JFrame jframe=new JFrame("Process List of minion "+query2.get(i).getMinionId());
			jframe.setTitle("Process List of minion "+query2.get(i).getMinionId());
			jframe.setVisible(true);
			jframe.setSize(750,750);
			JLabel label=new JLabel();
			label.setText(query2.get(i).toString());
			jframe.add(label);
		}
		

		
	}
	public void askForInformation() {
		int counter=0;
		int num_rows=table.getRowCount();
		ArrayList<UserQueryMinionBasicInfo> query2=new ArrayList<>();
		Boolean checked;
		array=new ArrayList<>();
		ArrayList<Integer> array2=new ArrayList<>();
		for (int i = 0; i < num_rows; i++) {//Checking if the check box is selected in each column
			if(!DOUBLE_QUERY) {
				checked=Boolean.valueOf(tableModel.getValueAt(i, 8).toString());
				if(checked) {
					array.add(Integer.parseInt(String.valueOf(table.getValueAt(i,0))));
					array2.add(i);
				}
			}
			else {
				array.add(Integer.parseInt(String.valueOf(table.getValueAt(i,0))));
				array2.add(i);
			}
		}
		DOUBLE_QUERY=false;
		for (int i = 0; i < array.size(); i++) {
			query2.add(user.getBasicInfo(array.get(i)));//Here I receive the information of each minion and I storage it into the array
		}
		for (UserQueryMinionBasicInfo userQueryMinionBasicInfo : query2) {

			Object[] row= {userQueryMinionBasicInfo.getMinionId(),userQueryMinionBasicInfo.getHostname(),userQueryMinionBasicInfo.getTag(),userQueryMinionBasicInfo.getPublicIP(),userQueryMinionBasicInfo.getIP(),userQueryMinionBasicInfo.getCPULoad(),userQueryMinionBasicInfo.getRAM(),userQueryMinionBasicInfo.isOnline(),true};
			tableModel.removeRow(array2.get(counter));
			tableModel.insertRow(array2.get(counter), row);
			counter++;
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
