const BASE_URL = "http://localhost:8080";
class RestClient {
	constructor(username, password) {
		console.log(username)
		console.log(password)
		this.authorization = "Basic " + btoa(username + ":" + password);
	}

	UpVotedQuestion = (title, body, createTime, author, upvotes, downvotes, tags) => {
		console.log(tags);
		return fetch(`${BASE_URL}/stackoverflow/uvoteQuestion`, {
			method: "PUT",
			body: JSON.stringify({
				"title": title,
				"body": body,
				"author": author,
				"tags": tags,
				"upvotes": upvotes,
				"downvotes": downvotes
			}),
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}

	DownVotedQuestion = (title, body, createTime, author, upvotes, downvotes, tags) => {
		return fetch(`${BASE_URL}/stackoverflow/dvoteQuestion`, {
			method: "PUT",
			body: JSON.stringify({
				"title": title,
				"body": body,
				"author": author,
				"tags": tags,
				"upvotes": upvotes,
				"downvotes": downvotes
			}),
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}

	loadUser = (id) => {
		return fetch(`${BASE_URL}/stackoverflow/getUser${id}`, {
			method: "GET",
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}

	editUser = (user) => {
		return fetch(`${BASE_URL}/stackoverflow/updateProfile`, {
			method: "PUT",
			body: JSON.stringify({
				"name": user.username,
				"username": "admin",
				"email": "login",
				"password": user.password,
				"score": 0
			}),
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
    }

	loadAllStudents = () => {
		console.log(this.authorization)
		return fetch(`${BASE_URL}/stackoverflow`, {
			method: "GET",
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}

	loadSerachedQuestions = (text) => {
		return fetch(`${BASE_URL}/stackoverflow/search?text=${text}`, {
			method: "POST",
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}

	loadUpdatedQuestion = (title, body, createTime, author, upvotes, downvotes, tags) => {
		console.log(tags);
		return fetch(`${BASE_URL}/stackoverflow/upvoteQuestion`, {
			method: "PUT",
			body: JSON.stringify({
				"title": title,
				"body": body,
				"author": author,
				"tags": tags,
				"upvotes": upvotes,
				"downvotes":downvotes
            }),
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}

	loadUpdatedDownQuestion = (title, body, createTime, author, upvotes, downvotes, tags) => {
		console.log(tags);
		return fetch(`${BASE_URL}/stackoverflow/downQuestion`, {
			method: "PUT",
			body: JSON.stringify({
				"title": title,
				"body": body,
				"author": author,
				"tags": tags,
				"upvotes": upvotes,
				"downvotes": downvotes
			}),
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}

	loadAnswaresForQuestion = (id) => {
		return fetch(`${BASE_URL}/stackoverflow/questionAnswares${id}`, {
			method: "GET",
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}

	upvoteAnsware = (id, questionId) => {
		return fetch(`${BASE_URL}/stackoverflow/like${id}?questionId=${questionId}`, {
			method: "PUT",
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}

	downvoteAnsware = (id, questionId) => {
		return fetch(`${BASE_URL}/stackoverflow/dislike${id}?questionId=${questionId}`, {
			method: "PUT",
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}

	loadAllAnswares = () => {
		return fetch(`${BASE_URL}/stackoverflow/allAnswares`, {
			method: "GET",
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json()
		}
		);
	}

	editAnsware = (id,text) => {
		return fetch(`${BASE_URL}/stackoverflow/updateAnsware${id}?text=${text}`, {
			method: "PUT",
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}

	loadAllTags = () => {
		return fetch(`${BASE_URL}/stackoverflow/allTags`, {
			method: "GET",
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json()
		}
		);
	}


	loadRefreshedAnswares = (upvotes,downvotes,text,question_id) => {
		return fetch(`${BASE_URL}/stackoverflow/createAnsware`, {
			method: "POST",
			body: JSON.stringify({
				"upvotes": upvotes,
				"downvotes": downvotes,
				"text": text,
				"creation_date":"date",
				"question_id": question_id,
				"author": "author"
			}),
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}

	createQuestion = (question) => {
		return fetch(`${BASE_URL}/stackoverflow/createQuestion`, {
			method: "POST",
			body: JSON.stringify({
				"title": question.title,
				"body": question.body,
				"createTime": question.createTime,
				"author": question.author,
				"tags": question.tags,
				"upvotes": question.upvotes,
				"downvotes": question.downvotes
			}),
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}

	loadLoginText = (username, password) => {
		console.log(password)
		return fetch(`${BASE_URL}/stackoverflow/log?username=${username}&password=${password}`, {
			method: "GET",
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}


	createTag = (tag) => {
		return fetch(`${BASE_URL}/stackoverflow/createTag`, {
			method: "POST",
			body: JSON.stringify({
				"text": tag.text
			}),
			headers: {
				"Authorization": this.authorization,
				"Content-Type": "application/json"
			},
		}).then((response) => {
			return response.json();
		}
		);
	}


}
export default RestClient;