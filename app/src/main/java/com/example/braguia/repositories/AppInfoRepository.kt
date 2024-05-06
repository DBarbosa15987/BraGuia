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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException


class AppInfoRepository(
    private val API: API,
    private val appInfoDAO: AppInfoDBDAO,
    private val socialDAO: SocialDAO,
    private val contactDAO: ContactDAO,
    private val partnerDAO: PartnerDAO
) {

    suspend fun fetchAppInfo() {

        try {
            val appInfoList = API.getAppInfo()

            try {
                val appInfo: AppInfo = appInfoList[0]
                val id = appInfo.appName

                appInfoDAO.deleteAll()
                appInfoDAO.insert(appInfo.toAppInfoDB())

                appInfo.contacts.forEach { contact -> contact.appInfoId = id }
                appInfo.socials.forEach { social -> social.appInfoId = id }
                appInfo.partners.forEach { partner -> partner.appInfoId = id }

                contactDAO.deleteAll()
                socialDAO.deleteAll()
                partnerDAO.deleteAll()

                contactDAO.insert(appInfo.contacts)
                socialDAO.insert(appInfo.socials)
                partnerDAO.insert(appInfo.partners)
            } catch (e: Exception) {
                Log.e("API", "IO Error or wrong api format $e")
            }

        } catch (e: Exception) {
            Log.e("API", e.toString())
        }
    }


    suspend fun getAppInfo(): AppInfo? {

        val appInfoDB: AppInfoDB
        try {
            appInfoDB = appInfoDAO.getAppInfo()[0]
            val id = appInfoDB.appName
            val socials: List<Social> = socialDAO.getSocials(id)
            val contacts: List<Contact> = contactDAO.getContacts(id)
            val partners: List<Partner> = partnerDAO.getPartner(id)
            return appInfoDB.toAppInfo(socials, contacts, partners)

        } catch (e: Exception) {
            Log.e("GETAPPINFO", e.toString())
        }

        return null

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




