package View;

import java.awt.Point;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

public class TextureRepository {	
	HashMap<TextureType, SpriteHelper> tileMap = new HashMap<TextureType, SpriteHelper>( );
	HashMap<SheetType, SpriteSheet> sheetMap = new HashMap<SheetType, SpriteSheet>( );
	public TextureRepository() {
		addToTileMap(TextureType.grass, "art/AWgrass.png");
		addToTileMap(TextureType.mountain, "art/AWmountain.png");		
		addToSheetMap(SheetType.Monster, "art/Monster.png", 32, 32);
	}
	private void addToTileMap(TextureType type, String fileName) {
		tileMap.put(type, new SpriteHelper(fileName));
	}
	private void addToSheetMap(SheetType type, String fileName, int frameHeight, int frameWidth) {
		//sheetMap.put(type, new SpriteSheet(fileName, new Vector2(0,0), new Vector2(3,4),frameHeight, frameWidth));
	}
	public SpriteHelper getTile(TextureType type) {
		return tileMap.get(type);
	}
	public void dispose() {	
		
	}
}
