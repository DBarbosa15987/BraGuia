import { fetchAppInfo, fetchTrails } from "@/api/api";
import { router } from "expo-router";
import React, { useEffect } from "react";
import { View, StyleSheet, FlatList } from "react-native";
import { Button, Text, Avatar, Card, IconButton } from "react-native-paper";
import { useDispatch, useSelector } from "react-redux";

export default function HomePage() {
  const dispatch = useDispatch();
  useEffect(() => {
    fetchAppInfo(dispatch);
    fetchTrails(dispatch);
    }, []);
  const appInfo = useSelector((state) => state.appData.appinfo);

  const DATA = [
    {
      "title": 'Trails',
      "icon": 'routes',
      "onPress": () => router.push("/home/trails"),
    },
    {
      "title": 'Pins',
      "icon": 'routes',
      "onPress": () => { } /* router.push("/home/pins") */
    },
    {
      "title": 'History',
      "icon": 'routes',
      "onPress": () => { }
    },
    {
      "title": 'Bookmarks',
      "icon": 'routes',
      "onPress": () => { }
    }
  ]

  return (
    <View style={styles.container}>
      <View style={styles.textContainer}>
        <Text variant='displayLarge' style={styles.title}>{appInfo.app_name}</Text>
        <Text variant='bodyMedium' style={styles.desc}>{appInfo.app_landing_page_text}</Text>
      </View>
      <FlatList
        data={DATA}
        renderItem={({ item }) => <HomeCard title={item.title} icon={item.icon} onPress={item.onPress} />}
        keyExtractor={item => item.title}
        numColumns={2}
      />
      <Button onPress={() => router.replace("login")}>Navigate to Login</Button>
    </View>
  );
}

const HomeCard = ({ title, icon, onPress }) => {
  return (
    <Button
      icon={icon}
      onPress={onPress}
      mode="elevated"
      style={styles.card}
      contentStyle={styles.buttonContent}
      labelStyle={styles.buttonLabel}
    >
      <Text variant="titleMedium">{title}</Text>
    </Button>
  );
};

const styles = StyleSheet.create({

  icon: {
    margin: 10,
  },
  card: {
    margin: 6,
    padding: 6,
    width: 150,
    height: 100,
    justifyContent: 'center'
  },
  textContainer: {
    marginTop: 60,
    paddingBottom: 50,
    alignItems: 'center',
  },
  title: {
    fontWeight: "bold",
    fontFamily: "cursive",
    marginBottom: 20
  },
  desc: {
    textAlign: 'center',
  },
  container: {
    padding: 8,
    textAlign: 'center',
    alignItems: 'center',
  },
  buttonContent: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonLabel: {
    textAlign: 'center',
  }
});