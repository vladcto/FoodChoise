package com.example.foodchoise.step_classes.create_recipe;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.R;

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
            Timber.d("Ставим в изображение URI: %s",imageUri);
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
                //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                Timber.i("Запуск галереи для получения изображения");
                startActivityForResult(Intent.createChooser(photoPickerIntent, "Выбор изображения"), CARDIMAGE_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            //Запрос изображения.
            case CARDIMAGE_REQUEST:
                if (resultCode == RESULT_OK) {
                    //Получаем URI изображения, преобразуем его в Bitmap
                    //объект и отображаем в элементе ImageView нашего интерфейса:
                    imageUri = imageReturnedIntent.getData();
                    Timber.i("Получено изображение");
                    Timber.d("URI Изображения: %s",imageUri);
                    ImageButton button = (ImageButton) getActivity().findViewById(R.id.select_foto_imagebutton);
                    button.setImageURI(imageUri);
                }
                break;
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


}
