package com.example.foodchoise.helperFirebase.storage;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;

public class StorageFirebaseHelper {
    //region Singleton
    private static StorageFirebaseHelper storageFirebaseHelper;

    private StorageFirebaseHelper() {
        firebaseStorage = FirebaseStorage.getInstance();
    }

    public static StorageFirebaseHelper getInstance() {
        if (storageFirebaseHelper == null) {
            storageFirebaseHelper = new StorageFirebaseHelper();
        }
        return storageFirebaseHelper;
    }
    //endregion

    //region public_const
    public static final String RECIPES_MAIN_PHOTO = "recipes_main_photo/";
    public static final String TEST = "test";
    //TODO: Сделать для UploadTask свой слушатель-константу.
    //TODO: Сделать для прогресс Listener.
    //endregion

    private static final long TWO_MEGABYTE = 1024 * 1024 * 2;
    FirebaseStorage firebaseStorage;

    public UploadTask uploadFile(String fullPathUpload, Uri fileUri) {
        StorageReference reference = firebaseStorage.getReference().child(fullPathUpload);
        return reference.putFile(fileUri);
    }

    public UploadTask uploadFile(String fullPathUpload, byte[] bytes) {
        StorageReference reference = firebaseStorage.getReference().child(fullPathUpload);
        return reference.putBytes(bytes);
    }

    public UploadTask uploadFile(String fullPathUpload, InputStream inputStream) {
        StorageReference reference = firebaseStorage.getReference().child(fullPathUpload);
        return reference.putStream(inputStream);
    }


    public Task<byte[]> downloadByte(String fullPathDownload) {
        StorageReference reference = firebaseStorage.getReference().child(fullPathDownload);
        return reference.getBytes(TWO_MEGABYTE);
    }

    public FileDownloadTask downloadFileToLocal(String fullPathDownload, String pathInLocal) {
        StorageReference reference = firebaseStorage.getReference().child(fullPathDownload);
        return reference.getFile(new File(pathInLocal));
    }

    public void downloadPhotoInImageView(String fullPathDownload, final ImageView imageView, final Activity activity) {
        StorageReference reference = firebaseStorage.getReference().child(fullPathDownload);

        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                // Pass it to Picasso to download, show in ImageView and caching
                Picasso.get().load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }


}
