package space;

import java.util.Deque;
import java.util.HashMap;

public class MemoryCache {
    private Node head;
    private Node tail;
    private int capacity = 0;
    private HashMap<Integer, Node> map = null;

    public MemoryCache(int capacity){
        this.capacity = capacity;
    }

    public Local get(int key){
        if(this.map.get(key) == null){
            return null;
        }

        Node tail = map.get(key);

        this.removeNode(tail);
        this.getNode(tail);

        return tail.local_map;

    }

    public void put(int key, Local local_map){
        if(this.map.containsKey(key)){
            Node tail = this.map.get(key);
            tail.local_map = local_map;

            // Move to tail for recently accessed
            this.removeNode(tail);
            getNode(tail);
        }
    }
    public void removeNode(Node node){

    }

    private void getNode(Node node){
        if(this.tail != null){
            this.tail.next = node;
        }

        node.prev = this.tail;
        node.next = null;
        this.tail = node;

        if(this.head == null){
            this.head = this.tail;
        }
    }


    /**
     * Node used in the doubly linked list of the cache that
     * contains the key and the local map.
     */
    class Node{
        int key;
        Local local_map = null;
        Node next = null;
        Node prev = null;

        public Node(int key, Local local_map){
            this.key = key;
            this.local_map = local_map;
        }
    }
}
