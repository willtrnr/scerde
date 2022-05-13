package scerde
package ser

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets.UTF_8

private[scerde] trait PlatformSerializerInstances {

  implicit final val serializerForByteBuffer: Serializer[ByteBuffer] = new SerializerThrow[ByteBuffer] {

    final override def serializeBool(self: ByteBuffer, value: Boolean): Result = tri {
      self.put((if (value) 1 else 0).toByte)
    }

    final override def serializeByte(self: ByteBuffer, value: Byte): Result = tri {
      self.put(value)
    }

    final override def serializeShort(self: ByteBuffer, value: Short): Result = tri {
      self.putShort(value)
    }

    final override def serializeInt(self: ByteBuffer, value: Int): Result = tri {
      self.putInt(value)
    }

    final override def serializeLong(self: ByteBuffer, value: Long): Result = tri {
      self.putLong(value)
    }

    final override def serializeFloat(self: ByteBuffer, value: Float): Result = tri {
      self.putFloat(value)
    }

    final override def serializeDouble(self: ByteBuffer, value: Double): Result = tri {
      self.putDouble(value)
    }

    final override def serializeChar(self: ByteBuffer, value: Char): Result = tri {
      self.putChar(value)
    }

    final override def serializeString(self: ByteBuffer, value: String): Result = tri {
      val enc = value.getBytes(UTF_8)
      self.putInt(enc.length)
      self.put(enc)
    }

    final override def serializeBytes(self: ByteBuffer, value: Array[Byte]): Result = tri {
      self.putInt(value.length)
      self.put(value)
    }

    final override def serializeNone(self: ByteBuffer): Result = tri {
      self.put(0.toByte)
    }

    final override def serializeSome[T: Serialize](self: ByteBuffer, value: T): Result = etri {
      self.put(1.toByte)
      this.serialize(self, value)
    }

    final override def serializeUnit(self: ByteBuffer): Result = {
      val _ = self
      Right(())
    }

    final override def serializeSeq(self: ByteBuffer, len: Option[Int]): Either[Err, SerializeSeq] = ???

    final override def serializeMap(self: ByteBuffer, len: Option[Int]): Either[Err, SerializeMap] = ???

  }

}
