package Data;

import View.AnimatedSprite;
import View.SheetType;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.mygdxgame.MainGame;

public class Monster extends Entity {
	private float BETWEEN_MOVES = 1.0f;
	private float tillNextMove = 0;
	//TODO: This should managed in a separate but parallel class
	private AnimatedSprite animations;
	
	public Monster(Vector2 position, Facing facing) {
		super(position, facing);
		animations = new AnimatedSprite(MainGame.getTextureRepo().getSpriteSheet(SheetType.Monster));
	}
	
	@Override
	public void update(float deltaTime) {
		animations.update(deltaTime);
		tillNextMove -= deltaTime;
		
		while(tillNextMove < 0) {
			move();
			tillNextMove += BETWEEN_MOVES;
		}
	}
	
	private void move( ) {
		Facing randomFacing = Facing.getRandom( );
		Vector2 oldPosition = position.cpy( );
		switch(randomFacing) {
			case Down:
				position.y--;
				animations.setCurrAnimation(0);
				break;
			case Left:
				position.x--;
				animations.setCurrAnimation(1);
				break;
			case Right:
				position.x++;
				animations.setCurrAnimation(2);
				break;
			case Up:
				position.y++;
				animations.setCurrAnimation(3);
				break;
			default:
				break;
		}		
		
		HeightMap map = MainGame.getMap();
		if(!map.contains(position) ||
			map.getTileType(position) != TileType.Grass) {  
			position = oldPosition;
		}
		
		
	}
	
	public Sprite getSprite( ) {
		return animations.getSprite();
	}
}
