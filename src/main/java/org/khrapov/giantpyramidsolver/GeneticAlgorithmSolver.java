package org.khrapov.giantpyramidsolver;

import java.util.ArrayList;
import java.util.List;


class GeneticAlgorithmSolver
{
  private Position solution = null;
  private static int NUM_GENERATIONS = 20;
  private static int GENERATION_SIZE = 10_000;
  private static int NUM_CHILDREN = 100;


  void solve()
  {
    int exp = 1;
    while(solution == null)
    {
      System.out.printf("Experiment %d.%n", exp);
      runExperiment(NUM_GENERATIONS);
      exp++;
    }
  }


  void runExperiment(int numGenerations)
  {
    int generationID = 0;
    List<Organism> currentGeneration = new ArrayList<>();
    List<Organism> nextGeneration = new ArrayList<>();

    randomlyGenaratedOrganisms(currentGeneration);

    while(generationID < numGenerations)
    {
      currentGeneration.sort(new OrganismComparator());
      System.out.printf("%d, ", currentGeneration.get(0).getFitness());

      for(int i = 0; i < GENERATION_SIZE/NUM_CHILDREN; i++)
      {
        Organism o = currentGeneration.get(i);

        for(int j = 0; j < NUM_CHILDREN; j++)
        {
          Organism child = o.child();
          if(child.finished())
          {
            solution = child.getPosition();
            System.out.println();
            return;
          }
          nextGeneration.add(child);
        }
      }

      currentGeneration = nextGeneration;
      nextGeneration = new ArrayList<>();

      generationID++;
    }

    System.out.println();
  }


  void randomlyGenaratedOrganisms(List<Organism> lst)
  {
    for(int i = 0; i < GENERATION_SIZE; i++)
    {
      lst.add(Organism.getRandom());
    }
  }


  Position getSolution()
  {
    return solution;
  }
}
