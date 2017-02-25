package testgame.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import testgame.Handler;

public class BGMplayer
{
	public Handler handler;
	
	Clip clip, song, paused;
	
	public BGMplayer(Handler handler)
	{
		this.handler = handler;
		
		handler.setBgmPlayer(this);
	}

	public void playSound(String path) 
	{
	    try
	    {
	    	File yourFile = new File(BGMplayer.class.getResource(path).toURI());
	        AudioInputStream stream;
	        AudioFormat format;
	        DataLine.Info info;

	        stream = AudioSystem.getAudioInputStream(yourFile);
	        format = stream.getFormat();
	        info = new DataLine.Info(Clip.class, format);
	        clip = (Clip) AudioSystem.getLine(info);
	        clip.open(stream);
	        clip.start();
	        clip.flush();
	    }
	    catch(Exception ex)
	    {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
	public void playMusic(String path)
	{
		
		try
		{
			File yourFile = new File(BGMplayer.class.getResource(path).toURI());
	        AudioInputStream stream;
	        AudioFormat format;
	        DataLine.Info info;

	        stream = AudioSystem.getAudioInputStream(yourFile);
	        format = stream.getFormat();
	        info = new DataLine.Info(Clip.class, format);
	        song = (Clip) AudioSystem.getLine(info);
	        song.open(stream);
	        song.loop(Clip.LOOP_CONTINUOUSLY);
	        song.start();
		}
		catch(Exception error)
		{
			System.out.println("File not found.");
			error.printStackTrace();
		}
		
	}
	
	public void stopMusic()
	{
		song.stop();
		song.flush();
		song.close();
	}
	
	public void pauseMusic()
	{
		song.stop();
		paused = song;
	}
	
	public void resumeMusic()
	{
		song = paused;
		song.start();
	}
}
