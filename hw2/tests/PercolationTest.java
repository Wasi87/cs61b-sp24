import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class PercolationTest {

    /**
     * Enum to represent the state of a cell in the grid. Use this enum to help you write tests.
     * <p>
     * (0) CLOSED: isOpen() returns true, isFull() return false
     * <p>
     * (1) OPEN: isOpen() returns true, isFull() returns false
     * <p>
     * (2) INVALID: isOpen() returns false, isFull() returns true
     *              (This should not happen! Only open cells should be full.)
     * <p>
     * (3) FULL: isOpen() returns true, isFull() returns true
     * <p>
     */
    private enum Cell {
        CLOSED, OPEN, INVALID, FULL
    }

    /**
     * Creates a Cell[][] based off of what Percolation p returns.
     * Use this method in your tests to see if isOpen and isFull are returning the
     * correct things.
     */
    private static Cell[][] getState(int N, Percolation p) {
        Cell[][] state = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = Cell.values()[open + full];
            }
        }
        return state;
    }

    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        // open sites at (r, c) = (0, 1), (2, 0), (3, 1), etc. (0, 0) is top-left
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void oneByOneTest() {
        int N = 1;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        Cell[][] expectedState = {
                {Cell.FULL}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    private void batchOpen(Percolation p, String[] drawing) {
        class Pos {
            int x;
            int y;

            public Pos(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
        Pos[] opens = new Pos[16];

        for (int y = 0; y < drawing.length; y++) {
            for (int x = 0; x < drawing.length; x++) {
                char c = drawing[y].charAt(x);
                if (c == '.') {
                    continue;
                }
                int n = c - '0';
                opens[n] = new Pos(x, y);
            }
        }

        for (Pos pos : opens) {
            if (pos == null) {
                break;
            }
            p.open(pos.y, pos.x);
        }
    }

    private void assertCellState(Percolation p, String[] drawing) {
        String[] actual = new String[drawing.length];

        for (int y = 0; y < drawing.length; y++) {
            actual[y] = new String();
            for (int x = 0; x < drawing.length; x++) {
                if (p.isOpen(y, x)) {
                    if (p.isFull(y, x)) {
                        actual[y] += '@';
                    } else {
                        actual[y] += '+';
                    }
                } else {
                    actual[y] += '.';
                }
            }
        }

        assertThat(String.join("\n", actual)).isEqualTo(String.join("\n", drawing));
    }

    @Test
    public void diagonalLeft() {
        Percolation p = new Percolation(2);

        batchOpen(p, new String[] {
            ".0",
            "1.",
        });
        assertCellState(p, new String[] {
            ".@",
            "+.",
        });
        assertThat(p.percolates()).isFalse();
    }
    
    @Test
    public void diagonalRight() {
        Percolation p = new Percolation(2);

        batchOpen(p, new String[] {
            "0.",
            ".1",
        });
        assertCellState(p, new String[] {
            "@.",
            ".+",
        });
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void testInitialGrid() {
        Percolation perc = new Percolation(3);
        String[] expected = {
            "...",
            "...",
            "..."
        };
        assertCellState(perc, expected);
    }

    @Test
    public void testSingleOpenSite() {
        Percolation perc = new Percolation(3);
        perc.open(1, 1);
        String[] expected = {
            "...",
            ".+.",
            "..."
        };
        assertCellState(perc, expected);
    }

    @Test
    public void testPercolation() {
        Percolation perc = new Percolation(3);
        perc.open(0, 0);
        perc.open(1, 0);
        perc.open(2, 0);
        assertThat(perc.percolates()).isTrue();

        String[] expected = {
            "@..",
            "@..",
            "@.."
        };
        assertCellState(perc, expected);
    }


    @Test
    public void testNoPercolation() {
        Percolation perc = new Percolation(3);
        perc.open(0, 0);
        perc.open(1, 0);
        assertThat(perc.percolates()).isFalse();

        String[] expected = {
            "@..",
            "@..",
            "..."
        };
        assertCellState(perc, expected);
    }

    @Test
    public void testBackwash() {
        Percolation perc = new Percolation(3);
        perc.open(0, 0);
        perc.open(1, 0);
        perc.open(2, 0);
        perc.open(2, 2);

        String[] expected = {
            "@..",
            "@..",
            "@.+"
        };
        assertCellState(perc, expected);
    }
}

