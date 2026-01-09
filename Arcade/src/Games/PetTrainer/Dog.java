package Games.PetTrainer;
/**
 * Write a description of class Dog here.
 *
 * @author Will Miller
 * @version 1.0
 */
public class Dog extends Pet{
    String breed;
    public Dog(String name, String breed){
        super.name = name;
        this.breed = breed;
    }
    
    public Dog(){
        this("Pete", "Chihuahua");
    }
    
    public String toString(){
        String greeting = "your " + breed + " " + name + " (health = " + health + ", energy = " + energy + ", happiness = " + happiness + ")"; 
        return greeting;
    }
    
    public String feed(){
        if (energy >= 7 && health < 6){
            health -= 2;
            happiness -= 2;
            health = statChecker(health);
            happiness = statChecker(happiness);
            return "Feeding... \n" + "Your " + breed + " " + name + " is throwing up after eating a bad chocolate cookie -> -2 health, -2 happiness \n";
        }
        else{
            energy += 2;
            happiness += 2;
            energy = statChecker(energy);
            happiness = statChecker(happiness);
            return "Feeding... \n" + "Your " + breed + " " + name + " is enjoying crunchy munchies!!! -> +2 energy, +2 happiness \n";
        }
    }
        
    public String walk(){
        if (energy < 5){
            health -= 1;
            happiness -= 3;
            health = statChecker(health);
            happiness = statChecker(happiness);
            return "Walking... \n" + "Your " + breed + " " + name + " is exhausted and is laying on the ground whimpering... -> -1 health, -3 happiness \n";
        }
        else{
            health += 2;
            happiness += 2;
            energy -= 1;
            health = statChecker(health);
            happiness = statChecker(happiness);
            energy = statChecker(energy);
            return "Walking... \n" + "Your " + breed + " " + name + " is having fun on THE WALK!!! -> +2 health, -1 energy, +2 happiness \n";
        }
    }
    
    public String play(){
        if (happiness < 2){
            happiness += 8;
            energy += 2;
            happiness = statChecker(happiness);
            energy = statChecker(energy);
            return "Playing... \n" + "Your " + breed + " " + name + " is feeling a lot better after BELLY RUBS!!! -> +2 energy, +8 happiness, \n";
        }
        else{
            health += 1;
            happiness += 2;
            energy -= 1;
            health = statChecker(health);
            happiness = statChecker(happiness);
            energy = statChecker(energy);
            return "Playing... \n" + "Your " + breed + " " + name + " loves playing fetch with you!!! -> +1 health,  -1 energy, +2 happiness \n";
        }
    }
}
