package AliasMOP;

import at.bench.BenchIAT;

import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;

import edu.vub.at.exceptions.InterpreterException;
import edu.vub.at.objects.ATAbstractGrammar;
import edu.vub.at.objects.ATObject;
import edu.vub.at.objects.natives.NATNumber;

public class ExampleBench extends Benchmark {
	
	

	public void timeSimpleExecution(int reps) {
		long time = 0;
		for (int i = 0; i < reps; i++) {
			time += System.nanoTime();
		}
	}
	
	private ATAbstractGrammar ast;
	private BenchIAT iat;
	
	public void timeSimpleAdditionAmbientTalk(int reps) {
		for (int i = 0; i < reps; i++) {
			try {
				ATObject o = iat.evaluate(ast);
				
				// verify result
				NATNumber n = (NATNumber)o;
				long result = n.base_millisec();
				if (result != 4) { throw new UnknownError(); }				
			} /*fuckit*/ catch (InterpreterException e) {
				e.printStackTrace();
			}			
		}
	}
	
	
	@Override
	protected void setUp() {
		try {
			iat = BenchIAT.create();
			ast = iat.parse("1+3");			
		} /*fuckit*/ catch (InterpreterException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//new ExampleBench().setUp();
		CaliperMain.main(ExampleBench.class, args);
	}
}
