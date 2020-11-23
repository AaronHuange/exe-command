
class DateTool {
  weekChineseNumber = ["一", "二", "三", "四", "五", "六", "日"];
  addZero (value) {
    return value < 10 ? `0${value}` : `${value}`;
  }
  transformDate2String (date) {
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    return `${year}-${this.addZero(month)}-${this.addZero(day)}`;
  }
  transformDateTime2String (date) {
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const hour = date.getHours();
    const minutes = date.getMinutes();
    const second = date.getSeconds();
    return `${year}-${this.addZero(month)}-${this.addZero(day)} ${this.addZero(hour)}:${this.addZero(minutes)}:${this.addZero(second)}`;
  }

  getNMonthBefore (n) {
    let resultMonth;
    const currentDate = new Date();
    let year = currentDate.getFullYear();
    let month = currentDate.getMonth() + 1;
    let date = currentDate.getDate();
    if (n === 1) {
      switch (month) {
        case 1:
          month += (12 - n);
          year--;
          break;
        default:
          month -= n;
          break;
      }
    }
    if (n === 3) {
      switch (month) {
        case 1:
        case 2:
        case 3:
          month += (12 - n);
          year--;
          break;
        default:
          month -= n;
          break;
      }
    }
    if (n === 6) {
      switch (month) {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
          month += (12 - n);
          year--;
          break;
        default:
          month -= n;
          break;
      }
    }
    resultMonth = `${year}-${this.addZero(month)}-${this.addZero(date)}`;
    return resultMonth;
  }
}

export default new DateTool();
