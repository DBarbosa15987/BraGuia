import React from "react";
import { fetchAppInfo, fetchUserInfo } from "@/api/api";
import { Redirect, router } from "expo-router";
import { useEffect, useState } from "react";
import { View } from "react-native";
import { ActivityIndicator } from "react-native-paper";
import { useDispatch, useSelector } from "react-redux";
import { COOKIE_KEY } from "@/api/api";
import { StyleSheet } from "react-native";
import * as SecureStore from "expo-secure-store";

export default function InitialPage() {
  const dispatch = useDispatch();
  const [cookie, setCookie] = useState<string | null>(null);
  const [loaded, setLoaded] = useState(false);
  useEffect(() => {
    const fetchCookiesAndAppInfo = async () => {
      await fetchAppInfo(dispatch);
      const cookies = await SecureStore.getItemAsync(COOKIE_KEY);
      setCookie(cookies);
      setLoaded(true);
    };

    if (loaded) {
      if (!cookie) {
        router.replace("/login");
      } else {
        router.replace("/home");
      }
    } else {
      fetchCookiesAndAppInfo();
    }
  }, [cookie, loaded]);
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
