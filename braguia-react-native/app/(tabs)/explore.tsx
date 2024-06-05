import Ionicons from '@expo/vector-icons/Ionicons';
import { StyleSheet, Image, Platform, Text, ScrollView } from 'react-native';

import { Collapsible } from '@/components/Collapsible';
import { ExternalLink } from '@/components/ExternalLink';
import ParallaxScrollView from '@/components/ParallaxScrollView';
import { ThemedText } from '@/components/ThemedText';
import { ThemedView } from '@/components/ThemedView';
import { useEffect, useState } from 'react';


const BASE_URL = 'http://192.168.85.186';

// const getAppInfo = async () => {
//   return fetch(BASE_URL + '/app')
//     .then(response => {
//       return response.json();
//     })
//     .catch(error => {
//       console.error(error);
//     });
// };

// async function getAppInfo(){
//   try {
//       const response = await fetch(BASE_URL+'/app');
//       if (!response.ok) {
//           throw new Error(
//               `Unable to Fetch Data, Please check URL
//               or Network connectivity!!`
//           );
//       }
//       const data = await response.json();
//       return data;
//   } catch (error) {
//       console.error('Some Error Occured:', error);
//   }
// }

export default function TabTwoScreen() {



  const [appInfo, setAppInfo] = useState("Loading...")
  const [testText, setTestText] = useState("")

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await fetch(BASE_URL + '/app');
        if (response.ok) {
          const data = await response.json();
          setAppInfo(data);
          setTestText("bomdia");
        }
      } catch (error) {
        console.error("Error parsing JSON:", error);
      }

    }
    fetchData();
  }, []);

  return (
    //<ScrollView></ScrollView>
    <ScrollView>
      <Text>{testText}</Text>
      <Text>{JSON.stringify(appInfo)}</Text>

    </ScrollView>
  );
}

const styles = StyleSheet.create({
  headerImage: {
    color: '#808080',
    bottom: -90,
    left: -35,
    position: 'absolute',
  },
  titleContainer: {
    flexDirection: 'row',
    gap: 8,
  },
});
