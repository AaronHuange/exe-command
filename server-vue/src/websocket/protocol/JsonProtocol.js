const JsonProtocol = {
    toObject(str) {
        //不判断是否是字符串了
        return JSON.parse(str);
    },
    toString(type, obj) {
        AMessage.type = type;
        AMessage.clientName = obj.username;
        AMessage.msg.password = obj.password;
        return JSON.stringify(AMessage);
    },
};


const AMessage = {
    type: "",
    clientName: "",
    msg: {}
};

module.exports = JsonProtocol;
