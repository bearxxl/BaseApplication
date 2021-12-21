package com.utry.baselib.log.core.alert;


import com.utry.baselib.log.ProfUseLogUtils;
import com.utry.baselib.log.core.LogConstants;

import java.io.BufferedOutputStream;
import java.io.PrintStream;

/**
 * Helper functions for running processes.
 * @author nolan
 */
public class LogRuntimeHelper {

    private static final String TAG = LogRuntimeHelper.class.getName();

    /**
     * Exec the arguments, using root if necessary.
     * @param commands
     */
    public static Process exec(String[] commands) {

        Process process = null;
        PrintStream outputStream = null;
        try {

            process = Runtime.getRuntime().exec(LogConstants.IS_ROOT
                    ? LogConstants.COMMAND_SU
                    : LogConstants.COMMAND_SH);

            outputStream = new PrintStream(new BufferedOutputStream(process.getOutputStream(),
                    LogConstants.COMMAND_BUFFER_TYPE));

            for (String command : commands) {
                if (command != null) {
                    outputStream.println(command);
                    outputStream.flush();
                }
            }

        } catch (Exception e) {
            ProfUseLogUtils.e(TAG, "LogRuntimeHelper exec: ", e);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return process;
    }

    public static void destroy(Process process) {
        // if we're in JellyBean, then we need to kill the process as root, which requires all this
        // extra UnixProcess logic
        LogSuperUserHelper.destroy(process);
    }

}