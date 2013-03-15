package bench.clbg;

/**
 * The Computer Language Benchmarks Game
 * http://benchmarksgame.alioth.debian.org/
 * contributed by Klaus Friedel
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;

public class threadring extends Benchmark {

	private static final int MAX_NODES = 503;
    private static final int MAX_THREADS = 503;

    private ExecutorService executor;

	
	@Override
	protected void setUp() {
		setN(1000);
	}

	public void threadringBenchmark() {
		Node node = start(MAX_NODES);
        node.sendMessage(new TokenMessage(0));
        try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void timeThreadRing(int reps) {
		for (int i = 0; i < reps; i++) {
			threadringBenchmark();
		}
	}

	private int N;
	protected void setN(int n) {
		this.N = n;
	}

	public static void main(String[] args) {
		CaliperMain.main(threadring.class, args);
	}
	    

//    public static void main(String[] args) {
//        threadring ring = new threadring();
//        ring.setN(Integer.parseInt(args[0]));
//        Node node = ring.start(MAX_NODES);
//        node.sendMessage(new TokenMessage(1,0));
//    }
    
    public Node start(int n) {
        Node[] nodes = spawnNodes(n);
        connectNodes(n, nodes);
        return nodes[0];
    }

    private Node[] spawnNodes(int n) {
        executor = Executors.newFixedThreadPool(MAX_THREADS);
        Node[] nodes = new Node[n+1];
        for (int i = 0; i < n ; i++) {
            nodes[i] = new Node(i+1, null);
        }
        return nodes;
    }
    
    public void connectNodes(int n, Node[] nodes) {
        nodes[n] = nodes[0];
        for (int i=0; i<n; i++) {
            nodes[i].connect(nodes[i+1]);
        }
    }

    private static class TokenMessage {
        private volatile int value;
        private boolean isStop;

        public TokenMessage(int value) {
            this.value = value;
        }
        
        public TokenMessage(int value, boolean isStop) {
            this.value = value;
            this.isStop = isStop;
        }
    }

    private class Node implements Runnable {
        private int nodeId;
        private Node nextNode;
        private BlockingQueue<TokenMessage> queue = new LinkedBlockingQueue<TokenMessage>();
        private boolean isActive = false;

        public Node(int id, Node nextNode) {
            this.nodeId = id;
            this.nextNode = nextNode;
        }

        public void connect(Node node) {
            this.nextNode = node;
            isActive = true;
        }

        public void sendMessage(TokenMessage m) {
            queue.add(m);
            executor.execute(this);
        }

        public void run() {
            if (isActive) {
                try {
                    TokenMessage m = queue.take();
                    if (m.isStop) {
                        int nextValue = m.value+1;
                        if (nextValue == MAX_NODES) {
                        	// System.out.println("last one");
                            executor.shutdown();                            
                        } else {
                            m.value = nextValue;
                            nextNode.sendMessage(m);
                        }
                        isActive = false;
                        // System.out.println("ending node " + nodeId);
                    } else {
                        if (m.value == N) {
                            System.out.println(nodeId);
                            nextNode.sendMessage(new TokenMessage(0, true));
                        } else {
                            m.value = m.value + 1;
                            nextNode.sendMessage(m);
                        }
                    }
                } catch (InterruptedException ie) {
                   ie.printStackTrace();
                }
            }
        }
    }
}
