import React from "react";
import { StyleSheet } from "react-native";
import MapView, { Marker, PROVIDER_GOOGLE } from "react-native-maps";

export function MapWithMultipleMarkers({ route }) {
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

export function MapWithSingleMarker({ pin }) {
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
        coordinate={{
          latitude: Number(pin.pin_lat),
          longitude: Number(pin.pin_lng),
        }}
        title={pin.pin_name}
      />
    </MapView>
  );
}

const styles = StyleSheet.create({
  map: {
    height: "100%",
    width: "100%",
  },
});
