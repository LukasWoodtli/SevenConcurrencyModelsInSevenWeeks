Notes taken from
[Seven Concurrency Models in Seven Weeks When Threads Unravel by Paul Butcher](https://pragprog.com/book/pb7con/seven-concurrency-models-in-seven-weeks)

[TOC]

# Introduction

## Concurrent or Parallel?

"A concurrent program has multiple logical threads of control. These threads may or may not run in parallel."

"A parallel program potentially runs more quickly than a sequential program by executing different parts of the computation simultaneously (in parallel). It may or may not have more than one logical thread of control."

"[...] concurrency is an aspect of the problem domain — your program needs to handle multiple simultaneous (or near-simultaneous) events."

"Parallelism [...] is an aspect of the solution domain — you want to make your program faster by processing different portions of the problem in parallel."

"Concurrency is about dealing with lots of things at once. Parallelism is about doing lots of things at once."


<!--
Beyond Sequencial Programming
-----------------------------
"[...] traditional threads and locks don't provide any direct support for parallelism."

"[...] concurrent programs are often nondetermistic."

"Parallelism [...] doesn't necessarily imply nondetermism"


The seven Models
----------------

"Bear the following questions in mind:

- Is the model applicable to solving concurrent problems, parallel problems, or both?
- Which parallel architecture or architectures can this model target?
- Does this model provide tools to help you write resilient or geographically distributed code?"


Threads and Locks
=================
From [BUT2014]_ Chapter 2


1. Race condition: " A race condition [...] is the behavior of a [...] software system where the 
   output is dependent on the sequence or timing of other uncontrollable events. It becomes a bug 
   when events do not happen in the order the programmer intended." (From [Wiki2015R]_)
2. Memory Visibility: " The [..] memory model defines when changes to memory made by one thread become 
   visible to another thread."
3. Deadlocks (& Livelocks): "Deadlock is a danger whenever a thread tries to hold more than one lock. 
   Happily, there is a simple rule that guarantees you will never deadlock - always acquire locks in a
   fixed, global order."

Grobal Ordering Rule
--------------------
"To avoid deadlock always acquire locks in a fixed, global order."
This completely avoids any deadlocks. But it's practically impossible to achive in any complex software.

Calling Alien Methods
---------------------
"[If we hold a lock an we call an alien method] that method could do anything, including acquiring another
lock. If it does, then we've acquired two locks without knowing whether we've done so inthe right order."

*"The only solution is to avoid calling alien methods while holding a lock."*

Wrap Up
-------
"[...] three primary perils of threads and locks - race conditions, deadlock and memory visibility [...]
rules that help us avoiding them:

* Synchronize all access to shared variables.
* Both the writing and the reading threads need to use synchronization.
* Acquire multiple locks in a fixed, global order.
* Don't call alien methods while holding a lock.
* Hold locks for the shortest possible amount of time."

*Double-Checked Locking is an anti-pattern!* [DLC]_
 

Beyond Intrinsic Locks
======================
tbd



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


Thread-Safe and Reentrant (Qt)
==============================
From [Qt2014]_

"A thread-safe function can be called simultaneously from multiple threads, even when the invocations use shared data, because all references to the shared data are serialized."

"A reentrant function can also be called simultaneously from multiple threads, but only if each invocation uses its own data.
Hence, a thread-safe function is always reentrant, but a reentrantfunction is not always thread-safe."


-->
