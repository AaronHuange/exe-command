
class Verification {
  phoneNumberRegexp = /^1[3,4,5,7,8,9][\d]{9}$/;
  checkPhoneNumber (phoneNumber) {
    return this.phoneNumberRegexp.test(phoneNumber);
  };

  cardRule = {
    "A": "10",
    "B": "11",
    "C": "12",
    "D": "13",
    "E": "14",
    "F": "15",
    "G": "16",
    "H": "17",
    "I": "34",
    "J": "18",
    "K": "19",
    "L": "20",
    "M": "21",
    "N": "22",
    "O": "35",
    "P": "23",
    "Q": "24",
    "R": "25",
    "S": "26",
    "T": "27",
    "U": "28",
    "V": "29",
    "W": "32",
    "X": "30",
    "Y": "31",
    "Z": "33"
    // "a": "10",
    // "b": "11",
    // "c": "12",
    // "d": "13",
    // "e": "14",
    // "f": "15",
    // "g": "16",
    // "h": "17",
    // "i": "34",
    // "j": "18",
    // "k": "19",
    // "l": "20",
    // "m": "21",
    // "n": "22",
    // "o": "35",
    // "p": "23",
    // "q": "24",
    // "r": "25",
    // "s": "26",
    // "t": "27",
    // "u": "28",
    // "v": "29",
    // "w": "32",
    // "x": "30",
    // "y": "31",
    // "z": "33"
  };

  checkIdCard (identityNo) {
    let regIdentity = /^[A-Z][1-2][0-9]{8}$/;
    if (!regIdentity.test(identityNo)) {
      return false;
    }
    let firstWord = identityNo.substring(0, 1);
    // 首字母转成数字
    let trans2Num = this.cardRule[firstWord];
    let totalNum = 0;
    let resideNum = trans2Num + identityNo.substring(1, 10);
    let ratioArr = [1, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1];
    for (let i = 0; i < resideNum.length; i++) {
      totalNum += parseInt(resideNum.substring(i, i + 1)) * ratioArr[i];
    }
    if (totalNum % 10 === 0) return true;
    return false;
  }
};

export default new Verification();
