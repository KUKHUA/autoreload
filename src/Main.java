import Command.Manager;
import java.io.IOException;
import watcher.FolderWatcher;
import commands.SetupCommand;
import commands.StartCommand;

public class Main  {
    public static void main(String[] args) { 
       Manager appManager = new Manager("changedetecter - ","1","Detects modifications, deletions, or creations of files/folders within the current directory.");

       appManager.register("setup", new SetupCommand());
       appManager.register("start", new StartCommand());

       appManager.execute(args);

    }
}