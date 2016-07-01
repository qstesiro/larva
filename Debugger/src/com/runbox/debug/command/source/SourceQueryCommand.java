package com.runbox.debug.command.source;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.SourceManager;

import java.util.Map;

/**
 * Created by qstesiro
 */
public class SourceQueryCommand extends Command {

    public SourceQueryCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
        Map<Integer, String> map = SourceManager.instance().paths();
        int index = 0;
        System.out.println("index\tnumber\tpath");
        for (Integer key : map.keySet()) {
            String print = index++ + "\t";
            print += key + "\t";
            print += map.get(key) + "\t";
            System.out.println(print);
        }
        return super.execute();
    }
}
