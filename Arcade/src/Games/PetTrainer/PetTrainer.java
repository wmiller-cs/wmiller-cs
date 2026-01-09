package Games.PetTrainer;
import Games.AGame;
import java.util.Scanner;
/**
 * @class PetTrainer .
 *
 * @author Will Miller
 * @version 1.0
 */
public class PetTrainer extends AGame
{
    Scanner scanner = new Scanner(System.in);

    public PetTrainer(){
        super.name = "Pet Trainer";
    }

    public void play() {

        Scanner scanner = new Scanner(System.in);
        boolean end = false;
        while (!end) {
            System.out.println("\f");
            System.out.println("Welcome to Will's pet trainer! Get ready to have a blast!");

            System.out.println("Your first pet is a cat.");
            System.out.println("What color is your cat?");
            String color = scanner.nextLine();
            System.out.println("What is your cat's name?");
            String catName = scanner.nextLine();
            Cat cat = new Cat(catName, color);

            System.out.println("Your second pet is a dog.");
            System.out.println("What breed is your dog?");
            String breed = scanner.nextLine();
            System.out.println("What is your dog's name?");
            String dogName = scanner.nextLine();
            Dog dog = new Dog(dogName, breed);

            System.out.println("Your third pet is a rock.");
            System.out.println("What shape is your rock?");
            String shape = scanner.nextLine();
            System.out.println("What is your rock's name?");
            String rockName = scanner.nextLine();
            Rock rock = new Rock(rockName, shape);
            //String type = "cat";
            //String name = catName;
            pickAndRunAction(cat, dog, rock);

            System.out.println("Enter q to exit to the main menu or any other key to continue Pet Trainer!");
            if (scanner.nextLine().equals("q")) {
                end = true;
            }
        }
    }
    
    public static void pickAndRunAction(Pet cat, Pet dog, Pet rock){
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i < 6; i++){
            System.out.println("~~~~~~~Round " + i + "~~~~~~~");
            
            PetTrainer start = new PetTrainer();
            start.action(cat);
            start.action(dog);
            start.action(rock);
        }
        int dogTotalStats = dog.health + dog.energy + dog.happiness;
        int catTotalStats = cat.health + cat.energy + cat.happiness;
        int rockTotalStats = rock.health + rock.energy + rock.happiness;
        if (dogTotalStats > catTotalStats && dogTotalStats > rockTotalStats){
            System.out.println("After much deliberation from the judges, " + dog.toString() + " defeats " + cat.toString() + " and " + rock.toString() + "!!!");
        }
        else if (catTotalStats > dogTotalStats && catTotalStats > rockTotalStats){
            System.out.println("After much deliberation from the judges, " + cat.toString() + " defeats " + dog.toString() + " and " + rock.toString() + "!!!");
        }
        else if (rockTotalStats > dogTotalStats && rockTotalStats > catTotalStats){
            System.out.println("After much deliberation from the judges, " + rock.toString() + " defeats " + dog.toString() + " and " + cat.toString() + "!!!");
        }
        else{
            if (dogTotalStats > rockTotalStats){
                System.out.println("After much deliberation from the judges, " + dog.toString() + " and " + cat.toString() + " tie and " + rock.toString() + " loses!!!");
            }
            else if (catTotalStats > dogTotalStats){
                System.out.println("After much deliberation from the judges, " + cat.toString() + " and " + rock.toString() + " tie and " + dog.toString() + " loses!!!");
            }
            else if (rockTotalStats > catTotalStats){
                System.out.println("After much deliberation from the judges, " + dog.toString() + " and " + rock.toString() + " tie and " + cat.toString() + " loses!!!");
            }
            else{
                System.out.println("After much deliberation from the judges, " + dog.toString() + ", " + cat.toString() + ", and " + rock.toString() + " all go home victorious!!!");
            }
        }
    }
    
    public void action(Pet pet){
        boolean i = true;
        while (i == true){      
            System.out.println("Would you like to feed, walk, or play with " + pet.toString() + "?");
            String action = scanner.nextLine();
            
            if (action.toLowerCase().contains("feed")){
                System.out.println(pet.feed());
                i = false;
            }           
            else if (action.toLowerCase().contains("walk")){
                System.out.println(pet.walk());
                i = false;
            }
            else if (action.toLowerCase().contains("play")){
                System.out.println(pet.play());
                i = false;
            }
            else{
                System.out.println("Invalid input. Please enter 'feed', 'walk', or 'play'.");
            }
        }
    }

    public int compareTo(AGame o) {
        return 0;
    }
}
