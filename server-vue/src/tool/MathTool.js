import BaseTool from "./baseTool";

class MathTool extends BaseTool {
  /**
   * 将一个浮点类型值转放大成整数
   * @param {Number} value 需要转换的值
   * @return {Object} -> {
   *  floatInt: 整数部分
   *  floatIntStr: 整数部分(字符串)
   *  floatDecimal: 小数部分
   *  floatDecimalStr: 小数部分(字符串)
   *  allInteger：转换成的整数
   *  times: 放大倍数
   * }
   */
  floatToInt (value) {
    if (!(this.isNumber(value) && `${value}`.indexOf(".") !== -1)) {
      return {
        floatInt: value,
        floatIntStr: `${value}`,
        floatDecimal: null,
        floatDecimalStr: null,
        allInteger: value,
        times: 1
      };
    }
    const floatString = `${value}`;
    const floatDecimalStr = floatString.split(".")[1];
    const floatIntStr = floatString.split(".")[0];
    const allInteger = Number.parseInt(floatIntStr + floatDecimalStr, 10);
    const times = 10 ** (floatDecimalStr.length);
    const floatInt = Number.parseInt(floatIntStr, 10);
    const floatDecimal = Number.parseInt(floatDecimalStr, 10);
    return {
      floatInt,
      floatIntStr,
      floatDecimal,
      floatDecimalStr,
      allInteger,
      times
    };
  }

  /**
   * 浮点类型计算 加法 或 乘法
   * @param {String} sign 计算符号 '+' '*'
   * @param {...Number} valueArray 累加(乘)的一系列值
   * @return {Number} 计算结果
   */
  floatComputeAddorMul (sign, ...valueArray) {
    return valueArray.reduce((sum, currentValue) => {
      if (!this.isNumber(currentValue)) {
        return sum;
      }
      sum = this.floatToInt(sum);
      currentValue = this.floatToInt(currentValue);
      switch (sign) {
        case "+":
          if (sum.times === currentValue.times) {
            return ((sum.allInteger + currentValue.allInteger) / sum.times);
          }
          return sum.times > currentValue.times
            ? (sum.allInteger + (currentValue.allInteger * (sum.times / currentValue.times))) / sum.times
            : (currentValue.allInteger + (sum.allInteger * (currentValue.times / sum.times))) / currentValue.times;
        default:
          return (sum.allInteger * currentValue.allInteger) / (sum.times * currentValue.times);
      }
    });
  }

  /**
   * 浮点类型计算 减法 或 除法
   * @param {String} sign 计算符号 '-' '/'
   * @param {Number} firstvalue 被减数/被除数
   * @param {...Number} valueArray 累减(除)的一系列值
   * @return {Number} 计算结果
   */
  floatComputeSuborDiv (sign, firstvalue, ...valueArray) {
    return valueArray.reduce((sum, currentValue) => {
      if (!this.isNumber(currentValue)) {
        return sum;
      }
      sum = this.floatToInt(sum);
      currentValue = this.floatToInt(currentValue);
      switch (sign) {
        case "-":
          if (sum.times === currentValue.times) {
            return ((sum.allInteger - currentValue.allInteger) / sum.times);
          }
          return sum.times > currentValue.times
            ? (sum.allInteger - (currentValue.allInteger * (sum.times / currentValue.times))) / sum.times
            : (((sum.allInteger * (currentValue.times / sum.times)) - currentValue.allInteger) / currentValue.times);
        default: return (sum.allInteger / currentValue.allInteger) * (sum.times / currentValue.times);
      }
    }, firstvalue);
  }

  /**
   * 生成一个 [min, max] 的随机数
   * @param {Number} min 最小值
   * @param {Number} max 最大值
   */
  randomNumber (min, max) {
    if (!this.isNumber(min) || !this.isNumber(max)) {
      console.error("min 或 max 不是数字类型");
      return 0;
    }
    return Math.floor(Math.random() * (max - min + 1) + min);
  }

  /**
   * 仟分位
   * *@param {String} val 数值,
   */
  quantileMoney (val) {
    val = val.toString();
    if (val === "") {
      return "";
    }
    let decimal = "";
    let integer = "";
    if (val.indexOf(".") !== -1) {
      decimal = val.substring(val.indexOf("."));
      integer = val.substring(0, val.indexOf("."));
    } else {
      integer = val;
    }
    return integer.replace(/(\d)(?=(?:\d{3})+$)/g, "$1,") + decimal;
  }
}

export default new MathTool();
