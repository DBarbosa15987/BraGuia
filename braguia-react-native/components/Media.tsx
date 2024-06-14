import React from "react";
import { FlatList, Image, Pressable, StyleSheet, View } from "react-native";
import { Text, Icon, Modal, Portal } from "react-native-paper";
import { ResizeMode, Video } from "expo-av";

function MediaItem({ item }) {
  const [visible, setVisible] = React.useState(false);
  const hideModal = () => setVisible(false);
  const showModal = () => setVisible(true);
  let itemElement;
  if (item.media_type === "I") {
    itemElement = (
      <Image style={styles.mediaItem} source={{ uri: item.media_file }} />
    );
  } else if (item.media_type === "V") {
    itemElement = <Icon size={100} source="file-video" />;
  } else {
    itemElement = <Icon source="file-music" size={100} />;
  }
  return (
    <View>
      <Pressable onPress={showModal}>{itemElement}</Pressable>
      <Portal>
        <Modal
          style={{ paddingHorizontal: 20, paddingVertical: 100 }}
          visible={visible}
          onDismiss={hideModal}
        >
          {item.media_type === "I" ? (
            <Image
              style={{ width: "100%", height: "100%", objectFit: "scale-down" }}
              source={{ uri: item.media_file }}
            />
          ) : (
            <Video
              style={styles.mediaPlayer}
              source={{ uri: item.media_file }}
              useNativeControls={true}
              resizeMode={ResizeMode.CONTAIN}
              isLooping={false}
            />
          )}
        </Modal>
      </Portal>
    </View>
  );
}

export function MediaSection({ media }) {
  // TODO: Add styling
  return (
    <View>
      <Text variant="headlineSmall">Media</Text>
      <FlatList
        style={{ paddingTop: 5 }}
        horizontal={true}
        data={media}
        keyExtractor={(item, index) => item.id + index}
        renderItem={({ item }) => <MediaItem item={item} />}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  mediaItem: {
    width: 100,
    height: 100,
  },
  mediaPlayer: {
    backgroundColor: "black",
    width: "100%",
    height: "100%",
  },
});
