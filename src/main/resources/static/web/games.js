
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

function createList(data){
    var ol = document.getElementById("games");
    for(var i=0; i < data.games.length; i++){
        var li = document.createElement("li");
        ol.appendChild(li);
        if(data.games[i].gamePlayer[1] != null){
            li.textContent = data.games[i].gamePlayer[0].Player.userName + " VS " + data.games[i].gamePlayer[1].Player.userName;
        }else{
            li.textContent = data.games[i].gamePlayer[0].Player.userName + " VS " + "There is no brave opponent";
        }
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
