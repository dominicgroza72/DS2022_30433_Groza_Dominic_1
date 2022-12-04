<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="green" dark>Energy consumption</v-toolbar>
        <v-data-table
            :headers="headers"
            :items="measurements"
            :search="search"
        >
          <template v-slot:item="row">
            <tr>
              <td>{{ row.item.date }}</td>
              <td>{{ row.item.reading_value }}</td>
            </tr>
          </template>
        </v-data-table>

      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import measurements from "../api/services/measurements";


export default {
  name: "MeasurementDialog",
  data() {
    return {
      measurements: [],
      search: "",
      headers: [
        {text: "Date", align: "start", value: "date",},
        {text: "Consumption", value: "reading_value"}
      ],
    };
  },
  watch: {
    async opened(newVal) {
      if (newVal) {
        console.log("new val",newVal,this.deviceId);
        this.measurements = await measurements.getMeasurementsForDevice(this.deviceId);
        console.log(this.measurements);
      }
    }
  },
  props: {
    deviceId: Number,
    opened: Boolean
  },
}
</script>

<style scoped>

</style>