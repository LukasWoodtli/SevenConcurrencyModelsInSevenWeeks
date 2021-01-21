import :timer, only: [ sleep: 1 ]

defmodule Talker do

  def loop do
    receive do  # receive block waits for a message
      # Pattern matching
      {:greet, name} -> IO.puts("Hello #{name}")
      {:praise, name} -> IO.puts("#{name} you are amazing")
      {:celebrate, name, age} -> IO.puts("Here's to another #{age} years, #{name}")
    end
    loop()
  end
end



pid = spawn(&Talker.loop/0)
send(pid, {:greet, "Huey"})
send(pid, {:praise, "Dewey"})
send(pid, {:celebrate, "Louie", 12})
sleep(1000)
