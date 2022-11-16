import authHeader, { BASE_URL, HTTP } from "../http";

export default {

  allDevices() {
    return HTTP.get(BASE_URL + "/devices", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(device) {
    return HTTP.post(BASE_URL + "/devices/"+device.user_id, device, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(device) {
    return HTTP.put(BASE_URL + "/devices/"+ device.id,device, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(device) {
    return HTTP.delete(BASE_URL + "/devices/"+ device.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
