import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClusterManager {
    private Map<String, Cluster> clusters = new HashMap<>();

    private ClusterManager() {
    }

    private static class Helper {
        private final static ClusterManager instance = new ClusterManager();
    }

    public static ClusterManager getInstance() {
        return Helper.instance;
    }

    public boolean addCluster(Cluster cluster) {
        clusters.put(UUID.randomUUID().toString(), cluster);
        return true;
    }

    public String findAnsUseCluster(int ram, int cpu) {
        for (Cluster cluster : clusters.values()) {
            if(cluster.useResources(ram, cpu)){
                return cluster.toString();
            }
        }
        return null;
    }

    public boolean claimResource(String clusterId , int ram, int cpu) {
        Cluster cluster = clusters.get(clusterId);
        if(cluster != null){
            cluster.reAllocateResources(ram, cpu);
            return true;
        }
        return false ;
    }

}
