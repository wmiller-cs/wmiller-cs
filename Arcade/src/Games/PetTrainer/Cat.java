package Games.PetTrainer;
/**
 * Write a description of class Cat here.
 *
 * @author Will Miller
 * @version 1.0
 */
public class Cat extends Pet{
    String color;
    int sickOfYou = 0;
    public Cat(String name, String color){
        super.name = name;
        this.color = color;
    }
    
    public Cat(){
        this("Ronald", "yellow");
    }
    
    public String toString(){
        String greeting = "your " + color + " cat " + name + " (health = " + health + ", energy = " + energy + ", happiness = " + happiness + ")"; 
        return greeting;
    }
    
    public String feed(){
        int random = (int)(Math.random() * 10 + 1);
        if (random % 2 == 0){
            happiness -= 2;
            happiness = statChecker(happiness);
            return "Feeding... \n" + "Your " + color + " cat " + name + " is upset at you and knocks the food out of your hands and runs away to sulk... -> -2 happiness \n";
        }
        else{
            energy += 2;
            happiness += 2;
            energy = statChecker(energy);
            happiness = statChecker(happiness);
            return "Feeding... \n" + "Your " + color + " cat " + name + " accepts your humble sacrifice... -> +2 energy, +2 happiness \n";
        }
    }
        
    public String walk(){
        int random = (int)(Math.random() * 10 + 1);
        if (random % 2 == 0){
            health -= 2;
            energy += 3;
            happiness += 2;
            health = statChecker(health);
            energy = statChecker(energy);
            happiness = statChecker(happiness);
            return "Walking... \n" + "Your " + color + " cat " + name + " is not in the mood to go for a walk with you but will have a super tasty snack instead... -> -2 health, +3 energy, +2 happiness \n";
        }
        else{
            energy += 2;
            happiness += 2;
            energy = statChecker(energy);
            happiness = statChecker(happiness);
            return "Walking... \n" + "Your " + color + " cat " + name + " goes for a walk to view this mortal realm... -> +2 energy, +2 happiness \n";
        }
    }
    
    public String play(){
        int random = (int)(Math.random() * 10 + 1);
        if (random % 2 == 0 && sickOfYou == 0){
            health += 3;
            energy += 2;
            happiness += 4;
            health = statChecker(health);
            energy = statChecker(energy);
            happiness = statChecker(happiness);
            sickOfYou = 1;
            return "Playing... \n" + "Your " + color + " cat " + name + " agrees to play with you just this once... -> +3 health, +2 energy, +4 happiness \n";
        }
        else{
            energy -= 1;
            happiness -= 2;
            energy = statChecker(energy);
            happiness = statChecker(happiness);
            return "Playing... \n" + "Your " + color + " cat " + name + " is annoyed and scratches you, staring you down as you slowly back away... -> -1 energy, -2 happiness \n";
        }
    }
}
