package com.callum.squareboard;

public class Dictionary implements DictionaryADT {
    private final Bucket<Record> bucket = new Bucket<>();
    private final int buckets;
    private int records = 0;
    private int size;

    public Dictionary() {
        this(10000, 20);
    }

    public Dictionary(int size) {
        this(size, 20);
    }

    public Dictionary(int size, int bucketsSize) {
        this.buckets = bucketsSize;

        Bucket<Record> lastBucket = bucket;

        for (int i = 1; i <= buckets - 1; i++) {
            lastBucket.nextBucket = new Bucket<>();
            lastBucket = lastBucket.nextBucket;
        }
    }

    /**
     * Put an entry in the dictionary
     * @param val the value to add
     * @return 1 if collision, else 0.
     * @throws DuplicatedKeyException
     */
    @Override
    public int put(Record val) throws DuplicatedKeyException {
        int index = ((int) (Hash.hashCode(val.getKey()) & (buckets - 1)));

        int res = bucket.getIndex(index).addNode(new HashNode<Record>(val.getKey(), val));
        records++;

        return res;
    }

    /**
     * Get a key
     * @param key the key to look for
     * @return the record if found, or null.
     */
    @Override
    public Record get(String key) {
        long hashcode = Hash.hashCode(key);
        int index = ((int) (hashcode & (buckets - 1)));

        Bucket<Record> location = bucket.getIndex(index);
        HashNode<Record> inspect = location.rootNode;

        //While there is something to inspect.
        while (inspect != null) {

            //if the object has the correct hashcode.
            if (Hash.hashCode(inspect.getKey()) == hashcode) {

                //if the object has the same key.
                if (inspect.getKey().equals(key)) {

                    //if a match, return.
                    return inspect.getValue();
                } else {

                    //if correct hash but wrong key
                    if (inspect.getSeparateChaining() != null) {

                        //inspect the chain
                        inspect = inspect.getSeparateChaining();
                        continue;
                    }

                    //if it is the correct but the wrong key, and there are not more chains to search.
                    return null;
                }
            }

            inspect = inspect.getNext();
        }

        return null;
    }

    /**
     * The amount of records  that have been added not including chains.
     * @return the number of added records
     */
    @Override
    public int numRecords() {
        return records;
    }

    /**
     * Remove a reocrd from the list
     * @param key the key to remove
     * @throws InexistentKeyException
     */
    public void remove(String key)  throws InexistentKeyException{
        long hashcode = Hash.hashCode(key);
        int index = ((int) (hashcode & (buckets - 1)));

        Bucket<Record> location = bucket.getIndex(index);
        location.remove(key);
        records--;
    }

    /**
     * Buckets allow for the hashes to be compressed into indexes that are stored in buckets.
     * @param <T>
     */
    private static class Bucket<T> {
        private Bucket<T> nextBucket;
        private HashNode<T> rootNode;

        /**
         * Get the bucket at i.
         * @param i index of the entry
         * @return the bucket at the index
         */
        public Bucket<T> getIndex(int i) {
            return (Bucket<T>) getIndex(i, this);
        }

        /**
         * Add a node
         * @param value the node to add
         * @return 1 if collision, else 0.
         */
        public int addNode(HashNode<T> value) {
            if (rootNode == null) {
                this.rootNode = value;
                return 0;
            }

            long hashcode = Hash.hashCode(value.getKey());

            HashNode<T> current = rootNode;
            while (current != null) {
                if (Hash.hashCode(current.getKey()) == hashcode) {
                    if (current.getKey().equals(value.getKey())) {
                        throw new DuplicatedKeyException("Duplicated key");
                    }

                    current.addSeparateChaining(value);
                    return 1;
                }

                if (current.getNext() != null){
                    current = current.getNext();
                }else{
                    break;
                }
            }

            if (current != null){
                current.setNext(value);
            }else throw new RuntimeException("Current was null during add procedure!");

            return 0;
        }

        /**
         * Get the bucket at i.
         * @param i index of the entry
         * @return the bucket at the index
         */
        public static Bucket<?> getIndex(int i, Bucket<?> bucket) {
            int tempI = 0;
            Bucket current = bucket;

            while (tempI < i) {
                if (current.nextBucket == null) {
                    throw new ArrayIndexOutOfBoundsException(i + " is outside bounds of " + tempI);
                }

                current = current.nextBucket;
                tempI++;
            }

            return current;
        }

        /**
         * Remove the node
         * @param key
         */
        public void remove(String key) {
            long hashcode = Hash.hashCode(key);

            HashNode<T> last = null;
            HashNode<T> inspect = rootNode;

            if (rootNode == null){
                throw new InexistentKeyException("No keys found");
            }

            //If the node is the root
            if (rootNode.getKey().equals(key)) {

                //if the root contains chains
                if (rootNode.getSeparateChaining() != null) {

                    //Move them outside the node
                    addNode(rootNode.getSeparateChaining());
                }

                //remove the node and set the new root node.
                rootNode = rootNode.getNext();
            }

            //While there is something to inspect.
            while (inspect != null) {

                //if the object has the correct hashcode.
                if (Hash.hashCode(inspect.getKey()) == hashcode) {

                    //if the object has the same key.
                    if (inspect.getKey().equals(key)) {

                        //if a match
                        removeFromEntries(last, inspect);
                        return;
                    } else {

                        //if correct hash but wrong key
                        if (inspect.getSeparateChaining() != null) {

                            //inspect the chain
                            last = inspect;
                            inspect = inspect.getSeparateChaining();
                            removeFromChain(key, inspect);
                            return;
                        }

                        //if it is the correct but the wrong key, and there are not more chains to search.
                        throw new InexistentKeyException("No keys found");
                    }
                }

                last = inspect;
                inspect = inspect.getNext();
            }
        }

        /**
         * Remove a node from the chain
         * @param key the key to remove
         * @param current the current node
         */
        private void removeFromChain(String key, HashNode<T> current) {
            HashNode<T> last = current;
            HashNode<T> node = current.getSeparateChaining();

            while (node != null) {
                if (node.getKey().equals(key)) {
                    last.setSeparateChaining(node.getSeparateChaining());
                    node.setSeparateChaining(null);
                }

                last = node;
                node = node.getSeparateChaining();
            }
        }

        /**
         * Remove node from linked node tree.
         * @param last last node
         * @param current current node
         */
        private void removeFromEntries(HashNode<T> last, HashNode<T> current) {
            if (current.getSeparateChaining() != null) {
                addNode(current.getSeparateChaining());
                current.setNext(null);
            }

            if (last == null){
                rootNode = current.getNext();
            }else{
                last.setNext(current.getNext());
                current.setNext(null);
            }
        }
    }
}
