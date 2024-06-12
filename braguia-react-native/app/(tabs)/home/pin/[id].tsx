import React from "react";
import { Stack, useLocalSearchParams } from "expo-router";
import { useSelector } from "react-redux";
import MapView, { Marker, PROVIDER_GOOGLE } from "react-native-maps";
import { ScrollView, StyleSheet, View } from "react-native";
import { Text} from "react-native-paper";


function MapWithSingleMarker({pin}) {
  return (
    <MapView
      style={styles.map}
      provider={PROVIDER_GOOGLE}
      initialRegion={{
        latitude: Number(pin.pin_lat),
        longitude: Number(pin.pin_lng),
        latitudeDelta: 0.0922,
        longitudeDelta: 0.0421,
      }}
    >
      <Marker
        key={pin.id}
        coordinate={{ latitude: Number(pin.pin_lat), longitude: Number(pin.pin_lng)}}
        title={pin.pin_name}
      />
    </MapView>
  );
}

function findPin(trails, id) {
  for (let i = 0; i < trails.length; i++) {
    const edges = trails[i].edges;
    // Iterate through each edge in the current trail
    for (let j = 0; j < edges.length; j++) {
      const edge = edges[j];
      // Check if edge_start has a pin with id equal to 1
      if (edge.edge_start.id == id) {
        return edge.edge_start;
      }
      // Check if edge_end has a pin with id equal to 1
      if (edge.edge_end.id == id) {
        return edge.edge_end;
      }
    }
  }
  return null;
}

export default function SinglePinScreen() {
  const { id } = useLocalSearchParams();
  const pin = useSelector((state) => findPin(state.appData.trails, id));
  if (!pin) {
    return alert(`Pin ${id} not found`);
  }
  return (
    <ScrollView>
      <Stack.Screen options={{ headerTitle: `${pin.pin_name}` }} />
      <Text>
        {pin.pin_name}
      </Text>
      <MapWithSingleMarker pin={pin}/>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  map: {
    width: "100%",
    height: 300,
  },
});
