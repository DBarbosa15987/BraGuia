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
import { DO_NOT_ASKAGAIN, THEME } from "@/constants/preferences";

export default function SettingsPage() {
  const userInfo = useSelector((state) => state.user.info);
  const [darkMode, setDarkMode] = useState(false);
  const [delData, setDelData] = useState(false);
  const [delPrefs, setDelPrefs] = useState(false);
  const dispatch = useDispatch();

  const showDelData = () => setDelData(true);
  const hideDelData = () => setDelData(false);
  const showDelPrefs = () => setDelPrefs(true);
  const hideDelPrefs = () => setDelPrefs(false);

  useEffect(() => {
    const theme = Appearance.getColorScheme();
    setDarkMode(theme === "dark");
  }, []);
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
    // await setItem(THEME, theme);
    console.log("dark mode switched");
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
