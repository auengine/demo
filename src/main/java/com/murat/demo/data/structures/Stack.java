package com.murat.demo.data.structures;

public class Stack<T extends Object> {
    private Node head;
    private int count;
    private String name ;

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void push(T data) {
        Node node = new Node(data);
        node.setNext(head);
        this.head = node;
        this.count++;
    }

    public T pop() {
        if (this.isEmpty()) {
            throw new IllegalStateException("pop an empty stack!");
        }
        T data = (T) this.head.getData();
        this.head = this.head.getNext();
        this.count--;
        return data;
    }

    public T top() {
        if (this.isEmpty()) {
            throw new IllegalStateException("top an empty stack!");
        }
        T data = (T) this.head.getData();
        return data;
    }

    public boolean isEmpty() {
        return this.count == 0 ? true : false;
    }

    public Stack<T> reverse(){
        Stack reverseStack= new Stack<>();
        while (!this.isEmpty())
        {
            reverseStack.push(this.pop());
        }
        return reverseStack;
    }

    public   void merge(Stack<T> stack){
        while (!stack.isEmpty())
        {
            this.push(stack.pop());
        }
    }
}