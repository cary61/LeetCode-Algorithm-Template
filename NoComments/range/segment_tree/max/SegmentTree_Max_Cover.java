class SegmentTree_Max_Cover {
    
    static final int DEFAULT_VALUE = Integer.MIN_VALUE;
    
    class Node {
        int max = DEFAULT_VALUE;
        Node lc, rc;
        int lazyCover;
        boolean updated = true;
    }
    
    int LOWERBOUND;
    int UPPERBOUND;
    Node root;
    
    public SegmentTree_Max_Cover() {
        this.LOWERBOUND = Integer.MIN_VALUE;
        this.UPPERBOUND = Integer.MAX_VALUE;
        this.root = new Node();
    }
    
    public SegmentTree_Max_Cover(int LOWERBOUND, int UPPERBOUND) {
        this.LOWERBOUND = LOWERBOUND;
        this.UPPERBOUND = UPPERBOUND;
        this.root = new Node();
    }
    
    public SegmentTree_Max_Cover(int[] arr) {
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
    
    public void cover(int l, int r, int val) {
        cover(l, r, val, root, LOWERBOUND, UPPERBOUND);
    }
    
    public int get(int idx) {
        return get(idx, root, LOWERBOUND, UPPERBOUND);
    }
    
    public int max(int l, int r) {
        return max(l, r, root, LOWERBOUND, UPPERBOUND);
    }



    // Internal Implementations



    void set(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.max = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   set(idx, val, node.lc, s, c);
        else            set(idx, val, node.rc, c + 1, t);
        node.max = Math.max(node.lc.max, node.rc.max);
    }

    void add(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.max += val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   add(idx, val, node.lc, s, c);
        else            add(idx, val, node.rc, c + 1, t);
        node.max = Math.max(node.lc.max, node.rc.max);
    }

    void multiply(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.max *= val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   multiply(idx, val, node.lc, s, c);
        else            multiply(idx, val, node.rc, c + 1, t);
        node.max = Math.max(node.lc.max, node.rc.max);
    }

    void cover(int l, int r, int val, Node node, int s, int t) {
        if (l <= s && t <= r) {
            node.max = val;
            if (s != t) {
                node.lazyCover = val;
                node.updated = false;
            }
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (l <= c)     cover(l, r, val, node.lc, s, c);
        if (c < r)      cover(l, r, val, node.rc, c + 1, t);
        node.max = Math.max(node.lc.max, node.rc.max);
    }

    int get(int idx, Node node, int s, int t) {
        if (s == t) {
            return node.max;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   return get(idx, node.lc, s, c);
        else            return get(idx, node.rc, c + 1, t);
    }

    int max(int l, int r, Node node, int s, int t) {
        if (l <= s && t <= r) {
            return node.max;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        int ret = DEFAULT_VALUE;
        if (l <= c) ret = max(l, r, node.lc, s, c);
        if (c < r)  ret = Math.max(ret, max(l, r, node.rc, c + 1, t));
        return ret;
    }

    void build(int[] arr, Node node, int s, int t) {
        if (s == t) {
            node.max = arr[s];
            return;
        }
        int c = (s & t) +((s ^ t) >> 1);
        node.lc = new Node();
        node.rc = new Node();
        build(arr, node.lc, s, c);
        build(arr, node.rc, c + 1, t);
        node.max = Math.max(node.lc.max, node.rc.max);
    }

    void pushDown(Node node, int s, int t, int c) {
        if (!node.updated) {
            node.lc.max = node.lazyCover;
            node.lc.lazyCover = node.lazyCover;
            node.lc.updated = false;

            node.rc.max = node.lazyCover;
            node.rc.lazyCover = node.lazyCover;
            node.rc.updated = false;

            node.updated = true;
        }
    }
}

