package commend;

public interface CommandOper {
    void cmd(String command);//执行命令行操作

    void getfile(String command);//上传文件

    void downfile(String command);//下载文件

    void opensoft(String command);//打开应用

    void http(String command);//请求http接口

    void browser(String command);//用浏览器打开网页

    void shotscreen(String command);//截屏

    void mouse(String command);//鼠标操作

    void keyevent(String command);//键盘事件
}
