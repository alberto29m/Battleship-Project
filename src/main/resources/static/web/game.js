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
            var td = document.createElement("td");
            row.appendChild(td);
            if(( i > 0)&&(j == 0)){
                td.textContent = arrayLetters[i-1];
            }else if((i == 0)&&(j > 0)){
                td.textContent = j;
            }
            if(i > 0){
                id1 = arrayLetters[i-1];
                td.setAttribute("id", string + id1 + id2);
                td.setAttribute("ondrop", "drop(event)");
                td.setAttribute("ondragover", "allowDrop(event)");
            }
            if(j > 0){
                id2 =j;
                td.setAttribute("id", string + id1 + id2);
                td.setAttribute("ondrop", "drop(event)");
                td.setAttribute("ondragover", "allowDrop(event)");
            }
            if(i == 0){
                td.setAttribute("id", "none");
                td.removeAttribute("ondrop");
                td.removeAttribute("ondragover");
            }else if(j == 0){
                td.setAttribute("id", "none");
                td.removeAttribute("ondrop");
                td.removeAttribute("ondragover");
            }
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
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

// function drop(ev){
//     // console.log(ev.target);
//      if(ev.target.tagName == "TD") {
//          ev.preventDefault();
//          var data = ev.dataTransfer.getData("text");
//          // var shipInTd = ev.target.childNodes[0];
//          console.log(data);
//          if (data == "size5") {
//              if (ev.target.id.split('').length == 3) {
//                  var idShipSize = 11;
//              } else {
//                  var idShipSize = (parseInt(ev.target.id.split('')[1]) + 4);
//              }
//              var idChanged = ev.target.id.split('')[0] + idShipSize;
//              console.log(idShipSize);
//              console.log(ev.target.id.split('')[1]);
//              var ship5 = document.getElementById(data);
//
//              ev.target.appendChild(document.getElementById(data));
//              ship5.addEventListener("dblclick", function rotateShips(event) {
//                  console.log(idShipSize)
//                          if ((document.getElementById("size5").className != "rotate")&&(idShipSize <= 10)){
//                              console.log(event.target);
//                              ship5.setAttribute("class", "rotate");
//
//                          }else{
//                              console.log("hola");
//                              ship5.removeAttribute("class");
//                          }
//                          }
//                      );
//
//
//              // }
//          } else if (data == "size4") {
//              if (ev.target.id.split('').length == 3) {
//                  var idShipSize = 11;
//              } else {
//                  var idShipSize = (parseInt(ev.target.id.split('')[1]) + 3);
//              }
//              var idChanged = ev.target.id.split('')[0] + idShipSize;
//              if (idShipSize <= 10) {
//                  ev.target.appendChild(document.getElementById(data));
//                  var ship4 = document.getElementById(data);
//                  ship4.addEventListener("dblclick", function rotateShips(event) {
//                          console.log(event.target);
//                          event.target.style.transform = 'rotate(90deg)';
//                          event.target.style.marginLeft = "-25px";
//                      }
//                  );
//              }
//          } else if (data == "size3a") {
//              if (ev.target.id.split('').length == 3) {
//                  var idShipSize = 11;
//              } else {
//                  var idShipSize = (parseInt(ev.target.id.split('')[1]) + 2);
//              }
//              var idChanged = ev.target.id.split('')[0] + idShipSize;
//              if (idShipSize <= 10) {
//                  ev.target.appendChild(document.getElementById(data));
//                  var ship3a = document.getElementById(data);
//                  ship3a.addEventListener("dblclick", function rotateShips(event) {
//                          console.log(event.target);
//
//                          ev.target.replaceChild(event.target.style.transform = 'rotate(90deg)', ship3a);
//
//                      }
//                  );
//
//              }
//          } else if (data == "size3b") {
//              if (ev.target.id.split('').length == 3) {
//                  var idShipSize = 11;
//              } else {
//                  var idShipSize = (parseInt(ev.target.id.split('')[1]) + 2);
//              }
//              var idChanged = ev.target.id.split('')[0] + idShipSize;
//              if (idShipSize <= 10) {
//                  var ship3b = document.getElementById(data);
//                  ship3b.addEventListener("dblclick", function rotateShips(event) {
//                          console.log(event.target);
//                          event.target.style.transform = 'rotate(90deg)';
//                      }
//                  );
//                  ev.target.appendChild(document.getElementById(data));
//              }
//          } else if (data == "size2") {
//              if (ev.target.id.split('').length == 3) {
//                  var idShipSize = 11;
//              } else {
//                  var idShipSize = (parseInt(ev.target.id.split('')[1]) + 1);
//              }
//              var idChanged = ev.target.id.split('')[0] + idShipSize;
//              if (idShipSize <= 10) {
//                  ev.target.appendChild(document.getElementById(data));
//                  var ship2 = document.getElementById(data);
//                  ship2.addEventListener("dblclick", function rotateShips(event) {
//                          console.log(event.target);
//                          event.target.style.transform = 'rotate(90deg)';
//                          event.target.style.marginLeft = "-25px";
//                      }
//                  );
//              }
//          }
//      }
//
// }
function letterValue(str){
    var anum={
        A: 1, B: 2, C: 3, D: 4, E: 5, F: 6, G: 7, H: 8, I: 9, J: 10
    }
    if(str.length== 1) return anum[str] || ' ';
    return str.split('').map(letterValue);
}

