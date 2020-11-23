<template>
    <div class="page-warp">
        <canvas class="canvas" id="canvas"></canvas>
        <div class="login-warp">
            <div>
                <div class="input input2">
                    {{tip}}
                </div>
            </div>
            <input ref="input" v-if="canShow" v-model="value" autofocus class="input input3" type="text"/>
        </div>
    </div>
</template>

<script>
    import LoginServer from "../api/login.server"

    export default {
        name: "Login",
        data() {
            return {
                tip: "",
                start: 1,
                nameTip: "username:",
                passwordTip: "password:",
                canShow: false,
                value: "",
                loginName: "",
            }
        },
        methods: {
            bg_js() {
                const canvas = document.getElementById('canvas');
                const ctx = canvas.getContext('2d');
                canvas.height = window.innerHeight;
                canvas.width = window.innerWidth;
                const texts = '0123456789'.split('');

                const fontSize = 16;
                const columns = canvas.width / fontSize;
                // 用于计算输出文字时坐标，所以长度即为列数
                const drops = [];
                //初始值
                for (let x = 0; x < columns; x++) {
                    drops[x] = 1;
                }

                function draw() {
                    //让背景逐渐由透明到不透明
                    ctx.fillStyle = 'rgba(0, 0, 0, 0.05)';
                    ctx.fillRect(0, 0, canvas.width, canvas.height);
                    //文字颜色
                    ctx.fillStyle = '#0F0';
                    ctx.font = fontSize + 'px arial';
                    //逐行输出文字
                    let text;
                    for (let i = 0; i < drops.length; i++) {
                        text = texts[Math.floor(Math.random() * texts.length)];
                        ctx.fillText(text, i * fontSize, drops[i] * fontSize);

                        if (drops[i] * fontSize > canvas.height || Math.random() > 0.95) {
                            drops[i] = 0;
                        }
                        drops[i]++;
                    }
                }

                setInterval(draw, 33);
            },
            input() {
                const that = this;
                setTimeout(() => {
                    that.tip = that.nameTip.slice(0, that.start);
                    console.log(that.tip);
                    that.start++;
                    if (that.tip.length < that.nameTip.length) {
                        that.input();
                    } else {
                        this.canShow = true;
                    }
                }, 100);
            },
            focus() {
                document.addEventListener("mousedown", function (e) {
                    if (e.target.id !== "input") {
                        e.preventDefault()
                    }
                }, false);
            },
            enter() {
                document.onkeydown = (event) => {
                    if (event.keyCode == 13) { //这是键盘的enter监听事件
                        if (this.value === undefined || this.value === "") {
                            return;
                        }
                        if (this.tip === this.nameTip) {
                            this.$refs.input.type = "password";
                            this.tip = this.passwordTip;
                            this.loginName = this.value;
                            this.value = "";
                        } else {
                            //提交请求
                            LoginServer.login({
                                "username": this.loginName,
                                "password": this.value
                            }).then(() => {

                            }).catch(() => {

                            })

                        }
                    }
                }
            }
        },
        created() {

        },
        mounted() {
            this.bg_js();
            this.input();
            this.focus();
            this.enter();
        }
    }
</script>

<style scoped>
    .canvas {
        width: 100%;
        height: 100%;
        background: black;
    }

    .page-warp {
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .login-warp {
        position: absolute;
        width: 100%;
        height: 100px;
        z-index: 1000;
        display: flex;
        flex-direction: row;
    }

    .input {
        width: max-content;
        height: 100%;
        border: none;
        font-size: 32px;
        padding-left: 30px;
        color: gray;
        background: transparent;
        caret-color: red !important;
    }

    .input2 {
        display: flex;
        flex-direction: column;
        justify-content: center;
        flex: 0;
        color: #5d5d5d;
        font-size: 50px;
    }

    .input3 {
        flex: 1;
        margin-left: 10px;
        color: #5d5d5d;
    }
</style>
