package Games.PetTrainer;
/**
 * Pet .
 *
 * @author Will Miller
 * @version 1.0
 */
public class Pet{   
    String name;
    int health;
    int energy;
    int happiness;
    
    Pet(String name){
        this.name = name;
        this.health = 5;
        this.energy = 5;
        this.happiness = 5;
    }
    
    Pet(){
        this("Jake");
    }
    
    public String toString(){
        String greeting = "Pet " + name + " (health = " + health + ", energy = " + energy + ", happiness = " + happiness + ")"; 
        return greeting;
    }
    
    public String feed(){
        
        return "Pet " + name + " instead meditates. " + this.toString() + "\n";
    }
    
    public String walk(){
        return "Pet " + name + " instead meditates. " + this.toString() +  "\n";
    }
    
    public String play(){
        return "Pet " + name + " instead meditates. " + this.toString() +  "\n"; 
    }
    
    public int statChecker(int stat){
        if (stat < 0){
            return 0;
        }
        else if (stat > 10){
            return 10;
        }
        else{
            return stat;
        }
    }
}
