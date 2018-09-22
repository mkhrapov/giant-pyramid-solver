package org.khrapov.giantpyramidsolver;

class BasicConstraintSolver
{
  private long counter = 0;
  private Position solution = null;


  void solve()
  {
    recurse(new Position(), 0);
  }


  long getCounter()
  {
    return counter;
  }


  String getSolution()
  {
    return solution.toString();
  }


  /**
   *
   * @param p - current Position
   * @param i - piece number
   */
  private void recurse(Position p, int i)
  {
    if(p.finished())
    {
      solution = p;
      return;
    }

    if(i > 8)
    {
      return;
    }

    counter++;

    for(Position next : PositionFactory.positions.get(i))
    {
      if(p.canAdd(next))
      {
        recurse(p.add(next), i+1);
        if(solution != null)
        {
          return;
        }
      }
    }
  }
}
