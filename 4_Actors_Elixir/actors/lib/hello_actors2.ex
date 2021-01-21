
defmodule Talker2 do

  def loop do
    receive do  # receive block waits for a message
      # Pattern matching
      {:greet, name} -> IO.puts("Hello #{name}")
      {:praise, name} -> IO.puts("#{name} you are amazing")
      {:celebrate, name, age} -> IO.puts("Here's to another #{age} years, #{name}")
      {:shutdown} -> exit(:normal)
    end
    loop()
  end
end



Process.flag(:trap_exit, true)
pid = spawn_link(&Talker2.loop/0)
send(pid, {:greet, "Huey"})
send(pid, {:praise, "Dewey"})
send(pid, {:celebrate, "Louie", 12})
send(pid, {:shutdown})

receive do
  {:EXIT, ^pid, reason} ->
    IO.puts("Talker2 has exited (#{reason}))")
end
