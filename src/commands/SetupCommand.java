package commands;
import Command.IHandler;
import Command.Command;
import java.util.Scanner;
import config.Config;

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

       System.out.println("\t- true/false -\t");
        config.set("test", "test");

       System.out.println("Do you want to enable Server-Sent-Events? (true/false)");
       sseEnable = sc.nextBoolean();
       if(sseEnable){
            System.out.println("At what port? (integer)");
            ssePort = sc.nextInt(); 
       }

       System.out.println("Do you want to enable webhooks? (true/false)");
       webhookEnable = sc.nextBoolean();
       if(webhookEnable) stillSetup = true;
       while(stillSetup){
            System.out.println("What webhook slot do you wish to edit? (0-9)");
            int editSlot = sc.nextInt();
            if(editSlot > 9 || editSlot < 0) continue;

            System.out.println("What is the webhook URL?");
            sc.nextLine();
            webhooks[editSlot] = sc.nextLine();

            System.out.println("Do you want to stop editing slots? (true/false)");
            stillSetup = !sc.nextBoolean();
       }

       System.out.println("Do you want to enable http posts? (true/false)");
       httpEnable = sc.nextBoolean();
       if(httpEnable) stillSetup = true;

       while(stillSetup){
            System.out.println("What http post slot do you wish to edit? (0-9)");
            int editSlot = sc.nextInt();
            if(editSlot > 9 || editSlot < 0) continue;

            System.out.println("What is the http post URL?");
            sc.nextLine();
            httpPosts[editSlot] = sc.nextLine();

            System.out.println("Do you want to stop editing slots? (true/false)");
            stillSetup = !sc.nextBoolean();
       }

      config.set("sources.sse.enabled", Boolean.toString(sseEnable));
      config.set("sources.sse.port", Integer.toString(ssePort));

      config.set("sources.webhook.enabled", Boolean.toString(webhookEnable));
      config.set("sources.webhook.urls", String.join(",",webhooks));

      config.set("sources.http.enabled", Boolean.toString(httpEnable));
      config.set("sources.http.urls", String.join(",", httpPosts));
      
      config.save();
    }

    @Override
    public String getHelpInfo(){
        return "This command is a interactive setup procedure to configure everything.";
    }
}