var gamePlayerID;
function getParameterByName(name) {
    var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
    return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
}

function makeUrl(){
    gamePlayerID = getParameterByName("gp");
    return '/api/game_view/' + gamePlayerID;
}

fetch(makeUrl())
    .then(function(response) {
        return response.json();
    })
    .then(function(json) {
        var data = json;
        console.log(data);
        var ship = data.ships;
        var salvoes = data.salvoes;
        makeTheTable("board", "");
        makeTheTable("board2", "O");
        buildShips(ship);
        buildSalvoes(salvoes);

    })
    .catch(function(error) {
        console.log('Request failed', error);
    });


function makeTheTable(id, string){
    var id1 = "";
    var id2;
    var arrayLetters = ["A","B","C","D","E","F","G","H","I","J"];
    var table = document.getElementById(id);
    for(var i = 0; i< 11; i++){
        var row = document.createElement("tr");
        table.appendChild(row);
        for(var j = 0; j < 11; j++){
            var column = document.createElement("td");
            row.appendChild(column);
            if(( i > 0)&&(j == 0)){
                column.textContent = arrayLetters[i-1];
            }else if((i == 0)&&(j > 0)){
                column.textContent = j;
            }
            if(i > 0){
                id1 = arrayLetters[i-1];
            }
            if(j > 0){
                id2 =j;
            }
            column.setAttribute("id", string + id1 + id2);
        }
    }
}

function buildShips(ship){
    if(ship != null) {
        for (var i = 0; i < ship.length; i++) {
            var locations = ship[i].location;

            for (var j = 0; j < locations.length; j++) {
                var td = document.getElementById(locations[j]);
                td.setAttribute("class", "ships");
            }
        }
    }
}

    function buildSalvoes(salvoes){
        if(salvoes != null){
            for(var i = 0; i< salvoes.length; i++) {
                if (gamePlayerID == salvoes[i].gameplayerID) {
                    for (var j = 0; j < salvoes[i].salvoes.length; j++) {
                        var locations = salvoes[i].salvoes[j].location;
                        for (var l = 0; l < locations.length; l++) {
                            var td = document.getElementById("O" + locations[l]);
                            td.textContent = salvoes[i].salvoes[j].turn;
                            var boolean = td.className == "ships";
                            if (boolean) {
                                td.setAttribute("class", "salvoShip");
                                console.log("hola");
                            } else {
                                td.setAttribute("class", "salvoWater");
                            }
                        }
                    }
                } else {
                    for (var n = 0; n < salvoes[i].salvoes.length; n++) {
                        locations2 = salvoes[i].salvoes[n].location;
                        for (var m = 0; m < locations2.length; m++) {
                            var td2 = document.getElementById(locations2[m]);
                            td2.textContent = salvoes[i].salvoes[n].turn;
                            // var boolean2 = td2.className == "ships";
                            if (td2.classList.contains("ships")) {
                                td2.className += " salvoShip";
                                console.log("hola");
                            } else {
                                td2.className += " salvoWater";
                            }
                        }
                    }
                }
            }
            }
    }

    function createShips(){
        fetch("/api/games/players/" + gpID +"/ships", {
            credentials: 'include',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: 'POST',
            body: JSON.stringify([{ "shipType": "destroyer", "locations": ["A1", "B1", "C1"] },
                { "shipType": "patrol boat", "locations": ["H5", "H6"] }
            ])
        })
            .then(function (response) {
                return response.json();

            }).then(function (json) {
            console.log(json);
            var ship = json.ships;
            location.reload();
        })
            .catch(function (error) {
                console.log('Request failure: ', error);

            });
    }