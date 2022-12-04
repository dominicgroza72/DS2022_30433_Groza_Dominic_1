import authHeader, {BASE_URL, HTTP} from "../http";

export default {

    allMeasurements() {
        return HTTP.get(BASE_URL + "/measurements", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    getMeasurementsForDevice(deviceId) {
        return HTTP.get(BASE_URL + "/measurements/" + deviceId, {headers: authHeader()}).then(
            (response) => {
                console.log("Response ", response.data);
                return response.data;
            }
        );
    }
}