import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node<Item> {
        Node<Item> next;
        Node<Item> prev;
        Item value;
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        // constructor
        public DequeIterator(Node<Item> first) {
            this.current = first;
            // initialize cursor
        }

        // Checks if the next element exists
        public boolean hasNext() {
            return this.current != null;
        }

        // moves the cursor/iterator to next element
        public Item next() {
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Node<Item> oldCurrent = this.current;
            this.current = oldCurrent.next;
            return oldCurrent.value;
        }

        // Used to remove an element. Implement only if needed
        public void remove() {
            // Default throws UnsupportedOperationException.
        }
    }

    private int n;
    private Node<Item> first;
    private Node<Item> last;

    // construct an empty deque
    public Deque() {
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    private void addFirstNode(Item item) {
        Node<Item> node = new Node<Item>();
        node.value = item;
        first = node;
        last = node;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == 0) {
            addFirstNode(item);
        }
        else {
            Node<Item> oldFirst = first;

            first = new Node<Item>();
            first.next = oldFirst;
            first.value = item;
            oldFirst.prev = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == 0) {
            addFirstNode(item);
        }
        else {
            Node<Item> oldLast = last;

            last = new Node<Item>();
            last.prev = oldLast;
            last.value = item;
            oldLast.next = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (n == 0) {
            throw new java.util.NoSuchElementException();
        }

        if (--n == 0) {
            Node<Item> oldFirst = first;
            first = null;
            last = null;
            return oldFirst.value;
        }
        else {
            Node<Item> oldFirst = first;

            first = oldFirst.next;
            first.prev = null;
            return oldFirst.value;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (n == 0) {
            throw new java.util.NoSuchElementException();
        }

        if (--n == 0) {
            Node<Item> oldFirst = first;
            first = null;
            last = null;
            return oldFirst.value;
        }
        else {
            Node<Item> oldLast = last;

            last = oldLast.prev;
            last.next = null;
            return oldLast.value;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(this.first);
    }

    private static class TInt {
        public int intValue;

        public TInt(int value) {
            intValue = value;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<TInt> dq = new Deque<TInt>();
        dq.addFirst(new TInt(1));
        dq.addFirst(new TInt(2));
        dq.addFirst(new TInt(3));
        dq.addLast(new TInt(0));
        StdOut.println(dq.size());

        Iterator<TInt> iterator = dq.iterator();

        StdOut.println(
                iterator.next().intValue + " " +
                        iterator.next().intValue + " " +
                        iterator.next().intValue + " " +
                        iterator.next().intValue
        );
        StdOut.println(iterator.hasNext());

        StdOut.println(
                dq.removeLast().intValue + " " +
                        dq.removeFirst().intValue + " " +
                        dq.removeFirst().intValue + " " +
                        dq.removeFirst().intValue
        );
        StdOut.println(dq.isEmpty());

    }

}
