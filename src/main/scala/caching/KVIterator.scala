package caching

import scala.collection.Iterator

class KVIterator[A, B](val cacheAsMap: CacheAsMap[A, B]) extends Iterator[(A, B)] {
  val keyIterator=cacheAsMap.internalCache.getKeys.iterator()

  def hasNext: Boolean = keyIterator.hasNext

  def next(): (A, B) = {
    val key = keyIterator.next.asInstanceOf[A]
    (key, cacheAsMap.getValue(key))
  }
}