package bench.clbg;

import java.io.InputStream;

import at.bench.BenchIAT;

import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;

import edu.vub.at.exceptions.InterpreterException;
import edu.vub.at.objects.ATAbstractGrammar;

public class FannkuchReduxAT extends Benchmark {	
	
	private BenchIAT          fannkuchIAT;
	private ATAbstractGrammar fannkuch;
	
	@Override
	protected void setUp() {
		// nothing for AT
	}
	
	public void timeFannkuch(int reps) {
		try {
			InputStream strm;
			
			fannkuchIAT = BenchIAT.create(new String[] { "5" });
			strm = FannkuchReduxAT.class.getResourceAsStream("/bench/clbg/fannkuch-redux.at");
			fannkuch   = fannkuchIAT.parse("fannkuchredux.at", strm);
			
			fannkuchIAT.evaluate(fannkuch);
			fannkuchIAT.stopProcessing();
		} catch (InterpreterException e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		CaliperMain.main(FannkuchReduxAT.class, args);
	}
}
