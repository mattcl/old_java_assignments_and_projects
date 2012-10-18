import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Matt Chun-Lum
 *
 */
public class ChunkList<E> extends AbstractCollection<E> implements Collection<E> {
    private Chunk head = null, tail = null;
    private int size = 0;
    
    @Override
    public boolean add(E e) {
        if(tail == null || tail.isFull()) addChunk();
        return tail.add(e);
    }
    
    @Override
    public Iterator<E> iterator() {
        return new ChunkIterator();
    }

    @Override
    public int size() {
        return size;
    }
    
    // ------------------- Private ------------------- //
    
    /*
     * adds a chunk to the list. If the ChunkList is empty (the tail is null), adds the
     * chunk as the initial element in the list.
     */
    private void addChunk() {
        Chunk chunk = new Chunk();
        if(tail == null) {
            tail = chunk;
            head = chunk;
        } else {
            tail.next = chunk;
            tail = chunk;
        }
    }
    
    /*
     * private inner class for iterator
     */
    private class ChunkIterator implements Iterator<E> {
        private int index = 0;
        private Chunk next = head;
        private Chunk previous = null;
        
        // returns whether or not the iterator has a next element
        public boolean hasNext() {
            if(next != null && (next.next != null || index < next.size())) return true;
            return false;
        }
        
        // returns the next element
        public E next() {
            if(index >= next.size()) {
                previous = next;
                next = next.next;
                index = 0;
            }
            return next.data[index++];
        }
        
        // removes the last element returned by the iterator
        public void remove() {
            next.remove(--index);
            if(next.isEmpty()) {
                if(previous == null)
                    head = next.next;
                else
                    previous.next = next.next;
                Chunk temp = next.next;
                next = null;
                next = temp;
                index = 0;
                if(next == null) tail = previous;
                else if(next.next == null) tail = next;
            }
        }
    }
    
    /*
     * private inner class Chunk stores the elements for the Chunk list
     */
    private class Chunk {
        private static final int ARRAY_SIZE = 8;
        E[] data;
        int chunkSize;
        Chunk next;
        
        Chunk() {
            data = (E[]) new Object[ARRAY_SIZE];
            chunkSize = 0;
            next = null;
        }
        
        /*
         * Adds an element to the internal array. Returns true if the element was
         * added, false if the internal array is full.
         */
        boolean add(E e) {
            if(!isFull()) {
                size++;
                data[chunkSize++] = e;
                return true;
            }
            return false;
        }
        
        /*
         * returns true if the internal array is full
         */
        boolean isFull() {
            return chunkSize == ARRAY_SIZE;
        }
        
        /*
         * returns true if the internal array is empty
         */
        boolean isEmpty() {
            return chunkSize == 0;
        }
        
        /*
         * returns the size of the chunk
         */
        int size() {
            return chunkSize;
        }
        
        /*
         * removes the element at the specified index and shifts the contents of the
         * array if necessary
         */
        void remove(int index) {
            data[index] = null;
            int i;
            for(i = index; i < chunkSize-1; i++)
                data[i] = data[i + 1];
            data[i] = null;
            chunkSize--;
            size--;
        }
        
        /*
         * removes the element, if it exists, from the array.
         * 
         * not called in this implementation (it would be slower)
         */
        void remove(E e) {
            for(int i = 0; i < chunkSize; i++)
                if(e.equals(data[i])) {
                    remove(i);
                    break;
                }
        }
    }
}
