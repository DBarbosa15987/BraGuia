package com.example.braguia.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.braguia.BraGuiaApplication
import com.example.braguia.repositories.UserRepository

object BraGuiaViewModelProvider {

    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            TrailsViewModel(
                inventoryApplication().container.trailRepository,
                inventoryApplication().container.appInfoRepository
            )
        }
        initializer {
            UserViewModel(
                inventoryApplication().container.userRepository
            )
        }
    }

}

fun CreationExtras.inventoryApplication(): BraGuiaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BraGuiaApplication)
