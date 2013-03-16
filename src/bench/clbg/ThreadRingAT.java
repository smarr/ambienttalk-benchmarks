package bench.clbg;

import java.io.InputStream;

import at.bench.BenchIAT;

import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;

import edu.vub.at.exceptions.InterpreterException;
import edu.vub.at.objects.ATAbstractGrammar;

public class ThreadRingAT extends Benchmark {
	
	private BenchIAT          threadringIAT;
	private ATAbstractGrammar threadring;
	
	private final static Object signal = new Object();
	
	@Override
	protected void setUp() {
		try {
			InputStream strm;
			
			threadringIAT = BenchIAT.create(new String[] { "1000" });
			strm = ThreadRingAT.class.getResourceAsStream("/bench/clbg/threadring.at");
			threadring   = threadringIAT.parse("threadring.at", strm);
		} catch (InterpreterException e) {
			e.printStackTrace();
		}
	}
	
	public void timeThreadring(int reps) {
		for (int i = 0; i < reps; i++) {
			try {
				synchronized (signal) {
					threadringIAT.evaluate(threadring);
					signal.wait();
				}
			} catch (InterpreterException e) { e.printStackTrace();
			} catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
	
	public static void sendSignal() {
		synchronized (signal) {
			signal.notify();
		}
	}
	
	public static void main(String[] args) {
		CaliperMain.main(ThreadRingAT.class, args);
	}
}
