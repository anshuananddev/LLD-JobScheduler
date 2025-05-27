# LLD-JobScheduler

A Low-Level Design implementation of a Job Scheduler system that handles concurrent job execution across multiple clusters with resource management.

## Overview

This project implements a job scheduling system that:
- Manages jobs with different resource requirements and priorities
- Allocates resources from available clusters
- Handles concurrent job execution
- Ensures thread-safe resource allocation and deallocation

## Components

### Job
Represents a task that needs to be executed with specific resource requirements:
- `jobId`: Unique identifier for the job
- `requiredRAM`: Amount of RAM required by the job
- `requiredCPU`: Amount of CPU required by the job
- `priority`: Priority level of the job (lower number means higher priority)

### Cluster
Represents a resource provider with specific capacities:
- `clusterId`: Unique identifier for the cluster
- `ramCapacity`: Total available RAM
- `cpuCapacity`: Total available CPU
- `diskCapacity`: Total available disk space
- Thread-safe resource allocation using locks

### ClusterManager
Singleton class that manages all clusters:
- Maintains a registry of available clusters
- Finds suitable clusters for jobs based on resource requirements
- Handles resource allocation and deallocation

### JobScheduler
Schedules and executes jobs:
- Maintains a priority queue of jobs
- Uses a thread pool to process jobs concurrently
- Allocates resources from clusters via the ClusterManager
- Handles job execution and resource cleanup

## How It Works

1. Jobs are added to the JobScheduler with specific resource requirements and priorities
2. The JobScheduler maintains a priority queue of jobs
3. Worker threads take jobs from the queue based on priority
4. For each job, the system finds a cluster with sufficient resources
5. If a suitable cluster is found, resources are allocated and the job is executed
6. After execution, resources are released back to the cluster
7. If no suitable cluster is found, the job is put back in the queue

## Concurrency Handling

The system handles concurrency through:
- Thread-safe data structures (PriorityBlockingQueue)
- Explicit locking mechanisms for resource allocation
- Timeout-based lock acquisition to prevent deadlocks
- Proper resource cleanup in finally blocks

## Usage Example

```java
// Create a job
Job job = new Job();
job.jobId = UUID.randomUUID().toString();
job.requiredCPU = 2;
job.requiredRAM = 3;
job.priority = 2;

// Create a cluster
Cluster cluster = new Cluster();
cluster.clusterId = UUID.randomUUID().toString();
cluster.cpuCapacity = 10;
cluster.ramCapacity = 20;
cluster.lock = new ReentrantLock();

// Add cluster to the manager
ClusterManager clusterManager = ClusterManager.getInstance();
clusterManager.addCluster(cluster);

// Add job to the scheduler
JobScheduler jobScheduler = new JobScheduler();
jobScheduler.addJob(job);

// Start job scheduler
jobScheduler.init();
```
