package com.murat.demo.data.structures.concurency;

import com.murat.demo.data.structures.Node;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Murat Ayengin
 * Stack implementation with default concurrency support
 * throws IllegalStateException if internal state and operation conflict
 *
 */
public class Stack<T extends Object> {

    //fields
    private Node head;
    private int count;

    //concurency
    private boolean enableConcurrency;
    private ReentrantLock lockFlag= new ReentrantLock();

    //region constructure
    public Stack(){
        this.enableConcurrency=true;
    }

    public Stack(boolean concurrent){
         this.enableConcurrency=concurrent;
    }
    // endregion

    //region  stack functions

    public void push(T data) {
        Node node = new Node(data);
        this.lock();
        node.setNext(head);
        this.head = node;
        this.count++;
        this.unlock();
    }

    public T pop() {
        return pop(true);
    }

    private T pop(boolean lock) {
        this.lock(lock);
        if (this.isEmptyNoLock()) {
            throw new IllegalStateException("pop an empty stack!");
        }
        T data = (T) this.head.getData();
        this.head = this.head.getNext();
        this.count--;
        this.unlock(lock);
        return data;
    }

    public T top( ){
        this.lock();
        if (this.isEmptyNoLock()) {
            throw new IllegalStateException("top an empty stack!");
        }
        T data = (T) this.head.getData();
        this.unlock();
        return data;
    }



    public boolean isEmpty() {
        return isEmpty(true);
    }

    private boolean isEmptyNoLock() {
        return isEmpty(false);
    }
    private boolean isEmpty(boolean lock) {
        this.lock(lock);
        boolean result= this.count == 0 ? true : false;
        this.lock(lock);
        return result;
    }
    // endregion

    //region  lock management
    private void lock(){
        this.lock(true);
    }
    private void lock(boolean lock){
        if(this.enableConcurrency && lock){
            this.lockFlag.lock();
        }
    }
    private void unlock() {
        this.unlock(true);
    }
    private void unlock(boolean unlock) {
        if (this.enableConcurrency && unlock) {
            this.lockFlag.unlock();
        }
    }
    //endregion

    //region external utility functions
    public Stack<T> reverseOrder(){
        Stack reverseStack= new Stack<>();
        this.lock(true);
        while (!this.isEmptyNoLock())
        {
            reverseStack.push(this.pop(false));
        }
        this.unlock(true);
        return reverseStack;
    }

    public   void merge(Stack<T> stack){
        this.lock(true);
        while (!stack.isEmpty())
        {
            this.push(stack.pop(false));
        }
        this.unlock(true);
    }
    // endregion



}