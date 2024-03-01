/* Implement this class. */

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MyDispatcher extends Dispatcher {

    public AtomicInteger host = new AtomicInteger();

   public MyDispatcher(SchedulingAlgorithm algorithm, List<Host> hosts) {
        super(algorithm, hosts);
    }
    // metoda prin care adaugam un task in functie de algoritm
    @Override
    public synchronized void addTask(Task task) {
        // alegem nodul utilizand un index incrementat
        if (this.algorithm == SchedulingAlgorithm.ROUND_ROBIN) {
            hosts.get(host.getAndIncrement() % hosts.size()).addTask(task);
            
        } 
    
        // alegem nodul cu coada de task-uri cea mai mica
        if (this.algorithm == SchedulingAlgorithm.SHORTEST_QUEUE) {
            Host selectedHost = hosts.get(0);
            long IDmin = selectedHost.getId();

            for (Host host : hosts) {
                if (host.getQueueSize() < selectedHost.getQueueSize()) {
                    selectedHost = host;
                    // in caz de egalitate, alegem ID-ul cel mai mic
                    } else if (host.getQueueSize() == selectedHost.getQueueSize()) {
                        if (IDmin < host.getId()){
                            IDmin = host.getId();
                            selectedHost = host;
                    }
                }
            }
            selectedHost.addTask(task);
        } 
        
        if (this.algorithm == SchedulingAlgorithm.SIZE_INTERVAL_TASK_ASSIGNMENT) {
            // adaugam task-ul in functie de tipul sau
            if (task.getType() == TaskType.SHORT) {
                this.hosts.get(0).addTask(task);
            } else if (task.getType() == TaskType.MEDIUM) {
                this.hosts.get(1).addTask(task);
            } else if (task.getType() == TaskType.LONG) {
                this.hosts.get(2).addTask(task);
            }
        }
        
         if (this.algorithm == SchedulingAlgorithm.LEAST_WORK_LEFT) {
            // alegem nodul cu cel mai mic timp ramas
            Host selectedHost = hosts.get(0);
            long IDmin = selectedHost.getId();

            for (Host host : hosts) {
                if (host.getWorkLeft() < selectedHost.getWorkLeft()) {
                    selectedHost = host;
                    } else if (host.getWorkLeft() == selectedHost.getWorkLeft()) {
                        // in caz de egalitate, alegem nodul cu ID-ul mai mic
                        if (IDmin < host.getId()){
                            IDmin = host.getId();
                            selectedHost = host;
                    }
                }
            }
            selectedHost.addTask(task);
        } 
    }
}