import React from "react";
import { Stack, useLocalSearchParams } from "expo-router";
import { Button, List, Text } from "react-native-paper";
import { useSelector } from "react-redux";
import MapView, { Marker, PROVIDER_GOOGLE } from "react-native-maps";
import { Image, Linking, ScrollView, StyleSheet, View } from "react-native";
import ListAccordionGroup from "react-native-paper/lib/typescript/components/List/ListAccordionGroup";

function MapWithMultipleMarkers(route) {
  return (
    <MapView
      style={styles.map}
      provider={PROVIDER_GOOGLE}
      initialRegion={{
        latitude: Number(route[0].pin_lat),
        longitude: Number(route[0].pin_lng),
        latitudeDelta: 0.0922,
        longitudeDelta: 0.0421,
      }}
    >
      {route.map((pin) => (
        <Marker
          key={pin.id}
          coordinate={{ latitude: pin.pin_lat, longitude: pin.pin_lng }}
          title={pin.pin_name}
        />
      ))}
    </MapView>
  );
}

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
  for (let i = 0; i < route.length - 1; i++) {
    waypoints += route[i].pin_lat + "," + route[i].pin_lng;
    if (i < route.length - 2) {
      waypoints += "|";
    }
  }
  const mapsUrl = `https://www.google.com/maps/dir/?api=1&${waypoints}&${destination}`;
  console.log(mapsUrl);
  Linking.openURL(mapsUrl);
}

export default function SingleTrailScreen() {
  const { id } = useLocalSearchParams();
  const trail = useSelector((state) =>
    state.appData.trails.find((t) => t.id == id),
  );
  console.log(trail);
  if (!trail) {
    return alert(`Trail ${id} not found`);
  }
  const route = calculateRoute(trail.edges);
  return (
    <ScrollView>
      <Stack.Screen options={{ headerTitle: `${trail.trail_name}` }} />
      <Image source={{ uri: trail.trail_img }} />
      <Text> {trail.trial_desc}</Text>
      <Button onPress={() => startTrail(route)}>Start Trail</Button>
      <MapWithMultipleMarkers {...route} />
      <List.AccordionGroup>
        {trail.edges.map((edge) => (
          <List.Accordion
            key = {edge.id}
            title={edge.edge_start.pin_name + " -> " + edge.edge_end.pin_name}
            description={edge.edge_duration}
          >
            <List.Item title={edge.edge_start.pin_name} left={props => <List.Icon {...props} icon="marker"/>}onPress={() => route.push("/home/pin/" + edge.edge_start.id)} />
            <List.Item title={edge.edge_end.pin_name} onPress={() => route.push("/home/pin/" + edge.edge_end.id)} />
          </List.Accordion>
        ))}
      </List.AccordionGroup>
    </ScrollView>
  );
}
const styles = StyleSheet.create({
  map: {
    width: "100%",
    height: "100%",
  },
});
