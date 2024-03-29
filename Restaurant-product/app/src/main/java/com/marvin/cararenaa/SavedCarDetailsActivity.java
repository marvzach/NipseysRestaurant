package com.marvin.cararenaa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.marvin.cararenaa.adapters.FirebaseCararenaListAdapter;
import com.marvin.cararenaa.models.Carzarena;
import com.marvin.cararenaa.util.OnStartDragListener;
import com.marvin.cararenaa.util.SimpleItemTouchHelperCallback;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SavedCarDetailsActivity extends AppCompatActivity implements OnStartDragListener {
    private DatabaseReference mReference;
    private FirebaseCararenaListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savedcars);
        ButterKnife.bind(this);


        setUpFirebaseAdapter();
    }
    private void setUpFirebaseAdapter(){



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        Query query = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_FOUND_CARS).child(uid).orderByChild(Constants.FIREBASE_QUERY_INDEX);
        FirebaseRecyclerOptions<Carzarena> options =
                new FirebaseRecyclerOptions.Builder<Carzarena>()
                        .setQuery(query, Carzarena.class)
                        .build();

        mFirebaseAdapter = new FirebaseCararenaListAdapter(options, query,this,this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }



    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }

    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }


}

