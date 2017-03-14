package game_of_life

import java.util.concurrent.ThreadLocalRandom

import rx.lang.scala.observables.SyncOnSubscribe
import rx.lang.scala.{Notification, Observable}

import scala.collection._

trait Game extends Grid {

  import Game._

  def iterate(start: Board): Iterable[Board] = {
    Iterator.iterate(start)(_.next).toIterable
  }

  /** Returns an observable which emits the given board and its successors while responding to backpressure. */
  def observe(start: Board): Observable[Board] = {
    Observable create SyncOnSubscribe.stateful(() => start)(board => Notification.OnNext(board) -> board.next)
  }

  case class Board private(generation: Int, cells: BitSet) {

    def next: Board = {
      copy(generation = generation + 1, cells = (BitSet.empty /: (allPositions filter nextAlive))(_ + _.index))
    }

    private def nextAlive(position: Position) = {
      position.allNeighborPositions count alive match {
        case 2 => alive(position)
        case 3 => true
        case _ => false
      }
    }

    def alive(position: Position): Boolean = cells(position.index)
  }

  object Board {

    def apply(setup: SetupPredicate = random): Board = {
      apply(position => setup(position.row, position.column))
    }

    def apply(setup: Position => Boolean): Board = {
      val cells = (BitSet.empty /: (allPositions filter setup))(_ + _.index)
        .ensuring(cells => cells.isEmpty || 0 <= cells.min)
        .ensuring(cells => cells.isEmpty || cells.max < size)
      new Board(1, cells)
    }
  }
}

object Game {

  type SetupPredicate = (Int, Int) => Boolean

  def random(row: Int, column: Int): Boolean = ThreadLocalRandom.current nextBoolean ()

  def blinkers(row: Int, column: Int): Boolean = row % 4 == 1 && column % 4 < 3
}
