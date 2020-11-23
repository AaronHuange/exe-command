import BaseTool from "./baseTool";

class ObjectTool extends BaseTool {
  copyValue (source, target, checkEmpty = false) {
    if (!source || !target) {
      return;
    }
    Object.keys(target).forEach(e => {
      if (Object.prototype.hasOwnProperty.call(source, e)) {
        if ((checkEmpty && source[e]) || !checkEmpty) {
          target[e] = source[e];
        }
      }
    });
  }
}

export default new ObjectTool();
