package scerde

trait Error[E] {
  def custom(msg: String): E
}

object Error extends ErrorInstances {

  @inline def apply[E](implicit ev: Error[E]): Error[E] = ev

}

abstract private[scerde] class ErrorInstances {

  implicit val errorForThrowable: Error[Throwable] = new Error[Throwable] {
    override def custom(msg: String): Throwable = new RuntimeException(msg)
  }

  implicit val errorForString: Error[String] = new Error[String] {
    override def custom(msg: String): String = msg
  }

}

trait Deserialize[T] {
  def deserialize[D: Deserializer](deserializer: D): Either[Deserializer[D]#Err, T]
}

object Deserialize {

  @inline def apply[T](implicit ev: Deserialize[T]): Deserialize[T] = ev

}

trait Deserializer[D] {

  type Err = Throwable

  @inline implicit def error: Error[Throwable] = Error.errorForThrowable

  @inline final def deserialize[T](self: D)(implicit ev: Deserialize[T]): Either[Err, T] =
    ev.deserialize(self)(this)

  def deserializeAny[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value]

  def deserializeBool[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeByte[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeShort[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeInt[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeLong[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeFloat[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeDouble[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeChar[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeString[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeBytes[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeOption[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeUnit[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeSeq[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

  def deserializeMap[V: Visitor](self: D, visitor: V): Either[Err, Visitor[V]#Value] =
    this.deserializeAny(self, visitor)

}

object Deserializer extends DeserializerInstances {

  @inline def apply[D](implicit ev: Deserializer[D]): Deserializer[D] = ev

  final class DeserializerOps[D](self: D)(implicit ev: Deserializer[D]) {

    type Err = Deserializer[D]#Err

    @inline def deserialize[T: Deserialize]: Either[Err, T] =
      ev.deserialize(self)

    @inline def deserializeAny[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeBool[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeBool(self, visitor)

    @inline def deserializeByte[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeShort[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeInt[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeLong[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeFloat[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeDouble[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeChar[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeString[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeBytes[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeOption[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeUnit[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeSeq[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

    @inline def deserializeMap[V: Visitor](visitor: V): Either[Err, Visitor[V]#Value] =
      ev.deserializeAny(self, visitor)

  }

}

abstract private[scerde] class DeserializerInstances {

  implicit val deserializerForBool: Deserializer[Boolean] = new Deserializer[Boolean] {
    override def deserializeAny[V: Visitor](self: Boolean, visitor: V): Either[Err, Visitor[V]#Value] =
      Visitor[V].visitBool[Err](visitor, self)
  }

  implicit val deserializerForByte: Deserializer[Byte] = new Deserializer[Byte] {
    override def deserializeAny[V: Visitor](self: Byte, visitor: V): Either[Err, Visitor[V]#Value] =
      Visitor[V].visitByte[Err](visitor, self)
  }

  implicit val deserializerForShort: Deserializer[Short] = new Deserializer[Short] {
    override def deserializeAny[V: Visitor](self: Short, visitor: V): Either[Err, Visitor[V]#Value] =
      Visitor[V].visitShort[Err](visitor, self)
  }

  implicit val deserializerForInt: Deserializer[Int] = new Deserializer[Int] {
    override def deserializeAny[V: Visitor](self: Int, visitor: V): Either[Err, Visitor[V]#Value] =
      Visitor[V].visitInt[Err](visitor, self)
  }

  implicit val deserializerForLong: Deserializer[Long] = new Deserializer[Long] {
    override def deserializeAny[V: Visitor](self: Long, visitor: V): Either[Err, Visitor[V]#Value] =
      Visitor[V].visitLong[Err](visitor, self)
  }

  implicit val deserializerForFloat: Deserializer[Float] = new Deserializer[Float] {
    override def deserializeAny[V: Visitor](self: Float, visitor: V): Either[Err, Visitor[V]#Value] =
      Visitor[V].visitFloat[Err](visitor, self)
  }

  implicit val deserializerForDouble: Deserializer[Double] = new Deserializer[Double] {
    override def deserializeAny[V: Visitor](self: Double, visitor: V): Either[Err, Visitor[V]#Value] =
      Visitor[V].visitDouble[Err](visitor, self)
  }

  implicit val deserializerForChar: Deserializer[Char] = new Deserializer[Char] {
    override def deserializeAny[V: Visitor](self: Char, visitor: V): Either[Err, Visitor[V]#Value] =
      Visitor[V].visitChar[Err](visitor, self)
  }

  implicit val deserializerForString: Deserializer[String] = new Deserializer[String] {
    override def deserializeAny[V: Visitor](self: String, visitor: V): Either[Err, Visitor[V]#Value] =
      Visitor[V].visitString[Err](visitor, self)
  }

  implicit val deserializerForBytes: Deserializer[Array[Byte]] = new Deserializer[Array[Byte]] {
    override def deserializeAny[V: Visitor](self: Array[Byte], visitor: V): Either[Err, Visitor[V]#Value] =
      Visitor[V].visitBytes[Err](visitor, self)
  }

  implicit def deserializerForOption[T](implicit ev: Deserializer[T]): Deserializer[Option[T]] =
    new Deserializer[Option[T]] {
      override type Err = Deserializer[T]#Err

      override def error: Error[Err] = ev.error

      override def deserializeAny[V: Visitor](self: Option[T], visitor: V): Either[Err, Visitor[V]#Value] =
        self match {
          case Some(value) => Visitor[V].visitSome(visitor, value)
          case None => Visitor[V].visitNone[Err](visitor)
        }
    }

  implicit val deserializerForUnit: Deserializer[Unit] = new Deserializer[Unit] {
    override def deserializeAny[V: Visitor](self: Unit, visitor: V): Either[Err, Visitor[V]#Value] =
      Visitor[V].visitUnit[Err](visitor)
  }

}

trait Visitor[V] {

  type Value

  def expecting(): String

  def visitBool[E: Error](self: V, value: Boolean): Either[E, Value] = {
    void(self, value)
    Left(Error[E].custom(this.expecting()))
  }

  def visitByte[E: Error](self: V, value: Byte): Either[E, Value] =
    this.visitShort(self, value.toShort)

  def visitShort[E: Error](self: V, value: Short): Either[E, Value] =
    this.visitInt(self, value.toInt)

  def visitInt[E: Error](self: V, value: Int): Either[E, Value] =
    this.visitLong(self, value.toLong)

  def visitLong[E: Error](self: V, value: Long): Either[E, Value] = {
    void(self, value)
    Left(Error[E].custom(this.expecting()))
  }

  def visitFloat[E: Error](self: V, value: Float): Either[E, Value] =
    this.visitDouble(self, value.toDouble)

  def visitDouble[E: Error](self: V, value: Double): Either[E, Value] = {
    void(self, value)
    Left(Error[E].custom(this.expecting()))
  }

  def visitChar[E: Error](self: V, value: Char): Either[E, Value] =
    this.visitString(self, value.toString)

  def visitString[E: Error](self: V, value: String): Either[E, Value] = {
    void(self, value)
    Left(Error[E].custom(this.expecting()))
  }

  def visitBytes[E: Error](self: V, value: Array[Byte]): Either[E, Value] = {
    void(self, value)
    Left(Error[E].custom(this.expecting()))
  }

  def visitNone[E: Error](self: V): Either[E, Value] = {
    void(self)
    Left(Error[E].custom(this.expecting()))
  }

  def visitSome[D](self: V, deserializer: D)(implicit ev: Deserializer[D]): Either[Deserializer[D]#Err, Value] = {
    import ev.error
    void(self, deserializer)
    Left(Error[ev.Err].custom(this.expecting()))
  }

  def visitUnit[E: Error](self: V): Either[E, Value] = {
    void(self)
    Left(Error[E].custom(this.expecting()))
  }

  def visitSeq[A](self: V, seq: A)(implicit ev: SeqAccess[A]): Either[SeqAccess[A]#Err, Value] = {
    import ev.error
    void(self, seq)
    Left(Error[ev.Err].custom(this.expecting()))
  }

  def visitMap[A](self: V, map: A)(implicit ev: MapAccess[A]): Either[MapAccess[A]#Err, Value] = {
    import ev.error
    void(self, map)
    Left(Error[ev.Err].custom(this.expecting()))
  }

}

object Visitor {

  @inline def apply[V](implicit ev: Visitor[V]): Visitor[V] = ev

  final class VisitorOps[V](self: V)(implicit ev: Visitor[V]) {

    type Value = Visitor[V]#Value

    @inline def expecting(): String =
      ev.expecting()

    @inline def visitBool[E: Error](value: Boolean): Either[E, Value] =
      ev.visitBool(self, value)

    @inline def visitByte[E: Error](value: Byte): Either[E, Value] =
      ev.visitByte(self, value)

    @inline def visitShort[E: Error](value: Short): Either[E, Value] =
      ev.visitShort(self, value)

    @inline def visitInt[E: Error](value: Int): Either[E, Value] =
      ev.visitInt(self, value)

    @inline def visitLong[E: Error](value: Long): Either[E, Value] =
      ev.visitLong(self, value)

    @inline def visitFloat[E: Error](value: Float): Either[E, Value] =
      ev.visitFloat(self, value)

    @inline def visitDouble[E: Error](value: Double): Either[E, Value] =
      ev.visitDouble(self, value)

    @inline def visitChar[E: Error](value: Char): Either[E, Value] =
      ev.visitChar(self, value)

    @inline def visitString[E: Error](value: String): Either[E, Value] =
      ev.visitString(self, value)

    @inline def visitBytes[E: Error](value: Array[Byte]): Either[E, Value] =
      ev.visitBytes(self, value)

    @inline def visitNone[E: Error](): Either[E, Value] =
      ev.visitNone(self)

    @inline def visitSome[D: Deserializer](deserializer: D): Either[Deserializer[D]#Err, Value] =
      ev.visitSome(self, deserializer)

    @inline def visitUnit[E: Error](): Either[E, Value] =
      ev.visitUnit(self)

    @inline def visitSeq[A: SeqAccess](seq: A): Either[SeqAccess[A]#Err, Value] =
      ev.visitSeq(self, seq)

    @inline def visitMap[A: MapAccess](map: A): Either[MapAccess[A]#Err, Value] =
      ev.visitMap(self, map)

  }

}

trait SeqAccess[A] {

  type Err = Throwable

  @inline implicit def error: Error[Err] = Error.errorForThrowable

  def nextElement[T: Deserialize](self: A): Either[Err, Option[T]]
}

object SeqAccess {

  @inline def apply[A](implicit ev: SeqAccess[A]): SeqAccess[A] = ev

}

trait MapAccess[A] {

  type Err = Throwable

  @inline implicit def error: Error[Err] = Error.errorForThrowable

  def nextKey[K: Deserialize](self: A): Either[Err, Option[K]]

  def nextValue[V: Deserialize](self: A): Either[Err, V]

  def nextEntry[K: Deserialize, V: Deserialize](self: A): Either[Err, Option[(K, V)]] =
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
