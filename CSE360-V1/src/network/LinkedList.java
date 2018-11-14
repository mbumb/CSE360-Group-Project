package network;
import java.util.*;
import java.lang.Math; 

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
           count = count * (mul.dependiciesCpy2.length);
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
                if(obj1.getDuration() < obj2.getDuration()){
                    //swap j and i indexes
                    temp = pathObjArr[j];
                    pathObjArr[j] = pathObjArr[i];
                    pathObjArr[i] = temp;
                }
            }
        }
        return pathObjArr;
    }
    
    //---------------------- Find Critical Path(s) ----------------------------
    public pathObject[] criticalPath(pathObject[] pathObjArr) {
     
        List<pathObject> criticalPath = new ArrayList<pathObject>();
        criticalPath.add(pathObjArr[0]);
        
        pathObject obj1;
        pathObject obj2;
        
        //criticalPath[0] = pathObjArr[0];
        obj2 = criticalPath.get(0);
        
            for (int i = 1; i < pathObjArr.length; i++) {
                obj1 = pathObjArr[i];
                if(obj1.getDuration() == obj2.getDuration()) {
                    criticalPath.add(obj1);
                }
            }
        
      return criticalPath.toArray(new pathObject[criticalPath.size()]);
        
    }
    
    //------------------- Print Nework Paths -----------------------------------
	//uses for loop in main to print all paths
    public pathObject getPathObj(){
        List<String> pathStr = new ArrayList<String>();
        Node[] end = findLastNodes();
        Node check = new Node();
        boolean error; //if 0 there is an error
        
        String paths = "";
        int duration = 0;
        Node current = end[0];
        
        while(current.dependiciesEmpty() == false){
            //get node dependency
            error = true;
            if(current.dependicies[0].equals("}")){
                current.swapDependicies();
            }

            listNode iterate = head;
            while(iterate != null){
                check = iterate.data;
                if(current.dependicies[0].equals(check.getName())){
                    current.swapDependicies();
                    error = false; //if current is found
                    break;
                }
                iterate = iterate.next;
            }//end while
            if(error == true){
                System.out.print("Unconnected node encountered");
                return null;
            }
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
        
        if (cycleCheck(pathObj) == true) {
            System.out.println("cycle");
        }
        
        return pathObj;
    }
    
    //---------------------- Check for cycles in network ----------------------
    public boolean cycleCheck(pathObject pathObj) {
        boolean cycle = false;
        String obj1 = pathObj.getPath();
        
        String[] objArr = obj1.split(",");
        //List<String> obj = Arrays.asList(objArr);
        
        for (int i = 0; i < objArr.length; i++) {
            for (int j = i + 1; j < objArr.length; j++) {
                if(j != i && (objArr[i]).equals(objArr[j])) {
                    cycle = true;
                }
            }
        }
        return cycle;
    }
    
    //---------------------- Remove Path Duplicates ----------------------------
    public pathObject[] removeDuplicates(pathObject[] pathObj){
        List<pathObject> pathList = new ArrayList<pathObject>();
        Set<String> pathSet = new HashSet<String>();
        for(pathObject path : pathObj){
            if(pathSet.add(path.getPath())){
                pathList.add(path);
            }
        }
        
        return pathList.toArray(new pathObject[pathList.size()]);
    }
    
    //-------------------- Sort nodes in ABC order -----------------------------
    public void sortNodesABC(){
        listNode node = head;
        Node current = new Node();
        Node nextNode = new Node();
        Node temp = new Node();
        while(node != null){
            current = node.data;
            listNode iterate = head;
            while(iterate != null){
                nextNode = iterate.data;
                //swap
                if(current.getName().compareTo(nextNode.getName()) < 0){
                    temp = current;
                    current = nextNode;
                    nextNode = temp;
                    node.data = current;
                    iterate.data = nextNode;
                }//end if
                iterate = iterate.next;
            }//end while
            node = node.next;
        }//end while
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
    
    //---------------- Change path duration ------------------------------------
    public boolean changeDuration(String name, int newDuration){
        listNode node = head;
        Node find = new Node();
        
        while(node != null){
            find = node.data;
            if(find.getName().equals(name)){
                find.setDuration(newDuration); //change node duration
                return true;
            }else{
                node = node.next; //else move to next position
            }
        }//end while
        //if we reach the end of the loop without changing the duration
        return false;
        
        /*
        // ---------- To be added into mainView under change duration btn ----------
        if(list.isEmpty() == true){
            errorLbl.setVisible(true);
            errorLbl.setText("There are no nodes in the list");
        }else{
            String name = "A"; //varName.getText();
        
        //try-catch to check if input is an int
            try{
                //input is an integer - convert to int
                int newDuration = Integer.parseInt(CHANGEVARNAME.getText());
                //changes node duration
                CHANGEVARNAME.setVisible(false); //error label to check if int
                boolean tf = list.changeDuration(name,newDuration);
                //----- Check if node duration was changed -------
                if(tf == true){
                    errorLbl.setVisible(false);
                    displayNodesField.setText(list.printString()); //show updated node
                }else{
                    errorLbl.setVisible(true);
                    errorLbl.setText("That node isn't in the list!");
                }//end else
            //----- Input is not integer ------
            }catch(NumberFormatException e){
                //input is not an integer
                CHANGEVARNAME.setVisible(true); //error label to check if int
            }
        }//end else
        */
    }
    
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
    
    //------------ Prints out linkedlist contents plus a newline character, for the report ---------
    public String printActivitiesStringForReport() {
        String universalNewLineChar = System.getProperty("line.separator");
    	String result = "";
    	listNode current = head;
    	while(current != null) {
    		result += String.valueOf(current.data);
    		current = current.next;
                result += universalNewLineChar;
    	}
    	return result;
    }
}//end linkedlist class