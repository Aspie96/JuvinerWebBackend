var templates = {};
var session;

function parseJwt(token) {
    var base64Url = token.split(".")[1];
    var base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    var jsonPayload = decodeURIComponent(atob(base64).split("").map(function(c) {
        return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(""));
    return JSON.parse(jsonPayload);
};

var auth;
if(auth = sessionStorage.getItem("auth")) {
	session = JSON.parse(auth);
}

function refreshToken(callback) {
	call("/users/oauth/token", function(response) {
	}, "POST", encodeData({
		client_id: "CLIENT_ID",
		client_secret: "CLIENT_SECRET",
		grant_type: "refresh_token",
		refresh_token: session.refreshToken
	}));
}

function call(api, callback, method, body, contentType) {
	if(typeof body == "object") {
		body = JSON.stringify(body);
		contentType =  "application/json; charset=utf-8";
	}
	var headers = {
		"Content-Type": contentType,
	};
	if(session) {
		headers["Authorization"] = "Bearer " + session.authToken;
	}
	fetch(api, {
		method: method,
		headers: headers,
		body: body
	})
		.then(function(response) {
			if(response.status == 401 && session.authToken) {
				refreshToken(function() {
					call(api, callback, method, body, contentType);
				});
			} else {
				var error;
				var data;
				if(!response.ok) {
					error = new Error("Response status is " + response.status);
					data = response;
				}
				response.json().then(function(data) {
					callback(data, error);
				});
			}
		})
		.catch(function(error) {
			callback(null, error);
		});
}

function encodeData(data) {
	var formBody = [];
	for(var property in data) {
		var encodedKey = encodeURIComponent(property);
		var encodedValue = encodeURIComponent(data[property]);
		formBody.push(encodedKey + "=" + encodedValue);
	}
	formBody = formBody.join("&");
	return formBody;
}

function updateMenu(callback) {
	getTemplate("menu", function(template) {
		document.querySelector("#menu").innerHTML = Mustache.render(template, {
			username: session.username
		});
		callback();
	});
}

function getTemplate(name, callback) {
	if(name in templates) {
		callback(templates[name]);
	} else {
		fetch("/templates/" + name + ".html")
			.then(function(response) {
				response.text().then(function(data) {
					Mustache.parse(data);
					templates[name] = data;
					callback(templates[name]);
				});
			});
	}
}

var main;

GoldenRoute.addRoute("/", function(params, query, callback) {
	call("/api/sections/root", function(data, error) {
		getTemplate("home", function(template) {
			main.innerHTML = Mustache.render(template, { root: data });
			callback("titolo");
		});
	});
});

GoldenRoute.addRoute("/thread/:id", function(params, query, callback) {
	call("/api/threads/" + params.id, function(thread, error) {
		call("/api/posts/thread/" + params.id, function(posts, error) {
			getTemplate("thread", function(template) {
				main.innerHTML = Mustache.render(template, {
					thread: thread,
					posts: posts
				});
				callback("titolo");
			});
		});
	});
});

GoldenRoute.addRoute("/category/:id", function(params, query, callback) {
	call("/api/sections/category/" + params.id, function(category, error) {
		call("/api/threads/category/" + params.id, function(threads, error) {
			getTemplate("category", function(template) {
				main.innerHTML = Mustache.render(template, {
					category: category,
					threads: threads
				});
				callback("titolo");
			});
		});
	});
});

GoldenRoute.addRoute("/user/self", function(params, query, callback) {
	call("/api/users/self", function(data, error) {
		getTemplate("user_details", function(template) {
			main.innerHTML = Mustache.render(template, { user: data });
			callback("titolo");
		});
	});
});

GoldenRoute.addRoute("/user/:id", function(params, query, callback) {
	call("/api/users/" + params.id, function(data, error) {
		getTemplate("user", function(template) {
			main.innerHTML = Mustache.render(template, { user: data });
			callback("titolo");
		});
	});
});

GoldenRoute.addRoute("/login", function(params, query, callback) {
	getTemplate("login", function(template) {
		main.innerHTML = Mustache.render(template, { error: "error" in query });
		callback("titolo");
	});
});

GoldenRoute.addRoute("/new", function(params, query, callback) {
	getTemplate("new_thread", function(template) {
		main.innerHTML = Mustache.render(template, {});
		callback("titolo");
	});
});

document.addEventListener("load", function() {
	GoldenRoute.start();
});

function onNewPostSubmit(form) {
	var thread_id = parseInt(form.attr("action").split("/")[2]);
	var text = form.text.value;
	call("/api/posts/thread" + thread_id, function(data, error) {
		GoldenRoute.routeTo("/thread/" + thread_id);
	}, "POST", {
		thread_id: parseInt(thread_id),
		text: text
	});
}

function onNewThreadSubmit(form) {
	var category_id = form.category_id.value;
	var text = form.text.value;
	var title = form.title.value;
	call("/api/threads", function(data, error) {
		GoldenRoute.routeTo("/thread/" + data.id);
	}, "POST", {
		category_id: parseInt(category_id),
		title: title,
		text: text
	});
	return false;
}

function onLogin(form) {
	var username = form.username.value;
	var password = form.password.value;
	call("/api/users/oauth/token", function(data, error) {
		if(!error) {
			session = {
				authToken: data.access_token,
				refreshToken: data.refresh_token,
				username: username
			};
			updateMenu(function() {
				GoldenRoute.routeTo("/");
			});
			sessionStorage.setItem("auth", JSON.stringify(session));
		} else {
			getTemplate("login", function(template) {
				main.innerHTML = Mustache.render(template, { error: true });
				callback("titolo");
			});
		}
	}, "POST", encodeData({
		client_id: "CLIENT_ID",
		client_secret: "CLIENT_SECRET",
		grant_type: "password",
		username: username,
		password: password
	}), "application/x-www-form-urlencoded");
	return false;
}

function onUserDetailsSubmit(form) {
	var description = form.description.value;
	call("/api/users/self", function(data, error) {
		GoldenRoute.routeTo("/user/self");
	}, "PUT", {
		description: description
	});
	return false;
}
