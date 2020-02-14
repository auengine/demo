package com.murat.demo.data.structures.concurency;

import java.util.concurrent.locks.ReentrantLock;

public class TransactionalStackWithJavaStackLock<T extends Object> {


    private java.util.Stack<T> mainStack;
    private java.util.Stack<java.util.Stack<T>> transactionStack;

    private ReentrantLock lockFlag;

    public TransactionalStackWithJavaStackLock() {
        this.mainStack = new java.util.Stack<>();
        this.transactionStack = new java.util.Stack<>();
        this.lockFlag = new ReentrantLock();
    }

    //region stack methods
    public void push(T data) {
        this.lock();
        if (!this.transactionStack.isEmpty()) {
            this.transactionStack.peek().push(data);
        } else {
            this.mainStack.push(data);
        }
        this.unlock();
    }

    public T pop() {
        try {
            this.lock();
            if (!this.transactionStack.isEmpty()) {
                return this.transactionStack.peek().pop();
            }
            return this.mainStack.pop();
        } finally {
            this.unlock();
        }
    }

    public T peek() {
        try {
            this.lock();
            if (!this.transactionStack.isEmpty()) {
                return this.transactionStack.peek().peek();
            }
            return this.mainStack.peek();
        } finally {
            this.unlock();
        }

    }
    //endregion

    //region transactional methods
    public void begin() {
        java.util.Stack<T> innerStack = new java.util.Stack<>();
        this.lock();
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
            java.util.Stack<T> innerTransaction = transactionStack.pop();
            if (!innerTransaction.isEmpty()) {
                java.util.Stack<T> parentTrasaction = transactionStack.isEmpty() ? mainStack : transactionStack.peek();
                // innerTransaction.sort(Collections.reverseOrder());
                parentTrasaction.addAll(innerTransaction);
            }
            return true;
        } finally {
            this.unlock();
        }
    }

    //endregion

    //region utility methods
    public boolean isActive() {
        this.lock();
        boolean result = transactionStack.isEmpty() ? false : true;
        this.unlock();
        return result;
    }

    public boolean isCurrentEmpty() {
        try {
            this.lock();
            if (!this.transactionStack.isEmpty()) {
                return this.transactionStack.peek().isEmpty();
            }
            return this.mainStack.isEmpty();
        } finally {
            this.unlock();
        }
    }

    public int currentSize() {
        try {
            this.lock();
            if (!this.transactionStack.isEmpty()) {
                return this.transactionStack.peek().size();
            }
            return this.mainStack.size();
        } finally {
            this.unlock();
        }
    }

    //endregion


    //region  lock management
    private void lock() {
        this.lockFlag.lock();
    }

    private void unlock() {
        this.lockFlag.unlock();
    }
    //endregion
}
