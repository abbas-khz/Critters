package assignment4;

/*This Critter is called the "Diagonal Critter". it moves diagonally through the world. it doesn't reproduce.
* Additionally, this Critter will have a random chance of running away or fighting depending on whether or not
* it has an even or odd value for it's energy. The symbol for it in the world view is a "D". */


public class Critter1 extends Critter {

    /**
     * chooses whther or not fight a passed critter
     * @param oponent Critter who will be fought
     * @return random choice between true or false based on the energy level
     */
    public  boolean fight(String oponent){
        if(getEnergy()%2==0) {
            run(1);
            return false;
        }
        return true;
    }

    /**
     * calls walk(2). "moves in a diagonal direction"
     */
    public void doTimeStep() {
        walk(2);
    }


    @Override
    public String toString() {
        return "1";
    }
}
