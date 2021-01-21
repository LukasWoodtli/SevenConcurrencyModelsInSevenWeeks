defmodule Counter do

  def start(count) do
    spawn(__MODULE__, :loop, [count])
  end

  def next(counter) do
    send(counter, {:next})
  end

  def loop(count) do
    receive do
      {:next} ->
        IO.puts("Current count: #{count}")
        loop(count + 1)
    end
  end
end
