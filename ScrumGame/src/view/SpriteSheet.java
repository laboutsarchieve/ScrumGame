package view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import data.GameSettings;

public class SpriteSheet {
	int numRow;
	int numCol;
	int frameHeight;
	int frameWidth;
	Vector2 start;
	Vector2 end;
	Vector2 sheetSize;

	Texture texture;

	public SpriteSheet(String fileName, Vector2 start, Vector2 end,
			int frameHeight, int frameWidth) {
		this.start = start;
		this.end = end;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		FileHandle file = Gdx.files.internal(fileName);
		texture = new Texture(file);
		texture.setFilter(TextureFilter.Linear,
				TextureFilter.MipMapLinearNearest);

		numRow = (int) (end.y - start.y);
		numCol = (int) (end.x - start.x);
	}

	public Sprite getFrame(int step) {
		int frameNum = step % getNumFrames();

		Vector2 framePoint = new Vector2((frameNum / numCol),
				(frameNum % numCol));
		int left = (int) (start.x + framePoint.x) * frameHeight;
		int down = (int) (start.y + framePoint.y) * frameWidth;
		TextureRegion region = new TextureRegion(texture, down, left,
				frameWidth, frameHeight);

		Sprite sprite = new Sprite(region);

		sprite.setSize(frameHeight / GameSettings.getScreenHeight(), frameWidth
				/ GameSettings.getScreenHeight());
		sprite.setOrigin(0, 0);

		return sprite;
	}

	public Sprite getStepInRow(int row, int step) {
		int frameNum = step % numCol;

		Vector2 framePoint = new Vector2(row, frameNum);
		int left = (int) (start.x + framePoint.x) * frameHeight;
		int down = (int) (start.y + framePoint.y) * frameWidth;
		TextureRegion region = new TextureRegion(texture, left, down,
				frameWidth, frameHeight);

		Sprite sprite = new Sprite(region);

		sprite.setSize(frameHeight / GameSettings.getScreenHeight(), frameWidth
				/ GameSettings.getScreenHeight());
		sprite.setOrigin(0, 0);

		return sprite;
	}
	
	public Sprite getStepInCol(int col, int step) {
		int frameNum = step % numCol;

		Vector2 framePoint = new Vector2(frameNum, col);
		int left = (int) (start.x + framePoint.x) * frameHeight;
		int down = (int) (start.y + framePoint.y) * frameWidth;
		TextureRegion region = new TextureRegion(texture, left, down,
				frameWidth, frameHeight);

		Sprite sprite = new Sprite(region);

		sprite.setSize(frameHeight / GameSettings.getScreenHeight(), frameWidth
				/ GameSettings.getScreenHeight());
		sprite.setOrigin(0, 0);

		return sprite;
	}

	public int getNumRow() {
		return numRow;
	}

	public int getNumCol() {
		return numCol;
	}

	public int getNumFrames() {
		return numRow * numCol;
	}
}
