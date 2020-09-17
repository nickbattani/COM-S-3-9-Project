package com.example.anti_social.view_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anti_social.R;

import java.util.ArrayList;


public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> comments;
    private Context commentListContext;

    public CommentRecyclerViewAdapter(ArrayList<String> comments, Context commentListContext){
        this.comments = comments;
        this.commentListContext = commentListContext;
    }

    /**
     * @param parent This is the target container for all the ViewHolder items listed in onBindViewHolder
     * @param viewType The type of view we are dealing with
     * @return Returns the ViewHolder so that it can be rendered.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_commentlistitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     * This function is used to generate each individual viewholder in our RecyclerView.  A ViewHolder can be loosely defined as
     *     each individual item in the RecyclerView.
     * @param holder This would be the ViewHolder object in which we are adding properties too. The ViewHolder object's specific layout can be found in the layout layout_listitem.xml
     *         in the layouts folder
     * @param position The position of this ViewHolder in the RecyclerView, e.g. if 3, than this is the third item down.
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.commentTV.setText(comments.get(position));
    }

    /**
     * This function returns the number of ViewHolders in this RecyclerView
     * @return The number of ViewHolders in this RecyclerView
     */
    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView commentTV;
        RelativeLayout commentItemWrapper;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentTV = itemView.findViewById(R.id.commentTV);
            commentItemWrapper = itemView.findViewById(R.id.commentListItem);
        }
    }
}
