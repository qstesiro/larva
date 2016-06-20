package com.runbox.debug.manager;

import com.sun.jdi.Location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by huangmengmeng01 on 2016/5/24.
 */
public class SourceManager extends Manager {

    private SourceManager() {

    }

    private static SourceManager manager = new SourceManager();

    public static SourceManager instance() {
        return manager;
    }

    @Override
    public  void clean() {
        map.clear();
    }

    private int number = 1;

    public int number() {
        return number++;
    }

    private Map<Integer, String> map = new HashMap<>();

    public boolean append(String path) {
        for (Integer key : map.keySet()) {
            if (map.get(key).equals(path)) {
                return true;
            }
        }
        map.put(number(), path);
        return true;
    }

    public boolean delete(int number) {
        for (Integer key : map.keySet()) {
            if (key == number) {
                map.remove(key);
                return true;
            }
        }
        return false;
    }

    public Map<Integer, String> lines(Location location, int front, int back) throws Exception {
        return lines(match(location), location, front, back);
    }

    public File match(Location location) throws Exception {
        for (Integer key : map.keySet()) {
            File file = new File(map.get(key) + File.separator + location.sourcePath());
            if (file.exists()) {
                return file;
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
