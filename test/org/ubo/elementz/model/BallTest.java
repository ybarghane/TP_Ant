/*
 * 
 */
package org.ubo.elementz.model;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.ubo.elementz.model.Ball;
import org.ubo.elementz.model.ElementZ_Model;
import org.ubo.elementz.ui.MyPanel;

/**
 * Test class for {@link Ball} model class.
 * 
 * @author Guillaume Bourel, guillaume.bourel@univ-brest.fr
 */
public class BallTest {
	
	/** Copy constructor test. */
	@Test
	public void testCopyCtor(){
		ElementZ_Model model = new ElementZ_Model();
		MyPanel panel = new MyPanel(model);
		Ball ball = new Ball(panel, 10, 10, 8, 2, 20, 20);
		
		Ball copy = new Ball(ball);
		
		assertSame(panel, copy.getPanel());
		assertEquals(10, copy.getX());
		assertEquals(10, copy.getY());
		assertEquals(8, copy.getSize());
		assertEquals(2, copy.getValue());
		assertEquals(20, copy.getPosX());
		assertEquals(20, copy.getPosY());
		assertEquals(ball.getColor(), copy.getColor());
	}
	
	/**
	 * Test {@link Ball#isInside(int, int)} method.
	 */
	@Test
	public void testIsInside() {
		Ball ball = new Ball(null, 10, 10, 8, 2, 20, 20);
		assertEquals(true, ball.isInside(15, 15));
		assertEquals(false, ball.isInside(18, 18));
	}
}
