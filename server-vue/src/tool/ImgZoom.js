class ImgZoom {
  canvas = null;
  canvasContext = null;
  canvasWidth = null;
  canvasHeight = null;
          canvasBaseZoom = 2;

  // imgTexture: HTMLImageElement = null;
  imgTexture = null;
  init = false;

  scale = {
    x: 0.5,
    y: 0.5
  };

  minScale = {
    x: 0,
    y: 0
  };

  position = {
    x: 0,
    y: 0
  };

  zoomCenterPoint = {
    x: 0,
    y: 0
  };

  lastZoomScale = null;
  lastX = null;
  lastY = null;

  imgLoadComplete = null;

  constructor (option) {
    if (!option.canvas) {
      throw new Error("Miss required parameter: canvas");
    } else if (!option.imgUrl) {
      throw new Error("Miss required parameter: imgUrl");
    }

    this.imgLoadComplete = option.imgLoadComplete;

    this.canvas = option.canvas;
    this.setCanvasBaseProp();

    this.initImg(option.imgUrl);

    window.requestAnimationFrame(this.animate.bind(this));

    this.addListener();
  }

  animate () {
    if (!this.init) {
      if (this.imgTexture.width) {
        const canvasRate = this.canvasWidth / this.canvasHeight;
        const imgRate = this.imgTexture.width / this.imgTexture.height;
        var scaleRatio = this.canvasWidth * 1 / this.imgTexture.width;
        if (canvasRate > imgRate) {
          scaleRatio = this.canvasHeight * 1 / this.imgTexture.height;
        }
        this.scale.x = scaleRatio;
        this.scale.y = scaleRatio;
        this.minScale.x = scaleRatio;
        this.minScale.y = scaleRatio;
        this.drawWholeImageOnCanvas();
        this.setImgCenter();
        if (this.imgLoadComplete !== null) {
          this.imgLoadComplete();
        }
        this.init = true;
      }
    }

    this.canvasContext.clearRect(0, 0, this.canvasWidth, this.canvasHeight);

    this.canvasContext.drawImage(
      this.imgTexture,
      this.position.x, this.position.y,
      this.imgTexture.width * this.scale.x, this.imgTexture.height * this.scale.y);

    window.requestAnimationFrame(this.animate.bind(this));
  }

  setCanvasBaseProp () {
    this.canvasContext = this.canvas.getContext("2d");
    this.canvasWidth = this.canvas.clientWidth * this.canvasBaseZoom;
    this.canvasHeight = this.canvas.clientHeight * this.canvasBaseZoom;
    this.canvas.width = this.canvasWidth;
    this.canvas.height = this.canvasHeight;
  }

  initImg (imgUrl) {
    this.imgTexture = new Image();
    this.imgTexture.src = imgUrl;
  }

  drawWholeImageOnCanvas () {
    this.canvasContext.drawImage(this.imgTexture, -this.imgTexture.width, -this.imgTexture.height, this.imgTexture.width, this.imgTexture.height);
  }

  setImgCenter () {
    this.position.x = (this.canvasWidth - this.imgTexture.width * this.scale.x) / 2;
    this.position.y = (this.canvasHeight - this.imgTexture.height * this.scale.y) / 2;
  }

  addListener () {
    this.canvas.addEventListener("touchstart", () => {
      this.lastX = null;
      this.lastY = null;
      this.lastZoomScale = null;
    });

    this.canvas.addEventListener("touchmove", (e) => {
      e.preventDefault();
      if (e.targetTouches.length === 2) {
        this.doZoom(this.gesturePinchZoom(e));
      } else if (e.targetTouches.length === 1) {
        const relativeX = e.targetTouches[0].clientX;
        const relativeY = e.targetTouches[0].clientY;
        this.doMove(relativeX, relativeY);
      }
    });
  }

  gesturePinchZoom (event) {
    let zoom = false;
    if (event.targetTouches.length >= 2) {
      let p1 = event.targetTouches[0];
      let p2 = event.targetTouches[1];
      const p2ClientX = p2.clientX * this.canvasBaseZoom;
      const p2ClientY = p2.clientY * this.canvasBaseZoom;
      const p1ClientX = p1.clientX * this.canvasBaseZoom;
      const p1ClientY = p1.clientY * this.canvasBaseZoom;
      let zoomScale = Math.sqrt(Math.pow(p2ClientX - p1ClientX, 2) + Math.pow(p2ClientY - p1ClientY, 2));

      this.zoomCenterPoint.x = Math.abs((p2ClientX - p1ClientX)) / 2 + (p1ClientX < p2ClientX ? p1ClientX : p2ClientX) - this.position.x;
      this.zoomCenterPoint.y = Math.abs((p2ClientY - p1ClientY)) / 2 + (p1ClientY < p2ClientY ? p1ClientY : p2ClientY) - this.position.y;
      if (this.lastZoomScale) {
        zoom = zoomScale - this.lastZoomScale;
      }
      this.lastZoomScale = zoomScale;
    }
    return zoom;
  }

  doMove (relativeX, relativeY) {
    if (this.lastX && this.lastY) {
      const deltaX = relativeX - this.lastX;
      const deltaY = relativeY - this.lastY;
      this.position.x += deltaX * this.canvasBaseZoom;
      this.position.y += deltaY * this.canvasBaseZoom;
    }

    const currentWidth = this.imgTexture.width * this.scale.x;
    const currentHeight = this.imgTexture.height * this.scale.y;

    if (currentWidth > this.canvasWidth) {
      if (this.position.x > 0) {
        this.position.x = 0;
      } else if (this.position.x + currentWidth < this.canvasWidth) {
        this.position.x = this.canvasWidth - currentWidth;
      }
    } else {
      if (this.position.x < 0) {
        this.position.x = 0;
      } else if (this.position.x + currentWidth > this.canvasWidth) {
        this.position.x = this.canvasWidth - currentWidth;
      }
    }

    if (currentHeight > this.canvasHeight) {
      if (this.position.y > 0) {
        this.position.y = 0;
      } else if (this.position.y + currentHeight < this.canvasHeight) {
        this.position.y = this.canvasHeight - currentHeight;
      }
    } else {
      if (this.position.y < 0) {
        this.position.y = 0;
      } else if (this.position.y + currentHeight > this.canvasHeight) {
        this.position.y = this.canvasHeight - currentHeight;
      }
    }
    this.lastX = relativeX;
    this.lastY = relativeY;
  }

  doZoom (zoom) {
    if (!zoom) return;

    let newScale = this.scale.x + zoom / 400;
    if (newScale < this.minScale.x) {
      this.scale.x = this.minScale.x;
      this.scale.y = this.minScale.y;
      return;
    }

    const beforeZoomImgWidth = this.imgTexture.width * this.scale.x;
    const beforeZoomImgHeight = this.imgTexture.height * this.scale.y;
    const afterZoomImgWidth = this.imgTexture.width * newScale;
    const afterZoomImgHeight = this.imgTexture.height * newScale;

    this.position.x = this.position.x - (afterZoomImgWidth - beforeZoomImgWidth) / 2;
    this.position.y = this.position.y - (afterZoomImgHeight - beforeZoomImgHeight) / 2;

    const afterZoomCenterPointX = this.zoomCenterPoint.x * (newScale / this.scale.x);
    const afterZoomCenterPointY = this.zoomCenterPoint.y * (newScale / this.scale.y);

    this.position.x = this.position.x + ((afterZoomImgWidth - beforeZoomImgWidth) / 2 + this.zoomCenterPoint.x - afterZoomCenterPointX);
    this.position.y = this.position.y + ((afterZoomImgHeight - beforeZoomImgHeight) / 2 + this.zoomCenterPoint.y - afterZoomCenterPointY);

    this.zoomCenterPoint.x = afterZoomCenterPointX;
    this.zoomCenterPoint.y = afterZoomCenterPointY;

    this.scale.x = newScale;
    this.scale.y = newScale;
  }
}

export default ImgZoom;
