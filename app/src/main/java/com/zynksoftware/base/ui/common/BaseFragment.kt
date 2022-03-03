package com.zynksoftware.base.ui.common

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding>(val viewBinder: (LayoutInflater) -> B) : Fragment() {

    protected var binding: B? = null

    private var toast: Toast? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return viewBinder(inflater).let {
            binding = it
            it.root
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.onViewCreated(savedInstanceState)
    }

    abstract fun B.onViewCreated(savedInstanceState: Bundle?)

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    protected fun navigateToNextDestination(navDirections: NavDirections) {
        val destinationId = findNavController().currentDestination?.getAction(navDirections.actionId)?.destinationId

        findNavController().currentDestination?.let { node ->
            val currentNode = when (node) {
                is NavGraph -> node
                else -> node.parent
            }
            if (destinationId != null) {
                currentNode?.findNode(destinationId)?.let {
                    findNavController().navigate(navDirections)
                }
            }
        }
    }

    protected fun navigate(@IdRes actionId: Int) {
        navigate(actionId, null)
    }

    protected fun navigate(@IdRes actionId: Int, args: Bundle?) {
        if (actionId == -1) {
            Toast.makeText(
                requireContext(),
                "Navigation destination not set yet!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val destinationId = findNavController().currentDestination?.getAction(actionId)?.destinationId

            findNavController().currentDestination?.let { node ->
                val currentNode = when (node) {
                    is NavGraph -> node
                    else -> node.parent
                }
                if (destinationId != null) {
                    currentNode?.findNode(destinationId)?.let {
                        findNavController().navigate(actionId, args)
                    }
                }
            }
        }
    }

    fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    protected fun showToastLong(message: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
        toast?.show()
    }

    protected fun hideKeyboard() {
        ViewCompat.getWindowInsetsController(requireView())?.hide(WindowInsetsCompat.Type.ime())
    }
}