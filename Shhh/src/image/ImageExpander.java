package image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;


public class ImageExpander {
	public BufferedImage expand(BufferedImage bufimg){
		WritableRaster raster = bufimg.getRaster();
		
		BufferedImage newBufimg = new BufferedImage(
				bufimg.getWidth() * 2, 
				bufimg.getHeight() * 2,
				java.awt.image.BufferedImage.TYPE_INT_RGB);
		WritableRaster newRaster = newBufimg.getRaster();
		
		//System.out.println(bufimg.getWidth() + " " + bufimg.getHeight());

		for(int x = 0; x < bufimg.getWidth(); x++){
			for(int y = 0; y < bufimg.getHeight(); y++){
				int[] inputPixel = new int[4];
				int[] rightPixel = new int[4];
				int[] downPixel = new int[4];
				int[] downRightPixel = new int[4];

				raster.getPixel(x, y, inputPixel);
				if(x+1 < bufimg.getWidth()){
					raster.getPixel(x+1, y, rightPixel);
				}else{
					raster.getPixel(x, y, rightPixel);
				}
				if(y+1 < bufimg.getHeight()){
					raster.getPixel(x, y+1, downPixel);
				}else{
					raster.getPixel(x, y, downPixel);
				}
				if(y+1 < bufimg.getHeight() && x+1 < bufimg.getWidth()){
					raster.getPixel(x+1, y+1, downRightPixel);
				}else{
					raster.getPixel(x, y, downRightPixel);
				}
//				System.out.println(inputPixel[0]
//						+ ", " + inputPixel[1]
//								+ ", " + inputPixel[2]
//										+ ", " + inputPixel[3]);

				newRaster.setPixel(2*x, 2*y, inputPixel);
				newRaster.setPixel(2*x+1, 2*y, 
						interpolate(inputPixel, rightPixel));
				newRaster.setPixel(2*x, 2*y+1, 
						interpolate(inputPixel, downPixel));
				newRaster.setPixel(2*x+1, 2*y+1, 
						interpolate(inputPixel, downRightPixel));

			}
		}


		return newBufimg;
	}

	private int[] interpolate(int[] key, int[] nextKey){
		int[] interpolated = new int[4];
		interpolated[0] = key[0] - ((key[0] - nextKey[0])/2);
		interpolated[1] = key[1] - ((key[1] - nextKey[1])/2);
		interpolated[2] = key[2] - ((key[2] - nextKey[2])/2);
		interpolated[3] = 255;

		return interpolated;
	}
	
	public BufferedImage recoverKeyImage(BufferedImage bufimg){
		WritableRaster raster = bufimg.getRaster();
		
		BufferedImage newBufimg = new BufferedImage(
				bufimg.getWidth() / 2, 
				bufimg.getHeight() / 2,
				java.awt.image.BufferedImage.TYPE_INT_RGB);
		WritableRaster newRaster = newBufimg.getRaster();
		
		for(int x = 0; x < bufimg.getWidth(); x+=2){
			for(int y = 0; y < bufimg.getHeight(); y+=2){
				int[] inputPixel = new int[4];
				raster.getPixel(x, y, inputPixel);
				newRaster.setPixel(x/2, y/2, inputPixel);
			}
		}
		
		return newBufimg;
	}
	
}
