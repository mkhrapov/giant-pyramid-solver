package org.khrapov.giantpyramidsolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PositionFactory
{
  // 0 - 3-ball piece
  // 1 - piece with the triangle
  // 2 - piece with the 60-degree arm
  // 3 - cis-piece
  // 4 - trans-piece
  // 5 - the 4 pieces with 90-degree arm

  static final List<List<Position>> positions = new ArrayList<>();

  static
  {
    for(int i = 0; i < 9; i++)
    {
      positions.add(new ArrayList<Position>());
    }

    // positions for the 3-ball piece have been
    // determined by visual examination and account for
    // duplicates

    List<Position> pos1 = positions.get(0);
    pos1.add(new Position(0, 1, 2));
    pos1.add(new Position(1, 2, 3));
    pos1.add(new Position(5, 6, 7));
    pos1.add(new Position(9, 10, 11));
    pos1.add(new Position(19, 20, 21));
  }


  private static final int[][] planes = {
    // planes parallel to the sides
    {0, 1,2,3,4,5,6,7,8,9,10,11,12,13,14},
    {15,16,17,18,19,20,21,22,23,24},
    {25,26,27,28,29,30},

    {0,5,9,12,14,15,19,22,24,25,28,30,31,33,34},
    {1,6,10,13,16,20,23,26,29,32},
    {2,7,11,17,21,27},

    {0,1,2,3,4,15,16,17,18,25,26,27,31,32,34},
    {5,6,7,8,19,20,21,28,29,33},
    {9,10,11,22,23,30},

    {4,8,11,13,14,18,21,23,24,27,29,30,32,33,34},
    {3,7,10,12,17,20,22,26,28,31},
    {2,6,9,16,19,25},

    // planes parallel to the edges
    {5,6,7,8,15,16,17,18},
    {9,10,11,19,20,21,25,26,27},
    {12,13,22,23,28,29,31,32},

    {1,6,10,13,15,19,22,24},
    {2,7,11,16,20,23,25,28,30},
    {3,8,17,21,26,29,31,33},

    {3,7,10,12,18,21,23,24},
    {2,6,9,17,20,22,27,29,30},
    {1,5,16,19,26,28,32,33}
  };


  /*
  For each of the 4-ball pieces, distance between balls
  1 & 2, 1 & 3, 1 & 4,
  2 & 3, 2 & 4,
  1 & 4
  sorted by distance. This creates a unique fingerprint of the
  piece that can be used to identify if this piece would fit into
  4 selected positions in the puzzle.
   */
  private static final double[][] knownDistances = {
    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // placeholder just so indexes of this and positions list align
    {1.0, 1.0, 1.0, 1.732, 2.0, 2.64575},
    {1.0, 1.0, 1.0, 1.0, 1.732, 2.0},
    {1.0, 1.0, 1.0, 1.732, 1.732, 2.64575},
    {1.0, 1.0, 1.0, 1.732, 1.732, 2.0},
    {1.0, 1.0, 1.0, 1.41421, 2.0, 2.23606}
  };


  // coordinates of each position in the puzzle
  private static final double[][] coords = {
      {0.0, 0.0, 0.0},
      {1.0, 0.0, 0.0},
      {2.0, 0.0, 0.0},
      {3.0, 0.0, 0.0},
      {4.0, 0.0, 0.0},
      {0.5, 0.866, 0.0},
      {1.5, 0.866, 0.0},
      {2.5, 0.866, 0.0},
      {3.5, 0.866, 0.0},
      {1.0, 1.732, 0.0},
      {2.0, 1.732, 0.0},
      {3.0, 1.732, 0.0},
      {1.5, 2.598, 0.0},
      {2.5, 2.598, 0.0},
      {2.0, 3.464, 0.0},
      {0.5, 0.28867, 0.8165},
      {1.5, 0.28867, 0.8165},
      {2.5, 0.28867, 0.8165},
      {3.5, 0.28867, 0.8165},
      {1.0, 1.15467, 0.8165},
      {2.0, 1.15467, 0.8165},
      {3.0, 1.15467, 0.8165},
      {1.5, 2.02067, 0.8165},
      {2.5, 2.02067, 0.8165},
      {2.0, 2.88667, 0.8165},
      {1.0, 0.57734, 1.633},
      {2.0, 0.57734, 1.633},
      {3.0, 0.57734, 1.633},
      {1.5, 1.44334, 1.633},
      {2.5, 1.44334, 1.633},
      {2.0, 2.30934, 1.633},
      {1.5, 0.86601, 2.4495},
      {2.5, 0.86601, 2.4495},
      {2.0, 1.73201, 2.4495},
      {2.0, 1.15468, 3.266}
  };





  // positions for 4-ball pieces have to be calculated
  static void calculate()
  {
    int count = 0;

    for(int i = 0; i <= 31; i++)
    {
      for(int j = i+1; j <= 32; j++)
      {
        for(int k = j+1; k <= 33; k++)
        {
          for(int l = k+1; l <= 34; l++)
          {
            if(isPlanar(i, j, k, l))
            {
              count += 1;
              match(i, j, k, l);
            }
          }
        }
      }
    }

    // Lists 6, 7, 8 should have the same positions as list 5

    List<Position> l5 = positions.get(5);
    List<Position> l6 = positions.get(6);
    List<Position> l7 = positions.get(7);
    List<Position> l8 = positions.get(8);

    for(Position p : l5)
    {
      l6.add(p.convert6To(7));
      l7.add(p.convert6To(8));
      l8.add(p.convert6To(9));
    }

    System.out.printf("Total count of planar positions is %d%n", count);
  }


  private static boolean isPlanar(int i, int j, int k, int l)
  {
    for(int[] plane : planes)
    {
      if(contains(plane, i, j, k, l))
      {
        return true;
      }
    }
    return false;
  }


  private static boolean contains(int[] plane, int i, int j, int k, int l)
  {
    if(!contains(plane, i)) return false;
    if(!contains(plane, j)) return false;
    if(!contains(plane, k)) return false;
    if(!contains(plane, l)) return false;

    return true;
  }


  private static boolean contains(int[] plane, int i)
  {
    for(int a : plane)
    {
      if(a == i)
      {
        return true;
      }
    }
    return false;
  }


  private static void match(int i, int j, int k, int l)
  {
    double[] distances = new double[6];
    distances[0] = distance(i, j);
    distances[1] = distance(i, k);
    distances[2] = distance(i, l);
    distances[3] = distance(j, k);
    distances[4] = distance(j, l);
    distances[5] = distance(k, l);

    Arrays.sort(distances);

    if(distances[5] > 2.66)
    {
      return;
    }

    for(int n = 1; n < 6; n++)
    {
      if(fingerPrintMatch(distances, knownDistances[n]))
      {
        positions.get(n).add(new Position(n+1, i, j, k, l));
        return;
      }
    }
  }


  private static double distance(int i, int j)
  {
    double dx = coords[i][0] - coords[j][0];
    double dy = coords[i][1] - coords[j][1];
    double dz = coords[i][2] - coords[j][2];

    return Math.sqrt(dx*dx + dy*dy + dz*dz);
  }


  private static boolean fingerPrintMatch(double[] a, double[] b)
  {
    for(int i = 0; i < 6; i++)
    {
      if(!aboutEqual(a[i], b[i]))
      {
        return false;
      }
    }
    return true;
  }


  private static boolean aboutEqual(double a, double b)
  {
    double c = Math.abs(a - b);
    return c < 0.01;
  }
}
