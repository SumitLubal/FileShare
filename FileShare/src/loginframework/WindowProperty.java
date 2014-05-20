package loginframework;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;


public class WindowProperty {

	 static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	public static void setFullScreen(JFrame frame) {
		
		GraphicsDevice vc = ge.getDefaultScreenDevice();
		vc.setFullScreenWindow(frame);
	}

	public static int getWidth() {
		return ge.getMaximumWindowBounds().width;
	}
	public static int getHeight(){
		return ge.getMaximumWindowBounds().height;
	}
}
