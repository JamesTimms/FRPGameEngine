/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://misc.lwjgl.org/license.php
 */
package misc.lwjgl.system.windows;

import org.lwjgl.system.windows.EnumObjectsCallback;
import org.testng.annotations.Test;

import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.windows.WinGDI.EnumObjects;
import static org.lwjgl.system.windows.WinGDI.OBJ_BRUSH;
import static org.lwjgl.system.windows.WinUser.GetDC;
import static org.testng.Assert.assertTrue;

@Test
public class WinGDITest {

	public void testEnumObjects() {
		long dc = GetDC(0);
		EnumObjects(dc, OBJ_BRUSH, new EnumObjectsCallback() {
			@Override
			public int invoke(long logObject, long data) {
				assertTrue(logObject != 0L);
				return 1;
			}
		}, NULL);
	}

}