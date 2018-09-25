package org.khrapov.giantpyramidsolver;


import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class HybridSolver
{
  private Position solution = null;
  private static final int INITIAL_SIZE = 500;
  private static final int INITIAL_BEST = 100;
  private PriorityQueue<Organism> pq;


  void solve()
  {
    runExperiment();
  }


  Position getSolution()
  {
    return solution;
  }


  private void runExperiment()
  {
    List<Organism> initialRandomBunch = new ArrayList<>();
    randomlyGenaratedOrganisms(initialRandomBunch);
    //System.out.println("Finished generating.");
    initialRandomBunch.sort(new OrganismComparator());
    //System.out.println("Finished sorting.");
    pq = new PriorityQueue<>(2*INITIAL_BEST, new OrganismComparator());

    int counter = 0;
    for(Organism o : initialRandomBunch)
    {
      pq.add(o);
      counter++;
      if(counter == INITIAL_BEST)
      {
        break;
      }
    }

    Organism o;
    while((o = pq.poll()) != null)
    {
      boolean found = process(o);
      if(found)
      {
        break;
      }
    }
  }


  private void randomlyGenaratedOrganisms(List<Organism> lst)
  {
    for(int i = 0; i < INITIAL_SIZE; i++)
    {
      lst.add(Organism.getRandom());
    }
  }


  private boolean process(Organism o)
  {
    //System.out.printf("Proseccing org with fitness of %d.%n", o.getFitness());

    int[] sizes = { 5, 384, 336, 96, 168, 96, 96, 96, 96 };

    for(int gene = 0; gene < 9; gene++)
    {
      for(int value = 0; value < sizes[gene]; value++)
      {
        Organism child = o.specificMutant(gene, value);

        if(child.finished())
        {
          solution = child.getPosition();
          return true;
        }

        if(child.getFitness() > o.getFitness())
        {
          pq.add(child);
        }
      }
    }

    return false;
  }
}
