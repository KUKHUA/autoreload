/*
 * ChangeDetector -  detects modifications, deletions, or creations of files and folders.
 * Copyright (C) 2025 KUKHUA
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package misc; 

import java.nio.file.*; 

public class FileFilter {

    public static boolean isAllowed(Path path) {
        try {
            if (path.getParent().toString().chars().filter(ch -> ch == '.').count() == 2) return false; // hidden folders

            if (path.getFileName().toString().endsWith(".part")) return false; // hide parts of a file

             if (Files.exists(path)) {
                path = path.toRealPath(); 

                if(Files.isHidden(path)) return false; //hide hidden files that exist
            } else {
                return !path.getFileName().toString().startsWith("."); // hide hidden files that dont exist, and show files that dont exist yet are not hidden
            }

            return true;

        } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
    e.printStackTrace();  
            return false;
        }
    }

    public static String objectType(Path path){
        return Files.isDirectory(path) ? "folder" : Files.isRegularFile(path) ? "file" : guess(path);
    }

    private static String guess(Path path) {
        String name = path.getFileName().toString();
        if (name.isEmpty()) return "magical_thing";

        if (name.contains(".")) {
            return "maybe_file";
        } else if (!name.contains(".")) {
            return "maybe_folder";
        } else {
        return "magical_thing"; // fallback
        }
}

}
