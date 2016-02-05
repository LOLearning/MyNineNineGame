package mynineninegame;
import java.util.*;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;


public class My_Nine_Nine_Game {
    public static void main(String[] args)
    {
        ArrayList<Integer> cardList = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> playerList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> sequencePlayerList = new ArrayList<Integer>();
        Scanner scn = new Scanner(System.in);
        setPlayerList(playerList);//要改init
        cardDeck(cardList);
        playerCardList(cardList, playerList);
        setPlayerNumber(sequencePlayerList);

        // System.out.println("歡迎來到我的99遊戲");
        // System.out.print("請輸入你的姓名: ");
        // String name = scn.nextLine();
        // System.out.println("歡迎" + name);
        // System.out.println("輸入'0'即可立即結束遊戲喔~~");
        // System.out.println("--------------開始遊戲--------------");
        int total = 0;
        int initSetCardVariable = 1;///修改名子  位置
        int initPlayerOutTheCard = 0;//修掉init
        ArrayList<Integer> setSpecialCardTurnPlace = playerList.get(0);
        for (;;)
        {
            int selectCard;
            if (setSpecialCardTurnPlace == playerList.get(0))
            {
                selectCard = playerUserChoose(playerList.get(0));
                initPlayerOutTheCard = playOutCard(setSpecialCardTurnPlace, cardList, initSetCardVariable,selectCard);
                initSetCardVariable = specialCardVariable(sequencePlayerList, userSequenceNumber(initPlayerOutTheCard, sequencePlayerList));
                total = UserChooseSpecialCard(initPlayerOutTheCard, total);
                System.out.println("目前分數: " + total);
                System.out.println("**********");
                if (total > 99)
                {
                    System.out.println("玩家" + initSetCardVariable + "輸了!!!");
                    break;
                }
                else
                {
                    if (initPlayerOutTheCard == -1)
                    {
                        System.out.println("遊戲結束!!!");
                        for (int i = 0; i <= 3; i++)
                        {
                            System.out.println("**********");
                            System.out.println("玩家" + (i + 1) + "的手牌");
                            System.out.println(playerList.get(i));
                        }
                        break;
                    }
                }
            }
            else
            {
                selectCard = AIChooseCard(setSpecialCardTurnPlace, total);
                initPlayerOutTheCard = playOutCard(setSpecialCardTurnPlace, cardList, initSetCardVariable, selectCard);
                initSetCardVariable = specialCardVariable(sequencePlayerList, AISequenceNumber(initPlayerOutTheCard));
                total = AIChooseSpecialCard(initPlayerOutTheCard, total);
                System.out.println("目前分數: " + total);
                System.out.println("**********");
                if (total > 99)
                {
                    System.out.println("玩家" + initSetCardVariable + "輸了!!!");
                    break;
                }
            }
            setSpecialCardTurnPlace = SpecialCardTurnPlace(initSetCardVariable, playerList);//修改名子getnextplayer 
            if (cardList.size() == 0)
            {
                cardDeck(cardList);
                shuffleCard(cardList);
            }
        }
    }

