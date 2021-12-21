package com.utry.baselib.log.core;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

class LogFile {

    static void printFile(String tag, String targetDirectory, String fileName, String headString, String msg, boolean isAppend) {

        fileName = ((null == fileName) || (fileName.length() == 0)) ? getFileName() : fileName;
        if (save(targetDirectory, fileName, msg, isAppend)) {
            //Log.d(tag, headString + " save log success ! location is >>>" + targetDirectory + "/" + fileName);
        } else {
            Log.e(tag, headString + "save log fails !");
        }
    }

    private static boolean save(String dic, String fileName, String msg, boolean isAppend) {

        try {

            File tempDirectory = new File(dic);
            File tempFile;
            if (!tempDirectory.exists()) {
                tempDirectory.mkdirs();
                tempFile = new File(dic, fileName);
                if (!tempFile.exists()) {
                    tempFile.createNewFile();
                }

            } else {
                tempFile = new File(dic, fileName);
            }

            OutputStream outputStream;
            if (isAppend) {
                outputStream = new FileOutputStream(tempFile, true);
            } else {
                outputStream = new FileOutputStream(tempFile, false);
            }

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(msg);
            outputStreamWriter.flush();
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static String getFileName() {
        Random random = new Random();
        return "Run-" + Long.toString(System.currentTimeMillis() + random.nextInt(10000)).substring(4) + ".log";
    }

}
