import React from "react";
import { StyleSheet } from "react-native";
import { Surface, Text } from "react-native-paper";

export function Description({ description }) {
  return (
    <Surface style={styles.description} elevation={3}>
      <Text>{description}</Text>
    </Surface>
  );
}

const styles = StyleSheet.create({
  description: {
    padding: 8,
    borderRadius: 15,
    alignItems: "center",
    justifyContent: "center",
  },
});
