package com.murat.demo;


import com.murat.demo.algo.UpsideDownNail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UpsideDownNailTest {


    @Test
    public void testListConversions_thenConversionListWithTransactionIDShouldBeReturned() {
         int[] arr1=  new int[]{1,1,3,3,3,4,5,5,5};  int N1=2;
        int[] arr2=  new int[]{1};  int N2=5;
        int[] arr3=  new int[]{1,1,3,3,3,4,5,5,5,5,5,5,5};  int N3=2;
        int[] arr4=  new int[]{1,1,3,3,3,3,3,4,5,5,5,5,5,5,5};  int N4=3;
        int[] arr5=  new int[]{1,1,3,3,4,4,5,5,5,5,5,5,5,5};  int N5=4;

        assertEquals(UpsideDownNail.option(arr1,N1),5);  assertEquals(UpsideDownNail.option_old(arr1,N1),5);
        assertEquals(UpsideDownNail.option(arr2,N2),1);  assertEquals(UpsideDownNail.option_old(arr2,N2),1);
        assertEquals(UpsideDownNail.option(arr3,N3),7);  assertEquals(UpsideDownNail.option_old(arr3,N3),7);
        assertEquals(UpsideDownNail.option(arr4,N4),8);  assertEquals(UpsideDownNail.option_old(arr4,N4),8);
        assertEquals(UpsideDownNail.option(arr5,N5),8);  assertEquals(UpsideDownNail.option_old(arr5,N5),8);




    }








}
