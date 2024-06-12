import React, { useState } from "react";
import { ScrollView, Text, View, StyleSheet, Image } from "react-native";
import { useDispatch, useSelector } from "react-redux";
import {
  Button,
  Card,
  Avatar,
  Dialog,
  Portal,
  Paragraph,
} from "react-native-paper";
import { logout } from "@/api/api";
import { router } from "expo-router";

export default function UserPage() {
  const userInfo = useSelector((state) => state.user.info);
  const [visible, setVisible] = useState(false);
  const dispatch = useDispatch();

  const showDialog = () => setVisible(true);
  const hideDialog = () => setVisible(false);

  const handleLogout = () => {
    // Perform logout logic here
    console.log("User logged out");
    hideDialog();
    logout(dispatch);
    router.replace("/login");
  };

  return (
    <ScrollView>
      <View style={styles.imageContainer}>
        <Avatar.Image
          size={150}
          source={
            userInfo.user_type.toLowerCase() === "premium"
              ? require("@/assets/images/marega.jpg")
              : require("@/assets/images/npc.png")
          }
        />
      </View>
      <ProfileCard
        title="Name"
        icon="account"
        text={`${userInfo.first_name} ${userInfo.last_name}`}
      />
      <ProfileCard title="Username" icon="account" text={userInfo.username} />
      <ProfileCard title="User Type" icon="account" text={userInfo.user_type} />
      <ProfileCard
        title="Last Login"
        icon="account"
        text={convertDate(userInfo.last_login)}
      />
      <ProfileCard
        title="Date Joined"
        icon="account"
        text={convertDate(userInfo.date_joined)}
      />

      <Button mode="contained" onPress={showDialog} style={styles.logoutButton}>
        Logout
      </Button>
      <Portal>
        <Dialog visible={visible} onDismiss={hideDialog}>
          <Dialog.Title>Confirm Logout</Dialog.Title>
          <Dialog.Content>
            <Paragraph>Are you sure you want to logout?</Paragraph>
          </Dialog.Content>
          <Dialog.Actions>
            <Button onPress={hideDialog}>Cancel</Button>
            <Button onPress={handleLogout}>Logout</Button>
          </Dialog.Actions>
        </Dialog>
      </Portal>
    </ScrollView>
  );
}

const ProfileCard = ({ title, icon, text }) => {
  return (
    <Card style={styles.card}>
      <View style={styles.container}>
        <Avatar.Icon size={40} icon={icon} style={styles.icon} />
        <View style={styles.textContainer}>
          <Text style={styles.title}>{title}</Text>
          <Text style={styles.content}>{text}</Text>
        </View>
      </View>
    </Card>
  );
};

function convertDate(dateString: string) {
  try {
    const date: Date = new Date(dateString);
    const day = String(date.getUTCDate()).padStart(2, "0");
    const month = String(date.getUTCMonth() + 1).padStart(2, "0");
    const year = date.getUTCFullYear();
    return `${day}-${month}-${year}`;
  } catch (e) {
    return dateString;
  }
}

const styles = StyleSheet.create({
  card: {
    margin: 6,
    padding: 6,
  },
  container: {
    flexDirection: "row",
    alignItems: "center",
  },
  icon: {
    margin: 10,
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
    marginBottom: 10,
  },
  imageContainer: {
    alignItems: "center",
    marginBottom: 20,
  },
  profileImage: {
    width: 100,
    height: 100,
    borderRadius: 50,
    marginTop: 20,
  },
  logoutButton: {
    marginTop: 20,
    paddingVertical: 5,
    paddingHorizontal: 20,
  },
});

