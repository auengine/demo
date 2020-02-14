package com.murat.demo.data.structures.concurency;

import com.murat.demo.data.structures.Stack;

import java.util.concurrent.locks.ReentrantLock;

public class TransactionalStack<T extends Object> {

    private ReentrantLock lockFlag= new ReentrantLock();

    private Stack<T> mainStack = new Stack<>();
    private Stack<Stack<T>> transactionStack = new Stack<>();

    public void push(T data) {
        this.lock();
        if (!this.transactionStack.isEmpty()) {
              this.transactionStack.top().push(data);
        }else{
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
        }finally {
           this.unlock();
        }
    }

    public void begin() {
        this.lock();
        this.transactionStack.push(new Stack<>());
        this.unlock();
    }

    public boolean rollback() {
       try {
           this.lock();
           if (this.transactionStack.isEmpty()) return false;
           this.transactionStack.pop();
           return true;
       }finally {
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
        }finally {
            this.unlock();
        }
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
