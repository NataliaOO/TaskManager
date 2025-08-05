package manager;

import model.Epic;
import model.StatusTask;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.StatusTask.*;

public class TaskManagerImpl implements TaskManager {
    private int nextId = 1;

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    @Override
    public Task create(Task task) {
        if (task == null) throw new IllegalArgumentException("Task не может быть null");
        task.setId(nextId++);
        task.setStatusTask(NEW);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public void update(Task task) {
        if (task == null) return;
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    @Override
    public Epic create(Epic epic) {
        if (epic == null) throw new IllegalArgumentException("Epic не может быть null");
        epic.setId(nextId++);
        epic.setStatusTask(NEW);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public void update(Epic epic) {
        if (epic == null) return;
        if (epics.containsKey(epic.getId())) {
            Epic oldEpic = epics.get(epic.getId());
            oldEpic.setName(epic.getName());
            oldEpic.setDescription(epic.getDescription());
            updateEpicStatus(oldEpic);
        }
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic == null) return;
        epic.getSubtasks().forEach(this::removeSubTaskById);
        epic.removeAllSubtasks();
    }

    @Override
    public void removeAllEpics() {
        epics.values().forEach(epic -> epic.getSubtasks().forEach(subTasks::remove));
        epics.clear();
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    @Override
    public SubTask create(SubTask subTask) {
        if (subTask == null) throw new IllegalArgumentException("SubTask не может быть null");
        Epic epic = epics.get(subTask.getEpicId());
        if (epic == null) throw new IllegalArgumentException("Epic с id " + subTask.getEpicId() + " не найден");
        subTask.setId(nextId++);
        subTask.setStatusTask(NEW);
        subTasks.put(subTask.getId(), subTask);
        epic.addSubtask(subTask.getId());
        updateEpicStatus(epic);
        return subTask;
    }

    @Override
    public void update(SubTask subTask) {
        if (subTask == null) return;
        Epic epic = epics.get(subTask.getEpicId());
        if (subTasks.containsKey(subTask.getId()) && epic != null) {
            subTasks.put(subTask.getId(), subTask);
            updateEpicStatus(epic);
        }
    }

    @Override
    public void removeSubTaskById(int id) {
        SubTask subTask = subTasks.remove(id);
        if (subTask == null) return;
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            epic.removeSubtask(subTask.getId());
            updateEpicStatus(epic);
        }

    }

    @Override
    public void removeAllSubTasks() {
        epics.values().forEach(epic -> {
            epic.removeAllSubtasks();
            updateEpicStatus(epic);
        });
        subTasks.clear();
    }

    private void updateEpicStatus(Epic epic) {
        List<Integer> subtaskIds = epic.getSubtasks();

        if (subtaskIds.isEmpty()) {
            epic.setStatusTask(StatusTask.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Integer subId : subtaskIds) {
            SubTask subtask = subTasks.get(subId);
            if (subtask == null) continue;
            if (subtask.getStatusTask() != StatusTask.NEW) allNew = false;
            if (subtask.getStatusTask() != StatusTask.DONE) allDone = false;
            if (!allNew && !allDone) break;
        }

        if (allDone) epic.setStatusTask(StatusTask.DONE);
        else if (allNew) epic.setStatusTask(StatusTask.NEW);
        else epic.setStatusTask(StatusTask.IN_PROGRESS);
    }

    @Override
    public List<SubTask> getEpicSubTasks(Epic epic) {
        List<SubTask> result = new ArrayList<>();
        if (epic == null) return result;
        for (Integer subId : epic.getSubtasks()) {
            SubTask subTask = subTasks.get(subId);
            if (subTask != null) {
                result.add(subTask);
            }
        }
        return result;
    }
}
