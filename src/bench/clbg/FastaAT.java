package bench.clbg;

import java.io.InputStream;

import at.bench.BenchIAT;

import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;

import edu.vub.at.exceptions.InterpreterException;
import edu.vub.at.objects.ATAbstractGrammar;

public class FastaAT extends Benchmark {
	
	private BenchIAT          fastaIAT;
	private ATAbstractGrammar fasta;
	
	@Override
	protected void setUp() {
		try {
			InputStream strm;
			
			fastaIAT = BenchIAT.create(new String[] { "1000" });
			strm = FastaAT.class.getResourceAsStream("/bench/clbg/fasta.at");
			fasta   = fastaIAT.parse("fasta.at", strm);
		} catch (InterpreterException e) {
			e.printStackTrace();
		}
	}
	
	public void timeFasta(int reps) {
		for (int i = 0; i < reps; i++) {
			try {
				fastaIAT.evaluate(fasta);
			} catch (InterpreterException e) { e.printStackTrace();  }
		}
	}
	
	public static void main(String[] args) {
		CaliperMain.main(FastaAT.class, args);
	}
}
