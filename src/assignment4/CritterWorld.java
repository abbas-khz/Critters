package assignment4;

import java.util.ArrayList;
import java.util.List;

public class CritterWorld {
    //first y, then x
    static private ArrayList<Critter>[][] positions=new ArrayList[Params.world_height][Params.world_width];

    static {
        for(int i=0;i<Params.world_height;i++){
            for (int j=0;j<Params.world_width;j++){
                positions[i][j]=new ArrayList<>();
            }
        }
    }

    /**
     * adds a critter to a passed position in the world(the positions array)
     * @param c critter to add
     * @param y y-coordinate
     * @param x x-coordinate
     */
    public static void addToPosition(Critter c,int y, int x){
        x=x%(Params.world_width);
        y=y%(Params.world_height);
        if(x<0){
            x=Params.world_width+x;
        }
        if(y<0){
            y=Params.world_height+y;
        }

        positions[y][x].add(c);
    }


    /**
     * checks whether or not a passed position is occupied
     * @param x x coordinate
     * @param y y coordinate
     * @return true if occupied, false otherwise
     */
    public static boolean occupied(int x, int y){
        x=x%Params.world_width; // make sure that inputs are inside the world coordinates
        y=y%Params.world_height;
        if(x<0){
            x=Params.world_width+x;
        }
        if(y<0){
            y=Params.world_height+y;
        }

        if(positions[y][x].size()>=1){
            return true;
        }else {
            return false;
        }
    }

    /**
     * checks whether or not there is another critter in a passed critter's position
     * @param a critter whose position we want to check
     * @return returns a critter that has the same position as a, if none exist it returns null
     */
    public static Critter checkPosition(Critter a,int x, int y){
        List<Critter> checkList=positions[y][x];
        if(checkList.size()>1){
            int pos=checkList.indexOf(a);
            for(int i=0;i<checkList.size();i++){
                if(i!=pos){
                    return checkList.get(i);
                }
            }
        }
        return null;

    }


    /**
     * remove the matching critter from the world
     * @param a critter to remove from list
     * @return true if was removed, false otherwise
     */
    public static boolean remove(Critter a,int x,int y){
        List<Critter> positionList=positions[y][x];

        return positionList.remove(a);

    }

    /**
     * changes the position of a passed Critter in the positions array
     * @param x new x-coordinate
     * @param y new y-coordinate
     * @param c critter whose position is changing
     */
    public static void changePosition(int x,int y,Critter c){
        positions[y][x].remove(c);
        positions[x][y].add(c);
    }

    /**
     * removes all the critters from the positions array
     */
    public static void deleteWorld(){
        for(int i=0;i<positions.length;i++)
            for (int j=0;j<positions[0].length;j++){
                if(positions[i][j]!=null){
                    positions[i][j].clear();
                }
            }
    }

    /**
     * checks if a critter exists in a passed coordinate in the positions array
     * @param c critter to check
     * @param x x-coordinate of critter
     * @param y y-coordinate of critter
     * @return true if critter is in the passed position, false otherwise
     */
    public static boolean containsInPosition(Critter c, int x, int y){
        List<Critter> checkList=positions[y][x];
        if(checkList.contains(c)){
            return true;
        }else{
            return false;
        }
    }

}
