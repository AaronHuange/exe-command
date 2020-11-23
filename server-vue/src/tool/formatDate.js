class FormatDate {
  formatDate (value, mode) {
    if (value !== "") {
      const year = value.substring(0, 4);
      const month = value.substring(4, 6);
      const day = value.substring(6, 8);
      switch (mode) {
        case "en":
          return `${year}-${month}-${day}`;
        case "cn":
          return `${year}年${month}月${day}日`;
      }
    }
  }
  formatMonth (value, mode) {
    if (value !== "") {
      const year = value.substring(0, value.length - 2);
      const month = value.substring(value.length - 2, value.length);

      // const year = value.substring(0, 4);
      // const month = value.substring(4, 6);
      switch (mode) {
        case "en":
          return `${year}-${month}`;
        case "cn":
          return `${year}年${month}月`;
      }
    }
  }

  formatYear (value, mode) {
    if (value !== "") {
      const year = value;
      switch (mode) {
        case "en":
          return `${year}`;
        case "cn":
          return `${year}年`;
      }
    }
  }

  formatTime (value) {
    if (value !== "") {
      const hour = value.substring(0, 2);
      const minute = value.substring(2, 4);
      return `${hour}:${minute}`;
    }
  }

  transformDate2String (date) {
    return date.replace(/-/g, "").replace(/\s*/g, "");
  }
}

export default new FormatDate();
