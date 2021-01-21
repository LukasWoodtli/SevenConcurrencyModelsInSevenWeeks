import :timer, only: [ sleep: 1 ]

defmodule CacheTest do
  use ExUnit.Case


  test "Cache test 1" do
    #Cache.start_link
    #Cache.put("google.com", "Welcome to Google")
    #result = Cache.get("google.com")
    #assert result == "Welcome to Google"

    # this would bring down the cache
    # Cache.put("google.com", nil)

    #Cache.terminate()
  end
end

defmodule CacheSupervisorTest do
  use ExUnit.Case

  test "Cache supervisor test 1" do
    CacheSupervisor.start()
    sleep(1000)

    Cache.put("google.com", "Welcome to Google")
    result = Cache.size()
    assert result == 17

    # bring the cache down
    Cache.put("google.com", nil)
    sleep(1000)
    result = Cache.size()
    assert result == 0

    # fill it again
    Cache.put("google.com", "Welcome to Google")
    result = Cache.size()
    assert result == 17


  end

end
