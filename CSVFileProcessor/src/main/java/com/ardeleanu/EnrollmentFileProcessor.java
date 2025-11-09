package com.ardeleanu;

import java.util.ArrayList;
import java.util.Map;

public class EnrollmentFileProcessor extends FileProcessorBase {

	public EnrollmentFileProcessor() {
		header = new ArrayList<>(3);
		header.add("course_id");
		header.add("user_id");
		header.add("state");
	}

	@Override
	protected void processLine(String[] line, Map<String, Integer> headerMap) {
		int courseIdCol = headerMap.get(COURSE_ID);
		int userIdCol = headerMap.get(USER_ID);
		int stateCol = headerMap.get(STATE);

		String state = line[stateCol];
		String courseId = line[courseIdCol];
		String userId = line[userIdCol];

		Course course = courses.get(courseId);
		if (course != null) {
			if (state.equalsIgnoreCase(ACTIVE)) {
				course.addUser(userId);
			} else {
				course.removeUser(userId);
			}
		}
	}

}