function tdOccupied (ev, shipSize, numId, num){
    for(var i = 0; i< shipSize; i++){
        if(i == 0){num = num}else{num = ++num ;}
        var tdIdLetter = ev.target.id.split("")[0];
        var tdIdOccupied = tdIdLetter + num;
        var td = document.getElementById(tdIdOccupied);
        td.setAttribute("class", "occupied");
    }
}
function tdOccupiedLetters(ev, shipSize, tdLetterToNum){
    for(var i = 0; i< shipSize; i++){
        if(i == 0){tdLetterToNum = tdLetterToNum}else{tdLetterToNum = ++tdLetterToNum ;}

        var tdNumToLetter = String.fromCharCode(tdLetterToNum);
        console.log(tdNumToLetter);
        var tdIdNum = ev.target.id.split("")[1];
        var tdIdOccupied = tdNumToLetter + tdIdNum;
        var td = document.getElementById(tdIdOccupied);
        td.setAttribute("class", "occupied")
    }
}
function removeTdOccupiedLetters(ev, shipSize, tdLetterToNum) {
    for (var i = 0; i < shipSize; i++) {
        if (i == 0) {
            tdLetterToNum = tdLetterToNum
        } else {
            tdLetterToNum = ++tdLetterToNum;
        }

        var tdNumToLetter = String.fromCharCode(tdLetterToNum);
        console.log(tdNumToLetter);
        var tdIdNum = ev.target.id.split("")[1];
        var tdIdOccupied = tdNumToLetter + tdIdNum;
        var td = document.getElementById(tdIdOccupied);
        td.classList.remove("occupied")
    }
}
function removeTdOccupied(ev, shipSize, numId, num){
    for(var i = 0; i< shipSize; i++){
        if(i == 0){num = num}else{num = ++num ;}
        var tdIdLetter = ev.target.id.split("")[0];
        var tdIdOccupied = tdIdLetter + num;
        var td = document.getElementById(tdIdOccupied);
        td.classList.remove("occupied");
    }
}

