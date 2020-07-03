package com.formationandroid.contentproviderpermissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{

	companion object
	{
		// Constantes :
		private const val PERMISSION_CONTACTS = 1
	}


	override fun onCreate(savedInstanceState: Bundle?)
	{
		// init :
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// demande d'autorisation, si ce n'est déjà fait :
		val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
		if (permission != PackageManager.PERMISSION_GRANTED)
		{
			// affichage de la popup demande de permission :
			ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), PERMISSION_CONTACTS)
		}
		else
		{
			// permission déjà accordée, on peut afficher les contacts sans risque :
			afficherContacts()
		}
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
	{
		if (requestCode == PERMISSION_CONTACTS)
		{
			if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
			{
				// permission accordée, on peut afficher les contacts sans risque :
				afficherContacts()
			}
			else
			{
				// permission refusée :
				Toast.makeText(this, R.string.main_message_permission_refusee, Toast.LENGTH_LONG).show()
			}
		}
	}

	/**
	 * Affichage des contacts.
	 */
	private fun afficherContacts()
	{
		// chargement :
		val contactsDAO = ContactsDAO()
		val listeContacts = contactsDAO.getListeContacts(this)

		// affichage de la liste de contacts :
		val stringBuilderContacts = StringBuilder()
		for (contact in listeContacts)
		{
			stringBuilderContacts.append(contact.nom).append(" : ").append(contact.numero).append("\n")
		}
		texte_contacts.text = stringBuilderContacts.toString()
	}

}