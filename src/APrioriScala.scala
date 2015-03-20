/**
 * Created by Dereck on 3/13/2015.
 */

import scala.collection.JavaConverters._

object APrioriScala {
  def findSimilarStarts(set: java.util.Set[StringSet], startSize: Int) = {
    set.asScala.groupBy(set => set.asScala.take(startSize)).map {
      case (a, b) => (a.asJava, b.asJava)
    }.asJava
  }

  def getCombinations(set: java.util.Set[StringSet], startSize: Int) = {
    set.asScala.groupBy(set => set.asScala.take(startSize)).map {
      case (start, setsThatStartWithStart) => (start, unionSet(setsThatStartWithStart.toList))
    } /*.mapValues {
      case union => union.combinations(startSize+1)
    }*/
  }

  def constructCandidates(set: java.util.Set[StringSet], startSize: Int): java.util.Set[StringSet] = {
    set.asScala.groupBy(set => set.asScala.take(startSize)).map {
      case (_, setsThatStartWithStart) => unionSet(setsThatStartWithStart.toList)
    }.toSet.asJava
  }


  def isFrequent(bigSet: java.util.Set[StringSet], smallSet: List[StringSet]): Boolean = {
    bigSet.containsAll(smallSet.asJavaCollection)
  }


  def unionSet(list: List[StringSet]): StringSet = {
    val ret = new StringSet();
    list.map {
      case stringset => stringset.asScala.map {
        case s => ret.add(s)
      }
    }
    ret
  }

}
