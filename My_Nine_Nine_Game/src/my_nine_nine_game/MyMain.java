/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_nine_nine_game;

import java.util.Scanner;

/**
 *
 * @author st8740212
 */
public class MyMain
{

    public static void main(String[] args)
    {
        MyNineNineGame myGame = new MyNineNineGame();
        myGame.setName(gameName());
        myGame.NineNineGame();
        System.out.println(myGame.loser);
        System.out.println(myGame.playerList);
        myGame.setName(gameName());
        myGame.NineNineGame();

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
}
