import React from "react";
import { ScrollView } from "react-native";
import { Card, Text } from "react-native-paper";
import { useSelector } from "react-redux";
import { router } from "expo-router";

function TrailCard(props) {
  return (
    // FIX: add on press to navigate to trail single page
    <Card onPress={() => router.push("/home/trail/" + props.id)}>
      <Card.Cover source={{ uri: props.image }} />
      <Card.Content>
        <Text variant="headlineMedium">{props.name}</Text>
        <Text>Difficulty: {props.difficulty}</Text>
        <Text>Duration: {props.duration}</Text>
      </Card.Content>
    </Card>
  );
}

export default function TrailsScreen() {
  const trails = useSelector((state) => state.trails.trails);
  if (!trails) {
    return alert("No trails found");
  }
  return (
    <ScrollView>
      {trails.map((trail) => {
        let trailPreview = {
          id: trail.id,
          name: trail.trail_name,
          image: trail.trail_img,
          duration: trail.trail_duration,
          difficulty: trail.trail_difficulty,
        };
        return <TrailCard key={trail.id} {...trailPreview} />
      })}
    </ScrollView>
  );
}
