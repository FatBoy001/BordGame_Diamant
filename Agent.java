import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Agent
{
    // Number of this explorer.
    private final int number;
    // This explorer's status of exploration, which determines whether they stay in the tomb or not.
    private boolean inExploring;
    // The gems that this explorer has collected during a round.
    private int collectedGems;
    // The gems which were stored safely in this explorer's tent.
    private int gemsInsideTent;
    // This explorer's possession of artifacts.
    private final ArrayList<Artifact> ownedArtifacts = new ArrayList<>();
    
    public Agent(int number)
    {
        this.number = number;
        this.inExploring = false;
        this.collectedGems = 0;
        this.gemsInsideTent = 0;
    }

    public int getNumber()
    {
        /****************************************************************************************
         * Get the variable $number via this accessor method.
         **************************************************************************************/
        
        /************ START OF CODE ************/
        return number;
        /*************  END OF CODE *************/
    }

    public boolean isInExploring()
    {
        /***************************************************************************************
         * Get the variable $inExploring via this accessor method.
         **************************************************************************************/

        /************ START OF CODE ************/
        return inExploring;
        /*************  END OF CODE *************/
    }

    public void setInExploring(boolean inExploring)
    {
        /***************************************************************************************
         * Set the variable $inExploring via this mutator method.
         **************************************************************************************/

        /************ START OF CODE ************/
        this.inExploring = inExploring;
        /*************  END OF CODE *************/
    }

    public int getCollectedGems()
    {
        /***************************************************************************************
         * Get the variable $collectedGems via this accessor method.
         **************************************************************************************/

        /************ START OF CODE ************/
        return collectedGems;
        /*************  END OF CODE *************/
    }

    public int getGemsInsideTent()
    {
        /***************************************************************************************
         * Get the variable $gemsInsideTent via this accessor method.
         **************************************************************************************/

        /************ START OF CODE ************/
        return gemsInsideTent;
        /*************  END OF CODE *************/
    }

    public void addCollectedGems(int additionalGems)
    {
        /***************************************************************************************
         * Add in additional value of gems to this explorer's collection.
         **************************************************************************************/

        /************ START OF CODE ************/
        this.collectedGems += additionalGems;
        /*************  END OF CODE *************/
    }

    public void storeGemsIntoTent()
    {
        /***************************************************************************************
         * Make this explorer's holdings store into their tent.
         **************************************************************************************/

        /************ START OF CODE ************/
        this.gemsInsideTent=this.gemsInsideTent+this.collectedGems;    
        this.collectedGems =0;
        /*************  END OF CODE *************/
    }

    public ArrayList<Artifact> getOwnedArtifacts()
    {
        /***************************************************************************************
         * Get the variable $ownedArtifacts via this accessor method.
         **************************************************************************************/

        /************ START OF CODE ************/
        return ownedArtifacts;
        /*************  END OF CODE *************/
    }

    public void addOwnedArtifacts(Artifact artifact){
        ownedArtifacts.add(artifact);
    }

    public void flee()
    {
        /***************************************************************************************
         * When a hazard occurs, all explorers who still stay in the tomb are forced to flee and leave all treasure behind.
         **************************************************************************************/

        /************ START OF CODE ************/
        this.collectedGems=0;
        this.inExploring = false;
        /*************  END OF CODE *************/
    }

    public int totalValue()
    {
        /***************************************************************************************
         * The gems stored in their tent, plus the artifacts they owned, are the value that they totally possessed.
         **************************************************************************************/

        /************ START OF CODE ************/
        int total=0;

        for(Artifact artifacts:ownedArtifacts){
            total+=artifacts.getValue();
        }
        total += gemsInsideTent;
        return total;

        /*************  END OF CODE *************/
    }
    
    @Override
    public String toString()
    {
        return "Explorer " + this.number;
    }

    public final void act(Environment environment)
    {
        if (this.inExploring)
        {
            String fileName = "";

            try
            {
                fileName = environment.generateFile(this);
                this.inExploring = this.decision(fileName);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e.getMessage());
            }
            finally
            {
                File file = new File(fileName);
                file.delete();
            }
        }
    }

    public abstract boolean decision(String fileName) throws IOException;
}
