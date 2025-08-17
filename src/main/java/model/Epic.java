package model;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(callSuper = true)
public class Epic extends Task {
    private final List<Integer> subtasks = new ArrayList<>();

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
}
