package com.me.mygdxgame;

import com.badlogic.gdx.math.Vector2;

public class GameTools {
	public static Vector2 clamp(Vector2 vector, Vector2 min, Vector2 max) {
		if(min.x > max.x || min.y > max.y)
			throw new GameException("max vector must have both elements larger than min Vector");
			
		Vector2 clampedVector = new Vector2(vector);
		
		clampedVector.x = Math.max(clampedVector.x, min.x);
		clampedVector.y = Math.max(clampedVector.y, min.y);
		
		clampedVector.x = Math.min(clampedVector.x, max.x);
		clampedVector.y = Math.min(clampedVector.y, max.y);
		
		return clampedVector;
	}
}
