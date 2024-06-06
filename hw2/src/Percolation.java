import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private int opened;
    private int length;
    private int totalSites;
    private boolean[] isOpen;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufFull;
    
    public Percolation(int N) {
        totalSites = N * N;
        length = N;
        opened = 0;
        uf = new WeightedQuickUnionUF(totalSites + 2); // top & bottom virtual, check percolation
        ufFull = new WeightedQuickUnionUF(totalSites + 2); // check if it's full
        isOpen = new boolean[totalSites];
    }

    private int xyTo1D(int row, int col) {
        if (row >= 0 && row < length && col >= 0 && col < length) {
            return row*length + col;
        }
        return -1;
    }

    public void open(int row, int col) {
        int current = xyTo1D(row, col);
        if (current == -1 || isOpen[current]) {
            return;
        }
        isOpen[current] = true;
        opened++;
         
        // connect to top virtual point 
        if (row == 0) {
            uf.union(current, totalSites); 
            ufFull.union(current, totalSites);
        }

        // connect to bottom virtual point 
        if (row == length - 1) {
            uf.union(current, totalSites + 1);
        }
        
        int up = xyTo1D(row - 1, col);
        int down = xyTo1D(row + 1, col);
        int left = xyTo1D(row, col - 1);
        int right = xyTo1D(row, col + 1);
        
        if (up != -1 && isOpen[up]) {
            uf.union(current, up);
            ufFull.union(current, up);
        }
        if (down != -1 && isOpen[down]) {
            uf.union(current, down);
            ufFull.union(current, down);
        }
        if (left != -1 && isOpen[left]) {
            uf.union(current, left);
            ufFull.union(current, left);
        }
        if (right != -1 && isOpen[right]) {
            uf.union(current, right);
            ufFull.union(current, right);
        }
    }

    public boolean isOpen(int row, int col) {
        return isOpen[xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        int current = xyTo1D(row, col);
        return current != -1 && ufFull.connected(current, totalSites);
    }

    public int numberOfOpenSites() {
        return opened;
    }

    // only one uf will cause the problem of backwash once percolation is true, so separate uf and ufFull to tackle each problem
    public boolean percolates() {
        return uf.connected(totalSites, totalSites + 1);
    }

}
