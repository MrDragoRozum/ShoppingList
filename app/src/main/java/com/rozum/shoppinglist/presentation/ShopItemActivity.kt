package com.rozum.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.rozum.shoppinglist.R
import com.rozum.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputEditTextName: EditText
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var textInputEditTextCount: EditText
    private lateinit var buttonSave: Button

    private lateinit var viewModel: ShopItemViewModel

    private var screenMode = UNDEFINED_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        initViews()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        when(screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        // TODO: Реализовать редактирование
    }

    private fun launchAddMode() {
        // TODO: Реализовать добавление
    }

    private fun parseIntent() {
        if(!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("EXTRA_SCREEN_MODE пустой")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if(mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Неизвестный mode: $mode")
        }
        screenMode = mode
        if(screenMode == MODE_EDIT) {
            if(!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("EXTRA_SHOP_ITEM_ID пустой")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews() {
        textInputLayoutName = findViewById(R.id.textInputLayoutName)
        textInputEditTextName = findViewById(R.id.textInputEditTextName)
        textInputLayoutCount = findViewById(R.id.textInputLayoutCount)
        textInputEditTextCount = findViewById(R.id.textInputEditTextCount)
        buttonSave = findViewById(R.id.buttonSave)
    }

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