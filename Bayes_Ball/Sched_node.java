/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package PGM_1_2;

/**
 *
 * @author vignan_pc
 */
public class Sched_node {
    int name;
    boolean from; // 0->from children 1->from parent
    
    public Sched_node(int a){
        name=a;
        from=false;
    }
    public Sched_node(int a,boolean from1){
        name=a;
        from=from1;
    }
}
