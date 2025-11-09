package com.ardeleanu;

import java.util.ArrayList;
import java.util.Map;

public class CourseFileProcessor extends FileProcessorBase {

	private static final String COURSE_NAME = "course_name";

	public CourseFileProcessor() {
		header = new ArrayList<>(3);
		header.add(COURSE_ID);
		header.add(COURSE_NAME);
		header.add(STATE);
	}

	protected void processLine(String[] line, Map<String, Integer> headerMap) {
		int idCol = headerMap.get(COURSE_ID);
		int nameCol = headerMap.get(COURSE_NAME);
		int stateCol = headerMap.get(STATE);

		String state = line[stateCol];
		String id = line[idCol];
		String name = line[nameCol];
		if (state.equalsIgnoreCase(ACTIVE))
			courses.put(id, new Course(name)); // course name can be updated
		else
			courses.remove(id);
	}

}
