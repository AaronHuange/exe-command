
class BaseTool {
  tag = {
    arrayTag: "[object Array]",
    boolTag: "[object Boolean]",
    dateTag: "[object Date]",
    errorTag: "[object Error]",
    funcTag: "[object Function]",
    mapTag: "[object Map]",
    numberTag: "[object Number]",
    nullTag: "[object Null]",
    objectTag: "[object Object]",
    promiseTag: "[object Promise]",
    regexpTag: "[object RegExp]",
    setTag: "[object Set]",
    stringTag: "[object String]",
    symbolTag: "[object Symbol]",
    undefinedTag: "[object Undefined]"
  }

  getValueTag (value) {
    if (value === null) {
      return value === undefined ? this.tag.undefinedTag : this.tag.nullTag;
    }
    return Object.prototype.toString.call(value);
  }

  isObject (value) {
    const valueTag = typeof value;
    return valueTag != null && (valueTag === "object");
  }

  isNull (value) {
    return value === null;
  }

  isUndefined (value) {
    return value === undefined;
  }

  isArray (value) {
    return Array.isArray(value);
  }

  isArrayLike (value) {
    if (!this.hasOwn.call(value, "length")) {
      return false;
    }
    const length = value.length;
    return typeof length === "number" && length >= 0 && length <= Number.MAX_SAFE_INTEGER;
  }

  isString (value) {
    return typeof value === "string" || this.getValueTag(value) === this.tag.stringTag;
  }

  isNumber (value) {
    const valueTag = typeof value;
    return (valueTag === "number" || (this.isObject(value) && this.getValueTag(value) === this.tag.numberTag)) && !Number.isNaN(value);
  }

  isMap (value) {
    return value != null && this.getValueTag(value) === this.tag.mapTag;
  }

  isSet (value) {
    return value != null && this.getValueTag(value) === this.tag.setTag;
  }
}

export default BaseTool;
