import QuestionPresenterInstance from '../presenter/QuestionPresenter'
import TagPresenterInstance from '../presenter/TagPresenter'
import CreateQuestion from '../presentational/CreateQuestion'
import { useEffect, useState } from 'react';
import RestClient from '../rest/RestClient';
import AppPresenter from '../presenter/AppPresenter'

function SmartCreateQuestion() {
    const [question, setQuestion] = useState({
        title: "",
        body: "",
        author: "",
        createTime: "",
        tags: ["hello"],
        upvotes: 0,
        downvotes: 0
    });

    const [loggedUser, setLoggedUser] = useState({
        email: "",
        name: "",
        password: "",
        score: 0,
        username: ""
    })

    const [tagText, setTagText] = useState({
        text: ""
    });

    const [allTags, setTags] = useState({
        tags: []
    })

    useEffect(() => {
        AppPresenter.getLoggedUser().then(lgdUser => {
            if (lgdUser.name === "" && lgdUser.password === "") {
                window.location.assign("#/error");
            }
            const client = new RestClient(lgdUser.name, lgdUser.password);
            TagPresenterInstance.getTags(client).then(foundQuestions => {
                setTags({
                    tags: [...foundQuestions]
                })
            });
            setLoggedUser({
                ...lgdUser
            })
        })
    }, []);

    const onChange = (property, newValue) => {
        setQuestion({
            ...question,
            [property]: newValue
        });
    }

    const onChangeTagText = (newValue) => {
        setTagText({
            text: newValue
        });
    }

    const onSaveNewTag = () => {
        const client = new RestClient(loggedUser.name, loggedUser.password);
        TagPresenterInstance.saveTag(tagText,client).then(foundQuestions => {
            setTags({
                tags: [...foundQuestions]
            })
        });
        setQuestion({
            ...question,
            tags: [...question.tags, tagText.text]
        });
    }

    const onAddTag = (tag) => {
        setQuestion({
            ...question,
            tags: [...question.tags, tag]
        });
    }

    const onRemoveTag = (tag) => {
        console.log(tag)
        var tgs = question.tags;
        tgs.splice(tgs.indexOf(tag), 1);
        setQuestion({
            ...question,
            tags:tgs
        });
    }

    const onSave = () => {
        const client = new RestClient(loggedUser.name, loggedUser.password);
        QuestionPresenterInstance.addQuestion(question,client);
        window.location.assign("#/home");
    }

    return (<CreateQuestion title={question.title} body={question.body} tags={question.tags} allTags={allTags.tags} onChange={onChange} onRemoveTag={onRemoveTag} onAddTag={onAddTag} onSave={onSave} onChangeTagText={onChangeTagText} onSaveNewTag={onSaveNewTag} nwTag={tagText.text} />
    );
}
export default SmartCreateQuestion;