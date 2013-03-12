package com.me.mygdxgame;

import jLibNoise.noise.module.Perlin;

public class HeightMap {
	public float[][] values;

	public HeightMap( ) {
		values = new float[1][1];
	}
	
	public static HeightMap randomMap(int sizeX, int sizeY) {
		HeightMap map = new HeightMap( );
		Perlin noiseMaker = new Perlin();
		map.values = new float[sizeX][sizeY];

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				map.values[x][y] = (float) noiseMaker.GetValue(x / 4.0, y / 4.0, 1);
			}
		}
		
		return map;
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
