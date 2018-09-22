package org.khrapov.giantpyramidsolver;

import java.util.Arrays;

class Position
{
  private static final int SIZE = 35;
  private int[] occupied = new int[SIZE];

  // empty constructor
  Position()
  {

  }

  // Initializer for the 3-ball piece (piece type 1)
  Position(int i, int j, int k)
  {
    occupied[i] = 1;
    occupied[j] = 1;
    occupied[k] = 1;
  }


  // Initializer for the 4-ball piece
  Position(int type, int i, int j, int k, int l)
  {
    occupied[i] = type;
    occupied[j] = type;
    occupied[k] = type;
    occupied[l] = type;
  }


  @Override
  public String toString()
  {
    return Arrays.toString(occupied);
  }


  boolean finished()
  {
    for(int i : occupied)
    {
      if(i == 0)
      {
        return false;
      }
    }
    return true;
  }


  boolean canAdd(Position other)
  {
    for(int i = 0; i < SIZE; i++)
    {
      if(this.occupied[i] != 0 && other.occupied[i] != 0)
      {
        return false;
      }
    }

    return true;
  }


  Position add(Position other)
  {
    Position child = new Position();

    for(int i = 0; i < SIZE; i++)
    {
      child.occupied[i] = this.occupied[i];
    }

    for(int i = 0; i < SIZE; i++)
    {
      if(other.occupied[i] != 0)
      {
        child.occupied[i] = other.occupied[i];
      }
    }

    return child;
  }


  void merge(Position other)
  {
    for(int i = 0; i < SIZE; i++)
    {
      if(other.occupied[i] != 0)
      {
        this.occupied[i] = other.occupied[i];
      }
    }
  }


  int countNonZero()
  {
    int count = 0;

    for(int i = 0; i < SIZE; i++)
    {
      if(occupied[i] != 0)
      {
        count++;
      }
    }

    return count;
  }
}
