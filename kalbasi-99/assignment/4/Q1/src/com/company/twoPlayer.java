package com.company;

import java.util.ArrayList;
import java.util.Scanner;


public class twoPlayer {
    public static void twoPlayers(OthelloBoard board){
        Scanner scan = new Scanner(System.in);
        OthelloBoard.Points move = new OthelloBoard.Points(-1, -1);
        System.out.println("Black Moves first");

        int result;
        boolean skip = false;
        String input;

        while(true){
            ArrayList<OthelloBoard.Points> blackPlaceableLocations = board.getPlaceablePoints('B', 'W');
            ArrayList<OthelloBoard.Points> whitePlaceableLocations = board.getPlaceablePoints('W', 'B');

            board.setPlaceablePoints('B', 'W' , blackPlaceableLocations);
            result = board.result(whitePlaceableLocations, blackPlaceableLocations);

            if(result == 0){System.out.println("draw.");break;}
            else if(result==1){System.out.println("White wins: "+board.whiteScore+":"+board.blackScore);break;}
            else if(result==-1){System.out.println("Black wins: "+board.whiteScore+":"+board.blackScore);break;}

            if(blackPlaceableLocations.isEmpty()){
                System.out.println("Black have no more moves! skip bro");
                skip = true;
            }

            if(!skip){
                System.out.println("black team: ");
                input = scan.next();
                move.y = board.xCoor(input.charAt(0));
                move.x = (Integer.parseInt(input.charAt(1)+"")-1);

                while(!blackPlaceableLocations.contains(move)){
                    System.out.println("Invalid move!\n\nblack team: ");
                    input = scan.next();
                    move.y = board.xCoor(input.charAt(0));
                    move.x = Integer.parseInt((input.charAt(1)+""))-1;
                }
                board.applyChanges(move, 'B', 'W');
                board.newScores();
                System.out.println("\nBlack: "+board.blackScore+" White: "+board.whiteScore);
            }
            skip = false;

            whitePlaceableLocations = board.getPlaceablePoints('W', 'B');
            blackPlaceableLocations = board.getPlaceablePoints('B', 'W');

            board.setPlaceablePoints('W', 'B' , whitePlaceableLocations);
            result = board.result(whitePlaceableLocations, blackPlaceableLocations);

            if(result==0){System.out.println("draw.");break;}
            else if(result==1){System.out.println("White wins: "+board.whiteScore+":"+board.blackScore);break;}
            else if(result==-1){System.out.println("Black wins: "+board.blackScore+":"+ board.whiteScore);break;}

            if(whitePlaceableLocations.isEmpty()){
                System.out.println("White have no more moves! skip bro");
                skip = true;
            }

            if(!skip){
                System.out.println("white team: ");
                input = scan.next();
                move.y = board.xCoor(input.charAt(0));
                move.x = (Integer.parseInt(input.charAt(1)+"")-1);

                while(!whitePlaceableLocations.contains(move)){
                    System.out.println("Invalid move!\n\nwhite team: ");
                    input = scan.next();
                    move.y = board.xCoor(input.charAt(0));
                    move.x = (Integer.parseInt(input.charAt(1)+"")-1);
                }
                board.applyChanges(move, 'W', 'B');
                board.newScores();
                System.out.println("\nWhite: "+board.whiteScore+" Black: "+ board.blackScore);
            }
        }
    }
}
