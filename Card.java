public abstract class Card
{
    // Type of this card.
    private final int type;

    public Card(int type)
    {
        this.type = type;
    }

    public abstract String name();

    public int getType()
    {
        return this.type;
    }
}
