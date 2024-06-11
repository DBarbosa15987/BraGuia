import { fetchAppInfo, fetchTrails } from "@/api/api";
import { router } from "expo-router";
import React, { useEffect } from "react";
import { View } from "react-native";
import MapView, { PROVIDER_GOOGLE } from "react-native-maps";

import { Button, Text } from "react-native-paper";
import { useDispatch } from "react-redux";

export default function HomePage() {
  const dispatch = useDispatch();

  useEffect(() => {
    fetchAppInfo(dispatch);
    fetchTrails(dispatch);
  }, []);
  return (
    <View>
      <Button onPress={() => router.push("/home/trails")}>
        Navigate to TrailsScreen
      </Button>
      <Button onPress={() => router.push("login")}>Navigate to Login</Button>
    </View>
  );
}
