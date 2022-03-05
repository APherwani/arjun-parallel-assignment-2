I picked the third approach for the problem 2. I implemented the CLH lock. 

The reason I did that is because I believe it is the most efficient solution to the problem. 
The advantage of using the CLH lock is that it is space efficient and does not require prior knowledge of how many threads are going to be trying to access the lock at the same time. 
This is very powerful and perfectly fits this problem because we can have as many or as few guests as possible with minimal configuration needed.

Another advantage that the CLH lock has is that it is more memory efficient than other queue based locking algorithms like ALock.

To discuss the other approaches, another reason why I picked the third one is because it is starvation free.

The problem with the first approach is that it is not starvation free. In fact the problem statement said it "A particular guest wanting to see the vase would also have no guarantee that she or he will be able to do so and when." It is deadlock free though.

The second approach was good too. What the other threads would be doing is a busy wait while the critical section is occupied.

References:
- https://www.cs.rochester.edu/research/synchronization/pseudocode/ss.html
- https://www.cs.tau.ac.il/~shanir/nir-pubs-web/Papers/CLH.pdf
- https://javamana.com/2021/08/20210824210053875y.html
- [The textbook](https://www.elsevier.com/books/the-art-of-multiprocessor-programming/herlihy/978-0-12-415950-1)