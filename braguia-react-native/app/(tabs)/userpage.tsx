import React from 'react';
import { ScrollView, Text } from 'react-native';
import { useSelector } from 'react-redux';

export default function UserPage() {

    const userInfo = useSelector((state) => state.userInfo);

    return (
        <ScrollView>
            <Text>{JSON.stringify(userInfo)}</Text>
        </ScrollView>
    );
}
