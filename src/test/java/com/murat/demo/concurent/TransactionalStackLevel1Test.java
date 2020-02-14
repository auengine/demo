package com.murat.demo.concurent;

import com.murat.demo.data.structures.TransactionalStack;
import com.murat.demo.data.structures.concurency.TransactionalStackLevel1Lock;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
public class TransactionalStackLevel1Test {

    private static final String SEPERATOR_DASH=" - ";

    @Test
    public void test_ConcurentPush() {

        try {

            TransactionalStackLevel1Lock<String> transactionalStack = new TransactionalStackLevel1Lock();
            IntStream.range(0, 1000).parallel().forEach(i ->
                    transactionalStack.push(Thread.currentThread() + SEPERATOR_DASH + i)
            );

            printAll(transactionalStack);

        }catch (Exception e){
           fail("Concurency problem!");
        }
    }



    @Test
    public void test_ConcurentPushInnerTransaction() {
        try {
            TransactionalStackLevel1Lock<String> transactionalStack = new TransactionalStackLevel1Lock();
            IntStream.range(0, 1000).parallel().
                    forEach(
                      (i) ->{
                             if( i % 5 == 0){
                                 transactionalStack.begin();
                            }
                          transactionalStack.push(Thread.currentThread() + SEPERATOR_DASH + transactionalStack.getCurrentTransName()+ SEPERATOR_DASH + i);
                          if( i % 10 == 0 && transactionalStack.isActive()){
                              transactionalStack.commit();
                          }
                      }

                   );

            while (transactionalStack.isActive()) transactionalStack.commit();

            printAll(transactionalStack);

        }catch (Exception e){
            fail("Concurrency problem!");
        }
    }


    private void printAll(TransactionalStackLevel1Lock<String> transactionalStack){

            while (!transactionalStack.isEmpty()) {
                String s = transactionalStack.pop();
                System.out.println(s);
            }
        System.out.println("");
        System.out.flush();
        System.out.println("");

    }


}
