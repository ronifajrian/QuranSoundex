package quransoundex;

/* 	Author: Angelo Chung                
*	Instructor: Bryan Green                                  
* 	Purpose: 	To make a soundex algorithm. Soundex is a phonetic index and not a strictly alphabetical one. 
* 			 The key feature is that it codes names, surnames (last names) primarily, 
*			 based on the way a name sounds rather than on how it is spelled.
*	@author Angelo Chung
*	@version 2.0 
*/
import java.util.Scanner;
import java.util.regex.Pattern;
public class SoundEx
{
	// Main Method
    public static void main(String[] args){
        run();
	Scanner input = new Scanner(System.in);
        System.out.println("Program Finished! yay!");
    }   
    
    /*
     * Find out if two last names sounds the same
     */
    
    private static void run(){
    	String n1 = getName("Nama");
        String n1Normal;
        
        n1Normal = n1
                .replaceAll("iyy", "i")
                .replaceAll("sh", "s") //referensi Tabel 3 paper IPB dan 
                .replaceAll("ts", "s")//pemadanan aksara latin arab-indo kemenag
                .replaceAll("sy", "s")
                .replaceAll("dz", "z")
                .replaceAll("zh", "z")              
                .replaceAll("dh", "d")
                .replaceAll("th", "t")
                .replaceAll("q", "k")
                .replaceAll("aw", "au")
                .replaceAll("ay", "i")
                
                .replaceAll("v","f")
                .replaceAll("p","f")
                .replaceAll("j","z")
                
                .replaceAll("ng", "n")//ikhfa
                .replaceAll("nb", "mb")//iqlab
                .replaceAll("ny", "y")//idgham
                .replaceAll("nw", "w")//idgham
                .replaceAll("nm", "m")//idgham
                .replaceAll("nn", "n")//idgham
                .replaceAll("nl", "l")//idgham
                .replaceAll("nr", "r")//idgham
                .replaceAll("'", "")
                .replaceAll("-", "")
                ;
        //Cek hasil Normalisasi//
        System.out.println("Normalisasi : "+n1Normal);
            
        String s1 = doSoundexIND(n1Normal);
            System.out.println("Kode Fonetis : "+s1+"\n");
            
        String n2 = getName("Nama lain");
        String n2Normal;
        
        n2Normal = n2
                .replaceAll("iyy", "i")
                .replaceAll("sh", "s") //referensi Tabel 3 paper IPB dan 
                .replaceAll("ts", "s")//pemadanan aksara latin arab-indo kemenag
                .replaceAll("sy", "s")
                .replaceAll("dz", "z")
                .replaceAll("zh", "z")              
                .replaceAll("dh", "d")
                .replaceAll("th", "t")
                .replaceAll("q", "k")
                .replaceAll("'", "")
                .replaceAll("aw", "au")
                .replaceAll("ay", "i")
                .replaceAll("-", "")
                .replaceAll("ng", "n")
                .replaceAll("v","f")
                .replaceAll("p","f")
                .replaceAll("j","z")
                ;
                
        //cek normalisasi// 
        System.out.println("Normalisasi "+n2Normal);
       
        String s2 = doSoundexIND(n2Normal);
        System.out.println("Kode Fonetis : "+s2+"\n");
        
        compareTheNames(n1,s1,n2,s2); 		// bandingkan 
    }
    
    public static String normalize(String text){
        return text
               
                .replaceAll("\\b'([^aiu])","k$1")//normalisasi huruf ain mati '[^aiu] menjadi K
                .replaceAll("\\b`([^aiu])","k$1")//normalisasi huruf hamzah mati '[^aiu] menjadi K
                
                .replaceAll("\\bal`", "")//penghilangan al` hamzah hidup
                .replaceAll("'", "")//penghilangan petik
                .replaceAll("`", "")//penghilangan petik 
                
                
                .replaceAll("\\b(a)l([t,s,d,z,r,d,l,n])", "$1$2")//alif lam syamsiah
                .replaceAll("\\b([^aiu][aiu])l([t,s,d,z,r,d,l,n])", "$1$2")
                //.replaceAll("\\b(a)[^aiu]", "$1")//penghilangan al tasdid,doubel alrr,alshsh
                
                .replaceAll("\\biyy", "i")
                .replaceAll("kh","h")
                .replaceAll("sh", "s") //referensi Tabel 3 paper IPB dan 
                .replaceAll("ts", "s")//pemadanan aksara latin arab-indo kemenag
                .replaceAll("sy", "s")
                .replaceAll("dz", "z")
                .replaceAll("zh", "z")              
                .replaceAll("dh", "d")
                .replaceAll("th", "t")
                .replaceAll("q", "k")
                .replaceAll("aw", "au")
                .replaceAll("ay", "ai")
                
                .replaceAll("v","f")
                .replaceAll("p","f")
                .replaceAll("j","z")
                
                .replaceAll("ng", "n")//ikhfa
                .replaceAll("nb", "mb")//iqlab
                .replaceAll("ny", "y")//idgham
                .replaceAll("nw", "w")//idgham
                .replaceAll("nm", "m")//idgham
                .replaceAll("nn", "n")//idgham
                .replaceAll("nl", "l")//idgham
                .replaceAll("nr", "r")//idgham
                
                
                
                
               
                
                ;
                
        
        
    }
   
