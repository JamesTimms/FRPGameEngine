/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://misc.lwjgl.org/license.php
 */
package misc.lwjgl.system.windows;

import org.lwjgl.system.DynamicLinkLibrary;
import org.lwjgl.system.windows.WindowsLibrary;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class LibraryTest {

	public void testLifecycle() {
		DynamicLinkLibrary lib = new WindowsLibrary("opengl32.dll");
		assertEquals(lib.getName(), "opengl32.dll");
		lib.release();
	}

	public void testGetProcAddress() {
		DynamicLinkLibrary lib = new WindowsLibrary("opengl32.dll");
		assertTrue(lib.getFunctionAddress("wglGetProcAddress") != 0L);
		lib.release();
	}

}