// Starter code for LP5

package mxs170043;

import rbk.Graph;
import rbk.Graph.Vertex;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;
import rbk.Graph.Timer;

import rbk.BinaryHeap.Index;
import rbk.BinaryHeap.IndexedHeap;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.File;

public class MST extends GraphAlgorithm<MST.MSTVertex> {
    String algorithm;
    public long wmst;
    List<Edge> mst;
    MST(Graph g) {
        super(g, new MSTVertex((Vertex) null));
    }

    public static class MSTVertex implements Index, Comparable<MSTVertex>, Factory {
        MSTVertex parent;
        boolean seen;
        int rank;

        MSTVertex(Vertex u) {
            parent = this;
            seen = false;
            rank = 0;

        }


        MSTVertex(MSTVertex u) {  // for prim2
        }

        public MSTVertex make(Vertex u) {
            return new MSTVertex(u);
        }

        public void putIndex(int index) {

        }

        public int getIndex() {
            return 0;
        }

        public int compareTo(MSTVertex other) {
            return 0;
        }
    public void union(MSTVertex u) {
        if (this.rank > u.rank) {
            u.parent = this.parent;
        } else if (this.rank < u.rank) {
            this.parent = u;
        } else {
            u.parent = this.parent;
            this.rank++;
        }
    }



}

    public long kruskal() {
        algorithm = "Kruskal";
        Edge[] edgeArray = g.getEdgeArray();
        mst = new LinkedList<>();
        wmst = 0;
        Arrays.sort(edgeArray,(a,b)->a.getWeight()-b.getWeight());//replace with comparator
        for(Edge e:edgeArray){
            MSTVertex u=find(get(e.fromVertex()));
            MSTVertex v=find(get(e.toVertex()));
            if(u!=v){
                u.union(v);
                mst.add(e);
                wmst+=e.getWeight();
            }
        }
        return wmst;
    }
    private MSTVertex find(MSTVertex u) {
        while (u.parent!=u){
            u=u.parent;
        }
        return u;
    }

    public long prim3(Vertex s) {
        algorithm = "indexed heaps";
        mst = new LinkedList<>();
        wmst = 0;
        IndexedHeap<MSTVertex> q = new IndexedHeap<>(g.size());
        return wmst;
    }

    public long prim2(Vertex s) {
        algorithm = "PriorityQueue<Vertex>";
        mst = new LinkedList<>();
        wmst = 0;
        PriorityQueue<MSTVertex> q = new PriorityQueue<>();
        return wmst;
    }

    public long prim1(Vertex s) {
        algorithm = "PriorityQueue<Edge>";
        mst = new LinkedList<>();
        wmst = 0;
        PriorityQueue<Edge> q = new PriorityQueue<>();
        return wmst;
    }

    public static MST mst(Graph g, Vertex s, int choice) {
        MST m = new MST(g);
        switch(choice) {
            case 0:
                m.kruskal();
                break;
            case 1:
                m.prim1(s);
                break;
            case 2:
                m.prim2(s);
                break;
            default:
                m.prim3(s);
                break;
        }
        return m;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        int choice = 0;  // Kruskal
        if (args.length == 0 || args[0].equals("-")) {
            in = new Scanner(System.in);
        } else {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        }

        if (args.length > 1) { choice = Integer.parseInt(args[1]); }

        Graph g = Graph.readGraph(in);
        Vertex s = g.getVertex(1);

        Timer timer = new Timer();
        MST m = mst(g, s, choice);
        System.out.println(m.algorithm + "\n" + m.wmst);
        System.out.println(timer.end());
    }
}