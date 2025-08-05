package manager;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getAllTasks();

    Task getTaskById(int id);

    Task create(Task task);

    void update(Task task);

    void removeTaskById(int id);

    void removeAllTasks();

    List<Epic> getAllEpics();

    Epic getEpicById(int id);

    Epic create(Epic epic);

    void update(Epic epic);

    void removeEpicById(int id);

    void removeAllEpics();

    List<SubTask> getAllSubTasks();

    SubTask getSubTaskById(int id);

    SubTask create(SubTask subTask);

    void update(SubTask subTask);

    void removeSubTaskById(int id);

    void removeAllSubTasks();

    List<SubTask> getEpicSubTasks(Epic epic);
}
