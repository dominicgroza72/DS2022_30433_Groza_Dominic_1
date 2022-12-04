<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
      <v-btn v-if="isAdminUser()" @click="addUser">Add User</v-btn>
    </v-card-title>
    <v-data-table v-if="isAdminUser()"
        :headers="headers"
        :items="users"
        :search="search"
        @click:row="editUser"
    ></v-data-table>
    <DeviceList v-if="isAdminUser()"></DeviceList>
    <UserDialog
        :opened="dialogVisible"
        :user="selectedUser"
        @refresh="refreshList"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "@/components/UserDialog";
import DeviceList from "@/views/DeviceList";

let user;

export default {
  name: "UserList",
  components: {DeviceList, UserDialog},
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "name",
        },
        {text: "Email", value: "email"},
        {text: "Roles", value: "roles"},
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    editUser(user) {

      this.selectedUser = user;
      this.dialogVisible = true;
    },
    addUser() {
      this.dialogVisible = true;
    },
    async refreshList() {
      user = JSON.parse(localStorage.getItem("user"));
      if (this.isAdminUser()) {
        this.dialogVisible = false;
        this.selectedUser = {};
        this.users = await api.users.allUsers();
      }
    },
    isAdminUser() {
      return user.roles[0] === "ADMIN"
    },
  },

  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