function drop(ev){
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");

    var ship = document.getElementById(data);
    var regex = /\d+/g;
    var numSize = data.match(regex);
    var shipSize = parseInt(numSize[0]);

    var tdLetter = ev.target.id.split("")[0];
    var tdLetterToNum = tdLetter.charCodeAt(0);
    console.log(tdLetterToNum)
    var tdLetterSize = letterValue(tdLetter);
    console.log(tdLetterSize);
    var tdFinalLetterSize = tdLetterSize + shipSize;
    if (ev.target.id.split('').length == 3) {
        var tdShipSize = 15;
    } else {
        console.log(shipSize);
        var tdShipSize = (parseInt(ev.target.id.split('')[1]) + shipSize);
    }
    console.log(tdShipSize);
    console.log(tdFinalLetterSize);
    var numId = ev.target.id.match(regex);
    var num = parseInt(numId[0]);
    if((ship.className == "")&&(tdShipSize <=  11)){
        ship.setAttribute("class", "horizontal");
        drop(ev);
    }else if(ship.className == "horizontal"){
        tdOccupied(ev, shipSize, numId, num);
        switch (ship.id){
            case "size5":
                if(tdShipSize <=  11){
                    ev.target.appendChild(ship);
                    console.log("hola");
                    // ship.setAttribute("class", "horizontal");
                    ship.addEventListener("dblclick", function rotateShip(){
                        if(tdFinalLetterSize <= 11){
                            // ship.classList.remove("horizontal");
                            ship.setAttribute("class", "vertical");
                            removeTdOccupied(ev, shipSize, numId, num)
                            tdOccupiedLetters(ev, shipSize, tdLetterToNum);
                        }
                    });
                }
                break;
            case "size4":
                if(tdShipSize <=  11){
                    ev.target.appendChild(ship);
                    // ship.setAttribute("class", "horizontal");
                    ship.addEventListener("dblclick", function rotateShip(){
                        if(tdFinalLetterSize <= 11){
                            // ship.classList.remove("horizontal");
                            ship.setAttribute("class", "vertical");
                            removeTdOccupied(ev, shipSize, numId, num)
                            tdOccupiedLetters(ev, shipSize, tdLetterToNum);
                        }
                    });
                }
                break;
            case "size3a":
                if(tdShipSize <=  11){
                    ev.target.appendChild(ship);
                    console.log("hola");
                    // ship.setAttribute("class", "horizontal");
                    ship.addEventListener("dblclick", function rotateShip(){
                        if(tdFinalLetterSize <= 11){
                            // ship.classList.remove("horizontal");
                            ship.setAttribute("class", "vertical");
                            removeTdOccupied(ev, shipSize, numId, num)
                            tdOccupiedLetters(ev, shipSize, tdLetterToNum);
                        }
                    });
                }

                break;
            case "size3b":
                if(tdShipSize <=  11){
                    ev.target.appendChild(ship);
                    console.log("hola");
                    // ship.setAttribute("class", "horizontal");
                    ship.addEventListener("dblclick", function rotateShip(){
                        if(tdFinalLetterSize <= 11){
                            // ship.classList.remove("horizontal");
                            ship.setAttribute("class", "vertical");
                            removeTdOccupied(ev, shipSize, numId, num)
                            tdOccupiedLetters(ev, shipSize, tdLetterToNum);
                        }
                    });
                }

                break;
            case "size2":
                if(tdShipSize <=  11){
                    ev.target.appendChild(ship);
                    console.log("hola");
                    // ship.setAttribute("class", "horizontal");
                    ship.addEventListener("dblclick", function rotateShip(){
                        if(tdFinalLetterSize <= 11){
                            // ship.classList.remove("horizontal");
                            ship.setAttribute("class", "vertical");
                            removeTdOccupied(ev, shipSize, numId, num)
                            tdOccupiedLetters(ev, shipSize, tdLetterToNum);
                        }
                    });
                }

                break;
        }

    }else if(ship.className == "vertical"){
        console.log(letterValue(tdLetter))
        tdOccupiedLetters(ev, shipSize, tdLetterToNum);
        switch (ship.id){
            case "size5":
                if(tdFinalLetterSize <= 11){
                    ev.target.appendChild(ship);
                    console.log("hola");
                    // ship.setAttribute("class", "horizontal");
                    ship.addEventListener("dblclick", function rotateShip(){
                            // ship.classList.remove("horizontal");
                        console.log(tdShipSize)
                        if(tdShipSize <= 11) {
                            ship.setAttribute("class", "horizontal");
                            tdOccupied(ev, shipSize, numId, num);
                            removeTdOccupiedLetters(ev, shipSize, tdLetterToNum);
                        }
                    });
                }
                break;
            case "size4":
                if(tdFinalLetterSize <= 11){
                    ev.target.appendChild(ship);
                    console.log("hola");
                    // ship.setAttribute("class", "horizontal");
                    ship.addEventListener("dblclick", function rotateShip(){
                        // ship.classList.remove("horizontal");
                        console.log(tdShipSize)
                        if(tdShipSize <= 11) {
                            ship.setAttribute("class", "horizontal");
                        }
                    });
                }

                break;
            case "size3a":
                if(tdFinalLetterSize <= 11){
                    ev.target.appendChild(ship);
                    console.log("hola");
                    // ship.setAttribute("class", "horizontal");
                    ship.addEventListener("dblclick", function rotateShip(){
                        // ship.classList.remove("horizontal");
                        console.log(tdShipSize)
                        if(tdShipSize <= 11) {
                            ship.setAttribute("class", "horizontal");
                        }
                    });
                }

                break;
            case "size3b":
                if(tdFinalLetterSize <= 11){
                    ev.target.appendChild(ship);
                    console.log("hola");
                    // ship.setAttribute("class", "horizontal");
                    ship.addEventListener("dblclick", function rotateShip(){
                        // ship.classList.remove("horizontal");
                        console.log(tdShipSize)
                        if(tdShipSize <= 11) {
                            ship.setAttribute("class", "horizontal");
                        }
                    });
                }

                break;
            case "size2":
                if(tdFinalLetterSize <= 11){
                    ev.target.appendChild(ship);
                    console.log("hola");
                    // ship.setAttribute("class", "horizontal");
                    ship.addEventListener("dblclick", function rotateShip(){
                        // ship.classList.remove("horizontal");
                        console.log(tdShipSize)
                        if(tdShipSize <= 11) {
                            ship.setAttribute("class", "horizontal");
                        }
                    });
                }
                break;
        }
    }
 }
