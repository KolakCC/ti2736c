package assignment1

import assignment1.samples.ShingleSet
import org.scalatest.FunSuite

class Test extends FunSuite {
  test("ShingleSet") {
    val s = new ShingleSet(2)
    s.add("ab")
    s.add("bc")
    s.add("cd")
    s.add("da")
    s.add("bd")
    assertResult({

      s
    }
    ) {val s = new ShingleSet(2)
      ShingleSetUtils.createShingles(s, "abcdabd",2)
      s
    }
  }
}