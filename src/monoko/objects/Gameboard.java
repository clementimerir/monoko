package monoko.objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Gameboard extends Nameable{

	
	private Tile[][] board;
	private int[] currentlySelected;
	String items[] = {"Sword", "Bow", "Pyromancy Tome" , "Scepter"};
	Player p1;
	Player p2;
	
	public Gameboard(int id, String name, int width, int height, Player p1, Player p2) {
		setId(id);
		setName(name);
		setPlayers(p1, p2);
		setBoard(width,height);
		setCurrentlySelected(-1, -1);
	}
	
	
	public void setBoard(int width, int height) {
		//Creation aleatoire de la map
		
        this.board = new Tile[width][height];
        this.mapReader("map1.txt");
        //double rand = 0;
        //double rand2 = 0;
        int mod = 0;
        //int mod2 = 0;
        int x=0;
        int x2=0;
        int widthP1 = this.getPlayer1().getTeam().getCharacters().size();
        int widthP2 = this.getPlayer2().getTeam().getCharacters().size();
        int mod2 = 0;
       /*
        for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				//Creation de la tile
				rand = Math.ceil(Math.random() * ( 25 - 0 ));
				mod = (int) rand;
				
				if(j == 0) {
					mod2 = width/widthP1;
					if(i % mod2 == 1 && x < p1.getTeam().getCharacters().size()) {
						p1.getTeam().getCharacters().get(x).setVision(2);
						p1.getTeam().getCharacters().get(x).setInGameSprite();
						this.board[i][j] = new Tile(0,0,p1.getTeam().getCharacters().get(x));
						x++;
					}else {
						this.board[i][j] = new Tile(0,0,null);
					}
				}else if(j == height-1) {
					mod2 = width/widthP2;
					if(i % mod2 == 1  && x2 < p2.getTeam().getCharacters().size()) {
						this.board[i][j] = new Tile(0,0,p2.getTeam().getCharacters().get(x2));
						x2++;
					}else {
						this.board[i][j] = new Tile(0,0,null);
					}
				}else {
					if(mod <= 20) {
						this.board[i][j] = new Tile(0,0,null);
					}else if(mod <= 23){
						this.board[i][j] = new Tile(1,1,null);
					}else {
						this.board[i][j] = new Tile(2,2,null);
					}
				}
			}
		}
		*/
        mod = width/widthP1;
        mod2 = width/widthP2;
        int j1 = 0;
        int j2 = height-1;
        for (int i = 0; i < width; i++) {
        	if(i % mod == 1 && x < p1.getTeam().getCharacters().size()) {
    			p1.getTeam().getCharacters().get(x).setVision(2);
    			p1.getTeam().getCharacters().get(x).setInGameSprite();
    			this.board[i][j1].setCharacter(p1.getTeam().getCharacters().get(x));
    			x++;
    		}
        	if(i % mod2 == 1  && x2 < p2.getTeam().getCharacters().size()) {
				this.board[i][j2].setCharacter(p2.getTeam().getCharacters().get(x2));
				x2++;
			}
        }
        
        
	}
	
	//Lis un fichier text contenant une map et creer le board en fonction
	public void mapReader(String mapName) {
		try{
			InputStream flux=new FileInputStream(new File("./res/data/"+mapName).getAbsolutePath()); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String ligne;
			int j = 0;
			
			while ((ligne=buff.readLine())!=null){
				String num[] =ligne.split(",");
				for(int i = 0; i < getBoard().length; i++) {
					int image = Integer.parseInt(num[i]);
					this.getBoard()[i][j] = new Tile(image % 3,image,null);
				}
				j++;
				
			}
			buff.close(); 
			}		
			catch (Exception e){
			System.out.println(e.toString());
			}
		
	}
	
	
	public void setPlayers(Player p1, Player p2) {
		setPlayer1(p1);
		setPlayer2(p2);
	}
	
	public Player getPlayer1() {
		return p1;
	}


	public void setPlayer1(Player p1) {
		this.p1 = p1;
	}


	public Player getPlayer2() {
		return p2;
	}


	public void setPlayer2(Player p2) {
		this.p2 = p2;
	}
	
	public void setBoard(Tile[][] board) {
		this.board = board;
	}
	
	public Tile[][] getBoard() {
		return this.board;
	}
	
	public Tile getTile(int i, int j) {
		return board[i][j];
	}
	
	public Tile getTile(int[] tile) {
		return board[tile[0]][tile[1]];
	}
	
	public int[] getCurrentlySelected() {
		return currentlySelected;
	}
	
	public Tile getCurrentTileSelected() {
		Tile tile = board[currentlySelected[0]][currentlySelected[1]];
		return tile;
	}


	public void setCurrentlySelected(int[] currentlySelected) {
		this.currentlySelected = currentlySelected;
	}
	
	public void setCurrentlySelected(int i, int j) {
		int[] newS = new int[] {i,j};
		this.currentlySelected = newS;
	}
	
	public void changeSelected(int i, int j) {
		
			int[] oldS = this.getCurrentlySelected();
			if(oldS[0] >= 0) {
				this.board[oldS[0]][oldS[1]].setSelected(false);
			}
			
			if(i == -1 || j == -1) {
				this.setCurrentlySelected(-1, -1);
			}else{
				this.board[i][j].setSelected(true);
				this.setCurrentlySelected(i, j);
			}
			
	}
	
	
	public void changeSelected(int[] currentlySelected) {
		int[] oldS = this.getCurrentlySelected();
		if(oldS[0] >= 0) {
			this.board[oldS[0]][oldS[1]].setSelected(false);
		}
		this.board[currentlySelected[0]][currentlySelected[1]].setSelected(true);
		this.setCurrentlySelected(currentlySelected);
	}
	
	public boolean haveSelected() {
		if(this.getCurrentlySelected()[0] == -1) {
			return false;
		}else {
			return true;
		}
	}
	
	public void setTabMvmnt(){
		
		int speed = getCurrentTileSelected().getCharaMvmnt();
		boolean[][] array = new boolean[speed*2+1][speed*2+1];
		int [][] dist;
		int x = 0;
		int y = 0;
		for (int i=this.getCurrentlySelected()[0]-speed; i<=this.getCurrentlySelected()[0]+speed; i++) {
			y = 0;
			for(int j = this.getCurrentlySelected()[1]-speed; j<=this.getCurrentlySelected()[1]+speed; j++) {
				if((i >= 0 && i< board.length) && (j>=0 && j< board[0].length)) {
					array[x][y] = this.board[i][j].isNotObstacle(getCurrentTileSelected());
				}else {
					array[x][y] = false;
				}
				y++;
			}
			x++;
		}	
		
		Dijkstra dij = new Dijkstra(array, speed);
		dist = dij.resolve();
		
		x=0;
		y=0;
		
		for (int i=this.getCurrentlySelected()[0]-speed; i<=this.getCurrentlySelected()[0]+speed; i++) {
			y = 0;
			for(int j = this.getCurrentlySelected()[1]-speed; j<=this.getCurrentlySelected()[1]+speed; j++) {
				
				if((i >= 0 && i < board.length) && (j >= 0 && j < board[0].length)) {
					if(dist[x][y] <= speed && !board[i][j].haveCharacter()) {
						this.board[i][j].setMvmnt(true);
					}else {
						this.board[i][j].setMvmnt(false);
					}
				}
				y++;
			}
			x++;
		}
		
	}
	
	public void resetMvmnt() {
		for (int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				board[i][j].setMvmnt(false);
			}
		}
	}
	
	
	public void setAction() {
		
		Character c = this.getCurrentTileSelected().getCharacter();
		int range = c.getSkills().get(0).getRange();
		
		boolean[] stop = new boolean[4];
		
		for (int i = 1; i<=range; i++) {
			
			if(!stop[0]) {
				if(this.getCurrentlySelected()[0]+i < board.length) {
					if(this.board[this.getCurrentlySelected()[0]+i][this.getCurrentlySelected()[1]].getType() == 2) {
						stop[0] = true;
					}else if(this.board[this.getCurrentlySelected()[0]+i][this.getCurrentlySelected()[1]].getType() != 1){
						this.board[this.getCurrentlySelected()[0]+i][this.getCurrentlySelected()[1]].setAction(true);
					}
					
				}else {
					stop[0] = true;
				}
			}
			
			if(!stop[1]) {
				if(this.getCurrentlySelected()[0]-i >= 0) {
					if(this.board[this.getCurrentlySelected()[0]-i][this.getCurrentlySelected()[1]].getType() == 2) {
						stop[1] = true;
					}else if(this.board[this.getCurrentlySelected()[0]-i][this.getCurrentlySelected()[1]].getType() != 1){
						this.board[this.getCurrentlySelected()[0]-i][this.getCurrentlySelected()[1]].setAction(true);
					}
				}else {
					stop[1] = true;
				}
			}
			
			if(!stop[2]) {
				if(this.getCurrentlySelected()[1]+i < board[0].length) {
					if(this.board[this.getCurrentlySelected()[0]][this.getCurrentlySelected()[1]+i].getType() == 2) {
						stop[2] = true;
					}else if(this.board[this.getCurrentlySelected()[0]][this.getCurrentlySelected()[1]+i].getType() != 1){
						this.board[this.getCurrentlySelected()[0]][this.getCurrentlySelected()[1]+i].setAction(true);
					}
				}else {
					stop[2] = true;
				}
			}
			
			if(!stop[3]) {
				if(this.getCurrentlySelected()[1]-i >= 0) {
					if(this.board[this.getCurrentlySelected()[0]][this.getCurrentlySelected()[1]-i].getType() == 2) {
						stop[3] = true;
					}else if(this.board[this.getCurrentlySelected()[0]][this.getCurrentlySelected()[1]-i].getType() != 1){
						this.board[this.getCurrentlySelected()[0]][this.getCurrentlySelected()[1]-i].setAction(true);
					}
				}else {
					stop[3] = true;
				}
			}
		}
	}
	
	public void resetAction() {
		for (int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				board[i][j].setAction(false);
			}
		}
	}
	
	
	public void resetAction_Mouvmnnt() {
		for (int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				board[i][j].setMvmnt(false);
				board[i][j].setAction(false);
			}
		}
	}
	
}

