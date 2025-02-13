# Java Multithreading Projects

This repository contains several Java projects that demonstrate key concepts in multithreading. Below is a comprehensive explanation of each project and the key concepts they cover.

## Table of Contents

1. [Project ex00: Basic Threading](#project-ex00-basic-threading)
2. [Project ex01: Thread Synchronization](#project-ex01-thread-synchronization)
3. [Project ex02: Thread Coordination](#project-ex02-thread-coordination)
4. [Project ex03: Concurrent File Download](#project-ex03-concurrent-file-download)

## Project ex00: Basic Threading

### Key Concepts

- **Thread Creation**: Creating and starting threads in Java.
- **Runnable Interface**: Implementing the `Runnable` interface to define the task for the thread.

### Explanation

In this project, we create two threads (`Egg` and `Hen`) that print their names a specified number of times. The main thread also prints "Human" the same number of times.

### Code Snippet

```java
Thread eggThead = new Thread(new Main("Egg", time));
Thread henThead = new Thread(new Main("Hen", time));

eggThead.start();
henThead.start();
```

### Summary

This project demonstrates the basics of creating and starting threads in Java using the `Runnable` interface.

## Project ex01: Thread Synchronization

### Key Concepts

- **Synchronization**: Using synchronized methods to prevent thread interference and memory consistency errors.
- **Inter-thread Communication**: Using `wait()` and `notify()` for communication between threads.

### Explanation

In this project, two threads (`Egg` and `Hen`) print their names in a synchronized manner using a `Monitor` class. The `Monitor` class ensures that the threads print their names alternately.

### Code Snippet

```java
public synchronized void printEgg() throws InterruptedException {
    while (counter == 2) wait();
    System.out.println("Egg");
    counter = 2;
    notify();
}
```

### Summary

This project demonstrates how to use synchronization and inter-thread communication to coordinate the execution of multiple threads.

## Project ex02: Thread Coordination

### Key Concepts

- **Thread Coordination**: Dividing a task among multiple threads and coordinating their execution.
- **Thread Join**: Using `join()` to wait for a thread to finish its execution.

### Explanation

In this project, we divide an array processing task among multiple threads. Each thread processes a chunk of the array and prints the sum of its chunk.

### Code Snippet

```java
Thread workingThread = new Thread(new ComputeThread(i + 1, start, end));
workingThread.start();
workingThread.join();
```

### Summary

This project demonstrates how to divide a task among multiple threads and coordinate their execution using the `join()` method.

## Project ex03: Concurrent File Download

### Key Concepts

- **Concurrent File Download**: Using multiple threads to download files concurrently.
- **Blocking Queue**: Using `LinkedBlockingQueue` to manage a queue of download tasks.

### Explanation

In this project, multiple threads download files concurrently from a list of URLs. Each thread takes a URL from the queue and downloads the file.

### Code Snippet

```java
Thread worker = new Thread(new WorkerThread(links, i + 1));
worker.start();
```

### Summary

This project demonstrates how to use multiple threads to perform concurrent file downloads, improving the efficiency of the download process.

## Key Takeaways

- **Multithreading**: Understanding the basics of creating and managing threads in Java.
- **Synchronization**: Learning how to synchronize threads to prevent interference and ensure consistency.
- **Coordination**: Dividing tasks among multiple threads and coordinating their execution.
- **Concurrency**: Using multiple threads to perform tasks concurrently, improving efficiency.

These projects provide a solid foundation in multithreading concepts and techniques in Java, which are essential for building efficient and responsive applications.
