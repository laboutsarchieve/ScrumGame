package View;

import java.util.HashMap;

import Data.TileType;

import com.badlogic.gdx.math.Vector2;

public class TextureRepository {	
	HashMap<TileType, SpriteHelper> tileMap = new HashMap<TileType, SpriteHelper>( );
	HashMap<SheetType, SpriteSheet> sheetMap = new HashMap<SheetType, SpriteSheet>( );
	public TextureRepository() {
		addToTileMap(TileType.Grass, "art/AWgrass.png");
		addToTileMap(TileType.Mountain, "art/AWmountain.png");		
		addToSheetMap(SheetType.Monster, "art/Monster.png", 32, 32);
	}
	private void addToTileMap(TileType type, String fileName) {
		tileMap.put(type, new SpriteHelper(fileName));
	}
	private void addToSheetMap(SheetType type, String fileName, int frameHeight, int frameWidth) {
		sheetMap.put(type, new SpriteSheet(fileName, new Vector2(0,0), new Vector2(3,4),frameHeight, frameWidth));
	}
	public SpriteHelper getTile(TileType type) {
		return tileMap.get(type);
	}
	public SpriteSheet getSpriteSheet(SheetType type) {
		return sheetMap.get(type);
	}
	public void dispose() {	
		//TODO
	}	
}
