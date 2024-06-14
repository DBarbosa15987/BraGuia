import React, { useEffect } from "react";
import { FlatList, Image, Pressable, StyleSheet, View } from "react-native";
import { Text, Icon, Modal, Portal, IconButton } from "react-native-paper";
import { ResizeMode, Video } from "expo-av";
import {
  downloadMedia,
  getCachedMediaUri,
  removeDownloadedMedia,
} from "@/utils/mediaCaching";

function MediaItem({ item }) {
  const [visible, setVisible] = React.useState(false);
  const hideModal = () => setVisible(false);
  const showModal = () => setVisible(true);
  const [mediaUri, setMediaUri] = React.useState<string | null>(null);
  const [isLocalMedia, setIsLocalMedia] = React.useState(false);

  useEffect(() => {
    const checkMedia = async () => {
      const localMediaUri = await getCachedMediaUri(item.media_file);
      if (!localMediaUri) {
        setMediaUri(item.media_file);
      } else {
        setMediaUri(localMediaUri);
        setIsLocalMedia(true);
      }
    };
    checkMedia();
  }, [isLocalMedia]);

  const localDownloadMedia = async () => {
    await downloadMedia(item.media_file);
    setIsLocalMedia(true);
  };

  const localRemoveMedia = async () => {
    await removeDownloadedMedia(item.media_file);
    setIsLocalMedia(false);
  };

  if (!mediaUri) {
    return <Icon source="sync" size={100} />;
  }
  let itemElement;
  if (item.media_type === "I") {
    itemElement = <Image style={styles.imageItem} source={{ uri: mediaUri }} />;
  } else if (item.media_type === "V") {
    itemElement = <Icon source="file-video" size={100} />;
  } else {
    itemElement = <Icon source="file-music" size={100} />;
  }
  return (
    <View>
      <Pressable onPress={showModal}>
        <View style={styles.mediaItemBox}>{itemElement}</View>
        {isLocalMedia ? (
          <IconButton
            iconColor="black"
            style={styles.downloadIcon}
            icon="download"
            size={25}
            onPress={() => localRemoveMedia()}
          />
        ) : (
          <IconButton
            iconColor="black"
            style={styles.downloadIcon}
            icon="download-outline"
            size={25}
            onPress={() => localDownloadMedia()}
          />
        )}
      </Pressable>
      <Portal>
        <Modal
          style={{ paddingHorizontal: 20, paddingVertical: 100 }}
          visible={visible}
          onDismiss={hideModal}
        >
          {item.media_type === "I" ? (
            <Image
              style={{ width: "100%", height: "100%", objectFit: "scale-down" }}
              source={{ uri: mediaUri }}
            />
          ) : (
            <Video
              style={styles.mediaPlayer}
              source={{ uri: mediaUri }}
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
  downloadIcon: {
    alignSelf: "flex-end",
    position: "absolute",
    top: -15,
    right: -15,
  },
  mediaItemBox: {
    width: 100,
    height: 100,
  },
  imageItem: {
    flex: 1,
    width: "100%",
    height: "100%",
  },
  mediaPlayer: {
    backgroundColor: "black",
    width: "100%",
    height: "100%",
  },
});
