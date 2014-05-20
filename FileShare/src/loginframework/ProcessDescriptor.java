package loginframework;
import java.util.Calendar;

public class ProcessDescriptor {
	String name, userName;
	Calendar processStart, processEnd;

	private void setStartTimer() {
		// Get the Date corresponding to 11:01:00 pm today.
		processStart = Calendar.getInstance();
	}

	public void setStopTimer() {
		processEnd = Calendar.getInstance();
	}

	public static String getData(Calendar data,int type) {
		Calendar processEnd = data;
		String decodedData = "";
		System.out.println("decoding data");
		if(type==1){
		decodedData += "added";
		}else{
			decodedData+="removed";
		}
		decodedData += processEnd.get(Calendar.DAY_OF_MONTH) + "/"
				+ processEnd.get(Calendar.MONTH) + "/"
				+ processEnd.get(Calendar.YEAR) + " - ";
		decodedData += processEnd.get(Calendar.HOUR) + ":"
				+ processEnd.get(Calendar.MINUTE) + ":"
				+ processEnd.get(Calendar.SECOND) + "";
		if (processEnd.get(Calendar.AM_PM) == 1) {
			decodedData += "PM";
		} else {
			decodedData += "AM";

		}
		return decodedData;
	}

	public ProcessDescriptor(String processName, String userName) {
		name = processName;
		this.userName = userName;
		setStartTimer();

	}

	public ProcessDescriptor(String processName) {
		name = processName;
		setStartTimer();
	}

	public static void main(String a[]) {
		new ProcessDescriptor("example process");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Calendar getProcessStart() {
		return processStart;
	}

	public void setProcessStart(Calendar processStart) {
		this.processStart = processStart;
	}

	public Calendar getProcessEnd() {
		return processEnd;
	}

	public void setProcessEnd(Calendar processEnd) {
		this.processEnd = processEnd;
	}

}
