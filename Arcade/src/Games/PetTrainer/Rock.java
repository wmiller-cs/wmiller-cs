package Games.PetTrainer;
/**
 * The Rock class represents.
 *
 * @author Will Miller
 * @version 1.0
 */
public class Rock extends Pet{
    String shape;
    
    public Rock(String name, String shape){
        super.name = name;
        this.shape = shape;
    }
    
    public Rock(){
        this("Blippy", "square");
    }
    
    public String toString(){
        String greeting = "the bestest " + shape + " rock " + name + " (health = " + health + ", energy = " + energy + ", happiness = " + happiness + ")"; 
        return greeting;
    }
    
    public String feed(){
        if (happiness < 8){
            health += 1;
            happiness += 1;
            health = statChecker(health);
            happiness = statChecker(happiness);
            return "Feeding... \n" + "The bestest " + shape + " rock " + name +  " smiles as you devour a bag of Cheetos. -> +1 health, +1 happiness \n";
        }
        else{
            health += 1;
            happiness += 1;
            health = statChecker(health);
            happiness = statChecker(happiness);
            return "Feeding... \n" + "The bestest " + shape + " rock " + name +  " smiles as you munch on a big bean burrito. -> +1 health, +1 happiness \n";
        }
    }
        
    public String walk(){
        if (happiness < 8){
            health -= 1;
            happiness += 2;
            health = statChecker(health);
            happiness = statChecker(happiness);
            return "Walking... \n" + "The bestest " + shape + " rock " + name +  " gets dragged along through the dirt behind you, always smiling. -> -1 health, + 2 happiness \n";
        }
        else{
            health -= 1;
            happiness += 2;
            health = statChecker(health);
            happiness = statChecker(happiness);
            return "Walking... \n" + "While you stop at a street light, the bestest " + shape + " rock " + name +  "gets peed on, still smiling through it all. -> -1 health, +2 happiness \n";
        }
    }
    
    public String play(){
        int random = (int)(Math.random() * 10 + 1);
        if (random > 7){
            energy += 4;
            happiness += 1;
            energy = statChecker(energy);
            happiness = statChecker(happiness);
            return "Playing... \n" + "Lighting flashes through your window and strikes the bestest " + shape + " rock " + name +  " as you dance in your room. -> +4 energy, +1 happiness \n";
        }
        else{
            happiness += 1;
            happiness = statChecker(happiness);
            return "Playing... \n" + "The bestest " + shape + " rock " + name +  " smiles as you play 'toss the rock.' -> +1 happiness \n";
        }
    }
}
