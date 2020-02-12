package com.murat.demo.algo;

import java.util.*;

public class UpsideDownNail {

    //https://www.solveforum.com/threads/codility-test-for-nails-hammer-on-hold.12748/

   /* There are N nails hammered into the same block of wood. Each nail sticks out of the wood at some length. You can choose at most K nails and hammer them down to any length between their original length and 0. Nails cannot be pulled up. the goal is to have as many nails of the same length as possible. the goal is to have as many nails of the same length as possible.

    */



    public   static  long option(int[] nails, int maxHammeringCount) {

        //preserve order of input and count occurences
        LinkedHashMap<Long, Long> occurrenceMap = new LinkedHashMap<>();
        Arrays.stream(nails).forEach(e -> occurrenceMap.merge(Long.valueOf(e), 1L, Long::sum));

        //build key list in reverse order
        List<Long> keys= new ArrayList<Long>(occurrenceMap.keySet());
        Collections.reverse(keys);

        long inheritedHammeringCount = 0;
        long maxResult=0;

        for(int i =0 ; i<keys.size();i++) {
            long val = occurrenceMap.get(keys.get(i));
            long rowResult= val + inheritedHammeringCount;
            if(rowResult>maxResult){
                maxResult=rowResult;
            }
            inheritedHammeringCount =Long.min(inheritedHammeringCount+val,maxHammeringCount);
        }
        return  maxResult;
    }


    public  static  long option_old(int[] nails, int maxHammeringCount) {

        //preserve order of input and count occurences
        LinkedHashMap<Long, Long> occurrenceMap = new LinkedHashMap<>();
        Arrays.stream(nails).forEach(e -> occurrenceMap.merge(Long.valueOf(e), 1L, Long::sum));

        //build key list in reverse order
        List<Long> keys= new ArrayList<Long>(occurrenceMap.keySet());
        Collections.reverse(keys);

        int hammeringCount = 0;
        long maxRowItem = 0;

        int i = 0;
        long val =0;

        /*
            because hummering is only work on one direction
            find  the point we can utilize all hummers
         */
        for(; i<keys.size(); i++){
            val = occurrenceMap.get(keys.get(i));
            if (hammeringCount + val > maxHammeringCount) {
                break;
            }
            hammeringCount += val ;
        }

        // if hummering count is not too large
        if(i < keys.size()) {

            //special treatment for the level
            val = occurrenceMap.get(keys.get(i));
            long remeinder = val + hammeringCount - maxHammeringCount;
            hammeringCount = maxHammeringCount;
            i++;

            //find row with max nails
            for(; i<keys.size();i++) {
                val = occurrenceMap.get(keys.get(i));
                if (val > maxRowItem) {
                    maxRowItem = val;
                }
            }

            if(remeinder>maxRowItem){
                maxRowItem=remeinder;
            }

        }
        return  hammeringCount+ maxRowItem;
    }

}
