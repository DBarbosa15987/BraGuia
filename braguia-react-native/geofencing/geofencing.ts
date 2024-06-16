import * as TaskManager from "expo-task-manager";
import * as Location from "expo-location";

export const GEOFENCING_TASK = "GEOFENCING_TASK";

export const requestLocationPermissions = async () => {
  const { status: foregroundStatus } =
    await Location.requestForegroundPermissionsAsync();
  if (foregroundStatus === "granted") {
    const { status: backgroundStatus } =
      await Location.requestBackgroundPermissionsAsync();
    if (backgroundStatus === "granted") {
      console.log("Location permissions granted");
      return true;
    }
  }
  return false;
};

export const defineGeofencingTask = TaskManager.defineTask(
  GEOFENCING_TASK,
  ({ data: { eventType, region }, error }) => {
    if (error) {
      console.error(error.message);
      return;
    }
    if (eventType === Location.GeofencingEventType.Enter) {

      console.log("You've entered region:", region.identifier);
      // sendNotification();
    }
  },
);

function extractGeofenceRegions(
  pins,
  radius: number,
): Location.LocationRegion[] {
  const regions = [];
  for (const pin of pins) {
    regions.push({
      identifier: GEOFENCING_TASK + "-" + pin.id,
      latitude: Number(pin.pin_lat),
      longitude: Number(pin.pin_lng),
      radius: radius,
      notifyOnEnter: true,
      notifyOnExit: false,
    });
  }
  return regions;
}

export async function startGeofencing(pins, radius = 150) {
  console.log("Starting geofencing");
  const permissions = await Location.getBackgroundPermissionsAsync();
  if (permissions.granted) {
    const regions = extractGeofenceRegions(pins, radius);
    await Location.startGeofencingAsync(GEOFENCING_TASK, regions);
  } else {
    console.log("Location permissions not granted");
  }
}

export async function stopGeofencing() {
  console.log("Stopping geofencing");
  await Location.stopGeofencingAsync(GEOFENCING_TASK);
}
