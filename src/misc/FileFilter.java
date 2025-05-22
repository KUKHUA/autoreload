package misc;

import java.nio.file.*; 

public class FileFilter {

    public static boolean isAllowed(Path path) {
        try {
            System.out.println(path.getParent().toString());

            if (path.getParent().toString().chars().filter(ch -> ch == '.').count() == 2) return false; // hidden folders
            //if(path.getFileName().contains(".")) return false;

            if (path.getFileName().toString().endsWith(".part")) {
                return false;
            } // parts of a file

             if (Files.exists(path)) {
                path = path.toRealPath(); 

                if(Files.isHidden(path)) return false;//hide hidden files that exist
            } else {
                return !path.getFileName().toString().startsWith("."); // hide hidden files that dont exist, and files that dont exist
            }

            return true;

        } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
    e.printStackTrace();  
            return false;
        }
    }

    public static String objectType(Path path){
        return Files.isDirectory(path) ? "folder" : Files.isRegularFile(path) ? "file" : "magical thing";
    }
}
