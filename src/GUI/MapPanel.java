package GUI;

import javax.swing.JPanel;

import Controllers.GraphController;
import uni.Link;
import uni.Node;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MapPanel extends JPanel {
	private GraphController controller = GraphController.getInstance();
	private int scale = 80;
	private int nodeWH = 35;

	public MapPanel() {
		setBackground(Color.lightGray);
		setLayout(null);
		changeSize();

		drawing();

	}

	public void drawing() {
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		for (Node n : controller.getNodes()) {
			g2D.setColor(Color.BLACK);
			g2D.fillOval(n.getX() * scale, n.getY() * scale, nodeWH, nodeWH);
		}

		for (Node n : controller.getNodes()) {

			for (Link l : n.getLinks()) {
				g2D.setColor(Color.darkGray);
				int xFrom = (n.getX() * scale) + (nodeWH / 2);
				int yFrom = (n.getY() * scale) + (nodeWH / 2);
				int xTo = (l.getToNode().getX() * scale) + (nodeWH / 2);
				int yTo = (l.getToNode().getY() * scale) + (nodeWH / 2);
				double angle = Math.atan2(yTo - yFrom, xTo - xFrom);
				int endX = (int) (xTo - 30 * Math.cos(angle - Math.PI / 12));
				int endY = (int) (yTo - 30 * Math.sin(angle - Math.PI / 12));
				int endX1 = (int) (xTo - 30 * Math.cos(angle + Math.PI / 12));
				int endY1 = (int) (yTo - 30 * Math.sin(angle + Math.PI / 12));

				g2D.setStroke(new BasicStroke(6F));

				g2D.drawLine(xFrom, yFrom, xTo, yTo);
				g2D.setColor(Color.darkGray);
				g2D.fillPolygon(new int[] { xTo, endX, endX1 }, new int[] { yTo, endY, endY1 }, 3);
				g2D.setFont(new Font("TimesRoman", Font.BOLD, 15));
				g2D.setColor(Color.CYAN);
				g2D.drawString(String.valueOf(l.getLength()), (xTo + xFrom) / 2, (yTo + yFrom) / 2);

			}

		}
		for (Node n : controller.getNodes()) {
			g2D.setFont(new Font("TimesRoman", Font.BOLD, 15));
			g2D.setColor(Color.RED);
			g2D.drawString(n.getName(), (n.getX() * scale) + (nodeWH / 2), (n.getY() * scale) + (nodeWH / 2));
			g2D.setColor(Color.GREEN);
			g2D.drawString(String.valueOf(n.getExpense()), (n.getX() * scale) + (nodeWH / 2),
					(n.getY() * scale) + (nodeWH));
		}
		if (!controller.getPathNodes().isEmpty()) {
			g2D.setColor(Color.blue);
			g2D.setStroke(new BasicStroke(2F));
			for (int i = 0; i < controller.getPathNodes().size() - 1; i++) {
				for (Link l : controller.getPathNodes().get(i + 1).getLinks()) {
					if (l.getToNode().equals(controller.getPathNodes().get(i))) {
						g2D.drawLine((controller.getPathNodes().get(i).getX() * scale) + (nodeWH / 2),
								(controller.getPathNodes().get(i).getY() * scale) + (nodeWH / 2),
								(controller.getPathNodes().get(i + 1).getX() * scale) + (nodeWH / 2),
								(controller.getPathNodes().get(i + 1).getY() * scale) + (nodeWH / 2));
					}
				}

			}

		}
	}

	public void changeSize() {
		int maxX = 0;
		int maxY = 0;
		for (Node n : controller.getNodes()) {
			if (n.getX() > maxX) {
				maxX = n.getX();
			}
			if (n.getY() > maxY) {
				maxY = n.getY();
			}
		}
		int height = (maxY * scale) + (nodeWH*2);
		int width = (maxX * scale) + (nodeWH*2);
		this.setPreferredSize(new Dimension(width, height));
	}

}
