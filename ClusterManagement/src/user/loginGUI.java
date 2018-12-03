package user;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.DBConnector;

public class loginGUI extends JFrame{


	private JPanel panel;             // To reference a panel
	private JLabel userLabel;      // To reference a label
	private JLabel passLabel;
    private JTextField user; // To reference a text field 
    private JButton loginButton;       // To reference a button
    private JPasswordField password;
    private JLabel output;
    private final int WINDOW_WIDTH = 310;  // Window width 
    private final int WINDOW_HEIGHT = 160; // Window height

	   /**
	      Constructor
	   */

	   public loginGUI()
	   {
	      // Set the window title.
	      setTitle("Login to the SuperServerManagement System");

	      // Set the size of the window.
	      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

	      // Specify what happens when the close button is clicked.
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	      // Build the panel and add it to the frame.
	      buildPanel();

	      // Add the panel to the frame's content pane.
	      add(panel);

	      // Display the window.
	      setVisible(true);
	   }

	   /**
	      The buildPanel method adds a label, text field, and
	      and a button to a panel.
	   */

	   private void buildPanel()
	   {
	      // Create a label to display instructions.
	      userLabel = new JLabel("Enter your username: ");
	      passLabel = new JLabel("Enter your password: ");
	      // Create a text field 10 characters wide.
	      user = new JTextField(10);
	      password = new JPasswordField(10);
	      loginButton = new JButton("Login");
	      
	      loginButton.addActionListener(new LoginHandler());
	      
	      // Create a JPanel object and let the panel
	      // field reference it.
	      panel = new JPanel();
	      
	      // Add the label, text field, and button
	      // components to the panel.
	      panel.add(userLabel);
	      panel.add(user);
	      panel.add(passLabel);
	      panel.add(password);
	      panel.add(loginButton);
	   }

	private class LoginHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String username, pass;
			username = user.getText();
			pass = String.valueOf(password.getPassword());
			
			System.out.println("Username: " + username + " and password: " + pass);
			
			DBConnector db = new DBConnector();
			try {
				Connection con = db.connect();
				String tableName = "ssm_users";
				PreparedStatement ps = con.prepareStatement("SELECT * FROM " + tableName + " WHERE username=(?) AND password=(?)");
				ps.setString(1, username);
				ps.setString(2, pass);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					System.out.println("Authentication success.");
					new GUI();
					setVisible(false);
				}
				else {
					System.out.println("Authentication failed.");
					output = new JLabel();
					output.setText("Authentication failed. Please try again.");
					panel.add(output);
				}
				
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			
		}
		
	}
	
}
