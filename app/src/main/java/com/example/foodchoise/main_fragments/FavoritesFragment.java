package com.example.foodchoise.main_fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.AdapterBuilder;
import com.example.foodchoise.entity_classes.BriefRecipeCardAdapter;
import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import timber.log.Timber;

public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<String>> {
    BriefRecipeCardAdapter adapter;
    RecyclerView recyclerView;
    androidx.loader.content.Loader<List<String>> mLoader;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_favorites,container,false);
        Bundle bundle = new Bundle();
        bundle.putString("23", "test");
        // Инициализируем загрузчик с идентификатором
        // Если загрузчик не существует, то он будет создан,
        // иначе он будет перезапущен.
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLoader = getActivity().getSupportLoaderManager().initLoader(1, bundle, this);
        mLoader.onContentChanged();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            adapter.stopListening();
        } catch (NullPointerException ignored) {
        }
        mLoader.stopLoading();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @NonNull
    @Override
    public androidx.loader.content.Loader<List<String>> onCreateLoader(int id, @Nullable Bundle args) {
        return new Loader(getContext());
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<String>> loader, List<String> data) {
        Query query = FirebaseFirestore.getInstance().collection(FirestoreHelper.COLLECTION_RECIPES)
                .whereIn(FieldPath.documentId(), data);
        Timber.e("id: %s", data.toString());
        adapter = AdapterBuilder.getBriefRecipeAdapter(getActivity(), query);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<List<String>> loader) {

    }

    static class Loader extends AsyncTaskLoader<List<String>> {

        public Loader(@NonNull Context context) {
            super(context);
        }

        @Nullable
        @Override
        public List<String> loadInBackground() {
            AsyncTask task;
            task = FirestoreHelper.getInstance().getFavoritesRecipesRefernce();
            List<String> ids = new ArrayList<>();
            //TODO: Реализовать патерн слушатель , что бы экран не зависал.
            task.execute();
            try {
                ids = (List<String>) task.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (ids.isEmpty()) {
                Toast.makeText(getContext(), R.string.not_have_favorite, Toast.LENGTH_LONG).show();
            }
            return ids;
        }

        @Override
        public void deliverResult(@Nullable List<String> data) {
            super.deliverResult(data);
        }
    }
}
