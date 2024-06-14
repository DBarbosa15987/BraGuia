import * as FileSystem from "expo-file-system";
const mediaCacheDir = FileSystem.cacheDirectory + "media/";

export async function ensureDirExists() {
  const dirInfo = await FileSystem.getInfoAsync(mediaCacheDir);
  if (!dirInfo.exists) {
    console.log("Media directory doesn't exist, creating...");
    await FileSystem.makeDirectoryAsync(mediaCacheDir, { intermediates: true });
  }
}

function getFileName(uri) {
  return uri.split("/").pop();
}

export async function downloadMedia(uri) {
  try {
    await ensureDirExists();
    const fileUri = mediaCacheDir + getFileName(uri);
    const { exists } = await FileSystem.getInfoAsync(fileUri);
    if (!exists) {
      console.log("Downloading media: ", uri);
      await FileSystem.downloadAsync(uri, fileUri);
    }
    return fileUri;
  } catch (error) {
    console.error("Error downloading media", error);
  }
}

export async function removeDownloadedMedia(uri) {
  const fileUri = mediaCacheDir + getFileName(uri);
  await FileSystem.deleteAsync(fileUri);
  console.log("Removed media: ", fileUri);
}

export async function clearMediaCache() {
  await FileSystem.deleteAsync(mediaCacheDir, { idempotent: true });
}

export async function getCachedMediaUri(uri) {
  const fileUri = mediaCacheDir + getFileName(uri);
  console.log("Checking for cached media: ", fileUri);
  const { exists } = await FileSystem.getInfoAsync(fileUri);
  if (exists) {
    return fileUri;
  }
  return null;
}
