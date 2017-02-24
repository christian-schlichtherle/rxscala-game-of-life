# RxScala Game of Life

This is a simple implementation of [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) 
using [Scala](https://www.scala-lang.org) with [RxScala](http://reactivex.io/rxscala/).
It's a fun project for comparison with other implementations in
[Ruby](https://github.com/christian-schlichtherle/ruby-game-of-life) and
[Scala with Akka](https://github.com/christian-schlichtherle/akka-game-of-life).

Among the three, this implementation performs best.

## How to Use

First, start sbt:

    sbt

Now, for simulating a random pattern of cells on a grid with 80 rows and 240 columns for 1000 generations enter:

    > run 80 240 1000
    
To simulate a simple test pattern of blinkers for three generations enter:

    > run 80 240 3 blinkers
    
To simulate a custom pattern you can provide boolean expressions like this:

    > run 80 240 33 ($r + $c) % 40 == 0 || ($r - $c) % 40 == 0
    
In the expression, `$r` refers to the current row and `$c` to the current column.
The Scala API can be used in the expression, so you can also write this:

    > run 80 240 556 scala.math.sqrt(scala.math.pow($r - 40, 2) + scala.math.pow($c - 120, 2)).toInt % 4 < 2
