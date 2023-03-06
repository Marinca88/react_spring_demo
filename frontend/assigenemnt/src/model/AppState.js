class AppState {
    constructor() {
        this.state = {
            username: "",
            password: ""

        }
    }

    getCredeentials() {
        console.log(this.state)
        return this.state;
    }

    setCredentials(username, password) {
        this.state = {
            username: username,
            password: password
        }

        console.log(this.state)
    }

    getUsername() {
        return this.state.username;
    }

    getPassword() {
        return this.state.password;
    }
}
const AppInstance = new AppState();
export default AppInstance;