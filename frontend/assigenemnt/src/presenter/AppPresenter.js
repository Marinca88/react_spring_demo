import AppInstance from '../model/AppState';
import RestClient from '../rest/RestClient';

const client = new RestClient("username1", "password");
class AppPresenter {
    async getLoggedUser() {
        var user = await client.loadUser(1)
        return user;
    }

    async setLoggedUserId(username,password) {
        var message = await client.loadLoginText(username, password)
        if (message.message === "Logged") {
            var m = await client.editUser({
                username: username,
                password: password
            })
        }
        return message.message;
    }

}
const AppPresenterInstance = new AppPresenter();
export default AppPresenterInstance;