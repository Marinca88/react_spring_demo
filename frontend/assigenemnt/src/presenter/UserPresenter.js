import UserInstance from '../model/User';

class UserPresenter {
    addUser(newUser) {
        return UserInstance.addUser(newUser);
    }
    getUserByIndex(index) {
        return UserInstance.getUserByIndex(index);
    }
    getUserByUsername(username) {
        return UserInstance.getUserByUsername(username);
    }
}
const UserPresenterInstance = new UserPresenter();
export default UserPresenterInstance;