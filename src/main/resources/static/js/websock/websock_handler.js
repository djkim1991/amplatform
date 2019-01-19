
var sock = new SockJS('http://localhost:9090/websock');
var client = Stomp.over(sock);

client.connect({}, function(frame) {

    console.log('connected stomp over sockjs');

    client.subscribe('/topic/exchange', function(messagePacket) {

        var payload = JSON.parse(messagePacket.body);
        console.log("================== " + payload);

        var userId = payload.userId;
        var data = payload.data;
        var localDateTime = payload.localDateTime;
        console.log("cmd ================== " + userId + " " + data + " " + localDateTime);
        alert(userId + ' ' + data + localDateTime);
    });
});