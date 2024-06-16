import React, { useState, useEffect } from "react";
import { useFonts } from "expo-font";
import { Stack } from "expo-router";
import * as SplashScreen from "expo-splash-screen";
import "react-native-reanimated";
import { store, persistor } from "../state/store";
import { useColorScheme } from "@/hooks/useColorScheme";
import { Provider, useSelector } from "react-redux";
import { DefaultTheme, ThemeProvider } from '@react-navigation/native';
import { PaperProvider, MD3LightTheme, MD3DarkTheme, adaptNavigationTheme } from "react-native-paper";
import { PersistGate } from "redux-persist/integration/react";

// Prevent the splash screen from auto-hiding before asset loading is complete.
SplashScreen.preventAutoHideAsync();
const { LightTheme, DarkTheme } = adaptNavigationTheme({
  reactNavigationLight: DefaultTheme,
  reactNavigationDark: DefaultTheme
})

export default function RootLayout() {
  const colorScheme = useColorScheme();
  const [loaded] = useFonts({
    SpaceMono: require("../assets/fonts/SpaceMono-Regular.ttf"),
    Cursive: require("../assets/fonts/DancingScript-Bold.ttf"),
  });


  useEffect(() => {
    if (loaded) {
      SplashScreen.hideAsync();
    }
  }, [loaded]);

  if (!loaded) {
    return null;
  }
  return (
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <PaperProvider theme={colorScheme === "light" ? MD3LightTheme : MD3DarkTheme}>
          <ThemeProvider value={colorScheme === "light" ? LightTheme : DarkTheme}>
            <Stack>
              <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
              <Stack.Screen name="index" options={{ headerShown: false }} />
              <Stack.Screen name="login" options={{ headerTitle: "Login" }} />
            </Stack>
          </ThemeProvider>
        </PaperProvider>
      </PersistGate >
    </Provider>
  );
}
