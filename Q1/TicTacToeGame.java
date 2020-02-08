/**
 * The class <b>TicTacToeGame</b> is the
 * class that implements the Tic Tac Toe Game.
 * It contains the grid and tracks its progress.
 * It automatically maintain the current state of
 * the game as players are making moves.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class TicTacToeGame {

	// Gameboard
	private CellValue[] board;

	// Game level
	private int level;

	// Game status
	private GameState gameState;

	// Board rows
	public final int lines;

	// Board columns
	public final int columns;

	// Win size
	public final int sizeWin;

	// Default constructor
	public TicTacToeGame(){
		this(3,3,3);
	}

  /**
	* Line + Column constructor
 	* @param lines board lines
  * @param columns board columns
	*/
	public TicTacToeGame(int lines, int columns){
		this(lines, columns, 3);
	}

  /**
	* Custom constructor
 	* @param lines board lines
  * @param columns board columns
  * @param sizeWin Win size
	*/
	public TicTacToeGame(int lines, int columns, int sizeWin){
		this.lines = lines;
		this.columns = columns;
		this.sizeWin = sizeWin;
		board = new CellValue[lines*columns];
		for(int i = 0; i < lines*columns ; i ++) {board[i] = CellValue.EMPTY;}
		level = 0;
		gameState = GameState.PLAYING;
	}



  /**
	* Level getter
	* @return the value of level
	*/
	public int getLevel() {return level;}


  /**
	* Game state getter
	* @return the value of gameState
	*/
	public GameState getGameState() {return gameState;}

  /**
	* Returns next expected cellValue,
	* @return the value of the enum CellValue
	*/
	public CellValue nextCellValue() {return (level%2 == 0) ? CellValue.X : CellValue.O;}

  /**
	* returns the value  of cell at i.
	* If invalid, error is printed out
 	* @param i index
  * @return board value at index i
	*/
	public CellValue valueAt(int i){
		if(i < 0 || i >= lines*columns) {throw new IllegalArgumentException("Illegal position: " + i);}
		return board[i];
	}

  /**
	* Method is call by next player to play at index i.
	* If invalid, error message is printed
	* If not empty, an error message is printed out
	* If valid, board and state updated
	* To faciliate testing, is is acceptable to keep playing
	* after a game is already won. If that is the case, the
	* a message should be printed out and the move recorded.
	* the  winner of the game is the player who won first
 	* @param i index of cell in board selected by the next player
	*/
	public void play(int i){

		if(i < 0 || i >= lines*columns) {throw new IllegalArgumentException("Illegal position: " + i);}
		if(board[i] != CellValue.EMPTY) {throw new IllegalArgumentException("CellValue not empty: " + i + " in game " + toString());}
		board[i] = nextCellValue();
		level++;
		if(gameState != GameState.PLAYING) {System.out.println("hum, extending a finished game... keeping original winner");}
		else {setGameState(i);}
	}

  /**
	* Updates the gameState after the cell i was set.
	* Method assumes gameState variable was correctly set.
	* Only needs to check if playing at index i has concluded the game
 	* @param i index of cell in board that just been set
	*/
	private void setGameState(int index){

		int left = Math.min(sizeWin-1,index%columns);
		int right= Math.min(sizeWin-1,columns - (index%columns +1));
		if( (countConsecutive(index-1, left,-1,board[index]) +
		   	 countConsecutive(index+1, right,1,board[index]))
			>= sizeWin-1 ) {
			setGameState(board[index]);
			return;
		}

		int up 	= Math.min(sizeWin-1,index/columns);
		int down= Math.min(sizeWin-1, lines - (index/columns +1));
		if( (countConsecutive(index-columns, up,-columns,board[index]) +
		   	 countConsecutive(index+columns, down,columns,board[index]))
			>= sizeWin-1 ) {
			setGameState(board[index]);
			return;
		}

		int upLeft = Math.min(up, left);
		int downRight= Math.min(down, right);
		if( (countConsecutive(index-(columns+1), upLeft,-(columns+1),board[index]) +
		   	 countConsecutive(index+(columns+1), downRight,columns+1,board[index]))
			>= sizeWin-1 ) {
			setGameState(board[index]);
			return;
		}

		int upRight= Math.min(up, right);
		int downLeft = Math.min(down, left);
		if( (countConsecutive(index-(columns-1), upRight,-(columns-1),board[index]) +
		   	 countConsecutive(index+(columns-1), downLeft,columns-1,board[index]))
			>= sizeWin-1 ) {
			setGameState(board[index]);
			return;
		}

		if (level == lines*columns) {gameState = GameState.DRAW;}
		else {gameState = GameState.PLAYING;}
	}

	private int countConsecutive(int startingPosition, int numberOfSteps,
		int stepGap, CellValue value){
		int result= 0;
		for(int i = 0; i < numberOfSteps;i++){
			if(board[startingPosition + i*stepGap] != value) {break;}
			result++;
		}
		return result;
	}

	private void setGameState(CellValue value){
		switch(value){
			case X:
				gameState = GameState.XWIN;
				break;
			case O:
				gameState = GameState.OWIN;
				break;
			default:
				throw new IllegalArgumentException("cannot set Game State to value " + value);
		}
	}

	public final String NEW_LINE = System.getProperty("line.separator");

  /**
	* Returns a String representation of the game
 	* @return String representation of the game
	*/
	public String toString(){
		String res = "";
		for(int i = 0; i < lines ; i++){
			if(i>0){
				for(int j = 0; j < 4*columns - 1; j++) {res+="-";}
				res+= Utils.NEW_LINE;
			}
			for(int j = 0; j < columns ; j++){
				switch(board[i*columns + j]){
					case X:
						res+= " X ";
						break;
					case O:
						res+= " O ";
						break;
					default:
						res+=  "   ";
				}
				if(j<columns - 1) {res += "|";}
				else { res += Utils.NEW_LINE;}
			}
		}
		return res ;
	}
}
