package loginframework;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

//set start logging = true befor using list of process that are used by user
//set startLogging = false after completing Monitoring

public class ActivityLogger extends Thread {
	static ArrayList<ProcessDescriptor> processList, userProcess;
	private static boolean startLogging = false;

	public ActivityLogger() {
		start();
		processList = new ArrayList<ProcessDescriptor>();
		userProcess = new ArrayList<ProcessDescriptor>();
	}

	@Override
	public void run() {
		super.run();
		int count;
		String totalProcesses, line;
		try {

			while (true) {
				totalProcesses = "";
				Process p = Runtime.getRuntime().exec(
						System.getenv("windir") + "\\system32\\"
								+ "tasklist.exe");
				BufferedReader input = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
				// System.out.println("Now checking for processe");
				count = 0; // as input stream gives process list from 3rd
							// line
				while ((line = input.readLine()) != null) {
					// System.out.println(line); // <-- Parse data here.
					if (count > 2) {
						totalProcesses = totalProcesses + line + " ";
						checkProcesses(line);
					} else {
						count++;
					}
				}
				checkForRemovedProcess(totalProcesses);
				input.close();
				Thread.sleep(40);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	public static void main(String a[]) {
		new ActivityLogger();
	}

	private ProcessDescriptor checkForRemovedProcess(String totalProcesses) {
		// System.out.println(totalProcesses);
		ProcessDescriptor removedProcess = null;
		;
		for (int count = 0; count < processList.size(); count++) {
			String process = processList.get(count).getName();
			if (!(totalProcesses.contains(process))) {
				System.out.println("Closed program : " + process);
				(removedProcess = processList.get(count)).setStopTimer();
				processList.remove(count);
				for (int j = 0; j < userProcess.size(); j++) {
					if (userProcess.get(j).name.contains(process)) {
						userProcess.get(j).setStopTimer();
					}
				}
			}
		}
		if (removedProcess != null) {
			return removedProcess;
		}
		return null;
	}

	public static void write(String name) {
		FileStore.writeIntoFile(userProcess, name);
		setStartLogging(false);
	}

	public void checkProcesses(String content) {
		boolean isAlreadyRegistered = false;
		if (content.contains("Console")) {
			for (int i = 0; i < processList.size(); i++) {
				String process = processList.get(i).getName();
				if (process.contains(content.substring(0, 24))) {
					isAlreadyRegistered = true;
				}
			}
			if (!isAlreadyRegistered) {
				System.out.println("New process detected "
						+ content.substring(0, 24));
				processList.add(new ProcessDescriptor(content.substring(0, 24),
						"Abhi"));
				if (isStartLogging()) {
					userProcess.add(new ProcessDescriptor(content.substring(0,
							24)));
				} else {
					userProcess.clear();
				}
			}
		}
	}

	public static void writeToDB(String userName) {
		try {
			DB.createOrReplaceTable(userName);
		} catch (Exception e) {
		}
		try {
			for (int i = 0; i < userProcess.size(); i++) {
				ProcessDescriptor tmp = userProcess.get(i);
				System.out.println(ProcessDescriptor.getData(tmp.processStart, 1) +" asd");				
				String querry = "INSERT INTO  `test`.`"+userName+"` (`name` ,`date`)	VALUES ('"+tmp.name+"',  '"+ProcessDescriptor.getData(tmp.processEnd, 1)+"');";
				DB.fireQuerry(querry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isStartLogging() {
		return startLogging;
	}

	public static void setStartLogging(boolean startLogging) {
		ActivityLogger.startLogging = startLogging;
	}
}
