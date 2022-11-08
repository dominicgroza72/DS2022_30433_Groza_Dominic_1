<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Save new device" : "Edit device" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="device.title" label="Title"/>
          <v-text-field v-model="device.description" label="Description"/>
          <v-text-field v-model="device.max_consumption" label="Max Consumption"/>
          <v-text-field v-model="device.location" label="Location"/>
          <v-select :items="users"  label="User" v-model="device.userId" @change="selectedUserEvent($event)"></v-select>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteDevice">
            Delete Device
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

const user = JSON.parse(localStorage.getItem("user"));



export default {
  name: "DeviceDialog",
  data() {
    return {
      selectedUser:null,
    }
  },
  props: {
    device: Object,
    opened: Boolean,
    users: Array,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.devices
            .create({
              title: this.device.title,
              description: this.device.description,
              max_consumption: this.device.max_consumption,
              location: this.device.location,
              user_id: this.selectedUser,
            })
            .then(() => this.$emit("refresh"));
      } else {
        if (user.roles[0] === "ADMIN") {
          api.devices
              .edit({
                id:this.device.id,
                title: this.device.title,
                description: this.device.description,
                max_consumption: this.device.max_consumption,
                location: this.device.location,
                userId: this.selectedUser,
              })
              .then(() => this.$emit("refresh"));
        } else {
          alert("You are not authorized to update");
          location.reload();
        }
      }
    },
    selectedUserEvent(event){
      this.selectedUser=event
    },
    deleteDevice() {
      if (user.roles[0] === "ADMIN") {
        api.devices
            .delete(this.device)
            .then(() => this.$emit("refresh"));
      } else {
        alert("You are not authorized to delete a device");
        location.reload();
      }

    },
  },
  computed: {
    isNew: function () {
      return !this.device.id;
    },
  },
};
</script>

<style scoped></style>
