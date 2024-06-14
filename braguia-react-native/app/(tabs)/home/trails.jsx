import React from "react";
import { ScrollView } from "react-native";
import { useDispatch, useSelector } from "react-redux";
import { toggleBookmark } from "@/state/actions/user";
import { TrailCard } from "@/components/TrailCard";


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
        return <TrailCard key={trail.id} {...trailPreview} />
      })}
    </ScrollView>
  );
}

