/*
 * Copyright (c) 2009 - Ahcene Bounceur
 */
package org.ubo.elementz.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.ubo.elementz.MyColor;
import org.ubo.elementz.ui.MyPanel;

/**
 * Classe modèle pour une balle d'ElementZ.
 */
public class Ball implements MouseMotionListener, MouseListener {
	/** Coordonnée x de la balle. */
	private int x;
	/** Coordonnée y de la balle. */
	private int y;
	private int size;
	private Color color;
	/** Numéro de la ligne. */
	private int posX;
	/** Numéro de la colonne. */
	private int posY;
	private MyPanel panel;
	private int value;
	/** <code>True</code> si la balle est selectionnée. */
	private boolean selected = false;
	/** <code>True</code> si la balle est en surbrillance. */
	private boolean highlighted = false;
	/**
	 * Pour le déplacement des balles. Si non <code>null</code> la balle se
	 * déplace jusqu'à la destination désignée.
	 */
	private Point destination = null;

	private static Ball first = null;

	/** Contructeur par recopie. */
	public Ball(Ball ball) {
		this.panel = ball.getPanel();
		this.x = ball.getX();
		this.y = ball.getY();
		this.size = ball.getSize();
		this.value = ball.getValue();
		this.color = MyColor.tabColor[value];
		this.posX = ball.getPosX();
		this.posY = ball.getPosY();
	}

	public Ball(MyPanel panel, int x, int y, int size, int value, int posX,
			int posY) {
		this.panel = panel;
		this.x = x;
		this.y = y;
		this.size = size;
		this.value = value;
		this.color = MyColor.tabColor[value];
		this.posX = posX;
		this.posY = posY;
	}

	public MyPanel getPanel() {
		return panel;
	}

	public int getValue() {
		return value;
	}

	public int getSize() {
		return size;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public Color getColor() {
		return color;
	}

	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 250, 50));
		g.fillOval(x + 1, y + 1, size, size);
		g.setColor(color);
		g.fillOval(x, y, size, size);
		g.setColor(new Color(255, 255, 255, 50));
		g.fillOval(x + 2, y + 2, size - 4, size - 10);
		g.setColor(new Color(255, 255, 255, 60));
		g.fillOval(x + 8, y + 5, size - 16, size - 30);
		if (highlighted) {
			for (int i = 0; i < 4; i++) {
				g.setColor(new Color(color.getRed(), color.getGreen(), color
						.getBlue(), 255 - (i * 60)));
				g.drawOval(x - i, y - i, size + (2 * i), size + (2 * i));
			}
		}
		if (selected) {
			for (int i = 0; i < 10; i++) {
				g.setColor(new Color(color.getRed(), color.getGreen(), color
						.getBlue(), 255 - (i * 25)));
				g.drawOval(x - i, y - i, size + (2 * i), size + (2 * i));
			}
		}
	}

	public void condSelect() {
		selected = !selected;
		panel.repaint();
	}

	public void select() {
		selected = true;
		panel.repaint();
	}

	public void deselect() {
		selected = false;
		panel.repaint();
	}

	public void highlight() {
		highlighted = true;
		panel.repaint();
	}

	public void deHighlight() {
		highlighted = false;
		panel.repaint();
	}

	/**
	 * Vérifie si la position spécifiée est à l'intérieur de la balle.
	 * 
	 * @param x
	 *            coordonnée x de la position à tester
	 * @param y
	 *            coordonnée y de la position à tester
	 * @return <code>true</code> si la position est située à l'intérieur de la
	 *         balle, <code>false</code> sinon.
	 */
	public boolean isInside(int x, int y) {
		if (x > this.x && y > this.y && x < (this.x + size)
				&& y < (this.y + size))
			return true;
		return false;
	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		if (isInside(e.getX(), e.getY()) && !highlighted) {
			highlight();
		}
		if (!isInside(e.getX(), e.getY()) && highlighted) {
			deHighlight();
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (isInside(e.getX(), e.getY())) {
			if (first == null) {
				first = this;
				select();
			} else {
				this.deselect();
				first.deselect();
				panel.permut(this, first);
				first = null;
			}
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	@Override
	public String toString() {
		// return "("+posX+","+posY+")";
		// return "La boule de la ligne "+posY+ " et de la colonne "+posX;
		return value + "";
	}
	
	public void printColor(){
		StringBuilder sb = new StringBuilder();
		sb.append("Couleur : ");
		sb.append(color.getRed());
		sb.append(", ");
		sb.append(color.getGreen());
		sb.append(", ");
		sb.append(color.getBlue());
		System.out.println(sb.toString());
	}

	public void setColor(int r, int g, int b) {
		this.color = new Color(r, g, b);
	}
}
