import React from "react";
import { FlatList, ScrollView, StyleSheet } from "react-native";
import { useDispatch, useSelector } from "react-redux";
import { setBookmarks } from "@/state/actions/user";
import { TrailCard } from "@/components/TrailCard";


export default function TrailsScreen() {
  const trails = useSelector((state) => state.appData.trails);
  const bookmarks = useSelector((state) => state.user.bookmarks);
  console.log("bookmarks:" + bookmarks)
  const dispatch = useDispatch()
  const handleToggleBookmark = (bookmarkId, isBookmark) => {
    if (isBookmark) {
      dispatch(setBookmarks(bookmarks.filter((bookmark) => bookmark !== bookmarkId)))
    }
    else {
      dispatch(setBookmarks([bookmarkId, ...bookmarks]))
    }
  };

  if (!trails) {
    return alert("No trails found");
  }
  // FIXME: FlatList aqui??
  return (
    <FlatList style={styles.container}
      contentContainerStyle={{ gap: 5 }}
      data={trails}
      keyExtractor={(item) => item.id}
      renderItem={({ item: trail }) => {
        const isBookmark = bookmarks.includes(trail.id)
        let trailPreview = {
          id: trail.id,
          name: trail.trail_name,
          image: trail.trail_img,
          duration: trail.trail_duration,
          difficulty: trail.trail_difficulty,
          isBookmark: isBookmark,
          toggle: () => handleToggleBookmark(trail.id, isBookmark)
        };
        return <TrailCard key={trail.id} {...trailPreview} />
      }}
    />
  );
}

const styles = StyleSheet.create({
  container: {
    padding: 5,
  },
});
