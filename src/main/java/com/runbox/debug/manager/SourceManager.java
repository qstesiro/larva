package com.runbox.debug.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import com.sun.jdi.Location;
import com.sun.jdi.AbsentInformationException;

public class SourceManager extends Manager {

    private SourceManager() {

    }

    private static SourceManager instance = new SourceManager();

    public static SourceManager instance() {
        return instance;
    }

    @Override
    public  void clean() {
        map.clear();
    }    

    private Map<Integer, String> map = new HashMap<Integer, String>();

    public boolean append(String path) {
        for (Integer key : map.keySet()) {
            if (map.get(key).equals(path)) {
                return true;
            }
        }
        map.put(id(), path);
        return true;
    }

    public boolean delete(int id) {
        for (Integer key : map.keySet()) {
            if (key == id) {
                map.remove(key);
                return true;
            }
        }
        return false;
    }

    public Map<Integer, String> lines(Location location, int front, int back) throws Exception {
        return lines(find(location), location, front, back);
    }

    public File find(Location location) {
        for (Integer key : map.keySet()) {
			try {
				File file = new File(map.get(key) + File.separator + location.sourcePath());
				if (file.exists()) {
					return file;
				}
			} catch (AbsentInformationException e) {
				return null;
			}
        }
        return null;
    }

    private Map<Integer, String> lines(File file, Location location, int front, int back) throws Exception {
        if (null != file) {
            if (location.lineNumber() >= 0 && front <= 0 && back >= 0) {
                int start = location.lineNumber() + front;
                start = (0 > start) ? 0 : start;
                int end = location.lineNumber() + back;
                if (file.exists()) {
                    int number = 1;
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new FileReader(file));
                        String code;
                        Map<Integer, String> map = new HashMap<Integer, String>();
                        while (null != (code = reader.readLine())) {
                            if (number >= start && number <= end) {
                                map.put(number, code);
                            } else if (number > end) {
                                return map;
                            }
                            ++number;
                        }
                    } finally {
                        if (null != reader) {
                            reader.close();
                        }
                    }
                }
            }			
        }
        return null;
    }

    public Map<Integer, String> paths() {
        return map;
    }
}
