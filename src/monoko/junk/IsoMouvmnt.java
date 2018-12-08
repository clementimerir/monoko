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

public class IsoMouvmnt extends Application{
	
	
	public static final int TILES = 10;
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
    	WritableImage stones = new WritableImage(tiles.getPixelReader(),  0, TILE_HEIGHT*3, TILE_WIDTH, TILE_HEIGHT);
    	WritableImage water = new WritableImage(tiles.getPixelReader(), TILE_WIDTH*9, TILE_HEIGHT*5, TILE_WIDTH, TILE_HEIGHT);
		WritableImage selected = new WritableImage(tiles.getPixelReader(), 0, TILE_HEIGHT*5, TILE_WIDTH, TILE_HEIGHT);
    	
    	
        theStage.setTitle("My First Iso");
        
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
             
        final Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add( canvas );
      
        //Creation aleatoire de la map
        double [][] randMap = new double[TILES][TILES];
        for (int i = 0; i < TILES; i++) {
			for (int j = 0; j < TILES; j++) {
				//Creation de la tile
				randMap[i][j] = Math.random() * ( 10 - 0 );
			}
		}
        
        //Timer pour créer une map dynamique
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
            	gc.clearRect(0, 0, GAME_WIDTH,GAME_HEIGHT);
            	for (int i = 0; i < TILES; i++) {
        			for (int j = 0; j < TILES; j++) {
        				
    					if(coordSelected[0] == i && coordSelected[1] == j) {
    						int [] coordISO = toIso(coordSelected[0],coordSelected[1],canvas.getWidth()/2);
                            gc.drawImage(selected, coordISO[0], coordISO[1], TILE_WIDTH, TILE_HEIGHT);
    					}else {
    						//Recuperation coordonées
            				int [] coordISO = toIso(i,j,canvas.getWidth()/2);
            				//Creation de la tile
            				if (randMap[i][j] < 8) {
            					gc.drawImage(stones, coordISO[0], coordISO[1], TILE_WIDTH, TILE_HEIGHT);
            				}else {
            					gc.drawImage(water, coordISO[0], coordISO[1], TILE_WIDTH, TILE_HEIGHT);
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
        
        return new int[]{i,j};
        
        
        
        
    }
}

