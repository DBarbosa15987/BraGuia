import React, { useEffect, useState } from "react";
import { View, StyleSheet } from "react-native";
import {
  Text,
  TextInput,
  Button,
  Portal,
  Dialog,
  Paragraph,
  Checkbox,
} from "react-native-paper";
import { useDispatch, useSelector } from "react-redux";
import { fetchUserInfo, login } from "../api/api";
import { router } from "expo-router";
import { getItem, setItem } from "../utils/asyncStorage";
import { DO_NOT_ASKAGAIN } from "@/constants/preferences";
import DialogIcon from "react-native-paper/lib/typescript/components/Dialog/DialogIcon";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showDialog, setShowDialog] = useState(false);
  //const [showGoogleMaps, setShowGoogleMaps] = useState(true);
  const [ask, setAsk] = useState<boolean | null>(null);
  const [checked, setChecked] = useState(false);
  const dispatch = useDispatch();
  const appInfo = useSelector((state) => state.appData.appinfo);

  const handleConfirm = () => {
    setAsk(false);
    if (checked) {
      setItem(DO_NOT_ASKAGAIN, "yes");
    }
  };

  useEffect(() => {
    const getAsk = async () => {
      const askAgain = await getItem(DO_NOT_ASKAGAIN);
      setAsk(!askAgain);
      console.log("askAgain:", askAgain, !askAgain);
    };
    getAsk();
  }, []);

  const loginPress = () => {
    login(username, password).then((loggedIn) => {
      if (loggedIn) {
        fetchUserInfo(dispatch);
        console.log("Login success");
        router.replace("/home");
      } else {
        setShowDialog(true);
      }
    });
  };

  const resetLogin = () => {
    setShowDialog(false);
    setPassword("");
  };
  if (ask == null) {
    return null;
  }

  return (
    <View style={styles.container}>
      <Text variant="displayLarge" style={styles.title}>
        {appInfo.app_name}
      </Text>
      <View style={styles.inputContainer}>
        <TextInput
          placeholder="Username"
          value={username}
          onChangeText={setUsername}
          autoCorrect={false}
          autoCapitalize="none"
          style={styles.input}
        />
        <TextInput
          placeholder="Password"
          secureTextEntry
          value={password}
          onChangeText={setPassword}
          autoCorrect={false}
          autoCapitalize="none"
          style={styles.input}
        />
      </View>
      <Button mode="contained" onPress={loginPress} style={styles.button}>
        Login
      </Button>
      <Portal>
        <Dialog visible={showDialog} onDismiss={resetLogin}>
          <Dialog.Title>Login Failed</Dialog.Title>
          <Dialog.Content>
            <Paragraph>
              Login failed due to wrong credentials or network issues
            </Paragraph>
          </Dialog.Content>
          <Dialog.Actions>
            <Button onPress={resetLogin}>Dismiss</Button>
          </Dialog.Actions>
        </Dialog>
        <Dialog visible={ask} onDismiss={() => setAsk(false)}>
          <Dialog.Icon icon="alert" />
          <Dialog.Title style={{ textAlign: "center" }}>Warning</Dialog.Title>
          <Dialog.Content>
            <Paragraph>
              Some features in this app require Google Maps to work correctly
            </Paragraph>
          </Dialog.Content>
          <Dialog.Actions>
            <Text>{"Don't ask again"}</Text>
            <Checkbox
              status={checked ? "checked" : "unchecked"}
              onPress={() => {
                setChecked(!checked);
              }}
            />
            <Button
              onPress={() => {
                handleConfirm();
              }}
            >
              Confirm
            </Button>
          </Dialog.Actions>
        </Dialog>
      </Portal>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    padding: 10,
  },
  inputContainer: {
    width: "65%",
    marginBottom: 10,
  },
  input: {
    marginBottom: 10,
  },
  button: {
    width: "40%",
    alignSelf: "center",
  },
  title: {
    fontFamily: "Cursive", // This depends on available fonts, adjust as needed
    marginBottom: 50,
  },
});
