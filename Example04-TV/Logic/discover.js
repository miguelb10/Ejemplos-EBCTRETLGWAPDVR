var listAvaliableServers = []



function avaliableServersToDiv() {
	console.log("[discoveryHelper] Avaliable: servers:\n");
	var addOptions = "";
	for (var it = 0; it < listAvaliableServers.length; it++) {
		addOptions += '<button type="button" id="' + (it + 2)
			+ '" class="buttonPr" onclick="entablishConnection(\''
			+ listAvaliableServers[it] + '\');" >'
			+ listAvaliableServers[it] + "</button>";
		console.log(listAvaliableServers[it] + "\n");
	}
	var div = document.getElementById("avaliableServers");
	div.innerHTML = addOptions;
}

function discoveryServers() {
	console.log("[discoveryHelper] executing discoveryServers()");
	// pMethods.clearListServer();
	// pMethods.resetIterator();
	var serverArrayTest = [];
	for (var i = 0; i < 255; i++) {
		var ws = new WebSocket("ws://192.168.1." + i + ":8080");
		serverArrayTest.push(ws);
		serverArrayTest[i].onopen = function () {
			attribs.listAvaliableServers.push(this.url);
			console.log('[discoveryHelper] Websocket found in :' + this.url);
			this.close();
			avaliableServersToDiv();
		};
	}
}

function getUserIP(onNewIP) {

	var myPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
	var pc = new myPeerConnection({
		iceServers: []
	}),
		noop = function () { },
		localIPs = {},
		ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g,
		key;

	function iterateIP(ip) {
		if (!localIPs[ip]) onNewIP(ip);
		localIPs[ip] = true;
	}
	pc.createDataChannel("");
	pc.createOffer(function (sdp) {
		sdp.sdp.split('\n').forEach(function (line) {
			if (line.indexOf('candidate') < 0) return;
			line.match(ipRegex).forEach(iterateIP);
		});

		pc.setLocalDescription(sdp, noop, noop);
	}, function (reason) {
	})

	pc.onicecandidate = function (ice) {
		if (!ice || !ice.candidate || !ice.candidate.candidate || !ice.candidate.candidate.match(ipRegex)) return;
		ice.candidate.candidate.match(ipRegex).forEach(iterateIP);
	};
}

function entablishConnection(url) {
	$(location).attr('href', "example.html?url=" + url);
}

// Usage
getUserIP(function (ip) {

	var toSplit = ip;
	var ipBase = toSplit.split(".", 3);
	var ipWS = -1;
	ipWS = ipBase[0] + "." + ipBase[1] + "." + ipBase[2];

	for (var i = 0; i < 255; i++) {
		var ws = new WebSocket("ws://" + ipWS + "." + i + ":8080");
		ws.onopen = function () {
			listAvaliableServers.push(this.url);
			console.log('[discoveryHelper] Websocket found in :' + this.url);
			avaliableServersToDiv();
		};
	};
});
