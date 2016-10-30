package image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

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
	
	public byte[] decodeImage(BufferedImage keyBufimg, BufferedImage encodedBufimg){
		WritableRaster keyRaster = keyBufimg.getRaster();
		WritableRaster encodedRaster = encodedBufimg.getRaster();
		
		
		int offset = 0;
		byte[] bytearr = new byte[1024];
		
		
		for(int x = 0; x < keyRaster.getWidth(); x+=2){
			for(int y = 0; y < keyRaster.getHeight(); y+=2){
				
				
					int[] keyArr = new int[4];
					keyRaster.getPixel(x+1, y, keyArr);
					int[] encodedArr = new int[4];
					encodedRaster.getPixel(x+1, y, encodedArr);
					bytearr = compare(keyArr, encodedArr, bytearr, offset);
					offset += 3;
					
					keyArr = new int[4];
					keyRaster.getPixel(x, y+1, keyArr);
					encodedArr = new int[4];
					encodedRaster.getPixel(x, y+1, encodedArr);
					bytearr = compare(keyArr, encodedArr, bytearr, offset);
					offset += 3;
					
					keyArr = new int[4];
					keyRaster.getPixel(x+1, y+1, keyArr);
					encodedArr = new int[4];
					encodedRaster.getPixel(x+1, y+1, encodedArr);
					bytearr = compare(keyArr, encodedArr, bytearr, offset);
					offset += 3;
				
			}
		}
		
		
		
		
		//System.out.println(binaryStr);
//		String[] binStrArr = binaryStr.split("(?<=\\G........)");
//		Byte[] array = new Byte[binStrArr.length];
//		int counter = 0;
//		
//		for(String str : binStrArr){
//			
//			
//			String newstr = new StringBuffer(str).reverse().toString();
//			System.out.println(newstr);
//			//if(newstr.length() == 8)
//			//{
//			
//			if(newstr.startsWith("1")){
//				newstr = "-" + newstr.substring(1);
//			}
//			Byte b = Byte.parseByte(newstr, 2);
//			array[counter] = b;
//			//}
//			counter++;
//		}
		
		//process string
		
		
		
		
		return bytearr;	
	}
	
	private byte[] compare(int[] keyArr, int[] encodedArr, byte[] bytearr, int offset) {
		for(int i = 0; i < 3; i++){
			System.out.println(offset);
			if(keyArr[i] != encodedArr[i]){
				
				bytearr[offset/8] = (byte) (bytearr[offset/8] | (1 << (offset % 8)));
			}
			offset++;
		}
		
		
		
		return bytearr;
		
	}
	
//	private String compare(int[] keyArr, int[] encodedArr){
//		String str = "";
//		for(int i = 0; i < 3; i++){
//			
//			if(keyArr[i] != encodedArr[i]){
//				System.out.println("1: " + keyArr[i] + " - " + encodedArr[i]);			
//				str += "1";
//				
//			}else{
//				System.out.println("0: " + keyArr[i] + " - " + encodedArr[i]);
//				str += "0";
//			}
//			
//		}
//		
//		return str;
//	}
	
	public void outputImageData(BufferedImage bufimg){
		WritableRaster raster = bufimg.getRaster();
		//System.out.println("~~~########################################~~~");
		for(int x = 0; x < bufimg.getWidth(); x++){
			for(int y = 0; y < bufimg.getHeight(); y++){
				int[] arr = new int[4];
				raster.getPixel(x, y, arr);
				//System.out.println(arr[0] + ", " + arr[1] + ", " + arr[2] + ", " + arr[3]);
			}
		}
	}
}

