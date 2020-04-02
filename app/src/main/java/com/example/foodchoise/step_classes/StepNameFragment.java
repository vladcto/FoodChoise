package com.example.foodchoise.step_classes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class StepNameFragment extends Fragment {
    String text_name_dishes = null;
    String text_descr_dishes = null;
    Bitmap selectedImage = null;

    //TODO: Нормальный код и название перемнной.
    final int fd = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_name_recipes_fragment,container,false);
        if(text_name_dishes!= null){
            EditText text = view.findViewById(R.id.edit_name_dishes);
            text.setText(text_name_dishes);
        }

        if(text_descr_dishes!=null){
            EditText text = view.findViewById(R.id.edit_descr_dishes);
            text.setText(text_descr_dishes);
        }

        if(selectedImage!=null){
            ImageButton imageButton = view.findViewById(R.id.select_foto_imagebutton);
            imageButton.setImageBitmap(selectedImage);
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
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                startActivityForResult(photoPickerIntent,fd);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case fd:
                if (resultCode == RESULT_OK) {
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);
                        ImageButton button = (ImageButton) getActivity().findViewById(R.id.select_foto_imagebutton);
                        button.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Activity activity = getActivity();
        EditText text = activity.findViewById(R.id.edit_name_dishes);
        text_name_dishes = text.getText().toString();
        text = activity.findViewById(R.id.edit_descr_dishes);
        text_descr_dishes= text.getText().toString();
    }
}
