/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package pgm_1_b;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author vignan_pc
 */
public class PGM_1_B {

    /**
     * @param args the command line arguments
     */
    public static Stor storage;
    
    public static void main(String[] args) throws IOException {

        storage=new Stor("OCRdataset/potentials/ocr.dat","OCRdataset/potentials/trans.dat","OCRdataset/data/small/images.dat","OCRdataset/data/small/words.dat");
       // Input type of model 
        //0--->only OCR model |  1 --> OCR+Trans model | 2 --> OCR+Trans+Skip model
        
       BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.print("type:");
       int type = Integer.parseInt(br.readLine());
        //System.out.println("Score:"+prob(img,chars,type))
      final_output(storage.images_arr,storage.words_arr,type,"results/small_0.txt");
        
    }
    
    //////////// Helper Conversion functions //////////
    public static int[] str_to_int_arr(String[] s){
        int[] i=new int[s.length];
        for(int j=0;j<s.length;j++){
            i[j]=Integer.parseInt(s[j]);
        }
        return i;
    }
    public static int[] chars_to_int_arr(String[] s){
        int[] i=new int[s.length];
        for(int j=0;j<s.length;j++){
            i[j]=Helpers.convertStringToInt(s[j]);
        }
        return i;
    }
    //////////////////////////////
    ///////////////////////// Part1 :Facilitating  different type model log Unnormalized Scores  /////////////////
    //Model-1 only OCR Factors
    public static Double log_ocr_score(int[] img,int[] chars){
        Double score=0.0;
        for(int i=0;i<img.length;i++){
            score+=storage.ocr_Pots[img[i]][chars[i]];
        }
        return score;
    }
    //Model-2  OCR+Trans Factors
    public static Double log_trans_score(int[] img,int[] chars){
        Double score=0.0;
        for(int i=0;i<img.length;i++){
            score+=storage.ocr_Pots[img[i]][chars[i]];
        }
        for(int i=0;i<img.length-1;i++){
            score+=storage.trans_Pots[chars[i]][chars[i+1]];
        }
        return score;
    }
    
