package scerde

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets.UTF_8

abstract private[scerde] class SerializerPlatformInstances {

  implicit val serializerForByteBuffer: Serializer[ByteBuffer] = new Serializer[ByteBuffer] {

    def serializeBool(self: ByteBuffer, value: Boolean): Result = tri {
      self.put((if (value) 1 else 0).toByte)
    }

    def serializeByte(self: ByteBuffer, value: Byte): Result = tri {
      self.put(value)
    }

    def serializeShort(self: ByteBuffer, value: Short): Result = tri {
      self.putShort(value)
    }

    def serializeInt(self: ByteBuffer, value: Int): Result = tri {
      self.putInt(value)
    }

    def serializeLong(self: ByteBuffer, value: Long): Result = tri {
      self.putLong(value)
    }

    def serializeFloat(self: ByteBuffer, value: Float): Result = tri {
      self.putFloat(value)
    }

    def serializeDouble(self: ByteBuffer, value: Double): Result = tri {
      self.putDouble(value)
    }

    def serializeChar(self: ByteBuffer, value: Char): Result = tri {
      self.putChar(value)
    }

    def serializeString(self: ByteBuffer, value: String): Result = tri {
      val enc = value.getBytes(UTF_8)
      self.putInt(enc.length)
      self.put(enc)
    }

    def serializeBytes(self: ByteBuffer, value: Array[Byte]): Result = tri {
      self.putInt(value.length)
      self.put(value)
    }

    def serializeNone(self: ByteBuffer): Result = tri {
      self.put(0.toByte)
    }

    def serializeSome[T: Serialize](self: ByteBuffer, value: T): Result = etri {
      self.put(1.toByte)
      value.serialize(self)(this)
    }

    def serializeUnit(self: ByteBuffer): Result = {
      val _ = self
      Right(())
    }

    def serializeSeq(self: ByteBuffer, len: Option[Int]): Either[Err, SerializeSeq] = ???

    def serializeMap(self: ByteBuffer, len: Option[Int]): Either[Err, SerializeMap] = ???

  }

}
