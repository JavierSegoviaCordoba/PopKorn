package cc.popkorn.android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import cc.popkorn.popKorn

class PopKornViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = popKorn().inject(modelClass.kotlin)
}

inline fun <reified T : ViewModel> ViewModelStoreOwner.viewModel(): T {
    return ViewModelProvider(this, PopKornViewModelFactory()).get(T::class.java)
}
