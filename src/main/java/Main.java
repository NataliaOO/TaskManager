import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;

import static model.StatusTask.DONE;
import static model.StatusTask.IN_PROGRESS;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        Task task1 = new Task("Позвонить бабушке", "Узнать, как дела");
        Task task2 = new Task("Сделать зарядку", "10 минут утром");
        manager.create(task1);
        manager.create(task2);

        Epic epic1 = new Epic("Организовать переезд", "Переезд на новую квартиру");
        manager.create(epic1);

        SubTask subtask1 = new SubTask("Упаковать вещи", "Коробки, пакеты, сумки", epic1.getId());
        SubTask subtask2 = new SubTask("Заказать грузовое такси", "Позвонить в транспортную компанию", epic1.getId());
        manager.create(subtask1);
        manager.create(subtask2);

        Epic epic2 = new Epic("Подготовить день рождения", "Праздник для друга");
        manager.create(epic2);
        SubTask subtask3 = new SubTask("Купить торт", "Ванильный с ягодами", epic2.getId());
        manager.create(subtask3);

        System.out.println("=== Все Epics ===");
        System.out.println(manager.getAllEpics());
        System.out.println("=== Все Tasks ===");
        System.out.println(manager.getAllTasks());
        System.out.println("=== Все SubTasks ===");
        System.out.println(manager.getAllSubTasks());

        // Примеры просмотров:
        manager.getTaskById(task1.getId());
        manager.getEpicById(epic1.getId());
        manager.getSubTaskById(subtask1.getId());
        manager.getTaskById(task2.getId());
        manager.getEpicById(epic2.getId());
        manager.getTaskById(task1.getId());
        manager.getEpicById(epic1.getId());
        manager.getSubTaskById(subtask2.getId());
        manager.getTaskById(task2.getId());
        manager.getEpicById(epic2.getId());
        manager.getTaskById(task1.getId());
        manager.getEpicById(epic1.getId());
        System.out.println("\n=== История (ожидаем 10 элементов) ===");
        System.out.println(manager.getHistory().size());
        System.out.println(manager.getHistory());

        task1.setStatusTask(DONE);
        manager.update(task1);

        subtask1.setStatusTask(DONE);
        manager.update(subtask1);

        subtask2.setStatusTask(IN_PROGRESS);
        manager.update(subtask2);

        subtask3.setStatusTask(DONE);
        manager.update(subtask3);

        System.out.println("\n=== После изменения статусов ===");
        System.out.println("Epics:");
        System.out.println(manager.getAllEpics());
        System.out.println("Tasks:");
        System.out.println(manager.getAllTasks());
        System.out.println("SubTasks:");
        System.out.println(manager.getAllSubTasks());

        manager.removeSubTaskById(subtask2.getId());
        manager.removeEpicById(epic2.getId());
        System.out.println("\n=== После удаления задачи и эпика ===");
        System.out.println("Epics:");
        System.out.println(manager.getAllEpics());
        System.out.println("Tasks:");
        System.out.println(manager.getAllTasks());
        System.out.println("SubTasks:");
        System.out.println(manager.getAllSubTasks());
    }
}
