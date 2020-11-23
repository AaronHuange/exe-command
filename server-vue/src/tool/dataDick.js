import CommonService from "../api/common.service";
import { AjaxReturnState } from "../data/Ajax.data";

class DataDick {
    async getDataList(dictId, targetList) {
        await CommonService.getDataDict(dictId).then(async res => {
            if (res.data.resCode === AjaxReturnState.success) {
                targetList.push(...res.data.data.list)
            }
        }).catch(err => {

        })
    }

}

export default new DataDick();
