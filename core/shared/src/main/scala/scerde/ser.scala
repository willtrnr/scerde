package scerde
package ser

import scerde.error._

trait Serialize[T] {
  def serialize[S](value: T, serializer: S)(implicit S: Serializer[S]): S.Result
}

object Serialize extends SerializeInstances {

  @inline final def apply[T](implicit ev: Serialize[T]): Serialize[T] = ev

  final class Ops[T: Serialize](self: T) {

    final def serialize[S](serializer: S)(implicit S: Serializer[S]): S.Result =
      S.serialize(serializer, self)

  }

}

abstract private[scerde] class SerializeInstances {

  implicit final val serializeForBool: Serialize[Boolean] = new Serialize[Boolean] {
    final def serialize[S](value: Boolean, serializer: S)(implicit S: Serializer[S]): S.Result =
      S.serializeBool(serializer, value)
  }

  implicit final val serializeForByte: Serialize[Byte] = new Serialize[Byte] {
    final def serialize[S](value: Byte, serializer: S)(implicit S: Serializer[S]): S.Result =
      S.serializeByte(serializer, value)
  }

  implicit final val serializeForShort: Serialize[Short] = new Serialize[Short] {
    final def serialize[S](value: Short, serializer: S)(implicit S: Serializer[S]): S.Result =
      S.serializeShort(serializer, value)
  }

  implicit final val serializeForInt: Serialize[Int] = new Serialize[Int] {
    final def serialize[S](value: Int, serializer: S)(implicit S: Serializer[S]): S.Result =
      S.serializeInt(serializer, value)
  }

  implicit final val serializeForLong: Serialize[Long] = new Serialize[Long] {
    final def serialize[S](value: Long, serializer: S)(implicit S: Serializer[S]): S.Result =
      S.serializeLong(serializer, value)
  }

  implicit final val serializeForFloat: Serialize[Float] = new Serialize[Float] {
    final def serialize[S](value: Float, serializer: S)(implicit S: Serializer[S]): S.Result =
      S.serializeFloat(serializer, value)
  }

  implicit final val serializeForDouble: Serialize[Double] = new Serialize[Double] {
    final def serialize[S](value: Double, serializer: S)(implicit S: Serializer[S]): S.Result =
      S.serializeDouble(serializer, value)
  }

  implicit final val serializeForChar: Serialize[Char] = new Serialize[Char] {
    final def serialize[S](value: Char, serializer: S)(implicit S: Serializer[S]): S.Result =
      S.serializeChar(serializer, value)
  }

  implicit final val serializeForString: Serialize[String] = new Serialize[String] {
    final def serialize[S](value: String, serializer: S)(implicit S: Serializer[S]): S.Result =
      S.serializeString(serializer, value)
  }

  implicit final val serializeForBytes: Serialize[Array[Byte]] = new Serialize[Array[Byte]] {
    final def serialize[S](value: Array[Byte], serializer: S)(implicit S: Serializer[S]): S.Result =
      S.serializeBytes(serializer, value)
  }

  implicit final def serializeForOption[T: Serialize]: Serialize[Option[T]] =
    new Serialize[Option[T]] {
      final def serialize[S](value: Option[T], serializer: S)(implicit S: Serializer[S]): S.Result =
        value match {
          case Some(v) => S.serializeSome(serializer, v)
          case None => S.serializeNone(serializer)
        }
    }

  implicit final val serializeForUnit: Serialize[Unit] = new Serialize[Unit] {
    final def serialize[S](value: Unit, serializer: S)(implicit S: Serializer[S]): S.Result =
      S.serializeUnit(serializer)
  }

}

trait Serializer[-S] {

  type Ok
  type Err

  type SerializeSeq
  type SerializeMap

  final type Result = Either[Err, Ok]

  implicit def error: Error[Err]

  @inline final def serialize[T](self: S, value: T)(implicit S: Serialize[T]): Result =
    S.serialize(value, self)(this)

  def serializeBool(self: S, value: Boolean): Result

  def serializeByte(self: S, value: Byte): Result

  def serializeShort(self: S, value: Short): Result

  def serializeInt(self: S, value: Int): Result

  def serializeLong(self: S, value: Long): Result

  def serializeFloat(self: S, value: Float): Result

  def serializeDouble(self: S, value: Double): Result

  def serializeChar(self: S, value: Char): Result

  def serializeString(self: S, value: String): Result

  def serializeBytes(self: S, value: Array[Byte]): Result

  def serializeNone(self: S): Result

  def serializeSome[T: Serialize](self: S, value: T): Result

  def serializeUnit(self: S): Result

  def serializeSeq(self: S, len: Option[Int]): Either[Err, SerializeSeq]

  def serializeMap(self: S, len: Option[Int]): Either[Err, SerializeMap]

}

trait SerializerThrow[-S] extends Serializer[S] {

  override type Ok = Unit
  final override type Err = Throwable

  implicit final override def error: Error[Err] = Error.errorForThrowable

}

object Serializer extends SerializerInstances {

  @inline def apply[S](implicit ev: Serializer[S]): Serializer[S] = ev

  final class Ops[S](self: S)(implicit final val S: Serializer[S]) {

    @inline final def serialize[T: Serialize](value: T): S.Result =
      S.serialize(self, value)

    @inline final def serializeBool(value: Boolean): S.Result =
      S.serializeBool(self, value)

    @inline final def serializeByte(value: Byte): S.Result =
      S.serializeByte(self, value)

    @inline final def serializeShort(value: Short): S.Result =
      S.serializeShort(self, value)

    @inline final def serializeInt(value: Int): S.Result =
      S.serializeInt(self, value)

    @inline final def serializeLong(value: Long): S.Result =
      S.serializeLong(self, value)

    @inline final def serializeFloat(value: Float): S.Result =
      S.serializeFloat(self, value)

    @inline final def serializeDouble(value: Double): S.Result =
      S.serializeDouble(self, value)

    @inline final def serializeChar(value: Char): S.Result =
      S.serializeChar(self, value)

    @inline final def serializeString(value: String): S.Result =
      S.serializeString(self, value)

    @inline final def serializeBytes(value: Array[Byte]): S.Result =
      S.serializeBytes(self, value)

    @inline final def serializeNone(): S.Result =
      S.serializeNone(self)

    @inline final def serializeSome[T: Serialize](value: T): S.Result =
      S.serializeSome(self, value)

    @inline final def serializeUnit(): S.Result =
      S.serializeUnit(self)

    @inline final def serializeSeq(len: Option[Int]): Either[S.Err, S.SerializeSeq] =
      S.serializeSeq(self, len)

    @inline final def serializeMap(len: Option[Int]): Either[S.Err, S.SerializeMap] =
      S.serializeMap(self, len)

  }

}

abstract private[scerde] class SerializerInstances extends SerializerPlatformInstances
