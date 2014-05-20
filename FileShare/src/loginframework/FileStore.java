package loginframework;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class FileStore {
	static String writeMaterial = "";
	static Calendar start, end;

	public static void writeIntoFile(ArrayList<ProcessDescriptor> userProcess,
			String userName) {
		writeMaterial = System.getProperty("line.separator") + writeMaterial
				+ "******" + userName + "********";

		for (int i = 0; i < userProcess.size(); i++) {
			ProcessDescriptor singleDisc = userProcess.get(i);
			start = userProcess.get(i).processStart;
			end = userProcess.get(i).processEnd;
			writeMaterial = writeMaterial
					+ System.getProperty("line.separator") + singleDisc.name;
			writeMaterial = writeMaterial + "Start: "
					+ ProcessDescriptor.getData(start,1);
			if (end == null) {
				end = Calendar.getInstance();
			}
			writeMaterial = writeMaterial + " End: "
					+ ProcessDescriptor.getData(end,2);
			writeMaterial+=System.getProperty("line.separator");
			writeMaterial+=System.getProperty("line.separator");
			writeMaterial+=System.getProperty("line.separator");
		}
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter("logFile.txt", true)));
			out.println(writeMaterial);
			System.out.println(writeMaterial);
			out.close();
		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}
	}
}
