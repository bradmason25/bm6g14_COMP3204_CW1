package uk.ac.soton.ecs.bm6g14;

import java.io.File;
import java.net.URL;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.convolution.Gaussian2D;
import org.openimaj.image.processing.resize.ResizeProcessor;

public class App {
	
	public static void main( String[] args ) {
    	try {
    		MBFImage imageA = ImageUtilities.readMBF(new File("/home/brad/CompSci/COMP3204/CW2/hybrid_images/mona.jpg"));
    		imageA = ResizeProcessor.halfSize(ResizeProcessor.halfSize(imageA));
    		int aw = imageA.getWidth();
    		int ah = imageA.getHeight();
    		  		
    		int bw = aw;
    		int bh = ah;
    		MBFImage imageB = new MBFImage(bw,bh);
    		
    		MBFImage nic = ImageUtilities.readMBF(new File("/home/brad/CompSci/COMP3204/CW2/hybrid_images/nic.png"));			
			nic = ResizeProcessor.halfSize(ResizeProcessor.halfSize(nic));
			int ih = nic.getHeight();
    		int iw = nic.getWidth();
    		
    		imageB.drawImage(nic,(bw-iw)/2,(bh-ih)/2);
    		
    		
    		int sigma = 10;
    		int size = (int) (8.0f * sigma + 1.0f);
    		if (size % 2 == 0) { size ++; }
    		FImage kernel = Gaussian2D.createKernelImage(size, sigma);
    		MyConvolution conv = new MyConvolution(kernel.pixels);
    		
    		MBFImage lowpass = imageA.process(conv);
    		MBFImage highpass = imageB.subtract(imageB.clone().process(conv));
    		MBFImage result = lowpass.add(highpass);
    		
    		DisplayUtilities.display(result);
    		

			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
}
