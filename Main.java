package Grids;

import java.util.Scanner;
import java.awt.Button;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {

	public static void main(String[] args) throws IOException {

		Scanner in = new Scanner(System.in);
		System.out.print("input image path: ");
		// C:\Users\Adam Mills\Pictures\c16fec37081c4e68e4a31640142d9f14d29482d4.jpg
		String path = in.nextLine();
		System.out.println(path);
		System.out.print("Input number of iterations: ");
		int iterations = in.nextInt();
		in.close();

		File input = new File(path);
		BufferedImage image = ImageIO.read(input);

		Heap boxes = new Heap();

		//hot mess of a constructor for a box
		boxes.add(new Box(0, 0, image.getHeight(), image.getWidth(),
				getDelta(0, 0, image.getHeight(), image.getWidth(), 1,
						new int[3], image), getAvg(0, 0, image.getHeight(),
						image.getWidth(), image), new JPanel()));


		// Building gui
		JFrame layout = new JFrame("Quads");
		JPanel panel = new JPanel();
		GUI(image, boxes, layout, panel);

		int i = 0;
		while (i < iterations || boxes.size() == 0) {

			Box b = boxes.deleteMax();
			// cant have things getting too small
			if ((b.getWidth() * b.getHeight()) / 2 >= 2) {

				// box for top left quad
				int[] a1 = getAvg(b.getX(), b.getY(), b.getHeight() / 2,
						b.getWidth() / 2, image);
				Box b1 = new Box(b.getX(), b.getY(), b.getHeight() / 2,
						b.getWidth() / 2, getDelta(b.getX(), b.getY(),
								b.getHeight() / 2, b.getWidth() / 2,
								b.getIteration() + 1, a1, image), a1,
						new JPanel());
				b1.setIteration(b.getIteration() + 1);
				panel.add(b1.getPanel());
				boxes.add(b1);

				// box for top right quad
				int[] a2 = getAvg(b.getX() + b.getWidth() / 2, b.getY(),
						b.getHeight() / 2, b.getWidth() / 2, image);
				Box b2 = new Box(b.getX() + b.getWidth() / 2, b.getY(),
						b.getHeight() / 2, b.getWidth() - (b.getWidth() / 2),
						getDelta(b.getX() + b.getHeight() / 2, b.getY(),
								b.getHeight() / 2, b.getWidth() / 2,
								b.getIteration() + 1, a2, image), a2,
						new JPanel());
				b2.setIteration(b.getIteration() + 1);
				panel.add(b2.getPanel());
				boxes.add(b2);

				// box for bottom left quad
				int[] a3 = getAvg(b.getX(), b.getY() + b.getHeight() / 2,
						b.getHeight() / 2, b.getWidth() / 2, image);
				Box b3 = new Box(b.getX(), b.getY() + b.getHeight() / 2,
						b.getHeight() - (b.getHeight() / 2), b.getWidth() / 2,
						getDelta(b.getX(), b.getY() + b.getHeight() / 2,
								b.getHeight() / 2, b.getWidth() / 2,
								b.getIteration() + 1, a3, image), a3,
						new JPanel());
				b3.setIteration(b.getIteration() + 1);
				panel.add(b3.getPanel());
				boxes.add(b3);

				// box for bottom right quad
				int[] a4 = getAvg(b.getX() + b.getWidth() / 2,
						b.getY() + b.getHeight() / 2, b.getHeight() / 2,
						b.getWidth() / 2, image);
				Box b4 = new Box(b.getX() + b.getWidth() / 2, b.getY()
						+ b.getHeight() / 2, b.getHeight()
						- (b.getHeight() / 2), b.getWidth()
						- (b.getWidth() / 2), getDelta(b.getX() + b.getWidth()
						/ 2, b.getY() + b.getHeight() / 2, b.getHeight() / 2,
						b.getWidth() / 2, b.getIteration() + 1, a4, image), a4,
						new JPanel());
				b4.setIteration(b.getIteration() + 1);
				panel.add(b4.getPanel());
				boxes.add(b4);

				panel.repaint();
				panel.remove(b.getPanel());
			}
			i++;
		}
	}

	static void GUI(BufferedImage image, Heap boxes, JFrame layout, JPanel panel) {

		layout.setVisible(true);
		layout.setSize(image.getWidth() + 60, image.getHeight() + 120);
		layout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layout.setAlwaysOnTop(true);
		layout.setLocationRelativeTo(null);
		
		panel.setLayout(null);
		layout.add(panel);
		panel.setBackground(Color.BLACK);

		panel.add(boxes.peek().getPanel());

		// absolute positions for buttons because lazy and idgaf
		Button start = new Button("Start");
		start.setSize(60, 25);
		start.setLocation(20, image.getHeight() + 30);
		Button stop = new Button("Stop");
		stop.setSize(60, 25);
		stop.setLocation(100, image.getHeight() + 30);
		Button reset = new Button("Reset");
		reset.setSize(60, 25);
		reset.setLocation(180, image.getHeight() + 30);
		Button save = new Button("Save");
		save.setSize(60, 25);
		save.setLocation(260, image.getHeight() + 30);
		panel.add(start);
		panel.add(stop);
		panel.add(reset);
		panel.add(save);
		
	}

	// Returns the RGB float
	static int[] getAvg(int x, int y, int h, int w, BufferedImage img) {

		int[] RGB = new int[3];
		int counter = 0;
		for (int i = x; i < (w + x) && i < img.getWidth(); i++) {
			for (int j = y; j < (h + y) && j < img.getHeight(); j++) {
				// System.out.println(i + " " + j + " Red");
				Color c = new Color(img.getRGB(i, j));
				RGB[0] += c.getRed();
				RGB[1] += c.getGreen();
				RGB[2] += c.getBlue();
				counter++;
			}
		}
		RGB[0] = RGB[0] / counter;
		RGB[1] = RGB[1] / counter;
		RGB[2] = RGB[2] / counter;
		return RGB;
	}

	// TODO find a better way to do this calculation
	static double getDelta(int x, int y, int h, int w, int iteration,
			int[] avg, BufferedImage img) {

		double[] delta = new double[3];
		for (int i = x; i < w + x && i < img.getWidth(); i++) {
			for (int j = y; j < h + y && j < img.getHeight(); j++) {
				Color c = new Color(img.getRGB(i, j));
				delta[0] += Math.abs(c.getRed() - avg[0]);
				delta[1] += Math.abs(c.getGreen() - avg[1]);
				delta[2] += Math.abs(c.getBlue() - avg[2]);
			}

		}
		for (int i = 0; i <= 2; i++)
			delta[i] = delta[i];

		return Math.abs(((delta[0] + delta[1] + delta[2]) / 3)
				* (iteration * 4));
	}
}
