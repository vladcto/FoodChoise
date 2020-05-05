package com.example.foodchoise.helperFirebase.storage;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;

class StorageFirebaseHelper {
    //region Singleton
    private static StorageFirebaseHelper storageFirebaseHelper;

    private StorageFirebaseHelper() {
        firebaseStorage = FirebaseStorage.getInstance();
    }

    public StorageFirebaseHelper getInstance() {
        if (storageFirebaseHelper == null) {
            storageFirebaseHelper = new StorageFirebaseHelper();
        }
        return storageFirebaseHelper;
    }
    //endregion

    //region public_const
    private static final String RECIPES_MAIN_PHOTO = "/recipes_main_photo/";
    //TODO: Сделать для UploadTask свой слушатель-константу.
    //TODO: Сделать для прогресс Listener.
    //endregion

    private static final long TWO_MEGABYTE = 1024 * 1024 * 2;
    FirebaseStorage firebaseStorage;

    public UploadTask uploadFile(String fullPathUpload, Uri fileUri) {
        StorageReference reference = firebaseStorage.getReference(fullPathUpload);
        return reference.putFile(fileUri);
    }

    public UploadTask uploadFile(String fullPathUpload, byte[] bytes) {
        StorageReference reference = firebaseStorage.getReference(fullPathUpload);
        return reference.putBytes(bytes);
    }

    public UploadTask uploadFile(String fullPathUpload, InputStream inputStream) {
        StorageReference reference = firebaseStorage.getReference(fullPathUpload);
        return reference.putStream(inputStream);
    }


    public Task<byte[]> downloadByte(String fullPathDownload) {
        StorageReference reference = firebaseStorage.getReference(fullPathDownload);
        return reference.getBytes(TWO_MEGABYTE);
    }

    public FileDownloadTask downloadFileToLocal(String fullPathDownload, String pathInLocal) {
        StorageReference reference = firebaseStorage.getReference(fullPathDownload);
        return reference.getFile(new File(pathInLocal));
    }

    public void downloadPhotoInImageView(String fullPathDownload, ImageView imageView, Context context) {
        StorageReference reference = firebaseStorage.getReference(fullPathDownload);
        Glide.with(context)
                .load(reference)
                .into(imageView);
    }


}
