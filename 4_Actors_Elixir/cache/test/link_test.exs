import :timer, only: [ sleep: 1 ]

defmodule LinkTest do
  use ExUnit.Case

  test "Link test terminate unexpected" do
    pid1 = spawn(&Link.loop/0)
    pid2 = spawn(&Link.loop/0)

    send(pid1, {:link_to, pid2})
    send(pid1, {:exit_because, :bad_thing_happend})

    sleep(2000)

    assert Process.info(pid1, :status) == nil
    assert Process.info(pid2, :status) == nil
  end

  test "Link test terminate normally" do
    pid1 = spawn(&Link.loop/0)
    pid2 = spawn(&Link.loop/0)

    send(pid1, {:link_to, pid2})
    send(pid2, {:exit_because, :normal})

    sleep(2000)

    assert Process.info(pid1, :status) == {:status, :waiting}
    assert Process.info(pid2, :status) == nil
  end

  test "Link test system process" do
    pid1 = spawn(&Link.loop_system/0)
    pid2 = spawn(&Link.loop/0)

    send(pid1, {:link_to, pid2})
    send(pid2, {:exit_because, :yet_another_bad_thing_happened})

    sleep(2000)

    assert Process.info(pid1, :status) == {:status, :waiting}
    assert Process.info(pid2, :status) == nil
  end

end
