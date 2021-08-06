package ceit.aut.ac.ir.utils;

import ceit.aut.ac.ir.model.Note;

import java.io.*;
import java.util.ArrayList;

public class FileUtils {

    private static final String NOTES_PATH = "./notes/";

    //It's a static initializer. It's executed when the class is loaded.
    //It's similar to constructor
    static {
        boolean isSuccessful = new File(NOTES_PATH).mkdirs();
        System.out.println("Creating " + NOTES_PATH + " directory is successful: " + isSuccessful);
    }

    public static File[] getFilesInDirectory() {
        return new File(NOTES_PATH).listFiles();
    }


    public static String fileReader(File file) {
        try(InputStream inputStream = new FileInputStream(file)){
            InputStreamReader reader = new InputStreamReader(inputStream);
            char[] charArray = new char[(int) file.length()];
            reader.read(charArray);
            return new String(charArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String BufferedFileReader(File file) {
        try(FileReader fileReader = new FileReader(file)){
            BufferedReader br = new BufferedReader(fileReader);
            ArrayList<Character> charArray = new ArrayList<>();
            int i;
            while ((i = br.read()) != -1) {
                charArray.add((char) i);
            }

            String content = "";
            for (Character c : charArray) content = content.concat(c.toString());
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void fileWriter(String content) {
        String fileName = getProperFileName(content);
        fileName = fileName.concat(".txt");
        try(BufferedWriter bf = new BufferedWriter(new FileWriter(NOTES_PATH + fileName , true))) {
            bf.write(content);
            System.out.println(NOTES_PATH + fileName + ".txt data has been written successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void BufferedFileWriter(String content) {
        String fileName = getProperFileName(content);
        fileName = fileName.concat(".txt");
        try(OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(NOTES_PATH + fileName))) {

            writer.write(content);
            System.out.println(NOTES_PATH + fileName + ".txt data has been written successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //TODO: Phase2: proper methods for handling serialization
    public static void noteWriter(Note note) {
        String fileName = getProperFileName(note.getContent());
        fileName = fileName.concat(".txt");
        try(BufferedWriter bf = new BufferedWriter(new FileWriter(NOTES_PATH + fileName , true))) {
            bf.write(note.toString());
            System.out.println(NOTES_PATH + fileName + ".txt data has been written successfully-note");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperFileName(String content) {
        int loc = content.indexOf("\n");
        if (loc != -1) {
            return content.substring(0, loc);
        }
        if (!content.isEmpty()) {
            return content;
        }
        return System.currentTimeMillis() + "_new file.txt";
    }
}
