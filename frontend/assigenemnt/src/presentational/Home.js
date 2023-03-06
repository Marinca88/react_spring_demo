import 'bootstrap/dist/css/bootstrap.css';
import Background from '../images/temp.jpg';
import Bckground from '../images/scattered-forcefields.png'

const Home = ({ text, questions, onSearch, onChange, onSelect, onVote }) => (
    <div style={{ backgroundImage: `url(${Bckground})`}}>
        <nav class="navbar navbar-expand-lg navbar-light bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="#" style={{color:'white'}}>Home</a>
                <button
                    class="navbar-toggler"
                    type="button"
                    data-mdb-toggle="collapse"
                    data-mdb-target="#navbarTogglerDemo02"
                    aria-controls="navbarTogglerDemo02"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                >
                    <i class="fas fa-bars"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" href="#/create-question" style={{ color:'#4169E1'}}>AskQuestion</a>
                        </li>
                    </ul>
                    <form class="d-flex input-group w-auto">
                        <input
                            type="search"
                            class="form-control"
                            value={text}
                            onChange={e => onChange("text", e.target.value)}
                            aria-label="Search"
                        />
                        <button
                            class="btn"
                            type="submit"
                            onClick={onSearch}
                            data-mdb-ripple-color="dark"
                            style={{
                                color: "#4169E1",
                                borderBlockColor: "#4169E1",
                                borderLeftColor: "#4169E1",
                                borderRightColor: "#4169E1",
                                marginLeft:"7px"
                            }}
                        >
                            Search
                        </button>
                    </form>
                </div>
            </div>
        </nav>
        <br />
        <img src={Background} alt="Img" style={{
            width: '95%',
            height: '500px',
            border: " solid",
            position: "relative"
           
        }} />
        <div class="container" style={{
            width: '100%',
        }}>
            <h1 style={{ color: "#4169E1"}}> Questions</h1>
        </div>
        <div class="container">
            <br />
            <br />
            <div class="row">
                <div class="col-8">
            {
                questions.map((question, index) => (
                    <tr key={index}>
                        <div class="row" style={{
                            marginBottom: 15,
                            boxShadow: '0px 2px 2px rgba(0, 0, 0, 0.3)',
                            background: '#e6ecff',
                            width: 789,
                            padding: "5px",
                            border: "outset"
                        }}>
                            <div class="span8">
                                <div class="row">
                                    <div class="span8">
                                        <h4><strong><a>{ question.title}</a></strong></h4>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="span6">
                                        <p>
                                            {question.body}
                                        </p>
                                        <div class="form-group">
                                            <button type="button" value={question.title}  onClick={e => onSelect(e.target.value)} >View</button>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="span8">
                                        <p></p>
                                        <p>
                                            <i class="icon-user"></i> by <a href="#">{question.author}</a>
                                            | <i class="icon-calendar"></i> {question.createTime}
                                            | <i class="icon-tags"></i> Tags :

                                            {question.tags.map((tag, i) => (
                                                <tr key={i}>
                                                    <a href="#" style={{ marginLeft:"350px" }}><span class="label label-info">{tag}</span></a>
                                                </tr>))}
                                        </p>
                                    </div>
                                </div>
                                <div class="row" style={{ marginRight: "80%", display: "inline-flex" }}>
                                    <p>
                                        <a><h1>{question.upvotes - question.downvotes}</h1></a>
                                        <div class="form-group">
                                            <button type="button" onClick={e => onVote("up", question.title, question.body, question.author, question.createTime, question.upvotes, question.downvotes)} style={{ borderRadius: "50%", borderColor: "transparent", background: "transparent" }}><a style={{ fontSize: "30px" }}>&#129327;</a></button>
                                            <button type="button" onClick={e => onVote("down", question.title, question.body, question.author, question.createTime, question.upvotes, question.downvotes)} style={{ borderRadius: "50%", borderColor: "transparent", background: "transparent" }}><a style={{ fontSize: "30px" }}>&#128531;</a></button>
                                        </div>
                                        </p>
                                </div>
                            </div>


                        </div>
                    </tr>
                ))
                    }
                </div>
                <div class="col-4" style={{
                    height:'500px'
                }}>
                    <div class="card" style={{
                        marginTop: '30px',
                        backgroundColor:'#e6ecff'
                    }}>
                            <div class="card-body">
                            <h5 class="card-title">Use These 7 Tips to Help You Learn Computer Programming Faster</h5>
                            </div>
                            <ul class="list-group list-group-flush">
                            <li class="list-group-item">Focus on the Fundamentals</li>
                            <li class="list-group-item">Learn to Ask for Help</li>
                            <li class="list-group-item">Put Your Knowledge into Action</li>
                            <li class="list-group-item">Learn How to Code by Hand</li>
                            <li class="list-group-item">Check out Helpful Online Coding Resources</li>
                            <li class="list-group-item">Know When to Step Away and Take a Break from Code Debugging</li>
                            <li class="list-group-item">Do More Than Just Read Sample Code</li>
                            </ul>
                            <div class="card-body">
                            <span>&#128521; Good luck coding !!</span>
                            </div>
</div>
                </div>
                </div>
        </div>
        

    </div>
);
export default Home;