package image;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImgTestApp extends Application{
	public static void main(String args[]){
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("Expanding");
		ImageExpander imgex = new ImageExpander();
		stage.show();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File imgPath = fileChooser.showOpenDialog(stage);

		BufferedImage bufferedImage = ImageIO.read(imgPath);
		
		Byte[] data = new Byte[4];
		data[0]=72;
		data[1]=97;
		data[2]=105;
		data[3]=46;
		
		imgex.expand(bufferedImage, data);

	}
}
