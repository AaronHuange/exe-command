import axios from "axios";
import StringTool from "../tool/StringTool";
// eslint-disable-next-line no-unused-vars
// import Native from "../native/Native";
// eslint-disable-next-line no-unused-vars
import {
    AjaxGlobalReturnState,
    AjaxReturnState
} from "../data/Ajax.data";

// import { Toast } from "vant";
class AxiosConfig {
    // deviceId = "";
    token = "";
    // httpRequestPrefix = process.env.VUE_APP_REQUEST_ROOTPATH;
    uuid = "";

    createRequestNo() {
        return StringTool.randomString(32);
    }

    createSystemType() {
        return "H5";
    }

    createServiceName(path) {
        const pahtUrlArray = path.split("/");
        return pahtUrlArray[pahtUrlArray.length - 1];
    }

    addHttpRequestPrefix() {
        // axios.defaults.baseURL = this.httpRequestPrefix;
    }

    addCommonDataToRequest() {
        axios.interceptors.request.use((config) => {
            // const data = {
            //     requestSystem: this.createSystemType(),
            //     requestNo: this.createRequestNo(),
            //     serviceName: this.createServiceName(config.url),
            //     data: {
            //         token: this.token,
            //         uuid: this.uuid,
            //         deviceFingerprint: this.uuid,
            //         serviceName: this.createServiceName(config.url)
            //     }
            // };
            // config.headers = {
            //   token: this.token,
            //   uuid: this.uuid,
            //   appKey: this.appKey
            // };
            // if (config.method === "get") {
            //     if (!config.params) {
            //         config.params = {};
            //     }
            //     const params = config.params;
            //     data.data.data = params;
            //     config.params = data;
            // } else {
            //     if (!config.data) {
            //         config.data = {};
            //     }
            //     const requestData = config.data;
            //     data.data.data = requestData;
            //     config.data = data;
            // }
            config.headers['Content-Type'] = "application/json";
            return config;
        });
    }

    handleResponseData() {
        axios.interceptors.response.use((value) => {
            // return value;
            // Toast.clear();
            if (value.data.code === AjaxGlobalReturnState.success) {
                if (value.data.data.resCode === AjaxReturnState.serviceUnUse || value.data.data.resCode === AjaxReturnState.serviceFail || value.data.data.resCode === AjaxReturnState.tokenExpire) {
                    // Toast(value.data.data.resMessage);
                    setTimeout(() => {
                        // Native.appLogout();
                    }, 1500);
                }
                return value.data;
            } else {
                return {
                    data: {
                        resCode: value.data.code,
                        resMessage: value.data.message
                    }
                };
            }
        });
    }

    async init() {
        this.addHttpRequestPrefix();
        this.addCommonDataToRequest();
        // this.handleResponseData();
        // Native.getAppInfos().then(res => {
        //     this.token = res.token;
        //     this.uuid = res.deviceId;
        // })
    }
}

export default new AxiosConfig();
