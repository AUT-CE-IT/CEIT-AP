package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author BARDIA ARDAKANIAN 9831072
 * @version 0.0
 */

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK = "\u001B[30m";

    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[48;5;34m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[48;5;226m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[48;5;208m";

    /**
     * each player have a deck in which contains all of player cars
     * boolean isComputer show if the player is NPC(none playable character) or an avatar(you)
     */
    public static class Players {
        private ArrayList<Card> deck;
        private boolean isComputer;

        public Players(Boolean isComputer) {
            this.isComputer = isComputer;
            this.deck = new ArrayList<>();
        }

        public ArrayList<Card> getDeck() {
            return deck;
        }

        public boolean getIsComputer() {
            return isComputer;
        }
    }

    /**
     * card object is the mother of all cards in the game
     * boolean availability set if you can play this card with the current action or face card
     */
    public static class Card {

        private boolean availability;


        /**
         * convert card fields to readable string
         */
        public void cardToString() {

        }

        public void setAvailability(boolean availability) {
            this.availability = availability;
        }

        public boolean getAvailability() {
            return availability;
        }
    }

    /**
     * NormalCard is known as colorful cards
     * there are two type of NormalCards :
     * 1-numeric : cards with value between 1-9
     * 2-action : cards with specific action
     */
    public static class NormalCard extends Card {
        private final String color;

        public NormalCard(String color) {
            this.color = color;
        }

        public void cardColorPicker() {
            switch (getColor()) {
                case "red":
                    System.out.print(ANSI_RED_BACKGROUND + ANSI_BLACK);
                    break;
                case "green":
                    System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK);
                    break;
                case "blue":
                    System.out.print(ANSI_BLUE_BACKGROUND + ANSI_BLACK);
                    break;
                case "yellow":
                    System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_WHITE);
                    break;
            }
        }

        public String getColor() {
            return color;
        }
    }

    /**
     * numeric cards
     * value between 0-9
     */
    public static class NumericCard extends NormalCard {
        private int value;

        public NumericCard(String color, int value) {
            super(color);
            this.value = value;
        }

        public void cardToString() {
            cardColorPicker();
                System.out.print("|    0" + value + "    |" + ANSI_RESET + " ");
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * action cards :
     * skip - revers - draw 2
     */
    public static class ActionCard extends NormalCard {
        private String action;

        public ActionCard(String color, String action) {
            super(color);
            this.action = action;
        }

        public void cardToString() {
            cardColorPicker();
            if (action.equals("skip")) System.out.print("|   " + action + "   |" + ANSI_RESET + " ");
            else if (action.equals("draw +2")) System.out.print("|  draw+2  |" + ANSI_RESET + " ");
            else System.out.print("|  revers  |" + ANSI_RESET + " ");
        }

        public String getAction() {
            return action;
        }
    }

    /**
     * wild cards are either color or draw 4
     */
    public static class WildCard extends Card {
        String newColor = null;

        public void setNewColor(String newColor) {
            this.newColor = newColor;
        }

        public void cardToString() {
            System.out.print("|  colors  |" + ANSI_RESET + " ");
        }

        public String getNewColor() {
            return newColor;
        }
    }

    public static class WildDraw4 extends WildCard {

        public void cardToString() {
            System.out.print("|  draw+4  |" + ANSI_RESET + " ");
        }

        @Override
        public void setNewColor(String newColor) {
            super.setNewColor(newColor);
        }
    }

    /**
     * if the player is a NPC
     * a random generator will randomly choose a card from available cards
     * @param computersDeck
     * @return index of computersChoice
     */
    public static int computersChoice(ArrayList<Card> computersDeck) {
        ArrayList<Integer> possibleIndex = new ArrayList<>();
        for (Card i:
                computersDeck) {
            if (i.getAvailability()) possibleIndex.add(computersDeck.indexOf(i));
        }
        int rand = (int) Math.random() * (possibleIndex.size() - 1 - 0 + 1) + 0;
        //System.out.println(possibleIndex + " rand is : " + rand);
        return possibleIndex.get(rand);
    }

    /**
     * check if the player have any available cards
     * @param deck player deck
     * @return true if any moves left - if not returns false
     */
    public static boolean anyMovesLeft(ArrayList<Card> deck) {
        int counter = 0;
        for (Card i:
                deck) {
            if (i.getAvailability()) counter++;
        }
        if (counter > 0) return true;
        return false;
    }

    /**
     * distinguish face card class and show available cards from player's deck
     * @param deck player deck
     * @param face face Card
     */
    public static void faceClass(ArrayList<Card> deck , Card face) {
        if (face instanceof NumericCard) {
            availableNumeric(deck , (NumericCard) face);
        } else if (face instanceof ActionCard) {
            availableAction(deck , (ActionCard) face);
        } else if (face instanceof WildCard && !(face instanceof WildDraw4)) {
            availableColor(deck , (WildCard) face);
        } else if (face instanceof WildDraw4) {
            availableWild(deck , (WildDraw4) face);
        }
    }

    public static void availableNumeric(ArrayList<Card> deck , NumericCard face) {
        for (Card i :
                deck) {
            if (i instanceof NormalCard) {
                if (((NormalCard) i).getColor().equals(face.getColor())) {
                    i.setAvailability(true);
                } else if (i instanceof NumericCard) {
                    if (((NumericCard) i).getValue() == face.getValue()) {
                        i.setAvailability(true);
                    }
                }
            } else if (i instanceof WildCard) {
                i.setAvailability(true);
            }
        }
    }

    public static void availableAction(ArrayList<Card> deck , ActionCard face) {
        for (Card i :
                deck) {
            if (i instanceof NormalCard) {
                if (((NormalCard) i).getColor().equals(face.getColor()) && !face.getAction().equals("draw +2")) {
                    i.setAvailability(true);
                } else if (i instanceof ActionCard) {
                    if (((ActionCard) i).getAction().equals(face.getAction())) {
                        i.setAvailability(true);
                    }
                }
            } else if (i instanceof WildCard && !(i instanceof WildDraw4) && !face.getAction().equals("draw +2")) {
                i.setAvailability(true);
            } else if (i instanceof WildDraw4) {
                i.setAvailability(true);
            }
        }
    }

    public static void availableColor(ArrayList<Card> deck , WildCard face) {
        for (Card i :
                deck) {
            if (i instanceof NormalCard) {
                if (((NormalCard) i).getColor().equals(face.getNewColor())) {
                    i.setAvailability(true);
                }
            } else if (i instanceof WildCard) {
                i.setAvailability(true);
            }
        }
    }

    public static void availableWild(ArrayList<Card> deck , WildDraw4 face) {
        for (Card i :
                deck) {
            if (i instanceof NormalCard) {
                if (((NormalCard) i).getColor().equals(face.getNewColor())) {
                    i.setAvailability(true);
                }
            } else if (i instanceof WildCard) {
                i.setAvailability(true);
            }
        }
    }

    /**
     * prints out all available Cards
     * if face Cards is draw 2 or draw 4 this function will only show draw 2 draw 4 cards
     * in which you can use
     * if you dont have any prints null
     * @param deck player deck
     * @param drawIsZero face card have draw action or not
     * @return number of available choices
     */
    public static int showAvailableCards(ArrayList<Card> deck , boolean drawIsZero){
        int cnt = 0;
        ArrayList<Card> c = new ArrayList<>();
        System.out.print("available choices : ");
        for (Card i:
                deck) {
            if (drawIsZero) {
                if (i.getAvailability()) {
                    c.add(i);
                    cnt++;
                }
            } else {
                if (i.getAvailability()) {
                    if (i instanceof ActionCard) {
                        if (((ActionCard) i).getAction().equals("draw +2")) {
                            c.add(i);
                            cnt++;
                        }
                    } else if (i instanceof WildDraw4) {
                        c.add(i);
                        cnt++;
                    }
                }
            }
        }
        System.out.println();
        showIndexAtoB(c);
        return cnt;
    }

    /**
     * if the face card is draw 2/draw 4
     * this function check if you can put any draw2 / draw 4 on top
     * of that or you have to draw cards
     * @param deck
     * @return
     */
    public static boolean AvailableDraw(ArrayList<Card> deck){
        int cnt = 0;
        for (Card i:
                deck) {
            if (i instanceof WildDraw4 && i.getAvailability())
                cnt++;
        }
        if (cnt > 0) return true;
        return false;
    }

    /**
     * if given card have the conditions
     * put the given card on top of face card
     * @param deck player deck
     * @param allCards all in game cards
     * @param newFace
     * @param currentFace
     * @return
     */
    public static Card putFace(ArrayList<Card> deck , ArrayList<Card> allCards  , Card newFace , Card currentFace) {
        if (!newFace.getAvailability()) {
            System.out.println("invalid move! you cant put this card... skip");
            return currentFace;
        }

        deck.remove(newFace);
        allCards.add(currentFace);
        return newFace;
    }

    /**
     * normal - 0
     * skip - 1
     * reverse - 2
     * draw +2 - 3
     * color - -1
     * draw +4 - -2
     *
     * @param face
     * @return
     */
    public static int faceCardAction(Card face) {
        if (face instanceof NumericCard) {
            return 0;
        } else if (face instanceof ActionCard) {
            switch (((ActionCard) face).getAction()) {
                case "skip":
                    return 1;
                case "reverse":
                    return 2;
                case "draw +2":
                    return 3;
            }
        } else if (face instanceof WildCard && !(face instanceof WildDraw4)) {
            return -1;
        } else if (face instanceof WildDraw4) {
            return -2;
        }
        return 0;
    }

    /**
     * create all cards and add them to the list
     * @param allCards
     */
    public static void createAllCards(ArrayList<Card> allCards) {
        insert(allCards , 0 , 10);
        insert(allCards , 1 , 10);

        for (int j = 0 ; j < 4 ; j++) {
            allCards.add(new WildDraw4());
            allCards.add(new WildCard());
        }

    }

    public static void insert(ArrayList<Card> allCards , int start , int end) {
        for (int i = 0 ; i < 4 ; i++) {
            String c = null;
            switch (i) {
                case 0 :
                    c = "red";
                    break;
                case 1 :
                    c = "yellow";
                    break;
                case 2 :
                    c = "green";
                    break;
                case 3 :
                    c = "blue";
                    break;
            }

            for (int j = start ; j < end ; j++) {
                allCards.add(new NumericCard(c , j));
            }

            for (int j = 0 ; j < 3 ; j++) {
                String action = null;
                switch (j) {
                    case 0:
                        action = "skip";
                        break;
                    case 1:
                        action = "reverse";
                        break;
                    case 2:
                        action = "draw +2";
                }
                allCards.add(new ActionCard(c , action));
            }
        }
    }

    /**
     * check color of given card for changing background-color and text-color
     * @param i
     */
    public static void checkColor(Card i) {
        if (i instanceof NormalCard) {
            switch (((NormalCard) i).getColor()){
                case "red":
                    System.out.print(ANSI_RED_BACKGROUND + ANSI_BLACK);
                    break;
                case "green":
                    System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK);
                    break;
                case "blue":
                    System.out.print(ANSI_BLUE_BACKGROUND + ANSI_BLACK);
                    break;
                case "yellow":
                    System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_WHITE);
                    break;
            }
        }
    }

    /**
     * display given player deck
     * @param deck player deck
     * @param times times showIndexAtoB function is used to set card number
     */
    public static void displayDeck(List<Card> deck , int times) {
        int cnt = times * 12;
        for (Card i :
                deck) {
            if (cnt < 10) System.out.print((cnt+1) + "           " + ANSI_RESET + " ");
            else if (cnt < 100) System.out.print((cnt+1) + "          " + ANSI_RESET + " ");
            else System.out.print((cnt+1) + "         " + ANSI_RESET + " ");
            cnt++;
        }
        System.out.println();

        for (Card i :
                deck) {
            checkColor(i);
            System.out.print("|$$$$$$$$$$|" + ANSI_RESET + " ");
        }
        System.out.println();

        for (Card i :
                deck) {
            checkColor(i);
            System.out.print("|$$$$$$$$$$|" + ANSI_RESET + " ");
        }
        System.out.println();

        for (Card i :
                deck) {
            checkColor(i);
            System.out.print("|          |" + ANSI_RESET + " ");
        }
        System.out.println();

        for (Card i :
                deck) {
            i.cardToString();
        }
        System.out.println();

        for (Card i :
                deck) {
            checkColor(i);
            System.out.print("|          |" + ANSI_RESET + " ");
        }
        System.out.println();

        for (Card i :
                deck) {
            checkColor(i);
            System.out.print("|$$$$$$$$$$|" + ANSI_RESET + " ");
        }
        System.out.println();

        for (Card i :
                deck) {
            checkColor(i);
            System.out.print("|$$$$$$$$$$|" + ANSI_RESET + " ");
        }
        System.out.println("\n");
    }

    /**
     * gives a sublist of player deck to displayDeck function in order to
     * avoid a terminal-crashing bug
     * and YES int not a bug its feature
     * @param deck player deck
     */
    public static void showIndexAtoB(ArrayList<Card> deck) {
        int size = deck.size() , cnt = 0;
        ArrayList<Integer> flag = new ArrayList<Integer>();
        flag.add(0);
        while (true)
        {
            if (size <= 12)
            {
                flag.add(size + cnt*12);
                break;
            }

            size -= 12;
            flag.add(12 + cnt*12);
            cnt++;
        }

        for (int j = 0 ; j < flag.size() - 1 ; j++)
        {
            List<Card> copy = deck.subList(flag.get(j) , flag.get(j+1));
            displayDeck(copy , j);
        }
    }

    public static void displayFace(Card face) {
        System.out.println("-------------------UNO-------------------\n\n\n");
        System.out.print("               ");
        checkColor(face);
        System.out.println("|$$$$$$$$$$|" + "" + ANSI_RESET);

        System.out.print("               ");
        checkColor(face);
        System.out.println("|$$$$$$$$$$|" + "" + ANSI_RESET);

        System.out.print("               ");
        checkColor(face);
        System.out.println("|          |" + "" + ANSI_RESET);


        System.out.print("               ");
        face.cardToString();
        System.out.println();
        System.out.print("               ");
        checkColor(face);
        System.out.println("|          |" + "" + ANSI_RESET);

        System.out.print("               ");
        checkColor(face);
        System.out.println("|$$$$$$$$$$|" + "" + ANSI_RESET);

        System.out.print("               ");
        checkColor(face);
        System.out.println("|$$$$$$$$$$|" + "" + ANSI_RESET);

        System.out.println("\n\n\n\n-------------------UNO-------------------");
    }

    /**
     * draw given number of cards from allCard list to player's deck
     * and they are completely randomly selected
     * @param cardCounter
     * @param deck
     * @param allCards
     */
    public static void draw(int cardCounter , ArrayList<Card> deck , ArrayList<Card> allCards) {
        for (int i = 0 ; i < cardCounter ; i++) {
            int rand = (int) ((Math.random() * ((allCards.size() - 1) + 1)) + 0);
            deck.add(allCards.get(rand));
            allCards.remove(rand);
        }
    }


    /**
     * create a face card randomly in the beginning of the game
     * @param allCards
     * @return
     */
    public static Card createFace(ArrayList<Card> allCards) {
        int rand = (int) ((Math.random() * ((allCards.size() - 1) + 1)) + 0);
        Card face = allCards.get(rand);
        allCards.remove(rand);
        return face;
    }

    /**
     * reset card availability in the end of each round
     * @param players
     */
    public static void reset(ArrayList<Players> players) {
        for (Players p :
                players) {
            for (Card i:
                    p.getDeck()) {
                i.setAvailability(false);
            }
        }
    }

    public static void showDeckSizes(ArrayList<Players> players) {
        for (int i = 0 ; i < players.size() ; i++) {
            System.out.println("player" + (i+1) + " have " + players.get(i).getDeck().size() + " cards!");
        }
    }

    /**
     * check game ending condition and show the result
     * @param players allPlayers
     * @param counter played moves
     */
    public static void finish(ArrayList<Players> players , int counter) {
        for (int i = 0 ; i < players.size() ; i++) {
            if (players.get(i).getDeck().size() == 0) {
                System.out.println("-------------------UNO-------------------\n\n\n");
                System.out.println("               player" + (i+1) + " won!");
                System.out.println("            moves played : " + counter);
                calculateScores(players);
                System.out.println("-------------------UNO-------------------\n\n\n");
                System.exit(0);
            }
        }
    }

    public static void calculateScores(ArrayList<Players> players) {
        System.out.println("\n\n\n               Scores : ");
        for (int i = 0 ; i < players.size() ; i++) {
            int score = 0;
            for (int j = 0 ; j < players.get(i).getDeck().size() ; j++) {
                Card c = players.get(i).getDeck().get(j);
                if (c instanceof WildCard) score += 50;
                else if (c instanceof ActionCard) score += 20;
                else if (c instanceof NumericCard) score += ((NumericCard) c).getValue();
            }
            System.out.println("               " + "player" + (i+1) + " : " + score);
        }
    }

    /**
     * get the number of avatars and NPCs and create the game
     * create all in game cards and
     * draw 7 cards for each player randomly
     * @param players
     * @param allCards
     */
    public static void game(ArrayList<Players> players , ArrayList<Card> allCards) {
        System.out.print("Please enter number of players : ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 0 ; i < n ; i++) {
            System.out.print("Player" + (i+1) + " is a Avatar or NPC? ");
            String playerInfo = sc.next();
            switch (playerInfo) {
                case "npc":
                    players.add(new Players(true));
                    break;
                case "avatar":
                    players.add(new Players(false));
            }
        }

        for (int i = 0 ; i < n ; i++) {
            draw(7 , players.get(i).getDeck() , allCards);
        }
    }

    public static void main(String[] args) {
        int i = 10000, counter = 0;
        int gameCounter = 0;
        int turn = 0;
        boolean clockwise = true;
        int drawCounter = 0;
        ArrayList<Card> allCards = new ArrayList<>();
        createAllCards(allCards);
        ArrayList<Players> players = new ArrayList<Players>();
        game(players , allCards);
        turn = Math.abs(i % players.size());
        Card face = createFace(allCards);
        if (face instanceof WildCard) ((WildCard) face).setNewColor("red");
        Scanner sc = new Scanner(System.in);
        displayFace(face);
        showDeckSizes(players);

        while (true) {
            displayFace(face);
            boolean theActionIsDone = false;
            boolean drawIsEmpty = true;
            if (drawCounter > 0) drawIsEmpty = false;
            System.out.println("cards left : " + allCards.size());
            turn = Math.abs(i % players.size());
            System.out.println("player" + (turn + 1) + " turn");
            showIndexAtoB(players.get(turn).getDeck());
            faceClass(players.get(turn).getDeck(), face);
            if (anyMovesLeft(players.get(turn).getDeck()) && showAvailableCards(players.get(turn).getDeck(), drawIsEmpty) != 0) {
                if (!players.get(turn).getIsComputer()) {
                    Card curr = face;
                    System.out.print("Choose one of your cards : ");
                    face = putFace(players.get(turn).getDeck(), allCards, players.get(turn).getDeck().get(sc.nextInt() - 1), face);
                    if (curr.equals(face)) theActionIsDone = true;
                } else {
                    Card curr = face;
                    System.out.println("its computer turn");
                    face = putFace(players.get(turn).getDeck(), allCards,
                            players.get(turn).getDeck().get(computersChoice(players.get(turn).getDeck())), face);
                    if (curr.equals(face)) theActionIsDone = true;
                    if (face instanceof WildCard && !curr.equals(face)) {
                        int rand = (int) ((Math.random() * ((3 - 0) + 1)) + 0);
                        switch (rand) {
                            case 0:
                                ((WildCard) face).setNewColor("red");
                                break;
                            case 1:
                                ((WildCard) face).setNewColor("blue");
                                break;
                            case 2:
                                ((WildCard) face).setNewColor("green");
                                break;
                            case 3:
                                ((WildCard) face).setNewColor("yellow");
                                break;
                        }
                    }
                }
                displayFace(face);
                int action = faceCardAction(face);
                if (!theActionIsDone) {
                    switch (action) {
                        case 0:
                            //System.out.println("normal");
                            if (players.get(turn).getDeck().size() == 1) {
                                System.out.println("UNO!");
                            }
                            if (clockwise) i++;
                            else i--;
                            break;
                        case 1:
                            //System.out.println("skip");
                            if (players.get(turn).getDeck().size() == 1) {
                                System.out.println("UNO!");
                            }
                            if (clockwise) i += 2;
                            else i -= 2;
                            break;
                        case 2:
                            //System.out.println("reverse");
                            if (players.get(turn).getDeck().size() == 1) {
                                System.out.println("UNO!");
                            }
                            if (clockwise) clockwise = false;
                            else clockwise = true;

                            if (clockwise) i++;
                            else i--;
                            break;
                        case 3:
                            //System.out.println("draw +2");
                            if (players.get(turn).getDeck().size() == 1) {
                            System.out.println("UNO!");
                             }
                            if (clockwise) i++;
                            else i--;
                            drawCounter += 2;
                            turn = Math.abs(i % players.size());
                            if (AvailableDraw(players.get(turn).getDeck())) {
                                draw(drawCounter, players.get(turn).getDeck(), allCards);
                                drawCounter = 0;
                            } else {
                                continue;
                            }
                            break;
                        case -1:
                            //System.out.println("color");
                            if (!players.get(turn).getIsComputer()) {
                                System.out.println("player" + (turn + 1) + " choose a color : ");
                                if (face instanceof WildCard) ((WildCard) face).setNewColor(sc.next());
                            }
                            if (players.get(turn).getDeck().size() == 1) {
                                System.out.println("UNO!");
                            }
                            if (clockwise) i++;
                            else i--;
                            break;
                        case -2:
                            //System.out.println("draw +4");
                            if (!players.get(turn).getIsComputer()) {
                                System.out.println("player" + (turn + 1) + " choose a color : ");
                                if (face instanceof WildCard) ((WildCard) face).setNewColor(sc.next());
                            }
                            if (players.get(turn).getDeck().size() == 1) {
                                System.out.println("UNO!");
                            }
                            drawCounter += 4;
                            if (clockwise) i++;
                            else i--;
                            turn = Math.abs(i % players.size());
                            if (AvailableDraw(players.get(turn).getDeck())) {
                                draw(drawCounter, players.get(turn).getDeck(), allCards);
                                drawCounter = 0;
                            } else {
                                continue;
                            }
                    }
                }
            } else {
                System.out.println("player" + (turn + 1) + " is out of Cards");
                if (allCards.size() > 0)draw(1, players.get(turn).getDeck(), allCards);
                else System.out.println("master deck is empty!");
                if (drawCounter > 0 && allCards.size() >drawCounter) {
                    draw(drawCounter, players.get(turn).getDeck(), allCards);
                    drawCounter = 0;
                } else if (drawCounter > 0 && allCards.size() < drawCounter )System.out.println("there is not enough cards in master deck");
                if (clockwise) i++;
                else i--;
                theActionIsDone = false;
            }
            System.out.println("cards left : " + allCards.size());
            showDeckSizes(players);
            reset(players);
            counter++;
            finish(players , counter);
        }
    }
}