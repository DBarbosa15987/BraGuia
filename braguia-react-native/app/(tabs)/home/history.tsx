import React from "react";
import { ScrollView } from "react-native";
import { useDispatch, useSelector } from "react-redux";
import { TrailCard } from "@/components/TrailCard"
import { setBookmarks } from "@/state/actions/user";


export default function HistoryPage() {

  const history = useSelector((state) => state.user.history);
  const bookmarks = useSelector((state) => state.user.bookmarks);
  const trails = useSelector((state) => state.appData.trails);
  const userHistory = history.map(entry => {
    const trail = trails.find(trail => trail.id === entry.trail);
    return trail ? { ...trail, timestamp: entry.timestamp } : null;
  }).filter(trail => trail !== null);

  const dispatch = useDispatch()
  const handleToggleBookmark = (bookmarkId,isBookmark) => {
    if (isBookmark) {
      dispatch(setBookmarks(bookmarks.filter((bookmark) => bookmark !== bookmarkId)))
    }
    else {
      dispatch(setBookmarks([bookmarkId,...bookmarks]))
    }
  };
  // NOTE: abstrair isto, seguindo o trails???
  return (
    <ScrollView>
      {userHistory.map((trail) => {
        const isBookmark = bookmarks.includes(trail.id)
        let trailPreview = {
          id: trail.id,
          name: trail.trail_name,
          image: trail.trail_img,
          duration: trail.trail_duration,
          difficulty: trail.trail_difficulty,
          isBookmark: isBookmark,
          toggle: () => handleToggleBookmark(trail.id, isBookmark),
          timestamp:trail.timestamp
        };
        // aqui a key toda maluca permite "repetidos" no histórico
        return <TrailCard key={trail.id + "-" + trail.timestamp} {...trailPreview} />
      })}
    </ScrollView>
  );
}
