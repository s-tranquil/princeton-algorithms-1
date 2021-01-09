import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    private static class TString {
        public String stringValue;

        public TString(String value) {
            stringValue = value;
        }
    }

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String content = StdIn.readString();    // input file
        String[] splitted = content.split(" ");

        RandomizedQueue<TString> queue = new RandomizedQueue<>();
        for (int i = 0; i < k; i++) {
            queue.enqueue(new TString(splitted[i]));
        }

        Iterator<TString> iterator = queue.iterator();
        do {
            StdOut.println(iterator.next().stringValue);
        }
        while (iterator.hasNext());
    }
}
