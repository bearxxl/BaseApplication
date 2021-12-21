package com.utry.baselib.log.core.alert;


import com.utry.baselib.log.ProfUseLogUtils;
import com.utry.baselib.log.core.LogConstants;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

/*
 * Starting in JellyBean, the READ_LOGS permission must be requested as super user
 * or else you can only read your own app's logs.
 *
 * This class contains helper methods to correct the problem.
 */
public class LogSuperUserHelper {

    private static final String TAG = LogSuperUserHelper.class.getName();

    public static void destroy(Process process) {
        // stupid method for getting the pid, but it actually works

        Matcher matcher = LogConstants.COMMAND_PATTERN_PID.matcher(process.toString());
        matcher.find();

        int pid = Integer.parseInt(matcher.group());

        List<Integer> allRelatedPids = getAllRelatedPids(pid);

        ProfUseLogUtils.d(TAG, "destroy: allRelatedPids===", allRelatedPids);

        for (Integer relatedPid : allRelatedPids) {
            destroyPid(relatedPid);
        }

    }

    private static List<Integer> getAllRelatedPids(final int pid) {

        List<Integer> result = new ArrayList<Integer>(Arrays.asList(pid));
        // use 'ps' to get this pid and all pids that are related to it (e.g. spawned by it)

        Process suProcess = null;
        PrintStream outputStream = null;
        BufferedReader bufferedReader = null;

        try {

            suProcess = Runtime.getRuntime().exec(LogConstants.IS_ROOT
                    ? LogConstants.COMMAND_SU
                    : LogConstants.COMMAND_SH);

            outputStream = new PrintStream(new BufferedOutputStream(suProcess.getOutputStream(),
                    LogConstants.COMMAND_BUFFER_TYPE));
            outputStream.println(LogConstants.COMMAND_PS_LOGCAT);
            outputStream.println(LogConstants.COMMAND_EXIT);
            outputStream.flush();

            if (suProcess != null) {
                try {
                    suProcess.waitFor();
                } catch (Exception e) {
                    ProfUseLogUtils.w(TAG, "getAllRelatedPids: cannot get pids", e);
                }
            }

            bufferedReader = new BufferedReader(new InputStreamReader(suProcess.getInputStream()),
                    LogConstants.COMMAND_BUFFER_TYPE);

            while (bufferedReader.ready()) {
                String[] line = LogConstants.COMMAND_PATTERN_SPACES.split(bufferedReader.readLine());
                if (line.length >= 3) {
                    try {
                        if (pid == Integer.parseInt(line[2])) {
                            result.add(Integer.parseInt(line[1]));
                        }
                    } catch (Exception ignore) {
                    }
                }
            }

        } catch (Exception e) {
            ProfUseLogUtils.e(TAG, "getAllRelatedPids: ", e);
        } finally {

            if (outputStream != null) {
                outputStream.close();
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    ProfUseLogUtils.e(TAG, "getAllRelatedPids: ", e);
                }
            }

            if (suProcess != null) {
                try {
                    suProcess.waitFor();
                } catch (Exception e) {
                    ProfUseLogUtils.e(TAG, "getAllRelatedPids: cannot kill process " + pid, e);
                }
            }

        }

        return result;
    }

    private static void destroyPid(int pid) {

        Process suProcess = null;
        PrintStream outputStream = null;
        try {

            //suProcess = Runtime.getRuntime().exec("su");
            suProcess = Runtime.getRuntime().exec(LogConstants.IS_ROOT
                    ? LogConstants.COMMAND_SU
                    : LogConstants.COMMAND_SH);

            outputStream = new PrintStream(new BufferedOutputStream(suProcess.getOutputStream(),
                    LogConstants.COMMAND_BUFFER_TYPE));
            outputStream.println("kill " + pid);
            outputStream.println(LogConstants.COMMAND_EXIT);
            outputStream.flush();

        } catch (Exception e) {
            ProfUseLogUtils.e(TAG, "destroyPid: cannot kill process " + pid, e);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (suProcess != null) {
                try {
                    suProcess.waitFor();
                } catch (Exception e) {
                    ProfUseLogUtils.e(TAG, "destroyPid: cannot kill process " + pid, e);
                }
            }
        }
    }

    public static void checkRoot() {

        Process process;
        try {
            // Preform su to get root privledges
            process = Runtime.getRuntime().exec(LogConstants.COMMAND_SU);

            // confirm that we have root
            DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
            outputStream.writeBytes(LogConstants.COMMAND_CONFIRM_ROOT);

            // Close the terminal
            outputStream.writeBytes(LogConstants.COMMAND_EXIT);
            outputStream.flush();

            process.waitFor();
            LogConstants.IS_ROOT = process.exitValue() == 0;

        } catch (Exception e) {
            ProfUseLogUtils.e(TAG, "checkRoot: ", e);
            LogConstants.IS_ROOT = false;
        }

    }

}
