package com.example.mynotes

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.EasyEditSpan
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.mynotes.databinding.ListDetailActivityBinding
import com.example.mynotes.models.TaskList
import com.example.mynotes.ui.detail.ListDetailFragment
import com.example.mynotes.ui.main.MainViewModel
import com.example.mynotes.ui.main.MainViewModelFactory
import java.awt.font.TextAttribute

class ListDetailActivity : AppCompatActivity() {
    private lateinit var binding: ListDetailActivityBinding
    private lateinit var  viewModel: MainViewModel
    lateinit var textEdit: EditText
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this))
        ).get(MainViewModel::class.java)

        binding = ListDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        title = viewModel.list.name
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListDetailFragment.newInstance())
                .commitNow()
        }

    }
    override fun onPostCreate(savedInstanceState: Bundle?) {

        sharedPreferences = getSharedPreferences("", Context.MODE_PRIVATE)
        var loadnote = sharedPreferences.getString(viewModel.list.name,"")
        textEdit = findViewById(R.id.textEdit)
        textEdit.setText(loadnote)

        super.onPostCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        textEdit = findViewById(R.id.textEdit)
        val insertedText = textEdit.text.toString()
        sharedPreferences = getSharedPreferences("", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(viewModel.list.name,insertedText).apply()

        super.onBackPressed()
    }
}