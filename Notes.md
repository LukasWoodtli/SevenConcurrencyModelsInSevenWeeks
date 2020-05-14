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

*"An important point that’s easily missed is that both threads need to use synchronization. It’s not enough for just the thread making changes to do so."*

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


## Day 2: Beyond Intrinsic Locks

*"Intrinsic locks are convenient but limited."*

- *"There is no way to interrupt a thread that’s blocked as a result of trying to acquire an intrinsic lock."*
- *"There is no way to time out while trying to acquire an intrinsic lock."*
- *"There’s exactly one way to acquire an intrinsic lock: a synchronized block."*

Synchronized block:

    :::java
    synchronized ​(object) {    
      // use shared resources
    } 

*"This means that lock acquisition and release have to take place in the same method and have to be strictly nested."*

*"Note that declaring a method as `synchronized` is just syntactic sugar for surrounding the method’s body with the following:"*

    :::java
    ​synchronized ​(this) {​ 
        // method body
    }

*"ReentrantLock allows us to transcend these restrictions by providing explicit lock and unlock methods instead of using synchronized ."*

    :::java
    ​Lock ​lock = ​new ReentrantLock ​();
    lock.lock();
    try ​{
        // use shared resources​ 
    } finally {
        lock.unlock();
    }

### Hand-over-Hand Locking

*"Hand-over-hand locking is an alternative in which we lock only a small portion of the list, allowing other threads unfettered access as long as they’re not looking at the particular nodes we’ve got locked."*


### Condition Variables

*"Concurrent programming often involves waiting for something to happen. [...] This type of situation is what condition variables are designed to address."*

    :::java
    ​ReentrantLock lock = new ReentrantLock ​();
    Condition condition = lock.newCondition();
    
    lock.lock();
    try {
        while (!condition is true)
            condition.await();
        // use shared resources
    } finally {
        lock.unlock();
    }


*"A condition variable is associated with a lock, and a thread must hold that lock before being able to wait on the condition. Once it holds the lock, it checks to see if the condition that it’s interested in is already true. If it is, then it continues with whatever it wants to do and unlocks the lock. If, however, the condition is not true, it calls `await`, which **atomically** unlocks the lock and blocks on the condition variable."*

*"An operation is atomic if, from the point of view of another thread, it appears to be a single operation that has either happened or not - it never appears to be halfway through."*


*"When another thread calls `signal` or `signalAll` [on the condition variable] to indicate that the condition might now be true, `await` unblocks and automatically reacquires the lock. An important point is that when `await` returns, it only indicates that the condition might be true. This is why `await` is called within a loop - we need to go back, recheck whether the condition is true, and potentially block on `await` again if necessary."*

### Atomic Variables

*"Using an atomic variable instead of locks has a number of benefits. First, it’s not possible to forget to acquire the lock when necessary."*

*"Second, because no locks are involved, it’s impossible for an operation on an atomic variable to deadlock."*

*"atomic variables are the foundation of non-blocking, lock-free algorithms, which achieve synchronization without locks or blocking."*

> *If you think that programming with locks is tricky, then just wait until you try writing lock-free code.*

#### What About Volatile?

*"Java allows us to mark a variable as `volatile`. Doing so guarantees that reads and writes to that variable will not be reordered."*

*"valid use cases for `volatile` variables are rare. If you find yourself considering `volatile`, you should probably use one of the `java.util.concurrent.atomic` classes instead."*

In C++ `volatile` has a different meaning. It indicates that the read of a variable is not allowed to be optimized out. This is important for reading memory mapped I/O or memory mapped registers and similar use cases.

### Day 2 Wrap-Up

*"[With `ReentrantLock` and `java.util.concurrent.atomic`] threads can do the following:*

- *"Be interrupted while trying to acquire a lock"*
- *"Time out while acquiring a lock"*
- *"Acquire and release locks in any order [Danger! Dead locks!]"*
- *"Use condition variables to wait for arbitrary conditions to become true"*
- *"Avoid locks entirely by using atomic variables"*

