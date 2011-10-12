/*
 * Copyright (c) 2009 - Ahcene Bounceur
 */
package org.ubo.elementz.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;
import java.util.Random;

import javax.swing.JPanel;

import org.ubo.elementz.MyColor;
import org.ubo.elementz.model.Ball;
import org.ubo.elementz.model.ElementZ_Model;

public class MyPanel extends JPanel {
	/** Serializable version. */
	private static final long serialVersionUID = 1L;
	private ElementZ_Model ezm;
	private Ball[][] matrix = new Ball[8][8];
	private int v = 50;
	private BufferedImage im = null;
	private int size = 40;

	/**
	 * Ctor.
	 * 
	 * @param model
	 *            le modèle utilisé par ce panel. <b>Attention : </b>ce modèle
	 *            ne peut pas être <code>null</code>.
	 */
	public MyPanel(ElementZ_Model model) {
		if (model == null)
			throw new InvalidParameterException("Model canno't be null.");
		// try {
		// //im = ImageIO.read(new File("./images/4.jpg"));
		// im = ImageIO.read(this.getClass().getResource("/images/4.jpg"));
		// //getClass().getResource("/boules_isen/boule_0.jpg")
		// } catch (IOException ex) {
		// Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null,
		// ex);
		// }

		ezm = model;
		ezm.prepar();
		initialiser();
		setBackground(new Color(0, 0, 10));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(im, 0, 0, this);
		Random rnd = new Random();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				g.setColor(new Color(255, 255, 255, 20));
				g.fillRect(i * 50 - 4, j * 50 - 4, 48, 48);
				g.setColor(new Color(255, 255, 255, 30));
				g.drawRect(i * 50 - 4, j * 50 - 4, 48, 48);
			}
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				matrix[i][j].draw(g);
			}
		}
	}

	public void initialiser() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				matrix[i][j] = new Ball(this, j * v, i * v, size, ezm.getXY(i,
						j) - 1, j, i);
				addMouseMotionListener(matrix[i][j]);
				addMouseListener(matrix[i][j]);
			}
		}
	}

	public void afficher() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				matrix[i][j].setColor(MyColor.tabColor[ezm.getXY(i, j) - 1]);
			}
		}

		// repaint();
	}

	public void permut(Ball b1, Ball b2) {
		ezm.permut(b1.getPosY(), b1.getPosX(), b2.getPosY(), b2.getPosX());
		ezm.prepar();
		afficher();
	}
}
