<template>
    <div class="page-warp">
        <div class="content-warp">
            <div>

            </div>

            <div>

            </div>
        </div>
        <div class="list-warp">
            <div style="font-size: 18px;margin-bottom: 20px;">在线列表：</div>
            <template v-for="(item,index) in lineList">
                <div  style="border-top: gray solid 1px" :class="{client:currentID===item.name,}" :key="index+'lineList'">
                    <div>ID:{{item.name}}</div> <div :class="{client: currentID===item.name}">类型:{{item.clienttype}}</div>
                </div>
            </template>
        </div>

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
                currentID: "",
                lineList: []
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
                switch (msg.type) {
                    case  "amdinlogin":
                        console.log(msg);
                        if (msg.msg.tip === "登录成功") {
                            this.showcommand = true;
                            this.token = msg.msg.token;
                            //每隔6秒获取一次当前在线列表
                            setInterval(()=>{
                                websocket.send_data({
                                    type: "allclient",
                                    clientName: this.username,
                                    msg: {
                                        token:this.token
                                    }
                                });
                            },10000);
                        } else {
                            this.$router.replace("/auth")
                        }
                        break;
                    case "allclient":
                        this.lineList=[];
                        this.currentID="";
                        msg.msg.tip.forEach(item=>{
                            if (this.currentID===""){
                                this.currentID=item;
                            }

                            this.lineList.push({
                                name: item,
                                clienttype: "android"
                            })

                        });
                        break;

                }
            }, () => {
                //当连接关闭时
            });

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
        flex-direction: row;
    }

    .content-warp {
        flex: 0.75;
    }

    .list-warp {
        flex: 0.25;
    }

    .client {
        color: green;
    }


</style>
