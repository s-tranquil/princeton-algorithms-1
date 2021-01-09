import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        for (int i = 0; i < k; i++) {
            queue.enqueue(StdIn.readString());
        }

        Iterator<String> iterator = queue.iterator();
        do {
            StdOut.println(iterator.next());
        }
        while (iterator.hasNext());
    }
}
