package com.bitstructures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// TODO GTFO should break out of a loop -- not just jump to second KTHX

public class ImInUrJVM {
	private final Pattern BTW = Pattern.compile("BTW.*");
	private final Pattern HAI = Pattern.compile("HAI");
	private final Pattern CAN_HAS = Pattern.compile("CAN\\s+HAS\\s+[^\\?]+\\?");
	private final Pattern KTHXBYE = Pattern.compile("KTHXBYE");
	private final Pattern VISIBLE_STRING = Pattern.compile("VISIBLE\\s+\"([^\"]*)\"(!?)");
	private final Pattern VISIBLE_VAR = Pattern.compile("VISIBLE\\s+([A-Z][A-Z0-9]*)(!?)");
	private final Pattern I_HAS_A = Pattern.compile("I\\s+HAS\\s+A\\s+([A-Z][A-Z0-9]*)");
	private final Pattern LOL = Pattern.compile("LOL\\s+([A-Z][A-Z0-9]*)\\s+R\\s+([0-9]+)");
	private final Pattern NERFZ = Pattern.compile("NERFZ\\s+([A-Z][A-Z0-9]*)!!");
	private final Pattern IM_IN_YR = Pattern.compile("IM\\s+IN\\s+YR.*");
	private final Pattern KTHX = Pattern.compile("KTHX");
	private final Pattern IZ_LIEK = Pattern.compile("IZ\\s+([A-Z][A-Z0-9]*)\\s+LIEK\\s+([0-9]+)\\?");
	private final Pattern YARLY = Pattern.compile("YARLY");
	private final Pattern NOWAI = Pattern.compile("NOWAI");
	private final Pattern GTFO = Pattern.compile("GTFO");

	private List<String> statements;
	private int statementCounter;
	private Map<String, Integer> variables;

	public ImInUrJVM() {
		statements = new ArrayList<String>();
		statementCounter = 0;
		variables = new HashMap<String, Integer>();
	}

	private String getStatement(int index) {
		return statements.get(index).trim();
	}

	private String getStatement() {
		return getStatement(statementCounter);
	}

	private boolean matches(Pattern pattern, CharSequence input) {
		Matcher m = pattern.matcher(input);
		return m.matches();
	}

	private void skipUntil(Pattern... patterns) {
		while(statementCounter < statements.size()) {
			for (Pattern pattern : patterns) {
				if (matches(pattern, getStatement())) {
					return;
				}
			}
			statementCounter++;
		}
	}

	private void doVisible(Object o, String newlineFlag) {
		if (newlineFlag.equals("!")) {
			System.out.print(o);
		} else {
			System.out.println(o);
		}
	}

	private void doVisibleString(String statement) {
		Matcher m = VISIBLE_STRING.matcher(statement);
		m.matches();
		doVisible(m.group(1), m.group(2));
	}

	private void doVisibleVar(String statement) {
		Matcher m = VISIBLE_VAR.matcher(statement);
		m.matches();
		doVisible(variables.get(m.group(1)), m.group(2));
	}

	private void doIHasA(String statement) {
		Matcher m = I_HAS_A.matcher(statement);
		m.matches();
		variables.put(m.group(1), null);
	}

	private void doLol(String statement) {
		Matcher m = LOL.matcher(statement);
		m.matches();
		variables.put(m.group(1), new Integer(m.group(2)));
	}

	private void doNerfz(String statement) {
		Matcher m = NERFZ.matcher(statement);
		m.matches();
		variables.put(m.group(1), variables.get(m.group(1)) - 1);
	}

	private void doKthx() {
		int nestedKthx = 0;
		for (int n = statementCounter - 1; n >= 0; n--) {
			if (matches(KTHX, getStatement(n))) {
				nestedKthx++;
				continue;
			}
			if (matches(IZ_LIEK, getStatement(n))) {
				if (nestedKthx == 0) {
					// KTHX belongs to an IZ LIEK
					// no need to do anything
					return;
				} else {
					nestedKthx--;
					continue;
				}
			}
			if (matches(IM_IN_YR, getStatement(n))) {
				if (nestedKthx == 0) {
					// KTHX belongs to an IM IN YR
					// jump to start of loop
					statementCounter = n;
					return;
				} else {
					nestedKthx--;
					continue;
				}
			}
		}
	}

	private void doIzLiek(String statement) {
		Matcher m = IZ_LIEK.matcher(statement);
		m.matches();
		Integer n1 = variables.get(m.group(1));
		Integer n2 = new Integer(m.group(2));
		// skip YARLY
		if (matches(YARLY, getStatement(statementCounter + 1))) {
			statementCounter++;
		}
		if (!n1.equals(n2)) {
			skipUntil(NOWAI, KTHX);
		}
	}

	private void doNowai() {
		skipUntil(KTHX);
	}

	private void doGtfo() {
		// skip code until second KTHX
		skipUntil(KTHX);
		statementCounter++;
		skipUntil(KTHX);
	}

	public void load(String filename) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(filename));
		statements = new ArrayList<String>();
		String line = r.readLine();
		while(line != null) {
			statements.add(line);
			line = r.readLine();
		}
	}

	public void run() {
		statementCounter = 0;
		while(statementCounter < statements.size()) {
			String statement = getStatement();
			if (matches(VISIBLE_STRING, statement)) {
				doVisibleString(statement);
			} else if (matches(VISIBLE_VAR, statement)) {
				doVisibleVar(statement);
			} else if (matches(I_HAS_A, statement)) {
				doIHasA(statement);
			} else if (matches(LOL, statement)) {
				doLol(statement);
			} else if (matches(NERFZ, statement)) {
				doNerfz(statement);
			} else if (matches(KTHX, statement)) {
				doKthx();
			} else if (matches(IZ_LIEK, statement)) {
				doIzLiek(statement);
			} else if (matches(NOWAI, statement)) {
				doNowai();
			} else if (matches(GTFO, statement)) {
				doGtfo();
			} else if (!(matches(BTW, statement)
					|| matches(HAI, statement)
					|| matches(CAN_HAS, statement)
					|| matches(KTHXBYE, statement)
					|| matches(IM_IN_YR, statement))){
				System.err.println("OH NOES! SYNTAX ERROR: " + statement);
				return;
			}
			statementCounter++;
		}
	}

	public static void main(String[] args) throws IOException {
		ImInUrJVM interpreter = new ImInUrJVM();
		interpreter.load(args[0]);
		interpreter.run();
	}
}
