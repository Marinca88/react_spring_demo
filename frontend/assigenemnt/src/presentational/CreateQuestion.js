import 'bootstrap/dist/css/bootstrap.css';


const CreateQuestion = ({ title, body, tags, allTags, onChange, onRemoveTag, onAddTag, onSave, onChangeTagText, onSaveNewTag, nwTag }) => (

    <div style={{ alignContent: 'center'}}>
    <form style={{
        marginBottom: 50,
        width: 789,
        padding: 30
    }}>
        <div class="form-group">
            <label>Title</label>
            <input type="text" class="form-control" value={title} required="required" onChange={e => onChange("title", e.target.value)} />
        </div>
        <div class="form-group">
            <label>Description</label>
            <textarea class="form-control" rows="3" value={body} onChange={e => onChange("body", e.target.value)}></textarea>
        </div>
        <div>
            <label >Tags</label>
            <ul class="list-group list-group-flush">
                {allTags.map((tag, i) => (
                    <tr key={i}>
                        <button type="button" class="btn btn-outline-primary" value={tag.text} onClick={e => onAddTag(e.target.value)} > {tag.text}</button>
                    </tr>))
                }
            </ul>
                <div className="tags-inputContainer" style={{
                    marginBottom: 15,
                    boxShadow: '0px 2px 2px rgba(0, 0, 0, 0.3)',
                    background: '#f7f7f7',
                    padding: 30
                }} >
                    <ul class="list-group list-group-flush">
                        {tags.map((tag, i) => (
                            <tr key={i}>
                                <div className="tag-instance" style={{ background: "#DAD8D8", display: 'inline-block', borderRadius: 20, paddingLeft: 6, paddingRight: 6, paddingTop: 6, paddingBottom:6 }}>
                                    <span className="text">{tag}</span>
                                    <button class="btn btn-primary btn-sm" style={{ borderRadius: "50%", marginLeft: 6 }} value={tag} onClick={e => onRemoveTag(e.target.value)}>&times;</button>
                                </div>
                            </tr>))
                        }
                    </ul>
                    <input type="text" class="form-control" value={nwTag} onChange={e => onChangeTagText(e.target.value)} />
                    <button type="submit" class="btn" onClick={onSaveNewTag}>AddNewTag</button>
                </div>
            <div class="form-group">
                <button type="submit" class="btn" onClick={onSave}>Save</button>
            </div>
        </div>
        </form>
        </div>
);
export default  CreateQuestion