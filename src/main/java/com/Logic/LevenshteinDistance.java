package com.Logic;

import java.util.Arrays;

public class LevenshteinDistance {
    static int computeLDistance(String str1, String str2) {

        // A 2D matrix to store previously calculated values => Dynamic Programming
        int[][] store = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            for (int j = 0; j <= str2.length(); j++) {
                if (i == 0) {
                    // If str1 is empty, all characters of str2 are inserted into str1
                    store[i][j] = j;
                } else if (j == 0) {
                    // If str2 is empty, all characters of str1 are removed
                    store[i][j] = i;
                } else {

                    int needsReplacement = str1.charAt(i - 1) ==  str2.charAt(j - 1) ? 0 : 1;

                    int replacementCost = store[i - 1][j - 1] + needsReplacement;
                    int insertionCost = store[i][j - 1] +1;
                    int deletionCost = store[i - 1][j] +1;
                    
                    //Only store the minimum cost
                    store[i][j] = Math.min(Math.min(replacementCost, insertionCost), deletionCost);

                }
            }
        }

        return store[str1.length()][str2.length()];
    }


    // Driver Code
    public static void main(String args[]) {

        String s1 = "glomax";
        String s2 = "folmax";

        System.out.println(computeLDistance(s1, s2));
    }
}
