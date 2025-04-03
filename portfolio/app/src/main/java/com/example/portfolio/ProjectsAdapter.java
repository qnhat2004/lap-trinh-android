package com.example.portfolio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {

    private Project[] projects;

    public ProjectsAdapter(Project[] projects) {
        this.projects = projects;
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {
        ImageView imgViewPrjIcon;
        TextView txtViewPrjName;
        TextView txtViewPrjDescription;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewPrjIcon = itemView.findViewById(R.id.img_view_project_icon);
            txtViewPrjName = itemView.findViewById(R.id.textView_prj_title);
            txtViewPrjDescription = itemView.findViewById(R.id.textView_prj_description);
        }

        public void bind(Project project) {
            imgViewPrjIcon.setImageResource(project.getImage());
            txtViewPrjName.setText(project.getName());
            txtViewPrjDescription.setText(project.getDescription());
        }
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjectViewHolder(view);
        // Tiêm dữ liệu vào view
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        holder.bind(projects[position]);    // Bắt vị trí của phần từ người dùng đang cuôn để truyeefn vào viewHolder
    }

    @Override
    public int getItemCount() {
        return projects.length;
    }
}
