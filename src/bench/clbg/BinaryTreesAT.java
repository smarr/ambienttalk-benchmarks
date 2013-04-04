package bench.clbg;

import java.io.InputStream;

import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;

import at.bench.BenchIAT;
import edu.vub.at.exceptions.InterpreterException;
import edu.vub.at.objects.ATAbstractGrammar;

public class BinaryTreesAT extends Benchmark {
	private BenchIAT          binaryTreeIAT;
	private ATAbstractGrammar binaryTrees;
	
	@Override
	protected void setUp() {
		// nothing for AT :(
	}
	
	public void timeBinaryTrees(int reps) {
		try {
			InputStream strm;

			binaryTreeIAT = BenchIAT.create(new String[] { "7" });
			strm = BinaryTreesAT.class
					.getResourceAsStream("/bench/clbg/binarytrees.at");
			binaryTrees = binaryTreeIAT.parse("binarytrees.at", strm);
			
			binaryTreeIAT.evaluate(binaryTrees);
			binaryTreeIAT.stopProcessing();
		} catch (InterpreterException e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		CaliperMain.main(BinaryTreesAT.class, args);
	}
}
