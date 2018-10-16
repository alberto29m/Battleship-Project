
fetch('/api/Games')
    .then(function(response) {
        return response.json();
    })
    .then(function(json) {
        var data = json;

        createList(data);

    })
    .catch(function(error) {
        log('Request failed', error)
    });

function createList(data){
    var games = document.getElementById("games");
    for(var i=0; i < data.length; i++){
        var li = document.createElement("li");
        games.appendChild(li);
        if(data[i].gamePlayer[1] != null){
            li.textContent = data[i].gamePlayer[0].Player.userName + " VS " + data[i].gamePlayer[1].Player.userName;
        }else{
            li.textContent = data[i].gamePlayer[0].Player.userName + " VS " + "There is no brave opponent";
        }
    }

}