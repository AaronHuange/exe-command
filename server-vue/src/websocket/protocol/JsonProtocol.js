const JsonProtocol = {
    _userId: "",
    setUserId(userId) {
        this._userId = userId;
    },
    toObject(str) {
        //不判断是否是字符串了
        return JSON.parse(str);
    },
    toString(type, obj) {
        AMessage.type = type;
        AMessage.userId = this._userId;
        AMessage.message = obj;
        return JSON.stringify(AMessage);
    },
};


const AMessage = {
    type: "",
    userId: "",
    message: {}
};

module.exports = JsonProtocol;
