package com.callum.squareboard;

public class HashNode<T> {
    private String key;
    //using generics improves re-useability and robustness.
    private T value;
    private HashNode<T> next;
    private HashNode<T> separateChaining;

    /**
     * Constructor to create class.
     * @param key the key of the node.
     * @param value the value of the node.
     */
    public HashNode(String key, T value) {
        this.key = key;
        this.value = value;
    }

    /**
     * get the key
     * @return the key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Set the key
     * @param key the key
     */

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Get the value.
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * set the value
     * @param value get the value
     */

    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Get the next node
     * @return the next node
     */
    public HashNode<T> getNext() {
        return next;
    }

    /**
     * Set the next node
     * @param next the node that is next
     */
    public void setNext(HashNode<T> next) {
        this.next = next;
    }

    /**
     * Get the seperate chain, a node with the same hash but a differant key.
     * @return the next of separate chains
     */
    public HashNode<T> getSeparateChaining() {
        return separateChaining;
    }

    /**
     * Set the next separate chain node
     * @param separateChaining the node
     */
    public void setSeparateChaining(HashNode<T> separateChaining) {
        this.separateChaining = separateChaining;
    }

    /**
     * Get the furthest node in the chain.
     * @param value the node to add.
     */
    public void addSeparateChaining(HashNode<T> value) {
        if (separateChaining == null){
            separateChaining = value;
        }

        HashNode<T> currentChain = separateChaining;

        while (currentChain.getSeparateChaining() != null){
            currentChain = currentChain.getSeparateChaining();
        }

        currentChain.setSeparateChaining(value);
    }
}
