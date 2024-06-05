import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private boolean[][] isOpen;
    private boolean[][] isFull;
    private int size;
    private int opened;

    public Percolation(int N) {
        size = N;
        isOpen = new boolean[N][N];
        isFull = new boolean[N][N];
        
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                isOpen[row][col] = false;
                isFull[row][col] = false;
            }
        }
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            isOpen[row][col] = true;
            opened++;

            if (row == 0) {
                isFull[row][col] = true;
            }
            if (isFull(row, col) || isFull(row+1, col) || isFull(row-1, col) || isFull(row, col+1) || isFull(row, col-1)) {
                isFull[row][col] = true;
                propagateFull(row, col);
            }
        }
    }

    private void propagateFull(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return;
        }

        if (row +1 <= size-1 && isNotFull(row +1, col)) {
            isFull[row + 1][col] = true;
            propagateFull(row + 1, col);
        } 
        if (col - 1 >= 0 && isNotFull(row, col - 1)) {
            isFull[row][col - 1] = true;
            propagateFull(row, col - 1);
        }

        if (col + 1 <= size -1 && isNotFull(row, col + 1)) {
            isFull[row][col + 1] = true;
            propagateFull(row, col + 1);
            
        }
        if (row - 1 >= 0 && isNotFull(row - 1, col)) {
            isFull[row - 1][col] = true;
            propagateFull(row - 1, col);
        }
    }

    public boolean isNotFull(int row, int col) {
        return isOpen(row, col) && !isFull(row, col);
    }

    public boolean isOpen(int row, int col) {
        if (row >= 0 || row < size && col >= 0 || col < size) {
            return isOpen[row][col];
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            return isFull[row][col];
        }
        return false;
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
        if (size == 1 && isFull(size-1, size-1)) {
            return true;
        } else {
            for (int i = 0; i < size-1; i++) {
                if (isFull[size - 1][i]) {
                    return true;
                }
            }
        }
        return false;
    }
}
