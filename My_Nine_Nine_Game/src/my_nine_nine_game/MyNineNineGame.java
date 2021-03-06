package my_nine_nine_game;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class MyNineNineGame
{
    private final int CARD_REVERSE = 5;
    private final int CARD_ASSIGN = 4;
    private final int CARD_TYPE_REVERSE = 5;
    private final int CARD_TYPE_NORMAL = 4;
    private final int GAME_OVER = -1;
    public int selectCard;
    private Scanner scn;
    public int total;
    private ArrayList<Integer> cardDeck;
    public ArrayList<ArrayList<Integer>> playerList;
    private ArrayList<Integer> playerOrder;
    private String name = "Default";
    public ArrayList<Integer> getNextPlayer;
    public int position = 1;
    public int playCard;

    public boolean body()
    {
        addHandCard(getNextPlayer, selectCard);
        if (isGameOver()) {
            return true;
        }
        printTotal();
        position = getNextPlayerPosition(userSequenceNumber(playCard));
        getNextPlayer = playerList.get(position - 1);
        for (;;) {
            if (getNextPlayer != playerList.get(0)) {
                playCard = AI(position);
                printTotal();
                if (isGameOver()) {
                    return true;
                }
                position = getNextPlayerPosition(AISequenceNumber(playCard));
                getNextPlayer = playerList.get(position - 1);
            } else {
                return false;
            }
        }
    }

    private void printTotal()
    {
        System.out.println("目前分數: " + total);
        System.out.println("*****************");
    }

    private int AI(int position)
    {
        selectCard = AIChooseCard(getNextPlayer);
        playCard = playOutCard(getNextPlayer, selectCard, position);
        addHandCard(getNextPlayer, selectCard);
        AIChooseCard(playCard);
        return playCard;
    }

    public void init()
    {
        cardDeck = new ArrayList<>();
        playerList = new ArrayList<>();
        playerOrder = new ArrayList<>();
        scn = new Scanner(System.in);
        initPlayerList();
        initCardDeck();
        initPlayerCardList();
        initPlayerOrder();
        total = 0;
        getNextPlayer = playerList.get(0);
    }

    public void setName(String playerName)
    {
        name = playerName;
    }

    public boolean isGameOver()
    {
        if (total > 99) {
            return true;
        }
        return false;
    }

    private void initPlayerList()
    {
        for (int i = 1; i <= 4; i++) {
            playerList.add(new ArrayList<>());
        }
    }

    private int AISequenceNumber(int AIPlayNumber)
    {
        int outputOrder;
        switch (AIPlayNumber) {
            case CARD_REVERSE:
                outputOrder = CARD_TYPE_REVERSE;
                break;
            case CARD_ASSIGN:
                Random ran = new Random();
                outputOrder = ran.nextInt(3) + 1;
                break;
            default:
                outputOrder = CARD_TYPE_NORMAL;
                break;
        }
        return outputOrder;
    }

    private int userSequenceNumber(int UserPlayNumber)
    {
        int outputOrder;
        switch (UserPlayNumber) {
            case CARD_REVERSE:
                outputOrder = CARD_TYPE_REVERSE;
                break;
            case CARD_ASSIGN:
                outputOrder = cardAssign();
                break;
            default:
                outputOrder = CARD_TYPE_NORMAL;
                break;
        }
        return outputOrder;
    }

    private int cardAssign()
    {
        System.out.print("請問要指定哪位玩家  ");
        for (int i = 1; i <= 3; i++) {
            System.out.print(" " + i + ".[" + playerOrder.get(i) + "號玩家] ");
        }
        System.out.print(":  ");
        return scn.nextInt();
    }

    private int getNextPlayerPosition(int sequenceNumber)
    {
        int setPlayer;
        if (sequenceNumber == CARD_TYPE_NORMAL) //// 正常排序
        {
            int setPlayer2 = playerOrder.get(0);
            playerOrder.remove(0);
            playerOrder.add(setPlayer2);
            setPlayer = playerOrder.get(0);
        } else if (sequenceNumber == CARD_TYPE_REVERSE) ///// 5號牌-迴轉
        {
            Collections.swap(playerOrder, 1, 3);
            int setPlayer2 = playerOrder.get(0);
            playerOrder.remove(0);
            playerOrder.add(setPlayer2);
            setPlayer = playerOrder.get(0);
        } else /// 4號牌-指定
        {
            for (int i = 1; i <= sequenceNumber; i++) {
                int Player = playerOrder.get(0);
                playerOrder.remove(0);
                playerOrder.add(Player);
            }
            setPlayer = playerOrder.get(0);
        }
        return setPlayer;
    }

    private void initPlayerOrder()
    {
        for (int i = 1; i <= 4; i++) {
            playerOrder.add(i);
        }
    }

    private void AIChooseCard(int cardNumber)
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
    }

    public int UserChooseCard(int cardNumber, int sign)
    {
        switch (cardNumber) {
            case 13:
                total = 99;
                break;
            case 12:
                total += (20 * sign);
                break;
            case 11:
                total += 0;
                break;
            case 10:
                total += (10 * sign);
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

    private void initPlayerCardList()
    {
        for (int j = 0; j <= 3; j++) {
            for (int i = 1; i <= 5; i++) {
                playerList.get(j).add(shuffleCard());
            }
        }
    }

    private int AIChooseCard(ArrayList<Integer> player)
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

    private int playOutCard(ArrayList<Integer> curruntPlayer, int UserChoose, int position)
    {
        Collections.sort(curruntPlayer);
        int getcard;
        if (UserChoose == 0) {
            getcard = GAME_OVER;
        } else {
            getcard = curruntPlayer.get(UserChoose - 1);
            if (isSpecialCard(getcard)) {
                System.out.println("玩家" + position + "出: " + "特殊牌" + getcard);
            } else {
                System.out.println("玩家" + position + "出: " + getcard);
            }
        }
        return getcard;
    }

    private void addHandCard(ArrayList<Integer> curruntPlayer, int Choose)
    {
        curruntPlayer.remove(Choose - 1);
        curruntPlayer.add(shuffleCard());
        if (cardDeck.isEmpty()) {
            initCardDeck();
            shuffleCard();
        }
    }

    public boolean isSpecialCard(int getcard)
    {
        return getcard == 10 || getcard == 11 || getcard == 12 || getcard == 13
                || getcard == 4 || getcard == 5;
    }

    private void initCardDeck()
    {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 13; j++) {
                cardDeck.add(j);
            }
        }
    }

    private int shuffleCard()
    {
        Random ran = new Random();
        int temp = ran.nextInt(cardDeck.size());
        int number = cardDeck.get(temp);
        cardDeck.remove(temp);
        return number;
    }
}
