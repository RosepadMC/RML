package net.buj.rml.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileMan {
    /**
     * checks if a file/folder exists
     * @param path
     * @return exist boolean
     */
    public boolean Exists(String path) {
        File f = new File(path);
        if (f.exists()) return true;
        else return false;
    }

    /**
     * creates a dir
     * @param path
     * @return success boolean
     */
    public boolean CreateDir(String path) {
        File f = new File(path);
        return f.mkdir();
    }

    /**
     * creates a file
     * @param path
     * @return success boolean
     * @throws IOException
     */
    public boolean CreateFile(String path) throws IOException {
        File f = new File(path);
        return f.createNewFile();
    }

    /**
     * writes to a file
     * @param path
     * @param text
     * @throws IOException
     */
    public void writetoFile(String path, String text) throws IOException {
        FileWriter fwrite = new FileWriter(path);
        fwrite.write(text);
        fwrite.close();
    }
}
