package bench.clbg;

import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;

/* The Computer Language Benchmarks Game
 http://benchmarksgame.alioth.debian.org/

 contributed by Jarkko Miettinen
 */

public class binarytrees extends Benchmark {

	private final static int minDepth = 4;

	private int n;

	protected void setN(int n) {
		this.n = n;
	}
	
	@Override
	protected void setUp() {
		setN(10);
	}
	
	public void timeBinaryTrees(int reps) {
		for (int i = 0; i < reps; i++) {
			benchmarkBinaryTrees();
		}
	}
	
	public void benchmarkBinaryTrees() {
		int maxDepth = (minDepth + 2 > n) ? minDepth + 2 : n;
		int stretchDepth = maxDepth + 1;

		int check = (TreeNode.bottomUpTree(0, stretchDepth)).itemCheck();
		System.out.println("stretch tree of depth " + stretchDepth
				+ "\t check: " + check);

		TreeNode longLivedTree = TreeNode.bottomUpTree(0, maxDepth);

		for (int depth = minDepth; depth <= maxDepth; depth += 2) {
			int iterations = 1 << (maxDepth - depth + minDepth);
			check = 0;

			for (int i = 1; i <= iterations; i++) {
				check += (TreeNode.bottomUpTree(i, depth)).itemCheck();
				check += (TreeNode.bottomUpTree(-i, depth)).itemCheck();
			}
			System.out.println((iterations * 2) + "\t trees of depth " + depth
					+ "\t check: " + check);
		}
		System.out.println("long lived tree of depth " + maxDepth
				+ "\t check: " + longLivedTree.itemCheck());

	}
	
	public static void main(String[] args) {
		CaliperMain.main(binarytrees.class, args);
	}

	/* public static void main(String[] args) {
		int n = 0;
		if (args.length > 0)
			n = Integer.parseInt(args[0]);
		binarytrees bt = new binarytrees();
		bt.setN(n);
		bt.benchmarkBinaryTrees();
	} */

	private static class TreeNode {
		private TreeNode left, right;
		private int item;

		TreeNode(int item) {
			this.item = item;
		}

		private static TreeNode bottomUpTree(int item, int depth) {
			if (depth > 0) {
				return new TreeNode(bottomUpTree(2 * item - 1, depth - 1),
						bottomUpTree(2 * item, depth - 1), item);
			} else {
				return new TreeNode(item);
			}
		}

		TreeNode(TreeNode left, TreeNode right, int item) {
			this.left = left;
			this.right = right;
			this.item = item;
		}

		private int itemCheck() {
			// if necessary deallocate here
			if (left == null)
				return item;
			else
				return item + left.itemCheck() - right.itemCheck();
		}
	}
}
