import * as TaskManager from "expo-task-manager";
import * as Location from "expo-location";

const GEOFENCING_TASK = "GEOFENCING_TASK";

export const requestLocationPermissions = async () => {
  const { status: foregroundStatus } =
    await Location.requestForegroundPermissionsAsync();
  if (foregroundStatus === "granted") {
    const { status: backgroundStatus } =
      await Location.requestBackgroundPermissionsAsync();
    if (backgroundStatus === "granted") {
      console.log("Location permissions granted");
    }
  }
};
export const defineGeofencingTask = TaskManager.defineTask(
  GEOFENCING_TASK,
  ({ data: { eventType, region }, error }) => {
    if (error) {
      console.error(error.message);
      return;
    }
    if (eventType === Location.GeofencingEventType.Enter) {
      Notification;
    }
  },
);

function extractGeofenceRegions(pins, radius): Location.LocationRegion[] {
  return pins.map((pin) => ({
    identifier: pin.id,
    latitude: pin.pin_lat,
    longitude: pin.pin_lng,
    radius: radius,
    notifyOnEnter: true,
    notifyOnExit: false,
  }));
}
export async function startGeofencingAsync(pins, radius = 150) {
  const regions = extractGeofenceRegions(pins, radius);

  await Location.startGeofencingAsync(GEOFENCING_TASK, regions);
}

// 3. (Optional) Unregister tasks by specifying the task name
// This will cancel any future background fetch calls that match the given name
// Note: This does NOT need to be in the global scope and CAN be used in your React components!
export async function stopGeofencingAsync() {
  await Location.stopGeofencingAsync(GEOFENCING_TASK);
}
