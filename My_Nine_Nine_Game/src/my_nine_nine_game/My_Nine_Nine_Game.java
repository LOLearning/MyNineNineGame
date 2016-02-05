package my_nine_nine_game;

import java.util.*;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class My_Nine_Nine_Game
{

    static int CARD_REVERSE = 5;//改名子
    static int CARD_ASSIGN = 4;
    static int CARD_TYPE_REVERSE = 5;
    static int CARD_TYPE_NORMAL = 4;
    static int GAME_OVER = -1;
    public static void main(String[] args)
    {
        ArrayList<Integer> cardDeck = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> playerList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> playerOrder = new ArrayList<Integer>();
        Scanner scn = new Scanner(System.in);
        initPlayerList(playerList);
        initCardDeck(cardDeck);
        initPlayerCardList(cardDeck, playerList);
        initPlayerOrder(playerOrder);

        System.out.println("歡迎來到我的99遊戲");
        System.out.print("請輸入你的姓名: ");
        String name = scn.nextLine();
        System.out.println("歡迎" + name);
        System.out.println("輸入'0'即可立即結束遊戲喔~~");
        System.out.println("--------------開始遊戲--------------");
        int total = 0;
        int position = 1;
        int playCard = 0;
        ArrayList<Integer> getNextPlayer = playerList.get(0);
        for (;;) {
            int selectCard;
            if (getNextPlayer == playerList.get(0)) {
                selectCard = UserChooseCard(playerList.get(0));
                playCard = playOutCard(getNextPlayer, cardDeck, position, selectCard);
                position = getNextPlayerPosition(playerOrder, userSequenceNumber(playCard, playerOrder));
                total = My_Nine_Nine_Game.UserChooseCard(playCard, total);
                if (isGameOver(total, position)) {
                    break;
                }
                if (playCard == -1) {
                    System.out.println("遊戲結束!!!");
                    for (int i = 0; i <= 3; i++) {
                        System.out.println("**********");
                        System.out.println("玩家" + (i + 1) + "的手牌");
                        System.out.println(playerList.get(i));
                    }
                    break;
                }
            } else {
                selectCard = My_Nine_Nine_Game.AIChooseCard(getNextPlayer, total);
                playCard = playOutCard(getNextPlayer, cardDeck, position, selectCard);
                position = getNextPlayerPosition(playerOrder, AISequenceNumber(playCard));
                total = AIChooseCard(playCard, total);
                if (isGameOver(total, position)) {
                    break;
                }
            }
            getNextPlayer = playerList.get(position - 1);
            if (cardDeck.size() == 0) {
                initCardDeck(cardDeck);
                shuffleCard(cardDeck);
            }
        }
    }

    public static boolean isGameOver(int total, int position)
    {
        System.out.println("目前分數: " + total);
        System.out.println("**********");
        if (total > 99) {
            System.out.println("玩家" + (position - 1) + "輸了!!!");
            return true;
        }
        return false;
    }

    public static void initPlayerList(ArrayList<ArrayList<Integer>> playerList)
    {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = 1; i <= 4; i++) {
            playerList.add(new ArrayList<Integer>());
        }
    }

    public static int AISequenceNumber(int AIPlayNumber)
    {
        int outputOrder;
        if (AIPlayNumber == CARD_REVERSE) {
            outputOrder = CARD_TYPE_REVERSE;
        } else if (AIPlayNumber == CARD_ASSIGN) {
            Random ran = new Random();
            outputOrder = ran.nextInt(3) + 1;
        } else {
            outputOrder = CARD_TYPE_NORMAL;
        }
        return outputOrder;
    }

    public static int userSequenceNumber(int UserPlayNumber, ArrayList<Integer> playerList)
    {
        int outputOrder;
        if (UserPlayNumber == CARD_REVERSE) {
            outputOrder = CARD_TYPE_REVERSE;
        } else if (UserPlayNumber == CARD_ASSIGN) {
            Scanner scn = new Scanner(System.in);
            System.out.print("請問要指定哪位玩家  ");
            for (int i = 1; i <= 3; i++) {
                System.out.print(" " + i + ".[" + playerList.get(i) + "號玩家] ");
            }
            System.out.print(":  ");
            int choose = scn.nextInt();
            outputOrder = choose;
        } else {
            outputOrder = CARD_TYPE_NORMAL;
        }
        return outputOrder;
    }

    public static int getNextPlayerPosition(ArrayList<Integer> sequencePlayerList, int sequenceNumber)
    {
        int setPlayer;
        if (sequenceNumber == CARD_TYPE_NORMAL) //// 正常排序
        {
            int setPlayer2 = sequencePlayerList.get(0);
            sequencePlayerList.remove(0);
            sequencePlayerList.add(setPlayer2);
            setPlayer = sequencePlayerList.get(0);
        } else if (sequenceNumber == CARD_TYPE_REVERSE) ///// 5號牌-迴轉
        {
            Collections.swap(sequencePlayerList, 1, 3);
            int setPlayer2 = sequencePlayerList.get(0);
            sequencePlayerList.remove(0);
            sequencePlayerList.add(setPlayer2);
            setPlayer = sequencePlayerList.get(0);
        } else /// 4號牌-指定
        {
            for (int i = 1; i <= sequenceNumber; i++) {
                int Player = sequencePlayerList.get(0);
                sequencePlayerList.remove(0);
                sequencePlayerList.add(Player);
            }
            setPlayer = sequencePlayerList.get(0);
        }
        return setPlayer;
    }

    public static void initPlayerOrder(ArrayList<Integer> playerCard)
    {
        for (int i = 1; i <= 4; i++) {
            playerCard.add(i);
        }
    }

    public static int AIChooseCard(int cardNumber, int total)
    {
        switch (cardNumber) {
            case 13:
                total = 99;
                break;
            case 12:
                if (total >= 80) {
                    total -= 20;
                } else if (total < 19) {
                    total += 20;
                } else {
                    total += 20;
                }
                break;
            case 11:
                total += 0;
                break;
            case 10:
                if (total >= 90) {
                    total -= 10;
                } else if (total < 9) {
                    total += 10;
                } else {
                    total += 10;
                }
                break;
            case 5:
                total += 0;
                break;
            case 4:
                total += 0;
                break;
            default:
                total = total + cardNumber;
        }
        return total;
    }

    public static int UserChooseCard(int cardNumber, int total)
    {
        Scanner scn = new Scanner(System.in);
        switch (cardNumber) {
            case 13:
                total = 99;
                break;
            case 12:
                System.out.print("請問要加20還減20(輸入 1=加，2=減): ");
                if (scn.nextInt() == 1) {
                    total += 20;
                } else {
                    total -= 20;
                }
                break;
            case 11:
                total += 0;
                break;
            case 10:
                System.out.print("請問要加10還減10(輸入 1=加，2=減): ");
                if (scn.nextInt() == 1) {
                    total += 10;
                } else {
                    total -= 10;
                }
                break;
            case 5:
                total += 0;
                break;
            case 4:
                total += 0;
                break;
            case -1:
                total += 0;
                break;
            default:
                total = total + cardNumber;
        }
        return total;
    }

    private static void initPlayerCardList(ArrayList<Integer> cardList, ArrayList<ArrayList<Integer>> player)
    {
        for (int j = 0; j <= 3; j++) {
            for (int i = 1; i <= 5; i++) {
                player.get(j).add(shuffleCard(cardList));
            }
        }
    }

    public static int AIChooseCard(ArrayList<Integer> player, int total)
    {

        int choose = 3;
        Collections.sort(player);
        for (int i = 4; i >= 0; i--) {
            switch (player.get(i)) {
                case 12:
                    if (total >= 80 || total >= 70) {
                        choose = i;
                    } else {
                        break;
                    }

                case 10:
                    if (total >= 90 || total >= 60) {
                        choose = i;
                    } else {
                        break;
                    }

                case 11:
                    if (total >= 90) {
                        choose = i;
                    } else {
                        break;
                    }
                case 13:
                    if (total >= 90) {
                        choose = i;
                    } else {
                        break;
                    }
                case 5:
                    if (total >= 90) {
                        choose = i;
                    } else {
                        break;
                    }
                case 4:
                    if (total >= 90) {
                        choose = i;
                    } else {
                        break;
                    }
            }
            if (total + player.get(i) > 80) {
                choose = 4;
            } else if (total < 50 && player.get(i) != 11 && player.get(i) != 4 && player.get(i) != 5) {
                choose = 3;
            } else if (total > 50 && total <= 80) {
                choose = 2;
            }

        }
        return choose + 1;
    }

    public static int UserChooseCard(ArrayList<Integer> player)
    {
        Scanner scn = new Scanner(System.in);
        System.out.println("我的手牌");
        Collections.sort(player);
        for (int i = 1; i <= 5; i++) {
            System.out.print(" " + i + ".[" + player.get(i - 1) + "]");
        }
        System.out.println("");
        System.out.print("輸入你的選擇(1~5): ");
        return scn.nextInt();
    }

    public static int playOutCard(ArrayList<Integer> curruntPlayer, ArrayList<Integer> cardList, int curruntPlayerNumber, int UserChoose)
    {
        Collections.sort(curruntPlayer);
        int getcard;
        if (UserChoose == 0) {
            getcard = GAME_OVER;
        } else {
            getcard = curruntPlayer.get(UserChoose - 1);
            int number = shuffleCard(cardList);
            curruntPlayer.remove(UserChoose - 1);
            curruntPlayer.add(number);
            if (isSpecialCard(getcard)) {
                System.out.println("玩家" + curruntPlayerNumber + "出: " + "特殊牌" + getcard);
            } else {
                System.out.println("玩家" + curruntPlayerNumber + "出: " + getcard);
            }
        }
        return getcard;
    }

    public static boolean isSpecialCard(int getcard)
    {
        return getcard == 10 || getcard == 11 || getcard == 12 || getcard == 13
                || getcard == 4 || getcard == 5;
    }

    public static void initCardDeck(ArrayList<Integer> cardList)
    {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 13; j++) {
                cardList.add(j);
            }
        }
    }

    public static int shuffleCard(ArrayList<Integer> cardList)
    {
        Random ran = new Random();
        int temp = ran.nextInt(cardList.size());
        int number = cardList.get(temp);
        cardList.remove(temp);
        return number;
    }
}
