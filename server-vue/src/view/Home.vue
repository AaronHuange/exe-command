<template>
    <div class="page-warp">
        <div class="content-warp">
            <div  class="textarea-warp">
                <div style="flex: 1"></div>
                <div ref="chatContent" class="textarea" v-html="textValue">
                </div>
            </div>

            <div class="list-warp">
                <div style="font-size: 18px;margin-bottom: 20px;">在线列表：</div>
                <template v-for="(item,index) in lineList">
                    <a href="#" @click="clickClient(index)" style="border-top: gray solid 1px"
                       :class="{client:currentID===item.name,noclient:currentID!==item.name}"
                       :key="index+'lineList'">
                        <div>ID:{{item.name}}</div>
                        <div :class="{client: currentID===item.name,noclient:currentID!==item.name}">
                            类型:{{item.clienttype}}
                        </div>
                    </a>
                </template>
            </div>

        </div>

        <input id="input" ref="input" @onblur="blur" v-model="value" class="input"
               type="text"/>

    </div>
</template>

<script>
    import websocket from "../websocket/wsconnect";

    const Protocol = require("../websocket/protocol/JsonProtocol"); //TODO 暂时用json
    export default {
        name: "Home",
        data() {
            return {
                username: this.$route.query.username,
                password: this.$route.query.password,
                showcommand: false,
                token: "",
                textValue: "",
                currentID: "",
                lineList: [],
                value: ""
            }
        },
        watch:{
            textValue(){
                this.$nextTick(() =>{
                    this.$refs.chatContent.scrollTop = this.$refs.chatContent.scrollHeight;
                })
            }
        },
        created() {
            //开始连接websocket服务
            websocket.connect("ws://127.0.0.1:8080/chat", () => {
                //发送认证管理员消息 当连接上时
                websocket.send_data(Protocol.toString("amdinlogin", {
                    username: this.username,
                    password: this.password
                }));
            }, (msg) => {
                if ("po" === msg) {
                    setTimeout(() => {
                        websocket.send_data("pi")
                    }, 5000);
                    return
                }

                switch (msg.type) {
                    case  "amdinlogin": {
                        console.log(msg);
                        if (msg.msg.tip === "登录成功") {
                            this.showcommand = true;
                            this.token = msg.msg.token;
                            //每隔6秒获取一次当前在线列表
                            setInterval(() => {
                                websocket.send_data({
                                    type: "allclient",
                                    clientName: this.username,
                                    msg: {
                                        token: this.token
                                    }
                                });
                            }, 10000);
                            websocket.send_data("pi")
                        } else {
                            this.$router.replace("/auth")
                        }
                        break;
                    }
                    case "allclient": {
                        this.lineList = [];
                        msg.msg.tip.forEach(item => {
                            this.lineList.push({
                                name: item,
                                clienttype: "android"
                            })
                        });
                        let havefind = false;
                        this.lineList.forEach(item => {
                            if (item.name === this.currentID) {
                                havefind = true;
                                return;
                            }
                        });
                        if (!havefind) {
                            this.currentID = this.lineList[0].name;
                        }
                        break;
                    }
                }
            }, () => {
                //当连接关闭时
            });
            this.enter();
        },
        mounted() {
            this.$refs.input.focus();
        },
        methods: {
            clickClient(index) {
                this.currentID = this.lineList[index].name;
            },
            blur() {
                setTimeout(() => {
                    this.$refs.input.focus();
                }, 1000);
            },
            enter() {
                document.onkeydown = (event) => {
                    if (event.keyCode == 13) { //这是键盘的enter监听事件
                        if (this.value === undefined || this.value === "") {
                            return;
                        }
                        websocket.send_data({
                            type: "command",
                            name: this.clientName,
                            msg: {
                                sendToList: [
                                    this.currentID
                                ],
                                sendCommand: {
                                    name: this.clientName,
                                    type: "open",
                                    msg: {
                                        packagename: "com.alibaba.android.rimet"
                                    }
                                }
                            }
                        });

                        this.textValue = this.textValue + this.value + "<br/>";
                        this.value = "";
                    }
                }
            },
        }
    }
</script>

<style scoped>
    .page-warp {
        background: black;
        width: 100%;
        height: 100vh;
        color: gray;
        display: flex;
        flex-direction: column;
    }

    .content-warp {
        flex: 1;
        display: flex;
        flex-direction: row;
    }

    .textarea-warp {
        flex: 0.75;
        display: flex;
        flex-direction: column;
        justify-content: end;
        height: 90vh;
    }

    .textarea {
        background: transparent;
        color: gray;
        padding-left: 30px;
        padding-bottom: 0px;
        padding-top: 50px;
        word-wrap: break-word;
        font-size: 13px;
        word-break: break-all;
        overflow: scroll; /*这个参数根据需要来绝对要不要*/
        -webkit-overflow-scrolling: touch;
        overflow-scrolling: touch;
        overflow: scroll;
    }

    .list-warp {
        flex: 0.25;
    }

    .input {
        flex: 0;
        min-width: 95%;
        max-width: 95%;
        height: 30px;
        border: none;
        border: 0;
        /*outline: none;*/
        font-size: 32px;
        padding-left: 30px;
        color: gray;
        background: black;
        caret-color: #ff0992 !important;
    }

    .client {
        color: wheat;
    }

    .noclient {
        color: #6a604e;
    }


</style>
