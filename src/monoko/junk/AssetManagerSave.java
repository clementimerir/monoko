package monoko.junk;

import java.awt.image.BufferedImage;

import monoko.utils.ImageLoader;

public class AssetManagerSave {
	private static final int width = 64, height = 64;

    public static BufferedImage grass, water, selected;

    public static void init(){
        //Temp
        SpriteSheet tileSheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));

        grass = tileSheet.crop(width*2, 0, width, height);
        water = tileSheet.crop(width*9, height*5, width, height);
        selected = tileSheet.crop(0, height*5, width, height);
    }
}
