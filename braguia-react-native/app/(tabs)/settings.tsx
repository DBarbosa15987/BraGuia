import React, { useEffect } from "react";
import {
  StyleSheet,
  TouchableOpacity,
  ScrollView,
  View,
  Appearance,
} from "react-native";
import {
  Button,
  Card,
  Text,
  Switch,
  Dialog,
  Portal,
  Paragraph,
} from "react-native-paper";
import { router } from "expo-router";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { clearUserData } from "@/state/actions/user";
import { getItem, removeItem, setItem } from "@/utils/asyncStorage";
import { DO_NOT_ASKAGAIN, GEOFENCING, THEME } from "@/constants/preferences";
import { startGeofencing, stopGeofencing } from "@/geofencing/geofencing";

export default function SettingsPage() {
  const [darkMode, setDarkMode] = useState(
    Appearance.getColorScheme() === "dark",
  );
  const [delData, setDelData] = useState(false);
  const [geofencingSwitch, setGeofencingSwitch] = useState(true);
  const [delPrefs, setDelPrefs] = useState(false);
  const dispatch = useDispatch();

  const pins = useSelector((state) => state.appData.pins);

  useEffect(() => {
    const getPrefs = async () => {
      const geofencing = await getItem(GEOFENCING);
      setGeofencingSwitch(!geofencing || geofencing === "true");
    };
    getPrefs();
  }, []);

  const showDelData = () => setDelData(true);
  const hideDelData = () => setDelData(false);
  const showDelPrefs = () => setDelPrefs(true);
  const hideDelPrefs = () => setDelPrefs(false);

  const handleDelData = () => {
    // Perform logout logic here
    console.log("User Data Deleted");
    dispatch(clearUserData());
    setDelData(false);
  };

  const handleResetPrefs = async () => {
    // Perform logout logic here
    console.log("User Prefs reset");
    await removeItem(DO_NOT_ASKAGAIN);
    setDelPrefs(false);
  };

  const handleDarkMode = async () => {
    setDarkMode(!darkMode);
    const theme = darkMode ? "light" : "dark";
    Appearance.setColorScheme(theme);
    await setItem(THEME, theme);
    console.log("dark mode switched");
  };
  const handleGeofencingSwitch = async () => {
    setGeofencingSwitch(!geofencingSwitch);
    console.log("geofencing switched");
    if (geofencingSwitch) {
      await setItem(GEOFENCING, "false");
      stopGeofencing();
    } else {
      await setItem(GEOFENCING, "true");
      startGeofencing(pins);
    }
  };

  const navigateToAbout = () => {
    router.push("/home/about");
    console.log("Navigate to about");
  };

  return (
    <ScrollView>
      <SettingsCard
        title="Dark Theme"
        text="Switch Between Light and Dark Theme"
        toggle={true}
        toggleValue={darkMode}
        toggleAction={handleDarkMode}
      />
      <SettingsCard
        title="Delete User Data"
        text="Deletes all user data, including preferences"
        onPress={showDelData}
      />
      <SettingsCard
        title="Reset Preferences"
        text="Resets all user preferences"
        onPress={showDelPrefs}
      />
      <SettingsCard
        title="Geofencing"
        text="Enable or disable geofencing"
        toggle={true}
        toggleValue={geofencingSwitch}
        toggleAction={handleGeofencingSwitch}
      />
      <SettingsCard title="About BraGuia" text="" onPress={navigateToAbout} />

      <Portal>
        <Dialog visible={delData} onDismiss={hideDelData}>
          <Dialog.Title>Delete User Data</Dialog.Title>
          <Dialog.Content>
            <Paragraph>
              By pressing confirm, your user data like history, bookmarks and
              preferences will all be deleted
            </Paragraph>
          </Dialog.Content>
          <Dialog.Actions>
            <Button onPress={hideDelData}>Cancel</Button>
            <Button onPress={handleDelData}>Confirm</Button>
          </Dialog.Actions>
        </Dialog>
      </Portal>
      <Portal>
        <Dialog visible={delPrefs} onDismiss={hideDelPrefs}>
          <Dialog.Title>Reset Preferences</Dialog.Title>
          <Dialog.Content>
            <Paragraph>
              By pressing, confirm all your preferences will be reset.
            </Paragraph>
          </Dialog.Content>
          <Dialog.Actions>
            <Button onPress={hideDelPrefs}>Cancel</Button>
            <Button onPress={handleResetPrefs}>Confirm</Button>
          </Dialog.Actions>
        </Dialog>
      </Portal>
    </ScrollView>
  );
}

const SettingsCard = ({
  title,
  text,
  onPress = () => {},
  toggle = false,
  toggleValue = false,
  toggleAction = () => {},
}) => {
  return (
    <TouchableOpacity onPress={onPress}>
      <Card style={styles.card}>
        <View style={styles.cardContainer}>
          <View style={styles.textContainer}>
            <Text style={styles.title}>{title}</Text>
            {text ? <Text style={styles.content}>{text}</Text> : null}
          </View>
          {toggle ? (
            <Switch value={toggleValue} onValueChange={toggleAction} />
          ) : null}
        </View>
      </Card>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  card: {
    margin: 4,
    padding: 10,
  },
  cardContainer: {
    flexDirection: "row",
    alignItems: "center",
  },
  textContainer: {
    flex: 1,
  },
  title: {
    fontSize: 16,
    fontWeight: "bold",
  },
  content: {
    fontSize: 14,
    marginBottom: 5,
  },
});
