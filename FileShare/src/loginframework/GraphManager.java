package loginframework;
import java.awt.Color;
import java.awt.Graphics;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GraphManager {
	Graphics g;
	int width, height;
	ArrayList<String> counter;
	ArrayList<String> users;
	ArrayList<UserInformation> info;
	int graphwidth, graphheight;
	int usersCount;
	private int totalProcessesInAllUsers;
	private String date = "";

	public GraphManager(Graphics graphics, int width, int height) {
		g = graphics;
		this.width = width;
		this.height = height;
		info = new ArrayList<UserInformation>();
		fetchData(DB.readData());
		for (int i = 0; i < info.size(); i++) {
			fetchDataForIndivisualUser(info.get(i)); // gets data and parses
														// into userInformation
														// type class

		}
		for (int j = 0; j < info.size(); j++) {
			totalProcessesInAllUsers += info.get(j).total;
		}
		usersCount = info.size();
		prepare();
	}

	public GraphManager(Graphics graphics, int width2, int height2, String date) {
		// TODO Auto-generated constructor stub
		this.date = date;
		g = graphics;
		this.width = width2;
		this.height = height2;
		info = new ArrayList<UserInformation>();
		fetchData(DB.readData());
		for (int i = 0; i < info.size(); i++) {
			fetchDataForIndivisualUser(info.get(i)); // gets data and parses
														// into userInformation
														// type class

		}
		for (int j = 0; j < info.size(); j++) {
			totalProcessesInAllUsers += info.get(j).total;
		}
		usersCount = info.size();
		prepare();
	}

	private void prepare() {
		g.setColor(Color.BLUE);
		g.drawLine(10, height - 10, 10, 10);
		g.drawLine(10, height - 10, width - 10, height - 10);
		graphheight = height - 20;
		graphwidth = width - 20;

		int diff = width / (usersCount + 1);
		for (int i = 0; i < usersCount; i++) {
			int y2 = height
					- ((info.get(i).total * height) / totalProcessesInAllUsers);
			int x1 = (diff * (i + 1)) + 10;
			g.drawLine(x1, height - 10, x1, y2);
			g.setColor(Color.GREEN);
			g.drawString(info.get(i).name, x1, height - 5);
			for (int cnt = 0; cnt < info.get(i).usedapplicationname.size(); cnt++) {
				g.setColor(Color.red);
				g.drawString(info.get(i).usedapplicationname.get(cnt), x1,
						height - 15 - (cnt*10));
				g.setColor(Color.GREEN);
			}
			g.drawString(info.get(i).total + "", x1, y2);
			g.drawString("y=usage", 10, height / 2);
			g.drawString("x = users", width / 2, height - 10);
			g.setColor(Color.BLUE);

		}
	}

	private void fetchDataForIndivisualUser(UserInformation userInformation) {

		ResultSet resultset = DB.readFromTable(userInformation.name);
		try {
			while (resultset.next()) {
				String datercvd = resultset.getString("date");
				if (datercvd.contains(date)) {
					userInformation.rawdata.add(new raw(resultset
							.getString("name"), datercvd));
				}
			}
			userInformation.format();
			for (int i = 0; i < info.size(); i++) {
				System.out.println("name " + info.get(i).name + " counts "
						+ info.get(i).total);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fetchData(ResultSet resultSet) {
		try {
			while (resultSet.next()) {
				String name = resultSet.getString("user");
				System.out.println(name);
				info.add(new UserInformation(name));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}