package com.murat.demo.data.structures;

public class Node {
        private Object data;
        private Node next;

        public Node(Object data) {
            this.data = data;
        }

        public void setNext(Node next) {
            this.next = next;
        }
        public Node getNext() {        return next;    }

        public Object getData() {
            return data;
        }
}
