package uk.ac.soton.ecs.bm6g14.ch6;

import java.util.Map.Entry;

import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.data.dataset.VFSListDataset;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.dataset.FlickrImageDataset;
import org.openimaj.util.api.auth.DefaultTokenFactory;
import org.openimaj.util.api.auth.common.FlickrAPIToken;

public class Tutorial6_1_1 {
	public static void main(String[] args) {
		try {
			
			VFSListDataset<FImage> images = new VFSListDataset<FImage>("/home/brad/Pictures/sample", ImageUtilities.FIMAGE_READER);
			/*
			System.out.println(images.size());
			DisplayUtilities.display(images.getRandomInstance(), "A random image from the dataset");
			DisplayUtilities.display("My images", images);
			
			VFSListDataset<FImage> faces = 
					new VFSListDataset<FImage>("zip:http://datasets.openimaj.org/att_faces.zip", ImageUtilities.FIMAGE_READER);
			DisplayUtilities.display("ATT faces", faces);
			*/
			VFSGroupDataset<FImage> groupedFaces = 
					new VFSGroupDataset<FImage>( "zip:http://datasets.openimaj.org/att_faces.zip", ImageUtilities.FIMAGE_READER);
			/*
			for (final Entry<String, VFSListDataset<FImage>> entry : groupedFaces.entrySet()) {
				DisplayUtilities.display(entry.getKey(), entry.getValue());
			}
			*/
			
			for (final Entry<String, VFSListDataset<FImage>> entry : groupedFaces.entrySet()) {
			}
			DisplayUtilities.display("Random Faces", ranFace);
			System.out.println(ranFace.size());
			
			
		} catch(Exception e) { e.printStackTrace(); }
	}
}
