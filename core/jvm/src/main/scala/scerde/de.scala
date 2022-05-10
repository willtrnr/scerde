package scerde

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets.UTF_8

abstract private[scerde] class DeserializerPlatformInstances {

  implicit val deserializerForByteBuffer: Deserializer[ByteBuffer] = new Deserializer[ByteBuffer] {

    override def deserializeAny[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = {
      void(self, visitor)
      Left(new UnsupportedOperationException("non self-describing format"))
    }

    override def deserializeBool[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = etri {
      visitor.visitBool(self.get() != 0)
    }

    override def deserializeByte[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = etri {
      visitor.visitByte(self.get())
    }

    override def deserializeShort[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = etri {
      visitor.visitShort(self.getShort())
    }

    override def deserializeInt[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = etri {
      visitor.visitInt(self.getInt())
    }

    override def deserializeLong[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = etri {
      visitor.visitLong(self.getLong())
    }

    override def deserializeFloat[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = etri {
      visitor.visitFloat(self.getFloat())
    }

    override def deserializeDouble[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = etri {
      visitor.visitDouble(self.getDouble())
    }

    override def deserializeChar[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = etri {
      visitor.visitChar(self.getChar())
    }

    override def deserializeString[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = etri {
      val len = self.getInt()
      if (len < 0) {
        Left(new IllegalArgumentException("invalid string length: " + len))
      } else if (len == 0) {
        visitor.visitString("")
      } else {
        val buf = Array.ofDim[Byte](len)
        self.get(buf)
        visitor.visitString(new String(buf, UTF_8))
      }
    }

    override def deserializeBytes[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = etri {
      val len = self.getInt()
      if (len < 0) {
        Left(new IllegalArgumentException("invalid byte array length: " + len))
      } else if (len == 0) {
        visitor.visitBytes(Array.emptyByteArray)
      } else {
        val buf = Array.ofDim[Byte](len)
        self.get(buf)
        visitor.visitBytes(buf)
      }
    }

    override def deserializeOption[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = etri {
      if (self.get() != 0) {
        visitor.visitSome(self)(this)
      } else {
        visitor.visitNone()
      }
    }

    override def deserializeUnit[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = {
      void(self)
      visitor.visitUnit()
    }

    override def deserializeSeq[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = ???

    override def deserializeMap[V: Visitor](self: ByteBuffer, visitor: V): Result[Visitor[V]#Value] = ???

  }

}
