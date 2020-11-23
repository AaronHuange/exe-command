class RemController {
  baseFontSize = 16;
  baseWindowWidth = 375;
  global = null;
  constructor () {
    this.global = window;
  }
  findHtml () {
    return this.global.document.querySelector("html");
  }
  getCurrentWindowWidth () {
    return this.global.innerWidth;
  }
  computeFontSize () {
    const currentWindowWidth = this.getCurrentWindowWidth();
    const resizePersent = currentWindowWidth / this.baseWindowWidth;
    const currentFontSize = this.baseFontSize * resizePersent;
    return currentFontSize;
  }
  setHtmlFontSize (fontSize) {
    const html = this.findHtml();
    html.style.fontSize = fontSize + "px";
  }
  addResizeEvent () {
    this.global.addEventListener("resize", () => {
      this.setHtmlFontSize(this.computeFontSize());
    });
  }
  init () {
    this.setHtmlFontSize(this.computeFontSize());
    this.addResizeEvent();
  }
};

export default new RemController();
