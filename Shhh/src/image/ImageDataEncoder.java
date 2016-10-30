package image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class ImageDataEncoder {
	
	
	public BufferedImage encode(BufferedImage bufimg, Byte[] data){
		int offset = 0;
		WritableRaster raster = bufimg.getRaster();

		for(int x = 0; x < bufimg.getWidth(); x+=2){
			for(int y = 0; y < bufimg.getHeight(); y+=2){
				int[] rightPixel = new int[4];
				int[] downPixel = new int[4];
				int[] downRightPixel = new int[4];
				
				raster.getPixel(x+1, y, rightPixel);
				raster.setPixel(x+1, y, adjust(rightPixel, offset, data)) ;
				offset += 3;
				
				raster.getPixel(x, y+1, downPixel);
				raster.setPixel(x, y+1, adjust(downPixel, offset, data));
				offset += 3;
				
				raster.getPixel(x+1, y+1, downRightPixel);
				raster.setPixel(x+1, y+1, adjust(downRightPixel, offset, data));
				offset += 3;
			}
		}
		
		
		return bufimg; 
	}

	private int[] adjust(int[] pixel, int offset, Byte[] data){
		
		for(int i = 0; i < 3; i++){
			try{
				//System.out.println(offset);
				if((data[offset/8] >> (offset % 8) & 1) == 1){
					//System.out.println("1");
					if(pixel[i] == 0){
						pixel[i] = 1;
					}else{
						pixel[i] -= 1;
					}
				}else{
					//System.out.println("0");
				}
			}catch (Exception e){
				//System.out.println("null");
			}
			
			offset++;
		}
		return pixel;
	}
	
	public Byte[] decodeImage(BufferedImage keyBufimg, BufferedImage encodedBufimg){
		WritableRaster keyRaster = keyBufimg.getRaster();
		WritableRaster encodedRaster = encodedBufimg.getRaster();
		
		String binaryStr = "";
		
		for(int x = 0; x < keyRaster.getWidth(); x+=2){
			for(int y = 0; y < keyRaster.getHeight(); y+=2){
				
					int[] keyArr = new int[4];
					keyRaster.getPixel(x+1, y, keyArr);
					int[] encodedArr = new int[4];
					encodedRaster.getPixel(x+1, y, encodedArr);
					binaryStr += compare(keyArr, encodedArr);
					
					keyArr = new int[4];
					keyRaster.getPixel(x, y+1, keyArr);
					encodedArr = new int[4];
					encodedRaster.getPixel(x, y+1, encodedArr);
					binaryStr += compare(keyArr, encodedArr);
					
					keyArr = new int[4];
					keyRaster.getPixel(x+1, y+1, keyArr);
					encodedArr = new int[4];
					encodedRaster.getPixel(x+1, y+1, encodedArr);
					binaryStr += compare(keyArr, encodedArr);
				
			}
		}
		
		System.out.println(binaryStr);
		String[] binStrArr = binaryStr.split("(?<=\\G........)");
		Byte[] array = new Byte[binStrArr.length];
		int counter = 0;
		
		for(String str : binStrArr){
			String newstr = new StringBuffer(str).reverse().toString();
			System.out.println(newstr);
			Byte b = Byte.parseByte(newstr, 2);
			array[counter] = b;
			counter++;
		}
		
		//process string
		
		
		return array;	
	}
	
	private String compare(int[] keyArr, int[] encodedArr){
		String str = "";
		for(int i = 0; i < 3; i++){
			
			if(keyArr[i] != encodedArr[i]){
				System.out.println("1: " + keyArr[i] + " - " + encodedArr[i]);			
				str += "1";
				
			}else{
				System.out.println("0: " + keyArr[i] + " - " + encodedArr[i]);
				str += "0";
			}
			
		}
		
		return str;
	}
	
	public void outputImageData(BufferedImage bufimg){
		WritableRaster raster = bufimg.getRaster();
		System.out.println("~~~########################################~~~");
		for(int x = 0; x < bufimg.getWidth(); x++){
			for(int y = 0; y < bufimg.getHeight(); y++){
				int[] arr = new int[4];
				raster.getPixel(x, y, arr);
				System.out.println(arr[0] + ", " + arr[1] + ", " + arr[2] + ", " + arr[3]);
			}
		}
	}
}

