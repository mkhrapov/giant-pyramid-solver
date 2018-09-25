package org.khrapov.giantpyramidsolver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PerformanceTest
{
  @BeforeAll
  static void calculatePositions()
  {
    PositionFactory.calculate();
    for(int i = 0; i < 9; i++)
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
    Piece number: 7 Position count: 96
    Piece number: 8 Position count: 96
    Piece number: 9 Position count: 96
    Calculation took 110.44 seconds.
    Examined 52649910 positions.
    Solution: [1, 1, 1, 4, 3, 6, 7, 4, 3, 2, 7, 8, 2, 7, 2, 6, 6, 6, 3, 2, 4, 9, 4, 8, 7, 5, 5, 3, 8, 9, 8, 9, 5, 9, 5].
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
    Calculation took 15.03 seconds.
    Solution: [1, 1, 1, 6, 4, 7, 7, 7, 6, 2, 5, 5, 5, 9, 5, 7, 8, 6, 4, 9, 8, 4, 9, 8, 9, 2, 6, 3, 2, 4, 8, 2, 3, 3, 3].

    Calculation took 1.19 seconds.
    Solution: [1, 1, 1, 7, 4, 9, 9, 9, 7, 2, 5, 5, 5, 6, 5, 9, 8, 7, 4, 6, 8, 4, 6, 8, 6, 8, 7, 3, 2, 4, 2, 3, 3, 2, 3].

    Calculation took 42.93 seconds.
    Solution: [1, 1, 1, 6, 4, 7, 9, 4, 4, 9, 4, 3, 3, 3, 3, 7, 7, 7, 6, 8, 9, 6, 2, 6, 2, 2, 5, 5, 8, 9, 2, 5, 8, 8, 5].
     */
  }


  @Test
  void hybridTest()
  {
    HybridSolver solver = new HybridSolver();

    long start = System.nanoTime();
    solver.solve();
    long end = System.nanoTime();

    System.out.printf("Calculation took %.2f seconds.%n", (end - start)/1_000_000_000.0);
    Position solution = solver.getSolution();

    if(solution != null)
    {
      System.out.printf("Solution: %s.%n", solution.toString());
    }
    else
    {
      System.out.println("Solution not found.");
    }
  }
}