    /*
     * Get last name input from the user
     * 
     * @param text 	specify what to enter
     * @return 		lastName a last name string
     */
    
    private static String getName(String text){     
        System.out.print("Enter " + text +" : ");        
        Scanner input = new Scanner(System.in);        
        
        return input.nextLine();
        
    }
    
    /*
     * Produce the soundex of a lastname
     * 
     * @param lastName 	a last name
     * @return string 	converted last name to soundex
     */
     public static String doSoundexIND(String Name){
       
        String encodedName = encode(Name);           				// get codes for whole
        String noRepeats = deleteRepeats(encodedName); 					// encodeName to delete repeating the initial letter
        String str = Name.charAt(0) + noRepeats.substring(1);		// soundes now has initial letter and the rest are numbers
        str = (str.replace("/", "") + "***").substring(0,4);			// remove all zeros, add "000" at the end, and then take only the first 4 characters
        
        return str;														// this is the final soundex
    }
    
    /*
     * Encode any string s into a soundex
     * 
     * @param s 	the string wanted to be converted into soundex 
     * @return 		string converted into soundex
     */
    public static String encode(String s){
        s = s.toLowerCase();
        String encode = "";
        char letter;
        
        
        for(int i = 0; i < s.length(); i++){
            letter = s.charAt(i);            
            
            //referensi TA Sukma Rahadian string matching soundex dan metaphone 113030012 (2007)
            if(letter == 'a'|| letter == 'e'|| letter == 'i'|| letter == 'o'|| letter == 'u'|| letter == 'h'|| letter == 'y')
            	encode += "/";
            if(letter == 'b'|| letter == 'p')
            	encode += "0";
            if(letter == 'c'|| letter == 'j'|| letter == 's'|| letter == 'x'|| letter == 'z')
            	encode += "1";
            if(letter == 'd')
            	encode += "2";
            if(letter == 'f' || letter=='v')
            	encode += "3";
            if(letter == 'g'|| letter == 'k' || letter =='q')
            	encode += "4";
            if(letter == 'l')
            	encode += "5";   
            if(letter == 'm')
            	encode += "6";
            if(letter == 'n')
            	encode += "7";
            if(letter == 'r')
            	encode += "8";
            if(letter == 't')
            	encode += "9";
            if(letter == 'w')
            	encode += "w";
            
        }
        return encode;
    }
    
    /*
     * Remove repeating letters that are adjacent to each other of the last name
     * 
     * @param s 	the last name
     * @return 		string last name without repeating letters
     */
    public static String deleteRepeats(String s){
    	String temp = s.charAt(0)+"";
    	if(s.length() != 1){
	    	for(int i = 0; i<s.length()-1; i++){
	    		if(s.charAt(i)!=s.charAt(i+1))	// return when initial letter is not repeating anymore
	    			temp += s.charAt(i+1)+"";
	    	}
	    	return temp;
    	}
	    else
	    	return s; // default; return input
    }
    
    /*
     * Compares two last names if they're the same. Announces if two last names are the same otherwise, different.
     * 
     * @param n1 	1st last name
     * @param n2 	2nd last name
     * @param s1 	soundex code of the 1st last name
     * @param s2 	soundex code of the 2nd last name
     */
    public static void compareTheNames(String n1, String s1, String n2, String s2){
        if (s1.equals(s2)){
            System.out.println("'" +n1+ "', kode fonetis = " +s1);
            System.out.println("'"+n2+ "', kode fonetis = " +s2);
            System.out.println("SAMA!\n");
        }
        else{
            System.out.println("'" +n1+ "', kode fonetis = " +s1);
            System.out.println("'"+n2+ "', kode fonetis = " +s2);
            System.out.println("BEDA!\n");
        }
    }
}
