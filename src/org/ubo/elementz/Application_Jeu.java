/*
 * Copyright (c) 2009 - Ahcene Bounceur
 */
package org.ubo.elementz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.ubo.elementz.model.ElementZ_Model;
import org.ubo.elementz.ui.MyPanel;
import org.ubo.elementz.ui.ScorePanel;

/**
 * Classe principale d'ElementZ.
 * Bla bla bla bla.
 */
public final class Application_Jeu {

	/** Classe utilitaire. */
	private Application_Jeu() {
	}

	/**
	 * Méthode principale.
	 */
	public static void main(String[] args) {
		JFrame jf = new JFrame("ElementZ");
		jf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		jf.setJMenuBar(createMenu());

		ElementZ_Model model = new ElementZ_Model();
		MyPanel jp = new MyPanel(model);
		ScorePanel score = new ScorePanel(model);
		jf.setLayout(new BorderLayout());
		jf.getContentPane().add(jp, BorderLayout.CENTER);
		jf.getContentPane().add(score, BorderLayout.SOUTH);
		jf.setSize(400, 470);
		jf.setVisible(true);
	}

	/**
	 * Creates the main menu.
	 */
	private static JMenuBar createMenu() {
		final JMenuBar menuBar = new JMenuBar();

		final JMenu helpMenu = new JMenu("Aide");
		final JMenuItem about = new JMenuItem("A propos");
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane
						.showMessageDialog(
								menuBar,
								"<html><h2>Element Z</h2>"
										+ "But du jeu : aligner au moins 3 balles de même couleur.</html>",
								"A propos d'ElementZ",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(about);

		menuBar.add(helpMenu);

		return menuBar;
	}
}
