package scerde

trait Deserialize[T] {
  def deserialize[D: Deserializer](deserializer: D): Deserializer[D]#Result[T]
}

object Deserialize {

  @inline def apply[T](implicit ev: Deserialize[T]): Deserialize[T] = ev

}

trait Deserializer[D] {

  type Err = Throwable

  final type Result[T] = Either[Err, T]

  @inline implicit def error: Error[Throwable] = Error.errorForThrowable

  @inline final def deserialize[T](self: D)(implicit ev: Deserialize[T]): Result[T] =
    ev.deserialize(self)(this)

  def deserializeAny[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value]

  def deserializeBool[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeByte[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeShort[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeInt[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeLong[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeFloat[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeDouble[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeChar[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeString[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeBytes[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeOption[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeUnit[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeSeq[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeMap[V: Visitor](self: D, visitor: V): Result[Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

}

object Deserializer extends DeserializerInstances {

  @inline def apply[D](implicit ev: Deserializer[D]): Deserializer[D] = ev

  final class DeserializerOps[D](self: D)(implicit ev: Deserializer[D]) {

    type Result[T] = Deserializer[D]#Result[T]

    @inline def deserialize[T: Deserialize]: Result[T] =
      ev.deserialize(self)

    @inline def deserializeAny[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeBool[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeBool(self, visitor)

    @inline def deserializeByte[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeShort[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeInt[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeLong[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeFloat[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeDouble[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeChar[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeString[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeBytes[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeOption[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeUnit[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeSeq[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeMap[V: Visitor](visitor: V): Result[Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

  }

}

abstract private[scerde] class DeserializerInstances extends DeserializerPlatformInstances {

  implicit val deserializerForBool: Deserializer[Boolean] = new Deserializer[Boolean] {
    override def deserializeAny[V: Visitor](self: Boolean, visitor: V): Result[Visitor[V]#Value] =
      visitor.visitBool(self)
  }

  implicit val deserializerForByte: Deserializer[Byte] = new Deserializer[Byte] {
    override def deserializeAny[V: Visitor](self: Byte, visitor: V): Result[Visitor[V]#Value] =
      visitor.visitByte(self)
  }

  implicit val deserializerForShort: Deserializer[Short] = new Deserializer[Short] {
    override def deserializeAny[V: Visitor](self: Short, visitor: V): Result[Visitor[V]#Value] =
      visitor.visitShort(self)
  }

  implicit val deserializerForInt: Deserializer[Int] = new Deserializer[Int] {
    override def deserializeAny[V: Visitor](self: Int, visitor: V): Result[Visitor[V]#Value] =
      visitor.visitInt(self)
  }

  implicit val deserializerForLong: Deserializer[Long] = new Deserializer[Long] {
    override def deserializeAny[V: Visitor](self: Long, visitor: V): Result[Visitor[V]#Value] =
      visitor.visitLong(self)
  }

  implicit val deserializerForFloat: Deserializer[Float] = new Deserializer[Float] {
    override def deserializeAny[V: Visitor](self: Float, visitor: V): Result[Visitor[V]#Value] =
      visitor.visitFloat(self)
  }

  implicit val deserializerForDouble: Deserializer[Double] = new Deserializer[Double] {
    override def deserializeAny[V: Visitor](self: Double, visitor: V): Result[Visitor[V]#Value] =
      visitor.visitDouble(self)
  }

  implicit val deserializerForChar: Deserializer[Char] = new Deserializer[Char] {
    override def deserializeAny[V: Visitor](self: Char, visitor: V): Result[Visitor[V]#Value] =
      visitor.visitChar(self)
  }

  implicit val deserializerForString: Deserializer[String] = new Deserializer[String] {
    override def deserializeAny[V: Visitor](self: String, visitor: V): Result[Visitor[V]#Value] =
      visitor.visitString(self)
  }

  implicit val deserializerForBytes: Deserializer[Array[Byte]] = new Deserializer[Array[Byte]] {
    override def deserializeAny[V: Visitor](self: Array[Byte], visitor: V): Result[Visitor[V]#Value] =
      visitor.visitBytes(self)
  }

  implicit def deserializerForOption[T](implicit ev: Deserializer[T]): Deserializer[Option[T]] =
    new Deserializer[Option[T]] {

      override type Err = Deserializer[T]#Err

      override def error: Error[Err] = ev.error

      override def deserializeAny[V: Visitor](self: Option[T], visitor: V): Result[Visitor[V]#Value] =
        self match {
          case Some(value) => visitor.visitSome(value)
          case None => visitor.visitNone[Err]()
        }

    }

  implicit val deserializerForUnit: Deserializer[Unit] = new Deserializer[Unit] {
    override def deserializeAny[V: Visitor](self: Unit, visitor: V): Result[Visitor[V]#Value] =
      visitor.visitUnit()
  }

}

trait Visitor[V] {

  type Value

  final type Result[E] = Either[E, Value]

  def expecting(): String

  def visitBool[E: Error](self: V, value: Boolean): Result[E] = {
    void(self, value)
    Left(Error[E].custom(this.expecting()))
  }

  def visitByte[E: Error](self: V, value: Byte): Result[E] =
    this.visitShort(self, value.toShort)

  def visitShort[E: Error](self: V, value: Short): Result[E] =
    this.visitInt(self, value.toInt)

  def visitInt[E: Error](self: V, value: Int): Result[E] =
    this.visitLong(self, value.toLong)

  def visitLong[E: Error](self: V, value: Long): Result[E] = {
    void(self, value)
    Left(Error[E].custom(this.expecting()))
  }

  def visitFloat[E: Error](self: V, value: Float): Result[E] =
    this.visitDouble(self, value.toDouble)

  def visitDouble[E: Error](self: V, value: Double): Result[E] = {
    void(self, value)
    Left(Error[E].custom(this.expecting()))
  }

  def visitChar[E: Error](self: V, value: Char): Result[E] =
    this.visitString(self, value.toString)

  def visitString[E: Error](self: V, value: String): Result[E] = {
    void(self, value)
    Left(Error[E].custom(this.expecting()))
  }

  def visitBytes[E: Error](self: V, value: Array[Byte]): Result[E] = {
    void(self, value)
    Left(Error[E].custom(this.expecting()))
  }

  def visitNone[E: Error](self: V): Result[E] = {
    void(self)
    Left(Error[E].custom(this.expecting()))
  }

  def visitSome[D](self: V, deserializer: D)(implicit ev: Deserializer[D]): Result[Deserializer[D]#Err] = {
    import ev.error
    void(self, deserializer)
    Left(Error[ev.Err].custom(this.expecting()))
  }

  def visitUnit[E: Error](self: V): Result[E] = {
    void(self)
    Left(Error[E].custom(this.expecting()))
  }

  def visitSeq[A](self: V, seq: A)(implicit ev: SeqAccess[A]): Result[SeqAccess[A]#Err] = {
    import ev.error
    void(self, seq)
    Left(Error[ev.Err].custom(this.expecting()))
  }

  def visitMap[A](self: V, map: A)(implicit ev: MapAccess[A]): Result[MapAccess[A]#Err] = {
    import ev.error
    void(self, map)
    Left(Error[ev.Err].custom(this.expecting()))
  }

}

object Visitor {

  @inline def apply[V](implicit ev: Visitor[V]): Visitor[V] = ev

  final class VisitorOps[V](self: V)(implicit ev: Visitor[V]) {

    type Result[E] = Visitor[V]#Result[E]

    @inline def expecting(): String =
      ev.expecting()

    @inline def visitBool[E: Error](value: Boolean): Result[E] =
      ev.visitBool(self, value)

    @inline def visitByte[E: Error](value: Byte): Result[E] =
      ev.visitByte(self, value)

    @inline def visitShort[E: Error](value: Short): Result[E] =
      ev.visitShort(self, value)

    @inline def visitInt[E: Error](value: Int): Result[E] =
      ev.visitInt(self, value)

    @inline def visitLong[E: Error](value: Long): Result[E] =
      ev.visitLong(self, value)

    @inline def visitFloat[E: Error](value: Float): Result[E] =
      ev.visitFloat(self, value)

    @inline def visitDouble[E: Error](value: Double): Result[E] =
      ev.visitDouble(self, value)

    @inline def visitChar[E: Error](value: Char): Result[E] =
      ev.visitChar(self, value)

    @inline def visitString[E: Error](value: String): Result[E] =
      ev.visitString(self, value)

    @inline def visitBytes[E: Error](value: Array[Byte]): Result[E] =
      ev.visitBytes(self, value)

    @inline def visitNone[E: Error](): Result[E] =
      ev.visitNone(self)

    @inline def visitSome[D: Deserializer](deserializer: D): Result[Deserializer[D]#Err] =
      ev.visitSome(self, deserializer)

    @inline def visitUnit[E: Error](): Result[E] =
      ev.visitUnit(self)

    @inline def visitSeq[A: SeqAccess](seq: A): Result[SeqAccess[A]#Err] =
      ev.visitSeq(self, seq)

    @inline def visitMap[A: MapAccess](map: A): Result[MapAccess[A]#Err] =
      ev.visitMap(self, map)

  }

}

trait SeqAccess[A] {

  type Err = Throwable

  final type Result[T] = Either[Err, T]

  @inline implicit def error: Error[Err] = Error.errorForThrowable

  def nextElement[T: Deserialize](self: A): Result[Option[T]]
}

object SeqAccess {

  @inline def apply[A](implicit ev: SeqAccess[A]): SeqAccess[A] = ev

}

trait MapAccess[A] {

  type Err = Throwable

  final type Result[T] = Either[Err, T]

  @inline implicit def error: Error[Err] = Error.errorForThrowable

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

object MapAccess {

  @inline def apply[A](implicit ev: MapAccess[A]): MapAccess[A] = ev

}
