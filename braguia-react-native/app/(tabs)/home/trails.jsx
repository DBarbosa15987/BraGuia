import React from "react";
import { ScrollView } from "react-native";
import { Card, Text, IconButton } from "react-native-paper";
import { useDispatch, useSelector } from "react-redux";
import { router } from "expo-router";
import { toggleBookmark } from "@/state/actions/user";

function TrailCard(props) {

  return (
    // FIX: add on press to navigate to trail single page
    <Card onPress={() => router.push("/home/trail/" + props.id)}>
      <IconButton
        icon={props.isBookmark ? "bookmark" : "bookmark-outline"}
        size={24}
        onPress={()=>props.toggle(props.id)}
      />
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
  const trails = useSelector((state) => state.appData.trails);
  const bookmarks = useSelector((state) => state.user.bookmarks);
  console.log("bookmarks:" + bookmarks)
  const dispatch = useDispatch()
  const handleToggleBookmark = (id) => {
    dispatch(toggleBookmark(id));
  };

  if (!trails) {
    return alert("No trails found");
  }
  // FIXME: FlatList aqui??
  return (
    <ScrollView>
      {trails.map((trail) => {
        let trailPreview = {
          id: trail.id,
          name: trail.trail_name,
          image: trail.trail_img,
          duration: trail.trail_duration,
          difficulty: trail.trail_difficulty,
          isBookmark: bookmarks.includes(trail.id),
          toggle: handleToggleBookmark
        };
        return <TrailCard key={trail.id} /* toggle={handleToggleBookmark} */ {...trailPreview}  />
      })}
    </ScrollView>
  );
}
