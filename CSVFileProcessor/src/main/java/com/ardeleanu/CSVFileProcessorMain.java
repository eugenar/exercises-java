package com.ardeleanu;

/**
 * Console application to process all csv files in a given directory (passed as first argument). If a second
 * argument is provided (as file path), it will write the output to the file. Otherwise, it
 * will write to the console.
 * 
 * The following processing order supports updates:
 * 
 * 1. All course files are processed in updated date order 2. All user files are
 * processed in the updated date order 3. All enrollments files are processed in
 * the updated date order.
 *
 */
public class CSVFileProcessorMain {

	public static void main(String[] args) {
		if (args.length == 0)
			return;
		if (args.length == 1) {
			String dirPath = args[0];
			if (dirPath == null)
				return;
			new Processor(dirPath).process();
		} else {
			String dirPath = args[0];
			String filePath = args[1];
			if (dirPath == null || filePath == null)
				return;
			new Processor(dirPath).process(filePath);
		}
	}

}
