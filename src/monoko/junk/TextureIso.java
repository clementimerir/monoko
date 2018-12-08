package monoko.junk;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

public class TextureIso extends Application{
	
    public static void main(String[] args) 
    {
        launch(args);
    }
 
    public void start(Stage theStage) 
    {
    	Image tiles = new Image("/tiles3d.png");
    	PixelReader reader = tiles.getPixelReader();
    	WritableImage grass = new WritableImage(reader, 681, 164, 127, 75);
    	
//    	Image tiles = new Image("tiles.png");
//    	PixelReader reader = tiles.getPixelReader();
//    	WritableImage grass = new WritableImage(reader, 910, 690, 110, 80);//reader, 910, 690, 110, 80
    	
        theStage.setTitle("My First Iso");
        
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
             
        final Canvas canvas = new Canvas(1600, 800);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add( canvas );
        
        int tileWidth = (int) grass.getWidth();
        int tileWidthHalf = tileWidth/2;
        int tileHeight = (int) grass.getHeight();
        int tileHeightHalf = tileHeight/2;
        
        for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				
			    gc.drawImage(grass, canvas.getWidth()/2 + (i-j) * tileWidthHalf, (i+j) * tileHeightHalf, 110, 80);
			    
			}
		}
        
        theStage.show();
    }
}
