package monoko.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class AssetManager {

	
	
	public static final int TILES_W = 15; //Number of tiles for one side
	public static final int TILES_H = 25; //Number of tiles for one side
	public static final int CHARA_WIDTH = 32; //width of the character sprite
	public static final int CHARA_HEIGHT = 64; //height of the character sprite
	public static final int CHARA_WIDTH_HALF = CHARA_WIDTH / 2;
	public static final int CHARA_HEIGHT_HALF = CHARA_HEIGHT / 2;
	public static final int CHARA_WIDTH_QUARTER = CHARA_WIDTH / 4;
	public static final int CHARA_HEIGHT_QUARTER = CHARA_HEIGHT / 4;
	public static final int TILE_WIDTH = 64; //width of a tile
	public static final int TILE_HEIGHT = 64; //height of a tile
	public static final int TILE_WIDTH_HALF = TILE_WIDTH / 2;
	public static final int TILE_HEIGHT_HALF = TILE_HEIGHT / 2;
	public static final int TILE_WIDTH_QUARTER = TILE_WIDTH / 4;
	public static final int TILE_HEIGHT_QUARTER = TILE_HEIGHT / 4;
	public static final int TILE_HEIGHT_8 = TILE_HEIGHT / 8;
	public static final int GAME_WIDTH = 1920; //Width of the game
	public static final int GAME_HEIGHT = 1020; //Height of the game
	public static final int XOFFSET = (GAME_WIDTH/2)+((TILES_H-TILES_W)*(TILE_WIDTH_HALF-TILE_HEIGHT_QUARTER)); //Width of the game
	public static final double YOFFSET = (GAME_HEIGHT-(Math.sqrt(Math.pow(TILES_W*TILE_WIDTH_HALF, 2)+Math.pow(TILES_H*TILE_HEIGHT_QUARTER, 2))))/2; //Height of the game
	public static final double[] POINTS = new double[] {
								    		TILE_WIDTH_HALF, 0.0,
								    		TILE_WIDTH, TILE_HEIGHT_QUARTER,
								    		TILE_WIDTH_HALF, TILE_HEIGHT_HALF,
								    	    0.0, TILE_HEIGHT_QUARTER
								    	};
	
	
	public static WritableImage grass;
	public static WritableImage stones;
	public static WritableImage water;
	public static WritableImage blocade;
	public static WritableImage charadown;
	public static WritableImage charaup;
	
	//Initialisation of every tiles of the game
    public static void init(){
    	
    	Image tiles1 = new Image("/textures/tiles3d.png");
    	Image tiles2 = new Image("/textures/sheet.png");
    	Image chara = new Image("/textures/cara32x64.png");
    	grass = new WritableImage(tiles1.getPixelReader(), 681, 164, 137, 86);
    	stones = new WritableImage(tiles2.getPixelReader(),  0, TILE_HEIGHT*3, TILE_WIDTH, TILE_HEIGHT);
    	water = new WritableImage(tiles2.getPixelReader(), TILE_WIDTH*9, TILE_HEIGHT*5, TILE_WIDTH, TILE_HEIGHT);
    	blocade = new WritableImage(tiles2.getPixelReader(), 0, 0, TILE_WIDTH, TILE_HEIGHT);
    	charadown = new WritableImage(chara.getPixelReader(), 0, 0, CHARA_WIDTH, CHARA_HEIGHT);
    	charaup = new WritableImage(chara.getPixelReader(), CHARA_WIDTH, 0, CHARA_WIDTH, CHARA_HEIGHT);
    	
    	
    }
    
    
    //On donne des coordonnées dans la grille
    //Sort les coordonnées isometrique
    public static int[] toIso(int x, int y){
    	
        int i = (x - y) * TILE_WIDTH_HALF;
        int j = (x + y) * TILE_HEIGHT_QUARTER;
        
        i += XOFFSET;
        j += YOFFSET;

        return new int[]{i,j};
    }
    
    //On donne des coordonnées dans la grille
    //Sort les coordonnées isometrique
    public static int[] toIsoPoly(int x, int y){
    	
        int i = (x - y) * TILE_WIDTH_HALF;
        int j = (x + y) * TILE_HEIGHT_QUARTER;
        
        i += XOFFSET+TILE_WIDTH_HALF;
        j += YOFFSET+TILE_HEIGHT_QUARTER+TILE_HEIGHT_8;

        return new int[]{i,j};
    }

    //On donne des coordonées isometrique
    //Sort les coordonnées dans la grille
    public static int[] toGrid(double x, double y){

    	x-=XOFFSET;
    	y-=YOFFSET+TILE_HEIGHT_8;
    	
    	double ti = Math.ceil((x/TILE_WIDTH_HALF + y/TILE_HEIGHT_QUARTER)/2);
    	double tj = Math.ceil((y/TILE_HEIGHT_QUARTER - x/TILE_WIDTH_HALF)/2);
    	
    	int i = (int) Math.ceil(ti)-2;
        int j = (int) Math.ceil(tj)-1;
        
        if(i<0) {
        	i=0;
        }else if(i>=TILES_W) {
        	i=TILES_W-1;
        }
        
        if(j<0) {
        	j=0;
        }else if(j>=TILES_H) {
        	j=TILES_H-1;
        }
        
        return new int[]{i,j};
        
    }
    
    
    
    //On donne des coordonnées dans la grille
    //Sort les coordonnées isometrique
    public static int[] toIsoChara(int x, int y){
    	
    	int i = (x - y) * TILE_WIDTH_HALF;
        int j = (x + y) * TILE_HEIGHT_QUARTER;
        
        i += XOFFSET+CHARA_WIDTH_HALF;
        j += (YOFFSET-CHARA_WIDTH_HALF);

        return new int[]{i,j};
    }

    //On donne des coordonées isometrique
    //Sort les coordonnées dans la grille
    public static int[] toGridChara(double x, double y){

    	x-=(XOFFSET+CHARA_WIDTH_HALF);
    	y+=YOFFSET+CHARA_WIDTH_HALF;
    	
    	double ti = Math.ceil((x/TILE_WIDTH_HALF + y/TILE_HEIGHT_QUARTER)/2);
    	double tj = Math.ceil((y/TILE_HEIGHT_QUARTER - x/TILE_WIDTH_HALF)/2);
    	
    	int i = (int) Math.ceil(ti)-2;
        int j = (int) Math.ceil(tj)-1;
        
        if(i<0) {
        	i=0;
        }else if(i>=TILES_W) {
        	i=TILES_W-1;
        }
        
        if(j<0) {
        	j=0;
        }else if(j>=TILES_H) {
        	j=TILES_H-1;
        }
        
        return new int[]{i,j};
        
    }
    
    
    
}
