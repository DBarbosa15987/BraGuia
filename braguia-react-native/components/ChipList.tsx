import React from 'react';
import { View } from 'react-native';
import { Chip } from 'react-native-paper';

export function ChipList({ list }) {
  return (
    <View
      style={{
        alignItems: "center",
        flexDirection: "row",
        justifyContent: "center",
        flexWrap: "wrap-reverse",
        gap: 5,
      }}
    >
      {list.map((item) => (
        <Chip style={{alignSelf: "center"}}icon="information" key={item.id}>
          {item.attrib} : {item.value}
        </Chip>
      ))}
    </View>
  );
}
