
defmodule Parser do
  use GenServer

  def start_link(filename) do
    :gen_server.start_link({:global, :wc_parser}, __MODULE__, filename, [])
  end

  def request_page(pid) do
    :gen_server.cast({:global, :wc_parser}, {:request_page, pid})
  end

  def processed(ref) do
    :gen_server.cast({:global, :wc_parser}, {:processed, ref})
  end

  def init(filename) do
    xml_parser = Pages.start_link(filename)
    {:ok, {ListDict.new, xml_parser}}
  end

  def handle_cast({:request_page, pid}, {pending, xml_parser}) do
    new_pending = deliver_page(pid, pending, Pages.next(xml_parser))
    {:noreply, {new_pending, xml_parser}}
  end

  def handle_cast({:processed, ref}, {pending, xml_parser}) do
    new_pending = Dict.delete(pending, ref)
    {:noreply, {new_pending, xml_parser}}
  end

  defp deliver_page(pid, pending, page) when nil == page do
    if Enum.empty?(pending) do
      pending # Nothing to do
    else
      {ref, prev_page} = List.last(pending)
      Counter.deliver_page(pid, ref, prev_page)
      Dict.put(Dict.delete(pending, ref), ref, prev_page)
    end
  end

  defp deliver_page(pid, pending, page) do
    ref = make_ref()
    Counter.deliver_page(pid, ref, page)
    Dict.put(pending, ref, page)
  end
end

defmodule ParserSupervisor do
  use Supervisor

  def start_link(filename) do
    :supervisor.start_link(__MODULE__, filename)
  end

  def init(filename) do
    workers = [worker(Parser, [filename])]
    supervise(workers, strategy: :one_for_one)
  end
end
