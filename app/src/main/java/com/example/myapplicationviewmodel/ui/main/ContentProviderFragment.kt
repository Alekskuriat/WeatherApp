package com.example.myapplicationviewmodel.ui.main

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationviewmodel.R
import com.example.myapplicationviewmodel.appState.AppState
import com.example.myapplicationviewmodel.appState.AppStateContacts
import com.example.myapplicationviewmodel.contacts.Contact
import com.example.myapplicationviewmodel.databinding.FragmentContentProviderBinding
import com.example.myapplicationviewmodel.utils.showSnackBar
import kotlinx.android.synthetic.main.fragment_history.view.*


const val REQUEST_CODE = 42
private const val IS_WORLD_KEY = "SORTING"

class ContentProviderFragment : Fragment() {

    private var _binding: FragmentContentProviderBinding? = null
    private val binding get() = _binding!!
    private var sortContacts: Boolean = true
    private val viewModel: ContentProviderViewModel by lazy {
        ViewModelProvider(this)
            .get(ContentProviderViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermissionGetContacts()
        binding.sortBtn.setOnClickListener { sortingContacts() }
        viewModel.contactLiveData.observe(viewLifecycleOwner, Observer { renderData(it) })
        activity?.let {
            sortContacts = it.getPreferences(Context.MODE_PRIVATE).getBoolean(IS_WORLD_KEY, true)
        }

    }

    private fun sortingContacts() {
        sortContacts = !sortContacts
        saveSorting(sortContacts)
        context?.let { viewModel.getListContacts(it) }
    }

    private fun saveSorting(sortContacts: Boolean) {
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putBoolean(IS_WORLD_KEY, sortContacts)
                apply()
            }
        }
    }

    private fun renderData(AppState: AppStateContacts) {
        when (AppState) {
            is AppStateContacts.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                activity?.let {
                    if (sortContacts) {
                        for (i in AppState.listContacts.indices) {
                            context?.let { context ->
                                addView(
                                    context,
                                    AppState.listContacts[i].id,
                                    AppState.listContacts[i].name,
                                    AppState.listContacts[i].phone,
                                )
                            }
                        }
                    } else {
                        for (i in AppState.listContacts.size - 1 downTo 0) {
                            context?.let { context ->
                                addView(
                                    context,
                                    AppState.listContacts[i].id,
                                    AppState.listContacts[i].name,
                                    AppState.listContacts[i].phone,
                                )
                            }
                        }
                    }
                }
            }

            is AppStateContacts.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
                binding.containerForContacts.removeAllViews()
            }
            is AppStateContacts.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.containerForContacts.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        context?.let { viewModel.getListContacts(it) }
                    })
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPermissionGetContacts() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    context?.let { viewModel.getListContacts(it) }
                }
                //Опционально: если нужно пояснение перед запросом разрешений
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к совершению звонков")
                        .setMessage("Объяснение")
                        .setPositiveButton("Предоставить доступ") { q, m ->
                            requestPermission()
                        }
                        .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun checkPermissionCall(): Boolean {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.CALL_PHONE) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    return true
                }
                shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к совершению звонков")
                        .setMessage("Объяснение")
                        .setPositiveButton("Предоставить доступ") { q, m ->
                            requestPermission()
                        }
                        .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> {
                    requestPermission()
                }
            }
        }
        return false
    }


    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray,
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    context?.let { viewModel.getListContacts(it) }
                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Доступ к контактам")
                            .setMessage("Объяснение")
                            .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
                if (!(grantResults.isNotEmpty() &&
                            grantResults[1] == PackageManager.PERMISSION_GRANTED)
                ) {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Доступ к совершению звонков")
                            .setMessage("Вы запретили приложению совершать звонки")
                            .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
                return
            }
        }
    }


    private fun addView(context: Context, id: Int, name: String, phoneNumber: String) {
        val str = "$id. $name"
        binding.containerForContacts.addView(AppCompatTextView(context).apply {
            text = str
            textSize = resources.getDimension(R.dimen.margin_8_dp)
            setPadding(0, 0, 0, 10)
            setOnClickListener {
                AlertDialog.Builder(getContext())
                    .setTitle(name)
                    .setMessage("Номер телефона: $phoneNumber")
                    .setPositiveButton("Позвонить") { dialog, _ ->
                        if (checkPermissionCall()) {
                            val intent = Intent(Intent.ACTION_CALL)
                            intent.data = Uri.parse("tel:$phoneNumber")
                            startActivity(intent)
                        }

                    }
                    .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()
            }
        })

    }


    companion object {
        @JvmStatic
        fun newInstance() =
            ContentProviderFragment()
    }


}