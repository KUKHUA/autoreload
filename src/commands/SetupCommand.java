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

package commands;
import Command.IHandler;
import Command.Command;
import java.util.Scanner;
import misc.Config;
import ANSI.Print;

/**
 * The {@code SetupCommand} class implements the IHandler interface to provide an interactive setup procedure for configuring the application.
 * It allows users to enable or disable Server-Sent-Events (SSE) and webhooks, and to specify the port for SSE and URLs for webhooks.
 * The configuration is saved using the Config class.
 */
public class SetupCommand implements IHandler {
    @Override
    public void handleCommand(Command command) throws Exception {
        boolean stillSetup = false;
        boolean httpEnable = false;
        boolean webhookEnable = false;
        boolean sseEnable = false;
        Config config = Config.instance();
        int ssePort = 1337;
        String[] httpPosts = new String[10];
        String[] webhooks = new String[10];
        Scanner sc = new Scanner(System.in);

       ANSI.Print.setFront(93);
       System.out.println("Do you want to enable Server-Sent-Events? (true/false)");
       ANSI.Print.unsetFront();

       sseEnable = sc.nextBoolean();
       if(sseEnable){
            ANSI.Print.setFront(93);
            System.out.println("At what port? (integer)");
            ANSI.Print.unsetFront();
            ssePort = sc.nextInt(); 
       }

       ANSI.Print.setFront(93);
       System.out.println("Do you want to enable webhooks? (true/false)");
       ANSI.Print.unsetFront();
       webhookEnable = sc.nextBoolean();
       if(webhookEnable) stillSetup = true;

       while(stillSetup){
            ANSI.Print.setFront(93);
            System.out.println("What webhook slot do you wish to edit? (0-9)");
            ANSI.Print.unsetFront();

            int editSlot = sc.nextInt();
            if(editSlot > 9 || editSlot < 0) {
                ANSI.Print.setFront(196);
                System.out.println("Invaild slot number, try again.");
                ANSI.Print.unsetFront();
                continue;
            }

            ANSI.Print.setFront(93);
            System.out.println("What is the webhook URL?");
            ANSI.Print.unsetFront();

            sc.nextLine();
            webhooks[editSlot] = sc.nextLine();

            ANSI.Print.setFront(93);
            System.out.println("Do you want to stop editing slots? (true/false)");
            ANSI.Print.unsetFront();

            stillSetup = !sc.nextBoolean();
       }

      config.set("sources.sse.enabled", Boolean.toString(sseEnable));
      config.set("sources.sse.port", Integer.toString(ssePort));

      config.set("sources.webhook.enabled", Boolean.toString(webhookEnable));
      config.set("sources.webhook.urls", String.join(",", webhooks));
      config.set("blacklisted.files", "null,null");
      config.set("blacklisted.folders", "null,null");
      
      config.save();
    }

    @Override
    public String getHelpInfo(){
        return "This command is a interactive setup procedure to configure everything.";
    }
}