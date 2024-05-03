var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}



function connect() {
    var socket = new SockJS('http://localhost:8080/init');
    stompClient = Stomp.over(socket);
	
    var thisheaders={
        'Authorization':'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0X3Rlc3QiLCJleHAiOjE3MTQ4MDMyNTd9.LPyKCg5ro-ZZHA8Wg0DQUuyJ4X5Hxe1NuU1Mdue6WnQ'
    };

    console.log('before');
    stompClient.connect(thisheaders, function (frame) {

        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/post', (message) => {
            console.log(message);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});
