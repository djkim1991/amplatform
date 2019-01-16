
var sock = new SockJS('http://localhost:8080/websock');
var client = Stomp.over(sock);

client.connect({}, function(frame) {

    console.log('connected stomp over sockjs');

    client.subscribe('/topic/exchange', function(messagePacket) {

        var payload = JSON.parse(messagePacket.body);
        console.log("================== " + payload);

        var userId = payload.userId;
        var data = payload.data;
        console.log("cmd ================== " + userId);
        console.log("cmd ================== " + data);
        alert(userId);
        alert(data);
    });
});