package com.project.todo.ui;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.project.todo.MainActivity;
import com.project.todo.R;
import com.project.todo.data.ToDoDao;
import com.project.todo.data.ToDoRoomDatabase;
import com.project.todo.model.ToDo;
import com.project.todo.model.ToDoViewModel;
import com.project.todo.util.ToDoRepository;

import java.util.List;
import java.util.zip.Inflater;

/**
 * This is a list adapter class which will be used to inflate the data to the main activity
 * i.e to insert a child view to another view
 * The data is taken and it is used here to cache it and notify change when the data is changed
 * and this will be used as a adapter so that the Recycler view can be adapter to the activity
 */
public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoViewHolder> {

    private onEditClickListener onEditClickListener;
    private final LayoutInflater todoInflater;
    private List<ToDo> toDoList;//Caching the list

    public ToDoListAdapter(Context context) {
        this.todoInflater = LayoutInflater.from(context);
    }

    public interface onEditClickListener {
        void onItemClick(ToDo toDo);
    }

    public void setOnItemClickListener(onEditClickListener onItemClickListener){
        onEditClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ToDoListAdapter.ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = todoInflater.inflate(R.layout.recyclerview_item, viewGroup, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListAdapter.ToDoViewHolder holder, int position) {
        if (toDoList != null) {
            ToDo current = toDoList.get(position);
            holder.toDoTextView.setText(current.getToDo());
        } else {
            holder.toDoTextView.setText(R.string.no_toDo);
        }
    }

    public void setToDO(List<ToDo> toDos) {
        toDoList = toDos;
        notifyDataSetChanged();
    }

    public ToDo getToDo(int position){
        return toDoList.get(position);
    }

    @Override
    public int getItemCount() {
        if (toDoList != null)
            return toDoList.size();

        return 0;
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder {
        public TextView toDoTextView;
        public ImageView deleteView;
        public Application application;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            toDoTextView = itemView.findViewById(R.id.textView);
            deleteView = itemView.findViewById(R.id.deleteImg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(onEditClickListener!=null && position!=RecyclerView.NO_POSITION) {
                        onEditClickListener.onItemClick(toDoList.get(position));
                    }
                }
            });
        }
    }
}
