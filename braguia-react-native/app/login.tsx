import React, { useState } from "react";
import { Text, View, TextInput, SafeAreaView, Pressable } from "react-native";
import { useDispatch } from "react-redux";
import { fetchUserInfo, login } from "../api/api";
import { router } from "expo-router";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const loginPress = () => {
    login(username, password).then((loggedIn) => {
      if (loggedIn) {
        //fetchUserInfo(dispatch);
        console.log("Login success");
        //router.replace("index");
      } else {
        alert("Login failed");
      }
    });
  };

  return (
    <SafeAreaView>
      <Text> Login Page </Text>
      <View>
        <TextInput
          placeholder="Username"
          value={username}
          onChangeText={setUsername}
          autoCorrect={false}
          autoCapitalize="none"
        />
        <TextInput
          placeholder="Password"
          secureTextEntry
          value={password}
          onChangeText={setPassword}
          autoCorrect={false}
          autoCapitalize="none"
        />
      </View>
      <View>
        <Pressable onPress={loginPress}>
          <Text>LOGIN</Text>
        </Pressable>
      </View>
    </SafeAreaView>
  );
}
