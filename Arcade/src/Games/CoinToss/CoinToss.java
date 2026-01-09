package Games.CoinToss;
import Games.AGame;

import java.util.Scanner;

public class CoinToss extends AGame {
    public CoinToss(){
        super.name = "CoinToss";
    }
    Scanner scanner = new Scanner(System.in);

    @Override
    public void play(){
        boolean end = false;
        while(!end){
            if (Math.random()<0.5){
                System.out.println("Heads");
            }
            else{
                System.out.println("Tails");
            }
            System.out.println("Enter q to exit to the main menu or any other key to continue CoinToss!");
            if (scanner.nextLine().equals("q")) {
                System.out.print("\f");
                end = true;
            }
        }

    }

    public int compareTo(AGame o) {
        return 0;
    }
}
