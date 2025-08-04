package model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Epic extends Task {
    private List<Integer> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtask(int id) {
        subtasks.add(id);
    }

    public void removeSubtask(int id) {
        subtasks.remove(Integer.valueOf(id));
    }

    public void removeAllSubtasks() {
        subtasks.clear();
    }

    @Override
    public String toString() {
        return "\nEpic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatusTask() +
                ", subtasks=" + subtasks +
                '}';
    }
}
