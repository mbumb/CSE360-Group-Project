package network;
import java.util.*;

public class LinkedList{
    private class listNode{
        public Node data;
        public listNode next;
    }
    
    //head 
    private listNode head;
    
    //-------------- constructs empty LinkedList -------------------------------
    public LinkedList(){
        head = null;
    }//end constructor
    
    //------------- Reset Linked List ------------------------------------------
    public void resetList(){
        head = null;
    }
    
    //------------- Add element to end of LinkedList -------------------------
    public void addLast(Node element){
        listNode newNode = new listNode();
        newNode.data = element; //putting data in new node
        if(head == null){ //if list is empty
            newNode.next = head;
            head = newNode;
        }else{ //insert at end of list
            listNode temp = new listNode();
            temp = head;
            while(temp.next != null){
                temp = temp.next; //move to next position
            }
            temp.next = newNode;
            newNode.next = null;
        }
        
        
    }//end addLast()
    
    //------------------ Check if Linked List is empty -------------------------
    public boolean isEmpty(){
        listNode node = head;
        if(node == null){
            return true;
        }else{
            return false;
        }
    }
    
    //------------------ Number of paths ---------------------------------------
    //the max number of paths in a network is equal to the number of dependicies
    //multiplied together
    public int numberOfPaths(){
        int count = 1;
        listNode node = head;
        Node mul;
        
        while(node != null){
           mul = node.data;
           count = count * (mul.dependiciesCpy.length-1);
           node = node.next;
        }
        return count;
    }
    
    //------------------ List Size ---------------------------------------------
    public int listSize(){
        listNode node = head;
        int count = 0;
        while(node != null){
            count++;
            node = node.next;
        }
        return count;
    }
    
    //---------------------- Sort  Network Paths -------------------------------
    public pathObject[] sortPaths(pathObject[] pathObjArr){
        //if obj1 > obj2 swap
        pathObject obj1;
        pathObject obj2;
        pathObject temp;
        for(int i = 0; i < pathObjArr.length; i++){
            for(int j = 0; j < pathObjArr.length; j++){
                obj1 = pathObjArr[j];
                obj2 = pathObjArr[i];
                if(obj1.getDuration() > obj2.getDuration()){
                    //swap j and i indexes
                    temp = pathObjArr[j];
                    pathObjArr[j] = pathObjArr[i];
                    pathObjArr[i] = temp;
                }
            }
        }
        return pathObjArr;
    }
    
    //------------------- Print Nework Paths -----------------------------------
	//uses for loop in main to print all paths
    public pathObject getPath(){
        List<String> pathStr = new ArrayList<String>();
        Node[] end = findLastNodes();
        Node check = new Node();
        
        String paths = "";
        int duration = 0;
        Node current = end[0];
            
        while(current.dependiciesEmpty() == false){
            //get node dependency
            if(current.dependicies[0].equals("}")){
                String[] reset = current.dependiciesCpy;
                current.setDependicies(reset);
            }

            listNode iterate = head;
            while(iterate != null){
                check = iterate.data;
                if(current.dependicies[0].equals(check.getName())){
                    current.swapDependicies();
                    break;
                }
                iterate = iterate.next;
            }//end while
            pathStr.add(0, current.getName());
            duration += current.getDuration();
            current = check;   
        }//end while
        duration += current.getDuration();
        pathStr.add(0, current.getName());
        
        paths = pathStr.toString();
        System.out.println(paths);
        
        pathObject pathObj = new pathObject();
        pathObj.setPath(paths);
        pathObj.setDuration(duration);
        return pathObj;
    }
    
    //---------------------- Remove Path Duplicates ----------------------------
    public pathObject[] removeDuplicates(pathObject[] pathObj){
        
        Set<String> set = new HashSet<String>();
        List<pathObject> pathList = new ArrayList<pathObject>();
        for(pathObject path: pathObj) if(set.add(path.getPath())) pathList.add(path);
        pathObject[] uniquePaths = pathList.toArray(new pathObject[pathList.size()]);
        
        return uniquePaths;
    }
    
    //--------------------- Reset Node Dependicies -----------------------------
    public void resetDependicies(){
        listNode node = head;
        Node current = new Node();
        
        while(node != null){
            current = node.data;
            String[] reset = current.dependiciesCpy2;
            current.setDependicies(reset);
            node = node.next;
        }
    }

    //--------------- Return the first nodes in the network --------------------
    public Node[] findFirstNodes(){
        Node[] startNodes = new Node[listSize()];
        Node current = new Node();
        int i = 0;
        
        listNode node = head;
        //iterate through the linked list
        while(node != null){ 
            //get current node dependicies
            current = node.data;
            //if there are no dependicies
            if(current.dependiciesEmpty() == true){
                startNodes[i] = current;
                i++;
            }
            //increment to next node in linked list
            node = node.next;
        }
        //return array of starting node objects
        return startNodes;
    }
    
    //------------ Return last nodes in the network ----------------------------
    //There should only be one ending node in the network, if array is 
    //larger than 1 than there is something wrong
    public Node[] findLastNodes(){
        Node[] endNodes = new Node[listSize()];
        List<String> tempLst = new ArrayList<String>();
        Node current = new Node();
        int i;
        
        for(int j = 0; j < listSize(); j++){
            endNodes[j] = null;
        }
        
        listNode node = head;
        //iterate through the linked list
        while(node != null){
            current = node.data;
            if(current.dependiciesEmpty() == true){
                //do nothing
            }else{
                int s = current.dependicies.length;
                for(i = 0; i < s; i++){
                    String dep = current.dependicies[i];
                    //populate array list with dependicies
                    tempLst.add(dep);
                }//end for
            }//end else
            node = node.next;
        }//end while
        
        Boolean isEnd = false;
        int j = 0;
        
        node = head;
        //re-iterate through linked list
        while(node != null){
            for(i = 0; i<tempLst.size(); i++){
               current = node.data;
               if(current.getName().equals(tempLst.get(i))){
                   isEnd = false;
                   break;
               }else{
                   isEnd = true;
               }
            }
            if(isEnd == true){
                //if there were no matches, add node to end nodes array
                endNodes[j] = current;
                j++;
            }
            node = node.next;
        }
        
        //return array of end nodes
        return endNodes;
    }//end findLastNodes
    
    //-------------- Prints out the linkedlist contents ------------------------
    public String printString() {
    	String result = "";
    	listNode current = head;
    	while(current != null) {
    		result += String.valueOf(current.data);
    		current = current.next;
    	}
    	return result;
    }
    
}//end linkedlist class