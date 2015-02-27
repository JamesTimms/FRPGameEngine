/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://misc.lwjgl.org/license.php
 */
package misc.lwjgl.system.windows;

import org.lwjgl.system.windows.WindowsLibrary;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@Test
public class WindowsPlatformTest {

	public void testHINSTANCE() {
		assertTrue(WindowsLibrary.HINSTANCE != 0);
	}

}