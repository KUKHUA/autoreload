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

import Command.Manager;
import commands.SetupCommand;
import commands.StartCommand;
import ANSI.Get;

public class Main  {
    public static void main(String[] args) { 
       String unsetFront = ANSI.Get.unsetFront();

       String programName = String.format("%schangedetecter%s -", ANSI.Get.setFront(36), unsetFront);

       String programDescription = String.format("Detects %smodifications%s, %sdeletions%s, or %screations%s of files/folders within the %scurrent directory%s.", ANSI.Get.setFront(27), unsetFront, ANSI.Get.setFront(196), unsetFront, ANSI.Get.setFront(70), unsetFront, ANSI.Get.setBold(), ANSI.Get.unsetBold());

       Manager appManager = new Manager(programName, "1", programDescription);

       appManager.register("setup", new SetupCommand());
       appManager.register("start", new StartCommand());

       appManager.execute(args);

    }
}