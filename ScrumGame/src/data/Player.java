package data;

public class Player {
	float mana;
	float MaxMana;
	float manaPercent;
	
	public Player()
	{
		this.mana=100;
		this.MaxMana=100;
	}
	
	public Player(float mana, float MaxMana)
	{
		this.mana=mana;
		this.MaxMana=MaxMana;
		setManaPercent();
	}
	
	public float getMana() {
		return mana;
	}

	public float getMaxMana()
	{
		return MaxMana;
	}
	public void setMana(float mana) {
		this.mana = mana;
		setManaPercent();
	}
	public void addMana(float mana)
	{
		this.mana+=mana;
		if(this.mana>MaxMana)
		{
			this.mana=MaxMana;
		}
		setManaPercent();
	}
	public void subMana(float mana)
	{
		this.mana -=mana;
		setManaPercent();
	}
	
	public void setMaxMana(float mana)
	{
		this.MaxMana=mana;
		setManaPercent();
	}
	
	private void setManaPercent()
	{
		this.manaPercent=this.mana/this.MaxMana;
	}
	
	public float getManaPercent()
	{
		return manaPercent;
	}
}
