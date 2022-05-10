package scerde

trait Serialize[T] {
  def serialize[S: Serializer](value: T, serializer: S): Serializer[S]#Result
}

object Serialize extends SerializeInstances {

  @inline def apply[T](implicit ev: Serialize[T]): Serialize[T] = ev

  final class SerializeOps[T](self: T)(implicit ev: Serialize[T]) {

    def serialize[S: Serializer](serializer: S): Serializer[S]#Result =
      ev.serialize(self, serializer)

  }

}

abstract private[scerde] class SerializeInstances {

  implicit val serializeForBool: Serialize[Boolean] = new Serialize[Boolean] {
    def serialize[S: Serializer](value: Boolean, serializer: S): Serializer[S]#Result =
      serializer.serializeBool(value)
  }

  implicit val serializeForByte: Serialize[Byte] = new Serialize[Byte] {
    def serialize[S: Serializer](value: Byte, serializer: S): Serializer[S]#Result =
      serializer.serializeByte(value)
  }

  implicit val serializeForShort: Serialize[Short] = new Serialize[Short] {
    def serialize[S: Serializer](value: Short, serializer: S): Serializer[S]#Result =
      serializer.serializeShort(value)
  }

  implicit val serializeForInt: Serialize[Int] = new Serialize[Int] {
    def serialize[S: Serializer](value: Int, serializer: S): Serializer[S]#Result =
      serializer.serializeInt(value)
  }

  implicit val serializeForLong: Serialize[Long] = new Serialize[Long] {
    def serialize[S: Serializer](value: Long, serializer: S): Serializer[S]#Result =
      serializer.serializeLong(value)
  }

  implicit val serializeForFloat: Serialize[Float] = new Serialize[Float] {
    def serialize[S: Serializer](value: Float, serializer: S): Serializer[S]#Result =
      serializer.serializeFloat(value)
  }

  implicit val serializeForDouble: Serialize[Double] = new Serialize[Double] {
    def serialize[S: Serializer](value: Double, serializer: S): Serializer[S]#Result =
      serializer.serializeDouble(value)
  }

  implicit val serializeForChar: Serialize[Char] = new Serialize[Char] {
    def serialize[S: Serializer](value: Char, serializer: S): Serializer[S]#Result =
      serializer.serializeChar(value)
  }

  implicit val serializeForString: Serialize[String] = new Serialize[String] {
    def serialize[S: Serializer](value: String, serializer: S): Serializer[S]#Result =
      serializer.serializeString(value)
  }

  implicit val serializeForBytes: Serialize[Array[Byte]] = new Serialize[Array[Byte]] {
    def serialize[S: Serializer](value: Array[Byte], serializer: S): Serializer[S]#Result =
      serializer.serializeBytes(value)
  }

  implicit def serializeForOption[T](implicit ev: Serialize[T]): Serialize[Option[T]] =
    new Serialize[Option[T]] {
      def serialize[S: Serializer](value: Option[T], serializer: S): Serializer[S]#Result =
        value match {
          case Some(v) => serializer.serializeSome(v)
          case None => serializer.serializeNone()
        }
    }

  implicit val serializeForUnit: Serialize[Unit] = new Serialize[Unit] {
    def serialize[S: Serializer](value: Unit, serializer: S): Serializer[S]#Result =
      serializer.serializeUnit()
  }

}

trait Serializer[S] {

  type Ok = Unit
  type Err = Throwable

  type SerializeSeq
  type SerializeMap

  final type Result = Either[Err, Ok]

  @inline implicit def error: Error[Throwable] = Error.errorForThrowable

  @inline final def serialize[T](self: S, value: T)(implicit ev: Serialize[T]): Result =
    ev.serialize(value, self)(this)

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

object Serializer extends SerializerInstances {

  @inline def apply[S](implicit ev: Serializer[S]): Serializer[S] = ev

  final class SerializerOps[S](self: S)(implicit ev: Serializer[S]) {

    type Err = Serializer[S]#Err
    type Result = Serializer[S]#Result

    @inline def serialize[T: Serialize](value: T): Result =
      ev.serialize(self, value)

    @inline def serializeBool(value: Boolean): Result =
      ev.serializeBool(self, value)

    @inline def serializeByte(value: Byte): Result =
      ev.serializeByte(self, value)

    @inline def serializeShort(value: Short): Result =
      ev.serializeShort(self, value)

    @inline def serializeInt(value: Int): Result =
      ev.serializeInt(self, value)

    @inline def serializeLong(value: Long): Result =
      ev.serializeLong(self, value)

    @inline def serializeFloat(value: Float): Result =
      ev.serializeFloat(self, value)

    @inline def serializeDouble(value: Double): Result =
      ev.serializeDouble(self, value)

    @inline def serializeChar(value: Char): Result =
      ev.serializeChar(self, value)

    @inline def serializeString(value: String): Result =
      ev.serializeString(self, value)

    @inline def serializeBytes(value: Array[Byte]): Result =
      ev.serializeBytes(self, value)

    @inline def serializeNone(): Result =
      ev.serializeNone(self)

    @inline def serializeSome[T: Serialize](value: T): Result =
      ev.serializeSome(self, value)

    @inline def serializeUnit(): Result =
      ev.serializeUnit(self)

    @inline def serializeSeq(len: Option[Int]): Either[Err, Serializer[S]#SerializeSeq] =
      ev.serializeSeq(self, len)

    @inline def serializeMap(len: Option[Int]): Either[Err, Serializer[S]#SerializeMap] =
      ev.serializeMap(self, len)

  }

}

abstract private[scerde] class SerializerInstances extends SerializerPlatformInstances
