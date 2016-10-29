package application;


import java.awt.image.BufferedImage;

public class CustomBufferedImageStorage {

	
	private String filePath;
	private BufferedImage image;
	
	public CustomBufferedImageStorage(BufferedImage image, String filePath)
	{
		this.filePath=filePath;
		this.image=image;
	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
}
