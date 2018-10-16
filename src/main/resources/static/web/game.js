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
        makeTheTable();
        buildShips(ship);
        buildSalvoes(salvoes);

    })
    .catch(function(error) {
        console.log('Request failed', error);
    });

function makeTheTable(){
    var id1 = "";
    var id2;
    var arrayLetters = ["A","B","C","D","E","F","G","H","I","J"];
    var table = document.getElementById("board");
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
            column.setAttribute("id", id1 + id2);
        }
    }
}

function buildShips(ship){
    for(var i = 0; i< ship.length; i++){
        var locations = ship[i].location;
        for(var j = 0; j < locations.length; j++){
            var td = document.getElementById(locations[j]);
            td.setAttribute("class", "ships");
        }
    }
}

function buildSalvoes(salvoes){
    for(var i = 0; i< salvoes.length; i++){
        // if(salvoes[i].turn == 1){
            var locations = salvoes[i].location;
            for(var j = 0; j< locations.length; j++){
                var td = document.getElementById(locations[j]);
                td.textContent = salvoes[i].turn;
                var boolean = td.className == "ships"
                // var shipss = document.getElementsByClassName("ships")
                if(boolean){
                    td.setAttribute("class", "salvoShip");
                    console.log("hola");
                }else{
                    td.setAttribute("class", "salvoWater");
                }
            }
        // }
    }
}