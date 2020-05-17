Seven Concurrency Models
 
Threading:

Producer: Parser
Consumer: Counter


| Version | Producer          | Consumer                    |
|---------|-------------------|-----------------------------|
| v1      | none (Sequential) | none (Sequential)           |
| v2      | BlockingQueue     | Map                         |
| v3      | BlockingQueue     | Map (manually synchronized) |
| v4      | BlockingQueue     | ConcurrentHashMap           |
| v4      | BlockingQueue  | local HashMap (merged when finished counting to shared ConcurrentHashMap) |



Some Data Structures from `java.util.concurrent`:

- `CopyOnWriteArrayList`
- `BlockingQueue`
- `ConcurrentLinkedQueue` 
- `synchronizedMap()` method in `Collections`, but synchronized collections don’t provide atomic read-modify-write methods 
- `ConcurrentHashMap`


