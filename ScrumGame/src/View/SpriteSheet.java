package View;

import java.awt.Point;

import Data.GameSettings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SpriteSheet {
	int numRow;
	int numCol;
	int frameHeight;
	int frameWidth;
	Vector2 start;
	Vector2 end;
	Vector2 sheetSize;
	
	Texture texture;
	
	public SpriteSheet(String fileName, Vector2 start, Vector2 end, int frameHeight, int frameWidth) {
		texture = new Texture(Gdx.files.internal(fileName));
		texture.setFilter(TextureFilter.Linear, TextureFilter.MipMapLinearNearest);
		this.start = start;
		this.end = end;
		numRow = (int)(end.y - start.y);
		numCol = (int)(end.x - start.x);
	}
	
	public Sprite getFrame(int frameNum) {
		Vector2 framePoint = new Vector2((frameNum / numRow), (frameNum % numCol));
		int left = (int)(start.x + framePoint.x) * frameHeight; 		
		int down = (int)(start.y + framePoint.y) * frameWidth;
		TextureRegion region = new TextureRegion(texture, left, down, frameWidth, frameHeight);
		
		Sprite sprite = new Sprite(region);
		
		sprite.setSize(frameHeight/GameSettings.getScreenHeight( ), frameWidth/GameSettings.getScreenWidth( ));
		sprite.setOrigin(0, 0);
		
		return sprite;
	}

	Sprite spriteSheet;
}
