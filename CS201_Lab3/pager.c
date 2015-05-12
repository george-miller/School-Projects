// George Miller Lab III

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define PAGEHIT  0
#define PAGEMISS 1

int * mem;
int mem_size = 0;  /* memory size in pages */



/* Your job is to implement the following three functions */

/* input: none */
/* output: page to be replaced */
int fifo(int level1[])
{
	int victim;
	int i = 0;
	while (i < mem_size){
		if (level1[i] == 1){
			victim = i;
		}
		else{
			level1[i]--;
		}
		i++;
	}
	level1[victim] = mem_size;
	return victim;

}
/***************************************************************/
/* input: none */
/* output: page to be replaced */
int lru(int level2[])
{
	int max = 0;
	int maxIndex;
	int i = 0;
	while (i < mem_size){
		if (level2[i] > max){
			max = level2[i];
			maxIndex = i;
		}
		i++;
	}
	updateLevel2(level2, maxIndex);
	return maxIndex;

}

// Update the level2 array with a new memory access
// index is the index in memory that was just accessed

// This method simulates time passing, when memory is accessed,
// every other element in the array is incremented, because each
// of the elements is sort of like a timer, saving how long they've
// been sitting in memory for
updateLevel2(int level2[], int index){
	int i = 0;
	while (i < mem_size){
		if (i == index){
			level2[i] = 1;
		}
		else{
			level2[i]++;
		}
		i++;
	}
}


// helper method to print the arrays so I can test it
printArray(int level[]){
	printf("\n");
	int i = 0;
	while (i < mem_size){
		printf(" %d ", level[i]);
		i++;
	}
	printf("\n");
}

/***************************************************************/
/* input: none */
/* output: page to be replaced */

// For my own algorithm, I chose to do random.
// As a programmer, you can never predict the future of what
// page will be accessed, so going with random will at least
// give you an even number of page faults in many circumstances.
// After running this a bunch of times on random (and not so random)
// text files, it seems that random is the best because of this
// inability to predict what will actually come next.  Yes you can 
// say that if a page hasn't been used recently so maybe it won't be used,
// but in reality, there's too many processes happening at once to 
// decisivley say that this is true.  Same goes for first in first out.

// I had to reset the seed every time so I would get a truly random number
int own(int i)
{
  srand(i);
  return (rand() % mem_size);
}



/***************************************************************/
/* Input: file handle
/* Output: non
/* This function writes the memory content to a file */

void print_mem(FILE *file)
{
  int i;
  
  for(i = 0; i < mem_size; i++)
    fprintf(file, "%d ", mem[i]);
  
  fprintf(file, "\n");
}

/***************************************************************/
/* input: page number  */
/* output: NONE */
void insert(int page)
{
   unsigned i;
   
   for(i = 0; i < mem_size; i++)
     if(!mem[i])
     {
        mem[i] = page;
	return;
     }
     
   printf("Memory full and is not suppose to be!!\n");
   exit(1);
}



/***************************************************************/
/* Input:  page number */
/* Output: page hit or page miss */
int mem_check(int page, int level2[])
{
  unsigned i;
  
  for(i = 0; i < mem_size; i++){
    if(mem[i] == page){
      updateLevel2(level2, i);
      return PAGEHIT;
    }
  }
    
  return PAGEMISS;
  
}
/***************************************************************/
/* Input: none 
/* Ouput: 1 if memory is full, 0 otherwise */
int IsFull()
{
   unsigned i;
   
   for(i = 0; i < mem_size; i++)
     if(!mem[i])
       return 0;
   
   return 1;
}

/***************************************************************/
int main(int argc, char * argv[])
{
  int policy; /* replacement policy */
  int current;  /* current page accessed */
  FILE * fp; /* The file containing the page accesses */
  FILE * rp; /* output file */
  char filename[30]={""};
  const char * extension[] ={".fifo", ".lru", "new"};
  float num_accesses = 0.0; /* total number of page accesses */
  float page_faults = 0.0;
  unsigned victim = 0;  /* page to be replaced */
  
  /* Getting and checking the input from the command line */
  if(argc != 4)
  {
    printf("usage: pager policy size filename\n");
    exit(1);
  }
  
  policy = atoi(argv[1]);
  mem_size = atoi(argv[2]);
  
  if( policy < 0 || policy > 2)
  { 
    printf("policy must be 0, 1, or 2\n");
    exit(1);
  }
  
  if(mem_size <= 0 )
  {
    printf("Size must be a positive integer.\n");
    exit(1);
  }

  // Both level1 and level2 arrays are representations of memory
  // however, instead of the actual memory, they hold values.
  // For level1, the values are the order that memory locations were overwritten, so I can see which was first
  // For level2, the values represent time, so I can see which is the oldest.

  // Create the array used to hold fifo information
  // This array essentially hold the order of elements.
  // For example a level1 array looking like this:
  //     {2, 3, 4, 1}
  // Would mean that the element at index 3 in the memory
  // was used first.  Then the element at index 0, then 1, then 2
  int level1[mem_size];
  int i = 0;
  while (i != mem_size){
  	level1[i] = i+1;
  	i++;
  }

  // For LRU, it will be similiar
  // The level2 array will store which element in memory was
  // Used least recently
  int level2[mem_size];
  i = 0;
  while (i != mem_size){
  	level2[i] = mem_size - i;
  	i++;
  }
  i = 0;
  
  /* Allocate and initialize the memory */
  mem = (int *)calloc(mem_size, sizeof(int));
  if(!mem)
  {
    printf("Cannot allocate mem\n");
    exit(1);
  }
  
  /* open the memory access file */
  fp = fopen(argv[3], "r");
  if(!fp)
  {
    printf("Cannot open file %s\n", argv[3]);
    exit(1);
  }
  
  /* Create the output file */
  strcat(filename, argv[3]);
  strcat(filename,extension[policy]);
  rp = fopen(filename, "w");
  if(!rp)
  {
    printf("Cannot create file %s\n", filename);
    exit(1);
  }
  fscanf(fp,"%d", &current);
  /* The main loop of the program */
  while(!feof(fp))
  {

    num_accesses++;
    if(mem_check(current, level2) == PAGEMISS){
      page_faults++;
    
    
    switch(policy)
    {
      case 0: 
      	if( IsFull())
	      {
		victim = fifo(level1);
		mem[victim] = current;
	      }
	      else
		insert(current);
	      break;
	     
      case 1: if( IsFull())
	      {
		victim = lru(level2);
		mem[victim] = current;
	      }
	      else
		insert(current);
	      break;
	      
      case 2: if( IsFull())
	      {
		victim = own(i);
		mem[victim] = current;
	      }
	      else
		insert(current);
	      break;
	      
	      
      default: printf("Unknown policy ... Exiting\n");
	       exit(1);
      
    }/* end switch-case */
    }
    fscanf(fp,"%d", &current);
    print_mem(rp);

  }/* end while */
  fprintf(rp,"percentage of page faults = %f", page_faults/num_accesses);
  
  /* wrap-up */
  fclose(fp);
  fclose(rp);
  free(mem);
  
  return 1;

}
