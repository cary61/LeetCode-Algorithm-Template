class SegmentTree_Sum_Add {
    
    static final int DEFAULT_VALUE = 0;
    
    class Node {
        int sum = DEFAULT_VALUE;
        Node lc, rc;
        int lazyAdd;
    }
    
    int LOWERBOUND;
    int UPPERBOUND;
    Node root;
    
    public SegmentTree_Sum_Add() {
        this.LOWERBOUND = Integer.MIN_VALUE;
        this.UPPERBOUND = Integer.MAX_VALUE;
        this.root = new Node();
    }
    
    public SegmentTree_Sum_Add(int LOWERBOUND, int UPPERBOUND) {
        this.LOWERBOUND = LOWERBOUND;
        this.UPPERBOUND = UPPERBOUND;
        this.root = new Node();
    }
    
    public SegmentTree_Sum_Add(int[] arr) {
        this.LOWERBOUND = 0;
        this.UPPERBOUND = arr.length - 1;
        this.root = new Node();
        build(arr, root, LOWERBOUND, UPPERBOUND);
    }
    
    public void set(int idx, int val) {
        set(idx, val, root, LOWERBOUND, UPPERBOUND);
    }
    
    public void add(int idx, int val) {
        add(idx, val, root, LOWERBOUND, UPPERBOUND);
    }
    
    public void multiply(int idx, int val) {
        multiply(idx, val, root, LOWERBOUND, UPPERBOUND);
    }
    
    public void add(int l, int r, int val) {
        add(l, r, val, root, LOWERBOUND, UPPERBOUND);
    }
    
    public int get(int idx) {
        return get(idx, root, LOWERBOUND, UPPERBOUND);
    }
    
    public int sum(int l, int r) {
        return sum(l, r, root, LOWERBOUND, UPPERBOUND);
    }



    // Internal Implementations



    void set(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.sum = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   set(idx, val, node.lc, s, c);
        else            set(idx, val, node.rc, c + 1, t);
        node.sum = node.lc.sum + node.rc.sum;
    }

    void add(int idx, int val, Node node, int s, int t) {
        node.sum += val;
        if (s == t) {
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   add(idx, val, node.lc, s, c);
        else            add(idx, val, node.rc, c + 1, t);
    }

    void multiply(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.sum *= val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   multiply(idx, val, node.lc, s, c);
        else            multiply(idx, val, node.rc, c + 1, t);
        node.sum = node.lc.sum + node.rc.sum;
    }

    void add(int l, int r, int val, Node node, int s, int t) {
        if (l <= s && t <= r) {
            node.sum += (t - s + 1) * val;
            if (s != t) {
                node.lazyAdd += val;
            }
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (l <= c)     add(l, r, val, node.lc, s, c);
        if (c < r)      add(l, r, val, node.rc, c + 1, t);
        node.sum = node.lc.sum + node.rc.sum;
    }

    int get(int idx, Node node, int s, int t) {
        if (s == t) {
            return node.sum;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   return get(idx, node.lc, s, c);
        else            return get(idx, node.rc, c + 1, t);
    }

    int sum(int l, int r, Node node, int s, int t) {
        if (l <= s && t <= r) {
            return node.sum;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        int ret = 0;
        if (l <= c) ret = sum(l, r, node.lc, s, c);
        if (c < r)  ret += sum(l, r, node.rc, c + 1, t);
        return ret;
    }

    void build(int[] arr, Node node, int s, int t) {
        if (s == t) {
            node.sum = arr[s];
            return;
        }
        int c = (s & t) +((s ^ t) >> 1);
        node.lc = new Node();
        node.rc = new Node();
        build(arr, node.lc, s, c);
        build(arr, node.rc, c + 1, t);
        node.sum = node.lc.sum + node.rc.sum;
    }

    void pushDown(Node node, int s, int t, int c) {
        if (node.lazyAdd != 0) {
            node.lc.sum += (c - s + 1) * node.lazyAdd;
            node.lc.lazyAdd += node.lazyAdd;

            node.rc.sum += (t - c) * node.lazyAdd;
            node.rc.lazyAdd += node.lazyAdd;

            node.lazyAdd = 0;
        }
    }
}
