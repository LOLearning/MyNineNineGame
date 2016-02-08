/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_nine_nine_game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author st8740212
 */
public class MyMain
{

    private static MyNineNineGame myGame;
    private static Scanner scn;

    public static void main(String[] args)
    {
        scn = new Scanner(System.in);
        myGame = new MyNineNineGame();
        myGame.setName(gameName());
        myGame.init();

        for (;;) {
            int selectCard = UserChooseHandCard(myGame.playerList.get(0));
            if (userGameOver(selectCard)) {
                break;
            }
            myGame.playCard = playOutCard(myGame.getNextPlayer, selectCard, 1);
            myGame.UserChooseCard(myGame.playCard, specialCard(myGame.playCard));

            if (myGame.body()) {
                showGameOver();
                break;
            }
        }

    }

    private static String gameName()
    {
        Scanner scn = new Scanner(System.in);
        String name;
        System.out.println("歡迎來到我的99遊戲");
        System.out.print("請輸入你的姓名: ");
        name = scn.nextLine();
        System.out.println("歡迎" + name);
        System.out.println("輸入'0'即可立即結束遊戲喔~~");
        System.out.println("--------------開始遊戲--------------");
        return name;
    }

    private static int UserChooseHandCard(ArrayList<Integer> player)
    {

        System.out.println("我的手牌");
        Collections.sort(player);
        for (int i = 1; i <= 5; i++) {
            System.out.print(" " + i + ".[" + player.get(i - 1) + "]");
        }
        System.out.println("");
        System.out.print("輸入你的選擇(1~5): ");
        return scn.nextInt();
    }

    private static int playOutCard(ArrayList<Integer> curruntPlayer, int UserChoose, int position)
    {
        Collections.sort(curruntPlayer);
        int getcard;
        getcard = curruntPlayer.get(UserChoose - 1);
        if (myGame.isSpecialCard(getcard)) {
            System.out.println("玩家" + position + "出: " + "特殊牌" + getcard);
        } else {
            System.out.println("玩家" + position + "出: " + getcard);
        }

        return getcard;
    }

    private static int specialCard(int cardNumber)
    {
        int sign = 1;
        if (cardNumber == 12 || cardNumber == 10) {
            System.out.print("請問要加還是減(輸入 1=加，2=減): ");
            if (scn.nextInt() == 1) {
                sign = 1;
            } else {
                sign = -1;
            }
        }
        return sign;
    }

    public static boolean userGameOver(int playCard)
    {
        if (playCard == 0) {
            System.out.println("遊戲結束!!!");
            for (int i = 0; i <= 3; i++) {
                System.out.println("**********");
                System.out.println("玩家" + (i + 1) + "的手牌");
                System.out.println(myGame.playerList.get(i));
            }
            return true;
        }
        return false;
    }

    private static void showGameOver()
    {
        System.out.println("玩家" + myGame.position + "輸了!!!");
    }
    
}
