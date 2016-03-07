/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package PGM_1_2;

/**
 *
 * @author vignan_pc
 */
import java.io.*;
import java.util.*;
class Node{
    int name;
    boolean[] mark_arr;
    
    public Node(int a){
        name=a;
        mark_arr = new boolean[3];//0->visited | 1->marked at TOP | 2-> marked at below -- all are intialized to false
    }
}
public class bayes_graph {
    int b_size;
    Node[] graph;
    List<Integer>[] adj;
    List<Integer>[] child_adj;
    
    @SuppressWarnings("unchecked")
    public bayes_graph(String file_name) throws IOException {
        File file=new File(file_name);
        if (!file.exists()) { 
            System.out.println("File not Found");
	}
        else{
           FileReader fr = new FileReader(file);
           BufferedReader br = new BufferedReader(fr);
           
           /// Intializing graph
           
            b_size = Integer.parseInt(br.readLine()); // reading n
            graph =new Node[b_size+1];
            adj = (List<Integer>[])new List[b_size+1];
            child_adj=(List<Integer>[])new List[b_size+1];
            for (int i = 1; i < b_size+1; i++){ 
                adj[i] = new ArrayList<Integer>();
                child_adj[i] = new ArrayList<Integer>();
                graph[i]=new Node(i);
             }
            
            /// end intializing
           String line;
           while(!"".equals(line = br.readLine())){
               if(line==null){
                   break;
               }
               else{
                   int parent=Integer.parseInt(line.split(" ")[0]);
                   String  child_string=line.split(" ")[1].substring(1,line.split(" ")[1].length()-1);// removing '[',']'
                   
                   if(child_string.length()>0){
                    for(String ele:child_string.split(",")){ // adding edges
                        adj[parent].add(Integer.parseInt(ele));
                        child_adj[Integer.parseInt(ele)].add(parent);
                    }   
                   }
               } 
           }
           br.close();
        }
    }
    public void add_edge(int a,int b){
        adj[a].add(b);
        child_adj[b].add(a);
    }
    
    public void print_tofile (String file_name) throws IOException{
        File file=new File(file_name);
        if (!file.exists()) { 
            file.createNewFile();
	}
        
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
                        Integer size= b_size;
                        bw.write(size.toString());
                        for(Integer i=1;i<b_size+1;i++){
                            String data="\n"+i.toString()+" [";
                            for(Integer ele:adj[i]){
                                data=data+ele+",";
                            }
                            if(adj[i].size()>0){ // taking care of no entries at all
                                data=data.substring(0,data.length()-1);
                            }
                           data=data +"]";
                            bw.write(data);
                        }
//                        bw.write("-------------------------\n");
//                        for(Integer i=1;i<b_size+1;i++){
//                            String data=i.toString()+" [";
//                            for(Node ele:child_adj[i]){
//                                data=data+ele.name+",";
//                            }
//                            if(adj[i].size()>0){ // taking care of no entries at all
//                                data=data.substring(0,data.length()-1);
//                            }
//                           data=data +"]\n";
//                            bw.write(data);
//                        }
			bw.close();
        }
    
//    public void read_fromfile(String file_name) throws IOException{
//
//    }
    
}
