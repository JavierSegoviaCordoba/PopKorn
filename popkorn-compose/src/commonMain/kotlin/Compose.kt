package cc.popkorn.compose.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cc.popkorn.inject

@Composable
inline fun <reified T> inject(environment: String? = null): T = remember { inject(environment) }
