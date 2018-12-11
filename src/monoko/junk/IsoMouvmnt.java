package monoko.junk;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import monoko.objects.Gameboard;
import monoko.objects.Tile;

public class IsoMouvmnt extends Application{
	
	
	public static final int TILES = 10;
	public static final int CHARA_WIDTH = 32;
	public static final int CHARA_HEIGHT = 64;
	public static final int CHARA_WIDTH_HALF = CHARA_WIDTH / 2;
	public static final int CHARA_HEIGHT_HALF = CHARA_HEIGHT / 2;
	public static final int CHARA_WIDTH_QUARTER = CHARA_WIDTH / 4;
	public static final int CHARA_HEIGHT_QUARTER = CHARA_HEIGHT / 4;
	public static final int TILE_WIDTH = 64;
	public static final int TILE_HEIGHT = 64;
	public static final int TILE_WIDTH_HALF = TILE_WIDTH / 2;
	public static final int TILE_HEIGHT_HALF = TILE_HEIGHT / 2;
	public static final int TILE_WIDTH_QUARTER = TILE_WIDTH / 4;
	public static final int TILE_HEIGHT_QUARTER = TILE_HEIGHT / 4;
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 400;
	
	public int[] coordSelected = new int[] {-1,-1};
	
	
    public static void main(String[] args) 
    {
        launch(args);
    }
 
    public void start(Stage theStage) 
    {
    	Image tiles = new Image("/textures/sheet.png");
    	Image chara = new Image("/textures/cara32x64.png");
    	WritableImage stones = new WritableImage(tiles.getPixelReader(),  0, TILE_HEIGHT*3, TILE_WIDTH, TILE_HEIGHT);
    	WritableImage water = new WritableImage(tiles.getPixelReader(), TILE_WIDTH*9, TILE_HEIGHT*5, TILE_WIDTH, TILE_HEIGHT);
		WritableImage selected = new WritableImage(tiles.getPixelReader(), 0, TILE_HEIGHT*5, TILE_WIDTH, TILE_HEIGHT);
		WritableImage charadown = new WritableImage(chara.getPixelReader(), 0, 0, CHARA_WIDTH, CHARA_HEIGHT);
		WritableImage charaup = new WritableImage(chara.getPixelReader(), CHARA_WIDTH, 0, CHARA_WIDTH, CHARA_HEIGHT);
		
    	Gameboard board = new Gameboard(0, "map1", TILES, TILES);
    	
        theStage.setTitle("My First Iso");
        
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
             
        final Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add( canvas );
        
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
            	gc.clearRect(0, 0, GAME_WIDTH,GAME_HEIGHT);
            	for (int i = 0; i < TILES; i++) {
        			for (int j = 0; j < TILES; j++) {
        				Tile currentTile = board.getTile(i, j);
        				int [] coordISO = toIso(i,j,canvas.getWidth()/2);
        				//Create tile in fonction on the selected and type attribute
    					if(currentTile.isSelected()) {
                            gc.drawImage(selected, coordISO[0], coordISO[1], TILE_WIDTH, TILE_HEIGHT);
    					}else {
            				if (currentTile.getType() == 0) {
            					gc.drawImage(stones, coordISO[0], coordISO[1], TILE_WIDTH, TILE_HEIGHT);
            				}else if (currentTile.getType() == 1) {
            					gc.drawImage(water, coordISO[0], coordISO[1], TILE_WIDTH, TILE_HEIGHT);
            				}else {
            					gc.drawImage(stones, coordISO[0], coordISO[1], TILE_WIDTH, TILE_HEIGHT);
            				}
    					}
    					//Add the character if one is on the tile
    					if(currentTile.getCharacter() != null) {
    						coordISO = toIsoChara(i,j,canvas.getWidth()/2);
    						if(currentTile.getCharacter().getInGameSprite() == "charaup") {
    							gc.drawImage(charaup, coordISO[0], coordISO[1], CHARA_WIDTH, CHARA_HEIGHT);
    						}else {
    							gc.drawImage(charadown, coordISO[0], coordISO[1], CHARA_WIDTH, CHARA_HEIGHT);
    						}
    						
    					}
        			}
        		}
            }
        }.start();
         
        
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	coordSelected = toGrid(event.getSceneX(), event.getSceneY(), canvas.getWidth()/2);
            	board.changeSelected(coordSelected);
            	System.out.println("X :" + coordSelected[0]+ "Y :" +coordSelected[1]);
            }
        });
        
        
        
        theStage.show();
    }
    
    //On donne des coordonnées dans la grille
    //Sort les coordonnées isometrique
    public static int[] toIso(int x, int y, double xOffset){
    	
        int i = (x - y) * TILE_WIDTH_HALF;
        int j = (x + y) * TILE_HEIGHT_QUARTER;
        
        i += xOffset;

        return new int[]{i,j};
    }

    //On donne des coordonées isometrique
    //Sort les coordonnées dans la grille
    public static int[] toGrid(double x, double y, double xOffset){

    	x-=xOffset;
    	
    	double ti = Math.ceil((x/TILE_WIDTH_HALF + y/TILE_HEIGHT_QUARTER)/2);
    	double tj = Math.ceil((y/TILE_HEIGHT_QUARTER - x/TILE_WIDTH_HALF)/2);
    	
    	int i = (int) Math.ceil(ti)-2;
        int j = (int) Math.ceil(tj)-1;
        
        if(i<0) {
        	i=0;
        }else if(i>=TILES) {
        	i=TILES-1;
        }
        
        if(j<0) {
        	j=0;
        }else if(j>=TILES) {
        	j=TILES-1;
        }
        
        return new int[]{i,j};
        
    }
    
    
    
    //On donne des coordonnées dans la grille
    //Sort les coordonnées isometrique
    public static int[] toIsoChara(int x, int y, double xOffset){
    	
    	int i = (x - y) * TILE_WIDTH_HALF;
        int j = (x + y) * TILE_HEIGHT_QUARTER;
        
        i += xOffset+CHARA_WIDTH_HALF;
        j -= CHARA_WIDTH_HALF;

        return new int[]{i,j};
    }

    //On donne des coordonées isometrique
    //Sort les coordonnées dans la grille
    public static int[] toGridChara(double x, double y, double xOffset){

    	x-=(xOffset+CHARA_WIDTH_HALF);
    	y+=CHARA_WIDTH_HALF;
    	
    	double ti = Math.ceil((x/TILE_WIDTH_HALF + y/TILE_HEIGHT_QUARTER)/2);
    	double tj = Math.ceil((y/TILE_HEIGHT_QUARTER - x/TILE_WIDTH_HALF)/2);
    	
    	int i = (int) Math.ceil(ti)-2;
        int j = (int) Math.ceil(tj)-1;
        
        if(i<0) {
        	i=0;
        }else if(i>=TILES) {
        	i=TILES-1;
        }
        
        if(j<0) {
        	j=0;
        }else if(j>=TILES) {
        	j=TILES-1;
        }
        
        return new int[]{i,j};
        
    }
    
    
}

