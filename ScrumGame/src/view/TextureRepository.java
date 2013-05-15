package view;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import data.TileType;

public class TextureRepository {
	HashMap<TileType, SpriteHelper> tileMap = new HashMap<TileType, SpriteHelper>();
	HashMap<SheetType, SpriteSheet> sheetMap = new HashMap<SheetType, SpriteSheet>();
	HashMap<UiElement, SpriteSheet> uiElementMap = new HashMap<UiElement, SpriteSheet>();
	HashMap<ScreenOverlay, SpriteSheet> overlays = new HashMap<ScreenOverlay, SpriteSheet>();
	HashMap<ObjectType, SpriteHelper> objectMap = new HashMap<ObjectType, SpriteHelper>();
	HashMap<MainMenuElement, SpriteSheet> mainMenuMap = new HashMap<MainMenuElement, SpriteSheet>();

	public TextureRepository() {
		addTiles();
		addSheets();
		addUi();
		addOverlays();
		addObjects( );
		addMainMenuElements();
	}

	private void addTiles() {
		addToTileMap(TileType.Grass, "art/AWgrass.png");
		addToTileMap(TileType.Mountain, "art/AWmountain.png");
		addToTileMap(TileType.Village, "art/City.png");
		addToTileMap(TileType.Forest, "art/forest.png");
		//addToTileMap(TileType.Forest, "art/newell.png");
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
		addToUiMap(UiElement.ManaBar, "art/UiElements.png", new Vector2(0,3), new Vector2(2,3), 64, 64);
		addToUiMap(UiElement.Circles, "art/UiElements.png", new Vector2(3,0), new Vector2(6,1), 64, 64);//in order: base, OK status, critical status(low villagers)
		addToUiMap(UiElement.Buttons, "art/UiElements.png", new Vector2(0,5), new Vector2( 5, 6), 64, 64);
		addToUiMap(UiElement.Health, "art/UiElements.png", new Vector2(4,8), new Vector2(5,8), 32, 32);
		addToUiMap(UiElement.HelpBubble, "art/UiElements.png", new Vector2(4,9), new Vector2(5, 9), 32, 32);
		addToUiMap(UiElement.HelpNotifyVertical, "art/UiElements.png", new Vector2(0,3), new Vector2(1,3), 128, 128);
		addToUiMap(UiElement.HelpNotifyHorizontal, "art/UiElements.png", new Vector2(2,3), new Vector2(3,3), 128, 128);
		addToUiMap(UiElement.ManaChunk, "art/UiElements.png", new Vector2(7,8), new Vector2(7,8), 32, 32);
	}
	
	private void addOverlays(){
		addToOverlayMap(ScreenOverlay.GameOver, "art/Overlays.png", new Vector2(0,0), new Vector2(0,0), 256, 256 );
		addToOverlayMap(ScreenOverlay.LevelUp, "art/Overlays.png", new Vector2(1,0), new Vector2(1,0), 256, 256 );
		//TODO: Create and add overlays for: level up, main menu
	}
	
	private void addMainMenuElements(){
		addToMainMenuMap(MainMenuElement.Buttons, "art/MainMenuElements.png", new Vector2(1,0), new Vector2(1,0), 64,64);
		addToMainMenuMap(MainMenuElement.Background, "art/MainMenuElements.png", new Vector2(0,0), new Vector2(0,0), 64,64);
		
	}
	
	private void addObjects() {
		addToObjectMap(ObjectType.Arrow, "art/arrow.png");
		addToObjectMap(ObjectType.Fireball, "art/fireball.png");
	}
	
	private void addToMainMenuMap(MainMenuElement type, String fileName, Vector2 start, Vector2 end, int frameHeight, int frameWidth){
		mainMenuMap.put(type, new SpriteSheet(fileName, start, end, frameHeight, frameWidth));
	}
	
	private void addToOverlayMap(ScreenOverlay type, String fileName, Vector2 start, Vector2 end, int frameHeight, int frameWidth)
	{
		overlays.put(type, new SpriteSheet(fileName, start, end, frameHeight, frameWidth));
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
	
	private void addToObjectMap(ObjectType type, String fileName) {
		objectMap.put(type, new SpriteHelper(fileName));
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
	
	public SpriteSheet getOverlays(ScreenOverlay type){
		return overlays.get(type);
	}
	
	public SpriteSheet getMainMenuElement(MainMenuElement type){
		return mainMenuMap.get(type);
	}

	public void dispose() {
		// TODO
	}

	public SpriteHelper getObject(ObjectType type) {
		return objectMap.get(type);
	}

}
