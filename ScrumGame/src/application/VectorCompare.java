package application;

import java.util.Comparator;

import com.badlogic.gdx.math.Vector2;

public class VectorCompare implements Comparator<Vector2>{

	@Override
	public int compare(Vector2 firstVector, Vector2 secondVector) {		
		if(firstVector.x == secondVector.x && firstVector.y == secondVector.y)
			return 0;
		else
			return 1;
	}

}

