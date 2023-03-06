import UserPresenter from '../presenter/UserPresenter'
import AppPresenter from '../presenter/AppPresenter'
import LogIn from '../presentational/LogIn'
import { useState } from 'react'

function SmartLogIn() {
    const [newUser, setNewUser] = useState({
        username: "",
        password:""
    });
    const [text, setNewText] = useState({
        text:""
    });

    const onChange = (property, newValue) => {
        setNewUser({
            ...newUser,
            [property]: newValue
        });
    }
    const onLogIn = () => {
        AppPresenter.setLoggedUserId(newUser.username, newUser.password).then(foundMessage => {
            console.log(foundMessage)
            setNewText({
                text: foundMessage
            }
            )
            if (foundMessage === "Logged") {
                window.location.assign("#/home");
            }

        });
        
       
    }

    return (
        <LogIn 
            username={newUser.username}
            password={newUser.password}
            onLogIn={onLogIn}
            onChange={onChange}
            text={text.text} />
        );


}
export default SmartLogIn;