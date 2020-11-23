
import { Toast } from "vant";

class ToastTool {
  showLoading (message) {
    Toast.loading({
      duration: 0,
      mask: true,
      message: message
    });
  }

  hideLoading () {
    Toast.clear();
  }

  showMessage (message) {
    this.hideLoading();
    Toast({
      duration: 2000,
      message: message
    });
    Toast(message);
  }

  success (message, onClose) {
    this.hideLoading();
    Toast.success({
      duration: 2000,
      message: message,
      onClose: onClose
    });
  }

  fail (message, onClose) {
    this.hideLoading();
    Toast.fail({
      duration: 2000,
      message: message,
      onClose: onClose
    });
  }
};

export default new ToastTool();
