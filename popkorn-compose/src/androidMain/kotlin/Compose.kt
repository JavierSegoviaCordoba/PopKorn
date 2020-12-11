package cc.popkorn.compose.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.AmbientViewModelStoreOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cc.popkorn.android.viewModel.PopKornViewModelFactory

@Composable
inline fun <reified T : ViewModel> viewModel(): T {
    val viewModelStore = AmbientViewModelStoreOwner.current.viewModelStore
    return remember {
        ViewModelProvider(viewModelStore, PopKornViewModelFactory()).get(T::class.java)
    }
}
