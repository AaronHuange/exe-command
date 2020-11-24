import Axios from "axios"

const HttpServer = {
    _url: "",
    _requestMethod: "post",
    _params: {},
    url(uri) {
        this._url = uri;
        return this;
    },
    params(data) {
        this._params = data;
        return this;
    },
    post(success, fail) {
        Axios.post(this._url, this._params)
            .then(res => {



            })
            .catch(error => {
                fail("exception", error)
            })
    }


};
