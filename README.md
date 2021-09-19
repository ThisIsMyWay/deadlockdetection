# Deadlock detection demo
I created this small project, to check how easy is, to find 
deadlock based on the thread dump. 

The project itself contains a simple app, which generates deadlock.

## Ways of finding deadlock 

Here I will describe the ways in which, I've analyzed running app to find the deadlock.


### JStack
```
//prints all running JVMs processes to find PID of the running application
jps -v
//prints stack traces of Java threads of the running application
jstack -l <PID of application> 
```
Part of the logs, where the deadlock can be spotted 
```
"Thread-0" #23 prio=5 os_prio=0 cpu=0.00ms elapsed=561.60s tid=0x000002197715f180 nid=0x3f94 waiting on condition  [0x000000b7ee7fe000]
java.lang.Thread.State: WAITING (parking)
at jdk.internal.misc.Unsafe.park(java.base@16.0.1/Native Method)
- parking to wait for  <0x000000062958c200> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
at java.util.concurrent.locks.LockSupport.park(java.base@16.0.1/LockSupport.java:211)
at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@16.0.1/AbstractQueuedSynchronizer.java:714)
at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@16.0.1/AbstractQueuedSynchronizer.java:937)
at java.util.concurrent.locks.ReentrantLock$Sync.lock(java.base@16.0.1/ReentrantLock.java:153)
at java.util.concurrent.locks.ReentrantLock.lock(java.base@16.0.1/ReentrantLock.java:322)
at com.thisismyway.deadlockdetection.FirstThread.run(FirstThread.java:28)
at java.lang.Thread.run(java.base@16.0.1/Thread.java:831)

Locked ownable synchronizers:
- <0x000000062958c1d0> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)

"Thread-1" #24 prio=5 os_prio=0 cpu=0.00ms elapsed=561.60s tid=0x000002197715fab0 nid=0x1bf4 waiting on condition  [0x000000b7ee8fe000]
java.lang.Thread.State: WAITING (parking)
at jdk.internal.misc.Unsafe.park(java.base@16.0.1/Native Method)
- parking to wait for  <0x000000062958c1d0> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
at java.util.concurrent.locks.LockSupport.park(java.base@16.0.1/LockSupport.java:211)
at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@16.0.1/AbstractQueuedSynchronizer.java:714)
at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@16.0.1/AbstractQueuedSynchronizer.java:937)
at java.util.concurrent.locks.ReentrantLock$Sync.lock(java.base@16.0.1/ReentrantLock.java:153)
at java.util.concurrent.locks.ReentrantLock.lock(java.base@16.0.1/ReentrantLock.java:322)
at com.thisismyway.deadlockdetection.SecondThread.run(SecondThread.java:29)
at java.lang.Thread.run(java.base@16.0.1/Thread.java:831)

Locked ownable synchronizers:
- <0x000000062958c200> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
```
and explicit message, which informs that the deadlock has been spotted
```
Found one Java-level deadlock:
=============================
"Thread-0":
  waiting for ownable synchronizer 0x000000062958c200, (a java.util.concurrent.locks.ReentrantLock$NonfairSync),
  which is held by "Thread-1"

"Thread-1":
  waiting for ownable synchronizer 0x000000062958c1d0, (a java.util.concurrent.locks.ReentrantLock$NonfairSync),
  which is held by "Thread-0"
```

The same can be found in the tread dump, which has been done by JMC or VisualVM.
