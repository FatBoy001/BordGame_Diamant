import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Paths;


public class Player extends Agent
{
    public Player(int number)
    {
        super(number);
    }

    @Override
    public boolean decision(String fileName) throws IOException
    {
        /**************************************************************************************
        * Here goes algorithm for the gameplay. In order to get more treasure and reduce the risk,
        **************************************************************************************/

        /************ START OF CODE ************/
        int[] hazardsArray = new int[5];
        int[] remainArray = new int[22];
        int[] valueArray = new int[22];
        int[] occurredArray = new int[5];
        int hazardsTotal=0;
        int remainTotal=0;
        int valueTotal=0;
        int arkTotal=0;

        ArrayList<String> mainData = new ArrayList<>();
        try(Scanner input = new Scanner(Paths.get(fileName))){  
            while(input.hasNext()){
                mainData.add(input.nextLine());
            } 
        }catch(IOException e){
            System.err.println("error");
        }

 
        mainData.remove(7);
        mainData.remove(6);
        mainData.remove(5);
        mainData.remove(1);
        mainData.remove(0);

        ArrayList<String> collectGems = new ArrayList<>();
        String[] tokenOfcollectGems = mainData.get(4).split("Collected gems: |\\[|\\]|<|>|,");
        for(String i:tokenOfcollectGems){
            if(!(i.equals("")||i.equals(" "))){
                collectGems.add(i);
            }
        }

      
        ArrayList<String> occurredHazard = new ArrayList<>();
        String[] tokenOfoccurred = mainData.get(1).split("Occurred hazards: |\\[|\\]|<|>|,");
        for(String i:tokenOfoccurred){
            if(!(i.equals("")||i.equals(" "))){
                occurredHazard.add(i);
            }
        }
        for(String i:occurredHazard){
            String startOfdata = i.substring(0, i.indexOf(" "));
          
            String occurred = i.substring(startOfdata.length()+1, i.length());      
            occurredArray[hazardsType(occurred)]+=1;
        }
        ArrayList<String> path = new ArrayList<>();
        String[] tokenOfpath = mainData.get(0).split("Path: |\\[|\\]|<|>|,");
        for(String i:tokenOfpath){
            if(!(i.equals("")||i.equals(" "))){
                path.add(i);
            }
        }
        int count =0;
        for(String i:path){
            if(i.contains("H: ")){
                String startOfdata = i.substring(0, i.indexOf(" "));
              
                String hazard = i.substring(startOfdata.length()+1, i.length());
                hazardsArray[hazardsType(hazard)]=2;
                
            }else if(i.contains("G: ")){
                String startOfdata = i.substring(0, i.indexOf(" "));
                String endOfRemain = i.substring(0, i.indexOf("/"));
                String remain = i.substring(startOfdata.length()+1, endOfRemain.length());
                String value = i.substring(endOfRemain.length()+1, i.length());

                remainArray[count]=Integer.parseInt(remain);
                valueArray[count]=Integer.parseInt(value);
            }else if(i.contains("A: ")){
                ArrayList<String> ark = new ArrayList<>();
                String[] tokenOfArk = i.split("\\s|:|>|\\]|\\[|<");
                for(String x:tokenOfArk){
                    if(!(x.equals("")||x.equals(" "))){
                    ark.add(x);
                    }
                }
                if(ark.get(ark.size()-1).equals("---")){
                    arkTotal=0;
                }else{
                    arkTotal=Integer.parseInt(ark.get(ark.size()-1));
                }
                
            }
            count++;
        }
        
        for(int i=0;i<hazardsArray.length;i++){
            hazardsArray[i]=hazardsArray[i]-occurredArray[i];
        }
        
        for(int x:hazardsArray){hazardsTotal+=x;}
       
        for(int x:remainArray){remainTotal+=x;}
   
        for(int x:valueArray){valueTotal+=x;}
    
        int collectGemsTotal = Integer.parseInt(collectGems.get(0));

        if((124-valueTotal-arkTotal)-2*(hazardsTotal)*(collectGemsTotal+remainTotal+arkTotal)>=0){
            return (Math.random() < 0.9);
        }else{
            return (Math.random() < 0.3);
        }
        /*************  END OF CODE *************/
    }
    
    private int hazardsType(String H){
        switch(H){
            case "Spikes":
                return 0;
            case "Spiders":
                return 1;
            case "Mummy":
                return 2;
            case "Curse":
                return 3;
            case "Collapse":
                return 4;
            default:
                return -1;
        }
    }
}
