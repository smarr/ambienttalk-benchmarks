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
		// nothing for AT :(
	}
	
	public void timeFasta(int reps) {
		try {
			InputStream strm;
			
			fastaIAT = BenchIAT.create(new String[] { "1000" });
			strm = FastaAT.class.getResourceAsStream("/bench/clbg/fasta.at");
			fasta   = fastaIAT.parse("fasta.at", strm);
			fastaIAT.evaluate(fasta);
			fastaIAT.stopProcessing();
		} catch (InterpreterException e) { e.printStackTrace(); }
	}
	
	public void timeFastaDoubleParse(int reps) {
		try {
			InputStream strm;
			
			fastaIAT = BenchIAT.create(new String[] { "1000" });
			strm = FastaAT.class.getResourceAsStream("/bench/clbg/fasta.at");
			fasta   = fastaIAT.parse("fasta.at", strm);
			
			// double it to see how relevant it is to get parsing out of the numbers
			fastaIAT = BenchIAT.create(new String[] { "1000" });
			strm = FastaAT.class.getResourceAsStream("/bench/clbg/fasta.at");
			fasta   = fastaIAT.parse("fasta.at", strm);
			
			fastaIAT.evaluate(fasta);
			fastaIAT.stopProcessing();
		} catch (InterpreterException e) { e.printStackTrace(); }
	}

	
	public static void main(String[] args) {
		CaliperMain.main(FastaAT.class, args);
	}
}
