/**
 * Victor Chen & Madeleine Stanway
 * 300116813 & 300066622
 * Assignment #1
 * Feb 2
 */
public class TicTacToeGame {

    private CellValue[] board;

    private int level;

    private GameState gameState;

    private int lines;

    private int columns;

    private int sizeWin;

    public TicTacToeGame(int lines, int columns, int sizeWin)
    {
        this.columns = columns;
        this.lines = lines;
        this.sizeWin = sizeWin;
        this.gameState = GameState.PLAYING;
        this.level = 0;
        this.board = new CellValue[columns*lines];
        for(int i=0;i<board.length;i++) {board[i] = CellValue.EMPTY;}
    }

    public TicTacToeGame()
    {
        this.columns = 3;
        this.lines = 3;
        this.sizeWin = 3;
        this.gameState = GameState.PLAYING;
        this.level = 0;
        this.board = new CellValue[9];
        for(int i=0;i<9;i++) {board[i] = CellValue.EMPTY;}
    }

    public TicTacToeGame(int lines, int columns)
    {
        this.columns = columns;
        this.lines = lines;
        this.sizeWin = 3;
        this.gameState = GameState.PLAYING;
        this.level = 0;
        this.board = new CellValue[columns*lines];
        for(int i=0;i<board.length;i++) {board[i] = CellValue.EMPTY;}
    }

    public int getLines() {return this.lines;}

    public int getColumns() {return this.columns;}

    public int getLevel() {return this.level;}

    public int getSizeWin() {return this.sizeWin;}

    public GameState getGameState() {return this.gameState;}

    public CellValue nextCellValue()
    {
        if(this.level % 2 == 0 || this.level == 0) {return CellValue.X;}
        else {return CellValue.O;}
    }

    public CellValue valueAt(int i)
    {
     if(i<0 || i >= (this.columns)*(this.lines))
     {
       System.out.println("Error: That index is invalid.");
       return null;
     }
     else {return board[i];}
    }

    public void play(int i)
    {
        if(i<0 || i >= board.length) {System.out.println("That index is out of bounds");}
        else if(board[i] != CellValue.EMPTY) {System.out.println("That cell is not empty");}
        else
        {
            if(level % 2 == 0 || level == 0) {board[i] = CellValue.X;}
            else {board[i] = CellValue.O;}
            setGameState(i);

            switch (gameState)
            {
                case XWIN:
                    System.out.println("Player X has won");
                    level++;
                    break;

                case OWIN:
                    System.out.println("Player O has won");
                    level++;
                    break;

                case DRAW:
                    System.out.println("It's a draw");
                    level++;
                    break;

                default:
                    gameState = GameState.PLAYING;
                    level++;
            }
        }
    }

    private void setGameState(int i)
    {
        if(checkHor() == CellValue.X) {gameState = GameState.XWIN;}
        else if(checkHor() == CellValue.O) {gameState = GameState.OWIN;}
        else if(checkVert() == CellValue.X){gameState = GameState.XWIN;}
        else if(checkVert() == CellValue.O) {gameState = GameState.OWIN;}
        else if(checkDiagR() == CellValue.X) {gameState = GameState.XWIN;}
        else if(checkDiagR() == CellValue.O) {gameState = GameState.OWIN;}
        else if(checkDiagL() == CellValue.X) {gameState = GameState.XWIN;}
        else if(checkDiagL() == CellValue.O) {gameState = GameState.OWIN;}
        else if(checkDraw(board) == false) {gameState = GameState.PLAYING;}
        else {gameState = GameState.DRAW;}
    }

    private CellValue checkHor()
    {
        CellValue[] temp = new CellValue[sizeWin];
        for(int i=0;i<board.length;i+=columns)
        {
            for(int k=i;k<=i+(columns-sizeWin);k++)
            {
                for(int j=0;j<sizeWin;j++) {temp[j] = board[k+j];}
                if(checkAll(temp) == true) {return temp[0];}
            }
        }
        return null;
    }

    private CellValue checkVert()
    {
        CellValue[] temp = new CellValue[sizeWin];
        for(int i=0;i<columns;i++)
        {
            for(int k=i;k<=i+(board.length-sizeWin*columns);k+=columns)
            {
                for(int j=0;j<sizeWin;j++) {temp[j] = board[k+j*columns];}
                if(checkAll(temp) == true) {return temp[0];}
            }
        }
        return null;
    }

    private CellValue checkDiagR()
    {
        CellValue[] temp = new CellValue[sizeWin];
        for(int i=0;i<=board.length-(sizeWin * columns);i+=columns)
        {
            for(int j=i;j<=i+(columns - sizeWin);j++)
            {
                for(int k=0;k<sizeWin;k++) {temp[k] = board[j+k*(columns+1)];}
                if(checkAll(temp) == true) {return temp[0];}
            }
        }
        return null;
    }

    private CellValue checkDiagL()
    {
        CellValue[] temp = new CellValue[sizeWin];
        for(int i=columns-1;i<=board.length-(sizeWin * columns)+columns-1;i+=columns)
        {
            for(int j=i;j>=i-(columns - sizeWin);j--)
            {
                for(int k=0;k<sizeWin;k++) {temp[k] = board[j+k*(columns-1)];}
                if(checkAll(temp) == true) {return temp[0];}
            }
        }
        return null;
    }

    private boolean checkDraw(CellValue[] b)
    {
        for(int i=0;i<b.length;i++) { if(b[i] == CellValue.EMPTY) {return false;} }
        return true;
    }

    private boolean checkAll(CellValue[] b)
    {
        for(int i=0;i<b.length-1;i++)
        {
            if(b[i] == CellValue.EMPTY) {return false;}
            if(b[i] != b[i+1]) {return false;}
        }
        return true;
    }

    public String toString()
    {
        String display = "";
        for(int k=0;k<columns;k++) {display += "----";}
        display += "\n";
        for(int i=0;i<board.length;i+=columns)
        {
            for(int j=i;j<i+columns;j++)
            {
                display += "|";
                if(board[j] == CellValue.EMPTY) {display += "   ";}
                else if(board[j] == CellValue.X) {display += " X ";}
                else{display += " O ";}
            }
            display += "|";
            display += "\n";
            for(int k=0;k<columns;k++) {display += "----";}
            display += "\n";
        }
        return display;
    }

}
