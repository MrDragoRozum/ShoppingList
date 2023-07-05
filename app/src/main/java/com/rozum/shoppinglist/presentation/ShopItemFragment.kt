package com.rozum.shoppinglist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.rozum.shoppinglist.R
import com.rozum.shoppinglist.domain.ShopItem

class ShopItemFragment : Fragment() {

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputEditTextName: EditText
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var textInputEditTextCount: EditText
    private lateinit var buttonSave: Button

    private lateinit var viewModel: ShopItemViewModel

    private var screenMode: String = UNDEFINED_MODE
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        launchRightMode()
        viewModelObserves()
        listener()
    }


        private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun listener() {
        textInputEditTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        textInputEditTextCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun viewModelObserves() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            textInputLayoutCount.error = if (it) {
                getString(R.string.error_count)
            } else {
                null
            }
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            textInputLayoutName.error = if (it) {
                getString(R.string.error_name)
            } else {
                null
            }
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) { activity?.onBackPressedDispatcher?.onBackPressed() }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            textInputEditTextName.setText(it.name)
            textInputEditTextCount.setText(it.score.toString())
        }
        buttonSave.setOnClickListener {
            val name = textInputEditTextName.text.toString()
            val count = textInputEditTextCount.text.toString()
            viewModel.editShopItem(name, count)
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            val name = textInputEditTextName.text.toString()
            val count = textInputEditTextCount.text.toString()
            viewModel.addShopItem(name, count)
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("SCREEN_MODE пустой")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Неизвестный mode: $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("EXTRA_SHOP_ITEM_ID пустой")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        textInputLayoutName = view.findViewById(R.id.textInputLayoutName)
        textInputEditTextName = view.findViewById(R.id.textInputEditTextName)
        textInputLayoutCount = view.findViewById(R.id.textInputLayoutCount)
        textInputEditTextCount = view.findViewById(R.id.textInputEditTextCount)
        buttonSave = view.findViewById(R.id.buttonSave)
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val UNDEFINED_MODE = ""

        fun newInstanceAddItem() = ShopItemFragment().apply {
            arguments = Bundle().apply {
                putString(SCREEN_MODE, MODE_ADD)
            }
        }
        fun newInstanceEditItem(shopItemId: Int) = ShopItemFragment().apply {
            arguments = Bundle().apply {
                putString(SCREEN_MODE, MODE_EDIT)
                putInt(SHOP_ITEM_ID, shopItemId)
            }
        }
    }
}