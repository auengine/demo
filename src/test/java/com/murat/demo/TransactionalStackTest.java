package com.murat.demo;


import com.murat.demo.data.structures.TransactionalStack;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransactionalStackTest {


    @Test
    public void testListConversions_thenConversionListWithTransactionIDShouldBeReturned() {
        String LEVEL4_2="level4 :test2";
        String LEVEL1_2="level1 :test2";
        TransactionalStack<String> transactionalStack = new TransactionalStack();
        String item="";
        try {

          transactionalStack.push("level0:test1");
          transactionalStack.push("level0:test2");

          transactionalStack.begin();
          transactionalStack.push("level1 :test1");
          transactionalStack.push(LEVEL1_2);

          transactionalStack.begin();
          transactionalStack.push("level2 :test1");

          transactionalStack.begin();//level3

          transactionalStack.begin();
          transactionalStack.push("level4 :test1");
          transactionalStack.push(LEVEL4_2);
          transactionalStack.begin();
          transactionalStack.push("level5 :test1");

          transactionalStack.commit(); //level 5
          transactionalStack.commit(); //level 4

          transactionalStack.pop();
          item=  transactionalStack.pop();
          assertEquals(item,LEVEL4_2);

          item=transactionalStack.pop();
          item=transactionalStack.pop();
        }catch (Exception e){

            assertEquals(e.getClass(),IllegalStateException.class);
            transactionalStack.rollback();
        }

        transactionalStack.rollback();
        item=transactionalStack.pop();
        assertEquals(item,LEVEL1_2);


    }

    @Test
    public void test2() {

        TransactionalStack<String> transactionalStack = new TransactionalStack();
        int level=0;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));

        transactionalStack.begin();  level++;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));
        transactionalStack.commit();

        printAll(transactionalStack);

    }


    @Test
    public void test3() {
        TransactionalStack<String> transactionalStack = new TransactionalStack();
        int level=0;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));

        transactionalStack.begin();  level++;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));
        transactionalStack.pop();
        transactionalStack.push(str(level,2));
        transactionalStack.commit(); level --;
        printAll(transactionalStack);

    }

    @Test
    public void test4() {
        TransactionalStack<String> transactionalStack = new TransactionalStack();
        int level=0;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));

        transactionalStack.begin();  level++;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));
        transactionalStack.pop();
        transactionalStack.push(str(level,2));

        transactionalStack.begin();  level++;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));
        transactionalStack.rollback(); level--;
        transactionalStack.push(str(level,2));
        transactionalStack.commit(); level--;
        transactionalStack.push(str(level,2));
        assertEquals(level,0);
        printAll(transactionalStack);

    }


    private static String  str(int level,int object){
        return level + "-" +object;
    }


    private void printAll(TransactionalStack<String> transactionalStack){

        try {

            while (true) {
                String s = transactionalStack.pop();
                System.out.println(s);
            }

        }catch (IllegalStateException e){
            System.out.println("----------EMPTY-------");
        }
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.flush();
    }



}
