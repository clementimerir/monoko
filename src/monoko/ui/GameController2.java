package monoko.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import monoko.objects.Character;
import monoko.objects.Game;
import monoko.objects.Gameboard;
import monoko.objects.Player;
import monoko.objects.Skill;
import monoko.objects.Team;
import monoko.objects.Tile;
import monoko.utils.Action;
import monoko.utils.AssetManager;
import monoko.utils.FxmlManager;
import monoko.utils.Manager;
import monoko.utils.Network;

public class GameController2 extends GameBase{
	
	public int[] coordSelected = new int[] {-1,-1};
	public int[] coordMouse = new int[] {-1,-1};
	SkillBarController2 skillBar = new SkillBarController2(this);
	Gameboard board;
	Player[] players = new Player[2]; //Current players for the game [0] is team 1 and [1] is team 2
	Team playerTurn; //Contain the team whose turn it is
	Player userTurn;
	boolean haveCharacter = false; //Boolean to check if a character is currently selected
	Character caraTurn = null; //First Character played by the user
	int mvmntUsed = 0;//Contains how many cases did the player used
	int skillUsed = 0;
	//Timer
	Timeline timer;
	Timeline countdown = new Timeline();
	Timeline netDemand =new Timeline();
	double counter = 0;
	double turnDuration = 30;
	private Game _game;
	Network net = Manager.getInstance().getNetwork();
	int numAction = 0;
	boolean appliedNetChange = true;
	
	public GameController2(Game game) {
		_game = game;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadPlayers();
		start();
		//Manager.getInstance().getController().setBackgroundMusic("./res/sound/fight-theme.wav");
		root.getChildren().add( new FxmlManager("./ui/skillBar.fxml", skillBar).load() );
		_vsLabel.setText( new StringBuilder("Red Team (").append(players[0].getTeam().getName()).append(")").append(" vs ").append("Blue Team (").append(players[1].getTeam().getName()).append(")").toString() );
		_currentPlayerLabel.setText(players[0].getTeam().getName());
	}

	public void reloadSkillBar(Character character) {
		
		List<Skill> skills = character.getSkills();
		skillBar.setSkills(skills);
		skillBar.loadSkills();
	}
 
	public void loadPlayers() {
//		players[0] = new Player(1, "Player 1", Manager.getInstance().getController().getUser().getTeams().get(0));
//		players[1] = new Player(2, "Player 2", Manager.getInstance().getController().getUser().getTeams().get(1));
		
		players[0] = _game.getPlayer1();
		players[1] = _game.getPlayer2();
		
		for(Character currentCharacter : players[0].getTeam().getCharacters()) {
			currentCharacter.setTeam(players[0].getTeam());
		}
		for(Character currentCharacter : players[1].getTeam().getCharacters()) {
			currentCharacter.setTeam(players[1].getTeam());
		}
//		System.out.println(1);
	}
	
    public void start() 
    {
    	
		AssetManager.init();
		
		//
		//TO CHANGE
		//
//		players = AssetManager.teamCreator();
//		loadPlayers();//remove this line for pre-made teams
		//
		//
		//
    	board = new Gameboard(0, "map1", AssetManager.TILES_W, AssetManager.TILES_H, players[0], players[1]);
    	playerTurn = players[0].getTeam();
    	userTurn = players[0];

    	
        final Canvas canvas = new Canvas(AssetManager.GAME_WIDTH, AssetManager.GAME_HEIGHT);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add( canvas );
        
        
        timer = new Timeline(new KeyFrame(Duration.seconds(turnDuration), ev -> {
        	nextTurn();
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        
        
        
        countdown = new Timeline(new KeyFrame(Duration.seconds(0.1), ev -> {
    	    counter += 0.1;
    	    _turnTimerIndicator.setProgress( counter / turnDuration );
        }));
        countdown.setCycleCount(Animation.INDEFINITE);
        
        if(net.getUser().getUsername().equals(userTurn.getName())) {
        	timer.play();
        	countdown.play();
        }
        
        netDemand = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
        	try {
				net.getGameUpdates(_game);
				appliedNetChange = false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }));
        
        netDemand.setCycleCount(Animation.INDEFINITE);
        netDemand.play();
        
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
            	
            	
            	if (!appliedNetChange) {
            		_game.getActions();
            		if(_game.getLastActionID() > numAction) {
            			for(int i = numAction+1; i<=_game.getLastActionID(); i++) {
            				Action currentAction = _game.getActions().get(i);
            				if(currentAction.getSkillName() == "newTurn"){
            					myTurn();
            				}else {
            					makeAction(currentAction);
            				}
            			}
            			numAction = _game.getLastActionID();
            		}
            		appliedNetChange = true;
            	}
            	
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
            	
            }
        }.start();
         
        
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	coordSelected = AssetManager.toGrid(event.getSceneX(), event.getSceneY());
            	int [] oldCoordSelected = board.getCurrentlySelected();
            	//We check if a character is present on the selected tile
            	
