package org.ubo.elementz.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ElementZ_ModelTest {

	@Test
	public void testInitMatrixb() {
		ElementZ_Model model = new ElementZ_Model();
		model.initMatrixb();
		
		assertEquals(0, model.somme());
	}

}
