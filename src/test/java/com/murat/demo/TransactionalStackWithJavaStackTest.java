package com.murat.demo;



import com.murat.demo.data.structures.TransactionalStackWithJavaStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class TransactionalStackWithJavaStackTest {
    TransactionalStackWithJavaStack<String> transactionalStack_L0_01 = new TransactionalStackWithJavaStack<>();
    TransactionalStackWithJavaStack<String> transactionalStack_L0_01and_L101 = new TransactionalStackWithJavaStack<>();
    TransactionalStackWithJavaStack<String> transactionalStack_L0_02and_L101_L2023 = new TransactionalStackWithJavaStack<>();


    @BeforeEach
    public void init() {

        //init transactionalStack_L0_01and_L101
        int level=0;
        transactionalStack_L0_01and_L101.push(str(level,0));
        transactionalStack_L0_01and_L101.push(str(level,1));
        level++;
        transactionalStack_L0_01and_L101.push(str(level,0));
        transactionalStack_L0_01and_L101.push(str(level,1));

        //init transactionalStack_L0_01and_L101
        level=0;
        transactionalStack_L0_01.push(str(level,0));
        transactionalStack_L0_01.push(str(level,2));

        //init transactionalStack_L0_02and_L101_L2023
        level=0;
        transactionalStack_L0_02and_L101_L2023.push(str(level,0));
        transactionalStack_L0_02and_L101_L2023.push(str(level,2));
        level++;
        transactionalStack_L0_02and_L101_L2023.push(str(level,0));
        transactionalStack_L0_02and_L101_L2023.push(str(level,1));
        level++;
        transactionalStack_L0_02and_L101_L2023.push(str(level,0));
        transactionalStack_L0_02and_L101_L2023.push(str(level,2));
        transactionalStack_L0_02and_L101_L2023.push(str(level,3));



    }

    @Test
    public void test_Push_Commit() {

        TransactionalStackWithJavaStack<String> transactionalStack = new TransactionalStackWithJavaStack<>();
        int level=0;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));

        transactionalStack.begin();  level++;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));
        transactionalStack.commit();

        while (!transactionalStack.isCurrentEmpty()) {
            assertEquals(transactionalStack.pop(), transactionalStack_L0_01and_L101.pop());
        }
    }

    @Test
    public void test_Push_Pop_Commit_Rollback() {

        TransactionalStackWithJavaStack<String> transactionalStack = new TransactionalStackWithJavaStack();

        int level=0;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));
        transactionalStack.pop();
        transactionalStack.push(str(level,2));

        transactionalStack.begin();  level++;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));

        transactionalStack.begin();  level++;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));

        transactionalStack.commit();
        transactionalStack.rollback();

        while (!transactionalStack.isCurrentEmpty()) {
            assertEquals(transactionalStack.pop(), transactionalStack_L0_01.pop());
        }

    }


    @Test
    public void test_Push_Pop_Peek_Commit() {
        TransactionalStackWithJavaStack<String> transactionalStack = new TransactionalStackWithJavaStack();

        int level=0;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));
        transactionalStack.pop();
        transactionalStack.push(str(level,2));

        transactionalStack.begin();  level++;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));
        transactionalStack.peek();
        transactionalStack.begin();  level++;
        transactionalStack.push(str(level,0));
        transactionalStack.push(str(level,1));
        transactionalStack.peek();
        transactionalStack.pop();
        transactionalStack.push(str(level,2));
        transactionalStack.push(str(level,3));

        transactionalStack.commit();
        transactionalStack.commit() ;
        while (!transactionalStack.isCurrentEmpty()) {
            assertEquals(transactionalStack.pop(), transactionalStack_L0_02and_L101_L2023.pop());
        }

    }




    private static String  str(int level,int object){
        return level + "-" +object;
    }


    private void printAll(TransactionalStackWithJavaStack<String> transactionalStack){

            while (!transactionalStack.isCurrentEmpty()) {
                String s = transactionalStack.pop();
                System.out.println(s);
            }
        System.out.println("Printed all!");
        System.out.flush();
        System.out.println("End!");

    }



}
