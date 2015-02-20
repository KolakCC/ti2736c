package assignment1

import assignment1.samples.ShingleSet

object ShingleSetUtils {
  def createShingles(set: ShingleSet, s: String, k: Int): Int = {

    s.length() match{
      case l if(l>=k) => {
        set.add(s.take(k))
        createShingles(set, s.drop(1), k)
        1
      }
      case _ => 1
    }
  }
}