import java.util.TreeSet;

import apple.laf.JRSUIUtils.Tree;

public class UnionFind {
    private int[] uf;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        uf = new int[N];

        for (int i = 0; i < N; i++) {
            uf[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        int r = find(v);
        return Math.abs(uf[r]);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return uf[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        if (find(v1) == find(v2)) {
            return true;
        }
        return false;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v >= uf.length) {
            throw new IllegalArgumentException("Invalid input: " + v);
        }

        int root = v;
        while (uf[root] >= 0) {
            root = parent(root);
        }

        while (uf[v] >= 0) {
            uf[v] = root;
            v = parent(v);
        }
        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int rootV1 = find(v1);
        int rootV2 = find(v2);

        // if not connect 
        // same size 
        if (!connected(v1, v2)) {
            if (sizeOf(v1) <= sizeOf(v2)) {
                uf[rootV2] = -(sizeOf(v1) + sizeOf(v2));
                uf[rootV1] = rootV2;
            } else {
                uf[rootV1] = -(sizeOf(v1) + sizeOf(v2));
                uf[rootV2] = rootV1;
            }
        }
    }

}
