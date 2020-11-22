package commend;

import util.RuntimeUtil;

public class Commend {

    public static void parseCommand(String command) {
        if (command != null && !command.trim().equals("")) {
            if (command.startsWith("cmd:")) {
                //执行shell命令
                RuntimeUtil.exeCommadn(command.replace("cmd:", "").split(" "));
            } else if (command.startsWith("getfile:")) {
                //上传文件到服务端


            } else if (command.startsWith("downfile:")) {



            } else if (command.startsWith("opensoft:")) {
                //打开应用

            } else if (command.startsWith("http:")) {

            }
        }
    }
}
