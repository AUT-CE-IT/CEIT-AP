package com.company;

import java.util.ArrayList;

public class OthelloBoard {
    /**
     * points include x and y matrix-form coordinate
     * min = 0 , max = 7
     */
    public static class Points {
        int x, y;
        Points(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString(){
            return "["+x+", "+y+"]";
        }

        @Override
        public boolean equals(Object o){
            return o.hashCode()==this.hashCode();
        }

        @Override
        public int hashCode() {
            return Integer.parseInt(x+""+y);
        }
    }

    int whiteScore, blackScore, remainingPoints;
    private char[] xRow = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private char[][] othelloBoard;

    /**
     * constructor for basic game board
     * I use simple B(black) and W(white) characters
     */
    public OthelloBoard() {
        othelloBoard = new char[][]{
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', 'W', 'B', '_', '_', '_',},
                {'_', '_', '_', 'B', 'W', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
        };
    }

    public char[][] getOthelloBoard() {
        return othelloBoard;
    }

    /**
     * this function moves on matrix on its diameter, vertical, horizontal in both direction
     * to see possible moves and save them in an ArrayList of points
     *
     * @param player
     * @param opponent
     * @param placeablePoints
     */
    private void placeablePoints(char player, char opponent, ArrayList<Points> placeablePoints) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (othelloBoard[i][j] == opponent) {
                    int x = i, y = j;
                    if (i - 1 >= 0 && j - 1 >= 0 && othelloBoard[i - 1][j - 1] == '_') {
                        i++;
                        j++;
                        while (i < 7 && j < 7 && othelloBoard[i][j] == opponent) {
                            i++;
                            j++;
                        }
                        if (i <= 7 && j <= 7 && othelloBoard[i][j] == player)
                            placeablePoints.add(new Points(x - 1, y - 1));
                    }
                    i = x;
                    j = y;
                    if (i - 1 >= 0 && othelloBoard[i - 1][j] == '_') {
                        i++;
                        while (i < 7 && othelloBoard[i][j] == opponent) i++;
                        if (i <= 7 && othelloBoard[i][j] == player) placeablePoints.add(new Points(x - 1, y));
                    }
                    i = x;
                    if (i - 1 >= 0 && j + 1 <= 7 && othelloBoard[i - 1][j + 1] == '_') {
                        i++;
                        j--;
                        while (i < 7 && j > 0 && othelloBoard[i][j] == opponent) {
                            i++;
                            j--;
                        }
                        if (i <= 7 && j >= 0 && othelloBoard[i][j] == player)
                            placeablePoints.add(new Points(x - 1, y + 1));
                    }
                    i = x;
                    j = y;
                    if (j - 1 >= 0 && othelloBoard[i][j - 1] == '_') {
                        j++;
                        while (j < 7 && othelloBoard[i][j] == opponent) j++;
                        if (j <= 7 && othelloBoard[i][j] == player) placeablePoints.add(new Points(x, y - 1));
                    }
                    j = y;
                    if (j + 1 <= 7 && othelloBoard[i][j + 1] == '_') {
                        j = j - 1;
                        while (j > 0 && othelloBoard[i][j] == opponent) j--;
                        if (j >= 0 && othelloBoard[i][j] == player) placeablePoints.add(new Points(x, y + 1));
                    }
                    j = y;
                    if (i + 1 <= 7 && j - 1 >= 0 && othelloBoard[i + 1][j - 1] == '_') {
                        i--;
                        j++;
                        while (i > 0 && j < 7 && othelloBoard[i][j] == opponent) {
                            i--;
                            j++;
                        }
                        if (i >= 0 && j <= 7 && othelloBoard[i][j] == player)
                            placeablePoints.add(new Points(x + 1, x - 1));
                    }
                    i = x;
                    j = y;
                    if (i + 1 <= 7 && othelloBoard[i + 1][j] == '_') {
                        i = i - 1;
                        while (i > 0 && othelloBoard[i][j] == opponent) i--;
                        if (i >= 0 && othelloBoard[i][j] == player) placeablePoints.add(new Points(x + 1, y));
                    }
                    i = x;
                    if (i + 1 <= 7 && j + 1 <= 7 && othelloBoard[i + 1][j + 1] == '_') {
                        i--;
                        j--;
                        while (i > 0 && j > 0 && othelloBoard[i][j] == opponent) {
                            i--;
                            j--;
                        }
                        if (i >= 0 && j >= 0 && othelloBoard[i][j] == player)
                            placeablePoints.add(new Points(x + 1, y + 1));
                    }
                    i = x;
                    j = y;
                }
            }
        }
    }

    /**
     * display the game board
     *
     * @param board game board
     */
    protected void display(OthelloBoard board)
    {
        System.out.println();
        System.out.print("  ");
        for (char i : xRow) System.out.print(i + " "); //alphabet address
        System.out.println();
        for(int i = 0 ; i < 8 ; i++)
        {
            System.out.print((i + 1) + " "); //numeric address
            for(int j = 0 ; j < 8 ; j++)
            {
                char mChar = board.othelloBoard[i][j];
                //if(mChar == 'B') System.out.print("\u26AB ");
                //else if(mChar == 'W') System.out.print("\u26AA ");
                //else System.out.print(mChar + " ");
                System.out.print(mChar + " ");
            }
            System.out.println();
        }
    }

    /**
     * show the game result
     * it returns 1 when white team wins
     * it returns -1 when black team wins
     * it returns 0 when its a draw!
     * and it returns -2 when something goes wrong!
     *
     * @param whitePlaceablePoint
     * @param blackPlaceablePoint
     * @return
     */
    protected int result(ArrayList<Points> whitePlaceablePoint , ArrayList<Points> blackPlaceablePoint)
    {
        newScores();
        if(remainingPoints == 0){
            if(whiteScore > blackScore) return 1;
            else if(blackScore > whiteScore) return -1;
            else return 0; //Draw
        }
        if(whiteScore == 0 || blackScore == 0){
            if(whiteScore > 0) return 1;
            else if(blackScore > 0) return -1;
        }
        if(whitePlaceablePoint.isEmpty() && blackPlaceablePoint.isEmpty()){
            if(whiteScore > blackScore) return 1;
            else if(blackScore > whiteScore) return -1;
            else return 0; //Draw
        }
        return -2;
    }

    /**
     * return ArrayList of placeable point
     *
     * @param player
     * @param opponent
     * @return ArrayList of placeable point
     */
    protected ArrayList<Points> getPlaceablePoints(char player , char opponent)
    {
        ArrayList<Points> placeablePoints = new ArrayList<>();
        placeablePoints(player , opponent , placeablePoints);
        return placeablePoints;
    }

    /**
     * set '*' character in the board game for placeable points
     *
     * @param player
     * @param opponent
     * @param placeablePoints
     */
    protected void setPlaceablePoints(char player , char opponent , ArrayList<Points> placeablePoints)
    {
        for (Points i :
             placeablePoints) {
            othelloBoard[i.x][i.y] = '*';
        }
        display(this);
        for (Points i :
                placeablePoints) {
            othelloBoard[i.x][i.y] = '_';
        }
    }

    /**
     * after inserting a point for your next move
     * this function apply changes in the game
     * and flip color of peaces which have the condition
     *
     * @param p
     * @param player
     * @param opponent
     */
    protected void applyChanges(Points p, char player, char opponent){
        int i = p.x, j = p.y;
        othelloBoard[i][j] = player;
        int x = i, y = j;

        if(i-1>=0 && j-1>=0 && othelloBoard[i-1][j-1] == opponent){
            i--;
            j--;
            while(i>0 && j>0 && othelloBoard[i][j] == opponent){i--;j--;}
            if(i>=0 && j>=0 && othelloBoard[i][j] == player) {while(i!=x-1 && j!=y-1)othelloBoard[++i][++j]=player;}
        }
        i=x;
        j=y;
        if(i-1>=0 && othelloBoard[i-1][j] == opponent){
            i--;
            while(i>0 && othelloBoard[i][j] == opponent) i--;
            if(i>=0 && othelloBoard[i][j] == player) {while(i!=x-1)othelloBoard[++i][j]=player;}
        }
        i=x;
        if(i-1>=0 && j+1<=7 && othelloBoard[i-1][j+1] == opponent){
            i--;
            j++;
            while(i>0 && j<7 && othelloBoard[i][j] == opponent){i--;j++;}
            if(i>=0 && j<=7 && othelloBoard[i][j] == player) {while(i!=x-1 && j!=y+1)othelloBoard[++i][--j] = player;}
        }
        i=x;
        j=y;
        if(j-1>=0 && othelloBoard[i][j-1] == opponent){
            j--;
            while(j>0 && othelloBoard[i][j] == opponent)j--;
            if(j>=0 && othelloBoard[i][j] == player) {while(j!=y-1)othelloBoard[i][++j] = player;}
        }
        j=y;
        if(j+1<=7 && othelloBoard[i][j+1] == opponent){
            j++;
            while(j<7 && othelloBoard[i][j] == opponent)j++;
            if(j<=7 && othelloBoard[i][j] == player) {while(j!=y+1)othelloBoard[i][--j] = player;}
        }
        j=y;
        if(i+1<=7 && j-1>=0 && othelloBoard[i+1][j-1] == opponent){
            i++;
            j--;
            while(i<7 && j>0 && othelloBoard[i][j] == opponent){i++;j--;}
            if(i<=7 && j>=0 && othelloBoard[i][j] == player) {while(i!=x+1 && j!=y-1)othelloBoard[--i][++j] = player;}
        }
        i=x;
        j=y;
        if(i+1 <= 7 && othelloBoard[i+1][j] == opponent){
            i++;
            while(i<7 && othelloBoard[i][j] == opponent) i++;
            if(i<=7 && othelloBoard[i][j] == player) {while(i!=x+1)othelloBoard[--i][j] = player;}
        }
        i=x;

        if(i+1 <= 7 && j+1 <=7 && othelloBoard[i+1][j+1] == opponent){
            i++;
            j++;
            while(i<7 && j<7 && othelloBoard[i][j] == opponent){i++;j++;}
            if(i<=7 && j<=7 && othelloBoard[i][j] == player)while(i!=x+1 && j!=y+1)othelloBoard[--i][--j] = player;}
    }

    /**
     * calculate the latest game result
     */
    protected void newScores()
    {
        whiteScore = 0;
        blackScore = 0;
        remainingPoints = 0;
        for(int i = 0 ; i < 8 ; i++)
        {
            for(int j = 0 ; j < 8 ; j++)
            {
                if(othelloBoard[i][j] == 'W') whiteScore++;
                else if(othelloBoard[i][j] == 'B') blackScore++;
                else remainingPoints++;
            }
        }
    }

    /**
     * transfer alphabetical address of the board to matrix-form vertical address
     *
     * @param alphabeticAddress
     * @return matrix vertical address
     */
    protected int xCoor(char alphabeticAddress)
    {
        if(alphabeticAddress == 'A') return 0;
        else if(alphabeticAddress == 'B') return 1;
        else if(alphabeticAddress == 'C') return 2;
        else if(alphabeticAddress == 'D') return 3;
        else if(alphabeticAddress == 'E') return 4;
        else if(alphabeticAddress == 'F') return 5;
        else if(alphabeticAddress == 'G') return 6;
        else if(alphabeticAddress == 'H') return 7;
        else {
            System.out.println("invalid value!");
            return -1;
        }
    }
}
