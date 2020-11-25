package com.server.mp.server.config;

import java.util.List;
import java.util.Map;

public class CommandMessage {


    private String name;
    private String type;
    private Command msg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Command getMsg() {
        return msg;
    }

    public void setMsg(Command msg) {
        this.msg = msg;
    }

    public static class Command{
        private List<String> sendToList;
        private SendCommand sendCommand;

        public List<String> getSendToList() {
            return sendToList;
        }

        public void setSendToList(List<String> sendToList) {
            this.sendToList = sendToList;
        }

        public SendCommand getSendCommand() {
            return sendCommand;
        }

        public void setSendCommand(SendCommand sendCommand) {
            this.sendCommand = sendCommand;
        }

        public static class SendCommand{

            private String name;
            private String type;
            private Map<String,String> msg;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Map<String, String> getMsg() {
                return msg;
            }

            public void setMsg(Map<String, String> msg) {
                this.msg = msg;
            }
        }
    }

}
