package factories;

import java.io.*;
import java.util.*;

/**
 * Utility class for parsing data from text files.
 * Provides methods to read and parse whitespace-separated data files.
 */
public class DataFileParser {
    public static List<String[]> parseFile(String filePath) {
        List<String[]> data = new ArrayList<>();
        
        String[] pathsToTry = {
            filePath,                                    
            "../" + filePath,                            
            "../../" + filePath,                         
            System.getProperty("user.dir") + "/" + filePath  
        };

        BufferedReader reader = null;
        for (String path : pathsToTry) {
            try {
                reader = new BufferedReader(new FileReader(path));
                break; 
            } catch (IOException e) {
                // Try next path
            }
        }

        if (reader == null) {
            System.err.println("Error: Could not find file: " + filePath);
            System.err.println("Tried paths: " + String.join(", ", pathsToTry));
            return data;
        }

        try {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s+");
                data.add(parts);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }

        return data;
    }

    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
