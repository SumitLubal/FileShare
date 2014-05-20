package share;

import java.io.File;
import java.util.List;

public class FileSender {
	Client client;
	String baseDirectory;
	CopyDialog copy;
	String username;

	public void sendDroppedFiles(List<File> droppedFiles, Client client) {
		this.client = client;
		copy.setVisible(true);
		for (int i = 0; i < droppedFiles.size(); i++) {
			if (droppedFiles.get(i).isDirectory()) { // for every file that is
														// dropped
				baseDirectory = droppedFiles.get(i).getParentFile()
						.getAbsolutePath(); // get its parent file path
				sendFolder(droppedFiles.get(i));
			} else if (droppedFiles.get(i).isFile()) {
				sendFile(droppedFiles.get(i), "/"
						+ droppedFiles.get(i).getName());
			}
		}
		copy.setVisible(false);
	}

	private void sendFile(File file, String path) {
		try {
			client.send(file, path); // file path is without start like if u r
										// sending b folder from c:/a/b/c/ then
										// its /c/<filename>.ext
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		username = "";
	}

	private void sendFolder(File dir) {
		String directory = dir.getAbsolutePath(); // gets every item in folder
		File files[] = new File(directory).listFiles();
		String path;
		for (File file : files) {
			if (file.isDirectory()) {
				sendFolder(file);
			} else if (file.isFile()) {
				path = getDirectoryAndFile(file.getAbsolutePath());
				sendFile(file, path);
			}
		}
	}

	private String getDirectoryAndFile(String path) {
		String statement = null;
		statement = path.substring(baseDirectory.length());
		System.out.println(path);
		System.out.println(statement + " For directory");
		return statement;

	}

	public void sendDroppedFiles(List<File> droppedFiles, Client client,
			String string) {
		// TODO Auto-generated method stub
		username = string;
		this.client = client;
		copy.setVisible(true);
		for (int i = 0; i < droppedFiles.size(); i++) {
			if (droppedFiles.get(i).isDirectory()) { // for every file that is
														// dropped
				baseDirectory = droppedFiles.get(i).getParentFile()
						.getAbsolutePath(); // get its parent file path
				sendFolder(droppedFiles.get(i));
			} else if (droppedFiles.get(i).isFile()) {
				sendFile(droppedFiles.get(i), "/"
						+ droppedFiles.get(i).getName());
			}
		}
		copy.setVisible(false);
	}

}
