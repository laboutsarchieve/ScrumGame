package data;

import application.GameException;

import com.badlogic.gdx.math.MathUtils;

public enum Facing {
	Up,
	Down,
	Left,
	Right;
	
	public static Facing getRandom( ) {
		int facing = MathUtils.random(0, 3);
		switch(facing) {
			case 0: {
				return Up;
			}
			case 1: {
				return Down;
			}
			case 2: {
				return Left;
			}
			case 3: {
				return Right;
			}
			default: {
				throw new GameException("Facing generated a number out of range somehow");
			}
		}
	}
	
	public static Facing getOpposite(Facing f) {
		Facing opp;
		switch(f){
		case Up:
			opp = Down;
			break;
		case Down:
			opp = Up;
			break;
		case Left:
			opp = Right;
			break;
		case Right:
			opp = Left;
			break;
		default:
				throw new GameException("Facing: something bad happened");
		}
		return opp;
	}
}
