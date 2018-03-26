package assignment4;


/*This Critter is called Ronald. it picks a random direction to move in every time.
* Ronald always tries to reproduce, regardless of it's current energy. Ronald always
* picks to fight. It world character is R*/
public class Critter2 extends Critter {
    private int dir;

    /**
     * default constructor for Critter2
     */
    public Critter2(){
        dir=Critter.getRandomInt(8);
    }

    /**
     * decides whether or not to engage a passed critter
     * @param oponent critter to engage
     * @return always true
     */
    public  boolean fight(String oponent){
        return true;
    }

    /**
     * walks in dir, picks a random direction, attempts to reproduce
     */
    public void doTimeStep() {
    walk(dir);
    dir=(getEnergy()*dir)%8;
    Critter2 baby=new Critter2();
    reproduce(baby,dir);
    dir=(getEnergy()*dir)%8;
    }


    @Override
    public String toString() {
        return "2";
    }
}
