import QuestionPresenterInstance from '../presenter/QuestionPresenter'
import AppPresneter from "../presenter/AppPresenter"
import Home from '../presentational/Home'
import Error from '../presentational/ErrorPage'
import VotesPresenterInstance from '../presenter/VotesPresenter'
import { useEffect, useState } from 'react';
import RestClient from '../rest/RestClient';

function SmartHome() {
    const [text, setNewText] = useState({
        text:""
    });
    const [questions, setNewQuestions] = useState({
        questions: []
    });
    const [loggedUser, setLoggedUser] = useState({
        email: "",
        name: "",
        password: "",
        score: 0,
        username: ""
    })

    useEffect(() => {
        AppPresneter.getLoggedUser().then(lgdUser => {
            if (lgdUser.name === "" && lgdUser.password === "") {
                window.location.assign("#/error");
            }
            const client = new RestClient(lgdUser.name, lgdUser.password);
            QuestionPresenterInstance.getQuestion(client).then(foundQuestions => {
                setNewQuestions({
                    questions: [...foundQuestions]
                }
                )
            });
            setLoggedUser({
                ...lgdUser
            })
        });
    }, []);


    const onChange = (property, newValue) => {
        setNewText({
            [property]: newValue
        });
    }
    const onSearch = () => {
        const client = new RestClient(loggedUser.name,loggedUser.password);
        QuestionPresenterInstance.getQuestionByText(text.text,client).then(foundQuestions => {
            setNewQuestions({
                questions: [...foundQuestions]
            })
        });

    }

    const onSelect = (title) => {
        const client = new RestClient(loggedUser.name, loggedUser.password);
        QuestionPresenterInstance.getQuestion(client).then(foundQuestions => {
            setNewQuestions({
                questions: [...foundQuestions]
            }
            )
        });
        var index = questions.questions.indexOf(questions.questions.find(element => element.title === title))
        if (index === undefined) {
            window.location.assign("#/error");
        } {
            window.location.assign("/#/question-details/" + index)
        }
    }

    const onVote = (ud, title, body, user, createTime, upvotes, downvotes) => {
        const client = new RestClient(loggedUser.name, loggedUser.password);
        const questionsi = questions.questions
        const quest = questionsi.find(q => q.title === title && q.body === body && q.createTime === createTime && q.author === user && q.upvotes === upvotes && q.downvotes === downvotes );
        if (ud === "up") {
            QuestionPresenterInstance.upvote(quest,client).then(foundQuestions => {
                setNewQuestions({
                    questions: [...foundQuestions]
                })
            });
            //console.log(questions.questions);

            } else {
                if (ud === "down") {
                    QuestionPresenterInstance.downvote(quest,client).then(foundQuestions => {
                        setNewQuestions({
                            questions: [...foundQuestions]
                        })
                    });

                }
        }
    }


        return (
            <Home text={text.text}
                questions={questions.questions}
                onSearch={onSearch}
                onChange={onChange}
                onSelect={onSelect}
                onVote={onVote}
            />
        );
}
export default SmartHome;