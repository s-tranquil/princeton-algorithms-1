import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private Item[] items;
        private int[] shuffledIndices;
        private int currentIndex;

        // constructor
        public RandomizedQueueIterator(Item[] items, int n) {
            this.items = (Item[]) new Object[n];
            int[] availableIndices = new int[n];
            for (int i = 0; i < n; i++) {
                availableIndices[i] = i;
                this.items[i] = items[i];
            }
            StdRandom.shuffle(availableIndices);
            this.shuffledIndices = availableIndices;
            this.currentIndex = n - 1;
        }

        // Checks if the next element exists
        public boolean hasNext() {
            return this.currentIndex >= 0;
        }

        // moves the cursor/iterator to next element
        public Item next() {
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            int i = shuffledIndices[currentIndex];
            currentIndex = currentIndex - 1;

            return items[i];
        }

        // Used to remove an element. Implement only if needed
        public void remove() {
            // Default throws UnsupportedOperationException.
        }
    }


    private Item[] items;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        items = (Item[]) new Object[1];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == items.length) {
            resize(n * 2);
        }

        items[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) {
            throw new java.util.NoSuchElementException();
        }

        int rnd = StdRandom.uniform(n);
        Item current = items[rnd];
        for (int i = rnd; i < n; i++) {
            if (i == n - 1) {
                items[i] = null;
            }
            else {
                items[i] = items[i + 1];
            }
        }

        n = n - 1;
        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }

        return current;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) {
            throw new java.util.NoSuchElementException();
        }

        int rnd = StdRandom.uniform(n);
        Item current = items[rnd];
        return current;
    }

    private static class TInt {
        public int intValue;

        public TInt(int value) {
            intValue = value;
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>(items, n);
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<TInt> rq = new RandomizedQueue<>();
        StdOut.println(rq.isEmpty());

        rq.enqueue(new TInt(1));
        rq.enqueue(new TInt(2));
        rq.enqueue(new TInt(3));
        rq.enqueue(new TInt(4));
        StdOut.println(rq.size());
        StdOut.println("sample");
        StdOut.print(rq.sample().intValue);
        StdOut.print(rq.sample().intValue);

        Iterator<TInt> iterator1 = rq.iterator();
        Iterator<TInt> iterator2 = rq.iterator();
        StdOut.println("Iterator 1");
        StdOut.print(iterator1.next().intValue);
        StdOut.print(iterator1.next().intValue);
        StdOut.print(iterator1.next().intValue);
        StdOut.print(iterator1.next().intValue);
        StdOut.print(iterator1.hasNext());

        StdOut.println("dequeue");
        StdOut.print(rq.dequeue().intValue);
        StdOut.print(rq.dequeue().intValue);
        StdOut.print(rq.size());

        StdOut.println("Iterator 2");
        StdOut.print(iterator2.next().intValue);
        StdOut.print(iterator2.next().intValue);
        StdOut.print(iterator2.next().intValue);
        StdOut.print(iterator2.next().intValue);
        StdOut.print(iterator2.hasNext());
    }

}
