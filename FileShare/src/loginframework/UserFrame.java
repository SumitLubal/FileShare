package loginframework;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class UserFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton logout;
	private JPanel contentPane;
	static String userName;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserFrame frame = new UserFrame("user");
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param user
	 */
	public UserFrame(String user) {
		userName = user;
		viewnFeel();
		logout.addActionListener(this);
		btnNewButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(logout)) {
			new UserAndPass();
			dispose();
			DB.createOrReplaceTable(userName);
			ActivityLogger.writeToDB(userName);
			ActivityLogger.write(userName);
		} else if (arg0.getSource().equals(btnNewButton)) {
			String newPass = JOptionPane.showInputDialog(this,
					"Enter new password?");
			DB.fireQuerry("UPDATE `test`.`auth` SET `pass` = '" + newPass
					+ "' WHERE `auth`.`user` = '" + userName + "';");
			int prevcount = DB.getCountOfMonth(userName);
			DB.fireQuerry("UPDATE `test`.`passwordchange` SET `count` = '"+(prevcount+1)+"' WHERE `passwordchange`.`name` = '"+userName+"'");
		}
	}

	private void viewnFeel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBounds(WindowProperty.getWidth() - 120,
				WindowProperty.getHeight() - 75, 137, 69);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		logout = new JButton("**Logout**");
		contentPane.add(logout);

		btnNewButton = new JButton("change Password");
		contentPane.add(btnNewButton);

	}

}
