import React from "react";
import { router, Stack } from "expo-router";
import { Icon, IconButton } from "react-native-paper";

export default function HomeStackLayout() {
  const about = () => {
    return (
      <IconButton
        icon="information-outline"
        size={30}
        onPress={() => router.push("/home/about")}
      />
    );
  };

  return (
    <Stack>
      <Stack.Screen
        name="index"
        options={{ headerTitle: "Home", headerRight: about }}
      />
      <Stack.Screen name="trails" options={{ headerTitle: "Trails" }} />
      <Stack.Screen
        name="trail/[id]"
        options={{ headerTitle: "Single Trail" }}
      />
      <Stack.Screen name="about" options={{ headerTitle: "About" }} />
      <Stack.Screen name="pins" options={{ headerTitle: "Pins" }} />
      <Stack.Screen name="history" options={{ headerTitle: "History" }} />
      <Stack.Screen name="bookmarks" options={{ headerTitle: "Bookmarks" }} />
    </Stack>
  );
}
