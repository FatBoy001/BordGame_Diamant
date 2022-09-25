import java.util.ArrayList;

public class Gemstone extends Treasure
{
    // The current value (gems) remains on the card.
    private int remainValue;
    
    public Gemstone(int type, int value)
    {
        super(type, value);
        this.remainValue = value;
    }

    public int getRemainValue()
    {
        /**************************************************************************************
         * Get the variable $remainValue via this accessor method.
         **************************************************************************************/

        /************ START OF CODE ************/
        return remainValue;

        /*************  END OF CODE *************/
    }
    
    public void resetValue()
    {
        /**************************************************************************************
         * Reset the current value of this card to its original value.
         **************************************************************************************/

        /************ START OF CODE ************/
        this.remainValue=getValue();

        /*************  END OF CODE *************/
    }

    @Override
    public String name()
    {
        return "Gemstone";
    }

    @Override
    public void share(ArrayList<Agent> receivers)
    {
        /**************************************************************************************
         * Evenly share the gems that they find with all receivers, then the leftover remains.
         **************************************************************************************/

        /************ START OF CODE ************/
        int playerNum = receivers.size();
        for(Agent player:receivers){
              player.addCollectedGems(remainValue/playerNum);
        }
        this.remainValue = remainValue%playerNum;

        /*************  END OF CODE *************/
    }
    
    @Override
    public String toString()
    {
        return String.format("<G: %d/%d>", this.remainValue, this.getValue());
    }
}
