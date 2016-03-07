/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package pgm_1_b;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author vignan_pc
 */
// Data Structure to store all inputs in efficient manner 
public class Stor {
    Double[][] ocr_Pots;
    Double[][] trans_Pots;
    ArrayList<ArrayList<Integer>> images_arr;
    ArrayList<ArrayList<Integer>> words_arr;
    
    public Stor(String ocr,String trans,String images,String words) throws IOException{
        ocr_Pots=Helpers.read_OCR(ocr);
        trans_Pots=Helpers.read_Trans(trans);
        images_arr=Helpers.read_images(images);
        words_arr=Helpers.read_words(words);
    }
}
