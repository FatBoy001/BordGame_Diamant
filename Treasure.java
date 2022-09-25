import java.util.ArrayList;

public abstract class Treasure extends Card
{
    // The original value of this treasure card.
    private final int value;
    
    public Treasure(int type, int value)
    {
        super(type);
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    public abstract void share(ArrayList<Agent> receivers);
}
