import 'bootstrap/dist/css/bootstrap.css';
import Background from '../images/background.jpg';


const LogIn = ({ username, password, onLogIn, onChange, text }) => (
    <div style={{
        backgroundImage: `url(${Background})`,
    }}>
        <div class="login-form" style={{
            marginLeft:100,
            width: 500,
            margin: 200
            
        }}>
            <div style={{
                marginBottom: 15,
                boxShadow:'0px 2px 2px rgba(0, 0, 0, 0.3)',
            background: '#f7f7f7',
            padding: 30
    }}>
                <h2 class="text-center">Log in</h2>
                <br/>
                <h4>{text}</h4>
                <br/>
                <div class="form-group">
                    <input type="text" class="form-control" value={username} onChange={e => onChange("username", e.target.value)} required="required"/>
        </div>
                    <div class="form-group">
                    <input type="password" class="form-control" value={password} onChange={e => onChange("password", e.target.value)} required="required"/>
        </div>
                        <div class="form-group">
                    <button type="submit" class="btn" onClick={onLogIn}>Log in</button>
                </div>
                <br/>
                <p class="text-center"><a href="#">Create an Account</a></p>
  
    </div>
                        
                    </div>
		</div>
);
export default LogIn;