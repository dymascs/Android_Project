package br.com.dymascs.android_project.network


import android.util.Log
import br.com.dymascs.android_project.SharedPreferencesUtils
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

private const val TAG = "OauthTokenAuthenticator"

class OauthTokenAuthenticator() : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken: String?

        val token = retrieveNewToken()
        accessToken = token.accessToken
        SharedPreferencesUtils.saveToken(token.accessToken, token.expiresIn)

        return response.request().newBuilder()
            .header("Authorization", "Bearer ${accessToken}")
            .build()
    }

    private fun retrieveNewToken(): OauthTokenResponse {
        Log.i(TAG, "Retrieving new token")
        return SalesApi.retrofitService.getToken(
            "Basic c2llY29sYTptYXRpbGRl",
            "password",
            "matilde@siecola.com.br",
            "matilde"
        ).execute().body()!!
    }

}