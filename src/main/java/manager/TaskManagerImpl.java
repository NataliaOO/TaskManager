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
    private int id = 1;

    Map<Integer, Task> tasks = new HashMap<>();
    Map<Integer, SubTask> subTasks = new HashMap<>();
    Map<Integer, Epic> epics = new HashMap<>();

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
        task.setId(id++);
        task.setStatusTask(NEW);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public void update(Task task) {
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
        epic.setId(id++);
        epic.setStatusTask(NEW);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public void update(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic oldEpic = epics.get(epic.getId());
            oldEpic.setName(epic.getName());
            oldEpic.setDescription(epic.getDescription());
            epics.put(epic.getId(), oldEpic);
        }
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            epic.getSubtasks().forEach(subId -> subTasks.remove(subId));
        }
    }

    @Override
    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            for (Integer subId : epic.getSubtasks()) {
                subTasks.remove(subId);
            }
        }
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
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            subTask.setId(id++);
            subTask.setStatusTask(NEW);
            subTasks.put(subTask.getId(), subTask);
            epic.addSubtask(subTask.getId());
            updateEpicStatus(epic);
        }
        return subTask;
    }

    @Override
    public void update(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (subTasks.containsKey(subTask.getId()) && epic != null) {
            subTasks.put(subTask.getId(), subTask);
            updateEpicStatus(epic);
        }
    }

    @Override
    public void removeSubTaskById(int id) {
        SubTask subTask = subTasks.remove(id);
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(subTask.getId());
                updateEpicStatus(epic);
            }
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

            if (subtask.getStatusTask() != StatusTask.NEW) {
                allNew = false;
            }
            if (subtask.getStatusTask() != StatusTask.DONE) {
                allDone = false;
            }

            if (!allNew && !allDone) break;
        }

        if (allDone) {
            epic.setStatusTask(StatusTask.DONE);
        } else if (allNew) {
            epic.setStatusTask(StatusTask.NEW);
        } else {
            epic.setStatusTask(StatusTask.IN_PROGRESS);
        }
    }

    @Override
    public List<Integer> getEpicSubTasks(Epic epic) {
        return epic.getSubtasks();
    }
}
