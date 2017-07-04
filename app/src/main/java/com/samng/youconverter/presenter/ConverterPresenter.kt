package com.samng.youconverter.presenter

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.webkit.URLUtil.isValidUrl
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.drive.Drive
import com.google.android.gms.drive.DriveApi
import com.google.android.gms.drive.MetadataChangeSet
import com.samng.injector.youconverter.network.ConvertUseCaseInjector.convertUseCase
import com.samng.model.Convertion
import com.samng.youconverter.ConverterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileInputStream
import java.io.IOException


class ConverterPresenter(val converterView: ConverterView) :
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    lateinit private var googleApiClient: GoogleApiClient
    lateinit private var link: String

    fun startPresenting(text: String?) {

        if (!isValidLink(text)) {
            converterView.showErrorMessgae()
            return
        }
        link = text!!

        connectGoogleApiClient()
    }

    fun stopPresenting() {
        if (googleApiClient != null) {
            googleApiClient.disconnect()
        }
    }

    private fun connectGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(converterView.getContext())
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build()
        }
        googleApiClient.connect()
    }

    override fun onConnected(p0: Bundle?) {
        convertUseCase(converterView.getContext()).convertMovie(link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it: Convertion? ->
                    converterView.showSuccessMessgae(it!!.title)
                    saveFileToDrive(it!!.title)
                }, {
                    converterView.showErrorMessgae()
                })
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun saveFileToDrive(title: String) {
        Drive.DriveApi.newDriveContents(googleApiClient)
                .setResultCallback { it: DriveApi.DriveContentsResult ->
                    if (!it.status.isSuccess) {
                        Log.i(TAG, "Failed to create new contents.");
                        converterView.showErrorMessgae()
                        return@setResultCallback
                    }

                    val file = File(title + ".mp3")

                    // Read the contents and open its output stream for writing, then
                    // write a short message.
                    val originalContents = it.driveContents
                    val os = originalContents.outputStream

                    try {
                        val dbInputStream = FileInputStream(file)

                        val buffer = ByteArray(1024)
                        var length: Int = dbInputStream.read(buffer)
                        var counter = 0
                        while (length > 0) {
                            ++counter
                            os.write(buffer, 0, length)
                            length = dbInputStream.read(buffer)
                        }

                        dbInputStream.close()
                        os.flush()
                        os.close()

                    } catch (e: IOException) {
                        Log.d(javaClass.simpleName, e.localizedMessage)
                        converterView.showErrorMessgae()
                    }


                    // Create the metadata for the new file including title and MIME
                    // type.
                    val originalMetadata = MetadataChangeSet.Builder()
                            .setTitle(file.getName())
                            .setMimeType("application/x-sqlite3").build()

                    // Create the file in the root folder, again calling await() to
                    // block until the request finishes.
                    val rootFolder = Drive.DriveApi.getRootFolder(getGoogleApiClient())
                    val fileResult = rootFolder.createFile(
                            getGoogleApiClient(), originalMetadata, originalContents).await()

                    if (!fileResult.status.isSuccess) {
                        // We failed, stop the task and return.
                        return null
                    }

                    // Finally, fetch the metadata for the newly created file, again
                    // calling await to block until the request finishes.
                    val metadataResult = fileResult.driveFile
                            .getMetadata(getGoogleApiClient())
                            .await()

                    if (!metadataResult.status.isSuccess) {
                        // We failed, stop the task and return.
                        return null
                    }
                }
    }

    private fun isValidLink(text: String?): Boolean {
        if (isEmpty(text)) {
            return false
        }
        if (!isValidUrl(text)) {
            return false
        }
        return true
    }
}
