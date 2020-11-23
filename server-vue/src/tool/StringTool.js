import BaseTool from "./baseTool";
import MathTool from "./MathTool";

class StringTool extends BaseTool {
  allLetters = "QWERTYUIOPASDFGHJKLZXCVBNM";
  allNumber = "1234567890";

  randomString (length) {
    const allChar = this.allLetters + this.allNumber;
    let result = "";
    for (let i = 0; i < length; i++) {
      result += allChar[MathTool.randomNumber(0, allChar.length - 1)];
    }
    return result;
  }

  spliceString (mobile) {
    let string1 = "";
    let string2 = "";
    let string3 = "";
    let arr = [];
    if (mobile.indexOf("-") !== -1) {
      string1 = mobile.substring(0, mobile.indexOf("-"));
      if (mobile.substring(mobile.indexOf("-") + 1, mobile.length).indexOf("#") !== -1) {
        string2 = mobile.substring(mobile.indexOf("-") + 1, mobile.indexOf("#"));
        string3 = mobile.substring(mobile.indexOf("#") + 1, mobile.length);
      } else {
        string2 = mobile.substring(mobile.indexOf("-") + 1, mobile.length);
      }
    } else {
      if (mobile.indexOf("#") !== -1) {
        string2 = mobile.substring(0, mobile.indexOf("#"));
        string3 = mobile.substring(mobile.indexOf("#") + 1, mobile.length);
      }
    }
    arr.push(string1, string2, string3);
    return arr;
  }
}

export default new StringTool();
