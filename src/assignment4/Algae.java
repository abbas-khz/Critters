package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Algae extends TestCritter {

	public Algae(){
		setX_coord(getRandomInt(Params.world_width));
		setY_coord(getRandomInt(Params.world_height));
		setEnergy(Params.start_energy);
		getPopulation().add(this);
		CritterWorld.addToPosition(this,this.getY_coord(),this.getX_coord());
	}


	public String toString() { return "@"; }
	
	public boolean fight(String not_used) { return false; }
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}
}
