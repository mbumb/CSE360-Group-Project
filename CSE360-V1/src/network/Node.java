package network;

public class Node{
    private String name;
    private int duration;
    public String[] dependicies;
    public String[] dependiciesCpy2;
    
    public void setName(String nodeName){
        name = nodeName;
    }
    
    public void setDuration(int nodeDuration){
        duration = nodeDuration;
    }
    
    public void setDependicies(String[] nodeDependicies){
        dependicies = new String[nodeDependicies.length+1];
        int i;
        for(i = 0; i < nodeDependicies.length; i++){
            dependicies[i] = nodeDependicies[i];
        }
        dependicies[i] = "}";
    }
    
    public void setDependiciesCpy2(String[] nodeDependicies){
        dependiciesCpy2 = new String[nodeDependicies.length];
        int i;
        for(i = 0; i < nodeDependicies.length; i++){
            dependiciesCpy2[i] = nodeDependicies[i];
        }
    }
    
    public String getName(){
        return name;
    }
    
    public int getDuration(){
        return duration;
    }
    
    
    public String getDependicies(){
        String dependiciesStr = "";
        for(int i = 0; i < dependicies.length; i++){
            dependiciesStr = dependiciesStr + " " + dependicies[i];
        }
        return dependiciesStr;
    }
    
    public Boolean dependiciesEmpty(){
        //if dependicies array is empty
        if(getDependicies().equals("  }")){
            return true;
        //if dependicies array is not empty
        }else{
            return false;
        }
    }
    
    public void swapDependicies(){
        int length = dependicies.length;
        String temp;
        temp = dependicies[0];
        for(int i = 0; i < length-1; i++){
            dependicies[i] = dependicies[i+1];
        }
        dependicies[length-1] = temp;
    }
    
    public String toString(){
        String universalNewLineChar = System.getProperty("line.separator");
        return "\n" +
               "Name: " + getName() + universalNewLineChar +
               "Duration: " + getDuration() + universalNewLineChar +
               "Dependicies: {" +  getDependicies() + universalNewLineChar;
    }
    
}//end Node class