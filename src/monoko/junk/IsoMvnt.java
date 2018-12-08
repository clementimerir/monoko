package monoko.junk;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class IsoMvnt extends Application{
	
	public static final int TILES_WIDTH = 32;
	public static final int TILE_WIDTH = 64;
	public static final int TILE_HEIGHT = 64;
	public static final int TILE_WIDTH_HALF = TILE_WIDTH / 2;
	public static final int TILE_HEIGHT_HALF = TILE_HEIGHT / 2;
	public static final int TILE_WIDTH_QUARTER = TILE_WIDTH / 4;
	public static final int TILE_HEIGHT_QUARTER = TILE_HEIGHT / 4;
	
	
	public int xOffset;
	public int yOffset;
	
	
	public static void main(String[] args) 
    {
        launch(args);
    }
	
	
	
	//Fonction de conversion de coordonnées de grille a iso
	public int[] toIso(int x, int y){

	    int i = (x - y) * TILE_WIDTH_HALF;
	    int j = (x + y) * TILE_HEIGHT_QUARTER;

	    i += xOffset-TILE_WIDTH_HALF;
	    j += yOffset;

	    return new int[]{i,j};
	}
	//Fonction de conversion de coordonnées de iso a grille
	public int[] toGrid(double i, double j){

	    i-=xOffset;
	    j-=yOffset;

	    double tx = Math.ceil(((i / TILE_WIDTH_HALF) + (j / TILE_HEIGHT_QUARTER))/2);
	    double ty = Math.ceil(((j / TILE_HEIGHT_QUARTER) - (i / TILE_WIDTH_HALF))/2);

	    int x = (int) Math.ceil(tx)-1;
	    int y = (int) Math.ceil(ty)-1;

	    return new int[]{x, y};
	}
	
	
	@Override
    public void start(Stage theStage) 
    {
	
		//Initialisation de la scene
		Group root = new Group();
	    Scene theScene = new Scene( root );
	    theStage.setScene( theScene );
	    //Creation du canvas
	    Canvas canvas = new Canvas( 500, 500 );
	    root.getChildren().add( canvas );
		
		Image fullsheet = new Image( "/textures/sheet.png" );
		
		//Dessins a rajouter
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		
		WritableImage stones = new WritableImage(fullsheet.getPixelReader(), 0, TILE_HEIGHT*3, TILE_WIDTH, TILE_HEIGHT);
		WritableImage water = new WritableImage(fullsheet.getPixelReader(), TILE_WIDTH*9, TILE_HEIGHT*5, TILE_WIDTH, TILE_HEIGHT);
		WritableImage selected = new WritableImage(fullsheet.getPixelReader(), 0, TILE_HEIGHT*5, TILE_WIDTH, TILE_HEIGHT);
		/*TEST
		gc.drawImage( stones, 0, 0);
		gc.drawImage( water, 0, TILE_HEIGHT);
		gc.drawImage( selected, 0, TILE_HEIGHT*2);
		*/
		
		
		
		//Affichage du jeu
        theStage.show();
    }
	
}
