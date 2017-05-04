package com.runbox.manager;

import java.util.List;
import java.util.LinkedList;

import com.sun.tools.javac.util.Pair;

public class AliasManager extends Manager {

    private AliasManager() {

    }

    private static AliasManager instance = new AliasManager();

    public static AliasManager instance() {
        return instance;
    }

    public void clean() throws Exception {
        list.clear();
    }

    private List<Pair<String, String>> list = new LinkedList<Pair<String, String>>();

    public boolean append(String command, String name) {
        for (Pair<String, String> pair : list) {
            if (pair.fst.equals(command) || pair.snd.equals(name)) {
                return false;
            }
        }
        list.add(new Pair<String, String>(command, name));		 
        return true;
	}

	public boolean delete(String name) {
        for (Pair<String, String> pair : list) {
            if (pair.snd.equals(name)) {
                list.remove(pair); return true;
            }
        }
        return false;		
	}

    public void delete() {
        list.clear();
    }

	public String find(String name) {
        for (Pair<String, String> pair : list) {
            if (pair.snd.equals(name)) {
                return pair.fst;
            }
        }		
		return null;
	}
	
	public List<Pair<String, String>> get() {
		return list;
	}
}
