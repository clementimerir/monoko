package monoko.objects;

public class Gameboard extends Nameable{

	
	private Tile[][] board;
	private int[] currentlySelected;



	public Gameboard(int id, String name, int width, int height) {
		setId(id);
		setName(name);
		setBoard(width,height);
		setCurrentlySelected(-1, -1);
	}
	
	
	public void setBoard(int width, int height) {
		//Creation aleatoire de la map
        this.board = new Tile[width][height];
        double rand = 0;
        double rand2 = 0;
        int mod = 0;
        int mod2 = 0;
        for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				//Creation de la tile
				rand = Math.ceil(Math.random() * ( 25 - 0 ));
				rand2 = Math.ceil(Math.random() * ( 25 - 0 ));
				mod = (int) rand;
				mod2 = (int) rand2;
				if(mod <= 20) {
					if (mod2 == 1) {
						Character c = new Character(0, "", null, null, "charaup", "cara29x58.png");
				        this.board[i][j] = new Tile(0,c);
					}else if(mod2 == 2) {
						Character c = new Character(0, "", null, null, "charadown", "cara29x58.png");
				        this.board[i][j] = new Tile(0,c);
					}else {
						this.board[i][j] = new Tile(0,null);
					}
				}else if(mod <= 23){
					this.board[i][j] = new Tile(1,null);
				}else {
					this.board[i][j] = new Tile(2,null);
				}
			}
		}
        
        
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
	
	
}
