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

/**
 * Utility class for parsing file change event types into human-readable strings.
 */
public class ParseChange {

    /**
     * Translates a file change event type into a human-readable verb.
     * <p>
     * Supported event types:
     * <ul>
     *   <li>{@code ENTRY_MODIFY} - returns {@code "modified"}</li>
     *   <li>{@code ENTRY_CREATE} - returns {@code "created"}</li>
     *   <li>{@code ENTRY_DELETE} - returns {@code "deleted"}</li>
     *   <li>Any other value - returns the input string unchanged</li>
     * </ul>
     *
     * @param toTranslate the event type string to translate
     * @return the corresponding human-readable verb, or the original string if not recognized.
     */
    public static String parse(String toTranslate) {
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

    /**
     * Translates a file change event type into a human-readable noun.
     * <p>
     * Supported event types:
     * <ul>
     *   <li>{@code ENTRY_MODIFY} - returns {@code "modification"}</li>
     *   <li>{@code ENTRY_CREATE} - returns {@code "creation"}</li>
     *   <li>{@code ENTRY_DELETE} - returns {@code "deletion"}</li>
     *   <li>Any other value - returns the input string unchanged</li>
     * </ul>
     *
     * @param toTranslate the event type string to translate
     * @return the corresponding human-readable noun, or the original string if not recognized.
     */
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
