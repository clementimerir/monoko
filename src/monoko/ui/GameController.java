package monoko.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.beans.property.*;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
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
	Gameboard board;
	Player[] players = new Player[2]; //Current players for the game [0] is team 1 and [1] is team 2
	Team playerTurn; //Contain the team whose turn it is
	boolean haveCharacter = false; //Boolean to check if a character is currently selected
	Character caraTurn = null; //First Character played by the user
	int mvmntUsed = 0;//Contains how many cases did the player used
	int skillUsed = 0;
	//Timer
	Timeline timer, animation;
	Timeline countdown = new Timeline();
	double counter = 0;
	double turnDuration = 20;
	
	//Variable d'animation
	boolean animationStart = false;
	Image animImage = null;
	WritableImage currentSprite = null;
	int[] animPlace = new int[] {0,0};
	DoubleProperty frame  = new SimpleDoubleProperty();
	boolean endGame = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		start();
		//Manager.getInstance().getController().setBackgroundMusic("./res/sound/fight-theme.wav");
		root.getChildren().add( new FxmlManager("./ui/skillBar.fxml", skillBar).load() );
		_vsLabel.setText( new StringBuilder("Red Team (").append(players[0].getTeam().getName()).append(")").append(" vs ").append("Blue Team (").append(players[1].getTeam().getName()).append(")").toString() );
		_currentPlayerLabel.setText(players[0].getTeam().getName());
		
		root.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    nextTurn();
                }
            }
		});
		
	}

	public void reloadSkillBar(Character character) {
		
		List<Skill> skills = character.getSkills();
		skillBar.setSkills(skills);
		skillBar.loadSkills();
	}
 
	public void loadPlayers() {
		players[0] = new Player(1, "Player 1", Manager.getInstance().getController().getUser().getTeams().get(0));
		players[1] = new Player(2, "Player 2", Manager.getInstance().getController().getUser().getTeams().get(1));
		
		for(Character currentCharacter : players[0].getTeam().getCharacters()) {
			currentCharacter.setTeam(players[0].getTeam());
//			currentCharacter.setCurrentAttributes(currentCharacter.getBaseAttributes());
		}
		for(Character currentCharacter : players[1].getTeam().getCharacters()) {
			currentCharacter.setTeam(players[1].getTeam());
//			currentCharacter.setCurrentAttributes(currentCharacter.getBaseAttributes());
		}
		
//		try {
//			Manager.getInstance().getNetwork().saveTeam(players[0].getTeam());
//			Manager.getInstance().getNetwork().saveTeam(players[1].getTeam());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		System.out.println(1);
	}
	
    public void start() 
    {
    	
		AssetManager.init();
		
		//
		//TO CHANGE
		//
		players = AssetManager.teamCreator();
//		if( Manager.getInstance().getController().getUser().getTeams().size() >= 2) {
//			loadPlayers();//remove this line for pre-made teams
//		}
		
		//
		//
		//
    	board = new Gameboard(0, "map1", AssetManager.TILES_W, AssetManager.TILES_H, players[0], players[1]);
    	playerTurn = players[0].getTeam();

    	
        final Canvas canvas = new Canvas(AssetManager.GAME_WIDTH, AssetManager.GAME_HEIGHT);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add( canvas );
        
        
        timer = new Timeline(new KeyFrame(Duration.seconds(turnDuration), ev -> {
        	nextTurn();
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
        
        countdown = new Timeline(new KeyFrame(Duration.seconds(0.1), ev -> {
    	    counter += 0.1;
    	    _turnTimerIndicator.setProgress( counter / turnDuration );
        }));
        countdown.setCycleCount(Animation.INDEFINITE);
        countdown.play();
        
        //Time line pour Animation
        animation = new Timeline(
            new KeyFrame(Duration.seconds(0),
                    new KeyValue(frame, 0)
            ),
            new KeyFrame(Duration.seconds(0.5),
                    new KeyValue(frame, 5)
            )
        );
        
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
        		        
		        		AssetManager.drawTile(gc, currentTile, coordISO[0], coordISO[1]);
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
        					gc.setFill( currentTile.getCharacter().getTeam().equals(players[0].getTeam()) ? Color.RED : Color.BLUE );
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
            	
            	//Partie sur l'affichage des animations;
            	
        		
        		
            	if(animationStart) {
            		
            		animation.play();
            		
            		if(frame.doubleValue() >= 4) {
            			animationStart = false;
            		}else {
            			currentSprite = new WritableImage(animImage.getPixelReader(), frame.intValue()*AssetManager.ANIM_WIDTH, 0,  AssetManager.ANIM_WIDTH, AssetManager.ANIM_HEIGHT);
                		gc.drawImage(currentSprite, animPlace[0], animPlace[1], AssetManager.ANIM_WIDTH, AssetManager.ANIM_HEIGHT);
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
            	if(endGame) {
            		onMainMenuClicked();
            	}
            	
            	if(caraTurn != null) {
            		if(board.getTile(coordSelected).isMvmnt() && !board.getTile(oldCoordSelected).getCharacter().isUsingSkill()){
            			//Mouvement d'un personnage
            			mvmntUsed = Math.abs(oldCoordSelected[0]-coordSelected[0])+Math.abs(oldCoordSelected[1]-coordSelected[1]);
            			caraTurn.moved(mvmntUsed);
            			board.getCurrentTileSelected().getCharacter().setDirection(coordSelected[0],coordSelected[1]);
            			board.getTile(coordSelected).setCharacter(board.getTile(oldCoordSelected).getCharacter());
                		board.getTile(coordSelected).getCharacter().setInGameSprite();
                		board.getTile(board.getCurrentlySelected()).setCharacter(null);
        				board.resetAction_Mouvmnnt();
        				board.changeSelected(coordSelected);
        				board.setTabMvmnt();
        				haveCharacter = true;
        				board.setCurrentlySelected(coordSelected);
            		}else if(skillUsed < 2 && board.getTile(coordSelected).isAction()  && board.getTile(oldCoordSelected).getCharacter().isUsingSkill() && board.getTile(coordSelected).haveCharacter()) {
        				//Skill used ! <-- Character selected but only one skill allowed now
            			Character c = board.getTile(coordSelected).getCharacter();
            			animateSkill(caraTurn.getUsedSkill(), coordSelected);
            			caraTurn.useSkill(c, caraTurn.getUsedSkill(), coordSelected[0], coordSelected[1]);
            			if(c.getCurrentAttributes().getHp() <= 0) {
            				board.getTile(coordSelected).setCharacter(null);
            				endGame = checkEndGame();
            			}
            			board.getCurrentTileSelected().getCharacter().setUsedSkill(null);
            			board.resetAction_Mouvmnnt();
            			board.setTabMvmnt();
            			caraTurn = board.getTile(oldCoordSelected).getCharacter();
            			skillUsed++;
            			if(skillUsed >= 2) {
            				clearSkillBar();
            			}	
                    		
        			}
            	}else if(oldCoordSelected[0] == -1) {
            		//No tile selected and so no character
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
            		//The old tile had a character (from the good team)
            		if(board.getTile(coordSelected).isMvmnt() && !board.getTile(oldCoordSelected).getCharacter().isUsingSkill()){
            			//Mouvement d'un personnage <-- Character selected and still 2 skill able to be used
            			caraTurn = board.getTile(oldCoordSelected).getCharacter();
            			mvmntUsed = Math.abs(oldCoordSelected[0]-coordSelected[0])+Math.abs(oldCoordSelected[1]-coordSelected[1]);
            			caraTurn.moved(mvmntUsed);
            			board.getCurrentTileSelected().getCharacter().setDirection(coordSelected[0],coordSelected[1]);
            			board.getTile(coordSelected).setCharacter(board.getTile(oldCoordSelected).getCharacter());
                		board.getTile(coordSelected).getCharacter().setInGameSprite();
                		board.getTile(board.getCurrentlySelected()).setCharacter(null);
        				board.resetAction_Mouvmnnt();
        				board.changeSelected(coordSelected);
        				board.setTabMvmnt();
        				haveCharacter = true;
        				board.setCurrentlySelected(coordSelected);
            		}else if(board.getTile(coordSelected).isAction()  && board.getTile(oldCoordSelected).getCharacter().isUsingSkill()) {
            			if(!board.getTile(coordSelected).haveCharacter()) {
            				//Change tile so no action
            				board.getCurrentTileSelected().getCharacter().setUsedSkill(null);
            				board.setCurrentlySelected(-1,-1);
            				board.resetAction_Mouvmnnt();
            				haveCharacter = false;
                    		clearSkillBar();
            			}else {
            				//Skill used ! <-- Character selected but only one skill allowed now
                			Character c = board.getTile(coordSelected).getCharacter();
                			caraTurn = board.getTile(oldCoordSelected).getCharacter();
                			//Lancer l'animation d'une attaque
                			animateSkill(caraTurn.getUsedSkill(), coordSelected);
                			caraTurn.useSkill(c, caraTurn.getUsedSkill(), coordSelected[0], coordSelected[1]);
                			if(c.getCurrentAttributes().getHp() <= 0) {
                				board.getTile(coordSelected).setCharacter(null);
                				endGame = checkEndGame();
                			}
                			board.getCurrentTileSelected().getCharacter().setUsedSkill(null);
                			board.resetAction_Mouvmnnt();
                			board.setTabMvmnt();
                			skillUsed++;
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
    
    
    //Fonction appel�es lors des attaques
    private void animateSkill(Skill skill, int[] target) {
    	String skillName = skill.getName();
    	animPlace = AssetManager.toIso(target[0],target[1]);
    	animationStart = true;
		frame.set(0.0);
    	switch(skillName) {
    	case "Sword":
    		animImage = AssetManager.slash;
    		break;
    	case "Bow":
    		animImage = AssetManager.slash;
    		break;
    	case "Scepter":
    		animImage = AssetManager.slash;
    		break;
    	case "Pyromancy Tome":
    		animImage = AssetManager.slash;
    		break;
    	default: 
            break;
    	}
    }
    
	@Override
	public void onMainMenuClicked() {
		Manager.getInstance().getController().getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/mainMenu.fxml", Manager.getInstance().getMainMenu()).load());		
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
			_currentPlayerLabel.setText(players[1].getTeam().getName());
		}else {
			playerTurn = players[0].getTeam();
			_currentPlayerLabel.setText(players[0].getTeam().getName());
		}
		haveCharacter = false; //Boolean to check if a character is currently selected
		mvmntUsed = 0;//Contains how many cases did the player used
		skillUsed = 0;
		if(caraTurn != null) {
			caraTurn.resetSpeed();
			caraTurn.setUsedSkill(null);
			caraTurn = null;
			clearSkillBar();
		}
		board.resetAction_Mouvmnnt();
		board.changeSelected(-1, -1);
		
        timer.stop();
        timer.play();
        
        counter = 0;
	}
	
	private boolean checkEndGame(){
		Team team1 = players[0].getTeam();
		Team team2 = players[1].getTeam();
		int team1NB = team1.getCharacters().size();
		int team2NB = team2.getCharacters().size();
		int counterT1 = 0;
		int counterT2 = 0;
		for(int i=0; i<board.getBoard().length; i++) {
			for(int j = 0; j<board.getBoard()[0].length; j++) {
				if (board.getBoard()[i][j].getCharacter() != null) {
					if (board.getBoard()[i][j].getCharacter().getTeam().getName().equals(team1.getName())) {
						counterT1++;
					}else if (board.getBoard()[i][j].getCharacter().getTeam().getName().equals(team2.getName())) {
						counterT2++;
					}
				}
			}
		}
		if(counterT1 == 0 || counterT2 == 0) {
			return true;
		}else {
			return false;
		}
		
	}
	
}
