This Lab required us to implement 3 different page replacment policies: first in first out, least recently used, and one of our own implementation (I chose random).  The memory is depicted as an array and all the replacment policy does is modify values of that array to simulate what a real OS would do.  
To run: 
Compile pager.c
Run the output file with 3 arguments: the page replacement poilcy (0 for FIFO, 1 for LRU, 2 for Random), the number of pages the memory can hold, and the memory access text file.
Example:
"gcc pager.c
a.out 0 3 test3.txt"