            	//
				//TO NETWORK
				//
				//TODO RAJOUT D'UN IF ENGLOBANT POUR LA TEAM
            	//if (net.getUser().getUsername() ==  userTurn.getName()) {
            		//
    				//
    				//
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
            				//
            				//TO NETWORK
            				//
            				//TODO
            				sendToNetwork("Move", coordSelected);
            				//
            				//
            				//
                		}else if(skillUsed < 2 && board.getTile(coordSelected).isAction()  && board.getTile(oldCoordSelected).getCharacter().isUsingSkill() && board.getTile(coordSelected).haveCharacter()) {
            				//Skill used ! <-- Character selected but only one skill allowed now
                			//
            				//TO NETWORK
            				//
            				//TODO
                			sendToNetwork(caraTurn.getUsedSkill().getName(), coordSelected);
            				//
            				//
            				//
                			Character c = board.getTile(coordSelected).getCharacter();
                			caraTurn.useSkill(c, caraTurn.getUsedSkill(), coordSelected[0], coordSelected[1]);
                			if(c.getCurrentAttributes().getHp() <= 0) {
                				board.getTile(coordSelected).setCharacter(null);
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
            				//
            				//TO NETWORK
            				//
            				//TODO
            				sendToNetwork("Move", coordSelected);
            				//
            				//
            				//
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
                				//
                				//TO NETWORK
                				//
                				//TODO
                    			sendToNetwork(caraTurn.getUsedSkill().getName(), coordSelected);
                				//
                				//
                				//
                    			Character c = board.getTile(coordSelected).getCharacter();
                    			caraTurn = board.getTile(oldCoordSelected).getCharacter();
                    			caraTurn.useSkill(c, caraTurn.getUsedSkill(), coordSelected[0], coordSelected[1]);
                    			if(c.getCurrentAttributes().getHp() <= 0) {
                    				board.getTile(coordSelected).setCharacter(null);
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
        	//}
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
    
    
    private boolean sendToNetwork(String actionFaite, int[] target) {
    	Action lastAction = null;
    	
    	if(actionFaite.equals("newTurn")) {
    		lastAction = new Action(numAction, -1, -1, actionFaite, target[0], target [1]);
    	}else {
    		lastAction = new Action(numAction, caraTurn.getId(), caraTurn.getTeam().getId(), actionFaite, target[0], target [1]);
    	}
    	
    	try {
			net.updateGame(_game, lastAction);
			numAction++;
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
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
			userTurn = players[1];
			_currentPlayerLabel.setText(players[1].getTeam().getName());
		}else {
			playerTurn = players[0].getTeam();
			userTurn = players[0];
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
        countdown.stop();
        
        counter = 0;
        
        sendToNetwork("newTurn",new int[] {-1,-1});
	}
	
	private void myTurn() {
		
		if (playerTurn.getName().equals(players[0].getTeam().getName())) {
			playerTurn = players[1].getTeam();
			userTurn = players[1];
			_currentPlayerLabel.setText(players[1].getTeam().getName());
		}else {
			playerTurn = players[0].getTeam();
			userTurn = players[0];
			_currentPlayerLabel.setText(players[0].getTeam().getName());
		}
		
		timer.play();
		countdown.play();
	}
	
	private void makeAction(Action currentAction) {
		String actionName = currentAction.getSkillName();
		int [] t = findCharac(currentAction);
		Character c = board.getBoard()[t[0]][t[1]].getCharacter();
		if(actionName == "Move") {
			//Bouge le perso connard
			board.getBoard()[currentAction.getPosX()][currentAction.getPosY()].setCharacter(c);
			board.getTile(t).setCharacter(null);
		}else {
			Skill skillUsed = new Skill(currentAction.getSkillName());
			Character target = board.getBoard()[currentAction.getPosX()][currentAction.getPosY()].getCharacter();
			c.useSkill(target, skillUsed, currentAction.getPosX(), currentAction.getPosY());
			if(target.getCurrentAttributes().getHp() <= 0) {
				board.getTile(t).setCharacter(null);
			}
		}
	}
	
	
	private int[] findCharac(Action currentAction) {
		Character c = null;
		for(int i = 0; i<board.getBoard().length; i++) {
			for(int j = 0; j<board.getBoard()[0].length; j++) {
				c = board.getBoard()[i][j].getCharacter();
				if(c.getId() == currentAction.getCharacterID() && c.getTeam().getId() == currentAction.getTeamID()) {
					return new int [] {i,j};
				}
			}
		}
		return null;
	}

	@Override
	public void onMainMenuClicked() {
		// TODO Auto-generated method stub
		
	}
	
	
}
