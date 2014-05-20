package loginframework;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GraphFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel graphPane;
	private JButton Count;
	private JButton ApplicationPerDay;
	private JButton Clear;
	private JButton exit;
	private String date;
	private JButton btnPiechartpass;
	private JButton btnPasschange;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphFrame frame = new GraphFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GraphFrame() {
		setBounds(100, 100, 671, 389);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		graphPane = new JPanel();
		graphPane.setBounds(90, 11, 563, 340);
		contentPane.add(graphPane);

		Count = new JButton("Count");
		Count.setBounds(3, 179, 77, 23);
		contentPane.add(Count);

		ApplicationPerDay = new JButton("PerDay");
		ApplicationPerDay.setBounds(3, 213, 77, 23);
		contentPane.add(ApplicationPerDay);

		Clear = new JButton("Clear");
		Clear.setBounds(3, 247, 77, 23);
		contentPane.add(Clear);

		exit = new JButton("Exit");
		exit.setBounds(3, 281, 77, 23);
		contentPane.add(exit);
		
		btnPasschange = new JButton("PassChange");
		btnPasschange.setBounds(3, 145, 89, 23);
		contentPane.add(btnPasschange);
		btnPiechartpass = new JButton("PieChart-Pass");
		btnPiechartpass.setBounds(3, 111, 89, 23);
		contentPane.add(btnPiechartpass);
		
		JButton btnAccuracy = new JButton("Accuracy");
		btnAccuracy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AccuracyGraph.main(null);
			}
		});
		btnAccuracy.setBounds(3, 77, 89, 23);
		contentPane.add(btnAccuracy);
		btnPasschange.addActionListener(this);
		btnPiechartpass.addActionListener(this);
		Count.addActionListener(this);
		ApplicationPerDay.addActionListener(this);
		Clear.addActionListener(this);
		exit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton pressedButton = (JButton) e.getSource();
		if (pressedButton.equals(Count)) {
			showCount();
		} else if (pressedButton.equals(ApplicationPerDay)) {
			date = JOptionPane.showInputDialog(this,
					"Enter Date to be analysed? in dd//mm//yyyy format");
			showApp();
		} else if (pressedButton.equals(Clear)) {
			clearPanel();
		} else if (pressedButton.equals(exit)) {
			this.dispose();
		}else if(pressedButton.equals(btnPasschange)){
			BarChart.main(null);
		}else if(pressedButton.equals(btnPiechartpass)){
			pie.main(null);
		}
	}

	private void clearPanel() {
		Graphics g = graphPane.getGraphics();
		g.setColor(getBackground());
		g.fillRect(0, 0, graphPane.getWidth(), graphPane.getHeight());
	}

	private void showApp() {
		Graphics g = graphPane.getGraphics();
		BufferedImage paint = new BufferedImage(graphPane.getWidth(), graphPane.getHeight(), BufferedImage.TYPE_INT_RGB);
		new GraphManager(paint.getGraphics(),graphPane.getWidth(),graphPane.getHeight(),date);
		g.drawImage(paint, 0, 0, graphPane.getWidth(), graphPane.getHeight(), this);
	}

	private void showCount() {
		Graphics g = graphPane.getGraphics();
		BufferedImage paint = new BufferedImage(graphPane.getWidth(), graphPane.getHeight(), BufferedImage.TYPE_INT_RGB);
		new GraphManager(paint.getGraphics(),graphPane.getWidth(),graphPane.getHeight());
		g.drawImage(paint, 0, 0, graphPane.getWidth(), graphPane.getHeight(), this);
	}
}



