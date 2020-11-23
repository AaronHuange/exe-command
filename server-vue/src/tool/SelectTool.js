
class SelectTool {
  optionsFactory (obj) {
    const keys = Object.keys(obj);
    const options = [];
    for (let i = 0; i < keys.length; i++) {
      options.push(obj[keys[i]].text);
    }
    return options;
  }
}

export default new SelectTool();
