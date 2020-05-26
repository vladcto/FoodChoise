package com.example.foodchoise.main_fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoise.R;
import com.example.foodchoise.entity_classes.AdapterBuilder;
import com.example.foodchoise.helperFirebase.database.FirestoreHelper;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.ref.WeakReference;
import java.util.List;

public class FavoritesFragment extends Fragment {
    private MyTask task;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_favorites,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        task = new MyTask(recyclerView,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private static class MyTask extends AsyncTask<Void, Void, List<DocumentReference>> {

        private WeakReference<RecyclerView> recyclerViewWeakReference;
        private WeakReference<Activity> activityWeakReference;

        // only retain a weak reference to the activity
        MyTask(RecyclerView recyclerView,Activity activity) {
            recyclerViewWeakReference = new WeakReference<>(recyclerView);
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected List<DocumentReference> doInBackground(Void... params) {
            FirestoreHelper firestoreHelper = FirestoreHelper.getInstance();
            return firestoreHelper.getFavoritesRecipesRefernce();
        }

        @Override
        protected void onPostExecute(List<DocumentReference> result) {
            try{
                Query query = FirebaseFirestore.getInstance()
                        .collection(FirestoreHelper.COLLECTION_RECIPES)
                        .whereIn(FieldPath.documentId(),result);
                recyclerViewWeakReference.get().setAdapter(
                        AdapterBuilder.getAdapter(activityWeakReference.get(),query)
                );
            }catch (NullPointerException ignored){}
            activityWeakReference.clear();
            recyclerViewWeakReference.clear();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            activityWeakReference.clear();
            recyclerViewWeakReference.clear();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        task.cancel(true);
    }
}
