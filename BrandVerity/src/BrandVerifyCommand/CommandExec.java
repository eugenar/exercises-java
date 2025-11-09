/**
 * 
 */
package BrandVerifyCommand;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

/**
 * @author Eugen Ardeleanu
 *
 */
public class CommandExec {

	String commandFile;
	String outputFile;
	InputParser inputParser;

	/**
	 * Run from console: java CommandExec input.txt commands.txt output.txt
	 * 
	 * @param args, e.g., input.txt commands.txt output.txt
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Error! Usage: java CommandExec input.txt commands.txt output.txt");
			System.exit(1);
		}

		new CommandExec(args[0], args[1], args[2]).execute();
	}

	/**
	 * @param inputParser
	 */
	public CommandExec(String inputFile, String commandFile, String outputFile) {
		this.outputFile = outputFile;
		this.commandFile = commandFile;
		this.inputParser = new InputParser(inputFile);
	}

	void execute() {
		try (BufferedReader reader = new BufferedReader(new FileReader(commandFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] command = line.split("\\s+"); // command terms are white-space separated
				executeCommand(command, writer);
			}
		} catch (IOException e) {
			e.printStackTrace(); // write error at console
		}
	}

	private void executeCommand(String[] command, BufferedWriter writer) {
		if (command.length < 2)
			return;

		switch (command[0]) {
		case "FREQUENCY":
			executeFrequencyCommand(command, writer);
			break;
		case "IN_ORDER":
			executeInorderCommand(command, writer);
			break;
		case "TOP":
			executeTopCommand(command, writer);
			break;
		default:
			break;
		}
	}

	private void executeTopCommand(String[] command, BufferedWriter writer) {
		int top = 0;
		try {
			top = Integer.parseInt(command[1]);
		} catch (NumberFormatException e) {
			e.printStackTrace(); // ignore and continue
		}

		if (top < 1)
			return;

		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (Map.Entry<Integer, SortedSet<String>> entry : inputParser.frequency2term.entrySet()) {
			for (String term : entry.getValue()) {
				sb.append(term).append(' ');
				if (++count == top) {
					writeLine(writer, sb.toString());
					return;
				}
			}
		}
		
		writeLine(writer, sb.toString());
	}

	private void executeInorderCommand(String[] command, BufferedWriter writer) {
		int position = -1;
		for (int i = 1; i < command.length; i++) {
			String term = command[i];
			if (inputParser.term2index.containsKey(term)) {
				List<Integer> positions = inputParser.term2index.get(term);
				int index = Collections.binarySearch(positions, position);
				if (index >= 0) { // repeated term, e.g., IN_ORDER term term
					if (index == positions.size() - 1) {
						writeLine(writer, "FALSE");
						return;
					}
					else 
						position = positions.get(index + 1);
				}
				else
					index = -index - 1; // different terms, e.g., IN_ORDER term term2
				if (index == positions.size()) { // no more positions to advance to
					writeLine(writer, "FALSE");
					return;
				}
				
				position = positions.get(index); // advance current position
			}
			else { // term does not appear
				writeLine(writer, "FALSE");
				return;
			}
		}

		writeLine(writer, "TRUE");
		return;
	}

	private void executeFrequencyCommand(String[] command, BufferedWriter writer) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < command.length; i++) {
			if (inputParser.term2index.containsKey(command[i]))
				sb.append(inputParser.term2index.get(command[i]).size()).append(' ');
			else
				sb.append(0).append(' ');
		}

		writeLine(writer, sb.toString());
	}

	private void writeLine(BufferedWriter writer, String s) {
		try {
			writer.write(s.trim());
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace(); // ignore and continue
		}
	}

}
