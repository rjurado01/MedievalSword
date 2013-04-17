package com.modules.map;

import com.mygdxgame.Constants;

public class MapController {

	/* EVENTS INFO */
	static Object objectEvent = null;
	static int typeEvent = -1;
	
	static void addEvent( int type, Object receiver ) {
		if( typeEvent == Constants.NONE ) {
			typeEvent = type;
			objectEvent = receiver;
		}
	}
}
