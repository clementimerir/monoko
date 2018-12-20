package monoko.junk;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import monoko.objects.Gameboard;
import monoko.objects.Tile;
import monoko.utils.AssetManager;

public class IsoMouvmnt extends Application{
	
	public int[] coordSelected = new int[] {-1,-1};
	public int[] coordMouse = new int[] {-1,-1};
	
	
    public static void main(String[] args) 
    {
        launch(args);
    }
 
    public void start(Stage theStage) 
    {
    	
		AssetManager.init();
		
    	Gameboard board = new Gameboard(0, "map1", AssetManager.TILES_W, AssetManager.TILES_H, null, null);
    	
        theStage.setTitle("Monoko");
        
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
             
        final Canvas canvas = new Canvas(AssetManager.GAME_WIDTH, AssetManager.GAME_HEIGHT);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add( canvas );
        
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
            	//We clear the board
            	gc.clearRect(0, 0, AssetManager.GAME_WIDTH, AssetManager.GAME_HEIGHT);
            	//We draw the full board with the character
            	for (int i = 0; i < AssetManager.TILES_W; i++) {
        			for (int j = 0; j < AssetManager.TILES_H; j++) {

        				Tile currentTile = board.getTile(i, j);
        				int [] coordISO = AssetManager.toIso(i,j);
        				//Create tile in fonction on the selected and type attribute
        				
        		        
        		        int [] p1 = AssetManager.toIsoPoly(i,j);
        		        int [] p2 = AssetManager.toIsoPoly(i,j+1);
        		        int [] p3 = AssetManager.toIsoPoly(i+1,j+1);
        		        int [] p4 = AssetManager.toIsoPoly(i+1,j);
        		        

		        		if (currentTile.getType() == 0) {
        					gc.drawImage(AssetManager.stones, coordISO[0], coordISO[1], AssetManager.TILE_WIDTH, AssetManager.TILE_HEIGHT);
        				}else if (currentTile.getType() == 1) {
        					gc.drawImage(AssetManager.water, coordISO[0], coordISO[1], AssetManager.TILE_WIDTH, AssetManager.TILE_HEIGHT);
        				}else if (currentTile.getType() == 2){
        					gc.drawImage(AssetManager.wall, coordISO[0], coordISO[1], AssetManager.TILE_WIDTH, AssetManager.TILE_HEIGHT);
        				}
		        		
		        		//Where the mouse is pointing
		        		if(coordMouse[0] != -1 && i == coordMouse[0] && j == coordMouse[1]) {
		        			gc.setFill(Color.BLUE);
        					gc.setGlobalAlpha(0.5);
            		        gc.fillPolygon(new double[]{p1[0], p2[0], p3[0], p4[0]},
            		                       new double[]{p1[1], p2[1], p3[1], p4[1]}, 4);
    					}
        				gc.setFill(null);
        				gc.setGlobalAlpha(1.0);
		        		
		        		//The tile selected
		        		if(currentTile.isSelected()) {
        					gc.setFill(Color.GREEN);
        					gc.setGlobalAlpha(0.5);
            		        gc.fillPolygon(new double[]{p1[0], p2[0], p3[0], p4[0]},
            		                       new double[]{p1[1], p2[1], p3[1], p4[1]}, 4);
    					}
        				gc.setFill(null);
        				gc.setGlobalAlpha(1.0);
        				
        				//The tile where the character can move
        				if(currentTile.isMvmnt()) {
        					gc.setFill(Color.BLUE);
        					gc.setGlobalAlpha(0.5);
            		        gc.fillPolygon(new double[]{p1[0], p2[0], p3[0], p4[0]},
            		                       new double[]{p1[1], p2[1], p3[1], p4[1]}, 4);
    					}
        				gc.setFill(null);
        				gc.setGlobalAlpha(1.0);
    					
    					//Add the character if one is on the tile
    					if(currentTile.getCharacter() != null) {
    						coordISO = AssetManager.toIsoChara(i,j);
    						if(currentTile.getCharacter().getInGameSprite() == "charaup") {
    							gc.drawImage(AssetManager.charaup, coordISO[0], coordISO[1], AssetManager.CHARA_WIDTH, AssetManager.CHARA_HEIGHT);
    						}else {
    							gc.drawImage(AssetManager.charadown, coordISO[0], coordISO[1], AssetManager.CHARA_WIDTH, AssetManager.CHARA_HEIGHT);
    						}
    						
    					}
        			}
        		}
            	
            }
        }.start();
         
        
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	coordSelected = AssetManager.toGrid(event.getSceneX(), event.getSceneY());
            	//We check if a character is present on the selected tile

            	if(board.haveSelected() && board.getCurrentTileSelected().haveCharacter() && board.getTile(coordSelected).isMvmnt()) {
            		board.getTile(coordSelected).setCharacter(board.getTile(board.getCurrentlySelected()).getCharacter());
            		board.getTile(board.getCurrentlySelected()).setCharacter(null);
            		board.changeSelected(-1, -1);
            		board.resetMvmnt();
            	}else if(board.getTile(coordSelected).getType() == 0) {
            		board.changeSelected(coordSelected);
            		if(board.getCurrentTileSelected().haveCharacter()){
            			board.resetMvmnt();
            			board.setTabMvmnt();
    				}else {
    					board.resetMvmnt();
    				}
            		
            	}
            	
            	System.out.println("X :" + coordSelected[0]+ "Y :" +coordSelected[1]);
            }
        });
        
        
        root.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	coordMouse = AssetManager.toGrid(event.getSceneX(), event.getSceneY());
            	if(board.getTile(coordMouse).getType() != 0) {
            		coordMouse = new int[] {-1,-1};
            	}
            }
          });
        
        theStage.show();
    }
    
}

