const HttpServerConfig = {
    _prefixUrl: "",
    _suffixUrl: "",
    _preFilter: [],
    _afterFilter: [],
    _baseRequest: {},
    _baseResponse: {},
    _requestDataKey: "",
    _responseDataKey: "",
    _specialCodeHanderList: [],
    setPrefixUrl(prefixUrl) {
        this._prefixUrl = prefixUrl;
        return this;
    },
    setSuffixUrl(suffixUrl) {
        this._suffixUrl = suffixUrl;
        return this;
    },
    addPreFilter(code, handler) {
        const filter = {
            code,
            handler
        }
        this._preFilter.push(filter);
        return this;
    },
    addAfterFilter() {
    },
    setBaseRequest(baseRequest) {
        this._baseRequest = baseRequest;
        return this;
    },
    setBaseResponse(baseResponse) {
        this._baseResponse = baseResponse;
        return this;
    },
    setRequestDataKey(requestDataKey) {
        this._requestDataKey = requestDataKey;
        return this;
    },
    setResponseDataKey(responseDataKey) {
        this._responseDataKey = responseDataKey;
        return this;
    },
    addSpecialCodeHanderList(code, handler) {
        const specialCodeHander = {
            code,
            handler
        }
        this._specialCodeHanderList.push(filter);
        return this;
    }
};

module.exports = HttpServerConfig;
