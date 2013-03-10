package com.me.mygdxgame;

import jLibNoise.noise.module.Perlin;

public class HeightMap {
	public float[][] values;

	public HeightMap(int sizeX, int sizeY) {
		Perlin noiseMaker = new Perlin();
		values = new float[sizeX][sizeY];

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				values[x][y] = (float) noiseMaker.GetValue(x / 4.0, y / 4.0, 1);
			}
		}
	}

	public int getWidth() {
		return values.length;
	}

	public int getHeight() {
		return values[0].length;
	}
}
