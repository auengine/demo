package com.murat.demo.data.structures;

import java.util.concurrent.locks.ReentrantLock;

public class Queue<T extends Object> {

    private class Node {
        private Object data;
        private Node next;

        public Node(Object data) {
            this.data = data;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Object getData() {
            return data;
        }
    }


    private Node head;
    private Node rear;
    private int count;
    private ReentrantLock lock = new ReentrantLock();

    public void enqueue(T data) {
        Node node = new Node(data);
        node.next = null;
        this.lock.lock();
        if (this.isEmpty()) {
            this.head = node;
            this.rear = node;
            this.count++;
            this.lock.unlock();
            return;
        }
        this.rear.setNext(node);
        this.rear = node;
        this.count++;
        this.lock.unlock();
    }

    public T dequeue() {
        this.lock.lock();
        if (this.isEmpty()) {
            this.lock.unlock();
            throw new IllegalStateException("dequeue an empty queue!");
        }
        T data = (T) this.head.getData();
        this.head = this.head.next;
        this.count--;
        this.lock.unlock();
        return data;
    }

    public boolean isEmpty() {
        return this.count == 0 ? true : false;
    }
}

