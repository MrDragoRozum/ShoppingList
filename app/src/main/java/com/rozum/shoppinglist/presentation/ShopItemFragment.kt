package com.rozum.shoppinglist.presentation

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rozum.shoppinglist.databinding.FragmentShopItemBinding
import com.rozum.shoppinglist.domain.ShopItem
import javax.inject.Inject
import kotlin.concurrent.thread

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private var _binding: FragmentShopItemBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var screenMode: String = UNDEFINED_MODE
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    private val component by lazy {
        (requireActivity().application as AppShoppingList).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Вы должны реализовать интерфейс OnEditingFinishedListener!")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ShopItemViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        launchRightMode()
        viewModelObserves()

    }


    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun viewModelObserves() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        with(binding) {
            buttonSave.setOnClickListener {
                val name = textInputEditTextName.text.toString()
                val count = textInputEditTextCount.text.toString()
                viewModel?.editShopItem(name, count)
//                thread {
//                    requireContext().contentResolver.update(
//                        Uri.parse("content://com.rozum.shoppinglist/shop_items/"),
//                        ContentValues().apply {
//                            put("id", shopItemId)
//                            put("name", textInputEditTextName.text.toString())
//                            put("score", textInputEditTextCount.text.toString())
//                        },
//                        null,
//                        null
//                    )
//                }
            }
        }
    }

    private fun launchAddMode() {
        with(binding) {
            buttonSave.setOnClickListener {
                val name = textInputEditTextName.text.toString()
                val count = textInputEditTextCount.text.toString()
                viewModel?.addShopItem(name, count)
//                thread {
//                    requireContext().contentResolver.insert(
//                        Uri.parse("content://com.rozum.shoppinglist/shop_items"),
//                        ContentValues().apply {
//                            put("id", 0)
//                            put("name", textInputEditTextName.text.toString())
//                            put("score", textInputEditTextCount.text.toString())
//                            put("enabled", true)
//                        }
//                    )
//                }
            }
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

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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