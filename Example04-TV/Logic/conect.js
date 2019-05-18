//#region Connect With Web Socket

var url;

function connect(url) {
    webSocket.ws = new WebSocket(url);

    webSocket.ws.onmessage = function (e) {
        obj = JSON.parse(e.data);
        if(obj.body.message == "UP"){
			moveup();
		} else if (obj.body.message == "DOWN"){
			movedown();
		} else if (obj.body.message == "RIGHT"){
			for(i=0; i<3;i++){
			moveright();
			}
		} else if (obj.body.message == "LEFT"){
			moveleft();
		}
    };

    webSocket.ws.onclose = function () {
    };

};

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
        .exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(
        /\+/g, " "));
}

$(document).ready(function () {
    url = getParameterByName('url');
    connect(url);
});
//#endregion
function entablishConnectionGame() {
	$(location).attr('href', "../Game/game.html?url=" + url);
}
