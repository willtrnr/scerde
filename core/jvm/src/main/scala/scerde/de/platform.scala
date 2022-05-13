package scerde
package de

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets.UTF_8

private[scerde] trait PlatformDeserializerInstances {

  implicit final val deserializerForByteBuffer: Deserializer[ByteBuffer] = new DeserializerThrow[ByteBuffer] {

    final override def deserializeAny[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      Left(new UnsupportedOperationException("non self-describing format"))

    final override def deserializeBool[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      etri {
        V.visitBool(visitor, self.get() != 0)
      }

    final override def deserializeByte[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      etri {
        V.visitByte(visitor, self.get())
      }

    final override def deserializeShort[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      etri {
        V.visitShort(visitor, self.getShort())
      }

    final override def deserializeInt[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] = etri {
      V.visitInt(visitor, self.getInt())
    }

    final override def deserializeLong[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      etri {
        V.visitLong(visitor, self.getLong())
      }

    final override def deserializeFloat[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      etri {
        V.visitFloat(visitor, self.getFloat())
      }

    final override def deserializeDouble[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      etri {
        V.visitDouble(visitor, self.getDouble())
      }

    final override def deserializeChar[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      etri {
        V.visitChar(visitor, self.getChar())
      }

    final override def deserializeString[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      etri {
        val len = self.getInt()
        if (len < 0) {
          Left(new IllegalArgumentException("invalid string length: " + len))
        } else if (len == 0) {
          V.visitString(visitor, "")
        } else {
          val buf = Array.ofDim[Byte](len)
          self.get(buf)
          V.visitString(visitor, new String(buf, UTF_8))
        }
      }

    final override def deserializeBytes[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      etri {
        val len = self.getInt()
        if (len < 0) {
          Left(new IllegalArgumentException("invalid byte array length: " + len))
        } else if (len == 0) {
          V.visitBytes(visitor, Array.emptyByteArray)
        } else {
          val buf = Array.ofDim[Byte](len)
          self.get(buf)
          V.visitBytes(visitor, buf)
        }
      }

    final override def deserializeOption[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      etri {
        if (self.get() != 0) {
          V.visitSome(visitor, self)(this)
        } else {
          V.visitNone(visitor)
        }
      }

    final override def deserializeUnit[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] =
      V.visitUnit(visitor)

    final override def deserializeSeq[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] = ???

    final override def deserializeMap[V](self: ByteBuffer, visitor: V)(implicit V: Visitor[V]): Result[V.Value] = ???

  }

}
