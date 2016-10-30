package image;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
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
		ImageExpander imgex = new ImageExpander();
		ImageDataEncoder dataenc = new ImageDataEncoder();
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
		
		System.out.println("Expanding");
		BufferedImage bi1 = imgex.expand(bufferedImage);
		System.out.println("   ");
		
		BufferedImage bi2 = dataenc.encode(bi1, data);
		
		//clone
		BufferedImage bi0 = new BufferedImage(bi2.getWidth(), bi2.getHeight(), java.awt.image.BufferedImage.TYPE_INT_RGB);
		WritableRaster raster0 = bi0.getRaster();
		Raster raster2 = bi2.getRaster();
		for(int x = 0; x < bi2.getWidth(); x++){
			for(int y = 0; y < bi2.getHeight(); y++){
				int[] inarr = new int[4];
				int[] outarr = new int[4];
				raster2.getPixel(x, y, inarr);
				outarr[0] = inarr[0];
				outarr[1] = inarr[1];
				outarr[2] = inarr[2];
				outarr[3] = inarr[3];
				
				raster0.setPixel(x, y, outarr);
			}
		}
		
		//dataenc.outputImageData(bi2);
		
		//DECODE bi2 is what is posted
		BufferedImage bi3 = imgex.recoverKeyImage(bi0);
		
		BufferedImage bi4 = imgex.expand(bi3);
		//dataenc.outputImageData(bi4);
		
		
		//Byte[] dataOutput = dataenc.decodeImage(bi4, bi2);
		
		//System.out.println(dataOutput[0].toString() + ", "+ dataOutput[1].toString() + ", "+ dataOutput[2].toString() + ", "+ dataOutput[3].toString() + ", "+ dataOutput[4].toString());
		
		

	}
}
