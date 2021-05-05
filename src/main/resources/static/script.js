var templates = {};
var session;
var storedNickname;
var storedEmail;
var storedGoogleUser;
var userDescription = null;

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
var page;
if(location.pathname != "/login") {
	page = location.pathname + location.search;
} else {
	page = "/";
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
	}).then(function(response) {
		if(response.status == 401 && session) {
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
			response.text().then(function(data) {
				if(data) {
					data = JSON.parse(data);
				}
				callback(data, error);
			});
		}
	}).catch(function(error) {
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
		data = {};
		if(session){
			data.username = session.username;
		}
		document.querySelector("#menu").innerHTML = Mustache.render(template, data);
		if(callback) {
			callback();
		}
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
				if(session) {
					for(var i in posts) {
						if(session.username == posts[i].username) {
							posts[i].mine = true;
						}
					}
				}
				var d = {
					thread: thread,
					posts: posts
				};
				if(session) {
					d.session = session.username;
				}
				main.innerHTML = Mustache.render(template, d);
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
		if(userDescription != null) {
			data.description = userDescription;
			userDescription = null;
		}
		getTemplate("user_details", function(template) {
			main.innerHTML = Mustache.render(template, { user: data });
			gapi.signin2.render("googleLogin", {
				onsuccess: onGoogleConnect
			});
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
		gapi.signin2.render("googleLogin", {
			onsuccess: onGoogleLogin
		});
		callback("titolo");
	});
});

GoldenRoute.addRoute("/new", function(params, query, callback) {
	call("/api/sections/", function(data, error) {
		getTemplate("new_thread", function(template) {
			main.innerHTML = Mustache.render(template, { sections: data });
			callback("titolo");
		});
	});
});

GoldenRoute.addRoute("/register", function(params, query, callback) {
	getTemplate("register", function(template) {
		var obj = { error: "error" in query };
		if(storedGoogleUser) {
			obj.username = storedGoogleUser.getBasicProfile().getName().replaceAll(" ", "");
			obj.email = storedGoogleUser.getBasicProfile().getEmail();
			obj.googleId = storedGoogleUser.getAuthResponse().id_token;
			obj.googleName = storedGoogleUser.getBasicProfile().getName();
			storedGoogleUser = null;
		}
		main.innerHTML = Mustache.render(template, obj);
		callback("titolo");
	});
});

window.addEventListener("storage",function(event) {
	if(event.key == "session") {
		session = JSON.parse(event.newValue);
		updateMenu();
	} else if(event.key == "getSession" && session) {
		localStorage.setItem("session", JSON.stringify(session));
	}
});

window.addEventListener("load", function() {
	GoldenRoute.start();
	main = document.querySelector("main");
	if(session) {
		updateMenu();
	}
	if(location.pathname == "/login/github") {
		var code = new URLSearchParams(location.search).get("code");
		call("/api/users/login/github", function(data, error) {
			if(!error) {
				session = {
					authToken: data.access_token,
					refreshToken: data.refresh_token,
					username: JSON.parse(atob(data.access_token.split('.')[1])).user_name
				};
				updateMenu(function() {
					GoldenRoute.routeTo("/");
				});
				sessionStorage.setItem("auth", JSON.stringify(session));
				localStorage.setItem("session", JSON.stringify(session));
			} else {
				getTemplate("login", function(template) {
					main.innerHTML = Mustache.render(template, { error: true });
				});
			}
		}, "POST", encodeData({
			idToken: code
		}), "application/x-www-form-urlencoded");
	}
	if(location.pathname == "/user/self/github") {
		var code = new URLSearchParams(location.search).get("code");
		call("/api/users/self", function(data, error) {
			GoldenRoute.routeTo("/user/self");
		}, "PUT", {
			githubToken: code
		});
		return false;
	}
	if(location.pathname.startsWith("/thread/") && session) {
		var thread_id = location.pathname.split("/")[2];
		call("/api/threads/" + thread_id, function(thread, error) {
			call("/api/posts/thread/" + thread_id, function(posts, error) {
				getTemplate("thread", function(template) {
					for(var i in posts) {
						if(session.username == posts[i].username) {
							posts[i].mine = true;
						}
					}
					var d = {
						thread: thread,
						posts: posts
					};
					if(session) {
						d.session = session.username;
					}
					main.innerHTML = Mustache.render(template, d);
				});
			});
		});
	}
	localStorage.setItem("getSession", "");
	localStorage.removeItem("getSession", "");
});