class Dijkstra {
	private List<TileToCheck> tilesQueue = new ArrayList<TileToCheck>();
	private List<TileToCheck> tilesVisited = new ArrayList<TileToCheck>();
	int distance;
	
	public Dijkstra(boolean[][] board, int distance) {
		int x, y;
		TileToCheck t;
		this.distance = distance;
		
		for(y=0; y<board.length; y++) {
			for(x=0; x<board.length; x++) {
				t = new TileToCheck(x,y,1000);
				if(x==distance && y==distance)
					t.minDist = 0;
				if(board[x][y]==false)
					t.minDist = -1;
				tilesQueue.add(t);
			}
		}
		
		for(y=0; y<board.length; y++) {
			for(x=0; x<board.length; x++) {
				t = tilesQueue.get(x+y*board.length);
				if(board[x][y]==true) {
					if(t.x+1<board.length) {
						if(board[x+1][y]==true) {
							t.addNeighbour(tilesQueue.get((x+1)+y*board.length));
						}
					}
					if(t.x-1>=0) {
						if(board[x-1][y]==true) {
							t.addNeighbour(tilesQueue.get((x-1)+y*board.length));
						}
					}
					if(t.y+1<board.length) {
						if(board[x][y+1]==true) {
							t.addNeighbour(tilesQueue.get(x+(y+1)*board.length));
						}
					}
					if(t.y-1>=0) {
						if(board[x][y-1]==true) {
							t.addNeighbour(tilesQueue.get(x+(y-1)*board.length));
						}
					}
				}
			}
		}

		for(int i=0; i<tilesQueue.size(); i++) {
			if(tilesQueue.get(i).minDist == -1) {
				tilesQueue.remove(i);
				i--;
			}
		}
	}
	
