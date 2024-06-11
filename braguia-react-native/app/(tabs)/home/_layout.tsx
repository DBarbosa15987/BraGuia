import React from "react";
import { router, Stack } from "expo-router";
import { Icon, IconButton } from "react-native-paper";

export default function HomeStackLayout() {

  const about = () => {
    return (
      <IconButton icon="information-outline" size={30} onPress={()=>router.push("/home/about")}/>
    )
  }

  return (
    <Stack>
      <Stack.Screen name="index" options={{headerTitle: "Home",headerRight: about}}/>
    </Stack>
  );
}