    ////Model-3  OCR+Trans+Skip Factors
    public static Double log_skip_score(int[] img,int[] chars){
        Double score=0.0;
        for(int i=0;i<img.length;i++){
            score+=storage.ocr_Pots[img[i]][chars[i]];
        }
        for(int i=0;i<img.length-1;i++){
            score+=storage.trans_Pots[chars[i]][chars[i+1]];
        }
        for(int i=0;i<img.length;i++){
            for(int j=i+1;j<img.length;j++){
                if(img[i]==img[j] && chars[i]==chars[j]){
                    score+=Math.log(5.0);
                }
            }
        }
        return score;
    }
    public static Double log_score(int[] img,int[] chars,int type){
        if(type==0){
            return log_ocr_score(img,chars);
        }
        else if(type==1){
            return log_trans_score(img,chars);
        }
        else {
            return log_skip_score(img,chars);
        }
    }
    /////////// Part2 : Exhaustive Inference  //////////////////
    public static Double prob(int[] img,int[] chars,int type){
        Double prob_score=0.0;
        int arr_size=(int)Math.pow(10,img.length);
        //System.out.print(arr_size);
        Double[] score_arr=new Double[arr_size];
        int k=0;
        for(int i=0;i<arr_size;i++){
            int[] temp_chars=new int[img.length];
            int temp=img.length-1;
             k=i;
            while((k/10)>0){
                temp_chars[temp]=k%10;
                temp--;
                k=k/10;
            }
            temp_chars[temp]=k%10;
            
            score_arr[i]=Math.pow(Math.E, log_score(img,temp_chars,type));
            //System.out.println(score_arr[i]);
        }
        Double z_all=0.00000;
        for(int j=0;j<score_arr.length;j++){
            z_all+=score_arr[j];
        }
        System.out.println(z_all);
        prob_score=Math.pow(Math.E, log_score(img,chars,type))/(z_all);
        return prob_score;
    }
    // Function to return array of prob of every propable assignment  
    public static Double[] score_array(Integer[] img, int type){
//        Double prob_score=0.0;
        int arr_size=(int)Math.pow(10,img.length); 
        //System.out.print(arr_size);
        Double[] score_arr=new Double[arr_size];
        int k=0;
        for(int i=0;i<arr_size;i++){
            int[] temp_chars=new int[img.length];
            int temp=img.length-1;
             k=i;
             //getting the all possible character assignment using the index
            while((k/10)>0){
                temp_chars[temp]=k%10;
                temp--;
                k=k/10;
            }
            
            temp_chars[temp]=k%10;// taking care of temp=0
            int[] img_int = new int[img.length];
            for (int j = 0; j < img.length; j++) {
                img_int[j] = img[j];
            }
            //storing probabilities 
            score_arr[i]=Math.pow(Math.E,log_score(img_int,temp_chars,type));
            //System.out.println(score_arr[i]);
        }
        return score_arr;
    }
    // Helpers to convert from one type to another
    public static String convertToStringi(int[] arr){
        String ans="";
        for(int e:arr){
            ans=ans+Helpers.convertIntToString(e);
        }
        return ans;
    }
     public static String convertToStringI(ArrayList<Integer> arr){
        String ans="";
        for(int e:arr){
            ans=ans+Helpers.convertIntToString(e);
        }
        return ans;
    }
    // Everything goes here as name suggests it is the 'Final'
    public static void final_output(ArrayList<ArrayList<Integer>> images,ArrayList<ArrayList<Integer>> words,int type,String filename) throws IOException{
        File file=new File(filename);
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        
        int total_chars=0;
        int char_accuracy=0;
        int word_accuracy=0;
        int temp_index=0;
        Double log_like=0.0;
        for(int j=0;j<images.size();j++){
            
            int max_index=-1;
            Double max_num=Double.NEGATIVE_INFINITY;
            Double[] score_arr=score_array(images.get(j).toArray(new Integer[images.get(j).size()]),type);
            //Max prob assignment's index finding
            for(int i=0;i<score_arr.length;i++){
                if(score_arr[i]>max_num){
                    max_num=score_arr[i];
                    max_index=i;
                }
            }
            int temp=images.get(j).size()-1;
            int[] chars_arr =new int[images.get(j).size()]; // The array of chars of most probable assignmnent
            // filling up chars_arr
            temp_index=max_index;
            while(temp>0){
                chars_arr[temp]=temp_index%10;
                if(chars_arr[temp]==words.get(j).get(temp)){
                    char_accuracy++;
                }
                temp_index=temp_index/10;
                temp--;
            }
            
            /// taking care of temp=0
            chars_arr[temp]=temp_index%10;
            if(chars_arr[temp]==words.get(j).get(temp)){
                    char_accuracy++;
            }
            total_chars+=chars_arr.length;
            // writing to file the original word and most probable assignment
           bw.write(convertToStringi(chars_arr)+"\n");
           // if words are equal
           if(convertToStringI(words.get(j)).equals(convertToStringi(chars_arr))){
               word_accuracy++;
           }
           //avg-likelihood
           Double z_all=0.00000; // z the denominator
            for(int k=0;k<score_arr.length;k++){
                z_all+=score_arr[k];
            }
            // converting Integer to int
            int[] img_int = new int[images.get(j).size()];
            int[] char_int =new int[images.get(j).size()];
            for (int m = 0; m < images.get(j).size(); m++) {
                img_int[m] = images.get(j).get(m);
                char_int[m]=words.get(j).get(m);
            }
           log_like+= Math.log(Math.pow(Math.E, log_score(img_int,char_int,type))/(z_all));
            
        }
        bw.write("Total chars:"+total_chars+"\n");
        bw.write("Matched # of Chars:"+char_accuracy+"\n");
        bw.write("char-acc:"+(double)(char_accuracy/total_chars)+"\n");
        bw.write("Total words:"+words.size()+"\n");
        bw.write("Matched # of Words:"+word_accuracy+"\n");
        bw.write("word-acc:"+(double)(word_accuracy/words.size())+"\n");
        
        bw.write("Avg Dataset log-likelihood:"+(log_like/words.size()));
        
        bw.close();
    }
}
