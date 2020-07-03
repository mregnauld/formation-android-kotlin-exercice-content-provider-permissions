package com.formationandroid.contentproviderpermissions

import android.content.Context
import android.provider.ContactsContract

class ContactsDAO
{

    /**
     * Récupération d'une liste de contacts.
     * @param context Context
     */
    fun getListeContacts(context: Context): List<Contact>
    {
        // tri :
        val tri = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC "

        // requête :
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            tri)

        // liste de contacts finale :
        val listeContacts: MutableList<Contact> = ArrayList()
        if (cursor != null)
        {
            try
            {
                while (cursor.moveToNext())
                {
                    // conversion des données remontées en un objet métier :
                    listeContacts.add(Contact(
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))))
                }
            }
            catch (e: Exception)
            {
                e.printStackTrace()
            }
            finally
            {
                cursor.close()
            }
        }
        return listeContacts
    }
}