function onNewPostSubmit(form) {
	var thread_id = parseInt(form.getAttribute("action").split("/")[2]);
	var text = form.text.value;
	call("/api/posts/thread/" + thread_id, function(data, error) {
		GoldenRoute.routeTo("/thread/" + thread_id);
	}, "POST", {
		thread_id: parseInt(thread_id),
		text: text
	});
	return false;
}

function onNewThreadSubmit(form) {
	var category_id = form.category_id.value;
	var text = form.text.value;
	var title = form.title.value;
	call("/api/threads/", function(data, error) {
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
				GoldenRoute.routeTo(page);
			});
			sessionStorage.setItem("auth", JSON.stringify(session));
			localStorage.setItem("session", JSON.stringify(session));
		} else {
			getTemplate("login", function(template) {
				main.innerHTML = Mustache.render(template, { error: true });
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

function onGoogleLogin(googleUser) {
	var id_token = googleUser.getAuthResponse().id_token;
	call("/api/users/login/google", function(data, error) {
		gapi.auth2.getAuthInstance().signOut();
		if(!error) {
			var username = parseJwt(data.access_token).user_name;
			session = {
				authToken: data.access_token,
				refreshToken: data.refresh_token,
				username: username
			};
			updateMenu(function() {
				GoldenRoute.routeTo(page);
			});
			sessionStorage.setItem("auth", JSON.stringify(session));
			localStorage.setItem("session", JSON.stringify(session));
		} else {
			storedGoogleUser = googleUser;
			GoldenRoute.routeTo("/register");
		}
	}, "POST", encodeData({
		idToken: id_token
	}), "application/x-www-form-urlencoded");
}

function onPostDelete(button) {
	var first = document.querySelectorAll(".delete-button")[0];
	if(button == first) {
		$("#deleteThreadModal").modal();
	} else {
		window.deleteId = parseInt(button.getAttribute("data-id"));
		$("#deletePostModal").modal();
	}
}

function onDeletePostSubmit(button) {
	$("#deletePostModal").modal("hide");
	var thread_id = parseInt(window.location.pathname.split("/")[2]);
	call("/api/posts/" + deleteId, function(data, error) {
		GoldenRoute.routeTo("/thread/" + thread_id);
	}, "DELETE");
}

function onDeleteThreadSubmit(button) {
	$("#deleteThreadModal").modal("hide");
	var thread_id = parseInt(window.location.pathname.split("/")[2]);
	call("/api/threads/" + thread_id, function(data, error) {
		GoldenRoute.routeTo("/");
	}, "DELETE");
}

function preservePage() {
	if(location.pathname != "/login") {
		page = location.pathname + location.search;
	}
	return true;
}

function onLogout() {
	session = null;
	sessionStorage.removeItem("auth");
	updateMenu(function() {});
	return false;
}

function onRegister(form) {
	var username = form.username.value;
	var password = form.password.value;
	var email = form.email.value;
	var description = form.description.value;
	var googleId = form.googleId ? form.googleId.value : null;
	call("/api/users/register", function(data, error) {
		if(!error) {
			getTemplate("register_confirm", function(template) {
				main.innerHTML = Mustache.render(template);
			});
		} else {
			getTemplate("register", function(template) {
				main.innerHTML = Mustache.render(template, { error: true });
			});
		}
	}, "POST", {
		username: username,
		email: email,
		password: password,
		description: description,
		googleId: googleId
	});
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

function onGoogleDisconnect() {
	userDescription = document.getElementById("descriptionInput").value;
	call("/api/users/self", function(data, error) {
		GoldenRoute.routeTo("/user/self");
	}, "PUT", {
		googleId: "r"
	});
	return false;
}

function onGoogleConnect(googleUser) {
	userDescription = document.getElementById("descriptionInput").value;
	var id_token = googleUser.getAuthResponse().id_token;
	gapi.auth2.getAuthInstance().signOut();
	call("/api/users/self", function(data, error) {
		GoldenRoute.routeTo("/user/self");
	}, "PUT", {
		googleId: id_token
	});
	return false;
}

function onGithubDisconnect() {
	userDescription = document.getElementById("descriptionInput").value;
	call("/api/users/self", function(data, error) {
		GoldenRoute.routeTo("/user/self");
	}, "PUT", {
		githubToken: "r"
	});
	return false;
}
