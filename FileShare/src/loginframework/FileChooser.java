package loginframework;
// SimpleFileChooser.java
// A simple file chooser to see what it takes to make one of these work.
//
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileList = null;
	public String getFileList(){
		return fileList;
	}
	public FileChooser() {
		super("File Chooser Test Frame");
		setSize(350, 200);

		fileList = null;
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		JButton openButton = new JButton("Open");
		JButton saveButton = new JButton("Save");
		JButton dirButton = new JButton("Pick Dir");
		final JLabel statusbar = new JLabel(
				"Output of your selection will go here");

		// Create a file chooser that opens up as an Open dialog
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "JPG & GIF Images", "jpg", "gif");
				    chooser.setFileFilter(filter);
				chooser.setMultiSelectionEnabled(true);
				int option = chooser.showOpenDialog(FileChooser.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					File[] sf = chooser.getSelectedFiles();
					String filelist = "nothing";
					if (sf.length > 0)
						filelist = sf[0].getName();
					for (int i = 1; i < sf.length; i++) {
						filelist += ", " + sf[i].getName();
					}
					statusbar.setText("You chose " + filelist);
					filelist = fileList;
				} else {
					statusbar.setText("You canceled.");
				}
			}
		});

		// Create a file chooser that opens up as a Save dialog
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();
				int option = chooser.showSaveDialog(FileChooser.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					statusbar.setText("You saved "
							+ ((chooser.getSelectedFile() != null) ? chooser
									.getSelectedFile().getName() : "nothing"));
				} else {
					statusbar.setText("You canceled.");
				}
			}
		});

		// Create a file chooser that allows you to pick a directory
		// rather than a file
		dirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int option = chooser.showOpenDialog(FileChooser.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					statusbar.setText("You opened "
							+ ((chooser.getSelectedFile() != null) ? chooser
									.getSelectedFile().getName() : "nothing"));
				} else {
					statusbar.setText("You canceled.");
				}
			}
		});

		c.add(openButton);
		// c.add(saveButton);
		// c.add(dirButton);
		c.add(statusbar);
	}

	public static void main(String args[]) {
		FileChooser sfc = new FileChooser();
		sfc.setVisible(true);
	}
}
