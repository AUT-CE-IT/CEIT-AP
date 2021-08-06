package com.company;

import java.util.Scanner;

public class Game {
    static void twoPlayer(Board board) {
        Scanner sc = new Scanner(System.in);
        int result;
        board.display();

        while (true)
        {
            result = board.result();
            if (result == 0) {
                System.out.println("draw!");
                break;
            } else if (result == 1) {
                System.out.println("Winner Winner Chicken Dinner!\nRED wins, Fatality");
                break;
            } else if (result == -1) {
                System.out.println("Winner Winner Chicken Dinner!\nBLACK wins, Fatality");
                break;
            }

            int isTrue , zone;
            char direction;

            do {
                int x , y;
                System.out.println("Black turn : ");
                System.out.println("add a new piece : ");
                x = sc.nextInt();
                y = sc.nextInt();
                isTrue = board.addPiece(new Board.Point(x , y), 'B');
            } while ( isTrue == -1 );
            board.display();
            System.out.println("rotate : ");
            zone = sc.nextInt();
            direction = sc.next().charAt(0);
            board.rotation(zone , direction);
            board.display();

            result = board.result();
            if (result == 0) {
                System.out.println("draw!");
                break;
            } else if (result == 1) {
                System.out.println("Winner Winner Chicken Dinner!\nRED wins, Fatality");
                break;
            } else if (result == -1) {
                System.out.println("Winner Winner Chicken Dinner!\nBLACK wins, Fatality");
                break;
            }

            do {
                int x , y;
                System.out.println("RED turn : ");
                System.out.println("add a new piece : ");
                x = sc.nextInt();
                y = sc.nextInt();
                isTrue = board.addPiece(new Board.Point(x , y), 'R');
            } while ( isTrue == -1 );
            board.display();
            System.out.println("rotate : ");
            zone = sc.nextInt();
            direction = sc.next().charAt(0);
            board.rotation(zone , direction);
            board.display();
        }
    }
}
