import java.util.ArrayList;

public class Artifact extends Treasure
{
    // It determines whether this artifact is currently deposited in the tomb or not.
    private boolean inTomb;
    
    public Artifact(int type, int value)
    {
        super(type, value);
        this.inTomb = true;
    }

    public boolean isInTomb()
    {
        /**************************************************************************************
         * Get the variable $inTomb via this accessor method.
         **************************************************************************************/

        /************ START OF CODE ************/
        return inTomb;
        /*************  END OF CODE *************/
    }

    public void setInTomb(boolean inTomb)
    {
        this.inTomb=inTomb;
    }

    @Override
    public String name()
    {
        /**************************************************************************************
         * The name of this artifact card depends on its type. There are 5 kinds of artifacts in total.
         **************************************************************************************/

        /************ START OF CODE ************/
        switch(this.getType()){
            case 0:
                return "Meteoric Dagger";
            case 1:
                return "Ankh";
            case 2:
                return "Falcon Pectoral";
            case 3:
                return "Crook and Flail";
            case 4:
                return "Mask of Tutankhamun";
            default:
                return "Unknown";
        }   
        /*************  END OF CODE *************/
    }
    
    @Override
    public void share(ArrayList<Agent> receivers)
    {
        /**************************************************************************************
         * A valuable artifact can be taken away by the one and only one explorer who is leaving the tomb.
         **************************************************************************************/

        /************ START OF CODE ************/
        if(inTomb && receivers.size()==1){
            receivers.get(0).addOwnedArtifacts(this);
            setInTomb(false);
        }
        /*************  END OF CODE *************/
    }
    
    @Override
    public String toString()
    {
        if (this.inTomb)
            return String.format("<A: %s %d>", this.name(), this.getValue());
        else
            return "<A: --->";
    }
}
