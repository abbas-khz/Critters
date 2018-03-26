package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


//import com.sun.java.util.jar.pack.Package;

import java.util.ArrayList;
import java.util.List;
/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();

	/**
	 * returns a random number from 0 to just before max
	 * @param max upper bound for random numbers
	 * @return a random integer
	 */
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	/**
	 * sets the seed for random number generation in getRandomInt
	 * @param new_seed the seed to use
	 */
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }

	private void setEnergy(int newEnergy){energy=newEnergy;}
	
	private int x_coord;
	private int y_coord;


	/**
	 * moves the critter in the world with the passed direction
	 * @param direction direction for the critter to move in
	 */
	protected final void walk(int direction) {
		int oldX=x_coord;
		int oldY=y_coord;
		CritterWorld.remove(this,this.x_coord,this.y_coord);
		switch (direction){
			case 0:
				x_coord+=1;
				break;
			case 1:
				x_coord+=1;
				y_coord+=1;
				break;
			case 2:
				y_coord+=1;
				break;
			case 3:
				y_coord+=1;
				x_coord-=1;
				break;
			case 4:
				x_coord-=1;
				break;
			case 5:
				x_coord-=1;
				y_coord-=1;
				break;
			case 6:
				y_coord-=1;
				break;
			case 7:
				x_coord+=1;
				y_coord-=1;
				break;
		}
		if(x_coord>=Params.world_width){
			x_coord=x_coord%Params.world_width;
		}
		if(y_coord>=Params.world_height){
			y_coord=y_coord%Params.world_height;
		}
		if(x_coord<0){
			x_coord=Params.world_width-1;
		}
		if(y_coord<0){
			y_coord=Params.world_height-1;
		}
		energy-=Params.walk_energy_cost;
		CritterWorld.addToPosition(this,this.y_coord,this.x_coord);

	}

	/**
	 * moves twice as much as walk in the specified direction
	 * @param direction direction to run in
	 */
	protected final void run(int direction) {
		walk(direction);
		walk(direction);
		CritterWorld.remove(this,this.x_coord,this.y_coord);
		energy-=Params.run_energy_cost;
		energy+=2*Params.walk_energy_cost;
		CritterWorld.addToPosition(this,this.y_coord,this.x_coord);
	}

	/**
	 * produces spawns of a parent critter
	 * @param offspring a child object made inside the timeStep function
	 * @param direction direction to put the offspring
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy <Params.min_reproduce_energy){
			return;
		}
		offspring.setEnergy(energy/2 + Params.walk_energy_cost);
		offspring.x_coord=this.x_coord;
		offspring.y_coord=this.y_coord;
		energy-=(int)Math.ceil(energy/2);
		offspring.walk(direction);
		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
			Class c = Class.forName(critter_class_name);
			Critter crt= (Critter)c.newInstance();
			crt.x_coord=getRandomInt(Params.world_width);
			crt.y_coord=getRandomInt(Params.world_height);
			crt.energy=Params.start_energy;
			CritterWorld.addToPosition(crt,crt.y_coord,crt.x_coord);
			population.add(crt);
		}catch (ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}catch (InstantiationException e){
			throw new InvalidCritterException(critter_class_name);
		}catch (IllegalAccessException e){
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try{
			Class c = Class.forName(critter_class_name);
			Critter crt= (Critter)c.newInstance();
			String name;
			name=crt.getClass().getName();

			for(Critter critter:population){
				if(critter_class_name.compareTo(critter.getClass().getName())==0){
					result.add(critter);
				}
			}

			return result;

		}catch (ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}catch (InstantiationException e){
			throw new InvalidCritterException(critter_class_name);
		}catch (IllegalAccessException e){
			throw new InvalidCritterException(critter_class_name);
		}

	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		// Complete this method.
		population.clear();
		babies.clear();
		CritterWorld.deleteWorld();
	}

	/**
	 * calls doTimeStep() for all living critters, initiates encounters, updates rest energy, and adds all the offspring to the world
	 */
	public static void worldTimeStep() {
		// Complete this method.
		for(Critter c:population){
			CritterWorld.remove(c,c.x_coord,c.y_coord);
			c.doTimeStep();
			if(!CritterWorld.containsInPosition(c,c.x_coord,c.y_coord)){
				CritterWorld.addToPosition(c,c.y_coord,c.x_coord);
			}
		}

		completeEncounters();
		updateRestEnergy();
		genAlgea();
		population.addAll(babies);
		babies.clear();



	}

	/**
	 * completes all possible encounters in the world
	 */
	private static void completeEncounters(){
		List<Critter> toRemove=new ArrayList<>();
		for (Critter c: population){
			Critter check;
			check=CritterWorld.checkPosition(c,c.x_coord,c.y_coord);
			if(toRemove.contains(c)||toRemove.contains(check)){
				continue;
			}
			if(c.getEnergy()<=0){
				if(!toRemove.contains(c)){
					toRemove.add(c);
				}
				continue;
			}
			if(check==null){
				continue;
			}
			if(check.getEnergy()<=0){
				if(!toRemove.contains(check)){
					toRemove.add(check);
				}
			}
			if(check!= null){
				boolean c1;
				boolean c2;
				c1=c.fight(check.toString());
				c2=check.fight(c.toString());

				if(c.getEnergy()<=0){
					if(!toRemove.contains(c)){
						toRemove.add(c);
					}
					continue;
				}
				if(check==null){
					continue;
				}
				if(check.getEnergy()<=0){
					if(!toRemove.contains(check)){
						toRemove.add(check);
					}
					continue;
				}

				//both critters want to fight
				if(c1 && c2){
						fightEncounter(c,check,toRemove);
				}else if(c1 && !c2){//Critter 1 wants to fight, 2 doesn't
					if(!(check.y_coord != c.y_coord || check.x_coord != c.x_coord)){
						fightEncounter(c,check,toRemove);
					}
				}else if(!c1 && c2){
					if(!(check.y_coord != c.y_coord || check.x_coord != c.x_coord)){
						fightEncounter(c,check,toRemove);
					}
				}else if( !c1 && !c2){
					if(!(check.y_coord != c.y_coord || check.x_coord != c.x_coord)){
						fightEncounter(c,check,toRemove);
					}
				}
			}
			if(population.size()<=1){
				break;
			}
		}
		for(Critter c: toRemove){
			CritterWorld.remove(c,c.x_coord,c.y_coord);
			population.remove(c);
		}
	}

	/**
	 * deducts the rest energy from all living critters
	 */
	private static void updateRestEnergy(){
		for (Critter c:population){
			CritterWorld.remove(c,c.x_coord,c.y_coord);
			c.energy-=Params.rest_energy_cost;
			CritterWorld.addToPosition(c,c.y_coord,c.x_coord);
		}
	}

	/**
	 * generates new algea in the world based on the information in params
	 */
	private static void genAlgea(){
		for(int i=0;i<Params.refresh_algae_count;i++){
				Critter a=new Algae();
		}
	}

	/**
	 * completes a fight between two passed critters
	 * @param c1 critter1
	 * @param c2 critter2
	 */
	private static void fightEncounter(Critter c1,Critter c2,List<Critter> toRemove){
		int atk1;
		atk1=getRandomInt(c1.energy);
		int atk2;
		atk2=getRandomInt(c2.energy);
		if(atk1>=atk2){
			CritterWorld.remove(c1,c1.x_coord,c1.y_coord);
			c1.setEnergy(c1.getEnergy()+(c2.getEnergy()/2));
			CritterWorld.addToPosition(c1,c1.y_coord,c1.x_coord);
			toRemove.add(c2);
//			CritterWorld.remove(c2,c2.x_coord,c2.y_coord);
		}if(atk1<atk2){
			CritterWorld.remove(c2,c2.x_coord,c2.y_coord);
			c2.setEnergy(c2.getEnergy()+(c1.getEnergy()/2));
			CritterWorld.addToPosition(c2,c2.y_coord,c2.x_coord);
			toRemove.add(c1);
//			CritterWorld.remove(c2,c2.x_coord,c2.y_coord);
		}

	}

	/**
	 * runs away from fight if possible
	 * @return true if escape was possible, false otherwise
	 */
	private boolean runAwayFromFight(){
		int possibleDirections[]=new int[8];
		if(!CritterWorld.occupied(this.x_coord+2,this.y_coord))
			possibleDirections[0]=1;
		if(!CritterWorld.occupied(this.x_coord+2,this.y_coord+2))
			possibleDirections[1]=1;
		if(!CritterWorld.occupied(this.x_coord,this.y_coord+2))
			possibleDirections[2]=1;
		if(!CritterWorld.occupied(this.x_coord-2,this.y_coord+2))
			possibleDirections[3]=1;
		if(!CritterWorld.occupied(this.x_coord-2,this.y_coord))
			possibleDirections[4]=1;
		if(!CritterWorld.occupied(this.x_coord-2,this.y_coord-2))
			possibleDirections[5]=1;
		if(!CritterWorld.occupied(this.x_coord,this.y_coord-2))
			possibleDirections[6]=1;
		if(!CritterWorld.occupied(this.x_coord+2,this.y_coord-2))
			possibleDirections[7]=1;

		for (int i=0;i<possibleDirections.length;i++){
			if(possibleDirections[i]==1) {
				run(i);
				return true;
			}
		}

		return false;
	}

	/**
	 * outputs a simple representation of the critter world to the console
	 */
	public static void displayWorld() {
		// a char array to print
		String [][]toPrint=new String[Params.world_height+2][Params.world_width+2];

		//make corners
		toPrint[0][0]="+";
		toPrint[Params.world_height+1][0]="+";
		toPrint[0][Params.world_width+1]="+";
		toPrint[Params.world_height+1][Params.world_width+1]="+";

		//make horizontal edges
		for(int i=1;i<Params.world_width+1;i++){
			toPrint[0][i]="-";
			toPrint[Params.world_height+1][i]="-";
		}

		//make vertical edges
		for(int i=1;i<Params.world_height+1;i++){
			toPrint[i][0]="|";
			toPrint[i][Params.world_width+1]="|";
		}

		//fill the critter positions
		for(Critter c:population){
			toPrint[c.y_coord+1][c.x_coord+1]=c.toString();
		}


		//output to the screen
		for(int i=0;i<Params.world_height+2;i++) {
			for (int j = 0; j < Params.world_width+2; j++) {
				if (toPrint[i][j] == null) {
					toPrint[i][j] = " ";
				}

				System.out.print(toPrint[i][j]);
			}
			System.out.println();
		}
	}
}
