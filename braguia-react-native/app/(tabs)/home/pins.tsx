import { router } from "expo-router";
import React from "react";
import { StyleSheet, FlatList, View } from "react-native";
import { Text, Card, Icon } from "react-native-paper";
import { useSelector } from "react-redux";

function PinCard({ pin }) {
  return (
    <Card
    elevation={3}
      style={styles.card}
      onPress={() => router.push("/home/pin/" + pin.id)}
    >
      <View style={styles.cardContent}>
        <Icon size={20} source="pin" />
        <Text>{pin.pin_name}</Text>
      </View>
    </Card>
  );
}

export default function PinsScreen() {
  const pins = useSelector((state) => state.appData.pins);

  if (!pins) {
    return alert("No pins found");
  }
  return (
    <View style={styles.container}>
      <FlatList
        data={pins}
        contentContainerStyle={{ gap: 5 }}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => <PinCard pin={item} />}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    padding: 5,
  },
  card: {
    height: 50,
  },
  cardContent: {
    paddingLeft: 10,
    height: "100%",
    flexDirection: "row",
    alignItems: "center",
  },
});
