package com.example.foodchoise.step_classes.create_recipe;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

public class StepNameFragment extends Fragment {
    private Uri imageUri = null;
    private String textNameDishes = null;
    private String textDescrDishes = null;
    private final int CARDIMAGE_REQUEST = 1;

    public String getTextNameDishes() {
        return textNameDishes;
    }

    public String getTextDescrDishes() {
        return textDescrDishes;
    }

    public Uri getImageUri() {
        return imageUri;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_name_recipes, container, false);
        if (textNameDishes != null) {
            EditText text = view.findViewById(R.id.edit_name_dishes);
            text.setText(textNameDishes);
        }

        if (textDescrDishes != null) {
            EditText text = view.findViewById(R.id.edit_descr_dishes);
            text.setText(textDescrDishes);
        }

        if (imageUri != null) {
            ImageButton imageButton = view.findViewById(R.id.select_foto_imagebutton);
            Timber.d("Ставим в изображение URI: %s", imageUri);
            imageButton.setImageURI(imageUri);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Activity activity = getActivity();
        ImageButton imageButton = activity.findViewById(R.id.select_foto_imagebutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (requestCode == CARDIMAGE_REQUEST && resultCode == RESULT_OK) {
            //Получаем URI изображения, затем запрашиваем его кадрирование.
            Uri sourceUri = imageReturnedIntent.getData();
            assert sourceUri != null;
            Timber.i("Получено изображение из галереи.");

            File file = getMainImageFile();
            Uri destinationUri = Uri.fromFile(file);
            UCrop.of(sourceUri, destinationUri)
                    .withAspectRatio(1f, 1f)
                    .start(getActivity(),this);

        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            //Получаем URI обрезанного изображения.
            imageUri = UCrop.getOutput(imageReturnedIntent);
            Timber.d("ImageUri = %s", imageUri.toString());
            ImageButton button = (ImageButton) getActivity().findViewById(R.id.select_foto_imagebutton);
            button.setImageURI(imageUri);

        }else {
            Timber.w("Не отработан requestCode = %s , resultCode = %s",requestCode,requestCode);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Сохоранения данных для последующего востановления фрагмента или
        //для создания RecipeCard по этим данным
        Activity activity = getActivity();
        EditText text = activity.findViewById(R.id.edit_name_dishes);
        textNameDishes = text.getText().toString();
        text = activity.findViewById(R.id.edit_descr_dishes);
        textDescrDishes = text.getText().toString();
    }

    private void openCamera() {

    }

    private void openGallery() {
        Timber.v("Выбрана галерея.");
        //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        //Тип получаемых объектов - image:
        photoPickerIntent.setType("image/*");
        //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
        //TODO: Выбор типов.
        Timber.i("Запуск галереи для получения изображения");
        startActivityForResult(Intent.createChooser(photoPickerIntent, "Выбор изображения"), CARDIMAGE_REQUEST);
    }

    private File getMainImageFile() {
        File file = new File(getActivity().getFilesDir(), "main_photo");
        return file;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Не знаю, нужно ли обнулять , но лучше обнулю руками, чем получить утечку памяти.
        imageUri = null;
        textNameDishes = null;
        textDescrDishes = null;
    }
}
