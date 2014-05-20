package loginframework;
/*
Share on Google+Share on Google+
Pie Chart
Advertisement
In this section we are providing you an example to create a Pie Chart.
Pie Chart

     

In this section we are providing you an example to create a Pie Chart.

Description of Example
 
For creating a Pie Chart we use PieDataset. In this example, for creating a PieDataset we are using DefaultPieDataset class. Then we add some data in this dataset that will show in chart.

Description of Code
 
  DefaultPieDataset pieDataset = new DefaultPieDataset();
For defining a dataset for a pie chart we have to create an object of DefaultPieDataSet type :
  
    setValue(?one?, new Integer(10));
After creating the instance of dataset then we have to add the data in the data set by invoking the method setValue(). Like
    
  JFreeChart chart = ChartFactory.createPieChart("Pie Chart using JFreeChart", pieDataset true, true, true);
After added the data in dataset we create the Pie Chart by invoking the createPieChart() method. This method is a static method of ChartFactory class and its returns the object of JFreeChart type. This method syntax is:
  Public static JFreeChart createPieChart(java.lang.String title, pieDataset dataset, boolean legend, boolean tooltips, boolean urls);

  ChartFrame frame1=new ChartFrame("Pie Chart",chart);
After this we create the object of ChartFrame. It used to display a chart.

Here is code of the program :*/
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.jfree.chart.*;
import org.jfree.chart.title.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.*;

public class pie {
	public static void main(String arg[]) {
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		ArrayList<passwordtablestructure> data = getData(); // getsdata from table
		for(int i=0;i<data.size();i++){
			pieDataset.setValue(data.get(i).getName() + " "+data.get(i).month,data.get(i).count);
		}
		JFreeChart chart = ChartFactory.createPieChart(
				"Password change index ", pieDataset, true, true, true);

		ChartFrame frame1 = new ChartFrame("Pie Chart", chart);
		frame1.setVisible(true);
		frame1.setSize(300, 300);
	}

	private static ArrayList getData() {
		ResultSet rs = DB.readFromTable("passwordchange");
		ArrayList<passwordtablestructure> data = new ArrayList<passwordtablestructure>();
		try {
			while (rs.next()) {
				data.add(new passwordtablestructure(rs.getInt("count"), rs
						.getString("lastmonth"), rs.getString("name")));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}
}

class passwordtablestructure {
	int count;
	String month;
	String name;

	public passwordtablestructure(int cou, String mon, String na) {
		count = cou;
		month = mon;
		name = na;
	}

	public int getCount() {
		return count;
	}

	public String getMonth() {
		return month;
	}

	public String getName() {
		return name;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}