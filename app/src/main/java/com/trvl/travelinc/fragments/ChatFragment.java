package com.trvl.travelinc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trvl.travelinc.R;
import com.trvl.travelinc.activities.ChatActivity;
import com.trvl.travelinc.holders.ChatHolder;
import com.trvl.travelinc.models.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatFragment extends Fragment
{
    private FirebaseRecyclerAdapter adapter;

    public ChatFragment()
    {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_chat, container, false);

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Initialize Chat Database

        DatabaseReference chatDatabase = FirebaseDatabase.getInstance().getReference().child("Chat").child(currentUserId);
        chatDatabase.keepSynced(true); // For offline use

        // RecyclerView related

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        RecyclerView recyclerView = view.findViewById(R.id.chat_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        // Initializing adapter

        FirebaseRecyclerOptions<Chat> options = new FirebaseRecyclerOptions.Builder<Chat>().setQuery(chatDatabase.orderByChild("timestamp"), Chat.class).build();

        adapter = new FirebaseRecyclerAdapter<Chat, ChatHolder>(options)
        {
            @Override
            public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);

                return new ChatHolder(getActivity(), view, getContext());
            }

            @Override
            protected void onBindViewHolder(final ChatHolder holder, int position, final Chat model)
            {
                final String userid = getRef(position).getKey();

                holder.setHolder(userid, model.getMessage(), model.getTimestamp(), model.getSeen());
                holder.getView().setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                        chatIntent.putExtra("userid", userid);
                        startActivity(chatIntent);
                    }
                });
            }

            @Override
            public void onDataChanged()
            {
                super.onDataChanged();

                TextView text = view.findViewById(R.id.f_chat_text);

                if(adapter.getItemCount() == 0)
                {
                    text.setVisibility(View.VISIBLE);
                }
                else
                {
                    text.setVisibility(View.GONE);
                }
            }
        };

        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStop()
    {
        super.onStop();

        adapter.stopListening();
    }
}
