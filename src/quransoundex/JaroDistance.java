/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quransoundex;

/**
 *
 * @author ronifajrian
 */
public class JaroDistance {

    public static double PREFIX_SCALING_FACTOR = 0.1;
    
    public static double transposition (String s, String t){
        int s_len = s.length();
        int t_len = t.length();

        if (s_len == 0 && t_len == 0 || s.equals(t)) {
            return 1;
        }

        int match_distance = (Integer.max(s_len, t_len) / 2) - 1;

        boolean[] s_matches = new boolean[s_len];
        boolean[] t_matches = new boolean[t_len];

        int matches = 0;
        int transpositions = 0;

        for (int i = 0; i < s_len; i++) {
            int start = Integer.max(0, i - match_distance);
            int end = Integer.min(i + match_distance + 1, t_len);

            for (int j = start; j < end; j++) {
                if (t_matches[j]) {
                    continue;
                }
                if (s.charAt(i) != t.charAt(j)) {
                    continue;
                }
                s_matches[i] = true;
                t_matches[j] = true;
                matches++;
                break;
            }
        }

        if (matches == 0) {
            return 0;
        }

        int k = 0;
        for (int i = 0; i < s_len; i++) {
            if (!s_matches[i]) {
                continue;
            }
            while (!t_matches[k]) {
                k++;
            }
            if (s.charAt(i) != t.charAt(k)) {
                transpositions++;
            }
            k++;
        }
        return transpositions;
    }
    
    public static double match(String s, String t){
        int s_len = s.length();
        int t_len = t.length();

        if (s_len == 0 && t_len == 0 || s.equals(t)) {
            return 1;
        }
//else 
//            if(s.equals(t)){
//                return 1;
//            }

        int match_distance = (Integer.max(s_len, t_len) / 2) - 1;

        boolean[] s_matches = new boolean[s_len];
        boolean[] t_matches = new boolean[t_len];

        int matches = 0;
        int transpositions = 0;

        for (int i = 0; i < s_len; i++) {
            int start = Integer.max(0, i - match_distance);
            int end = Integer.min(i + match_distance + 1, t_len);

            for (int j = start; j < end; j++) {
                if (t_matches[j]) {
                    continue;
                    
                }
                if (s.charAt(i) != t.charAt(j)) {
                    continue;
                }
                s_matches[i] = true;
                t_matches[j] = true;
                matches++;
                break;
            }
        }

        if (matches == 0) {
            return 0;
        }

        int k = 0;
        for (int i = 0; i < s_len; i++) {
            if (!s_matches[i]) {
                continue;
            }
            while (!t_matches[k]) {
                k++;
            }
            if (s.charAt(i) != t.charAt(k)) {
                transpositions++;
            }
            k++;
        }
        return matches;
    }

    public static double jaro(String s, String t) {

        int s_len = s.length();
        int t_len = t.length();

        if (s_len == 0 && t_len == 0 || s.equals(t)) {
            return 1;
        }

        int match_distance = (Integer.max(s_len, t_len) / 2) - 1;
        System.out.println("MATCH DISTANCE = "+match_distance);
        
        boolean[] s_matches = new boolean[s_len];
        boolean[] t_matches = new boolean[t_len];

        int matches = 0;
        int transpositions = 0;

        for (int i = 0; i < s_len; i++) {
            int start = Integer.max(0, i - match_distance);
            int end = Integer.min(i + match_distance + 1, t_len);

            for (int j = start; j < end; j++) {
                if (t_matches[j]) {
                    continue;
                }
                if (s.charAt(i) != t.charAt(j)) {
                    continue;
                }
                s_matches[i] = true;
                t_matches[j] = true;
                matches++;
                System.out.println(""+s.charAt(i)+" & "+t.charAt(j)+"["+i+","+j+"]");
                break;
            }
        }

        if (matches == 0) {
            return 0;
        }

        int k = 0;
        for (int i = 0; i < s_len; i++) {
            if (!s_matches[i]) {
                continue;
            }
            while (!t_matches[k]) {
                k++;
            }
            if (s.charAt(i) != t.charAt(k)) {
                transpositions++;
            }
            k++;
        }

        double jDistance = (((double) matches / s_len)
                + ((double) matches / t_len)
                + (((double) matches - (transpositions) / 2) / matches)) / 3.0;
        return jDistance;
    }
    

    public static int winklerCommonPrefix(String stringOne, String stringTwo) {

        int commonPrefix = 0;
        //Find the shortest string (we don't want an index out of bounds
        //exception).
        int boundary = (stringOne.length() <= stringTwo.length())
                ? stringOne.length() : stringTwo.length();
        //iterate until the boundary is hit (shortest string length)
        for (int i = 0;
                i < boundary;
                i++) {
            //If the character at the current position matches
            if (stringOne.charAt(i) == stringTwo.charAt(i)) {
                //increment the common prefix
                commonPrefix++;
            } else {
                //otherwise, continue no further, we are done.
                break;
            }
            //If we hit our max number of matches, bust out
            //of the loop.
            if (commonPrefix == 4) {
                break;
            }
        }
        //return the number of matches at the beginning of 
        //both strings
        return commonPrefix;
    }

    public static double calculateJW(String s, String t) {
        int winklerCommonPrefix
                = winklerCommonPrefix(s, t);
        double jaroDistance = jaro(s, t);
        double jwDistance;
        
        double threshold = 0.7;
        if(jaroDistance < threshold){
            jwDistance = jaroDistance;
        }else        
            jwDistance = jaroDistance + ((winklerCommonPrefix * PREFIX_SCALING_FACTOR) * (1 - jaroDistance));
//         String percentage = String.format("%2.02f", hasilPresisi);
//            System.out.println(percentage);
        return jwDistance;
    }
    
    public static double calculateJD(String s, String t) {
        double jaroDistance = jaro(s, t);
        return jaroDistance;
    }

    public static void main(String[] args) {
        String s="funnels";
        String t="tunnesl";
        
        System.out.println("Jaro Distance (dJ)= "+jaro(s, t));       
        System.out.println("Transposition = "+transposition(s, t));
        System.out.println("T (half transposisi)= "+transposition(s, t)/2);
        System.out.println("Matches (m)= "+match(s, t));
        System.out.println("Common Prefix  (huruf yg sama di awal) (l)= "+winklerCommonPrefix(s, t));
        System.out.println("Prefix Scaling Factor (p)= "+PREFIX_SCALING_FACTOR+"\n");
        System.out.println("Jaro Winkler Distance (dW)= "+calculateJW(s,t));       
        
    }
}
