import React from "react";
import { Stack, router, useLocalSearchParams } from "expo-router";
import { Button, List, Text } from "react-native-paper";
import { useDispatch, useSelector } from "react-redux";
import { Image, Linking, ScrollView, StyleSheet, View } from "react-native";
import { addTrailToHistory } from "@/state/actions/user";
import { MapWithMultipleMarkers } from "@/components/Maps";
import { getMedia, calculateRoute } from "@/utils/trails";
import { MediaSection } from "@/components/Media";

function startTrail(route, trailId, dispatch) {
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
  const historyEntry = { trail: trailId, timestamp: Date.now() };
  dispatch(addTrailToHistory(historyEntry));
  console.log(historyEntry);
  Linking.openURL(mapsUrl);
}

export default function SingleTrailScreen() {
  const dispatch = useDispatch();
  const { id } = useLocalSearchParams();
  const trail = useSelector((state) =>
    state.appData.trails.find((t) => t.id == id),
  );
  if (!trail) {
    return alert(`Trail ${id} not found`);
  }
  const route = calculateRoute(trail.edges);
  const media = getMedia(route);
  return (
    <ScrollView style={styles.container}>
      <Stack.Screen options={{ headerTitle: `${trail.trail_name}` }} />
      <Image style={styles.trailImg} source={{ uri: trail.trail_img }} />
      {/* TODO: Add reltrail */}
      <Text> Difficulty: {trail.trail_difficulty}</Text>
      <Text> Duration: {trail.trail_duration} minutes</Text>
      <Text> {trail.trail_desc}</Text>
      <MediaSection media={media} />
      <View style={styles.mapContainer}>
        <MapWithMultipleMarkers route={route} />
      </View>

      <Button
        mode="contained"
        onPress={() => startTrail(route, trail.id, dispatch)}
      >
        Start Trail
      </Button>

      <List.AccordionGroup>
        {trail.edges.map((edge) => (
          <List.Accordion
            id={edge.id}
            key={edge.id}
            title={edge.edge_start.pin_name + " -> " + edge.edge_end.pin_name}
            // description={"Duration: " + edge.edge_duration}
          >
            <List.Item
              title={edge.edge_start.pin_name}
              left={(props) => <List.Icon {...props} icon="pin" />}
              onPress={() => router.push("/home/pin/" + edge.edge_start.id)}
            />
            <List.Item
              title={edge.edge_end.pin_name}
              left={(props) => <List.Icon {...props} icon="pin" />}
              onPress={() => router.push("/home/pin/" + edge.edge_end.id)}
            />
          </List.Accordion>
        ))}
      </List.AccordionGroup>
    </ScrollView>
  );
}
const styles = StyleSheet.create({
  container: {
    paddingHorizontal: 5,
  },
  trailImg: {
    width: "100%",
    height: 200,
  },
  mapContainer: {
    width: "100%",
    height: 350,
  },
});
