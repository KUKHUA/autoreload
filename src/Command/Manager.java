/*
 * Java Simple Build (JSB) - A straightforward build tool for Java projects
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
 * 
 * This has been modifed to properley handle when the user's input is nothing. - KUKHUA 
 */

package Command;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages the CLI app, registers {@link IHandler}s and executes {@link Command}s.
 */
public class Manager {

    private HashMap<String, IHandler> commands = new HashMap<>();
    private ArrayList<String> helpInfo = new ArrayList<>();
    private String applicationName;
    private String applicationVersion;
    private String applicationDescription;

    /**
     * Constructor for the Manager, initializes the application name, version and description displayed
     * on the help screen.
     *
     * @param applicationName The name of the application.
     * @param applicationVersion The version of the application.
     * @param applicationDescription The description of the application.
     */
    public Manager(
        String applicationName,
        String applicationVersion,
        String applicationDescription
    ) {
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
        this.applicationDescription = applicationDescription;
        this.helpInfo.add(
                String.format(
                    "%s %s - %s\n\n",
                    this.applicationName,
                    this.applicationVersion,
                    this.applicationDescription
                )
            );
    }

    /**
     * Executes the command based on the user input. If the command is not found, it will display an error message.
     * It will also display help information on error.
     *
     * @param userInput The user input, which is an array of strings.
     */
    public void execute(String[] userInput) {
        try {
            if (userInput[0].isBlank()) {
                throw new IllegalArgumentException("No command entered.");
            }

            Command command = new Command(userInput);
            if (
                command.prefix().equals("help") ||
                command.prefix().equals("--help") ||
                command.prefix().equals("-h") ||
                command.prefix().equals("-help") ||
                command.prefix().equals("--h")
            ) {
                displayHelp();
                return;
            }

            IHandler handler = commands.get(command.prefix());
            if (handler != null) {
                command.trim();
                try {
                    handler.handleCommand(command);
                } catch (Exception e) {
                    throw new RuntimeException(
                        String.format(
                            "The command %s failed, %s",
                            command.prefix(),
                            e.getMessage()
                        )
                    );
                }
            } else {
                System.out.println("Command not found.");
                this.displayHelp();
            }
        } catch (Exception e) {
            this.displayHelp();
            if(e instanceof ArrayIndexOutOfBoundsException) return;
            System.out.println("---\nFailed to execute command:");
            e.printStackTrace();
            System.out.println("---");
        }
    }
    
    /**
     * Displays the help information.
     */
    public void displayHelp() {
        for (String help : this.helpInfo) {
            System.out.println(help);
        }
    }

    /**
     * Registers a command with the {@link IHandler} that will handle it.
     *
     * @param commandName The name of the command.
     * @param handler The {@link IHandler} that will handle the command.
     */
    public void register(String commandName, IHandler handler) {
        commands.put(commandName, handler);
        this.helpInfo.add(
                String.format("%s - %s\n", commandName, handler.getHelpInfo())
            );
    }

    /**
     * Unregisters commands. Note that this isn't very performant because {@link #reloadHelp()}
     */
    public void unregister(String commandName) {
        this.commands.remove(commandName);
        this.reloadHelp();
    }

    public void reloadHelp() {
        this.helpInfo.clear();
        this.helpInfo.add(
                String.format(
                    "%s %s - %s\n\n",
                    this.applicationName,
                    this.applicationVersion,
                    this.applicationDescription
                )
            );

        this.commands.forEach((commandName, handler) -> {
                this.helpInfo.add(
                        String.format(
                            "%s - %s\n",
                            commandName,
                            handler.getHelpInfo()
                        )
                    );
            });
    }
}
