package Games;

public abstract class AGame implements IGame, Comparable<AGame>{
    public String name;
    public AGame(){
        name = "";
    }

    public void play(){

    }

    int compareTo(String a){
        return this.name.compareTo(a);
    }
}
