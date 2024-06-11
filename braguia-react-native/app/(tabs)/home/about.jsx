import { StyleSheet, Image, Platform, View, ScrollView, Linking, TouchableOpacity } from 'react-native';
import {Text} from 'react-native-paper'
import { useState } from 'react';
import { useSelector } from 'react-redux';

export default function AboutPage() {

  const appInfo = useSelector((state) => state.appData.appinfo);

  const handlePhonePress = (phone_number) => {
    Linking.openURL(`tel:${phone_number}`);
  };

  const handleUrlPress = (url) => {
    Linking.openURL(url);
  };

  return (
    <ScrollView>
      <View style={styles.container}>
        <Text variant='displaySmall' style={styles.section}>Contacts</Text>
      </View>
      {appInfo.contacts.map((contact, index) => (
        <ContactDetails key={index} contact={contact} handlePhonePress={handlePhonePress} handleUrlPress={handleUrlPress} />
      ))}
      <View style={styles.container}>
        <Text variant='displaySmall' style={styles.section}>Partners</Text>
      </View>
      {appInfo.partners.map((partner, index) => (
        <PartnerDetails key={index} partner={partner} handlePhonePress={handlePhonePress} handleUrlPress={handleUrlPress} />
      ))}
      <View style={styles.container}>
        <Text variant='displaySmall' style={styles.section}>Socials</Text>
      </View>
      {appInfo.socials.map((social, index) => (
        <SocialDetails key={index} social={social} handleUrlPress={handleUrlPress} />
      ))}
    </ScrollView>
  );
}

const ContactDetails = ({ contact, handlePhonePress, handleUrlPress }) => {

  return (
    <View style={styles.container}>
      <Text variant='headlineSmall' style={styles.title}>{contact.contact_name}</Text>
      <TouchableOpacity onPress={() => handlePhonePress(contact.contact_phone)}>
        <Text variant='titleMedium' style={styles.detail}>
          Phone:{' '}
          <Text variant='titleMedium' style={styles.url}>{contact.contact_phone}</Text>
        </Text>
      </TouchableOpacity>
      <TouchableOpacity onPress={() => handleUrlPress(contact.contact_url)}>
        <Text variant='titleMedium' style={styles.detail}>
          URL:{' '}
          <Text variant='titleMedium' style={styles.url}>{contact.contact_url}</Text>
        </Text>
      </TouchableOpacity>
      <Text variant='titleMedium' style={styles.detail}>
        Email:{' '}
        <Text variant='titleMedium' style={styles.subtitle}>{contact.contact_mail}</Text>
      </Text>

    </View>
  );
};

const PartnerDetails = ({ partner, handlePhonePress, handleUrlPress }) => {

  return (
    <View style={styles.container}>
      <Text variant='headlineSmall' style={styles.title}>{partner.partner_name}</Text>
      <TouchableOpacity onPress={() => handlePhonePress(partner.partner_phone)}>
        <Text variant='titleMedium' style={styles.detail}>
          Phone:{' '}
          <Text variant='titleMedium' style={styles.url}>{partner.partner_phone}</Text>
        </Text>
      </TouchableOpacity>
      <TouchableOpacity onPress={() => handleUrlPress(partner.partner_url)}>
        <Text variant='titleMedium' style={styles.detail}>
          URL:{' '}
          <Text variant='titleMedium' style={styles.url}>{partner.partner_url}</Text>
        </Text>
      </TouchableOpacity>
      <Text variant='titleMedium' style={styles.detail}>
        Email:{' '}
        <Text variant='titleMedium' style={styles.subtitle}>{partner.partner_mail}</Text>
      </Text>
    </View>
  );
};

const SocialDetails = ({ social, handleUrlPress }) => {


  return (
    <View style={styles.container}>
      <Text variant='headlineSmall' style={styles.title}>{social.social_name}</Text>
      <TouchableOpacity onPress={() => handleUrlPress(social.social_url)}>
        <Text variant='titleMedium' style={styles.detail}>
          URL:{' '}
          <Text variant='titleMedium' style={styles.url}>{social.social_url}</Text>
        </Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 10,
    alignItems: 'flex-start',
    justifyContent: '',
  },
  title: {
    marginBottom: 5,
    fontWeight: 'bold'
  },
  subtitle: {
    fontWeight: 'normal',
    marginBottom: 5,
  },
  url: {
    marginBottom: 5,
    color: 'blue',
    textDecorationLine: 'underline',
  },
  detail: {
    fontWeight: 'bold',
  },
  section: {
    fontWeight: 'bold',
    alignSelf: 'center'
  }

});