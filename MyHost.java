import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class MyHost extends Host {

    private volatile boolean ok = true;
    private volatile Task runningTask;
    // coada de prioritati pentru stocarea task-urilor
    private BlockingQueue<Task> tasks = new PriorityBlockingQueue<>(100,
            (t1, t2) -> {
                if (t1.getPriority() == t2.getPriority()) {
                    return t1.getStart() - t2.getStart();
                }
                return t2.getPriority() - t1.getPriority();
            });

    @Override
    public void run() {
        while (ok) {
            try {
                if (!tasks.isEmpty()) {
                    Task task = tasks.take();
                    if (task != null) {
                        runningTask = task;
                        // durata de executie a task-ului
                        Thread.sleep(task.getDuration());
                        //finalizarea task-ului
                        task.finish();                      
                        runningTask = null;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    // adaugare task in coada
    @Override
    public void addTask(Task task) {
        if (task != null) {
            // verificam daca task-ul este preemptibil
            // daca nu, il adaugam direct in coada
            if (task.isPreemptible()) {
                preemptTask(task);
            } else {
                try {
                    tasks.put(task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getQueueSize() {
        // calculam dimensiunea cozii, luand in considerare
        // existenta unui task in executie
        return tasks.size() + (runningTask != null ? 1 : 0);
    }

    @Override
    public long getWorkLeft() {
        long sum = 0;
        if (runningTask != null) {
            // calculam totalul de timp ramas, luand in considerare
            // task-urile din coada
            for (Task task : tasks) {
                sum = sum + task.getLeft();
            }
            return sum + runningTask.getLeft();
        }
        return 0;
    }

    @Override
    public void shutdown() {
        ok = false;
        // intreruoem thread-ul
        interrupt();
    }
    // metoda pentru preemtabilitatea unui task
    public void preemptTask(Task newTask) {
        synchronized (newTask) {
            // verificam daca exista un task in executie
            // si daca are prioritatea mai mica decat noul task
            if (runningTask != null && newTask.getPriority() > runningTask.getPriority()) {
                // adaugare task in coada si
                // eliberarea task ului din executie
                tasks.add(runningTask);
                runningTask = null;
            }
            // adaugarea noului task in coada
            tasks.add(newTask);
        }
    }
}