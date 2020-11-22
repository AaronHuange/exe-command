package commend;

public class Commend {

    private static CommandOper commandOper = new CommandOperImpl();

    public static void parseCommand(String command) {
        if (command != null && !command.trim().equals("")) {
            if (command.startsWith("cmd:")) {
                commandOper.cmd(command);
            } else if (command.startsWith("getfile::")) {
                commandOper.getfile(command);
            } else if (command.startsWith("downfile::")) {
                commandOper.downfile(command);
            } else if (command.startsWith("opensoft:")) {
                commandOper.opensoft(command);
            } else if (command.startsWith("http:")) {
                commandOper.http(command);
            } else if (command.startsWith("shotscreen::")) {
                commandOper.shotscreen(command);
            } else if (command.startsWith("mouse::")) {
                commandOper.mouse(command);
            } else if (command.startsWith("keyevent")) {
                commandOper.keyevent(command);
            }
        }
    }
}
