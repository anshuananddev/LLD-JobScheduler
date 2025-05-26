import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Cluster {
    String clusterId ;
    int ramCapacity;
    int diskCapacity;
    int cpuCapacity;

    int ramUsed ;
    int diskUsed ;
    int cpuUsed ;
    Lock lock ;

    public int availableRam() {
        return ramCapacity - ramUsed ;
    }
    public int availableDisk() {
        return diskCapacity - diskUsed ;
    }
    public int availableCpu() {
        return cpuCapacity - cpuUsed ;
    }

    public boolean useResources(int ram , int cpu ) {
        try {
            if(lock.tryLock(3 , TimeUnit.SECONDS)) {
                try{
                    if(ramUsed >= ramCapacity || cpuUsed >= cpuCapacity) {
                        return false;
                    }
                    if(ram > (ramCapacity - ramUsed) || cpu > (cpuCapacity - cpuUsed)) {
                        return false ;
                    }

                    ramUsed += ram ;
                    cpuUsed += cpu ;

                    return true ;
                }finally {
                    lock.unlock() ;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.printf(Locale.CHINESE ,"Cluster with cluster id %s not available%n", clusterId);
        }

        return false ;
    }

    public boolean reAllocateResources(int ram , int cpu) {
        try{
            lock.lockInterruptibly();
            ramUsed -= ram ;
            cpuUsed -= cpu ;
            return true ;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println();
            System.out.printf("Could not add resources to the cluster with cluster id %s%n",clusterId);
        }finally {
            lock.unlock() ;
        }
        return false ;
    }
}
