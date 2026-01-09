import Games.AGame;
import Games.CoinToss.CoinToss;
import Games.RockPaperScissors.RockPaperScissors;
import Games.TicTacToe.TicTacToe;
import Games.PetTrainer.PetTrainer;

import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        boolean end = false;
        while (!end) {
            System.out.println("\f");
            CoinToss coinToss = new CoinToss();

            RockPaperScissors rockPaperScissors = new RockPaperScissors();

            TicTacToe ticTacToe;
            ticTacToe = new TicTacToe('X', 'O');

            PetTrainer petTrainer = new PetTrainer();

            AGame[] arcade = {coinToss, rockPaperScissors, ticTacToe, petTrainer};
            Arrays.sort(arcade);

            Scanner scan = new Scanner(System.in);
            int ans = 0;
            String intro = "Welcome to Will's Wacky Wonderful Arcade of Wonders!!!\n" + "Please enter the number of the game you would like to play.\n";
            String options = "";
            for (int i = 0; i < arcade.length; i++) {
                options += i + ": " + arcade[i].name + "\n";
                if ((i + 1) == arcade.length) {
                    options += (i + 1) + ": Exit Arcade";
                }
            }

            System.out.println(intro + options);
            for (int i = 0; i == 0; ) {
                try {
                    ans = Integer.parseInt(scan.nextLine());
                    if (ans < 0 || ans > arcade.length) {
                        System.out.println("You did not enter a valid number.");
                        System.out.println("Please enter:\n" + options);
                    } else {
                        i = 1;
                    }
                } catch (Exception NumberFormatException) {
                    System.out.println("You did not enter a number");
                    System.out.println("Please enter:\n" + options);
                }
            }
            if (ans != arcade.length) {
                for (int i = 0; i <= arcade.length; i++) {
                    if (i == ans) {
                        arcade[i].play();
                    }
                }
            }
            else{
                end = true;
            }
        }
    }
}