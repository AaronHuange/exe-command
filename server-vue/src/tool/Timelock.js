
class TimeLock {
  /**
   * 为一个函数添加时间锁 fn函数在每一次调用成功后的time时间长度内都不会调用成功
   * @param {Function} fn 加锁的函数
   * @param {number} time 时间长度
   */
  addLock (fn, time) {
    let oldTime = 0;
    return (...arg) => {
      const newTime = Date.now();
      if (newTime - oldTime >= time) {
        oldTime = newTime;
        console.log(arg);
        fn();
      }
    };
  }
}

export default new TimeLock();
