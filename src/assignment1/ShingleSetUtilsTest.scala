package assignment1

import assignment1.samples.ShingleSet
import org.scalatest.FunSuite

class ShingleSetUtilsTest extends FunSuite {
  test("ShingleSet example in the assignment") {
    val expected = new ShingleSet(2)
    expected.add("ab")
    expected.add("bc")
    expected.add("cd")
    expected.add("da")
    expected.add("bd")

    val result = new ShingleSet(2)
    ShingleSetUtils.createShingles(result, "abcdabd", 2)

    assertResult(expected) { result }
  }

  test("ShingleSet self made k=5") {
    val expected = new ShingleSet(5)
    expected.add("abcda")
    expected.add("bcdab")
    expected.add("cdadd")

    val result = new ShingleSet(5)
    ShingleSetUtils.createShingles(result, "abcdabd", 5)

    assertResult(expected) { result }
  }

  test("ShingleSet self made k=1") {
    val expected = new ShingleSet(5)
    expected.add("a")
    expected.add("b")
    expected.add("c")
    expected.add("d")

    val result = new ShingleSet(1)
    ShingleSetUtils.createShingles(result, "abcdabd", 1)

    assertResult(expected) { result }
  }
}