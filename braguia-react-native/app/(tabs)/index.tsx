import { router } from "expo-router";
import React from "react";

import { Button, Text } from "react-native-paper";

export default function HomePage() {
  return (
    <Button onPress={() => router.push("trails")}>
      Navigate to TrailsScreen
    </Button>
  );
}
