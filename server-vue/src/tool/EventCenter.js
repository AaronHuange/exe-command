
class EventCenter {
  eventPool = new Map();
  eventIdPropName = "EVENT_CENTER_ID";
  eventIdBase = 0;

  broadcastEvent (eventName, data) {
    if (!this.eventPool.has(eventName)) {
      return;
    }
    const eventSet = this.eventPool.get(eventName);
    eventSet.forEach(e => {
      e(data);
    });
  }

  unSubscribe (eventName, callBackId) {
    const eventSet = this.eventPool.get(eventName);
    for (let i = 0; i < eventSet.length; i++) {
      const item = eventSet[i];
      if (item[this.eventIdPropName] === callBackId) {
        eventSet.splice(i, 1);
        break;
      }
    }
  }

  subscribe (eventName, callBack) {
    if (!this.eventPool.has(eventName)) {
      this.eventPool.set(eventName, []);
    }
    const eventSet = this.eventPool.get(eventName);
    this.eventIdBase += 1;
    callBack[this.eventIdPropName] = this.eventIdBase;
    eventSet.push(callBack);
    return this.eventIdBase;
  }

  clearAllSubscribeEvent (eventName) {
    this.eventPool.set(eventName, []);
  }

  deleteEventContainerFromEventPoot (eventName) {
    if (this.eventPool.has(eventName)) {
      this.eventPool.delete(eventName);
    }
  }

  addBroadCustomCallBackFunc (...func) {
    func.forEach(e => {
      if (Object.prototype.hasOwnProperty.call(window, e)) {
        throw new Error("函數重名");
      } else {
        window[e] = (responseData) => {
          this.broadcastEvent(e, responseData);
        };
      }
    });
  }

  addCustomCallBackFunc (funcName, func) {
    window[funcName] = func;
  }

  uninstallCallBackFunc (funcName) {
    if (Object.prototype.hasOwnProperty.call(window, funcName)) {
      delete window[funcName];
      if (this.eventPool.has(funcName)) {
        this.eventPool.delete(funcName);
      }
    }
  }
}

export default EventCenter;
