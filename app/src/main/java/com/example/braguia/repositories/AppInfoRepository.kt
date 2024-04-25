package com.example.braguia.repositories

import android.util.Log
import com.example.braguia.model.AppInfo
import com.example.braguia.model.AppInfoDB
import com.example.braguia.model.dao.AppInfoDBDAO
import com.example.braguia.model.Contact
import com.example.braguia.model.dao.ContactDAO
import com.example.braguia.model.Partner
import com.example.braguia.model.dao.PartnerDAO
import com.example.braguia.model.Social
import com.example.braguia.model.dao.SocialDAO
import com.example.braguia.network.API


class AppInfoRepository(
    val API: API,
    val appInfoDAO: AppInfoDBDAO,
    val socialDAO: SocialDAO,
    val contactDAO: ContactDAO,
    val partnerDAO: PartnerDAO
) {


    suspend fun getAppInfo(): AppInfo {

        var appInfo:AppInfo = API.getAppInfo()[0]
        val appInfoListDB = appInfoDAO.getAppInfo()
        val id:String

        if (appInfoListDB.isNotEmpty()){
            id = appInfoListDB[0].appName
        }
        else{
            appInfoDAO.insert(appInfo.toAppInfoDB())
            id = appInfo.appName
            appInfo.contacts.forEach { contact -> contact.appInfoId = id }
            appInfo.socials.forEach { social -> social.appInfoId = id }
            appInfo.partners.forEach { partner -> partner.appInfoId = id }

            contactDAO.deleteAll()
            socialDAO.deleteAll()
            partnerDAO.deleteAll()

            contactDAO.insert(appInfo.contacts)
            socialDAO.insert(appInfo.socials)
            partnerDAO.insert(appInfo.partners)
        }

        //TODO Transaction aqui? coroutines?
        //TODO Cache check
        val appInfoListDB2 = appInfoDAO.getAppInfo()
        val socials: List<Social> = socialDAO.getSocials(id)
        val contacts: List<Contact> = contactDAO.getContacts(id)
        val partners: List<Partner> = partnerDAO.getPartner(id)
        appInfo = appInfoListDB2[0].toAppInfo(socials, contacts, partners)
        Log.i("DB2",appInfo.toString())

        return appInfo
    }



    fun AppInfo.toAppInfoDB() = AppInfoDB(
        appName = appName,
        appDesc = appDesc,
        landingPageText = landingPageText
    )

    fun AppInfoDB.toAppInfo(
        socials: List<Social>,
        contacts: List<Contact>,
        partners: List<Partner>
    ) = AppInfo(
        appName = appName,
        appDesc = appDesc,
        landingPageText = landingPageText,
        socials = socials,
        contacts = contacts,
        partners = partners

    )

}




