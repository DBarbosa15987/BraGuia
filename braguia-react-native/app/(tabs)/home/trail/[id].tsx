import React from "react";
import { useLocalSearchParams } from "expo-router";
import { Button, Text } from "react-native-paper";
import { useSelector } from "react-redux";
import MapView, { PROVIDER_GOOGLE } from "react-native-maps";
import { Linking, StyleSheet, View } from "react-native";

// function MapWithMultipleMarkers() {
//   return <MapView provider={PROVIDER_GOOGLE}></MapView>;
// }

function calculateRoute(edges) {
  const route = [edges[0].edge_start];
  for (let i = 1; i < edges.length; i++) {
    route.push(edges[i].edge_start);
  }
  const lastEdge = edges[edges.length - 1].edge_end;
  route.push(lastEdge);
  return route;
}

function startTrail(route) {
  const lastPin = route[route.length - 1];
  const destination = "destination=" + lastPin.pin_lat + "," + lastPin.pin_lng;
  let waypoints = "waypoints=";
  for (let i = 0; i < route.length; i++) {
    waypoints += route[i].pin_lat + "," + route[i].pin_lng;
    if (i < route.length - 2) {
      waypoints += "|";
    }
  }
  const mapsUrl = `https://www.google.com/maps/dir/?api=1&${waypoints}&${destination}`;

  Linking.openURL(mapsUrl);
}

export default function SingleTrailScreen() {
  const { id } = useLocalSearchParams();
  const trail = useSelector((state) => state.trails.trails[id]);
  const route = calculateRoute(trail.edges);
  return (
    <View>
      <Button onPress={() => startTrail(route)}>Start Trail</Button>

      <MapView style={styles.map} provider={PROVIDER_GOOGLE} />
    </View>
  );
}
const styles = StyleSheet.create({
  map: {
    width: "100%",
    height: "100%",
  },
});
