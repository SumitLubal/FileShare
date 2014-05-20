package share;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class FileBrowserClient{
	Socket socket = null;
	public FileBrowserClient(String add) throws UnknownHostException, IOException {
		socket = new Socket(add,2400);
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		System.out.println("connected");
		output.writeUTF("sam");
		System.out.println("sent name");
		int i = input.readInt();
		System.out.println("rcvdcount");
		ArrayList<String> files = new ArrayList<String>();
		for(int j=0;j<i;j++){
			String tmp;
			files.add(tmp =input.readUTF());
			System.out.println(tmp);
		}
		new PathTest(files);
	}
	public static void main(String a[]){
		try {
			new FileBrowserClient("127.0.01");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
