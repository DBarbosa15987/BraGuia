import React from "react";
import { useLocalSearchParams } from "expo-router";
import { Text } from "react-native-paper";
import { useSelector } from "react-redux";
import MapView, { PROVIDER_GOOGLE } from "react-native-maps";
import { StyleSheet, View } from "react-native";

// function MapWithMultipleMarkers() {
//   return <MapView provider={PROVIDER_GOOGLE}></MapView>;
// }

export default function SingleTrailScreen() {
  const { id } = useLocalSearchParams();
  const trail = useSelector((state) => state.trails.trails[id]);
  return (
    <>
      <MapView style={styles.map} provider={PROVIDER_GOOGLE} />
    </>
  );
}
const styles = StyleSheet.create({
  map: {
    width: "100%",
    height: "100%",
  },
});
