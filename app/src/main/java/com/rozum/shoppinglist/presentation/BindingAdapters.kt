package com.rozum.shoppinglist.presentation

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.rozum.shoppinglist.R

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(inputLayout: TextInputLayout, error: Boolean) {
    inputLayout.error = if (error) {
        inputLayout.context.getString(R.string.error_count)
    } else {
        null
    }
}

@BindingAdapter("errorInputName")
fun bindErrorInputName(inputLayout: TextInputLayout, error: Boolean) {
    inputLayout.error = if (error) {
        inputLayout.context.getString(R.string.error_name)
    } else {
        null
    }
}

@BindingAdapter("resetErrorInputName")
fun bindTextChangedForName(textInputEditTextName: EditText, viewModel: ShopItemViewModel) {
    textInputEditTextName.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.resetErrorInputName()
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}

@BindingAdapter("resetErrorInputCount")
fun bindTextChangedForCount(textInputEditTextCount: EditText, viewModel: ShopItemViewModel) {
    textInputEditTextCount.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.resetErrorInputCount()
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}
