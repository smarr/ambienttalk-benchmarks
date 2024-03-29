package at.bench;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;

import at.bench.IATSettings.IATOptions;

import edu.vub.at.IAT;
import edu.vub.at.exceptions.InterpreterException;
import edu.vub.at.objects.ATAbstractGrammar;
import edu.vub.at.objects.ATObject;
import edu.vub.at.parser.NATParser;

public class BenchIAT extends IAT {

	public static BenchIAT create() throws InterpreterException {
		return create(IATSettings.getDefaultIATOptions());
	}
	
	public static BenchIAT create(String[] args) throws InterpreterException {
		return create(IATSettings.getDefaultIATOptions(), args);
	}
	
	public static BenchIAT create(IATOptions iatOptions) throws InterpreterException {
		return create(iatOptions, null);
	}

	public static BenchIAT create(IATOptions iatOptions, String[] additinalArgs)
			throws InterpreterException {
		File _ENV_AT_HOME_ = new File(iatOptions.AT_HOME_);

		System.setProperty("AT_HOME", iatOptions.AT_HOME_);
		System.setProperty("AT_INIT", iatOptions.AT_INIT_);
		System.setProperty("AT_STACK_SIZE", Constants._ENV_AT_STACKSIZE_);

		LinkedList<String> args = new LinkedList<String>(
				Arrays.asList(new String[] { "-a", iatOptions.ipAddress_, "-o",
						getNamespaceMappings(_ENV_AT_HOME_.toString()), "-n",
						iatOptions.networkName_, "-nojline" }));

		if (iatOptions.startFile_ != null)
			args.add(iatOptions.startFile_);
		
		if (additinalArgs != null) {
			args.add("BenchIAT"); // a dummy for the script name
			args.addAll(Arrays.asList(additinalArgs));
		}

		BenchIAT instance = new BenchIAT(args.toArray(new String[args.size()]));
		return instance;
	}

	private BenchIAT(String[] args) throws InterpreterException {
		super(args);
	}
	
	public ATAbstractGrammar parse(String expression) throws InterpreterException {
		return NATParser.parse("BenchIAT.parse", expression);
	}
	
	public ATAbstractGrammar parse(String filename, String expression) throws InterpreterException {
		return NATParser.parse(filename, expression);
	}
	
	public ATAbstractGrammar parse(String filename, InputStream file) throws InterpreterException {
		return NATParser.parse(filename, file);
	}
	
	public ATObject evaluate(final ATAbstractGrammar ast) throws InterpreterException {
		return evaluator_.sync_event_eval(ast);
	}
	
	@Override
	protected void loadMainCode() {
		/* NOP */
		// we want full control, there is now main code to be handled here.
	}
	
	public void stopProcessing() {
		this.evaluator_.stopProcessing();
		this.repl_.stopProcessing();
	}
	

	/*
	 * Emulate the behavior of the IAT start-script. Returns a string of the
	 * format "foo=/sdcard/android/<packagename>/files/foo:bar=/sdcard/..."
	 */
	private static String getNamespaceMappings(String atHomeFullPath) {
		StringBuilder sb = new StringBuilder(512);
		File atHome = new File(atHomeFullPath);
		String dirs[] = atHome.list();
		for (String dir : dirs) {
			File entry = new File(atHome, dir);
			if (entry.isDirectory() && !entry.isHidden())
				sb.append(dir).append('=').append(entry.toString()).append(':');
		}
		sb.append("tmp=" + Constants._ENV_AT_BASE_
				+ Constants._AT_TEMP_FILES_PATH);
		return sb.toString();
	}
}