## Day 3: On the Shoulders of Giants

#### How Large Should My Thread Pool Be? 

*"The optimum number of threads will vary according to the hardware [...], whether your threads are IO or CPU bound, what else the machine is doing at the same time, and a host of other factors. [...] a good rule of thumb is that for computation-intensive tasks, [to have] approximately the same number of threads as available cores. Larger numbers are appropriate for IO-intensive tasks. Beyond this rule of thumb, your best bet is to create a realistic load test and break out the stopwatch."*

#### Why a Blocking Queue?

*"As well as blocking queues, `java.util.concurrent` provides `ConcurrentLinkedQueue`, an unbounded, wait-free, and nonblocking queue. That sounds like a very desirable set of attributes [for the producer-consumer pattern]. The issue is that [...] if the producer runs faster than the consumer, the queue will get larger and larger."*

*"The beauty of the producer-consumer pattern is that it allows us not only to produce and consume values in parallel, but also to have multiple producers and/or multiple consumers."*

*"Unfortunately, synchronized collections don’t provide atomic read-modify-write methods, so this isn’t going to help us. If we want to use a `HashMap`, we’re going to have to synchronize access to it ourselves."*

*"Happily, we’re not beaten yet. `ConcurrentHashMap` in `java.util.concurrent` looks like exactly what we need. Not only does it provide atomic read-modify-write methods, but it’s been designed to support high levels of concurrent access (via a technique called lock striping)."*

*"Instead of put, we’re now using a combination of `putIfAbsent` and `replace`."*

*"Here’s the documentation for `putIfAbsent`: If the specified key is not already associated with a value, associate it with the given value. This is equivalent to"*

    :::java
    if (!map.containsKey(key))
        return map.put(key, value);
    else
        return ​ map.get(key);

*"except that the action is performed atomically."*

*"And for `replace`: Replaces the entry for a key only if currently mapped to a given value. This is equivalent to"*

    :::java
    if (map.containsKey(key) && map.get(key).equals(oldValue)) {
        map.put(key, newValue);
        return true;
    } else
        return false;
        
*"except that the action is performed atomically."*


### Day 3 Wrap-Up

#### What We Learned in Day 3

*"the facilities provided by `java.util.concurrent` [make] programs safer and more efficient by doing the following:"*

- *"Using thread pools instead of creating threads directly"*
- *"Creating simpler and more efficient listener-management code with `CopyOnWriteArrayList`"*
- *"Allowing producers and consumers to communicate efficiently with `ArrayBlockingQueue`"*
- *"Supporting highly concurrent access to a map with `ConcurrentHashMap`"*

### Wrap-Up

#### Weaknesses

*"Threads and locks provide no direct support for parallelism [...] they can be used to parallelize a sequential algorithm, but this has to be constructed out of concurrent primitives, which introduces the specter of nondeterminism."*

*"threads and locks support only shared-memory architectures. If you need to support distributed memory (and, by extension, either geographical distribution or resilience), you will need to look elsewhere."*

#### The Elephant in the Room

*"[...] what makes multithreaded programming difficult is not that writing it is hard, but that **testing it is hard**. It’s not the pitfalls that you can fall into; it’s the fact that you don’t necessarily know whether you’ve fallen into one of them."*

#### Maintenance

*"It’s one thing to make sure that everything’s synchronized correctly, locks are acquired in the right order, and no foreign functions are called with locks held. It’s quite another to guarantee that it will remain that way after twelve months of maintenance by ten different programmers."*

*"if you can’t reliably test for threading problems, you can’t reliably refactor multithreaded code."*

*"think very carefully about our multithreaded code. And then when we’ve done that, think about it very carefully some more."*

#### Other Languages

*"the general principles we covered in this chapter are broadly applicable. The rules about using synchronization to access shared variables; acquiring locks in a fixed, global order; and avoiding alien method calls while holding a lock are applicable to any language with threads and locks."*

*"a memory model was added to the C11 and C++ 11 standards."*

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
