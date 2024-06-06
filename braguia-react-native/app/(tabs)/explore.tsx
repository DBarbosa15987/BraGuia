import Ionicons from '@expo/vector-icons/Ionicons';
import { StyleSheet, Image, Platform, Text, ScrollView } from 'react-native';
import { Collapsible } from '@/components/Collapsible';
import { ExternalLink } from '@/components/ExternalLink';
import ParallaxScrollView from '@/components/ParallaxScrollView';
import { ThemedText } from '@/components/ThemedText';
import { ThemedView } from '@/components/ThemedView';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { updateAppInfo } from '@/state/actions/appInfo';
import { fetchAppInfo, fetchTrails } from '../api/api'

export default function TabTwoScreen() {

  const dispatch = useDispatch();
  const appInfo = useSelector((state) => state.appInfo);
  const trails = useSelector((state) => state.trails);

  useEffect(() => {
    
    fetchAppInfo(dispatch);
    fetchTrails(dispatch);
    //fetchUser();
  }, []);


  return (
    <ScrollView>
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
