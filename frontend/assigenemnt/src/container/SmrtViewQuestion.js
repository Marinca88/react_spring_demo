import ViewQuestion from '../presentational/ViewQuestion'
import AnswarePresenterInstance from '../presenter/AnswarePresenter'
import QuestionPresenterInstance from '../presenter/QuestionPresenter'
import AppPresneter from "../presenter/AppPresenter"
import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import RestClient from '../rest/RestClient';


function SmartViewQuestion() {

    const [loggedUser, setLoggedUser] = useState({
        email: "",
        name: "",
        password: "",
        score: 0,
        username: ""
    })

    const [question, setQuestion] = useState({
        title: "",
        body: "",
        tags: [],
        createTime: "",
        author:"",
        upvotes: 0,
        downvotes: 0
    });

    const [editedValue, setEdit] = useState({
        text: ""
    });

    const { index } = useParams();

    const [text, setText] = useState({
        text: "",
        authorUsername: "username1",
        referenceQuestion: index,
        upvotes: 0,
        downvotes:0
    });

    const [chAnsware, setChAnsware] = useState({
        text: ""
    });

    const onChAnsware = (newValue) => {
        setChAnsware({
            text:newValue
        });
    }

    const [answares, setAnswares] = useState({
        answares: []
    });


    useEffect(() => {
        AppPresneter.getLoggedUser().then(lgdUser => {
            if (lgdUser.name === "" && lgdUser.password === "") {
                window.location.assign("#/error");
            }
            const client = new RestClient(lgdUser.name, lgdUser.password);
            QuestionPresenterInstance.getQuestionById(index,client).then(foundQuestion => {
                setQuestion({
                    ...foundQuestion
                }
                )
            });
            AnswarePresenterInstance.getAnswaresForQuestion(index,client).then(foundAnswares => {
                setAnswares({
                    answares: [...foundAnswares]
                }
                )
            });
            setLoggedUser({
                ...lgdUser
            })
        })
    }, []);


    const onChangeTextArea = (property, newValue) => {
        setText({
            ...text,
            [property]: newValue
        });
    }



    const onAddAnsware = () => {

        const client = new RestClient(loggedUser.name, loggedUser.password);
        AnswarePresenterInstance.addAnsware(text,client).then(foundAnswares => {
            setAnswares({
                answares: [...foundAnswares]
            }
            )
        });
        
    }

    const onOpenEditAnsware = (proprety,newValue) => {
        setText({
            ...text,
            [proprety]: newValue
        });
        setEdit({
            text: newValue
        });
    }

    const onEditAnsware = () => {
        var answaresi = AnswarePresenterInstance.getAnswares()
        var ind = answaresi.indexOf(answaresi.find(element => element.text === chAnsware.text))
        console.log(ind)
        const client = new RestClient(loggedUser.name, loggedUser.password);
        AnswarePresenterInstance.editAnsware(text,editedValue.text,client).then(foundAnswares => {
            setAnswares({
                answares: [...foundAnswares]
            }
            )
        });
        
    }

    const onVote = (ud, type, answare) => {
        const client = new RestClient(loggedUser.name, loggedUser.password);
        if (type === "question") {

            if (ud === "up") {

                QuestionPresenterInstance.uVote({ title: question.title, body: question.body, creationTime: question.createTime, author: question.author, upvotes: question.upvotes, downvotes: question.downvotes, tags: question.tags },client).then(
                    foundQuestions => {
                        var q = foundQuestions.filter(element => element.title === question.title && element.body === question.body && element.author === question.author);
                        setQuestion({
                            ...q[0]
                        }
                        )
                    }
                )
            } else {
                if (ud === "down") {
                    QuestionPresenterInstance.dVote({ title: question.title, body: question.body, creationTime: question.createTime, author: "", upvotes: question.upvotes, downvotes: question.downvotes, tags: question.tags }, client).then(
                        foundQuestions => {
                            var q = foundQuestions.filter(element => element.title === question.title && element.body === question.body && element.author === question.author);
                            setQuestion({
                                ...q[0]
                            }
                            )
                        }
                    )
                }
            }
        } else {
                if (ud === "up") {
                    AnswarePresenterInstance.upvote(index,answare,client).then(foundAnswares => {
                        setAnswares({
                            answares: [...foundAnswares]
                        }
                        )
                    });

                } else {
                    if (ud === "down") {
                        AnswarePresenterInstance.downvote(index,answare,client).then(foundAnswares => {
                            setAnswares({
                                answares: [...foundAnswares]
                            }
                            )
                        });
                    }
                }
           
        }
    }

    return (<ViewQuestion title={question.title}
        body={question.body}
        createTime={question.createTime}
        tags={question.tags}
        answares={answares.answares}
        text={text.text}
        onChangeTextArea={onChangeTextArea}
        onAddAnsware={onAddAnsware}
        onOpenEditAnsware={onOpenEditAnsware}
        onEditAnsware={onEditAnsware}
        onChAnsware={onChAnsware}
        onVote={onVote}
        upvotes={question.upvotes}
        downvotes={question.downvotes}
    />
    );
};
export default SmartViewQuestion
