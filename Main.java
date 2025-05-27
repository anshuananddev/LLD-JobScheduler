import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Job job = new Job();
        job.jobId = UUID.randomUUID().toString();
        job.requiredCPU = 2 ;
        job.requiredRAM = 3 ;
        job.priority = 2 ;

        Job job2 = new Job();
        job2.jobId = UUID.randomUUID().toString();
        job2.requiredCPU =  7;
        job2.requiredRAM = 3 ;
        job2.priority = 1 ;

        Job job3 = new Job();
        job3.jobId = UUID.randomUUID().toString();
        job3.requiredCPU =  7;
        job3.requiredRAM = 3 ;
        job3.priority = 1 ;

        Lock lock = new ReentrantLock();
        Cluster cluster = new Cluster();
        cluster.clusterId = UUID.randomUUID().toString();
        cluster.cpuCapacity = 10 ;
        cluster.ramCapacity = 20 ;
        cluster.lock = lock ;

        ClusterManager clusterManager = ClusterManager.getInstance();
        clusterManager.addCluster(cluster);


        JobScheduler jobScheduler = new JobScheduler();
        jobScheduler.addJob(job);
        jobScheduler.addJob(job2);
        jobScheduler.addJob(job3);

        // start job scheduler
        jobScheduler.init();
    }
}
