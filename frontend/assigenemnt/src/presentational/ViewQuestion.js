import 'bootstrap/dist/css/bootstrap.css';
import Bckground from '../images/scattered-forcefields.png'

const ViewQuestion = ({ title, body, createTime, tags, answares, text, onChangeTextArea, onAddAnsware, onOpenEditAnsware, onEditAnsware, onChAnsware, onVote, upvotes,downvotes}) => (
    <div style={{
        backgroundImage: `url(${Bckground})`,
        height: '100%',
        width: "100%"
    }}>

        <div class="form-group" style={{
            float: 'right',
            marginRight: '5px'
        }}>
            <button type="button" onClick={() => {
                var x = document.getElementById("form-floating")
                x.style.visibility = "visible"
                var y = document.getElementById("addAnsware")
                y.style.visibility = "visible"
                var z = document.getElementById("edit-answare")
                z.style.visibility = "hidden"
                onChangeTextArea("text","")
            }} > Add Answare</button>
        </div>
        <div class="card w-75 " style={{
            margin: 'auto',
            width: '50%',
            border: '3px',
            padding: '10px',
            background:'transparent'
        }}>
            <div class="card-body" style={{ borderBottomColor: '#191970', borderBottom: 'solid' }}>
                <div style={{ marginLeft: "80%" }}>
                    <h1>{upvotes - downvotes}</h1>
                    <p>
                        <div class="form-group">
                            <button type="button" onClick={e => onVote("up", "question", "")} style={{ borderRadius: "50%", borderColor: "transparent", background: "transparent" }}><a style={{ fontSize: "30px" }}>&#129327;</a></button>
                            <button type="button" onClick={e => onVote("down", "question","")} style={{ borderRadius: "50%", borderColor: "transparent", background: "transparent" }}><a style={{ fontSize: "30px" }}>&#128531;</a></button>
                        </div>
                    </p>
                </div>
                <h5 class="card-title">{title}</h5>
                <p class="card-text">{body}</p>
                <p>{createTime}</p>
                <div class="btn-group" role="group" aria-label="Basic example">
                    {
                        tags.map((tag, index) => (
                            <tr key={index}>
                                <button type="button" class="btn btn-primary" style={{ marginLeft:"3px" }}>{tag}</button>
                            </tr>
                        ))
                    }
                </div>
            </div>
            <ul class="list-group" >
                {
                    answares.map((answare, index) => (
                        <tr key={index}>
                            <li class="list-group-item" style={{ background: "transparent" }}>
                                <div style={{ marginLeft: "80%" }}>
                                    <h1>{answare.upvotes - answare.downvotes}</h1>
                                    <p>
                                        <div class="form-group">
                                            <button type="button" onClick={e => onVote("up","answare", answare)} style={{ borderRadius: "50%", borderColor: "transparent", background: "transparent" }}><a style={{ fontSize: "30px" }}>&#129327;</a></button>
                                            <button type="button" onClick={e => onVote("down","answare", answare)} style={{ borderRadius: "50%", borderColor: "transparent", background: "transparent" }}><a style={{ fontSize: "30px" }}>&#128531;</a></button>
                                        </div>
                                    </p>
                                </div>
                                <p>{answare.text}</p>
                                <p>{answare.authorUsername}</p>
                                <p>{answare.createTime}</p>
                                <button type="button" value={answare.text} onClick={e => {
                                    onOpenEditAnsware("text", e.target.value)
                                    onChAnsware(e.target.value)
                                    var x = document.getElementById("form-floating")
                                    x.style.visibility = "visible"
                                    var y = document.getElementById("addAnsware")
                                    y.style.visibility = "hidden"
                                    var z = document.getElementById("edit-answare")
                                    z.style.visibility = "visible"
                                }} style={{ marginLeft: "80%", marginBottom: "50px" }}>Edit</button>
                            </li>
                        </tr>
                    ))
                }
            </ul>
            <div class="form-floating" id="form-floating" style={{visibility:"hidden"}}>
                <textarea class="form-control" value={text} onChange={e => onChangeTextArea("text", e.target.value)}></textarea>
                <label for="floatingTextarea">Text</label>
                <button type="submit" class="btn btn-primary" id="addAnsware" onClick={() => {
                    var x = document.getElementById("form-floating")
                    x.style.visibility = "hidden"
                    var y = document.getElementById("addAnsware")
                    y.style.visibility = "hidden"
                    onAddAnsware()
                }} style={{ marginTop: "5px" }}>Answare</button>
                <button type="submit" class="btn btn-primary" id="edit-answare" onClick={() => {
                    var x = document.getElementById("form-floating")
                    x.style.visibility = "hidden"
                    var z = document.getElementById("edit-answare")
                    z.style.visibility = "hidden"
                    onEditAnsware()
                }} style={{ marginTop: "5px" }}>Edit</button>
            </div>
        </div>
    </div>
);
export default ViewQuestion;

