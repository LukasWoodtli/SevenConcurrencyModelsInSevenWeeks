defmodule ActorsTest do
  use ExUnit.Case

  test "counter" do
    counter = spawn(Counter, :loop, [1])

    send(counter, {:next})
    send(counter, {:next})
    send(counter, {:next})
  end

  test "counter nice API" do
    counter = Counter.start(42)
    Counter.next(counter)
    Counter.next(counter)
    Counter.next(counter)
  end

  test "counter 2" do
    IO.puts("Counter 2")
    counter = Counter2.start(42)
    Counter2.next(counter)
    Counter2.next(counter)
    Counter2.next(counter)
  end

  test "counter 3" do
    IO.puts("Counter 3")
    Counter3.start(42)
    Counter3.next
    Counter3.next
    Counter3.next
  end

  test "parallel" do
    IO.puts("Parallel")
    Parallel.map([1, 2, 3, 4], fn(x) -> x * 2 end)
  end
end
