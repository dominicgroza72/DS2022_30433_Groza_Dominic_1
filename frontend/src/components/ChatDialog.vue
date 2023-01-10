<template>
  <div>

    <br/>
    <div>
      <button id="sendPrivateMessage" v-on:click="sendPrivateMessage();">Send Private</button>
      <input type="text" id="privateText" placeholder="Private Message"/>
      <input type="text" id="to" placeholder="To"/>
    </div>
    <br/>
    <br/>
    <br/>

    <div id="messages"></div>

  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "ChatDialog",
  data() {
    return {
      privateStompClient: null,
      socket: null
    }
  },
  methods: {
    initSocket() {
      this.socket = new SockJS('http://localhost:8090/app/websocket');
      this.privateStompClient = Stomp.over(this.socket);
      var stompClient = this.privateStompClient;
      this.privateStompClient.connect({}, function (frame) {
        console.log(frame);
        stompClient.subscribe('/user/specific', function (result) {
          console.log("O INTRAT CEVA", result.body)

          this.show(JSON.parse(result.body));
        });
      });
    },
    sendPrivateMessage() {
      const text = document.getElementById('privateText').value;
      const to = document.getElementById('to').value;
      const msg = {toUsername: "eu", message: "tu"};
      this.privateStompClient.send("/app/private", {}, JSON.stringify(msg)

      );
    },
    show(message) {
      const response = document.getElementById('messages');
      const p = document.createElement('p');
      p.innerHTML = "message: " + message.text;
      response.appendChild(p);
    }
  },
  mounted() {
    this.initSocket();
  }
}
</script>

<style scoped>

</style>