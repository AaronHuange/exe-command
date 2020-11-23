import Axios from "axios"

class LoginServer {

    login(data) {
        return Axios.post("/login", data)
    }


}

export default new LoginServer();
