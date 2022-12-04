<template>
  <v-card>
    <v-card-title>
      Devices
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
      <v-btn v-if="isAdminUser()" @click="addDevice">Add Device</v-btn>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="items"
        :search="search"
    >
      <template v-slot:item="row">
        <tr v-if="checkCondition(row.item.userId)" v-on:click="editDevice(row.item)">
          <td>{{ row.item.title }}</td>
          <td>{{ row.item.description }}</td>
          <td>{{ row.item.max_consumption }}</td>
          <td>{{ row.item.location }}</td>
          <v-btn small color="green" v-on:click="deviceMeasurements(row.item)">See energy Consumption</v-btn>
        </tr>

      </template>
    </v-data-table>
    <DeviceDialog
        :opened="dialogVisible"
        :device="selectedDevice"
        :users="allUsers"
        @refresh="refreshList"
    ></DeviceDialog>
    <MeasurementDialog
        :opened="measurementDialogVisible"
        :deviceId="selectedDeviceId">
    </MeasurementDialog>
  </v-card>
</template>

<script>
import devices from "../api/services/devices";
import users from "../api/services/users";
import DeviceDialog from "@/components/DeviceDialog";
import MeasurementDialog from "@/components/MeasurementDialog";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

let user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "DeviceList",
  components: {MeasurementDialog, DeviceDialog},
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {text: "Title", align: "start", sortable: false, value: "title",},
        {text: "Description", value: "description"},
        {text: "Maximum consumption (W)", value: "max_consumption"},
        {text: "Location", value: "location"},
      ],
      dialogVisible: false,
      measurementDialogVisible: false,
      selectedDevice: {},
      selectedDeviceId: 1,
      allUsers: [],
      loggedUser: user,
      received_messages: [],
      connected: false
    };
  },
  methods: {
    editDevice(item) {
      if (this.isAdminUser()) {
        this.selectedDevice = item;
        this.dialogVisible = true;
      }
    },
    addDevice() {
      this.dialogVisible = true;
    },
    async refreshList() {
      user = JSON.parse(localStorage.getItem("user"));
      this.loggedUser = user;
      this.dialogVisible = false;
      this.selectedDevice = {};
      this.items = await devices.allDevices();
      console.log(this.items);
      let apiUsers = await users.allUsers();
      for (let i = 0; i < apiUsers.length; i++) {
        let interm = {
          text: apiUsers[i].name,
          value: apiUsers[i].id
        }
        this.allUsers.push(interm);
      }
    },
    isAdminUser() {
      return user.roles[0] === "ADMIN"
    },
    checkCondition(rowUserId) {
      console.log("user id", rowUserId, user.id);
      if (user.roles[0] === "ADMIN") {
        return true;
      } else {
        return rowUserId === user.id
      }
    },
    deviceMeasurements(device) {
      this.selectedDeviceId = device.id;
      this.measurementDialogVisible = true;
    },
    connect() {
      this.socket = new SockJS("http://localhost:8090/api/websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
          {},
          frame => {
            this.connected = true;
            this.stompClient.subscribe("/topic/greetings", tick => {
              console.log("tick, ", tick);
              this.received_messages.push(tick);
            });
          },
          error => {
            console.log("error " + error);
            this.connected = false;
          }
      )
    }
  },
  created() {
    this.refreshList();
    this.connect();
  },
};
</script>

<style scoped></style>
