<template>
  <v-card class="d-flex flex-column">
    <v-card-title>
      Chat
    </v-card-title>
    <v-text-field v-model="messageText" label="Message"/>
    <v-text-field v-model="messageTo" label="To"/>
    <v-spacer></v-spacer>
    <v-card-actions>
      <div style="width: 75%!important;"></div>
      <v-btn @click="sendPrivateMessage" color="blue">Send Message</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import users from "../api/services/users";

let user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "ChatDialog",
  data() {
    return {
      messageText: '',
      messageTo: '',
      loggedUser: null,
    }
  },
  methods: {
    sendPrivateMessage() {
      const msg = {message: this.messageText, fromUsername: user.username};
      users.sendMessage(this.messageTo, msg);
      this.$emit('sentMessage',{message:this.messageText,fromUsername: 'YOU'})
    },
  },
  created() {
    user = JSON.parse(localStorage.getItem("user"));
    this.loggedUser = user;
  }
}
</script>

<style scoped>

</style>