package org.FRPengine.core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * Created by TekMaTek on 22/02/2015.
 */
public class FRPMouse {
    public static final int NUM_MOUSEBUTTONS = 5;

    private static boolean[] m_lastMouse = new boolean[ NUM_MOUSEBUTTONS ];

    public static void Update( ) {
        for( int i = 0; i < NUM_MOUSEBUTTONS; i++ )
            m_lastMouse[ i ] = GetMouse( i );
    }

    public static boolean GetKey( int keyCode ) {
        return Keyboard.isKeyDown(keyCode);
    }

    public static boolean GetMouse( int mouseButton ) {
        return Mouse.isButtonDown(mouseButton);
    }
//
//    public static boolean GetMouseDown( int mouseButton ) {
//        return GetMouse( mouseButton ) && !m_lastMouse[ mouseButton ];
//    }
//
//    public static boolean GetMouseUp( int mouseButton ) {
//        return !GetMouse( mouseButton ) && m_lastMouse[ mouseButton ];
//    }
//
//    public static Vector2f GetMousePosition( ) {
//        return new Vector2f( Mouse.getX( ), Mouse.getY( ) );
//    }
//
//    public static void SetMousePosition( Vector2f pos ) {
//        Mouse.setCursorPosition( ( int ) pos.getX( ), ( int ) pos.getY( ) );
//    }
//
//    public static void SetCursor( boolean enabled ) {
//        Mouse.setGrabbed( !enabled );
//    }
}
