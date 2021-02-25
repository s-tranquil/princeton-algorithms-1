/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        public SearchNode prev;
        public int moves;
        public Board board;

        public SearchNode(SearchNode prev, int moves, Board board) {
            this.prev = prev;
            this.moves = moves;
            this.board = board;
        }

        private int getWeight() {
            return this.board.manhattan() + moves;
        }

        public int compareTo(SearchNode o) {
            return Integer.compare(this.getWeight(), o.getWeight());
        }
    }


    private int _moves;
    private Iterable<Board> _solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.solve(initial);
    }

    private void solve(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<SearchNode> queue = new MinPQ<SearchNode>();
        queue.insert(new SearchNode(null, 0, initial));

        MinPQ<SearchNode> twinQueue = new MinPQ<SearchNode>();
        twinQueue.insert(new SearchNode(null, 0, initial.twin()));

        SearchNode min = null;
        SearchNode minTwin = null;
        do {
            min = queue.delMin();
            addNeighbours(queue, min);

            minTwin = twinQueue.delMin();
            addNeighbours(twinQueue, minTwin);
        }
        while (!min.board.isGoal() && !minTwin.board.isGoal());

        if (min.board.isGoal()) {
            _moves = min.moves;

            List<Board> solution = new ArrayList<Board>();
            SearchNode current = min;
            do {
                solution.add(current.board);
                current = current.prev;
            } while (current != null);
            Collections.reverse(solution);
            _solution = solution;

        }
        else {
            _moves = -1;
            _solution = null;
        }
    }

    private void addNeighbours(MinPQ<SearchNode> queue, SearchNode parentNode) {
        Iterable<Board> neighbours = parentNode.board.neighbors();
        neighbours.forEach(n -> {
            boolean isDuplicate = false;
            SearchNode current = parentNode; // В current ссылка на объект
            do {
                if (current.board.equals(n)) {
                    isDuplicate = true;
                }
                current = current.prev;
            } while (current != null);
            // queue.forEach(q -> {
            //     if (q.board.equals(n)) {
            //         equals.set(true);
            //     }
            // });

            if (!isDuplicate) {
                queue.insert(new SearchNode(parentNode, parentNode.moves + 1, n));
            }
        });
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return _solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return _moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return _solution;
    }

    // test client (see below)
    public static void main(String[] args) {

    }
}
