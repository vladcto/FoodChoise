package com.example.foodchoise.helperFirebase.database;

import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;

import timber.log.Timber;

class FirestoreHelperBasic {

    protected FirestoreHelperBasic() {
        db = FirebaseFirestore.getInstance();
    }


    FirebaseFirestore db;
    private QuerySnapshot getQuerySnapshotInCollection(String collectionName) {
        CollectionReference collectionReference = db.collection(collectionName);
        /*final QuerySnapshot[] querySnapshot = new QuerySnapshot[1];
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    querySnapshot[0] = task.getResult();
                }else {
                    if (true) throw new AssertionError();
                }
            }
        });*/
        Task<QuerySnapshot> querySnapshot = collectionReference.get();
        try {
            Tasks.await(querySnapshot);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        QuerySnapshot result = querySnapshot.getResult();
        return result;
    }

    private List<DocumentSnapshot> getDocumentsInCollection(String collectionName) {
        QuerySnapshot snapshots = getQuerySnapshotInCollection(collectionName);
        if (snapshots == null) throw new AssertionError();
        List<DocumentSnapshot> documentSnapshots = new ArrayList<>();
        for (QueryDocumentSnapshot document : snapshots) {
            documentSnapshots.add(document);
        }
        return documentSnapshots;
    }

    public List<Map<String, Object>> getMapDocumentsInCollection(String collectionName) {
        List<Map<String, Object>> documentMap = new ArrayList<>();
        List<DocumentSnapshot> documentSnapshots = getDocumentsInCollection(collectionName);
        for (DocumentSnapshot documentSnapshot : documentSnapshots) {
            //TODO: Вынести withID в отдельный метод
            Map<String,Object> map = new HashMap<>();
            map = documentSnapshot.getData();
            map.put("id",documentSnapshot.getId());
            documentMap.add(map);
        }
        return documentMap;
    }
}
