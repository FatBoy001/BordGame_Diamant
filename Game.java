import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game
{
    // The randomizer which is used for shuffling the deck.
    private static final Random RANDOM = new Random(System.currentTimeMillis());
    // The game consists of 5 rounds of exploration.
    private static final int ROUND = 5;
    // All explorers participate in the game.
    private final ArrayList<Agent> explorers = new ArrayList<>();
    // The deck of cards to be used for the game.
    private final ArrayList<Card> deck = new ArrayList<>();
    // A tomb-like path for exploration.
    private final ArrayList<Card> path = new ArrayList<>();
    // There are 5 sections (5 rounds) of exploration in the tomb, and one particular artifact is deposited in each section.
    private final ArrayList<Artifact> artifacts = new ArrayList<>();
    // The Hazards that occurred during the game play.
    private final ArrayList<Hazard> occurredHazards = new ArrayList<>();

    private final ArrayList<Agent> leave = new ArrayList<Agent>();

    public Game(String[] participants)
    {
        if (participants.length < 3 || participants.length > 8)
            throw new IllegalArgumentException("the number of participants is not between 3 and 8");
        try
        {
            for (int i = 0; i < participants.length; i++)
            {
                String participant = participants[i];
                Class<?> clazz = Class.forName(participant);
                Constructor<?> constructor = clazz.getConstructor(int.class);
                this.explorers.add((Agent) constructor.newInstance(i));
            }
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("invalid participant");
        }
        
        this.setUpCards();
    }
    
    public void runGame()
    {
        for (int round = 0; round < ROUND; round++)
        {
            /***************************************************************************
             * First, the game data should be initialized at the beginning of each round.
             **************************************************************************/

            /************ START OF CODE ************/
            for(Agent player:explorers){
                player.setInExploring(true);
            }       
            if(occurredHazards.size()!=0) {
                deck.remove(occurredHazards.get(0));
            }
            for(int i=path.size();i>0;i--){
                path.remove(i-1);
            }
             
            for(Card card:deck){
                if(card instanceof Gemstone){
                    ((Gemstone) card).resetValue();
                }
            }
            deck.add(artifacts.get(round));
            if(round!=0) {
                deck.remove(artifacts.get(round-1));
            }
            shuffleDeck();   
            int count=0; 
            System.out.printf("%s %d %s%n%n","ROUND",round+1,"START!");

            /*************  END OF CODE *************/

            while (this.isAnyoneStay())
            {
                /***************************************************************************
                 * During a round, all explorers explore the path in the ancient tomb and hunt for abundant treasures.
                 **************************************************************************/

                /************ START OF CODE ************/
                path.add(deck.get(count));
                Card pathCard = path.get(path.size()-1);
                ++count;
               
                if(pathCard instanceof Hazard){ 
                    int out=0;
                    for(Card card:path){
                       if(card.getType()==pathCard.getType() && card instanceof Hazard){
                           ++out;
                       }
                    }
                    if(out==2){
                       occurredHazards.add(0,(Hazard)pathCard);                                
                    }
                }
                if(pathCard instanceof Gemstone){
                    ((Gemstone)pathCard).share(getStayExplorers());
                }
                System.out.printf("[");
                for(Card card:path) {
                    if(path.size()==1||card==path.get(path.size()-1)){
                        System.out.printf("%s",card.toString());
                    }else {
                        System.out.printf("%s, ",card.toString());
                   }
                }
                System.out.printf("]%n");

                for(Agent player:explorers){
                    if(occurredHazards.size()!=0){
                        if(occurredHazards.get(0)==pathCard){
                            System.out.printf("Explorer %d left.%n",player.getNumber());   
                        }else if(player.isInExploring()){
                            System.out.printf("Explorer %d has %d gem(s).%n",player.getNumber(),player.getCollectedGems());
                        }else{
                            System.out.printf("Explorer %d left.%n",player.getNumber());                                   
                        }
                    }else{
                        if(player.isInExploring()){
                            System.out.printf("Explorer %d has %d gem(s).%n",player.getNumber(),player.getCollectedGems());
                        }else{
                            System.out.printf("Explorer %d left.%n",player.getNumber());                                   
                        }
                    }
                    
                }

                /*************  END OF CODE *************/

                System.out.println("----- STAY or LEAVE -----");

                Environment environment = this.createEnvironment();


                /************ START OF CODE ************/
                for(int i=leave.size();i>0;i--){
                    leave.remove(i-1);
                }
                if(occurredHazards.size()!=0){
                    if(occurredHazards.get(0)==pathCard){
                        for(Agent player:explorers){
                            player.flee();
                        }        	
                        System.out.printf("%s hazard occurs, all explorers attempt to flee!%n%n",occurredHazards.get(0).name());	
                    }else{
                        for(Agent player:getStayExplorers()){
                            if(player.isInExploring()){
                                player.act(environment);      
                            }
                            if(!player.isInExploring()){
                                System.out.printf("Explorer %d wants to leave.%n",player.getNumber());
                                leave.add(player);
                                
                            }
                        }
                        if(leave.size()!=0) { 
                            for(Card card:path){	
                                if(card instanceof Gemstone){
                                    ((Gemstone)card).share(leave);		
                                }else if(card instanceof Artifact){
                                    ((Artifact)card).share(leave);	
                                }
                            }	
                            for(Agent player:leave){
                                player.storeGemsIntoTent();
                            }
                           }else{ 
                            System.out.printf("Everyone keeps exploring.%n");
                        }
                        System.out.println();     	
                    }
                }else{
                    for(Agent player:getStayExplorers()){
                        if(player.isInExploring()){
                            player.act(environment);      
                        }
                        if(!player.isInExploring()){
                            System.out.printf("Explorer %d wants to leave.%n",player.getNumber());
                            leave.add(player);
                            
                        }
                    }
                    if(leave.size()!=0) { 
                        for(Card card:path){	
                            if(card instanceof Gemstone){
                                ((Gemstone)card).share(leave);		
                            }else if(card instanceof Artifact){
                                ((Artifact)card).share(leave);	
                            }
                        }	
                        for(Agent player:leave){
                            player.storeGemsIntoTent();
                        }
                       }else{ 
                        System.out.printf("Everyone keeps exploring.%n");
                    }
                    System.out.println();
                }
                
                /*************  END OF CODE *************/
            }

            /**************************************************************************
             * At the end of a round, all explorers finish their exploration and return to the camp with treasure.
             **************************************************************************/

            /************ START OF CODE ************/
            System.out.printf("ROUND %d END!%n%n",round+1);
            for(Agent player:explorers){
            	player.storeGemsIntoTent();
            }
            /*************  END OF CODE *************/
        }

        System.out.println("GAME OVER!");
        System.out.println();
        System.out.println("----- Final result -----");

        for (Agent explorer : this.explorers)
            System.out.println(explorer + ": " + explorer.totalValue());

        System.out.println();
        System.out.println("Winner: " + this.getWinners());
    }

    private void setUpCards()
    {
        this.deck.add(new Hazard(0));
        this.deck.add(new Hazard(0));
        this.deck.add(new Hazard(0));
        this.deck.add(new Hazard(1));
        this.deck.add(new Hazard(1));
        this.deck.add(new Hazard(1));
        this.deck.add(new Hazard(2));
        this.deck.add(new Hazard(2));
        this.deck.add(new Hazard(2));
        this.deck.add(new Hazard(3));
        this.deck.add(new Hazard(3));
        this.deck.add(new Hazard(3));
        this.deck.add(new Hazard(4));
        this.deck.add(new Hazard(4));
        this.deck.add(new Hazard(4));
        
        this.deck.add(new Gemstone(0, 1));
        this.deck.add(new Gemstone(1, 2));
        this.deck.add(new Gemstone(2, 3));
        this.deck.add(new Gemstone(3, 4));
        this.deck.add(new Gemstone(4, 5));
        this.deck.add(new Gemstone(4, 5));
        this.deck.add(new Gemstone(5, 7));
        this.deck.add(new Gemstone(5, 7));
        this.deck.add(new Gemstone(6, 9));
        this.deck.add(new Gemstone(7, 11));
        this.deck.add(new Gemstone(7, 11));
        this.deck.add(new Gemstone(8, 13));
        this.deck.add(new Gemstone(9, 14));
        this.deck.add(new Gemstone(10, 15));
        this.deck.add(new Gemstone(11, 17));
        
        this.artifacts.add(new Artifact(0, 5));
        this.artifacts.add(new Artifact(1, 7));
        this.artifacts.add(new Artifact(2, 8));
        this.artifacts.add(new Artifact(3, 10));
        this.artifacts.add(new Artifact(4, 12));
    }

    private void shuffleDeck()
    {
        Collections.shuffle(this.deck, RANDOM);
    }

    private ArrayList<Agent> getStayExplorers()
    {
        /**************************************************************************
         * Get all explorers who stay in the tomb.
         **************************************************************************/

        /************ START OF CODE ************/
        ArrayList<Agent> stay = new ArrayList<Agent>();
        for(Agent player:explorers){
            if(player.isInExploring()){
                stay.add(player);
            }
        }
        return stay;
        /*************  END OF CODE *************/
    }

    private boolean isAnyoneStay()
    {
        /***************************************************************************
         * Check if there is anyone who stays in the tomb.
         **************************************************************************/

        /************ START OF CODE ************/
        if(getStayExplorers().size()==0){
            return false;
        }else{
            return true;
        }
        /*************  END OF CODE *************/
    }

    private ArrayList<Agent> getWinners()
    {
        /***************************************************************************
         * The winners will be the explorers who hold the highest value of treasure.
         **************************************************************************/
        int max = 0;
        ArrayList<Agent> winner = new ArrayList<>();
        for(Agent player:explorers){
            if(max<player.totalValue()){
                max = player.totalValue();
            }
        }
        for(Agent player:explorers){
            if(player.totalValue()==max){
                winner.add(player);
            }
        }
        return winner;
        /*************  END OF CODE *************/
    }

    private Environment createEnvironment()
    {
        Environment environment = new Environment();

        environment.setCountOfExplorers(this.explorers.size());
        environment.setCountOfStayExplorers(this.getStayExplorers().size());
        environment.setPath(this.path);
        environment.setOccurredHazards(this.occurredHazards);

        return environment;
    }

    private static void doNothing(long millisecond)
    {
        if (millisecond > 2000)
            throw new IllegalArgumentException("timeout value is over 2000");

        try
        {
            Thread.sleep(millisecond);
        }
        catch (InterruptedException e)
        {
            throw new IllegalStateException("unexpected interruption");
        }
    }
}
