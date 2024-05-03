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
import retrofit2.HttpException


class AppInfoRepository(
    val API: API,
    val appInfoDAO: AppInfoDBDAO,
    val socialDAO: SocialDAO,
    val contactDAO: ContactDAO,
    val partnerDAO: PartnerDAO
) {


    suspend fun fetchAppInfo() {

        val appInfo: AppInfo

        try {

            appInfo = API.getAppInfo()[0]
            appInfoDAO.deleteAll()
            val appInfoListDB = appInfoDAO.getAppInfo()

            val id: String

            //TODO deixamos o anterior na db ou limpamos tudo??
            if (appInfoListDB.isNotEmpty()) {
                val appInfoDB = appInfoDAO.getAppInfo()[0]
                if (appInfo.appName != appInfoDB.appName) {
                    //TODO deleteAll e repopulação
                }

            } else {
                appInfoDAO.insert(appInfo.toAppInfoDB())
                id = appInfo.appName
                appInfo.contacts.forEach { contact -> contact.appInfoId = id }
                appInfo.socials.forEach { social -> social.appInfoId = id }
                appInfo.partners.forEach { partner -> partner.appInfoId = id }

                //TODO ainda é para dar delete?
                contactDAO.deleteAll()
                socialDAO.deleteAll()
                partnerDAO.deleteAll()

                contactDAO.insert(appInfo.contacts)
                socialDAO.insert(appInfo.socials)
                partnerDAO.insert(appInfo.partners)
            }
            //TODO Transaction aqui? coroutines?
            //TODO Cache check
        }
        catch (e: HttpException){
            Log.e("API",e.toString())
        }
    }

    suspend fun getAppInfo(): AppInfo {

        val appInfoListDB = appInfoDAO.getAppInfo()
        val appInfoDB = appInfoListDB[0]
        val id = appInfoDB.appName
        val socials: List<Social> = socialDAO.getSocials(id)
        val contacts: List<Contact> = contactDAO.getContacts(id)
        val partners: List<Partner> = partnerDAO.getPartner(id)

        return appInfoDB.toAppInfo(socials, contacts, partners)
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




