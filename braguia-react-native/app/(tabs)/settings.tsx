import { StyleSheet, Image, Platform, Text, ScrollView } from 'react-native';

export default function SettingsPage() {
    return (
        <ScrollView>
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