    public static void setPlayerList(ArrayList<ArrayList<Integer>> playerList)
    {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = 1; i <= 4; i++)
        {
            playerList.add(new ArrayList<Integer>());
        }
    }

    static int PLAYER_PLAY_CAR_REVERSE = 5;//改名子
    static int PLAYER_PLAY_CAR_ASSIGN = 4;
    static int SPEICAL_CARD_TYPE_REVERSE = 5;
    static int SPEICAL_CARD_TYPE_ASSIGN = 4;
    static int SEQUENTIA_DIGITAL = 4;

    public static int AISequenceNumber(int AIPlayNumber)
    {
        int outputOrder;
        if (AIPlayNumber == PLAYER_PLAY_CAR_REVERSE)
        {
            outputOrder = SPEICAL_CARD_TYPE_REVERSE;
        }
        else if (AIPlayNumber == PLAYER_PLAY_CAR_ASSIGN)
        {
            Random ran = new Random();
            outputOrder = ran.nextInt(3) + 1;
        }
        else
        {
            outputOrder = SEQUENTIA_DIGITAL;
        }
        return outputOrder;
    }

    public static int userSequenceNumber(int UserPlayNumber, ArrayList<Integer> playerList)
    {
        int outputOrder;
        if (UserPlayNumber == PLAYER_PLAY_CAR_REVERSE)
        {
            outputOrder = SPEICAL_CARD_TYPE_REVERSE;
        }
        else if (UserPlayNumber == PLAYER_PLAY_CAR_ASSIGN)
        {
            Scanner scn = new Scanner(System.in);
            System.out.print("請問要指定哪位玩家  ");
            for (int i = 1; i <= 3; i++)
            {
                System.out.print(" " + i + ".[" + playerList.get(i) + "號玩家] ");
            }
            System.out.print(":  ");
            int choose = scn.nextInt();
            outputOrder = choose;
        }
        else
        {
            outputOrder = SEQUENTIA_DIGITAL;
        }
        return outputOrder;
    }

    public static int specialCardVariable(ArrayList<Integer> sequencePlayerList, int AISequenceNumber)
    {
        int setPlayer;
        if (AISequenceNumber == SEQUENTIA_DIGITAL) //// 正常排序
        {
            int setPlayer2 = sequencePlayerList.get(0);
            sequencePlayerList.remove(0);
            sequencePlayerList.add(setPlayer2);
            setPlayer = sequencePlayerList.get(0);
        }
        else if (AISequenceNumber == SPEICAL_CARD_TYPE_REVERSE) ///// 5號牌-迴轉
        {
            Collections.swap(sequencePlayerList, 1, 3);
            int setPlayer2 = sequencePlayerList.get(0);
            sequencePlayerList.remove(0);
            sequencePlayerList.add(setPlayer2);
            setPlayer = sequencePlayerList.get(0);
        }
        else /// 4號牌-指定
        {
            for (int i = 1; i <= AISequenceNumber; i++)
            {
                int Player = sequencePlayerList.get(0);
                sequencePlayerList.remove(0);
                sequencePlayerList.add(Player);
            }
            setPlayer = sequencePlayerList.get(0);
        }
        return setPlayer;
    }

    public static ArrayList<Integer> SpecialCardTurnPlace(int initSetCardVariable, ArrayList<ArrayList<Integer>> playerList)
    {
        ArrayList<Integer> returnPlayer;
        returnPlayer = playerList.get(initSetCardVariable - 1);
        return returnPlayer;
    }

    public static void setPlayerNumber(ArrayList<Integer> playerCard)
    {
        for (int i = 1; i <= 4; i++)
        {
            playerCard.add(i);
        }
    }

    public static int AIChooseSpecialCard(int specialCard, int total)
    {
        switch (specialCard)
        {
        case 13:
            total = 99;
            break;
        case 12:
            if (total >= 80)
            {
                total -= 20;
            }
            else if (total < 19)
            {
                total += 20;
            }
            else
            {
                total += 20;
            }
            break;
        case 11:
            total += 0;
            break;
        case 10:
            if (total >= 90)
            {
                total -= 10;
            }
            else if (total < 9)
            {
                total += 10;
            }
            else
            {
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
            total = total + specialCard;
        }
        return total;
    }

    public static int UserChooseSpecialCard(int specialCard, int total)
    {
        Scanner scn = new Scanner(System.in);
        switch (specialCard)
        {
        case 13:
            total = 99;
            break;
        case 12:
            System.out.print("請問要加20還減20(輸入 1=加，2=減): ");
            int choose = scn.nextInt();
            if (choose == 1)
            {
                total += 20;
            }
            else
            {
                total -= 20;
            }
            break;
        case 11:
            total += 0;
            break;
        case 10:
            System.out.print("請問要加10還減10(輸入 1=加，2=減): ");
            int choose1 = scn.nextInt();
            if (choose1 == 1)
            {
                total += 10;
            }
            else
            {
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
            total = total + specialCard;
        }
        return total;
    }

    private static void playerCardList(ArrayList<Integer> cardList, ArrayList<ArrayList<Integer>> player)
    {
        for (int j = 0; j <= 3; j++)
        {
            for (int i = 1; i <= 5; i++)
            {
                player.get(j).add(shuffleCard(cardList));
            }
        }
    }

    public static int AIChooseCard(ArrayList<Integer> player, int total)
    {

        int choose = 3;
        for (int i = 4; i >= 0; i--)
        {
            Collections.sort(player);
            switch (player.get(i))
            {
            case 12:
                if (total >= 80 || total >= 70)
                {
                    choose = i;
                }
                else
                {
                    break;
                }

            case 10:
                if (total >= 90 || total >= 60)
                {
                    choose = i;
                }
                else
                {
                    break;
                }

            case 11:
                if (total >= 90)
                {
                    choose = i;
                }
                else
                {
                    break;
                }
            case 13:
                if (total >= 90)
                {
                    choose = i;
                }
                else
                {
                    break;
                }
            case 5:
                if (total >= 90)
                {
                    choose = i;
                }
                else
                {
                    break;
                }
            case 4:
                if (total >= 90)
                {
                    choose = i;
                }
                else
                {
                    break;
                }
            }
            if (total + player.get(i) > 80)
            {
                choose = 4;
            }
            else if (total < 50 && player.get(i) != 11 && player.get(i) != 4 && player.get(i) != 5)
            {
                choose = 3;
            }
            else if (total > 50 && total <= 80)
            {
                choose = 2;
            }

        }
        return choose + 1;
    }

    public static int playerUserChoose(ArrayList<Integer> player)
    {
        Scanner scn = new Scanner(System.in);
        System.out.println("我的手牌");
        Collections.sort(player);
        for (int i = 1; i <= 5; i++)
        {
            System.out.print(" " + i + ".[" + player.get(i - 1) + "]");
        }
        System.out.println("");
        System.out.print("輸入你的選擇(1~5): ");
        int choose = scn.nextInt();
        return choose;
    }

    public static int playOutCard(ArrayList<Integer> setSpecialCardTurnPlace, ArrayList<Integer> cardList, int initSetCardVariable, int playerUserChoose)
    {
        Collections.sort(setSpecialCardTurnPlace);
        int playergetcard;
        if (playerUserChoose == 0)
        {
            playergetcard = -1;
        }
        else
        {
            playergetcard = setSpecialCardTurnPlace.get(playerUserChoose - 1);
            int number = shuffleCard(cardList);
            setSpecialCardTurnPlace.remove(playerUserChoose - 1);
            setSpecialCardTurnPlace.add(number);
            if (playergetcard == 10 || playergetcard == 11 || playergetcard == 12 || playergetcard == 13
                    || playergetcard == 4 || playergetcard == 5)
            {
                System.out.println("玩家" + initSetCardVariable + "出: " + "特殊牌" + playergetcard);
            }
            else
            {
                System.out.println("玩家" + initSetCardVariable + "出: " + playergetcard);
            }
        }
        return playergetcard;
    }

    public static void cardDeck(ArrayList<Integer> cardList)
    {
        for (int i = 1; i <= 4; i++)
        {
            for (int j = 1; j <= 13; j++)
            {
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