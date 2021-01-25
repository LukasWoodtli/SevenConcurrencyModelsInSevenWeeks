defmodule Accumulator do
  use GenServer

  def start_link do
    :gen_server.start_link({:global, :wc_accumulator}, __MODULE__,
      {HashDict.new, HashSet.new}, [])
  end

  def deliver_counts(ref, counts) do
    :gen_server.cast({:global, :wc_accumulator},
      {:deliver_counts, ref, counts})
  end

  def handle_cast({:deliver_counts, ref, counts},
    {totals, processed_pages}) do

    if Set.member?(processed_pages, ref) do
      {:noreply, {totals, processed_pages}}
    else
      new_totals = Dict.merge(totals, counts, fn(_k, v1, v2) -> v1 + v2 end)
      new_processed_pages = Set.put(processed_pages, ref)
      Parser.processed(ref)
      {:noreply, {new_totals, new_processed_pages}}
    end
  end
end
