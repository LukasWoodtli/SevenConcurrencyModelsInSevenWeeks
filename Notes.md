Notes taken from
[Seven Concurrency Models in Seven Weeks When Threads Unravel by Paul Butcher](https://pragprog.com/book/pb7con/seven-concurrency-models-in-seven-weeks)

[TOC]

# Chapter 1 Introduction

## Concurrent or Parallel?

### Related but Different

*"A concurrent program has multiple logical threads of control. These threads may or may not run in parallel."*

*"A parallel program potentially runs more quickly than a sequential program by executing different parts of the computation simultaneously (in parallel). It may or may not have more than one logical thread of control."*

*"[...] concurrency is an aspect of the problem domain — your program needs to handle multiple simultaneous (or near-simultaneous) events."*

*"Parallelism [...] is an aspect of the solution domain — you want to make your program faster by processing different portions of the problem in parallel."*

*"Concurrency is about dealing with lots of things at once. Parallelism is about doing lots of things at once."*


### Beyond Sequencial Programming

*"[...] traditional threads and locks don't provide any direct support for parallelism."*

*"[...] concurrent programs are often nondetermistic - they will give different results depending on the precise timing of events."*

*"Parallelism [...] doesn't necessarily imply nondetermism"*


### Data Parallelism

*"Data-parallel (sometimes called SIMD [...]) architectures are capable of performing the same operations on a large quantity of data in parallel."*

# Chapter 2 Threads and Locks

## The Simplest Thing That Could Possibly Work

*"[Threads] also provide almost no help to the poor programmer, making programs very difficult to get right in the first place and even more difficult to maintain."*

### they also provide almost no help to the poor programmer, making programs very difficult to get right in the first place and even more difficult to maintain.


## Day 1: Mutual Exclusion and Memory Models

*"[There is something very] basic you need to worry about when dealing with shared memory - the Memory Model."*

### Creating a Thread

*"Threads communicate with each other via shared memory."*

### Mysterious Memory

- *"The compiler is allowed to statically optimize your code by reordering things."*
- *"The JVM is allowed to dynamically optimize your code by reordering things."*
- *"The hardware you’re running on is allowed to optimize performance by reordering things."*

*"Sometimes effects don’t become visible to other threads at all."*

### Memory Visibility

*"The Java memory model defines when changes to memory made by one thread become visible to another thread.""*

### Multiple Locks

*"Deadlock is a danger whenever a thread tries to hold more than one lock. Happily, there is a simple rule that guarantees you will never deadlock - always acquire locks in a fixed, global order."*


### The Perils of Alien Methods

*"avoid calling alien methods while holding a lock"*


### Day 1 Wrap Up

*"[...] three primary perils of threads and locks - race conditions, deadlock and memory visibility [...]
rules that help us avoiding them:"*

- *"Synchronize all access to shared variables."*
- *Both the writing and the reading threads need to use synchronization."*
- *Acquire multiple locks in a fixed, global order."*
- *Don't call alien methods while holding a lock."*
- *Hold locks for the shortest possible amount of time."*


### Additional Notes

#### Race Condition

A race condition is the behavior of a software system where the output is dependent on the sequence or timing of other uncontrollable events. It becomes a bug when events do not happen in the order the programmer intended.

#### Memory Visibility

The memory model defines when changes to memory made by one thread become visible to another thread.

#### Deadlocks & Livelocks

Deadlock is a danger whenever a thread tries to hold more than one lock. 
Happily, there is a simple rule that guarantees you will never deadlock - always acquire locks in a fixed, global order.

<!--

-----------------------------






Atomic vs. volatile
===================
Atomic operations/variables
---------------------------
An atomic operation is an operation that happens in one single step. So the operation either has happend or hasn't.

A variable is considered to be atomic if all operations on it happen in atomic manner.

Volatile
--------
A volatile variable is guaranteed to be read from its original location (in memory) every time it is used. This is donne to prevent any optimisation by the compiler or runtime system.
A volatile variable is usually not atomic by default.


-->
