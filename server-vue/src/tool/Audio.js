
class Audio {
  audioElement = null;

  constructor () {
    this.audioElement = this.createAudioElement();
  }

  createAudioElement () {
    const element = document.createElement("audio");
    element.style.display = "none";
    // element.controls = "controls";
    // element.style.position = "fixed";
    // element.style.top = "0px";
    // element.style.left = "0px";
    document.querySelector("body").appendChild(element);
    return element;
  }

  setAudioPath (audioPath) {
    this.audioElement.src = audioPath;
    this.audioElement.currentTime = 0.0;
  }

  playNewAudio (audioPath) {
    this.setAudioPath(audioPath);
    this.playAudio();
  }

  playAudio () {
    this.audioElement.play();
  }

  pause () {
    this.audioElement.pause();
  }

  stopAudio () {
    this.audioElement.pause();
    this.audioElement.currentTime = 0.0;
  }

  onended (fn) {
    this.audioElement.onended = fn;
  }

  destroy () {
    this.audioElement.remove();
    this.audioElement = null;
  }
}

export default Audio;
