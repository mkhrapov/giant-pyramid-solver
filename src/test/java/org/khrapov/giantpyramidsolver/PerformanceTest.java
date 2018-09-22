package org.khrapov.giantpyramidsolver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PerformanceTest
{
  @BeforeAll
  static void calculatePositions()
  {
    PositionFactory.calculate();
    for(int i = 0; i < 6; i++)
    {
      System.out.printf("Piece number: %d Position count: %d%n", i+1, PositionFactory.positions.get(i).size());
    }
  }

  @Test
  void basicConstraintSolverTest()
  {
    BasicConstraintSolver solver = new BasicConstraintSolver();

    long start = System.nanoTime();
    solver.solve();
    long end = System.nanoTime();

    System.out.printf("Calculation took %.2f seconds.%n", (end - start)/1_000_000_000.0);
    System.out.printf("Examined %d positions.%n", solver.getCounter());
    System.out.printf("Solution: %s.%n", solver.getSolution());

    /*
    Total count of planar positions is 7104
    Piece number: 1 Position count: 5
    Piece number: 2 Position count: 384
    Piece number: 3 Position count: 336
    Piece number: 4 Position count: 96
    Piece number: 5 Position count: 168
    Piece number: 6 Position count: 96
    Calculation took 109.05 seconds.
    Examined 52649910 positions.
    Solution: [1, 1, 1, 3, 2, 5, 5, 3, 2, 1, 5, 5, 1, 5, 1, 5, 5, 5, 2, 1, 3, 5, 3, 5, 5, 4, 4, 2, 5, 5, 5, 5, 4, 5, 4].
     */
  }


  @Test
  void geneticAlgorithmTest()
  {
    GeneticAlgorithmSolver solver = new GeneticAlgorithmSolver();

    long start = System.nanoTime();
    solver.solve();
    long end = System.nanoTime();

    System.out.printf("Calculation took %.2f seconds.%n", (end - start)/1_000_000_000.0);
    System.out.printf("Solution: %s.%n", solver.getSolution());

    /*
    Calculation took 3.71 seconds.
    Solution: [1, 1, 1, 3, 2, 5, 5, 3, 2, 1, 5, 5, 1, 5, 1, 5, 5, 5, 2, 1, 3, 5, 3, 5, 5, 4, 4, 2, 5, 5, 5, 5, 4, 5, 4].
     */
  }
}
