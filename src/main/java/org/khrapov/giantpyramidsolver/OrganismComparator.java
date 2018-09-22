package org.khrapov.giantpyramidsolver;

import java.io.Serializable;
import java.util.Comparator;


public class OrganismComparator implements Comparator<Organism>, Serializable
{
  private static final long serialVersionUID = 432985987217L;

  @Override
  public int compare(Organism b1, Organism b2)
  {
    int score1 = b1.getFitness();
    int score2 = b2.getFitness();
    if (score1 < score2) return 1;
    if (score1 == score2) return 0;
    return -1;
  }
}