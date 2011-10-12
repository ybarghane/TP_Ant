/*
 * Copyright (c) 2009 - Ahcene Bounceur 
 */
package org.ubo.elementz;

import javax.swing.JApplet;

import org.ubo.elementz.model.ElementZ_Model;
import org.ubo.elementz.ui.MyPanel;

public class ElementZ_Applet extends JApplet {
	@Override
	public void init() {
		ElementZ_Model model = new ElementZ_Model();
		MyPanel jp = new MyPanel(model);
		getContentPane().add(jp);
		setSize(400, 400);
		setVisible(true);
	}
}
