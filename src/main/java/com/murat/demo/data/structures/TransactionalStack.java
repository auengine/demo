package com.murat.demo.data.structures;

public class TransactionalStack<T extends Object> {

    private Stack<T> mainStack = new Stack<>();
    private Stack<Stack<T>> transactionStack = new Stack<>();

    public void push(T data) {
        if (!this.transactionStack.isEmpty()) {
              this.transactionStack.top().push(data);
        }else{
            this.mainStack.push(data);
        }

    }

    public T pop() {
        if (!this.transactionStack.isEmpty()) {
           return this.transactionStack.top().pop();
        }
        return this.mainStack.pop();
    }

    public void begin() {
        this.transactionStack.push(new Stack<>());
    }

    public boolean rollback() {
        if (this.transactionStack.isEmpty()) return false;
        this.transactionStack.pop();
        return true;
    }

    public boolean commit() {
        if (transactionStack.isEmpty()) return false;
        Stack<T> innerTransaction = transactionStack.pop();
        if ( !innerTransaction.isEmpty() ) {
            Stack<T> parentTrasaction = transactionStack.isEmpty() ?  mainStack :  transactionStack.top();
            parentTrasaction.merge(innerTransaction.reverse());
        }
        return true;
    }


}
