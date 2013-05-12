package data;

import java.util.LinkedList;

import application.MainGame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import jLibNoise.noise.module.Perlin;

public class Map {
	private float[][] values;
	private LinkedList<Village> villages = new LinkedList<Village>( );
	private LinkedList<Forest> forests = new LinkedList<Forest>( );

	public Map( ) {
		values = new float[1][1];
	}
	
	public static Map randomMap(int sizeX, int sizeY) {
		Map map = new Map( );
		Perlin noiseMaker = new Perlin();
		map.values = new float[sizeX][sizeY];

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				map.values[x][y] = (float) noiseMaker.GetValue(x / 8.0, y / 8.0, 1);
			}
		}
		
		int numVillages =(int)( Math.sqrt(sizeX*sizeY) / 5.0);
		
		for(int k = 0; k < numVillages; k++) {
			Vector2 villagePos = map.getRandomPosWithTile(TileType.Grass);
			Village vill = new Village(villagePos);
			map.getVillages( ).add(vill);
			MainGame.getEntityManager().addSpawnTile((SpawnTile)vill);
		}
		for(int k = 0; k < 2*numVillages/3.0; k++) {
			Vector2 forestPos = map.getRandomPosWithTile(TileType.Grass);
			Forest forest = new Forest(forestPos);
			map.getForests( ).add(forest);
			MainGame.getEntityManager().addSpawnTile((SpawnTile)forest);
		}
		
		return map;
	}
	public TileType getTileType(Vector2 position) {
		return getTileType((int)position.x, (int)position.y);
	}
	public TileType getTileType(int x, int y) {
		if(!contains(x,y))
			return TileType.Grass;
		
		float height = values[x][y];
		
		if (height < 0.4)
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

	public LinkedList<Village> getVillages() {
		return villages;
	}

	public LinkedList<Forest> getForests() {
		return forests;
	}
}
