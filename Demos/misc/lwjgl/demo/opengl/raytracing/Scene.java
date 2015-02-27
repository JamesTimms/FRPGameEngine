/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://misc.lwjgl.org/license.php
 */
package misc.lwjgl.demo.opengl.raytracing;

import misc.lwjgl.demo.util.Vector3f;

public interface Scene {

	Vector3f[] boxes = { new Vector3f(-5.0f, -0.1f, -5.0f), new Vector3f(5.0f, 0.0f, 5.0f),
			new Vector3f(-0.5f, 0.0f, -0.5f), new Vector3f(0.5f, 1.0f, 0.5f), new Vector3f(-5.1f, 0.0f, -5.0f),
			new Vector3f(-5.0f, 5.0f, 5.0f), new Vector3f(5.0f, 0.0f, -5.0f), new Vector3f(5.1f, 5.0f, 5.0f),
			new Vector3f(-5.0f, 0.0f, -5.1f), new Vector3f(5.0f, 5.0f, -5.0f), new Vector3f(-5.0f, 0.0f, 5.0f),
			new Vector3f(5.0f, 5.0f, 5.1f), new Vector3f(-5.0f, 5.0f, -5.0f), new Vector3f(5.0f, 5.1f, 5.0f),
			new Vector3f(-2.5f, 0.0f, -1.5f), new Vector3f(-1.5f, 1.0f, -0.5f), new Vector3f(-2.5f, 0.0f, 1.5f),
			new Vector3f(-1.5f, 1.0f, 2.5f), new Vector3f(1.5f, 0.0f, 1.5f), new Vector3f(2.5f, 1.0f, 2.5f),
			new Vector3f(1.5f, 0.0f, -2.5f), new Vector3f(2.5f, 1.0f, -1.5f) };

}
