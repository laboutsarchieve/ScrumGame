package data;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import jLibNoise.noise.module.Perlin;

public class HeightMap {
	private float[][] values;

	public HeightMap( ) {
		values = new float[1][1];
	}
	
	public static HeightMap randomMap(int sizeX, int sizeY) {
		HeightMap map = new HeightMap( );
		Perlin noiseMaker = new Perlin();
		map.values = new float[sizeX][sizeY];

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				map.values[x][y] = (float) noiseMaker.GetValue(x / 8.0, y / 8.0, 1);
			}
		}
		
		return map;
	}
	public TileType getTileType(Vector2 position) {
		return getTileType((int)position.x, (int)position.y);
	}
	public TileType getTileType(int x, int y) {
		float height = values[x][y];
		
		if (height < 0.2)
			return TileType.Grass;
		else
			return TileType.Mountain;
	}
	public Vector2 getRandomPos( ) {
		return new Vector2( MathUtils.random(0, getWidth()-1), MathUtils.random(0, getHeight()-1));
	}
	public Vector2 getRandomPosWithTile(TileType target) {
		TileType tile = TileType.Error;
		Vector2 position = new Vector2(0,0);
		
		while(tile != target) {
			position = getRandomPos( );
			tile = getTileType(position);
		}
		
		return position;
	}
	public boolean contains(Vector2 position) {
		return contains((int)position.x, (int)position.y);			
	}
	public boolean contains(int x, int y) {
		return x > -1 && y > -1 && x < getWidth( ) && y < getHeight( );			
	}

	public int getWidth() {
		return values.length;
	}

	public int getHeight() {
		return values[0].length;
	}
}
