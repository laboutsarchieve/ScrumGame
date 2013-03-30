package view;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimatedSprite {
	private final float DEFAULT_LENGTH = 0.3f;
	private float tillNextFrame = 0;
	private float frameLength[];
	private int numFrames[];
	private int currAnimation;
	private int currFrame;
	private SpriteSheet sheet;
	
	public AnimatedSprite(SpriteSheet sheet) {
		this.sheet = sheet;
		frameLength = new float[sheet.getNumRow( )];
		numFrames = new int[sheet.getNumRow( )];
		
		for(int index = 0; index < frameLength.length; index++) {
			frameLength[index] = DEFAULT_LENGTH;
			numFrames[index] = sheet.getNumCol();
		}
	}
	
	public void update(float deltaTime) {
		tillNextFrame -= deltaTime;
		
		while(tillNextFrame < 0) { // A loop to handle more than framelength time occurring
			currFrame = (currFrame + 1) % numFrames[currAnimation];
			tillNextFrame += frameLength[currAnimation];
		}
	}
	
	public Sprite getSprite( ) {
		return sheet.getStepInCol(currAnimation, currFrame);
	}
	
	public void setFrameLength(int animationNumber, float time) {
		frameLength[animationNumber] = time;
	}
	
	public int getCurrAnimation() {
		return currAnimation;
	}

	public void setCurrAnimation(int currAnimation) {
		this.currAnimation = Math.min(sheet.getNumCol(), currAnimation);
	}
}
