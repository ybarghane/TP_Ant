/*
 * @LICENSE@
 */
package org.ubo.elementz.model.events;

/**
 * Evènement utilisé lorsque le score est modifié.
 * 
 * @author Guillaume Bourel, guillaume.bourel@univ-brest.fr
 */
public class ScoreChangedEvt {

	/** Le nouveau score. */
	private final int _newScore;

	/**
	 * Ctor.
	 * 
	 * @param newScore
	 *            Le nouveau score.
	 */
	public ScoreChangedEvt(int newScore) {
		_newScore = newScore;
	}

	/** Le nouveau score. */
	public int getScore() {
		return _newScore;
	}
}
