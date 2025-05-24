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

public class ParseChange {
    public static String parse(String toTranslate){
        switch(toTranslate){
            case "ENTRY_MODIFY":
                return "modified";

            case "ENTRY_CREATE":
                return "created";

            case "ENTRY_DELETE":
                return "deleted";

            default:
                return toTranslate;
        }
    }

        public static String parseion(String toTranslate){
        switch(toTranslate){
            case "ENTRY_MODIFY":
                return "modification";

            case "ENTRY_CREATE":
                return "creation";

            case "ENTRY_DELETE":
                return "deletion";

            default:
                return toTranslate;
        }
    }
}
