package com.rozum.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.rozum.shoppinglist.R
import com.rozum.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {
//
//    private lateinit var textInputLayoutName: TextInputLayout
//    private lateinit var textInputEditTextName: EditText
//    private lateinit var textInputLayoutCount: TextInputLayout
//    private lateinit var textInputEditTextCount: EditText
//    private lateinit var buttonSave: Button

//    private lateinit var viewModel: ShopItemViewModel
//
//    private var screenMode = UNDEFINED_MODE
//    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
//        parseIntent()
//        initViews()
//        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
//        launchRightMode()
//        viewModelObserves()
//        listener()
    }

//    private fun launchRightMode() {
//        when (screenMode) {
//            MODE_EDIT -> launchEditMode()
//            MODE_ADD -> launchAddMode()
//        }
//    }
//
//    private fun listener() {
//        textInputEditTextName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })
//
//        textInputEditTextCount.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })
//    }
//
//    private fun viewModelObserves() {
//        viewModel.errorInputCount.observe(this) {
//            textInputLayoutCount.error = if (it) {
//                getString(R.string.error_count)
//            } else {
//                null
//            }
//        }
//        viewModel.errorInputName.observe(this) {
//            textInputLayoutName.error = if (it) {
//                getString(R.string.error_name)
//            } else {
//                null
//            }
//        }
//        viewModel.shouldCloseScreen.observe(this) { finish() }
//    }
//
//    private fun launchEditMode() {
//        viewModel.getShopItem(shopItemId)
//        viewModel.shopItem.observe(this) {
//            textInputEditTextName.setText(it.name)
//            textInputEditTextCount.setText(it.score.toString())
//        }
//        buttonSave.setOnClickListener {
//            val name = textInputEditTextName.text.toString()
//            val count = textInputEditTextCount.text.toString()
//            viewModel.editShopItem(name, count)
//        }
//    }
//
//    private fun launchAddMode() {
//        buttonSave.setOnClickListener {
//            val name = textInputEditTextName.text.toString()
//            val count = textInputEditTextCount.text.toString()
//            viewModel.addShopItem(name, count)
//        }
//    }
//
//    private fun parseIntent() {
//        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
//            throw RuntimeException("EXTRA_SCREEN_MODE пустой")
//        }
//        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
//        if (mode != MODE_EDIT && mode != MODE_ADD) {
//            throw RuntimeException("Неизвестный mode: $mode")
//        }
//        screenMode = mode
//        if (screenMode == MODE_EDIT) {
//            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
//                throw RuntimeException("EXTRA_SHOP_ITEM_ID пустой")
//            }
//            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
//        }
//    }
//
//    private fun initViews() {
//        textInputLayoutName = findViewById(R.id.textInputLayoutName)
//        textInputEditTextName = findViewById(R.id.textInputEditTextName)
//        textInputLayoutCount = findViewById(R.id.textInputLayoutCount)
//        textInputEditTextCount = findViewById(R.id.textInputEditTextCount)
//        buttonSave = findViewById(R.id.buttonSave)
//    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val UNDEFINED_MODE = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}