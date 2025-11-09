package com.eugen.ardeleanu.scramble;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import scramble.ScrambleValidator;

/**
 * Tests for ScrambleValidator
 *
 */
public class ScrambleValidatorTest {

	@Test
	public void NotScrambledTest() {
		assertEquals("not", ScrambleValidator.get("en").validate("irony", "irony"));
	}

	@Test
	public void PoorFirstPositionTest() {
		assertEquals("poor", ScrambleValidator.get("en").validate("IOYRN", "IRONY"));
	}
	
	@Test
	public void PoorConsecutivePositionTest() {
		assertEquals("poor", ScrambleValidator.get("en").validate("ROINY", "IRONY"));
	}
	
	@Test
	public void FairConsecutivePositionCombinationTest() {
		assertEquals("fair", ScrambleValidator.get("en").validate("RIONY", "IRONY"));
	}
	
	@Test
	public void FairFirstPositionCombinationTest() {
		assertEquals("fair", ScrambleValidator.get("en").validate("INOYR", "IRONY"));
	}
	
	@Test
	public void FairCombinationTest() {
		assertEquals("fair", ScrambleValidator.get("en").validate("MAPS", "SPAM"));
	}
	
	@Test
	public void HardAlternatingTest() {
		assertEquals("hard", ScrambleValidator.get("en").validate("ONYRI", "IRONY"));
	}
	
	@Test
	public void ListTest() {
		List<String> input = Stream.of("MAPS SPAM", "RIONY IRONY", "ONYRI IRONY", "IRONY IRONY", "INOYR IRONY", "IOYRN IRONY").collect(Collectors.toList());
		List<String> expected = Stream.of("MAPS is fair scramble of SPAM", "RIONY is fair scramble of IRONY", "ONYRI is hard scramble of IRONY", "IRONY is not scramble of IRONY", "INOYR is fair scramble of IRONY", "IOYRN is poor scramble of IRONY").collect(Collectors.toList());
		assertEquals(expected, ScrambleValidator.get("en").validate(input));
	}
	
}
