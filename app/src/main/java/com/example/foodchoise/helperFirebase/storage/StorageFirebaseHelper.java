package com.example.foodchoise.helperFirebase.storage;

import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.foodchoise.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
        //TODO: Сделать ограничение на размер файла , что бы хаком с root правами нельзя было загрузить огрмоный файл.
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

    public void downloadPhotoInImageView(String fullPathDownload, final ImageView imageView) {
        StorageReference reference = firebaseStorage.getReference().child(fullPathDownload);

        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(imageView).load(uri)
                        .centerCrop()
                        .override(300, 300)
                        .placeholder(R.drawable.themes_icon)
                        .error(R.drawable.ic_person_black_24dp)
                        .into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }


}
