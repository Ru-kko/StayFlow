package com.stayflow.app.data.local

import android.content.Context
import com.stayflow.app.domain.repository.TokenStorageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenStorageImpl @Inject constructor(
    @ApplicationContext context: Context
) : TokenStorageRepository {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    override fun saveToken(token: String, expires: Long) {
        prefs.edit()
            .putString("token", token)
            .putLong("expires", expires)
            .apply()
    }

    override fun getToken(): String? {
        val expires = prefs.getLong("expires", 0L)

        return if (System.currentTimeMillis() < expires) {
            prefs.getString("token", null)
        } else {
            clear()
            null
        }
    }

    override fun clear() {
        prefs.edit().clear().apply()
    }
}