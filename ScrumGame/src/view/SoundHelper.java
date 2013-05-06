package view;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;

public class SoundHelper {
	Music stream;
	Music soundEffect;
	HashMap<Sounds, Sound> SoundMap;
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
		
		SoundMap.put(Sounds.Summon, Gdx.audio.newSound(Gdx.files.internal("sound/Summon.wav")));
		SoundMap.put(Sounds.GameOver, Gdx.audio.newSound(Gdx.files.internal("sound/GameOver.wav")));
		//SoundMap.put(Sounds.LevelUp, Gdx.audio.newSound(Gdx.files.internal("sound/LevelUp.wav"))); //NYI
		//SoundMap.put(Sounds.Help, Gdx.audio.newSound(Gdx.files.internal("sound/Help.wav"))); //NYI
		SoundMap.put(Sounds.Gaben, Gdx.audio.newSound(Gdx.files.internal("sound/Gaben.wav")));
		SoundMap.put(Sounds.Mage, Gdx.audio.newSound(Gdx.files.internal("sound/Mage_Summon.wav")));
	}
	
	/*public void playSound(Sounds type)	//Unfortunate in that this won't work on Android :(
	{
        System.out.println("Playing Sound: "+type.toString());
        try {
        AudioInputStream stream;
        AudioFormat format;
        DataLine.Info info;
        Clip clip;

        stream = AudioSystem.getAudioInputStream(getClass().getResource("/sound/"+type.toString()+".wav"));
        format = stream.getFormat();
        info = new DataLine.Info(Clip.class, format);
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(stream);
        clip.start();
        }
        catch (Exception e) {
            //Error in GabePlay. not important. catch and continue 
            System.out.println("Gaberror: "+e);
        }
	}*/
	public void playSound(Sounds type)
	{
		
		SoundMap.get(type).play();
		
	}
	
}
