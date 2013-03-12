package com.me.mygdxgame;

import com.badlogic.gdx.math.Vector2;

public class GameTools {
	public static void clamp(Vector2 vector, Vector2 min, Vector2 max) {
		if(min.x > max.x || min.y > max.y)
			throw new GameException("max vector must have both elements larger than min Vector");
		
		if( vector.x < min.x )
			vector.x = min.x;
		
		if( vector.y < min.y )
			vector.y = min.y;
		
		if( vector.x > max.x )
			vector.x = max.x;
		
		if( vector.y > max.y )
			vector.y = max.y;	}
}
