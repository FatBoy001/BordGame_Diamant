import java.io.IOException;

public class Computer extends Agent
{
    public Computer(int number)
    {
        super(number);
    }

    @Override
    public boolean decision(String fileName) throws IOException
    {
        return (Math.random() < 0.65);
    }
}
