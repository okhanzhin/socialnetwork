let stompClient;

$(function () {
    let chatRoomId = $('#chatRoomId').val();
    let sourceId = $('#homeAccountId').val();
    let targetId = $('#interlocutorId').val();
    let chatWindow = $('.chat-window');
    let input = $('#message-input');

    $('#button-send').on('click', function () {
        sendMessage();
    });

    input.on('keypress', function (e) {
        console.log(e);
        if (e.shiftKey && e.key === 'Enter') {
            sendMessage();
            e.preventDefault();
        }
    })

    $(window).on('unload', function () {
        console.log("Unsubscribe");
        if (stompClient !== null) {
            stompClient.unsubscribe();
            stompClient.disconnect();
        }
    });

    function connect() {
        let socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnect, onError)
    }

    const onConnect = () => {
        console.log('/user/' + chatRoomId + '/queue/' + sourceId);
        stompClient.subscribe('/user/' + chatRoomId + '/interlocutor/' + sourceId, onMessageReceived);
    };

    const onError = (err) => {
        console.log(err)
    };

    const onMessageReceived = (msg) => {
        console.log("Receiving");
        let receivedMessage = JSON.parse(msg.body);

        messageOutput(receivedMessage.content, 'reply-template')
    };

    connect();

    const sendMessage = () => {
        console.log("Sending");
        let inputText = input.val().trim();

        if (inputText !== '') {
            const message = {
                sourceID: sourceId,
                targetID: targetId,
                content: inputText
            };

            input.val('');
            stompClient.send("/app/chat", {}, JSON.stringify(message));
            messageOutput(message.content, 'sent-template');
        }
    }

    function messageOutput(msg, template) {
        let templatePointer = '#' + template
        let messageTemplate = $(templatePointer).html();
        let msgEntry = Mustache.render(messageTemplate, {messageContent: msg});

        $('.chat-window .chat-message').last().after(msgEntry);
        chatWindow.animate({scrollTop: chatWindow[0].scrollHeight}, 0);
    }
});