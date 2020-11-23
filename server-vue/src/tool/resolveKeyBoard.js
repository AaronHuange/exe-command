class KeyBoard {
    offsetTop = 0;
    temp1 = 0;
    temp2 = 0;

    resolveKeyBoardCover(keyBoardHeight, offset) {
        this.offsetTop = offset;
        this.temp1 = document.body.scrollHeight - this.offsetTop;
        this.temp2 = (keyBoardHeight - this.temp1).toFixed(2);
        if (this.temp2 > 0) {
            // setTimeout(() => {
            //   el.scrollIntoView(true);
            // }, 100);
            let bounceHeight = (keyBoardHeight - 80) + "px";
            return bounceHeight;
        }
    }
}

export default new KeyBoard();