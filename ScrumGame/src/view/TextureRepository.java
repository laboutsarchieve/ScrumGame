package view;

import java.util.HashMap;


import com.badlogic.gdx.math.Vector2;

import data.TileType;

public class TextureRepository {
	HashMap<TileType, SpriteHelper> tileMap = new HashMap<TileType, SpriteHelper>();
	HashMap<SheetType, SpriteSheet> sheetMap = new HashMap<SheetType, SpriteSheet>();
	HashMap<UiElement, SpriteSheet> uiElementMap = new HashMap<UiElement, SpriteSheet>();

	public TextureRepository() {
		addTiles();
		addSheets();
		addUi();
	}

	private void addTiles() {
		addToTileMap(TileType.Grass, "art/AWgrass.png");
		addToTileMap(TileType.Mountain, "art/AWmountain.png");
		addToTileMap(TileType.Village, "art/City.png");
		addToTileMap(TileType.Forest, "art/forest.png");
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
		addToUiMap(UiElement.MoveCenter, "art/UiElements.png", new Vector2(0,0), new Vector2(3,3), 64, 64);//cursor
		addToUiMap(UiElement.Numbers, "art/UiElements.png", new Vector2(6,2), new Vector2(6,6), 32, 32);
		addToUiMap(UiElement.ManaBar, "art/UiElements.png", new Vector2(0,3), new Vector2(2,3), 64, 64);
		addToUiMap(UiElement.Circles, "art/UiElements.png", new Vector2(3,0), new Vector2(3,4), 64, 64);//in order: base, OK status, critical status
		addToUiMap(UiElement.Buttons, "art/UiElements.png", new Vector2(0,5), new Vector2( 4, 4), 64, 64);
		addToUiMap(UiElement.Health, "art/UiElements.png", new Vector2(4,8), new Vector2(5,8), 32, 32);
	}

	private void addToTileMap(TileType type, String fileName) {
		tileMap.put(type, new SpriteHelper(fileName));
	}

	private void addToSheetMap(SheetType type, String fileName, Vector2 start,
			Vector2 end, int frameHeight, int frameWidth) {
		sheetMap.put(type, new SpriteSheet(fileName, start, end, frameHeight,
				frameWidth));
	}

	private void addToUiMap(UiElement type, String fileName, Vector2 start, Vector2 end, int frameHeight, int frameWidth) {
		uiElementMap.put(type, new SpriteSheet(fileName, start, end, frameHeight, frameWidth));
	}

	public SpriteHelper getTile(TileType type) {
		return tileMap.get(type);
	}

	public SpriteSheet getSpriteSheet(SheetType type) {
		return sheetMap.get(type);
	}
	
	public SpriteSheet getUiElement(UiElement type) {
		return uiElementMap.get(type);
	}

	public void dispose() {
		// TODO
	}

}
