package loginframework;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChart {
	public static void main(String a[]) {
		JFreeChart chart = getChart();
		ChartFrame frame1 = new ChartFrame("Chart for password change", chart);
		frame1.setVisible(true);
		frame1.setSize(300, 300);
	}

	public static ArrayList<passwordtablestructure> getAllRevenueList() {
		try {
			// try-catch exception, if any exception occurs return null
			// driver
			// connection code

			String knwmonth = JOptionPane.showInputDialog(null, "Enter month?");
			String sql;
			if (knwmonth.length()<=0) {
				sql = "SELECT lastmonth,count,name FROM passwordchange ORDER BY lastmonth";
			} else {
				sql = "SELECT lastmonth,count,name FROM passwordchange where lastmonth='"+knwmonth+"'";
			}
			ResultSet rs = DB.selectQuerry(sql);
			// now iterate over the result set and create list of objects of
			// class `Revenue`

			// create an ArrayList<Revenue>
			ArrayList<passwordtablestructure> revenueList = new ArrayList<passwordtablestructure>();
			while (rs.next()) {
				// for each row in result set create the object of class Revenue
				passwordtablestructure r = new passwordtablestructure(0, "", "");
				r.setMonth((rs.getString("lastmonth")));
				r.setCount((Integer.parseInt(rs.getString("count"))));
				r.setMonth(rs.getString("name"));
				revenueList.add(r); // add object to list
			}

			return revenueList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JFreeChart getChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// retrieve the revenue ArrayList
		// make sure that you have initialized the revenueDao object before
		// calling method
		ArrayList<passwordtablestructure> revenueList = getAllRevenueList();
		// check list is not null and empty
		if (revenueList != null && !revenueList.isEmpty()) {
			for (passwordtablestructure r : revenueList) {
				dataset.setValue(r.getCount(), r.getMonth(), r.getName());
			}
		}

		JFreeChart chart = ChartFactory.createBarChart3D("User", "Month", // domain
																			// axis
																			// label
				"Count", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false);

		CategoryPlot plot = chart.getCategoryPlot();
		CategoryAxis axis = plot.getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 8.0));

		CategoryItemRenderer renderer = plot.getRenderer();
		BarRenderer r = (BarRenderer) renderer;
		r.setMaximumBarWidth(0.75);

		return chart;
	}
}
