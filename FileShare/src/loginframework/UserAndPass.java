package loginframework;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UserAndPass extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton login, cancle;
	private JTextField username;
	private JPasswordField password;
	private JLabel userlabel, passlabel;
	private String authenticationType = null;
	Pattern pattern;
	private String filePath;
	private double factorX = getWidth() / 1366, factorY = getHeight() / 768;

	public static void main(String arg[]) {
		new UserAndPass();
	}

	public UserAndPass() {

		setUndecorated(true);

		WindowProperty.setFullScreen(this);
		propertyAssignment();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void propertyAssignment() {
		System.out.println("Size " + getWidth());
		factorX = (float) getWidth() / 1366;
		factorY = (float) getHeight() / 768;
		// System.out.println(WindowProperty.getHeight()+" "+factorX+" "+factorY);
		username = new JTextField();
		password = new JPasswordField();
		passlabel = new JLabel("Password");
		userlabel = new JLabel("Username");
		login = new JButton("Login");
		cancle = new JButton("Cancel");
		username.setSize(80, 40);
		password.setSize(80, 40);
		// setLayout(new GridLayout(3, 2));
		setLayout(null);

		userlabel.setBounds((int) (500 * factorX), (int) (100 * factorY),
				(int) (100 * factorX), (int) (50 * factorY));
		username.setBounds((int) (600 * factorX), (int) (100 * factorY),
				(int) (100 * factorX), (int) (30 * factorY));
		passlabel.setBounds((int) (500 * factorX), (int) (200 * factorY),
				(int) (100 * factorY), (int) (50 * factorY));
		password.setBounds((int) (600 * factorX), (int) (200 * factorY),
				(int) (100 * factorX), (int) (30 * factorY));
		login.setBounds((int) (500 * factorX), (int) (250 * factorY),
				(int) (100 * factorX), (int) (30 * factorY));
		cancle.setBounds((int) (600 * factorX), (int) (250 * factorY),
				(int) (100 * factorX), (int) (30 * factorY));
		repaint();
		add(userlabel);
		add(username);
		add(passlabel);
		add(password);
		add(login);
		add(cancle);
		username.addActionListener(this);
		password.addActionListener(this);
		login.addActionListener(this);
		cancle.addActionListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == login || arg0.getSource() == username
				|| arg0.getSource() == password) {
			if (checkAndSet(username.getText(), password.getText())) {
				// Frame.main(null);
				System.out.println("Login successfull");
				login.setEnabled(false);
				cancle.setEnabled(false);
				JPanel imagePanel;
				if (!filePath.equals("null")) {
					imagePanel = new Frame(username.getText(), pattern,
							authenticationType, filePath, factorX, factorY,
							this);
				} else {
					imagePanel = new Frame(username.getText(), pattern, authenticationType, filePath, factorX, factorY, this,false);
				}
				userlabel.setVisible(false);
				username.setVisible(false);
				passlabel.setVisible(false);
				password.setVisible(false);
				login.setVisible(false);
				cancle.setVisible(false);
				repaint();
				imagePanel.setBounds(0, 0, getWidth(), getHeight());
				add(imagePanel);
				this.repaint();
			} else {
				password.setText("");
			}
		} else {
			username.setText("");
			password.setText("");
		}

	}

	private boolean checkAndSet(String userName, String password) {
		ResultSet result = DB.readData();
		System.out.println("In check");
		try {
			while (result.next()) {
				System.out.println(result.getString("pass"));
				if (result.getString("user").equals(userName)
						&& result.getString("pass").equals(password)) {
					pattern = new Pattern(result.getInt("pat1x"),
							result.getInt("pat2x"), result.getInt("pat3x"),
							result.getInt("pat1y"), result.getInt("pat2y"),
							result.getInt("pat3y"));
					authenticationType = result.getString("type");
					filePath = result.getString("filepath");
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
