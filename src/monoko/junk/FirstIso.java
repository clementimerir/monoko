package monoko.junk;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class FirstIso extends Application{
	
	
	static int tilesWidth = 32;
	
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 16;
	public static final int TILE_WIDTH_HALF = TILE_WIDTH / 2;
	public static final int TILE_HEIGHT_HALF = TILE_HEIGHT / 2;
	public static final int TILE_WIDTH_QUARTER = TILE_WIDTH / 4;
	public static final int TILE_HEIGHT_QUARTER = TILE_HEIGHT / 4;
	
	
    public static void main(String[] args) 
    {
        launch(args);
    }
 
    public void start(Stage theStage) 
    {
        theStage.setTitle("My First Iso");
        
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
             
        Canvas canvas = new Canvas( 1000, 800);
        root.getChildren().add( canvas );
        
        double numberTiles = Math.pow(tilesWidth, 2);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();  
        double[] points = new double[] {
        		TILE_WIDTH/2, 0.0,
        		TILE_WIDTH, TILE_HEIGHT/2,
        		TILE_WIDTH/2, TILE_HEIGHT,
        	    0.0, TILE_HEIGHT/2
        };
        
        for(int i = (int)numberTiles; i<numberTiles*2; i++) {
        	double x = (i / tilesWidth);
        	double y = Math.floorMod(i, tilesWidth);
        	Polygon poly = new Polygon(points);
        	Translate t = new Translate(TILE_WIDTH_HALF*(x-y),TILE_HEIGHT_HALF*(x+y));
        	poly.getTransforms().addAll(t);
        	poly.setFill(Color.GREEN);
        	root.getChildren().add(poly);
        }
        
        
        
//        var numberTiles = Math.pow(tilesWidth, 2);
//        tilesGroup.content = [
//          for (i in [0 .. numberTiles - 1]) {
//            var x = (i / tilesWidth) as Integer;
//            var y = (i mod tilesWidth) as Integer;
//            Polygon {
//              points : [ TILE_WIDTH / 2, 0.0, TILE_WIDTH, TILE_HEIGHT / 2, TILE_WIDTH / 2, TILE_HEIGHT, 0.0, TILE_HEIGHT / 2 ]
//              translateX : TILE_WIDTH / 2 * (x - y)
//              translateY : TILE_HEIGHT / 2 * (x + y)
//              fill : Color.GREEN
//            }
//          }
//        ];
        
        
        theStage.show();
    }
}
