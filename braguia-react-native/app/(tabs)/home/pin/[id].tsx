import React from "react";
import { ScrollView, StyleSheet, View } from "react-native";
import { Stack, useLocalSearchParams } from "expo-router";
import { useSelector } from "react-redux";
import { MapWithSingleMarker } from "@/components/Maps";
import { MediaSection } from "@/components/Media";
import { Description } from "@/components/Description";
import { ChipList } from "@/components/ChipList";

export default function SinglePinScreen() {
  const { id } = useLocalSearchParams();
  const pin = useSelector((state) =>
    state.appData.pins.find((t) => t.id == id),
  );
  const isPremium =
    useSelector((state) => state.user.info.user_type) === "Premium";
  if (!pin) {
    return alert(`Pin ${id} not found`);
  }
  return (
    <ScrollView style={styles.container}>
      <Stack.Screen options={{ headerTitle: `${pin.pin_name}` }} />
      <View style={styles.mapContainer}>
        <MapWithSingleMarker pin={pin} />
      </View>
      {pin.rel_pin.length > 0 ? <ChipList list={pin.rel_pin} /> : null}

      {isPremium && pin.media.length > 0 ? (
        <MediaSection media={pin.media} />
      ) : null}
      <Description description={pin.pin_desc} />
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    paddingHorizontal: 5,
    paddingTop: 5,
  },
  mapContainer: {
    overflow: "hidden",
    borderRadius: 15,
    width: "100%",
    height: 250,
  },
});
