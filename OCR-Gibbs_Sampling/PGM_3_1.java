/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * log likelihood cheyra
 */
//package pgm_3_1;
import java.io.*;
import java.util.*;
/**
 *
 * @author vignansai
 */
public class PGM_3_1 {
    public static Stor storage;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
       storage=new Stor("OCRdataset-2/potentials/ocr.dat","OCRdataset-2/potentials/trans.dat","OCRdataset-2/data/data-loopsWS.dat","OCRdataset-2/data/truth-loopsWS.dat");
      for(int k=10100;k<20000;k+=500){
        
    
       ArrayList<ArrayList<ArrayList<Integer>>> ans_words=gibbs_sampling(k,0.05);
      int ch_acc=0;
      int ch_total=0;
      int wd_acc=0;
      int wd_total=2*ans_words.size();
      for(int i=0;i<ans_words.size();i++){
          boolean word=true;
          ch_total+=ans_words.get(i).get(0).size()+ans_words.get(i).get(1).size();
          for(int j=0;j<ans_words.get(i).get(0).size();j++){
              if(storage.words_arr.get(i).get(0).get(j)==ans_words.get(i).get(0).get(j)){
                  ch_acc++;
              }
              else{
                  word=false;
              }
          }
          if(word){wd_acc++;}
          word=true;
          for(int j=0;j<ans_words.get(i).get(1).size();j++){
              if(storage.words_arr.get(i).get(1).get(j)==ans_words.get(i).get(1).get(j)){
                  ch_acc++;
              }
              else{
                  word=false;
              }
          }
          if(word){wd_acc++;}
      }
        System.out.println(k+"  "+(double)wd_acc/wd_total);
        //System.out.println("word_acc:"+wd_acc+"/"+wd_total);
      }
    }
   
    public static Double log_score1(ArrayList<Integer> img1,ArrayList<Integer> img2,ArrayList<Integer> char1,ArrayList<Integer> char2){
        Double score=0.0;
        for(int i=0;i<img1.size();i++){
            score+=storage.ocr_Pots[img1.get(i)][char1.get(i)];
        }
        for(int i=0;i<img2.size();i++){
            score+=storage.ocr_Pots[img2.get(i)][char2.get(i)];
        }
        for(int i=0;i<img1.size()-1;i++){
            score+=storage.trans_Pots[char1.get(i)][char1.get(i+1)];
        }
        for(int i=0;i<img2.size()-1;i++){
            score+=storage.trans_Pots[char2.get(i)][char2.get(i+1)];
        }
        for(int i=0;i<img1.size();i++){
            for(int j=i+1;j<img1.size();j++){
                if(img1.get(i) ==img1.get(j) && char1.get(i)==char1.get(j)){
                    score+=Math.log(5.0);
                }
            }
        }
        for(int i=0;i<img2.size();i++){
            for(int j=i+1;j<img2.size();j++){
                if(img2.get(i) ==img2.get(j) && char2.get(i)==char2.get(j)){
                    score+=Math.log(5.0);
                }
            }
        }
        
        for(int i=0;i<img1.size();i++){
            for(int j=0;j<img2.size();j++){
                if(img1.get(i)==img2.get(j) && char1.get(i)==char2.get(j)){
                    score+=Math.log(5.0);
                }
            }
        }
        return score;
    }
    
    public static ArrayList<ArrayList<ArrayList<Integer>>> gibbs_sampling(int iter_num,Double burn_in_per){
        Double burn=(burn_in_per*iter_num);
        Integer burn_in=burn.intValue();
        Random randomGenerator = new Random();
        ArrayList<ArrayList<ArrayList<Integer>>> answers=new ArrayList();
        for(ArrayList<ArrayList<Integer>> imgpair_arr:storage.images_arr){
            ArrayList<ArrayList<Integer>> answer = new ArrayList(2);
            ArrayList<Integer> first_ans=new ArrayList<Integer>(imgpair_arr.get(0).size());
           ArrayList<Integer> second_ans=new ArrayList<Integer>(imgpair_arr.get(1).size());
            ArrayList<ArrayList<ArrayList<Integer>>> samples = new ArrayList();
            
           ArrayList<ArrayList<Integer>> sample = new ArrayList(2);
           ArrayList<Integer> first_word=new ArrayList<Integer>(imgpair_arr.get(0).size());
           ArrayList<Integer> second_word=new ArrayList<Integer>(imgpair_arr.get(1).size());
           for(int i=0;i<imgpair_arr.get(0).size();i++){
               first_word.add(randomGenerator.nextInt(10));
           }
           for(int i=0;i<imgpair_arr.get(1).size();i++){
               second_word.add(randomGenerator.nextInt(10));
           }
           sample.add(first_word);sample.add(second_word);
           samples.add((ArrayList<ArrayList<Integer>>)sample.clone());//adding randomly generated initial sample
           
           //to create n samples
          for(int j=0;j<(iter_num+burn_in);j++){
            for(int i=0;i<first_word.size()+second_word.size();i++){
              ArrayList<ArrayList<Integer>> nxt_sample=sampler(sample,imgpair_arr,i);
              samples.add(nxt_sample);
              sample.clear();
              for(ArrayList<Integer> e:nxt_sample){
                  ArrayList<Integer> n=(ArrayList<Integer>) e.clone();
                  sample.add(n);
              }
           }
        }
          
          for(int i=0;i<first_word.size()+second_word.size();i++){
            int[] cnt=new int[10];
            if(i<first_word.size()){
                for(int j=burn_in;j<samples.size();j++){
                    cnt[samples.get(j).get(0).get(i)]+=1;
                }
            }
            else{
                for(int j=burn_in;j<samples.size();j++){
                    cnt[samples.get(j).get(1).get(i-first_word.size())]+=1;
                }
            }
            int max_val=0;
            int max_indx=0;
            for(int e=0;e<10;e++){
                if(cnt[e]>max_val){
                    max_val=cnt[e];
                    max_indx=e;
                }
            }
            if(i<first_word.size()){
                first_ans.add(max_indx);
            }
            else{
                second_ans.add(max_indx);
            }
            
        }
           
           answer.add(first_ans);answer.add(second_ans);
           answers.add(answer);
        }
        return answers;
    }
      ////Model-4  OCR+Trans+Skip+pair-skip Factors
    
    public static ArrayList<ArrayList<Integer>> sampler( ArrayList<ArrayList<Integer>> curr_sample,ArrayList<ArrayList<Integer>> imgpair_arr,int indx){
       
        Double[] score_arr=log_score_arr(imgpair_arr.get(0),imgpair_arr.get(1),curr_sample.get(0),curr_sample.get(1),indx);
        //normalize
        Double total=0.0;
        for(int i=0;i<score_arr.length;i++){
            score_arr[i]=Math.pow(Math.E, score_arr[i]);
        }
        for(Double e:score_arr){
            total+=e;
            //System.out.println(Math.pow(Math.E, e));
            
        }
        for(int i=0;i<score_arr.length;i++){
            score_arr[i]/=total;
        }
        Double temp=score_arr[0];
        for(int i=1;i<score_arr.length;i++){
            score_arr[i]+=temp;
            temp=score_arr[i];
        }
        
        Random generator = new Random();
        double number = generator.nextDouble();
        int char_num=0;
        for(int i=score_arr.length-1;i>0;i--){
            if(number<score_arr[i] && number>=score_arr[i-1]){
                char_num=i;
            }
        }
        //have to iterate over each seperately as ArrayList<int> is mutable object and arraylist = goes by ref rather than value
        ArrayList<ArrayList<Integer>> new_sample=new ArrayList<>();
        for(ArrayList<Integer> e:curr_sample){
            ArrayList<Integer> n=(ArrayList<Integer>) e.clone();
            new_sample.add(n);
        }
        if(indx<new_sample.get(0).size()){
            new_sample.get(0).set(indx,char_num);
        }
        else{
            new_sample.get(1).set(indx-new_sample.get(0).size(),char_num);
        }
        return new_sample;
       
//        int max_indx=0;
//        Double max_val=score_arr[0];
//        for(int i=1;i<score_arr.length;i++){ 
//            if(score_arr[i]>max_val){
//                max_indx=i;
//                max_val=score_arr[i];
//            }
//        }
//        if(max_indx<curr_sample.get(1).size()){
//            curr_sample.get(1).set(max_indx,)
//        };
    }
    public static Double[] log_score_arr(ArrayList<Integer> img1,ArrayList<Integer> img2,ArrayList<Integer> char1,ArrayList<Integer> char2,int indx){
        Double[] score_arr=new Double[10];
        for(int i=0;i<10;i++){
            Double score=0.0;
            
            if(indx<img1.size()){
                char1.set(indx, i);
                score+=storage.ocr_Pots[img1.get(indx)][char1.get(indx)];
                //trans_score
                if(indx==0){
                    score+=storage.trans_Pots[char1.get(indx)][char1.get(indx+1)];
                }
                else if(indx==img1.size()-1){
                    score+=storage.trans_Pots[char1.get(indx-1)][char1.get(indx)];
                }
                else{
                    score+=storage.trans_Pots[char1.get(indx-1)][char1.get(indx)]+storage.trans_Pots[char1.get(indx)][char1.get(indx+1)];
                }
                //skip
                for(int j=0;j<img1.size();j++){
                    if(j!=indx){
                        if(img1.get(indx) ==img1.get(j) && char1.get(indx)==char1.get(j)){
                            score+=Math.log(5.0);
                         }
                    }
                }
                //pair-skip
                for(int j=0;j<img2.size();j++){
                    if(img1.get(indx) ==img2.get(j) && char1.get(indx)==char2.get(j)){
                        score+=Math.log(5.0);
                    }
                }
                
            }
            else{
                int indx2=indx-img1.size();
                char2.set(indx2, i);
                score+=storage.ocr_Pots[img2.get(indx2)][char2.get(indx2)];
                //trans_score
                if(indx2==0){
                    score+=storage.trans_Pots[char2.get(indx2)][char2.get(indx2+1)];
                }
                else if(indx2==img2.size()-1){
                    score+=storage.trans_Pots[char2.get(indx2-1)][char2.get(indx2)];
                }
                else{
                    score+=storage.trans_Pots[char2.get(indx2-1)][char2.get(indx2)]+storage.trans_Pots[char2.get(indx2)][char2.get(indx2+1)];
                }
                //skip
                for(int j=0;j<img2.size();j++){
                    if(j!=indx2){
                        if(img2.get(indx2) ==img2.get(j) && char2.get(indx2)==char2.get(j)){
                            score+=Math.log(5.0);
                         }
                    }
                }
                //pair-skip
                for(int j=0;j<img1.size();j++){
                    if(img2.get(indx2) ==img1.get(j) && char2.get(indx2)==char1.get(j)){
                        score+=Math.log(5.0);
                    }
                }
            }
            score_arr[i]=score;
        }
        return score_arr;
    }
}
 