package view;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;

import data.EntityType;

public class SoundHelper {
	Music stream;
	Music soundEffect;
	HashMap<Sounds, Sound> SoundMap;
	HashMap<EntityType, Sound> EntityFX;
	Sound SummonEffect;
	public SoundHelper()
	{
		//stream = Gdx.audio.newMusic(Gdx.files.internal("sound/Gaben.wav"));
		//stream.setLooping(true);
		//stream.play();
		//stream = Gdx.audio.newMusic(Gdx.files.internal("sound/BackgroundMusic.mp3"));
		stream = Gdx.audio.newMusic(Gdx.files.internal("sound/Gaben.wav"));
		stream.setLooping(true);
		//stream.play();
		SoundMap=new HashMap<Sounds, Sound>();
		EntityFX=new HashMap<EntityType, Sound>();
		
		SoundMap.put(Sounds.Summon, Gdx.audio.newSound(Gdx.files.internal("sound/Summon.wav")));
		SoundMap.put(Sounds.GameOver, Gdx.audio.newSound(Gdx.files.internal("sound/GameOver.wav")));
		//SoundMap.put(Sounds.LevelUp, Gdx.audio.newSound(Gdx.files.internal("sound/LevelUp.wav"))); //NYI
		//SoundMap.put(Sounds.Help, Gdx.audio.newSound(Gdx.files.internal("sound/Help.wav"))); //NYI
		SoundMap.put(Sounds.BadSelection, Gdx.audio.newSound(Gdx.files.internal("sound/BadSelection.wav")));
		
		EntityFX.put(EntityType.Soldier, Gdx.audio.newSound(Gdx.files.internal("sound/sword_clang.wav")));
		EntityFX.put(EntityType.Archer, Gdx.audio.newSound(Gdx.files.internal("sound/archer_shot.wav")));
		
		
	}
	
	public void playSound(Sounds type)
	{
		SoundMap.get(type).play();
	}
	
	public void playSound(EntityType type)
	{
		EntityFX.get(type).play();
	}
	
}
