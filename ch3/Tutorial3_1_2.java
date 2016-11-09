package uk.ac.soton.ecs.bm6g14.ch3;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.connectedcomponent.GreyscaleConnectedComponentLabeler;
import org.openimaj.image.pixel.ConnectedComponent;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.ml.clustering.FloatCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.FloatKMeans;

public class Tutorial3_1_2 {
	public static void main( String[] args ) {
    	try {
			MBFImage input = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/sinaface.jpg"));
			
			input = ColourSpace.convert(input, ColourSpace.CIE_Lab);
			
			FloatKMeans cluster = FloatKMeans.createExact(2);
			
			float[][] imageData = input.getPixelVectorNative(new float[input.getWidth() * input.getHeight()][3]);
			
			FloatCentroidsResult result = cluster.cluster(imageData);
			
			final float[][] centroids = result.centroids;
			for (float[] fs : centroids) {
			    System.out.println(Arrays.toString(fs));
			}
			
			
			final HardAssigner<float[],?,?> assigner = result.defaultHardAssigner();
			
			long startTime = System.currentTimeMillis();
			
			for (int y=0; y<input.getHeight(); y++) {
			    for (int x=0; x<input.getWidth(); x++) {
			        float[] pixel = input.getPixelNative(x, y);
			        int centroid = assigner.assign(pixel);
			        input.setPixelNative(x, y, centroids[centroid]);
			    }
			}
			/*
			input.processInplace(new PixelProcessor<Float[]>() {
				public Float[] processPixel(Float[] pixel) {
					int l = pixel.length;
					
					float[] midPixel = new float[l];
					for(int i=0; i<pixel.length; i++) {
						midPixel[i] = pixel[i];
					}
					int centroid = assigner.assign(midPixel);
					float[] newPixel = centroids[centroid];
					for(int i=0; i<l; i++) {
						pixel[i] = newPixel[i];
					}
					return pixel;
				}
				
			});*/
			
			long endTime = System.currentTimeMillis();
			
			//Process in place and the dual for loops in my implementation took roughly the same time to process
			//However the process in place method uses Float[] as opposed to float[] which is used in the other methods provided.
			//This meant that I had to convert between these two types which I assume made the method slower
			
			System.out.println(endTime-startTime);
			
			input = ColourSpace.convert(input, ColourSpace.RGB);
			DisplayUtilities.display(input);
			
			GreyscaleConnectedComponentLabeler labeler = new GreyscaleConnectedComponentLabeler();
			List<ConnectedComponent> components = labeler.findComponents(input.flatten());
			
			int i = 0;
			for (ConnectedComponent comp : components) {
			    if (comp.calculateArea() < 50) 
			        continue;
			    input.drawText("Point:" + (i++), comp.calculateCentroidPixel(), HersheyFont.TIMES_MEDIUM, 20);
			}
			
			DisplayUtilities.display(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
