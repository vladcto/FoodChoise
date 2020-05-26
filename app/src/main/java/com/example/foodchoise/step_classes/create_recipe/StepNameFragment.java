package com.example.foodchoise.step_classes.create_recipe;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodchoise.R;
import com.google.android.material.textfield.TextInputLayout;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

public class StepNameFragment extends Fragment {
    private Uri imageUri = null;
    private String textNameDishes = null;
    private String textDescrDishes = null;
    private final int CARDIMAGE_REQUEST = 1;
    private TextInputLayout inputLayoutName, inputLayoutDescription;
    private EditText textName, textDescr;

    String getTextNameDishes() {
        return textNameDishes;
    }

    String getTextDescrDishes() {
        return textDescrDishes;
    }

    Uri getImageUri() {
        return imageUri;
    }

    public boolean hasErrorFields() {
        checkNameField();
        checkDescriptionError();
        return inputLayoutName.isErrorEnabled() && inputLayoutDescription.isErrorEnabled();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_name_recipes, container, false);

        textName = view.findViewById(R.id.edit_name_dishes);
        inputLayoutName = view.findViewById(R.id.inputNameLayout);
        if (textNameDishes != null) {
            textName.setText(textNameDishes);
        }
        textName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                checkNameField();
                textDescrDishes = text;
            }
        });

        textDescr = view.findViewById(R.id.edit_descr_dishes);
        inputLayoutDescription = view.findViewById(R.id.inputDescriptionLayout);
        if (textDescrDishes != null) {
            textDescr.setText(textDescrDishes);
        }
        textDescr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                checkDescriptionError();
                textNameDishes = text;
            }
        });

        if (imageUri != null) {
            ImageButton imageButton = view.findViewById(R.id.select_foto_imagebutton);
            Timber.d("Ставим в изображение URI: %s", imageUri);
            imageButton.setImageURI(imageUri);
        }

        return view;
    }

    private void checkNameField(){
        String text = textName.getText().toString().trim();
        if (text.length() > inputLayoutName.getCounterMaxLength()) {
            showNameError(EditTextErrors.MAX_LENGTH);
        } else if (text.length() == 0) {
            showNameError(EditTextErrors.EMPTY);
        } else {
            inputLayoutName.setErrorEnabled(false);
        }
    }

    private void checkDescriptionError(){
        String text = textDescr.getText().toString().trim();
        if (text.length() > inputLayoutDescription.getCounterMaxLength()) {
            showDescrError(EditTextErrors.MAX_LENGTH);
        } else if (text.length() == 0) {
            showDescrError(EditTextErrors.EMPTY);
        } else {
            inputLayoutDescription.setErrorEnabled(false);
        }
    }

    private void showNameError(EditTextErrors editTextErrors) {
        if (editTextErrors == EditTextErrors.MAX_LENGTH) {
            inputLayoutName.setError(getResources().getText(R.string.error_max_length) + " "
                    + inputLayoutName.getCounterMaxLength());
        } else {
            inputLayoutName.setError(getResources().getText(R.string.required_input));
        }
    }

    private void showDescrError(EditTextErrors editTextErrors) {
        if (editTextErrors == EditTextErrors.MAX_LENGTH) {
            inputLayoutDescription.setError(getResources().getText(R.string.error_max_length) + " "
                    + inputLayoutDescription.getCounterMaxLength());
        } else {
            inputLayoutDescription.setError(getResources().getText(R.string.required_input));
        }
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
                    .start(getActivity(), this);

        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            //Получаем URI обрезанного изображения.
            imageUri = UCrop.getOutput(imageReturnedIntent);
            Timber.d("ImageUri = %s", imageUri.toString());
            ImageButton button = (ImageButton) getActivity().findViewById(R.id.select_foto_imagebutton);
            button.setImageURI(imageUri);

        } else {
            Timber.w("Не отработан requestCode = %s , resultCode = %s", requestCode, requestCode);
        }
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
        return new File(getActivity().getFilesDir(), "main_photo");

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
