Concurrent or Parallel
=======================
From [BUT2014]_ Chapter 1 Introduction 

My German translation:
----------------------
Ein concurrentes (gleichzeitiges) Programm hat mehrere logische Abläufe (Threads). Diese können (aber müssen nicht) parallel ablaufen.

Ein paralleles Programm führt Berechnungen gleichzeitig aus und Läuft unter Umständen schneller als ein sequenzielles Programm. Es kann (aber muss nicht) mehrere Threads haben.

Concurrency (Gleichzeitigkeit) heisst sich um viele Dinge gleichzeitig kümmern.
Parallelität heisst viele Dinge gleichzeitig zu tun.

Concurrency (Gleichzeitigkeit) ist ein Aspekt der Problemstellung. Ein Programm muss mehrere (fast) gleichzeitige Ereignisse (Events) bearbeiten.
Parallelität ist ein Aspekt der Problemlösung. Mehrere Teile des Problems werden parallel bearbeitet, um das Programm schneller zu machen.


Engilsh:
--------
"A concurrent program has multiple logical threads of control. These threads may or may not run in parallel."

"A parallel program potentially runs more quickly than a sequential program by executing different parts of the computation simultaneously (in parallel). It may or may not have more than one logical thread of control."


"[...] concurrency is an aspect of the problem domain — your program needs to handle multiple simultaneous (or near-simultaneous) events."

"Parallelism [...] is an aspect of the solution domain — you want to make your program faster by processing different portions of the problem in parallel."

"Concurrency is about dealing with lots of things at once. Parallelism is about doing lots of things at once."




Atomic vs. volatile
===================
- Atomic operations/variables
- volatile


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
