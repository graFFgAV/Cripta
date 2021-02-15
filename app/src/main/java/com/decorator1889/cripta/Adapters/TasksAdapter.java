package com.decorator1889.cripta.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.decorator1889.cripta.Models.Task;
import com.decorator1889.cripta.R;
import com.decorator1889.cripta.Room.DatabaseClient;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private final Context mCtx;
    private final List<Task> taskList;

    public TasksAdapter(Context mCtx, List<Task> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @NotNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks, parent, false);
        return new TasksViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Task t = taskList.get(position);
        holder.textViewTask.setText(t.getTask());
        holder.textViewDesc.setText(t.getDesc());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteTask(t);
                taskList.remove(position);
                notifyItemRemoved(position);

                Context context = v.getContext();
                Toast.makeText(v.getContext(), "объет удален", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteTask(final Task task) {
        @SuppressLint("StaticFieldLeak")
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .taskDao()
                        .delete(task);
                return null;
            }
        }
        DeleteTask dt = new DeleteTask();
        dt.execute();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  textViewTask, textViewDesc;
        Button button;

        public TasksViewHolder(View itemView) {
            super(itemView);

            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            button = (Button) itemView.findViewById(R.id.button);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

            Context context = v.getContext();
            Toast.makeText(v.getContext(), "ПОШЕЛ ТЫ НАХЕР КОЗЕЛ", Toast.LENGTH_SHORT).show();

        }
    }

}
