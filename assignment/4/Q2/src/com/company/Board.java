package com.company;

public class Board {
    /**
     * point coordinate as matrix-form
     */
    public static class Point {
        private int x;
        private int y;
        public Point(int x , int y)
        {
            this.x = x;
            this.y = y;
        }
    }

    private int[] column = new int[]{0 , 1 , 2 , 3 , 4 , 5};
    private char[][] board;

    public Board() {
        board = new char[][]{
                {'O','O','O','O','O','O'},
                {'O','O','O','O','O','O'},
                {'O','O','O','O','O','O'},
                {'O','O','O','O','O','O'},
                {'O','O','O','O','O','O'},
                {'O','O','O','O','O','O'},
        };
    }

    protected void display()
    {
        System.out.print("\n   ");
        for(int i : column)
        {
            if(i == 3) System.out.print("   ");
            System.out.print(i + "  ");
        }
        System.out.println();
        for(int i = 0 ; i < 6 ; i++)
        {
            if(i != 3) System.out.print(i + "  ");
            else System.out.print("   -------------------\n" + i + "  ");
            for(int j = 0 ; j < 6 ; j++)
            {
                if(j == 2) System.out.print(board[i][j] + "  |  ");
                else System.out.print(board[i][j] + "  ");
            }
            System.out.println();
        }
    }

    /**
     * add a piece to the game board
     * if failed to add return -1
     * else return 1
     *
     * @param point piece point
     * @param player player character
     * @return invalid or valid operation
     */
    protected int addPiece(Point point , char player)
    {
        if ( board[point.x][point.y] != 'O' ) {
            System.out.println("invaded point");
            return -1;
        }
        board[point.x][point.y] = player;
        return 1;
    }

    /**
     * 1 | 2
     * -----
     * 4 | 3
     * takes a copy of rotation zone then change there values
     *
     * @param rotationZone
     */
    protected void rotation(int rotationZone , char direction)
    {
        int x = 0 , y = 0;
        if (rotationZone == 1) { x = 0 ; y = 0; }
        else if (rotationZone == 2) { x = 0 ; y = 3; }
        else if (rotationZone == 3) { x = 3 ; y = 3; }
        else if (rotationZone == 4) { x = 3 ; y = 0; }

        char[][] rotationZoneCopy = new char[3][3];
        for (int i = 0 ; i < 3 ; i++)
        {
            System.arraycopy(board[x + i], y + 0, rotationZoneCopy[i], 0, 3);
        }

        if (direction == 'r') {
            board[x][y] = rotationZoneCopy[2][0];
            board[x][y + 1] = rotationZoneCopy[1][0];
            board[x][y + 2] = rotationZoneCopy[0][0];
            board[x + 1][y] = rotationZoneCopy[2][1];
            board[x + 1][y + 2] = rotationZoneCopy[0][1];
            board[x + 2][y] = rotationZoneCopy[2][2];
            board[x + 2][y + 1] = rotationZoneCopy[1][2];
            board[x + 2][y + 2] = rotationZoneCopy[0][2];
        }

        else if (direction == 'l'){
            board[x][y] = rotationZoneCopy[0][2];
            board[x][y + 1] = rotationZoneCopy[1][2];
            board[x][y + 2] = rotationZoneCopy[2][2];
            board[x + 1][y] = rotationZoneCopy[0][1];
            board[x + 1][y + 2] = rotationZoneCopy[2][1];
            board[x + 2][y] = rotationZoneCopy[0][0];
            board[x + 2][y + 1] = rotationZoneCopy[1][0];
            board[x + 2][y + 2] = rotationZoneCopy[2][0];
        }

        else {
            System.out.println("invalid input!");
            return;
        }

        display();
    }

    /**
     * return 1 red
     * return -1 black
     * return 0 draw
     * return -2 error
     *
     * @return game result
     */
    protected int result()
    {
        int remaining = 0;
        for (int i = 0 ; i < 6 ; i++)
        {
            for (int j = 0 ; j < 6 ; j++)
            {
                if(board[i][j] == 'O') { remaining++; continue; }
                int count = 0 , res = -2;
                int I = i , J = j;
                char check = board[i][j];

                if (check == 'R') {
                    res = 1;
                } else if (check == 'B') {
                    res = -1;
                }

                if (j == 0 || j == 1) {
                    while ( J <= 5 && board[I][J++] == check)
                        count++;
                    //System.out.println(count + " " + I + " " + J + " \\ " + i + " " + j);
                    if (count > 4)
                        return res;
                    count = 0;
                    I = i;
                    J = j;
                }

                if(i == 0 || i == 1) {
                    while ( I <= 5 && board[I++][J] == check)
                        count++;
                    //System.out.println(count + " " + I + " " + J + " \\ " + i + " " + j);
                    if (count > 4)
                        return res;
                    count = 0;
                    I = i;
                    J = j;
                }

                if ( (i == 0 || i == 1) && (j == 0 || j == 1) ) {
                    while ( (I  <= 5 && J  <= 5) && board[I++][J++] == check)
                        count++;

                    if (count > 4)
                        return res;
                    count = 0;
                    I = i;
                    J = j;
                }

                if ( (i == 0 || i == 1) && (j == 4 || j == 5 ) ) {
                while ( (I  <= 5 && J  >= 0) && board[I++][J--] == check)
                    count++;

                    if (count > 4)
                        return res;
                    count = 0;
                    I = i;
                    J = j;
                }
            }
        }
        if (remaining == 0) return 0;
        return -2;
    }
}
