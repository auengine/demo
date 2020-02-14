package com.murat.demo.concurent;


import com.murat.demo.data.structures.concurency.TransactionalStackWithJavaStackLock;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
public class TransactionalStackWithJavaStackTest {

    private static final String SEPERATOR_DASH=" - ";

    @Test
    public void test_ConcurentPush() {

         int items=1000;
        try {

            TransactionalStackWithJavaStackLock<String> transactionalStack = new TransactionalStackWithJavaStackLock();
            IntStream.range(0, items).parallel().forEach(i ->
                    transactionalStack.push(Thread.currentThread() + SEPERATOR_DASH + i)
            );
            assertEquals(items,transactionalStack.currentSize());

        }catch (Exception e){
           fail("Concurency problem!");
        }
    }



}
