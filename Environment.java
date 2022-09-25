import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Environment
{
    // An algorithm of message digesting which is used to generate the name of a file.
    private static final MessageDigest MD;

    // Count of all explorers who participate in the game.
    private int countOfExplorers;
    // Count of all explorers who stay in the tomb currently.
    private int countOfStayExplorers;
    // The pathway which has been explored up to now.
    private ArrayList<Card> path;
    // All the removed hazards so far. 
    private ArrayList<Hazard> occurredHazards;

    static
    {
        try
        {
            MD = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Environment()
    {
    }

    public void setCountOfExplorers(int countOfExplorers)
    {
        this.countOfExplorers = countOfExplorers;
    }

    public void setCountOfStayExplorers(int countOfStayExplorers)
    {
        this.countOfStayExplorers = countOfStayExplorers;
    }

    public void setPath(ArrayList<Card> path)
    {
        this.path = new ArrayList<>(path);
    }

    public void setOccurredHazards(ArrayList<Hazard> occurredHazards)
    {
        this.occurredHazards = new ArrayList<>(occurredHazards);
    }

    public String generateFile(Agent agent) throws IOException
    {
        String input = String.valueOf(agent.hashCode() + System.currentTimeMillis());
        BigInteger digest = new BigInteger(1, MD.digest(input.getBytes()));
        String fileName = String.format("%32s.txt", digest.toString(16)).replaceAll(" ", "0");

        ArrayList<String> ownedArtifactsAsString = new ArrayList<>();

        for (Artifact artifact : agent.getOwnedArtifacts())
            ownedArtifactsAsString.add(String.format("<A: %s %d>", artifact.name(), artifact.getValue()));

        PrintWriter writer = new PrintWriter(new FileOutputStream(fileName));

        writer.println("*** Game Status ***");
        writer.println();
        writer.println("Path: " + this.path);
        writer.println("Occurred hazards: " + this.occurredHazards);
        writer.println(this.countOfStayExplorers + " out of " + this.countOfExplorers + " explorers stay in the tomb.");
        writer.println();
        writer.println("*** Explorer Information ***");
        writer.println();
        writer.println("Number: " + agent.getNumber());
        writer.println("Collected gems: " + agent.getCollectedGems());
        writer.println("Gems inside tent: " + agent.getGemsInsideTent());
        writer.println("Owned artifacts: " + ownedArtifactsAsString);

        writer.flush();
        writer.close();

        return fileName;
    }
}