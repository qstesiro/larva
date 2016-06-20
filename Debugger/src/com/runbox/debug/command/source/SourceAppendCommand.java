package com.runbox.debug.command.source;

import com.runbox.debug.command.Command;
import com.runbox.debug.manager.SourceManager;

import java.io.File;

/**
 * Created by qstesiro
 */
public class SourceAppendCommand extends Command {

    public SourceAppendCommand(String command) throws Exception {
        super(command);
        if (null == argument) {
            throw new Exception("invalid argument");
        }
    }

    @Override
    public boolean execute() throws Exception {
        String path = path();
        if (null != path) {
            File file = new File(path);
            if (file.exists() && file.isDirectory()) {
                if (0 < file.listFiles().length) {
                    SourceManager.instance().append(path);
                }
            } else {
                System.out.println("invalid path/" + path);
            }
        }
        return super.execute();
    }

    private String path() {
        String path = argument;
        if ('\\' != path.charAt(path.length() - 1)) {
            path += "\\";
        }
        return path;
    }
}
