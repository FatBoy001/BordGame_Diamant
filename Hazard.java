public class Hazard extends Card
{
    public Hazard(int type)
    {
        super(type);
    }

    @Override
    public String name()
    {
        /**************************************************************************************
         * The name of this hazard card depends on its type. There are 5 kinds of hazards in total.
         **************************************************************************************/

        /************ START OF CODE ************/
        switch(getType()){
            case 0:
                return "Spikes";
            case 1:
                return "Spiders";
            case 2:
                return "Mummy";
            case 3:
                return "Curse";
            case 4:
                return "Collapse";
            default:
                return "Unknown";
        }

        /*************  END OF CODE *************/
    }
    
    @Override
    public String toString()
    {
        return String.format("<H: %s>", this.name());
    }
}
