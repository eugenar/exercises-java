package com.ardeleanu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for CSVFileProcessorMain.
 */
public class CSVFileProcessorMainTest extends TestCase {
	private static final String SRC_TEST_RESOURCES = "src/test/resources/";

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public CSVFileProcessorMainTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(CSVFileProcessorMainTest.class);
	}

	/**
     * Test for printing at console.
     */
	public void testPrintConsole() {
		String dirPath = new File(SRC_TEST_RESOURCES).getAbsolutePath();
		new Processor(dirPath).process();
	}

	/**
	 * Test for printing to file.
	 * @throws IOException
	 */
	public void testPrintFile() throws IOException {
		String dirPath = new File(SRC_TEST_RESOURCES).getAbsolutePath();
		new Processor(dirPath).process(SRC_TEST_RESOURCES + "actual.txt");
		assertTrue(compareFiles(SRC_TEST_RESOURCES + "expected.txt", SRC_TEST_RESOURCES + "actual.txt"));
	}

	private boolean compareFiles(String path, String path2) throws IOException {
		String s = readFile(path);
		String s2 = readFile(path2);
		return s.equals(s2);
	}

	private String readFile(String filePath) throws IOException {
		FileInputStream fin = new FileInputStream(filePath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(fin));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

}
