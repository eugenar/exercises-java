package com.ardeleanu;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.opencsv.CSVReader;

public abstract class FileProcessorBase {

	protected static final String STATE = "state";
	protected static final String ACTIVE = "active";
	protected static final String COURSE_ID = "course_id";
	protected static final Object USER_ID = "user_id";

	protected List<File> files;
	protected List<String> header;
	protected static Map<String, Course> courses;
	protected static Map<String, String> users;

	static {
		courses = new HashMap<String, FileProcessorBase.Course>();
		users = new HashMap<>();
	}

	public FileProcessorBase() {
		files = new ArrayList<File>();
	}

	public void process() {
		for (File file : files)
			processFile(file);
	}

	public boolean processHeader(String[] fileHeader, File file) {
		if (fileHeader == null)
			return true;
		if (Arrays.asList(fileHeader).containsAll(header)) {
			files.add(file);
			return true;
		}
		return false;
	}

	public void print(PrintStream stream) {
		for (Entry<String, Course> entry : courses.entrySet()) {
			entry.getValue().print(entry.getKey(), stream);
			stream.println();
		}
	}

	private void processFile(File file) {
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(file));
			String[] fileHeader = reader.readNext();
			Map<String, Integer> headerMap = buildHeaderMap(fileHeader);
			String[] line;
			while ((line = reader.readNext()) != null) {
				processLine(line, headerMap);
			}
		} catch (IOException e) {
			// TODO log
		}
	}

	protected abstract void processLine(String[] line,
			Map<String, Integer> headerMap);

	private Map<String, Integer> buildHeaderMap(String[] fileHeader) {
		Map<String, Integer> headerMap = new HashMap<>(header.size());
		for (int i = 0; i < fileHeader.length; i++) {
			for (String col : header) {
				if (fileHeader[i].equalsIgnoreCase(col)) {
					headerMap.put(col, i);
					break;
				}
			}
		}
		return headerMap;
	}

	class Course {
		private String name;
		private Set<String> users;

		Course(String name) {
			this.name = name;
			users = new HashSet<>();
		}

		void addUser(String user) {
			users.add(user);
		}

		void removeUser(String user) {
			users.remove(user);
		}

		/**
		 * Print course to stream.
		 * 
		 * @param id
		 * @param stream
		 */
		void print(String id, PrintStream stream) {
			stream.println(name + " [" + id + "]");
			if (users.size() == 0) {
				stream.println("  (empty)");
				return;
			}
			for (User user : sortUsers())
				stream.println("  " + user.name + " [" + user.id + "]");
		}

		/**
		 * Sort users by name.
		 * @return
		 */
		private List<User> sortUsers() {
			List<User> sortedUsers = new ArrayList<>(users.size());
			for (String userId : users) {
				String name = FileProcessorBase.users.get(userId);
				sortedUsers.add(new User(userId, name));
			}
			Collections.sort(sortedUsers, new Comparator<User>() {
				@Override
				public int compare(User u, User u2) {
					return u.name.compareTo(u2.name);
				}
			});
			return sortedUsers;
		}

	}

	class User implements Comparable<User> {
		private String id;
		private String name;

		User(String id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public int compareTo(User u) {
			return name.compareToIgnoreCase(u.name);
		}
	}

}
