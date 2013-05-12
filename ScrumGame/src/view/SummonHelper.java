package view;

import com.badlogic.gdx.math.Vector2;

import data.GlobalGameData;
import data.TileType;

import application.MainGame;

public class SummonHelper {
	
	enum SummonMode {
		None,
		Warrior,
		Archer,
		Mage,
		GodAttack,
		Gaben	//Not Implemented
	};
	SummonMode currentSummonMode;
	
	public SummonHelper()
	{
		currentSummonMode=SummonMode.None;
	}
	public void setSummonMode(SummonMode mode)
	{
		currentSummonMode = (currentSummonMode==mode) ? SummonMode.None : mode;
	}
	public SummonMode getSummonMode()
	{
		return currentSummonMode;
	}
	public float getSummonCost(SummonMode select)
	{	
		float cost=-1;
		if(select==SummonMode.None || select == SummonMode.GodAttack){
			cost=0;
		}
		if(select==SummonMode.Warrior)
		{
			cost=10;
		}
		else if(select == SummonMode.Archer)
		{
			cost=20;
		}
		else if(select == SummonMode.Mage)
		{
			cost=30;
		}
		
		return cost;
	}
	public boolean SummonAtPos(Vector2 position)
	{
		if(MainGame.getMap().getTileType(position) != TileType.Grass)
			return false;
		
		boolean summoned=false;
		float ManaCost=0;
		switch(currentSummonMode)
		{
		case Warrior:
			
			ManaCost=10;
			if(GlobalGameData.getPlayer().getMana() < ManaCost)
			{
				System.out.println("Not Enough Mana");
				return false;
			}
			summoned=true;
			MainGame.getEntityManager().addEntity(new data.Soldier(position,data.Facing.Down));
			GlobalGameData.getPlayer().subMana(ManaCost);
			break;
		case Archer:
			
			ManaCost=20;
			if(GlobalGameData.getPlayer().getMana() < ManaCost)
			{
				System.out.println("Not Enough Mana");
				return false;
			}
			summoned=true;
			MainGame.getEntityManager().addEntity(new data.Archer(position, data.Facing.Down));
			GlobalGameData.getPlayer().subMana(ManaCost);
			break;
		case Mage:
			
			ManaCost=30;
			if(GlobalGameData.getPlayer().getMana() < ManaCost)
			{
				System.out.println("Not Enough Mana");
				return false;
			}
			summoned=true;
			MainGame.getEntityManager().addEntity(new data.Mage(position, data.Facing.Down));
			GlobalGameData.getPlayer().subMana(ManaCost);
			break;
			
		default:
			System.out.println("Error: Tried to summon bad unit");
			return false;
		}
		System.out.println(currentSummonMode.toString()+" Summoned at "+(int)position.x + " " + (int)position.y);
		MainGame.getSoundHelper().playSound(Sounds.Summon);
		return summoned;
	}

}
