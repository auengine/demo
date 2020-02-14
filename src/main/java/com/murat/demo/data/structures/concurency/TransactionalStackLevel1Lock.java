package com.murat.demo.data.structures.concurency;

import com.murat.demo.data.structures.Stack;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionalStackLevel1Lock<T extends Object> {

    private ReentrantLock lockFlag = new ReentrantLock();

    private Stack<T> mainStack;
    private Stack<Stack<T>> transactionStack;

    public TransactionalStackLevel1Lock() {
        this.mainStack = new Stack<>();
        this.transactionStack = new Stack<>();
        this.mainStack.setName(UUID.randomUUID().toString());
        this.transactionStack.setName(UUID.randomUUID().toString());
    }

    public void push(T data) {
        this.lock();
        if (!this.transactionStack.isEmpty()) {
            this.transactionStack.top().push(data);
        } else {
            this.mainStack.push(data);
        }
        this.unlock();
    }

    public T pop() {
        try {
            this.lock();
            if (!this.transactionStack.isEmpty()) {
                return this.transactionStack.top().pop();
            }
            return this.mainStack.pop();
        } finally {
            this.unlock();
        }
    }

    public void begin() {
        this.lock();
        Stack<T> innerStack = new Stack<>();
        innerStack.setName(UUID.randomUUID().toString());
        this.transactionStack.push(innerStack);
        this.unlock();
    }

    public boolean rollback() {
        try {
            this.lock();
            if (this.transactionStack.isEmpty()) return false;
            this.transactionStack.pop();
            return true;
        } finally {
            this.unlock();
        }
    }

    public boolean commit() {
        try {
            this.lock();
            if (transactionStack.isEmpty()) return false;
            Stack<T> innerTransaction = transactionStack.pop();
            if (!innerTransaction.isEmpty()) {
                Stack<T> parentTrasaction = transactionStack.isEmpty() ? mainStack : transactionStack.top();
                parentTrasaction.merge(innerTransaction.reverse());
            }
            return true;
        } finally {
            this.unlock();
        }
    }

    public String getCurrentTransName(){
        this.lock();
        String name = transactionStack.isEmpty()? mainStack.getName() :transactionStack.top().getName();
        this.unlock();
        return name;
      }

    public boolean isActive(){
        this.lock();
        boolean result= transactionStack.isEmpty() ? false :true;
        this.unlock();
        return result;
    }

    public boolean isEmpty(){
        this.lock();
        boolean result=this.mainStack.isEmpty();
        this.unlock();
        return result;
    }

    //region  lock management
    private void lock(){
            this.lockFlag.lock();
    }
    private void unlock() {
            this.lockFlag.unlock();
    }
    //endregion


}
