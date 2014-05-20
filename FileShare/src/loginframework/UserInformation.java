package loginframework;
import java.util.ArrayList;


public class UserInformation {
	public UserInformation(String name) {
		this.name = name;
	}
	String name;
	ArrayList<String> usedapplicationname = new ArrayList<String>();
	String date;
	ArrayList<raw> rawdata = new ArrayList<raw>();
	int counts[];
	int total = 0;
	public void format() {
		for(int i=0;i<rawdata.size();i++){
			total++;
			if(!usedapplicationname.contains(rawdata.get(i).name)){
			}
			usedapplicationname.add(rawdata.get(i).name);
		}
		counts = new int[usedapplicationname.size()];
		for(int i=0;i<usedapplicationname.size();i++){
			for(int j=0;j<rawdata.size();j++){
				if(rawdata.get(j).name.equals(usedapplicationname.get(i))){
					counts[i] = counts[i]+1;
				}
			}
		}
		System.out.println("count");
	}
}
class raw{
	public raw(String string, String string2) {
		name = string;
		date = string2;
	}

	String name,date;
}
