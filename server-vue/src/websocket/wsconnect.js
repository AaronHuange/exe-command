const websocket = {
    sock: null,
    onopen: undefined,
    onclose: undefined,
    onmessage: undefined,
    onUrl: "",
    isRecon: false,
    on_open: function () {
        if (this.onopen && !this.isRecon) {
            this.onopen();
        }
        this.isRecon = false;
        // this.send_data(JSON.stringify({
        //     stype: "auth",
        //     ctype: "login",
        //     data: {
        //         name: "jianan",
        //         pwd: 123456
        //     }
        // }));
    },

    on_message: function (event) {
        if (this.onmessage) {
            this.onmessage(event.data);
        }
    },

    on_close: function () {
        if (this.onclose()) {
            this.close();
        } else {
            //最多尝试3次
            // this.isRecon = true;
            // this.connect(this.onUrl, this.onopen, this.onmessage, this.onclose);
        }
    },

    on_error: function () {
        // if (this.onclose()) {
        //     this.close();
        // } else {
        //     this.isRecon = true;
        //     this.connect(this.onUrl, this.onopen, this.onmessage, this.onclose);
        // }
    },

    close: function () {
        if (this.sock) {
            this.sock.close();
            this.sock = null;
        }


    },

    connect: function (url, open, message, close) {
        this.onUrl = url;
        this.onopen = open;
        this.onclose = close;
        this.onmessage = message;
        const WebSocket =window.WebSocket;
        this.sock = new WebSocket(url);
        this.sock.binaryType = "arraybuffer";
        this.sock.onopen = this.on_open.bind(this);
        this.sock.onmessage = this.on_message.bind(this);
        this.sock.onclose = this.on_close.bind(this);
        this.sock.onerror = this.on_error.bind(this);
    },

    send_data: function (data) {
        if (typeof data === "object") {
            data = JSON.stringify(data);
        }

        if (this.sock) {
            if (this.sock.readyState === 1) { //可能处理重新连接的时候，这时候不能发消息
                this.sock.send(data);
            }
        }
    },
}

module.exports = websocket;
