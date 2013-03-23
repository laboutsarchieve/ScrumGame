package View;

import java.util.HashMap;

import Data.TileType;

import com.badlogic.gdx.math.Vector2;

public class TextureRepository {
	HashMap<TileType, SpriteHelper> tileMap = new HashMap<TileType, SpriteHelper>();
	HashMap<SheetType, SpriteSheet> sheetMap = new HashMap<SheetType, SpriteSheet>();
	HashMap<UiElement, SpriteHelper> uiElementMap = new HashMap<UiElement, SpriteHelper>();

	public TextureRepository() {
		addTiles();
		addSheets();
		addUi();
	}

	private void addTiles() {
		addToTileMap(TileType.Grass, "art/AWgrass.png");
		addToTileMap(TileType.Mountain, "art/AWmountain.png");
	}

	private void addSheets() {
		addToSheetMap(SheetType.Monster, "art/Monster.png", new Vector2(0, 0),
				new Vector2(3, 4), 32, 32);
		addToSheetMap(SheetType.Soldier, "art/SheetTwo.png", new Vector2(0, 0), 
				new Vector2(3, 4), 32, 32); 
		addToSheetMap(SheetType.Archer, "art/SheetOne.png", new Vector2(0, 4),
				new Vector2(3, 8), 32, 32);
		addToSheetMap(SheetType.Mage, "art/SheetOne.png", new Vector2(3, 4), 
				new Vector2(6, 8), 32, 32);
		addToSheetMap(SheetType.Villager, "art/SheetTwo.png", new Vector2(9, 4), // This wastes some memory, but its not a very big deal at the moment.
				new Vector2(12, 8), 32, 32);
	}

	private void addUi() {
		addToUiMap(UiElement.MoveCenter, "art/MoveCenter.png");
	}

	private void addToTileMap(TileType type, String fileName) {
		tileMap.put(type, new SpriteHelper(fileName));
	}

	private void addToSheetMap(SheetType type, String fileName, Vector2 start,
			Vector2 end, int frameHeight, int frameWidth) {
		sheetMap.put(type, new SpriteSheet(fileName, start, end, frameHeight,
				frameWidth));
	}

	private void addToUiMap(UiElement type, String fileName) {
		uiElementMap.put(type, new SpriteHelper(fileName));
	}

	public SpriteHelper getTile(TileType type) {
		return tileMap.get(type);
	}

	public SpriteSheet getSpriteSheet(SheetType type) {
		return sheetMap.get(type);
	}
	
	public SpriteHelper getUiElement(UiElement type) {
		return uiElementMap.get(type);
	}

	public void dispose() {
		// TODO
	}

}
