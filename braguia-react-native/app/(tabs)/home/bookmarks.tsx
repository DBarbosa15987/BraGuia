import { ScrollView } from "react-native";
import { Text } from "react-native-paper";
import { useDispatch, useSelector } from "react-redux";
import { TrailCard } from "./trails"
import { toggleBookmark } from "@/state/actions/user";


export default function BookmarksPage() {

  const bookmarks = useSelector((state) => state.user.bookmarks);
  const trails = useSelector((state) => state.appData.trails);
  const bookmarkedTrails = trails.filter(trail => bookmarks.includes(trail.id));
  const dispatch = useDispatch()
  const handleToggleBookmark = (id) => {
    dispatch(toggleBookmark(id));
  };
  // NOTE: abstrair isto, seguindo o trails???
  return (
    <ScrollView>
      {bookmarkedTrails.map((trail) => {
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