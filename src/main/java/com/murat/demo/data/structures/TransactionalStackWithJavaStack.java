package com.murat.demo.data.structures;

public class TransactionalStackWithJavaStack<T extends Object> {


    private java.util.Stack<T> mainStack;
    private java.util.Stack<java.util.Stack<T>> transactionStack;

    public TransactionalStackWithJavaStack() {
        this.mainStack = new java.util.Stack<>();
        this.transactionStack = new java.util.Stack<>();
    }

    //region stack methods
    public void push(T data) {
        if (!this.transactionStack.isEmpty()) {
            this.transactionStack.peek().push(data);
        } else {
            this.mainStack.push(data);
        }
    }

    public T pop() {
        if (!this.transactionStack.isEmpty()) {
            return this.transactionStack.peek().pop();
        }
        return this.mainStack.pop();
    }

    public T peek() {

        if (!this.transactionStack.isEmpty()) {
            return this.transactionStack.peek().peek();
        }
        return this.mainStack.peek();

    }

    //endregion

    //region transactional methods
    public void begin() {
        java.util.Stack<T> innerStack = new java.util.Stack<>();
        this.transactionStack.push(innerStack);

    }

    public boolean rollback() {

        if (this.transactionStack.isEmpty()) return false;
        this.transactionStack.pop();
        return true;

    }

    public boolean commit() {
        if (transactionStack.isEmpty()) return false;
        java.util.Stack<T> innerTransaction = transactionStack.pop();
        if (!innerTransaction.isEmpty()) {
            java.util.Stack<T> parentTrasaction = transactionStack.isEmpty() ? mainStack : transactionStack.peek();
           // innerTransaction.sort(Collections.reverseOrder());
            parentTrasaction.addAll(innerTransaction);
        }
        return true;
    }

    //endregion

    //region utility methods
    public boolean isActive() {
        boolean result = transactionStack.isEmpty() ? false : true;

        return result;
    }

    public boolean isCurrentEmpty() {
        if (!this.transactionStack.isEmpty()) {
            return this.transactionStack.peek().isEmpty();
        }
        return this.mainStack.isEmpty();
    }

    //endregion


}
