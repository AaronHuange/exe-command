<template>
    <div class="page-warp">


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
            }
        },
        created() {
            //开始连接websocket服务
            websocket.connect("ws://", () => {
                //发送认证管理员消息 当连接上时
                websocket.send_data(Protocol.toString("paly", {
                    username: this.username,
                    password: this.password
                }));
            }, () => {
                //当收到消息时
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
        height: 100%;
    }


</style>
