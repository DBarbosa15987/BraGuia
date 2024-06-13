import React from "react";
import { Stack, router, useLocalSearchParams } from "expo-router";
import {
  Button,
  Divider,
  Icon,
  List,
  Modal,
  Portal,
  Text,
} from "react-native-paper";
import { useDispatch, useSelector } from "react-redux";
import MapView, { Marker, PROVIDER_GOOGLE } from "react-native-maps";
import {
  FlatList,
  Image,
  Linking,
  Pressable,
  ScrollView,
  StyleSheet,
  View,
} from "react-native";
import { ResizeMode, Video } from "expo-av";
import { addTrailToHistory } from "@/state/actions/user";

function MapWithMultipleMarkers({ route }) {
  route = route.reduce((acc, current) => {
    if (!acc.some((item) => item.id === current.id)) {
      acc.push(current);
    }
    return acc;
  }, []);
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
          coordinate={{
            latitude: Number(pin.pin_lat),
            longitude: Number(pin.pin_lng),
          }}
          title={pin.pin_name}
          description={pin.pin_desc}
        />
      ))}
    </MapView>
  );
}

function calculateRoute(edges) {
  const route = [];
  route.push(edges[0].edge_start);
  for (let i = 1; i < edges.length; i++) {
    route.push(edges[i].edge_start);
  }
  const lastEdge = edges[edges.length - 1].edge_end;
  route.push(lastEdge);
  return route;
}

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

function getMedia(route) {
  const media = [];
  for (let i = 0; i < route.length; i++) {
    if (route[i].media) {
      media.push(...route[i].media);
    }
  }
  return media;
}

function MediaItem({ item }) {
  const [visible, setVisible] = React.useState(false);
  const hideModal = () => setVisible(false);
  const showModal = () => setVisible(true);
  let itemElement;
  if (item.media_type === "I") {
    itemElement = (
      <Image style={styles.mediaItem} source={{ uri: item.media_file }} />
    );
  } else if (item.media_type === "V") {
    itemElement = <Icon size={100} source="file-video" />;
  } else {
    itemElement = <Icon source="file-music" size={100} />;
  }
  return (
    <View>
      <Pressable onPress={showModal}>{itemElement}</Pressable>
      <Portal>
        <Modal
          style={{ paddingHorizontal: 20, paddingVertical: 100 }}
          visible={visible}
          onDismiss={hideModal}
        >
          {item.media_type === "I" ? (
            <Image
              style={{ width: "100%", height: "100%", objectFit: "scale-down" }}
              source={{ uri: item.media_file }}
            />
          ) : (
            <Video
              style={{ width: "100%", height: "100%" }}
              source={{ uri: item.media_file }}
              useNativeControls={true}
              resizeMode={ResizeMode.CONTAIN}
              isLooping={false}
            />
          )}
        </Modal>
      </Portal>
    </View>
  );
}

function MediaSection({ media }) {
  // TODO: Add styling

  return (
    <View>
      <Text variant="headlineSmall">Media</Text>
      <Divider bold={true} />
      <FlatList
        style={{ paddingTop: 5 }}
        horizontal={true}
        data={media}
        keyExtractor={(item) => item.key}
        renderItem={({ item }) => <MediaItem item={item} />}
      />
    </View>
  );
}

export default function SingleTrailScreen() {
  const dispatch = useDispatch();
  const { id } = useLocalSearchParams();
  const trail = useSelector((state) =>
    state.appData.trails.find((t) => t.id == id),
  );
  const hist = useSelector((state) => state.user.history);
  if (!trail) {
    return alert(`Trail ${id} not found`);
  }
  console.log(hist);
  const route = calculateRoute(trail.edges);
  const media = getMedia(route);
  return (
    <ScrollView>
      <Stack.Screen options={{ headerTitle: `${trail.trail_name}` }} />
      <Image source={{ uri: trail.trail_img }} />
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
  mediaItem: {
    width: 100,
    height: 100,
  },
  mapContainer: {
    width: "100%",
    height: 350,
  },
  map: {
    width: "100%",
    height: "100%",
  },
});
