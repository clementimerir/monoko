package monoko.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class AssetManager {

	public static WritableImage grass;
	
    public static void init(){

//    	Image tiles = new Image("/monoko/res/tiles.png");
//    	PixelReader reader = tiles.getPixelReader();
//    	WritableImage newImage = new WritableImage(reader, 910, 690, 110, 80);
//    	grass = newImage;
    	
    	
    	Image tiles = new Image("/monoko/res/tiles3d.png");
    	PixelReader reader = tiles.getPixelReader();
    	WritableImage newImage = new WritableImage(reader, 681, 164, 137, 86);
    	grass = newImage;
    }
}
