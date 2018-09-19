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
  }
}
