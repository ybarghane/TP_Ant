/*
 * Copyright (c) 2009 - Ahcene Bounceur
 */
package org.ubo.elementz.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.ubo.elementz.model.ElementZ_Model;
import org.ubo.elementz.model.events.ScoreChangedEvt;

/**
 * Panel pour l'affichage du score.
 * 
 * @author Guillaume Bourel, guillaume.bourel@univ-brest.fr
 * @since 0.2
 */
public class ScorePanel extends JPanel {

	/** Modèle de donnée d'où est issu le score. */
	private final ElementZ_Model _model;
	/** JLabel affichant le score. */
	private JLabel _score;
	
	/**
	 * Ctor.
	 * @param model Modèle de donnée d'où est issu le score.
	 */
	public ScorePanel(ElementZ_Model model) {
		AnnotationProcessor.process(this);
		this._model = model;
		
		_score = new JLabel("Score : 0");
		add(_score);
	}

	@EventSubscriber(eventClass=ScoreChangedEvt.class)
	public void onScoreChanged(ScoreChangedEvt evt){
		_score.setText("Score : " + evt.getScore());
	}
}
