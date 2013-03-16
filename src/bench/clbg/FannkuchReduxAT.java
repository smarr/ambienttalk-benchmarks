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
		try {
			InputStream strm;
			
			fannkuchIAT = BenchIAT.create(new String[] { "5" });
			strm = FannkuchReduxAT.class.getResourceAsStream("/bench/clbg/fannkuch-redux.at");
			fannkuch   = fannkuchIAT.parse("fannkuchredux.at", strm);
		} catch (InterpreterException e) {
			e.printStackTrace();
		}
	}
	
	public void timeFannkuch(int reps) {
		for (int i = 0; i < reps; i++) {
			try {
				fannkuchIAT.evaluate(fannkuch);
			} catch (InterpreterException e) { e.printStackTrace();  }
		}
	}
	
	public static void main(String[] args) {
		CaliperMain.main(FannkuchReduxAT.class, args);
	}
}
