package monoko.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import monoko.objects.Character;
import monoko.objects.Gameboard;
import monoko.objects.Player;
import monoko.objects.Skill;
import monoko.objects.Team;
import monoko.objects.Tile;
import monoko.utils.AssetManager;
import monoko.utils.FxmlManager;
import monoko.utils.Manager;

public class GameController extends GameBase{
	
	public int[] coordSelected = new int[] {-1,-1};
	public int[] coordMouse = new int[] {-1,-1};
	SkillBarController skillBar = new SkillBarController(this);
	boolean haveCharacter = false;
	Gameboard board;
	Player[] players;
	Team playerTurn;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		start();
		root.getChildren().add( new FxmlManager("./ui/skillBar.fxml", skillBar).load() );
	}
	
	public void reloadSkillBar(Character character) {
		
		List<Skill> skills = character.getSkills();
//		skills.add( new Skill(0, "Sword", SkillTypeEnum.OFFENSE, EffectTypeEnum.DAMAGE, 10, false) );
//		skills.add( new Skill(0, "Bow", SkillTypeEnum.OFFENSE, EffectTypeEnum.DAMAGE, 10, false) );
//		skills.add( new Skill(0, "Pyromancy Tome", SkillTypeEnum.OFFENSE, EffectTypeEnum.DAMAGE, 10, false) );
//		skills.add( new Skill(0, "Scepter", SkillTypeEnum.OFFENSE, EffectTypeEnum.DAMAGE, 10, false) );
		skillBar.setSkills(skills);
		skillBar.loadSkills();
	}
 
    public void start() 
    {
    	
		AssetManager.init();
		
		//
		//TO CHANGE
		//
		//players = AssetManager.teamCreator();
		players[0] = new Player(1, "Player 1", Manager.getInstance().getController().getUser().getTeams().get(0));
		players[1] = new Player(2, "Player 2", Manager.getInstance().getController().getUser().getTeams().get(1));
		//
		//
		//
    	board = new Gameboard(0, "map1", AssetManager.TILES_W, AssetManager.TILES_H, players[0], players[1]);
    	playerTurn = players[0].getTeam();
    	
    	
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
        				
        				
        		        int [] p1 = AssetManager.toIsoPoly(i,j);
        		        int [] p2 = AssetManager.toIsoPoly(i,j+1);
        		        int [] p3 = AssetManager.toIsoPoly(i+1,j+1);
        		        int [] p4 = AssetManager.toIsoPoly(i+1,j);
        		        
        		        //Create tile in fonction of the type attribute
		        		if (currentTile.getType() == 0) {
        					gc.drawImage(AssetManager.stones, coordISO[0], coordISO[1], AssetManager.TILE_WIDTH, AssetManager.TILE_HEIGHT);
        				}else if (currentTile.getType() == 1) {
        					gc.drawImage(AssetManager.water, coordISO[0], coordISO[1], AssetManager.TILE_WIDTH, AssetManager.TILE_HEIGHT);
        				}else if (currentTile.getType() == 2){
        					gc.drawImage(AssetManager.blocade, coordISO[0], coordISO[1], AssetManager.TILE_WIDTH, AssetManager.TILE_HEIGHT);
        				}
		        		
		        		//
		        		//Where the mouse is pointing
		        		//
		        		if(coordMouse[0] != -1 && i == coordMouse[0] && j == coordMouse[1]) {
		        			gc.setFill(Color.BLUE);
        					gc.setGlobalAlpha(0.5);
            		        gc.fillPolygon(new double[]{p1[0], p2[0], p3[0], p4[0]},
            		                       new double[]{p1[1], p2[1], p3[1], p4[1]}, 4);
    					}
        				gc.setFill(null);
        				gc.setGlobalAlpha(1.0);
		        		
        				//
		        		//The tile selected
		        		//
        				if(currentTile.isSelected()) {
        					gc.setFill(Color.GREEN);
        					gc.setGlobalAlpha(0.5);
            		        gc.fillPolygon(new double[]{p1[0], p2[0], p3[0], p4[0]},
            		                       new double[]{p1[1], p2[1], p3[1], p4[1]}, 4);
    					}
        				gc.setFill(null);
        				gc.setGlobalAlpha(1.0);
        				
        				
        				//
        				//Information concerning a character selected
        				//
        				if(haveCharacter) {
        					//
            				//The tile where the character can move
            				//
        					if(!board.getCurrentTileSelected().getCharacter().isUsingSkill() && currentTile.isMvmnt()) {
            					gc.setFill(Color.BLUE);
            					gc.setGlobalAlpha(0.5);
                		        gc.fillPolygon(new double[]{p1[0], p2[0], p3[0], p4[0]},
                		                       new double[]{p1[1], p2[1], p3[1], p4[1]}, 4);
        					}
            				gc.setFill(null);
            				gc.setGlobalAlpha(1.0);
            				
            				//
            				//The tile where the character can attack
            				//
            				if(board.getCurrentTileSelected().getCharacter().isUsingSkill() && currentTile.isAction()) {
            					gc.setFill(Color.RED);
            					gc.setGlobalAlpha(0.5);
                		        gc.fillPolygon(new double[]{p1[0], p2[0], p3[0], p4[0]},
                		                       new double[]{p1[1], p2[1], p3[1], p4[1]}, 4);
        					}
            				gc.setFill(null);
            				gc.setGlobalAlpha(1.0);
        				}

    					if(currentTile.getCharacter() != null) {
    						//
            				//Add health bar for each and every character on the map
            				//
            				int [] bar = AssetManager.toIsoBar(i,j);
        					gc.setFill(Color.RED);
        					gc.setStroke(Color.BLACK);
        					gc.setGlobalAlpha(0.5);
        					double charaHp = currentTile.getCharacter().getCurrentAttributes().getHp();
        					double maxHp = currentTile.getCharacter().getBaseAttributes().getHp();
        					double percentage = charaHp/maxHp;
        					gc.fillRect(bar[0], bar[1], AssetManager.BAR_WIDTH*percentage, AssetManager.BAR_HEIGHT);
        					gc.strokeRect(bar[0], bar[1], AssetManager.BAR_WIDTH, AssetManager.BAR_HEIGHT);
        					gc.setFill(null);
            				gc.setStroke(null);
            				gc.setGlobalAlpha(1.0);
            				//
            				//Add the character if one is on the tile
    						//
            				coordISO = AssetManager.toIsoChara(i,j);
							AssetManager.drawChara(gc,currentTile.getCharacter(), coordISO[0], coordISO[1]);
							currentTile.getCharacter().setPosition(i, j, -1);
    					}
        			}
        		}
            	
            }
        }.start();
         
        
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	coordSelected = AssetManager.toGrid(event.getSceneX(), event.getSceneY());
            	int [] oldCoordSelected = board.getCurrentlySelected();
            	//We check if a character is present on the selected tile
            	
            	if(oldCoordSelected[0] == -1) {
            		if(board.getTile(coordSelected).haveCharacter()) {
            			if(board.getTile(coordSelected).getCharacter().getTeam().getName().equals(playerTurn.getName())) {
            				//Changement de case vers un personnage
            				board.setCurrentlySelected(coordSelected);
            				board.resetAction_Mouvmnnt();
            				board.setTabMvmnt();
            				haveCharacter = true;
            				reloadSkillBar(board.getCurrentTileSelected().getCharacter());
            			}
            		}else {
            			//Changement de case
            			board.setCurrentlySelected(coordSelected);
        				board.resetAction_Mouvmnnt();
        				haveCharacter = false;
                		clearSkillBar();
            		}
            	}else if(board.getTile(oldCoordSelected).haveCharacter()) {
            		if(board.getTile(coordSelected).haveCharacter() && board.getTile(coordSelected).getCharacter().getTeam().getName().equals(playerTurn.getName())) {
            			//Changement de case vers un personnage
            			board.getCurrentTileSelected().getCharacter().setUsedSkill(null);
            			board.setCurrentlySelected(coordSelected);
        				board.resetAction_Mouvmnnt();
        				board.setTabMvmnt();
        				haveCharacter = true;
        				reloadSkillBar(board.getCurrentTileSelected().getCharacter());
            		}else if(board.getTile(coordSelected).isMvmnt() && !board.getTile(oldCoordSelected).getCharacter().isUsingSkill()){
            			//Mouvement d'un personnage
            			board.getCurrentTileSelected().getCharacter().setDirection(coordSelected[0],coordSelected[1]);
            			board.getTile(coordSelected).setCharacter(board.getTile(oldCoordSelected).getCharacter());
                		board.getTile(coordSelected).getCharacter().setInGameSprite();
                		board.getTile(board.getCurrentlySelected()).setCharacter(null);
        				board.resetAction_Mouvmnnt();
        				haveCharacter = false;
                		clearSkillBar();
        				board.setCurrentlySelected(-1,-1);
            		}else if(board.getTile(coordSelected).isAction()  && board.getTile(oldCoordSelected).getCharacter().isUsingSkill()) {
            			if(!board.getTile(coordSelected).haveCharacter()) {
            				board.getCurrentTileSelected().getCharacter().setUsedSkill(null);
            				board.setCurrentlySelected(-1,-1);
            				board.resetAction_Mouvmnnt();
            				haveCharacter = false;
                    		clearSkillBar();
            			}else {
            				//Attaque
                			Character c = board.getTile(coordSelected).getCharacter();
                			c.takeDamage(15);
                			if(c.getCurrentAttributes().getHp() == 0) {
                				board.getTile(coordSelected).setCharacter(null);
                			}
                			board.getCurrentTileSelected().getCharacter().setUsedSkill(null);
                			board.changeSelected(-1, -1);
                			board.resetAction_Mouvmnnt();
                    		clearSkillBar();
                    		haveCharacter = false;
                    		nextTurn();
            			}
            		}else {
            			if(!board.getTile(coordSelected).haveCharacter()) {
            				//Changement de case
            				board.getTile(oldCoordSelected).getCharacter().setUsedSkill(null);
            				board.setCurrentlySelected(coordSelected);
            				board.resetAction_Mouvmnnt();
            				haveCharacter = false;
                    		clearSkillBar();
            			}
            		}
            	}else if(board.getTile(coordSelected).haveCharacter()) {
            		if(board.getTile(coordSelected).getCharacter().getTeam().getName().equals(playerTurn.getName())) {
            			//Changement de case vers un personnage
            			board.setCurrentlySelected(coordSelected);
        				board.resetAction_Mouvmnnt();
        				board.setTabMvmnt();
        				haveCharacter = true;
                		reloadSkillBar(board.getCurrentTileSelected().getCharacter());
            		}
            	}else {
            		//Changement de case
            		board.setCurrentlySelected(coordSelected);
    				board.resetAction_Mouvmnnt();
    				haveCharacter = false;
            		clearSkillBar();
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
        
    }
    
	private void clearSkillBar() {
		skillBar.setSkills(new ArrayList<Skill>());
		skillBar.loadSkills();
	}
    
	public void setSelectedSkill(Skill skill) {
		System.out.println("selected : " + skill.getName());
		this.board.getCurrentTileSelected().getCharacter().setUsedSkill(skill);
		this.board.setAction();
	}
	
	private void nextTurn() {
		if (playerTurn.getName().equals(players[0].getTeam().getName())) {
			playerTurn = players[1].getTeam();
		}else {
			playerTurn = players[0].getTeam();
		}
	}
	
}
