/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package PGM_1_2;

import java.io.*;
import java.util.*;

/**
 *
 * @author vignan_pc
 */

public class PGM_1_2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
         
        
        File file=new File("query.txt");
        if (!file.exists()) { 
            System.out.println("File not Found");
	}
        else{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            File file1=new File("output.txt");
            if (!file.exists()) { 
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file1.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            int query_size=Integer.parseInt(br.readLine());
            for(int i=0;i<query_size;i++){
                bayes_graph bg=new bayes_graph("input.txt"); // taking bayseian network into memory
                  bw.write(run_Algo(br.readLine(),bg)+"\n");
            }
            br.close();
            bw.close();
        }
        
//        bg.print_tofile("output.txt");
    }
    
    public static String run_Algo(String line,bayes_graph bg){
        //adding query nodes to schedule array
        Queue<Sched_node> sched = new LinkedList<Sched_node>();
        String query_str=line.split(" ")[0].substring(1,line.split(" ")[0].length()-1); // removing []
        for(String ele:query_str.split(",")){
            sched.add(new Sched_node(Integer.parseInt(ele)));
        }
        //Creating a Hashmap for query nodes
        String obs_str=line.split(" ")[1].substring(1,line.split(" ")[1].length()-1); // removing []
        HashSet<Integer> observed = new HashSet<Integer>();
        if(obs_str.length()>0){
            for(String ele:obs_str.split(",")){
                observed.add(Integer.parseInt(ele));
            }   
        }
        //while loop to start algo
        while(sched.size()>0){
            Sched_node j=sched.poll();
            bg.graph[j.name].mark_arr[0]=true;// marking j as visited
            if(!observed.contains(j.name) && !j.from){
                if(!bg.graph[j.name].mark_arr[1]){ // if top not marked
                    bg.graph[j.name].mark_arr[1]=true; // marking top
                    for(int par:bg.child_adj[j.name]){ // adding each parent to sched
                        sched.add(new Sched_node(par)); // as visiting from child from will be false
                    } 
                }
                if(!bg.graph[j.name].mark_arr[2]){//if bottom not marked and assuming no deterministic nodes
                    bg.graph[j.name].mark_arr[2]=true;// marking bottom
                    for(int child:bg.adj[j.name]){
                        sched.add(new Sched_node(child,true)); // the second argument is true i.e from parent
                    }
                }
            }
            if(j.from){ // if visit is from parent
                if(observed.contains(j.name) &&!bg.graph[j.name].mark_arr[1]){ // if it is in observed nodes and top is not marked
                    bg.graph[j.name].mark_arr[1]=true; // marking top
                    for(int par:bg.child_adj[j.name]){ // adding each parent to sched
                        sched.add(new Sched_node(par)); // as visiting from child from will be false
                    } 
                }
                if(!observed.contains(j.name) &&!bg.graph[j.name].mark_arr[2]){// if it is not in observed nodes and bottom is not marked
                    bg.graph[j.name].mark_arr[2]=true;// marking bottom
                    for(int child:bg.adj[j.name]){
                        sched.add(new Sched_node(child,true)); // the second argument is true i.e from parent
                    }
                }
            }
        }
        
        /// writing to output file in given format
        String query,obs,dsep,req_prob,req_obs;
        query="query:"+line.split(" ")[0];
        obs="obs:"+line.split(" ")[1];
        dsep="dsep:[";
        req_prob="req-prob:[";
        req_obs="req-obs:[";
        for(int i=1;i<bg.b_size+1;i++){//not from zero zero is kept empty to follow nomenclature given in assignment
            Node ele=bg.graph[i];
            if(!ele.mark_arr[2]){ // d-sep or irelevant nodes if not marked on bottom
                dsep=dsep+ele.name+",";
            }
            if(ele.mark_arr[1]){ // req prob nodes if marked on top
                req_prob=req_prob+ele.name+",";
            }
            if(observed.contains(ele.name) && ele.mark_arr[0]){
                req_obs=req_obs+ele.name+",";
            }
            
        }
        dsep=(dsep.contains(","))?dsep.substring(0,dsep.length()-1)+"] ":dsep+"] ";
        req_prob=(req_prob.contains(","))?req_prob.substring(0,req_prob.length()-1)+"] ":req_prob+"] ";
        req_obs=(req_obs.contains(","))?req_obs.substring(0,req_obs.length()-1)+"]":req_obs+"]";
        
        String final_result=query+" "+obs+" "+dsep+req_prob+req_obs;
        return final_result;
    }
}
