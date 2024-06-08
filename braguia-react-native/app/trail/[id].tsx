import React from "react";
import { useLocalSearchParams } from "expo-router";
import { Text } from "react-native-paper";
import { useSelector } from "react-redux";

export default function SingleTrailScreen() {
  const { id } = useLocalSearchParams();
  const trail = useSelector((state) => state.trails.trails[id]);
  console.log(JSON.stringify(trail));
  return <Text> trail {id}</Text>;
}
