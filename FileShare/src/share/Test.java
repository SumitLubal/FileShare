package share;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import javax.swing.*;

import loginframework.ActivityLogger;
import loginframework.DB;
import loginframework.UserAndPass;

import RMI.HTTPClientEcho;

public class Test extends JFrame implements Runnable, ActionListener {
	public static boolean isLoginDone = false;
	Server server;
	Client client;
	FileSender fileSender;
	String lastIP = "";
	private List<File> droppedFilesParent = null;
	JButton setDirectory, banIP, help;
	public static JButton login, changePasswordButton, browseCluster,browseFTP;
	Label textPad;
	Label saveInfo;
	JPanel buttonPanel;
	String blockIp;
	CopyDialog copy;
	public static JFrame loginFrame;
	static String selectedItem = "";
	private CustomChooserFrame chooser;
	private UserAndPass loginmodule;
	public static boolean sendToCluster;

	public static void main(String[] args) {
		new Test();
	}

	public Test() {
		setFrameParameter();
		setBounds(WindowProperty.getWidth() - 500,
				WindowProperty.getHeight() - 160, 500, 100);
		copy = new CopyDialog();
		copy.setBounds(WindowProperty.getWidth() - 200,
				WindowProperty.getHeight() - 200, 200, 100);
		client = new Client();
		client.copy = this.copy;
		fileSender = new FileSender();
		fileSender.copy = this.copy;
		chooser = new CustomChooserFrame();
		chooser.setVisible(false);
		new Thread(this).start();
		setVisible(true);
		this.setDropTarget(new DropTarget() {
			public void drop(DropTargetDropEvent evt) {

				evt.acceptDrop(DnDConstants.ACTION_COPY);
				List<File> droppedFiles = null;
				try {
					droppedFiles = (List<File>) evt.getTransferable()
							.getTransferData(DataFlavor.javaFileListFlavor);
					chooser.setVisible(true);
					droppedFilesParent = droppedFiles;
					saveInfo.setText("Saving To" + server.dirPath);
					System.out.println("some files dropped");
				} catch (UnsupportedFlavorException | IOException e) {
				}

			}
		});
		try {
			server = new Server();
			server.saveInfo = this.saveInfo;
			if (server.dirPath == null) {
				saveInfo.setText("Saving To" + fileSender.baseDirectory);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),
					"Network Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	private void setFrameParameter() {
		// setUndecorated(true);
		try {
			ImageIcon img = new ImageIcon(
					Test.class.getResource("/org/icon.png"));
			this.setIconImage(img.getImage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("File Share Utility 1.0");
		setSize(200, 100);
		setResizable(false);
		setDirectory = new JButton("SaveTo");
		help = new JButton("Help");
		banIP = new JButton("Ban IP");
		login = new JButton("login");
		changePasswordButton = new JButton("Pass");
		changePasswordButton.setEnabled(false);
		browseCluster = new JButton("Browse");
		browseCluster.setEnabled(false);
		browseFTP = new JButton("FTP");
		textPad = new Label();
		saveInfo = new Label();
		textPad.setText("Drop SOME FILES ON ME");
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3));
		buttonPanel.add(setDirectory);
		buttonPanel.add(banIP);
		buttonPanel.add(help);
		buttonPanel.add(login);
		buttonPanel.add(changePasswordButton);
		buttonPanel.add(browseCluster);
		buttonPanel.add(browseFTP);
		add(textPad);
		add(saveInfo);
		add(buttonPanel);
		setLayout(new GridLayout(3, 1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setDirectory.addActionListener(this);
		banIP.addActionListener(this);
		help.addActionListener(this);
		login.addActionListener(this);
		changePasswordButton.addActionListener(this);
		browseCluster.addActionListener(this);
		browseFTP.addActionListener(this);
	}

	@Override
	public void run() {
		while (true) {
			if (selectedItem != null) {
				if (droppedFilesParent != null
						&& selectedItem.contains("Local Lan Share")) {
					// share in cloud
					setDirectory.setEnabled(false);
					banIP.setEnabled(false);
					client.hostDomain = getDestinationIpByDailogue();
					fileSender.sendDroppedFiles(droppedFilesParent, client);
					droppedFilesParent = null;
					setDirectory.setEnabled(true);
					banIP.setEnabled(true);
				} else if (droppedFilesParent != null
						&& selectedItem.contains("FTP")) {
					// share in FTPsetDirectory.setEnabled(false);
					banIP.setEnabled(false);
					String userName = JOptionPane.showInputDialog(this,
							"What is user's name?");
					JPasswordField pf = new JPasswordField();
					int okCxl = JOptionPane.showConfirmDialog(null, pf,
							"Enter Password", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);
					String password = null;
					if (okCxl == JOptionPane.OK_OPTION) {
						password = new String(pf.getPassword());
						// System.err.println("You entered: " + password);
					}
					// FTPuploader.uploadFiles(droppedFilesParent,CustomChooserFrame.ipField.getText());
					try {
						Sample.initiate(CustomChooserFrame.ipField.getText(),
								userName, password);
						for (File file : droppedFilesParent) {
							// encrypt in <filename.ext>.enc
							Sample.uploadFile(file.getAbsolutePath(),
									file.getName(), "/");
							// delet that tmp file
						}
						Sample.disconnect();
						System.out.println("Done");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					droppedFilesParent = null;
					setDirectory.setEnabled(true);
					banIP.setEnabled(true);
					System.out.println("FTP");
				} else if (droppedFilesParent != null
						&& selectedItem.contains("Cluster")) {
					if (isLoginDone) {
						// cloud sharing
						System.out.println("Cluster");
						// get available IP address from main server
						String addressOfNode;
						System.out.println("Communicating with: "
								+ (addressOfNode = HTTPClientEcho
										.getNodeIPAdress()));
						if (addressOfNode != null) {
							sendToCluster = true;
							setDirectory.setEnabled(false);
							banIP.setEnabled(false);
							client.hostDomain = addressOfNode;// getDestinationIpByDailogue();
							fileSender.username = loginframework.Frame.userName;
							fileSender.sendDroppedFiles(droppedFilesParent,
									client);
							droppedFilesParent = null;
							setDirectory.setEnabled(true);
							banIP.setEnabled(true);
							sendToCluster = false;

						} else {
							JOptionPane
									.showMessageDialog(null,
											"failed to connect to cluster please try after some time");
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Login required to send files on Cluster");
					}
				} else if (droppedFilesParent != null
						&& selectedItem.contains("Database")) {
					// database sharing
					System.out.println("Database");
				}
				selectedItem = null;
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(),
						"Network Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}

	private String getDestinationIpByDailogue() {
		while (true) {
			String s = (String) JOptionPane.showInputDialog(this,
					"Enter IP address of destination", "Destination IP",
					JOptionPane.PLAIN_MESSAGE, null, null, lastIP);

			System.out.println(s);
			if (s == null) {
				return "10";
			}
			try {
				testAddress(s);
			} catch (Exception e) {

			}
			lastIP = s;
			return s;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton pressedButton = (JButton) e.getSource();
		if (pressedButton.equals(setDirectory)) {
			setServerDirectory();
		} else if (pressedButton.equals(banIP)) {
			String s = (String) JOptionPane.showInputDialog(this,
					"Enter IP address To be blocked", "Block IP",
					JOptionPane.PLAIN_MESSAGE, null, null, lastIP);
			blockIp += s;
		} else if (pressedButton.equals(help)) {
			JOptionPane.showMessageDialog(this,
					"Please refer ReadMe file.. Credits - Sagar,Ankit,Sumit");
		} else if (pressedButton.equals(login)) {
			// start login module
			if (login.getText().contains("login")) {
				if (loginmodule == null || !loginmodule.isShowing()) {
					loginmodule = new UserAndPass();
				} else {
					loginmodule.setFocusable(true);
					loginmodule.toFront();
					loginmodule.repaint();
				}
			} else {
				loginFrame.dispose();
				changePasswordButton.setEnabled(false);
				browseCluster.setEnabled(false);
				DB.createOrReplaceTable(loginframework.Frame.userName);
				ActivityLogger.writeToDB(loginframework.Frame.userName);
				ActivityLogger.write(loginframework.Frame.userName);
				login.setText("login");
				isLoginDone = false;
			}
		} else if (pressedButton.equals(changePasswordButton)) {
			JPasswordField pf = new JPasswordField();
			int okCxl = JOptionPane.showConfirmDialog(null, pf,
					"Enter Password", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			String password = null;
			if (okCxl == JOptionPane.OK_OPTION) {
				password = new String(pf.getPassword());
				System.err.println("You entered: " + password);
			}
			String newPass = password;
			DB.fireQuerry("UPDATE `test`.`auth` SET `pass` = '" + newPass
					+ "' WHERE `auth`.`user` = '"
					+ loginframework.Frame.userName + "';");
			int prevcount = DB.getCountOfMonth(loginframework.Frame.userName);
			DB.fireQuerry("UPDATE `test`.`passwordchange` SET `count` = '"
					+ (prevcount + 1) + "' WHERE `passwordchange`.`name` = '"
					+ loginframework.Frame.userName + "'");

		} else if (pressedButton.equals(browseCluster)) {
			String add = HTTPClientEcho.getNodeIPAdress();
			try {
				new FileBrowserClient(add);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}else if(pressedButton.equals(browseFTP)){
			// Run a java app in a separate system process
			Process proc;
			try {
				proc = Runtime.getRuntime().exec("java -jar FTPGo.jar");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void setServerDirectory() {
		JFileChooser chooser = new JFileChooser();
		chooser.setLocation(this.getLocation());
		// chooser.setCurrentDirectory(new java.io.File(""));
		chooser.setDialogTitle("Set Save Directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			if (chooser.getSelectedFile().isDirectory())
				server.dirPath = chooser.getSelectedFile().getAbsolutePath();
			else if (chooser.getSelectedFile().isFile()) {
				server.dirPath = chooser.getCurrentDirectory().getParentFile()
						.getAbsolutePath();
			}
		}

		// server.dirPath = chooser.getCurrentDirectory().getAbsolutePath();
		System.out.println(server.dirPath);
		saveInfo.setText(server.dirPath);
		textPad.setText("Drop Files To Send");
	}

	public static InetAddress testAddress(String str)
			throws UnknownHostException {
		InetAddress add = InetAddress.getByName(str);

		// Check if IP address was simply returned, instead of host.
		if (add.getCanonicalHostName().equals(add.getHostAddress())) {
			throw new UnknownHostException(str + "is not a known host.");
		}
		return add;
	}

}