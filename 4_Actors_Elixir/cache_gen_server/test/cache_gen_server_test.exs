import :timer, only: [ sleep: 1 ]

defmodule CacheGenServerTest do
  use ExUnit.Case

  test "Cache supervisor test 1" do
    CacheSupervisor.start_link()
    sleep(1000)

    CacheGenServer.put("google.com", "Welcome to Google")
    result = CacheGenServer.size()
    assert result == 17

    # bring the CacheGenServer down
    CacheGenServer.put("google.com", nil)
    sleep(1000)
    result = CacheGenServer.size()
    assert result == 0

    # fill it again
    CacheGenServer.put("google.com", "Welcome to Google")
    result = CacheGenServer.size()
    assert result == 17
  end
end
