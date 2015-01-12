Introduction
============
From [BUT2014]_ Chapter 1 

Concurrent or Parallel?
-----------------------
"A concurrent program has multiple logical threads of control. These threads may or may not run in parallel."

Ein concurrentes (gleichzeitiges) Programm hat mehrere logische Abläufe (Threads). Diese können (aber müssen nicht) parallel ablaufen.


"A parallel program potentially runs more quickly than a sequential program by executing different parts of the computation simultaneously (in parallel). It may or may not have more than one logical thread of control."

Ein paralleles Programm führt Berechnungen gleichzeitig aus und Läuft unter Umständen schneller als ein sequenzielles Programm. Es kann (aber muss nicht) mehrere Threads haben.


"[...] concurrency is an aspect of the problem domain — your program needs to handle multiple simultaneous (or near-simultaneous) events."

"Parallelism [...] is an aspect of the solution domain — you want to make your program faster by processing different portions of the problem in parallel."

Concurrency (Gleichzeitigkeit) ist ein Aspekt der Problemstellung. Ein Programm muss mehrere (fast) gleichzeitige Ereignisse (Events) bearbeiten.

Parallelität ist ein Aspekt der Problemlösung. Mehrere Teile des Problems werden parallel bearbeitet, um das Programm schneller zu machen.


"Concurrency is about dealing with lots of things at once. Parallelism is about doing lots of things at once."

Concurrency (Gleichzeitigkeit) heisst sich um viele Dinge gleichzeitig kümmern. Parallelität heisst viele Dinge gleichzeitig zu tun.


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



References
==========
.. [BUT2014] Seven Concurrency Models in Seven Weeks When Threads Unravel by Paul Butcher Version: P1.0 (July 2014) https://pragprog.com/book/pb7con/seven-concurrency-models-in-seven-weeks

.. [Qt2014] https://qt-project.org/doc/qt-5-snapshot/threads-reentrancy.html
