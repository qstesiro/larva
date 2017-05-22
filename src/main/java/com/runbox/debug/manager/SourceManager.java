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
        paths.clear(); files.clear();		
    }    

    private Map<Integer, String> paths = new HashMap<Integer, String>();

	public Map<Integer, String> paths() {
        return paths;
    }
	
	private Map<String, Map<Integer, String>> files = new HashMap<String, Map<Integer, String>>();

	public Map<String, Map<Integer, String>> files() {
		return files;
	}

    public boolean append(String path) {
		if (null != path) {
			for (Integer key : paths.keySet()) {
				if (paths.get(key).equals(path)) {
					return true;
				}
			}
			paths.put(id(), path);
		}
		return true;
    }	
	
    public boolean delete(int id) {
        for (Integer key : paths.keySet()) {
            if (key == id) {
				delete(paths.remove(key));
				return true;
            }
        }
        return false;
    }

	public void delete(String path) {
		Iterator<Map.Entry<String, Map<Integer, String>>> iterator = files.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Map<Integer, String>> entry = iterator.next();
			if (0 == entry.getKey().indexOf(path)) {
				iterator.remove();
			}
		}
	}

	public void delete() {
		paths.clear(); files.clear();
	}

	public String line(Location location) throws Exception {
		if (null != location) {
			try {
				String path = location.sourcePath();				
				Map<Integer, String> lines = find(path); if (null != lines) {
					return lines.get(location.lineNumber());
				}
				lines = load(path); if (null != lines) {
					return lines.get(location.lineNumber());
				}
			} catch (AbsentInformationException e) {
				return null;
			}
		}
		return null;
	}

	public Map<Integer, String> find(String path) {
		for (String key : files.keySet()) {
			if (key.indexOf(path) + path.length() == key.length() - 1) {
				return files.get(key);
			}
		}
		return null;
	}

	public Map<Integer, String> load(String path) throws Exception {
		for (Integer key : paths.keySet()) {
			File file = new File(paths.get(key) + File.separator + path);			
			if (file.exists()) {
				Map<Integer, String> lines = read(file);
				files.put(file.getPath(), lines);
				return lines;
			}			
		}
		return null;
	}

	public Map<Integer, String> read(File file) throws Exception {
		Map<Integer, String> lines = new HashMap<Integer, String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;			
			int i = 0; while (null != (line = reader.readLine())) {				
				lines.put(++i, line);				
			}
		} finally {
			if (null != reader) {
				reader.close();
			}
		}
		return lines;
	}	       
}
