package application;


import java.awt.image.BufferedImage;

import javafx.scene.media.Media;

public class CustomSoundStorage {

	
	private String filePath;
	private Media sound;
	
	public CustomSoundStorage(Media sound, String filePath)
	{
		this.filePath=filePath;
		this.sound=sound;
	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	public Media getSound()
	{
		return sound;
	}
	
}
