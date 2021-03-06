var eventClickData;


fetch('/api/games',{
    method:"GET",
    credentials: "include"
}).then(function(response) {
        return response.json();
    })
    .then(function(json) {
        var data = json;
        console.log(data);
        createList(data);
        createTableLeaderboard(data);
    })
    .catch(function(error) {
        console.log('Request failed', error)
    });

// function createList(data){
//     var ol = document.getElementById("games");
//     for(var i=0; i < data.games.length; i++){
//         var li = document.createElement("li");
//         ol.appendChild(li);
//         var a = document.createElement("a");
//         li.appendChild(a);
//         if(data.games[i].gamePlayer[1] != null){
//             for(var j =0; j < data.games[i].gamePlayer.length; j++){
//                 if((data.currentUser)&&(data.games[i].gamePlayer[j].Player.id == data.currentUser.id)){
//                     var idPlayer = data.currentUser.id;
//                     a.setAttribute("href", "game.html?gp=" + idPlayer)
//
//                 }
//             }
//             a.textContent = data.games[i].gamePlayer[0].Player.userName + " VS " + data.games[i].gamePlayer[1].Player.userName;
//         }else{
//             a.textContent = data.games[i].gamePlayer[0].Player.userName + " VS " + "There is no brave opponent";
//         }
//     }
//
// }

function  createList(data){
    var ol=document.getElementById("games");

    var player1;
    var player2;

    for(var i=0; i<data.games.length; i++){
        var li=document.createElement("li");

        player1 = data.games[i].gamePlayer[0].Player.userName;

        if(data.games[i].gamePlayer[1] != null){
            player2=data.games[i].gamePlayer[1].Player.userName;
            var id=data.games[i].gamePlayer[1].id;
            li.textContent=player1+" vs "+player2;

            if(data.currentUser){
                for(j=0;j<data.games[i].gamePlayer.length;j++){
                    console.log(data.currentUser.userName==data.games[i].gamePlayer[j].Player.userName)
                    if(data.currentUser.userName==data.games[i].gamePlayer[j].Player.userName){
                        console.log("hola")
                        var link=document.createElement("a");
                        link.setAttribute("href","game.html?gp="+ data.games[i].gamePlayer[j].id);
                        link.textContent=player1+" vs "+player2;
                        li.innerHTML = "";
                        li.appendChild(link);
                    }
                }
            }
        }

        else if(data.games[i].gamePlayer[1] == null){
            player2="Waiting for rival";
            if(data.currentUser){
                var link=document.createElement("a");
                link.setAttribute("href","game.html?gp="+id);
                link.textContent=player1+" vs "+player2;
                li.appendChild(link);
                var button=document.createElement("button");
                button.textContent="Join";
                button.setAttribute("data-game", data.games[i].id);
                // button.addEventListener("onclick", joinGame);
                button.onclick = function(e){
                    eventClickData = e.target.dataset.game;
                    joinGame(eventClickData);
                    console.log(e.target.dataset.game);
                };
                li.appendChild(button);
            }else{
                li.innerHTML = "";

                li.textContent=player1+" vs "+player2;
            }

        }

        ol.appendChild(li);
    }
}

    function createTableLeaderboard(data){
        var tbody = document.getElementById("tbody");
        for(var i=0; i < data.leaderboard.length; i++){
            if(data.leaderboard[i].total != null ){

                var tr = document.createElement("tr");
                tbody.appendChild(tr);

                var tdname = document.createElement("td");
                var tdwin = document.createElement("td");
                var tdlost = document.createElement("td");
                var tdtie = document.createElement("td");
                var tdtotal = document.createElement("td");

                tdname.textContent = data.leaderboard[i].name;

                tdwin.textContent = data.leaderboard[i].win;

                tdlost.textContent = data.leaderboard[i].lost;

                tdtie.textContent = data.leaderboard[i].tie;

                tdtotal.textContent = data.leaderboard[i].total;

                tr.appendChild(tdname);
                tr.appendChild(tdwin);
                tr.appendChild(tdlost);
                tr.appendChild(tdtie);
                tr.appendChild(tdtotal);

                }
        }
        }


function getLogin(){
    var username = document.getElementById("userName").value;
    var password = document.getElementById("pass").value;
    console.log(password);
    console.log(username);
    fetch("/api/login", {
        credentials: 'include',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: 'POST',
        body: 'username='+ username + '&password='+ password,
        })
        .then(function (data) {
            console.log('Request success: ', data);
            location.reload();
        })
        .catch(function (error) {
            console.log('Request failure: ', error);
        });
}

function getLogout(){
    fetch("/api/logout", {
        credentials: 'include',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: 'POST',
    })
        .then(function (data) {
            console.log('Request success: ', data);
        })
        .catch(function (error) {
            console.log('Request failure: ', error);
        });
}

function signUp(){
    var username = document.getElementById("userName").value;
    var password = document.getElementById("pass").value;
    fetch("/api/players", {
        credentials: 'include',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: 'POST',
        body: 'userName='+ username + '&password='+ password,
    })
        .then(function (data) {
            console.log('Request success: ', data);


        }).then(function () {
            getLogin();
    })
        .catch(function (error) {
            console.log('Request failure: ', error);
        });
}

function createGame(){
    fetch("/api/games", {
        credentials: 'include',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: 'POST',
    })
        .then(function (response) {
            return response.json();

    }).then(function (json) {
        console.log(json.gpid);
        var gpID=json.gpid;
        window.location='game.html?gp='+ gpID;
    })
        .catch(function (error) {
            console.log('Request failure: ', error);
            // var modalLogin = document.getElementById("myModal");
            // modalLogin.style.display= "block";
            window.alert("You must be logged!")
        });
}

function joinGame(dataGame){

    // var gameID = event.target.dataset.games;
    fetch("/api/game/"+ dataGame + "/players", {
        credentials: 'include',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: 'POST',
    })
        .then(function (response) {
            return response.json();

        }).then(function (json) {
        console.log(json);
        var gpID=json.gpid;
        window.location='game.html?gp='+ gpID;
    })
        .catch(function (error) {
            console.log('Request failure: ', error);
            window.alert("You must be logged!")
        });
}




