package org.engineFRP2.core.Stuff;

import org.FRPengine.core.Transform;

import java.util.ArrayList;

/**
 * Created by TekMaTek on 01/02/2015.
 */
public class GameObject {

	private static GameObject root = new GameObject( );

	public Transform transform;

	private ArrayList< GameObject > children;
	private ArrayList< GameComponent > components;
	private GameObject parent;

	public GameObject() {
		children = new ArrayList<>( );
		components = new ArrayList<>( );
		transform = new Transform( );
	}

	public static GameObject getRoot( ) {
		return GameObject.root;
	}

	public void addChild( GameObject child ) {
		child.parent = this;
		children.add( child );
	}

	public void addComponent( GameComponent component ) {
		component.transform = transform;//Leave a reference back to transform.
		component.componentObject = component;
		components.add( component );
	}

	public < T extends GameComponent > T getComponent( Class< T > type ) {
		for( GameComponent component : components ) {
			if( component.getClass( ).equals( type ) ) {
				return ( T ) component;
			}
		}
		return null;
	}

	public < T extends GameComponent > T getComponentUpwards( Class< T > type ) {
		T foundComponent = getComponent( type );
		if( foundComponent != null ) {
			return foundComponent;
		} else if( parent != null ) {
			return parent.getComponentUpwards( type );
		}
		return null;
	}


	public void input( ) {
		for( GameComponent component : components ) {
			component.input( );
		}

		for( GameObject child : children ) {
			child.input( );
		}
	}

	public void update( ) {
		for( GameComponent component : components ) {
			component.update( );
		}

		for( GameObject child : children ) {
			child.update( );
		}
	}
}