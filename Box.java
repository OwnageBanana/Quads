package Grids;

import java.awt.Color;
import java.util.Arrays;

import javax.swing.JPanel;

public class Box {
	private int x, y, width, height, iteration = 0; // x & y are in top left of box
	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}

	private double delta; // delta is the entropy of pixels in the box, avg is
							// the average of all pixels in HSB
	private int[] avg = new int[3];
	private JPanel panel;

	public Box(int x, int y, int height, int width, double delta, int[] avg,
			JPanel panel) {

		this.x = x;
		this.y = y;	
//		if (x%2 == 1)
//			this.x = x+1;
//		if (y%2 == 1)
//			this.y = y+1;
		this.width = width;
		this.height = height;
		this.delta = delta;
		this.avg = avg;
		this.panel = panel;
		this.panel.setBackground(new Color(avg[0], avg[1], avg[2]));
		this.panel.setSize(width, height);
		this.panel.setLocation(x + 22, y + 20);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getAvg() {
		return avg;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public void setAvg(int[] avg) {
		this.avg = avg;
	}

	public JPanel getPanel() {
		return panel;
	}

	@Override
	public String toString() {
		return "Box [x=" + x + ", y=" + y + ", width=" + width + ", height="
				+ height + ", delta=" + delta + ", avg=" + Arrays.toString(avg)
				+ "]";
	}

}
