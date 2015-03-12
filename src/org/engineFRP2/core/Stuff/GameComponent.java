package org.engineFRP2.core.Stuff;

import org.FRPengine.core.Transform;

/**
 * Created by TekMaTek on 01/02/2015.
 */
public abstract class GameComponent< T > {

	public Transform transform;
	public GameObject gameObject;
	public T componentObject;

	public void init( ) {

	}

	public void input( ) {

	}

	public void update( ) {

	}

	public T getComponent( ) {
		return componentObject;
	}
}
