import React, { useState, useEffect } from "react";
import { useFonts } from "expo-font";
import { Stack } from "expo-router";
import * as SplashScreen from "expo-splash-screen";
import "react-native-reanimated";
import { THEME } from "../constants/preferences"
import { store } from "../state/store";
import { getItem, setDefaults, setItem } from "../utils/asyncStorage";
import { useColorScheme } from "@/hooks/useColorScheme";
import { Provider, useSelector } from "react-redux";
import { DefaultTheme, ThemeProvider } from '@react-navigation/native';
import { PaperProvider, MD3LightTheme, MD3DarkTheme, adaptNavigationTheme } from "react-native-paper";

// Prevent the splash screen from auto-hiding before asset loading is complete.
SplashScreen.preventAutoHideAsync();
const { LightTheme,DarkTheme } = adaptNavigationTheme({
  reactNavigationLight: DefaultTheme,
  reactNavigationDark: DefaultTheme
})

export default function RootLayout() {
  const colorScheme = useColorScheme();
  
  const [loaded] = useFonts({
    SpaceMono: require("../assets/fonts/SpaceMono-Regular.ttf"),
  });

  // useEffect(() => {
  //   async function loadTheme() {
  //     await setDefaults()
  //     let storedTheme = await getItem(THEME);
  //     //setTheme(storedTheme)
  //   //   if (!storedTheme) {
  //   //     storedTheme = 'dark';
  //   //     await setItem('theme', storedTheme);
  //   //   }
  //   //   setTheme(storedTheme);
  //   }

  //   loadTheme();
  // }, []);

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
      <PaperProvider theme={MD3LightTheme}>
        <ThemeProvider value={LightTheme}>
          <Stack>
            <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
            <Stack.Screen name="+not-found" />
          </Stack>
        </ThemeProvider>
      </PaperProvider>
    </Provider>
  );
}
