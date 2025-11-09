package com.ardeleanu;

import java.util.ArrayList;
import java.util.Map;

public class UserFileProcessor extends FileProcessorBase {
	
	private static final String USER_NAME = "user_name";

	public UserFileProcessor() {
		header = new ArrayList<>(3);
		header.add("user_id");
		header.add("user_name");
		header.add("state");
	}

	@Override
	protected void processLine(String[] line, Map<String, Integer> headerMap) {
		int idCol = headerMap.get(USER_ID);
		int nameCol = headerMap.get(USER_NAME);
		int stateCol = headerMap.get(STATE);

		String state = line[stateCol];
		String id = line[idCol];
		String name = line[nameCol];
		
		if (state.equalsIgnoreCase(ACTIVE))
			users.put(id, name); // user name can be updated
		else
			users.remove(id);
	}

}
