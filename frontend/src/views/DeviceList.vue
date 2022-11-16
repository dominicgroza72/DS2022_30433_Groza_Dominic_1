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
        <tr v-if="checkCondition()" v-on:click="editDevice(row.item)">
          <td>{{ row.item.title }}</td>
          <td>{{ row.item.description }}</td>
          <td>{{ row.item.max_consumption }}</td>
          <td>{{ row.item.location }}</td>
        </tr>

      </template>
    </v-data-table>
    <DeviceDialog
        :opened="dialogVisible"
        :device="selectedDevice"
        :users="allUsers"
        @refresh="refreshList"
    ></DeviceDialog>
  </v-card>
</template>

<script>
import api from "../api";
import DeviceDialog from "@/components/DeviceDialog";

const user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "DeviceList",
  components: {DeviceDialog},
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
      selectedDevice: {},
      allUsers: [],
      loggedUser: user,
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
      this.dialogVisible = false;
      this.selectedDevice = {};
      this.items = await api.devices.allDevices();
console.log("items" ,this.items);
      let apiUsers = await api.users.allUsers();
      for(let i=0;i<apiUsers.length;i++){
        let interm={
          text:apiUsers[i].name,
          value:apiUsers[i].id
        }
        this.allUsers.push(interm);
      }
    },
    isAdminUser() {
      return user.roles[0] === "ADMIN"
    },
    checkCondition(rowUserId) {
      if (user.roles[0] === "ADMIN") {
        return true;
      } else {
        return rowUserId === user.id
      }
    }
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
