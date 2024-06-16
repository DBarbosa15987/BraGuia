import React from "react";
import { fetchAppInfo } from "@/api/api";
import { router } from "expo-router";
import { useEffect, useState } from "react";
import { View } from "react-native";
import { ActivityIndicator } from "react-native-paper";
import { useDispatch, useSelector } from "react-redux";
import { COOKIE_KEY } from "@/api/api";
import { StyleSheet } from "react-native";
import * as SecureStore from "expo-secure-store";
import { defineGeofencingTask } from "@/location/geofencing";
import { Appearance } from "react-native";
import { getItem, setItem } from "../utils/asyncStorage";
import { THEME } from "../constants/preferences"

// NOTE: define the geofencing task in global scope
defineGeofencingTask

export default function InitialPage() {
  const dispatch = useDispatch();
  const [cookie, setCookie] = useState<string | null>(null);
  const [loaded, setLoaded] = useState(false);
  const appInfo = useSelector((state) => state.appData.appinfo);

  const fetchCookies = async () => {
    const cookies = await SecureStore.getItemAsync(COOKIE_KEY);
    setCookie(cookies);
    setLoaded(true);
  };
  useEffect(() => {
    async function loadTheme() {
      let storedTheme = await getItem(THEME);
      if (!storedTheme) {
        storedTheme = 'light';
        await setItem(THEME, storedTheme);
      }
      return storedTheme;
    }
    loadTheme().then(storedTheme => {
      Appearance.setColorScheme(storedTheme);
    });
  }, []);
  useEffect(() => {
    if (!appInfo) {
      fetchAppInfo(dispatch);
    } else {
      if (loaded) {
        if (!cookie) {
          router.replace("/login");
        } else {
          router.replace("/home");
        }
      } else {
        fetchCookies();
      }
    }
  }, [appInfo,loaded]);
  return (
    <View style={styles.container}>
      <ActivityIndicator size="large" animating={true} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
});
