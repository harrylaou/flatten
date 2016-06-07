package flatten

trait Part09 {

  // Most well-known typeclasses in Play are the ones for JSON: Reads, Writes and Format
  // Play provides some `instances` of them: Format[String], Reads[DateTime], etc.
  // You can define `instances` of these type classes for your own classes.

  // Exercise: without looking at the previous part, create a type class `Serializable`, a function `toBytes` that impicitly uses this
  // typeclass, and instances for `Int` and `String`.

  trait Serializable[T] {
    def toBytes: Array[Byte]
  }

  implicit def serializeInt(i: Int) = new Serializable[Int] {
    def toBytes = i.toString.getBytes()
  }
  implicit def serializeString(s: String) = new Serializable[String] {
    def toBytes = s.getBytes()
  }

}
