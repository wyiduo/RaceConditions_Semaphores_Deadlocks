
For Reader threads:
First, there is a waiting queue for Reader threads. Then, there is a lock for the critical section of Reader threads (also known as a lock for reading). There is also a mutex lock for the amount of current Reader threads (also known as readers).

How Reader threads will execute:

1.	Reading threads will get stopped at the waiting queue for readers. They will be released one at a time, but will also be stopped if there is a lock for reading (which in this case will be set by the Writer threads (also known as writers))
2.	If there is no read_lock, then the readers will execute one at a time to increment readcount. The first reader will acquire the lock for writing (write_lock) so writers don’t write when the readers are reading.
  - a.	read_lock will be acquired and released as the same time as mutex_readcount if there no writers that have acquired read_lock (basically, if there are only readers, than read_lock would not do anything)
  - b.	eventually, read_lock will be acquired by a writer and then no more readers will progress to the critical section (reading)
3.	The readers that make it past the read_lock will move on to their critical section (do reading). Then, the readers will one by one decrement readcount. When readcount == 0, then write_lock will be released so the writers can now write.
4.	Finally, the readers will “do something else” and reiterate 5 times as per the for loop.

---

For Writer threads:

How Writer threads will execute:

1.	Writing threads (also known as writers) will immediately, without going through a waiting queue, acquire the lock for reading (read_lock), increment writecount, and move on to the critical section (writing). This means that any Writer thread that is waiting will immediately acquire the lock for reading and stop any future readers from reading.
2.	The writers will now queue up to enter the critical section on a waiting queue represented by write_lock. Any readers that have started reading before the first writer that acquired read_lock will continue to execute and eventually finish their critical section (reading) and release the write_lock. Now, when the readers have released write_lock, the writers can now enter their critical section (writing).
  - a.	Since only writer can write to a file at one time, only 1 writer is allowed into the critical section at once.
3.	The writers will one by one leave the critical section and one by one decrement writecount. When writecount == 0 (when there are no more writers waiting or in critical section), read_lock will be released and the readers can continue going to their critical section (reading).
4.	Finally, the writers will “do something else” and reiterate 5 times as per the for loop.

