import React from "react";
import { FlatList, ScrollView, StyleSheet, View } from "react-native";
import { Stack, useLocalSearchParams } from "expo-router";
import { useSelector } from "react-redux";
import { MapWithSingleMarker } from "@/components/Maps";
import { Chip, Surface, Text } from "react-native-paper";

function PinChip({ relPin }) {
  return (
    <Chip icon="information">
      {relPin.attrib} : {relPin.value}
    </Chip>
  );
}
function ChipList({ list }) {
  console.log(list);
  return (
    <View
      style={{
        flex: 1,
        alignItems: "center",
        alignSelf: "center",
        alignContent: "center",
        flexDirection: "row",
        flexWrap: "wrap-reverse",
        gap: 5,
      }}
    >
      {list.map((relPin) => (
        <PinChip relPin={relPin} key={relPin.id} />
      ))}
    </View>
  );
}

function Description({ description }) {
  return (
    <Surface style={styles.description} elevation={3}>
      <Text>{description}</Text>
    </Surface>
  );
}

export default function SinglePinScreen() {
  const { id } = useLocalSearchParams();
  const pin = useSelector((state) =>
    state.appData.pins.find((t) => t.id == id),
  );
  if (!pin) {
    return alert(`Pin ${id} not found`);
  }
  return (
    <ScrollView style={styles.container}>
      <Stack.Screen options={{ headerTitle: `${pin.pin_name}` }} />

      <View style={styles.map}>
        <MapWithSingleMarker pin={pin} />
      </View>
      <ChipList list={pin.rel_pin} />
      <Description description={pin.pin_desc} />
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    paddingHorizontal: 5,
  },
  description: {
    padding: 8,
    borderRadius: 15,
    alignItems: "center",
    justifyContent: "center",
  },
  map: {
    overflow: "hidden",
    borderRadius: 15,
    width: "100%",
    height: 250,
  },
});
