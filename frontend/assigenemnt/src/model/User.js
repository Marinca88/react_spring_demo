class User {
    constructor() {
        this.state = {
            users: [{
                name: "User1",
                username: "Username1",
                password: "password",
                score: 0,
                email: "email1@yahoo.com"
            }, {
                name: "User2",
                username: "Username2",
                password: "password",
                score: 0,
                email: "email2@yahoo.com"
            }
            ]
        };
    }

    addUser(newUser) {
        this.state.users = [
            ...this.state.users,
            newUser
        ];
    }

    getUserByIndex(index) {
        return this.state.users[index];
    }

    getUserByUsername(username) {
        return this.state.users.filter(item => item.username.match(username));
    }


}
const UserInstance = new User();
export default UserInstance;