	public int[][] resolve() {
		int x,y,lastIndex;
		TileToCheck t;
		int[][] distancesTable = new int[2*distance+1][2*distance+1];
		for(y=0; y<distancesTable.length; y++) {
			for(x=0; x<distancesTable.length; x++) {
				distancesTable[x][y] = 1000;
			}
		}
		while(!tilesQueue.isEmpty()) {
			t = tilesQueue.get(0);
			lastIndex=0;
			for(int i=1; i<tilesQueue.size(); i++) {
				if(tilesQueue.get(i).minDist < t.minDist) {
					t=tilesQueue.get(i);
					lastIndex=i;
				}
			}
			tilesVisited.add(t);
			tilesQueue.remove(lastIndex);
			for(TileToCheck n : t.neighbours) {
				if(t.minDist+1 < n.minDist)
					n.minDist = t.minDist+1;
			}
		}
		
		for(int i=0; i<tilesVisited.size(); i++) {
			t=tilesVisited.get(i);
			distancesTable[t.x][t.y]=t.minDist;
		}
		return distancesTable;
	}
}

class TileToCheck {
	public int x;
	public int y;
	public int minDist;
	public List<TileToCheck> neighbours = new ArrayList<TileToCheck>();
	
	public TileToCheck(int x, int y, int minDist) {
		this.x = x;
		this.y = y;
		this.minDist = minDist;
	}
	
	public void addNeighbour(TileToCheck t) {
		neighbours.add(t);
	}
}
