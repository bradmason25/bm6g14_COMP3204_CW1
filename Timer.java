package uk.ac.soton.ecs.bm6g14;

import java.io.File;
import java.text.SimpleDateFormat;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.convolution.*;

public class Timer {
	
	public static void main( String[] args ) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
		
    	try {
    		MBFImage image = ImageUtilities.readMBF(new File("/home/brad/CompSci/COMP3204/CW2/hybrid_images/dog.bmp"));
			int w = image.getWidth();
			int h = image.getHeight();
			long startTime, endTime, finalTime;
			
			//Setup
    		int sigma = 10;
    		int size = (int) (8.0f * sigma + 1.0f);
    		if (size % 2 == 0) { size ++; }
    		FImage kernel = Gaussian2D.createKernelImage(size, sigma);
    		
    		MyConvolution conv = new MyConvolution(kernel.pixels);
    		FGaussianConvolve gConv = new FGaussianConvolve(sigma);
    		
    		
			
			
			//First Timer
			System.out.println(sdf.format(System.currentTimeMillis()) + " # Starting First Timer");
			startTime = System.currentTimeMillis();
			float[] val;
			//##########FirstTimerMethod##############
			MBFImage first = image.process(conv);
			//##########MethodEnd#####################
			
			endTime = System.currentTimeMillis();
			finalTime = endTime-startTime;
			System.out.println(sdf.format(System.currentTimeMillis()) + " # First Time = " + finalTime + "ms");
			
			System.out.println(sdf.format(System.currentTimeMillis()) + " # Starting Second Timer");
			//Second Timer
			startTime = System.currentTimeMillis();
			Float[] vall;
			//#########SecondTimerMethod##############
			MBFImage second = image.process(gConv);
			//#########MethodEnd######################
			
			endTime = System.currentTimeMillis();
			finalTime = endTime-startTime;
			System.out.println(sdf.format(System.currentTimeMillis()) + " # Second Time = " + finalTime + "ms");
			
			
			DisplayUtilities.displayName(first, "Image");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println(sdf.format(System.currentTimeMillis()) + " # Finished");
	}	

}
