class ImgToBase64 {
  canvas = null;

  constructor () {
    this.canvas = document.createElement("canvas");
  }

  getCanvas () {
    if (!this.canvas) {
      this.canvas = document.createElement("canvas");
    }
    return this.canvas;
  }

  destroyCanvas () {
    this.canvas = null;
  }

  loadImg (file) {
    const img = new Image();
    img.crossOrigin = "Anonymous";
    return new Promise((resolve, reject) => {
      img.onload = () => {
        resolve(img);
      };
      img.src = window.URL.createObjectURL(file);
      setTimeout(() => {
        reject(new Error("圖片加載超時"));
      }, 10000);
    });
    // const fs = new FileReader();
    // return new Promise((resolve, reject) => {
    //   fs.onload = () => {
    //     resolve(fs.result);
    //   };
    //   fs.readAsDataURL(file);
    //   setTimeout(() => {
    //     reject(new Error("圖片讀取超時"));
    //   }, 10000);
    // });
  }

  async transformImgToBase64 (file, outputFormat) {
    try {
      const img = await this.loadImg(file);
      img.width = 600;
      img.height = 800;
      const canvas = this.getCanvas();
      canvas.width = img.width;
      canvas.height = img.height;
      const ctx = canvas.getContext("2d");
      ctx.drawImage(img, 0, 0);
      const data = canvas.toDataURL("image/jpeg", 1);
      this.destroyCanvas();
      // const data = await this.loadImg(file);
      return data;
    } catch (error) {
      return false;
    }
  }
}

export default new ImgToBase64();
