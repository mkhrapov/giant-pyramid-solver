package org.khrapov.giantpyramidsolver;

import java.time.LocalDateTime;

public class BasicConstraintSolver
{
  private static long counter = 0;

  public static void main(String[] args)
  {
    PositionFactory.calculate();

    for(int i = 0; i < 6; i++)
    {
      System.out.printf("Piece number: %d Position count: %d%n", i+1, PositionFactory.positions.get(i).size());
    }

    System.out.println(LocalDateTime.now());
    recurse(new Position(), 0);
  }


  /**
   *
   * @param p - current Position
   * @param i - piece number
   */
  private static void recurse(Position p, int i)
  {
    if(p.finished())
    {
      System.out.println(p.toString());
      System.out.println(LocalDateTime.now());
      System.out.printf("Total number of nodes examined: %d%n", counter);
      System.exit(0);
    }

    if(i > 8)
    {
      return;
    }

    if(i > 5)
    {
      i = 5;
    }

    counter++;

    for(Position next : PositionFactory.positions.get(i))
    {
      if(p.canAdd(next))
      {
        recurse(p.add(next), i+1);
      }
    }
  }
}
