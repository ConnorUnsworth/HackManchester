package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class IOController extends Application {

	private Stage stage;

	private File outputfile;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;
		Group root = new Group();
		getBufferedImage();
	}

	public CustomBufferedImageStorage getBufferedImage()
	{
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilterJpg = new FileChooser.ExtensionFilter("JPG (*.jpg)(*.png)", "*.jpg","*.png");
		fileChooser.getExtensionFilters().add(extFilterJpg);
		File file = fileChooser.showOpenDialog(stage);

		try{
			BufferedImage bufferedImage = ImageIO.read(file);
			CustomBufferedImageStorage imageStorage = new CustomBufferedImageStorage(bufferedImage, file.getAbsolutePath());
			return imageStorage;
		}
		catch(Exception ex)
		{
			return null;

		}

	}

	public File getConvertedFile()
	{
		return outputfile;
	}




	public void tempSaveNewImage(BufferedImage image)
	{
//		 FileChooser fileChooser = new FileChooser();
//         fileChooser.setTitle("Save Image");
//     	FileChooser.ExtensionFilter extFilterJpg = new FileChooser.ExtensionFilter("JPG (*.jpg)(*.png)", "*.jpg","*.png");
//		fileChooser.getExtensionFilters().add(extFilterJpg);
//         File file = fileChooser.showSaveDialog(stage);
//         
//         if (file != null) {
//             try {
//                 ImageIO.write(image, "png", file);
//
//             } catch (IOException ex) {
//                 System.out.println(ex.getMessage());
//             }
//         }
		
		
		
		try {
			// retrieve image
			outputfile = new File("c:\\image\\saved.png");
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {

		}
	}
	
	public void saveImgToPath(Image image)
	{
		File tempFile = outputfile;
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(tempFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Save Image");
  	FileChooser.ExtensionFilter extFilterJpg = new FileChooser.ExtensionFilter("JPG (*.jpg)(*.png)", "*.jpg","*.png");
		fileChooser.getExtensionFilters().add(extFilterJpg);
      File file = fileChooser.showSaveDialog(stage);
      
      if (file != null) {
          try {
              ImageIO.write(bufferedImage, "png", file);

          } catch (IOException ex) {
              System.out.println(ex.getMessage());
          }
      }
	}
	

	public Image getNewImage()
	{


		try{
			File resizedFile = new File("c:\\image\\saved.png");

			Image image = new Image(resizedFile.toURI().toString());

			return image;
		}
		catch(Exception ex)
		{
			return null;

		}
	}




	public CustomSoundStorage getSound()
	{
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilterAud = new FileChooser.ExtensionFilter("JPG (*.jpg)(*.png)", "*.jpg","*.png");
		fileChooser.getExtensionFilters().add(extFilterAud);
		File file = fileChooser.showOpenDialog(stage);

		try{
			Media sound = new Media(file.getAbsolutePath());
			CustomSoundStorage soundStorage = new CustomSoundStorage(sound, file.getAbsolutePath());
			return soundStorage;
		}
		catch(Exception ex)
		{
			return null;

		}

	}




}