package network;

public class pathObject {
    private String path;
    private int duration;
    
    public void setPath(String nodePath){
        path = nodePath;
    }
    
    public void setDuration(int pathDuration){
        duration = pathDuration;
    }
    
    public String getPath(){
        return path;
    }
    
    public int getDuration(){
        return duration;
    }
    
    public String toString(){
        return "\n" + 
               getPath() + " : " + getDuration() + "\n";
    }
}
