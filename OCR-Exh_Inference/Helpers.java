/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package pgm_1_b;

/**
 *
 * @author vignan_pc
 */
import java.io.*;
import java.util.ArrayList;

public class Helpers {
    // Helper function to read ocr probabilities
    public static Double[][] read_OCR (String filename) throws IOException{
        Double[][] OCR=new Double[1000][10];
        File file=new File(filename);
        if (!file.exists()) { 
            System.out.println("ocr.dat File not Found");
           
	}
        else{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String inp_line="";
            String[] line_split;
            while((inp_line=br.readLine())!=null){
                line_split=inp_line.split("\t");
                OCR[Integer.parseInt(line_split[0])][convertStringToInt(line_split[1])]=Math.log(Double.parseDouble(line_split[2]));
            }
            br.close();
        }
        
    return OCR;
}
    // helper funct to read Transition Probabilities
    public static Double[][] read_Trans (String filename) throws IOException{
        Double[][] Trans=new Double[10][10];
        File file=new File(filename);
        if (!file.exists()) { 
            System.out.println("trans.dat File not Found");
	}
        else{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String inp_line="";
            String[] line_split;
            while((inp_line=br.readLine())!=null){
                line_split=inp_line.split("\t");
                Trans[convertStringToInt(line_split[0])][convertStringToInt(line_split[1])]=Math.log(Double.parseDouble(line_split[2]));
            }
        }
    return Trans;
}
    // helper funct to read from images file example array structure : [["67","909","787"],["55","755"]]
    public static ArrayList<ArrayList<Integer>> read_images (String filename) throws IOException{
        ArrayList<ArrayList<Integer>> images=new ArrayList();
        File file=new File(filename);
        if (!file.exists()) { 
            System.out.println(filename+".dat File not Found");
	}
        else{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String inp_line="";
            
            while((inp_line=br.readLine())!=null){
                ArrayList<Integer> temp =new ArrayList<Integer>();
                for(String e:inp_line.split("\t")){
                    temp.add(Integer.parseInt(e));
                }
                images.add(temp);
                //temp.clear();
            }
            br.close();
        }
        
    return images;
}
    // helper funct to read from words file example array structure : [["s","a","e"],["e","h"]]
    public static ArrayList<ArrayList<Integer>> read_words (String filename) throws IOException{
        ArrayList<ArrayList<Integer>> words=new ArrayList();
        File file=new File(filename);
        if (!file.exists()) { 
            System.out.println(filename+".dat File not Found");
	}
        else{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String inp_line="";
           
            while((inp_line=br.readLine())!=null){
                 ArrayList<Integer> temp=new ArrayList<Integer>();
                for(char e:inp_line.toCharArray()){
                    temp.add(convertCharToInt(e));
                }
                words.add(temp);
                //temp.clear();
            }
            br.close();
        }
        
    return words;
}
    public static int convertStringToInt(String string) {
		if (string.equals("d")) {
			return 0;
		}
		if (string.equals("o")) {
			return 1;
		}
		if (string.equals("i")) {
			return 2;
		}
		if (string.equals("r")) {
			return 3;
		}
		if (string.equals("a")) {
			return 4;
		}
		if (string.equals("h")) {
			return 5;
		}
		if (string.equals("t")) {
			return 6;
		}
		if (string.equals("n")) {
			return 7;
		}
		if (string.equals("s")) {
			return 8;
		}
		if (string.equals("e")) {
			return 9;
		}
		return -1; //default
	}
    	public static String convertIntToString(int imageval) {
		switch (imageval) {
		case 0:
			return "d";
		case 1:
			return "o";
		case 2:
			return "i";
		case 3:
			return "r";
		case 4:
			return "a";
		case 5:
			return "h";
		case 6:
			return "t";
		case 7:
			return "n";
		case 8:
			return "s";
		case 9:
			return "e";
		default:		
			return "";
		}
	}
        public static int convertCharToInt(char c) {
		if (c==('d')) {
			return 0;
		}
		if (c=='o') {
			return 1;
		}
		if (c=='i') {
			return 2;
		}
		if (c==('r')) {
			return 3;
		}
		if (c==('a')) {
			return 4;
		}
		if (c==('h')) {
			return 5;
		}
		if (c==('t')) {
			return 6;
		}
		if (c==('n')) {
			return 7;
		}
		if (c==('s')) {
			return 8;
		}
		if (c==('e')) {
			return 9;
		}
		return -1; //default
	}
}
