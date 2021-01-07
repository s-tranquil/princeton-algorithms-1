public class Deque<Item> implements Iterable<Item> {
    private class Node<Item> {
        Node<Item> next;
        Node<Item> prev;
        Item value;
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private Deque<Item> value;

        // constructor
        public DequeIterator<Item>(
        Deque<Item> value)

        {
            this.value = value;
            // initialize cursor
        }

        // Checks if the next element exists
        public boolean hasNext() {
            return !this.value.isEmpty();
        }

        // moves the cursor/iterator to next element
        public Item next() {
            return this.value.removeFirst();
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

    private void addFirstNode() {
        Node<Item> node = new Node<Item>();
        node.value = item;
        first = last = node;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == 0) {
            addFirstNode();
        } else {
            Node<Item> oldFirst = first;

            first = new Node<Item>();
            first.prev = oldFirst;
            first.value = item;
            oldFirst.next = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == 0) {
            addFirstNode();
        } else {
            Node<Item> oldLast = last;

            last = new Node<Item>();
            last.next = oldLast;
            last.value = item;
            oldLast.prev = last;
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
            first = last = null;
            return oldFirst;
        } else {
            Node<Item> oldFirst = first;

            first = oldFirst.prev;
            first.next = null;
            return oldFirst;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (n == 0) {
            throw new java.util.NoSuchElementException();
        }

        if (--n == 0) {
            Node<Item> oldFirst = first;
            first = last = null;
            return oldFirst;
        } else {
            Node<Item> oldLast = last;

            last = oldLast.next;
            last.prev = null;
            return oldLast;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(this);
    }

    // unit testing (required)
    public static void main(String[] args) {
        public

    }

}
