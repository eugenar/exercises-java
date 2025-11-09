package com.ardeleanu;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;

import com.opencsv.CSVReader;

/**
 * Main class containing different file processors.
 *
 */
public class Processor {
	static List<FileProcessorBase> processors;
	private String directoryPath;

	static {
		processors = new ArrayList<FileProcessorBase>(3);
		processors.add(new CourseFileProcessor());
		processors.add(new UserFileProcessor());
		processors.add(new EnrollmentFileProcessor());
	}

	public Processor(String directoryPath) {
		this.directoryPath = directoryPath;
	}

	public void process() {
		process0();
		print();
	}

	public void process(String filePath) {
		process0();
		print(filePath);
	}
	
	private void process0() {
		File[] files = getFiles(directoryPath);
		if (files != null) {
			separateFilesByHeader(files);
			processFiles();
		}
	}

	/**
	 * Print at the console.
	 */
	public static void print() {
		processors.get(0).print(System.out);
	}

	/**
	 * Print to file.
	 * 
	 * @param filePath
	 */
	public static void print(String filePath) {
		try {
			processors.get(0).print(new PrintStream(new File(filePath)));
		} catch (FileNotFoundException e) {
			// TODO log
		}
	}

	private static void processFiles() {
		for (FileProcessorBase proc : processors)
			proc.process();
	}

	/**
	 * Get files in updated date order.
	 * 
	 * @param directoryPath
	 * @return
	 */
	private static File[] getFiles(String directoryPath) {
		File directory = new File(directoryPath);
		File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
		Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
		return files;
	}

	private static void separateFilesByHeader(File[] files) {
		for (File file : files) {
			String[] header = getHeader(file);
			processHeader(header, file);
		}
	}

	private static String[] getHeader(File file) {
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(file));
			return reader.readNext();
		} catch (IOException e) {
			// TODO log
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					// ignore
				}
		}
		return null;
	}

	static void processHeader(String[] header, File file) {
		for (FileProcessorBase processor : processors) {
			if (processor.processHeader(header, file))
				break;
		}
	}

}
