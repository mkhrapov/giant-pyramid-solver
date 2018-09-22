package org.khrapov.giantpyramidsolver;


import java.util.Random;

class Organism
{
  private Position position;
  private int fitness;
  private int[] selection;
  private static final Random rng = new Random();


  static Organism getRandom()
  {
    Organism o = new Organism();

    for(int i = 0; i < 9; i++)
    {
      o.selection[i] = getRandomPosition(i);
    }

    o.calculateFitness();

    return o;
  }


  private Organism()
  {
    selection = new int[9];
  }


  private static int getRandomPosition(int piece)
  {
    int[] sizes = { 5, 384, 336, 96, 168, 96, 96, 96, 96 };
    return rng.nextInt(sizes[piece]);
  }


  private void calculateFitness()
  {
    Position p = new Position();
    for(int i = 0; i < 9; i++)
    {
      int adj = i > 5 ? 5 : i;
      p.merge(PositionFactory.positions.get(adj).get(selection[i]));
    }
    position = p;

    fitness = p.countNonZero();
  }


  Organism child()
  {
    Organism n = new Organism();
    for(int i = 0; i < 9; i++)
    {
      n.selection[i] = this.selection[i];
    }

    // mutate
    int gene = rng.nextInt(9);
    n.selection[gene] = getRandomPosition(gene);
    n.calculateFitness();
    return n;
  }


  Position getPosition()
  {
    return position;
  }


  int getFitness()
  {
    return fitness;
  }


  boolean finished()
  {
    return fitness == 35;
  }
}
