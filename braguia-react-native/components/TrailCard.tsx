import React from "react";
import { router } from "expo-router";
import { Card, Text, IconButton } from "react-native-paper";
import { formatDate } from "@/utils/trails";

export function TrailCard(props) {
  return (
    <Card elevation={3} onPress={() => router.push("/home/trail/" + props.id)}>
      <Card.Cover source={{ uri: props.image }} />
      <Card.Content style={{ flex: 1}}>
        <Text variant="headlineMedium">{props.name}</Text>
        <Text>Difficulty: {props.difficulty}</Text>
        <Text>Duration: {props.duration}</Text>
        {"timestamp" in props ?
          <Text>Accessed: {formatDate(props.timestamp)}</Text> : <></>
        }
        <IconButton style={{ position: "absolute", right: 0, top: "20%" }}
          icon={props.isBookmark ? "bookmark" : "bookmark-outline"}
          size={27}
          onPress={() => props.toggle(props.id)}
        />
      </Card.Content>
    </Card>
  );
}
