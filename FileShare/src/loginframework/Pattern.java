package loginframework;

public class Pattern {
	private int click1x,click2x,click3x;
	private int click1y,click2y,click3y;

	public Pattern(int pat1x, int pat2x, int pat3x, int pat1y, int pat2y, int pat3y) {
		click1x = pat1x;
		click2x = pat2x;
		click3x = pat3x;
		click1y = pat1y;
		click2y = pat2y;
		click3y = pat3y;
	}

	public int getClick1x() {
		return click1x;
	}

	public int getClick2x() {
		return click2x;
	}

	public int getClick3x() {
		return click3x;
	}

	public int getClick1y() {
		return click1y;
	}

	public int getClick2y() {
		return click2y;
	}

	public int getClick3y() {
		return click3y;
	}

	public void setClick1x(int click1x) {
		this.click1x = click1x;
	}

	public void setClick2x(int click2x) {
		this.click2x = click2x;
	}

	public void setClick3x(int click3x) {
		this.click3x = click3x;
	}

	public void setClick1y(int click1y) {
		this.click1y = click1y;
	}

	public void setClick2y(int click2y) {
		this.click2y = click2y;
	}

	public void setClick3y(int click3y) {
		this.click3y = click3y;
	}
}
