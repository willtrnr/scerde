package scerde
package de

import scerde.error._

trait Deserialize[T] {
  def deserialize[D](deserializer: D)(implicit D: Deserializer[D]): D.Result[T]
}

object Deserialize {

  @inline final def apply[T](implicit ev: Deserialize[T]): Deserialize[T] = ev

  final class DeserializePartialApply[T: Deserialize] {
    @inline final def apply[D](deserializer: D)(implicit D: Deserializer[D]): D.Result[T] =
      D.deserialize(deserializer)
  }

  @inline final def deserialize[T: Deserialize]: DeserializePartialApply[T] =
    new DeserializePartialApply[T]

}

trait Deserializer[-D] {

  type Err

  final type Result[T] = Either[Err, T]

  implicit def error: Error[Err]

  @inline final def deserialize[T](self: D)(implicit D: Deserialize[T]): Result[T] =
    D.deserialize(self)(this)

  def deserializeAny[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value]

  def deserializeBool[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

  def deserializeByte[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

  def deserializeShort[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

  def deserializeInt[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

  def deserializeLong[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

  def deserializeFloat[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

  def deserializeDouble[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

  def deserializeChar[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

  def deserializeString[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

  def deserializeBytes[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

  def deserializeOption[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

  def deserializeUnit[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

  def deserializeSeq[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

  def deserializeMap[V](self: D, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
    this.deserializeAny(self, visitor)

}

trait DeserializerThrow[-D] extends Deserializer[D] {

  final override type Err = Throwable

  implicit final override def error: Error[Err] = Error.errorForThrowable

}

object Deserializer extends DeserializerInstances {

  @inline final def apply[D](implicit ev: Deserializer[D]): Deserializer[D] = ev

  final class Ops[D](self: D)(implicit final val D: Deserializer[D]) {

    @inline final def deserialize[T: Deserialize]: D.Result[T] =
      D.deserialize(self)

    @inline final def deserializeAny[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

    @inline final def deserializeBool[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeBool(self, visitor)

    @inline final def deserializeByte[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

    @inline final def deserializeShort[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

    @inline final def deserializeInt[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

    @inline final def deserializeLong[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

    @inline final def deserializeFloat[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

    @inline final def deserializeDouble[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

    @inline final def deserializeChar[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

    @inline final def deserializeString[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

    @inline final def deserializeBytes[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

    @inline final def deserializeOption[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

    @inline final def deserializeUnit[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

    @inline final def deserializeSeq[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

    @inline final def deserializeMap[V](visitor: V)(implicit V: Visitor[V]): D.Result[V.Value] =
      D.deserializeAny(self, visitor)

  }

}

abstract private[scerde] class DeserializerInstances extends PlatformDeserializerInstances {

  implicit final val deserializerForBool: Deserializer[Boolean] = new DeserializerThrow[Boolean] {
    final override def deserializeAny[V](self: Boolean, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      V.visitBool(visitor, self)
  }

  implicit final val deserializerForByte: Deserializer[Byte] = new DeserializerThrow[Byte] {
    final override def deserializeAny[V](self: Byte, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      V.visitByte(visitor, self)
  }

  implicit final val deserializerForShort: Deserializer[Short] = new DeserializerThrow[Short] {
    final override def deserializeAny[V](self: Short, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      V.visitShort(visitor, self)
  }

  implicit final val deserializerForInt: Deserializer[Int] = new DeserializerThrow[Int] {
    final override def deserializeAny[V](self: Int, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      V.visitInt(visitor, self)
  }

  implicit final val deserializerForLong: Deserializer[Long] = new DeserializerThrow[Long] {
    final override def deserializeAny[V](self: Long, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      V.visitLong(visitor, self)
  }

  implicit final val deserializerForFloat: Deserializer[Float] = new DeserializerThrow[Float] {
    final override def deserializeAny[V](self: Float, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      V.visitFloat(visitor, self)
  }

  implicit final val deserializerForDouble: Deserializer[Double] = new DeserializerThrow[Double] {
    final override def deserializeAny[V](self: Double, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      V.visitDouble(visitor, self)
  }

  implicit final val deserializerForChar: Deserializer[Char] = new DeserializerThrow[Char] {
    final override def deserializeAny[V](self: Char, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      V.visitChar(visitor, self)
  }

  implicit final val deserializerForString: Deserializer[String] = new DeserializerThrow[String] {
    final override def deserializeAny[V](self: String, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      V.visitString(visitor, self)
  }

  implicit final val deserializerForBytes: Deserializer[Array[Byte]] = new DeserializerThrow[Array[Byte]] {
    final override def deserializeAny[V](self: Array[Byte], visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      V.visitBytes(visitor, self)
  }

  implicit final def deserializerForOption[T](implicit D: Deserializer[T]): Deserializer[Option[T]] =
    new Deserializer[Option[T]] {

      final override type Err = D.Err

      implicit final override def error: Error[Err] = D.error

      final override def deserializeAny[V](self: Option[T], visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
        self match {
          case Some(value) => V.visitSome(visitor, value)
          case None => V.visitNone(visitor)
        }

    }

  implicit final val deserializerForUnit: Deserializer[Unit] = new DeserializerThrow[Unit] {
    final override def deserializeAny[V](self: Unit, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      V.visitUnit(visitor)
  }

}

trait Visitor[-V] {

  type Value

  final type Result[E] = Either[E, Value]

  def expecting(): String

  def visitBool[E: Error](self: V, value: Boolean): Result[E] = {
    void(self, value)
    Left(Error.custom(this.expecting()))
  }

  def visitByte[E: Error](self: V, value: Byte): Result[E] =
    this.visitShort(self, value.toShort)

  def visitShort[E: Error](self: V, value: Short): Result[E] =
    this.visitInt(self, value.toInt)

  def visitInt[E: Error](self: V, value: Int): Result[E] =
    this.visitLong(self, value.toLong)

  def visitLong[E: Error](self: V, value: Long): Result[E] = {
    void(self, value)
    Left(Error.custom(this.expecting()))
  }

  def visitFloat[E: Error](self: V, value: Float): Result[E] =
    this.visitDouble(self, value.toDouble)

  def visitDouble[E: Error](self: V, value: Double): Result[E] = {
    void(self, value)
    Left(Error.custom(this.expecting()))
  }

  def visitChar[E: Error](self: V, value: Char): Result[E] =
    this.visitString(self, value.toString)

  def visitString[E: Error](self: V, value: String): Result[E] = {
    void(self, value)
    Left(Error.custom(this.expecting()))
  }

  def visitBytes[E: Error](self: V, value: Array[Byte]): Result[E] = {
    void(self, value)
    Left(Error.custom(this.expecting()))
  }

  def visitNone[E: Error](self: V): Result[E] = {
    void(self)
    Left(Error.custom(this.expecting()))
  }

  def visitSome[D](self: V, deserializer: D)(implicit D: Deserializer[D]): Result[D.Err] = {
    void(self, deserializer)
    Left(Error.custom(this.expecting()))
  }

  def visitUnit[E: Error](self: V): Result[E] = {
    void(self)
    Left(Error.custom(this.expecting()))
  }

  def visitSeq[A](self: V, seq: A)(implicit A: SeqAccess[A]): Result[A.Err] = {
    void(self, seq)
    Left(Error.custom(this.expecting()))
  }

  def visitMap[A](self: V, map: A)(implicit A: MapAccess[A]): Result[A.Err] = {
    void(self, map)
    Left(Error.custom(this.expecting()))
  }

}

object Visitor {

  @inline final def apply[V](implicit ev: Visitor[V]): Visitor[V] = ev

  final class Ops[V](self: V)(implicit final val V: Visitor[V]) {

    @inline final def expecting(): String =
      V.expecting()

    @inline final def visitBool[E: Error](value: Boolean): V.Result[E] =
      V.visitBool(self, value)

    @inline final def visitByte[E: Error](value: Byte): V.Result[E] =
      V.visitByte(self, value)

    @inline final def visitShort[E: Error](value: Short): V.Result[E] =
      V.visitShort(self, value)

    @inline final def visitInt[E: Error](value: Int): V.Result[E] =
      V.visitInt(self, value)

    @inline final def visitLong[E: Error](value: Long): V.Result[E] =
      V.visitLong(self, value)

    @inline final def visitFloat[E: Error](value: Float): V.Result[E] =
      V.visitFloat(self, value)

    @inline final def visitDouble[E: Error](value: Double): V.Result[E] =
      V.visitDouble(self, value)

    @inline final def visitChar[E: Error](value: Char): V.Result[E] =
      V.visitChar(self, value)

    @inline final def visitString[E: Error](value: String): V.Result[E] =
      V.visitString(self, value)

    @inline final def visitBytes[E: Error](value: Array[Byte]): V.Result[E] =
      V.visitBytes(self, value)

    @inline final def visitNone[E: Error](): V.Result[E] =
      V.visitNone(self)

    @inline final def visitSome[D](deserializer: D)(implicit D: Deserializer[D]): V.Result[D.Err] =
      V.visitSome(self, deserializer)

    @inline final def visitUnit[E: Error](): V.Result[E] =
      V.visitUnit(self)

    @inline final def visitSeq[A](seq: A)(implicit A: SeqAccess[A]): V.Result[A.Err] =
      V.visitSeq(self, seq)

    @inline final def visitMap[A](map: A)(implicit A: MapAccess[A]): V.Result[A.Err] =
      V.visitMap(self, map)

  }

}

trait SeqAccess[A] {

  type Err

  final type Result[T] = Either[Err, T]

  implicit def error: Error[Err]

  def nextElement[T: Deserialize](self: A): Result[Option[T]]

}

trait SeqAccessThrow[A] extends SeqAccess[A] {

  final override type Err = Throwable

  implicit final override def error: Error[Err] = Error.errorForThrowable

}

object SeqAccess {

  @inline final def apply[A](implicit ev: SeqAccess[A]): SeqAccess[A] = ev

}

trait MapAccess[A] {

  type Err

  final type Result[T] = Either[Err, T]

  implicit def error: Error[Err]

  def nextKey[K: Deserialize](self: A): Result[Option[K]]

  def nextValue[V: Deserialize](self: A): Result[V]

  def nextEntry[K: Deserialize, V: Deserialize](self: A): Result[Option[(K, V)]] =
    this.nextKey[K](self) match {
      case Right(Some(key)) =>
        this.nextValue[V](self) match {
          case Right(value) => Right(Some((key, value)))
          case Left(err) => Left(err)
        }
      case Right(None) => Right(None)
      case Left(err) => Left(err)
    }

}

trait MapAccessThrow[A] extends MapAccess[A] {

  final override type Err = Throwable

  implicit final override def error: Error[Err] = Error.errorForThrowable

}

object MapAccess {

  @inline final def apply[A](implicit ev: MapAccess[A]): MapAccess[A] = ev

}
