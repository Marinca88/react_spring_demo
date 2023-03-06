import './App.css';
import SmartLogIn from '../src/container/SmartLogIn'
import SmartHome from "../src/container/SmartHome"
import SmartViewQuestion from "../src/container/SmrtViewQuestion"
import SmartCreateQuestion from '../src/container/SmartCreateQuestion'
import Error from '../src/presentational/ErrorPage'
import { HashRouter, Routes, Route } from "react-router-dom";

function App() {
    return <div className="App">
        <HashRouter>
            <Routes>
                <Route exact={true} element={<SmartHome />} path="/home" />
                <Route exact={true} element={<Error/>} path="/error" />
                <Route exact={true} element={<SmartLogIn />} path="/login" />
                <Route exact={true} element={<SmartCreateQuestion />} path="/create-question" />
                <Route exact={true} element={<SmartViewQuestion />} path="/question-details/:index" />
            </Routes>
        </HashRouter>
    </div>
}

export default App;
