package bench.clbg;

import java.io.InputStream;

import at.bench.BenchIAT;

import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;

import edu.vub.at.exceptions.InterpreterException;
import edu.vub.at.objects.ATAbstractGrammar;

public class NbodyAT extends Benchmark {
	
	private BenchIAT          nbodyIAT;
	private ATAbstractGrammar nbody;
	
	@Override
	protected void setUp() {
//		try {
//			InputStream strm;
//			
//			nbodyIAT = BenchIAT.create(new String[] { "1000" });
//			strm = NbodyAT.class.getResourceAsStream("/bench/clbg/nbody.at");
//			nbody   = nbodyIAT.parse("nbody.at", strm);
//		} catch (InterpreterException e) {
//			e.printStackTrace();
//		}
	}
	
	public void timeNbody(int reps) {
		try {
			InputStream strm;
				
			nbodyIAT = BenchIAT.create(new String[] { "300" });
			strm = NbodyAT.class.getResourceAsStream("/bench/clbg/nbody.at");
			nbody   = nbodyIAT.parse("nbody.at", strm);
				
			nbodyIAT.evaluate(nbody);
			nbodyIAT.stopProcessing();
		} catch (InterpreterException e) { e.printStackTrace();  }
	}
	
	public void timeNbodyDoubleParse(int reps) {
		try {
			InputStream strm;
				
			nbodyIAT = BenchIAT.create(new String[] { "300" });
			strm = NbodyAT.class.getResourceAsStream("/bench/clbg/nbody.at");
			nbody   = nbodyIAT.parse("nbody.at", strm);
			
			// double it to see how relevant it is to get parsing out of the numbers
			nbodyIAT = BenchIAT.create(new String[] { "300" });
			strm = NbodyAT.class.getResourceAsStream("/bench/clbg/nbody.at");
			nbody   = nbodyIAT.parse("nbody.at", strm);
				
			nbodyIAT.evaluate(nbody);
			nbodyIAT.stopProcessing();
		} catch (InterpreterException e) { e.printStackTrace();  }
	}
	
	public static void main(String[] args) {
		CaliperMain.main(NbodyAT.class, args);
	}
}
