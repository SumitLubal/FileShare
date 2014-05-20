package RMI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class HTTPClientEcho {
	
	private static String serverAddress = "10.10.13.166";

	public static String getNodeIPAdress() {

		Socket smtpSocket = null;
		DataOutputStream os = null;
		DataInputStream is = null;
		String nodeAddress = null;
		try {
			smtpSocket = new Socket(serverAddress , 48103);
			os = new DataOutputStream(smtpSocket.getOutputStream());
			is = new DataInputStream(smtpSocket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: hostname");
		} catch (IOException e) {
			System.err
					.println("Couldn't get I/O for the connection to: hostname");
			e.printStackTrace();
		}

		if (smtpSocket != null && os != null && is != null) {
			try {
				os.writeBytes("Give me address...\n");
				String address = is.readLine();
				System.out.println("got address of free node in cluster");
				System.out.println(address);
				nodeAddress = address;
				is.close();
				os.close();
				smtpSocket.close();
			} catch (UnknownHostException e) {
				System.err.println("Trying to connect to unknown host: " + e);
			} catch (IOException e) {
				System.err.println("IOException:  " + e);
			}
		}
		return nodeAddress;
	}
}
