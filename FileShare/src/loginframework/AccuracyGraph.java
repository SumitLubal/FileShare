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

public class AccuracyGraph {
	public static void main(String a[]) {
		JFreeChart chart = getChart();
		ChartFrame frame1 = new ChartFrame("Chart for password change", chart);
		frame1.setVisible(true);
		frame1.setSize(300, 300);
	}

	public static ArrayList<AccuracyStructure> getAllRevenueList() {
		try {
			// try-catch exception, if any exception occurs return null
			// driver
			// connection code

			String sql;
			sql = "SELECT accuracy,user FROM accuracy ORDER BY user";

			ResultSet rs = DB.selectQuerry(sql);
			// now iterate over the result set and create list of objects of
			// class `Revenue`

			// create an ArrayList<Revenue>
			ArrayList<AccuracyStructure> revenueList = new ArrayList<AccuracyStructure>();
			while (rs.next()) {
				// for each row in result set create the object of class Revenue
				AccuracyStructure r = new AccuracyStructure();
				r.accuracy = rs.getInt("accuracy");
				r.name = rs.getString("user");
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
		ArrayList<AccuracyStructure> revenueList = getAllRevenueList();
		// check list is not null and empty
		if (revenueList != null && !revenueList.isEmpty()) {
			for (AccuracyStructure r : revenueList) {
				dataset.setValue(r.accuracy, r.name, "Months");
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

class AccuracyStructure {
	int accuracy;
	String name;
}
