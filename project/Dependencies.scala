import sbt._
import sbt.Keys._

import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbtcrossproject.CrossPlugin.autoImport.crossProjectPlatform
import scalafix.sbt.ScalafixPlugin.{autoImport => Scalafix}

object Dependencies {

  private def ifScala211[A](if211: => A, orElse: => A): Def.Initialize[A] = Def.setting {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, y)) if y <= 11 =>
        if211
      case _ =>
        orElse
    }
  }

  private def semver[A](version: String)(f: (Int, Int, Int) => A): A =
    version.split('.') match {
      case Array(x, y, z) =>
        f(x.toInt, y.toInt, z.toInt)
    }

  object V {

    val scala211 = "2.11.12"
    val scala212 = "2.12.14"
    val scala213 = "2.13.6"
    val scala3 = "3.1.2"

    val scalatest = ifScala211("3.2.3", "3.2.9")
    val scalacheck = ifScala211("1.15.2", "1.15.4")

  }

  val versionsJvm = Def.settings(
    scalaVersion := V.scala213,
    crossScalaVersions := Seq(V.scala211, V.scala212, V.scala213, V.scala3),
  )

  val versionsJs = Def.settings(
    scalaVersion := V.scala213,
    crossScalaVersions := Seq(V.scala212, V.scala213, V.scala3),
  )

  val versionsNative = Def.settings(
    scalaVersion := V.scala213,
    crossScalaVersions := Seq(V.scala211, V.scala212, V.scala213),
  )

  object Compile {

    val scala2Macros = Def.setting {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, _)) =>
          Seq("org.scala-lang" % "scala-reflect" % scalaVersion.value)
        case _ =>
          Seq.empty
      }
    }

    object Compiler {

      val betterMonadicFor = compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
      val kindProjector = compilerPlugin("org.typelevel" % "kind-projector" % "0.13.0" cross CrossVersion.full)
      val semanticdb = compilerPlugin(Scalafix.scalafixSemanticdb)

    }

    object Test {

      val scalatest = Def.setting("org.scalatest" %%% "scalatest" % V.scalatest.value % "test")
      val scalatestCheck = Def.setting("org.scalatestplus" %%% ("scalacheck-" + semver(V.scalacheck.value)((x, y, _) => x + "-" + y)) % (V.scalatest.value + ".0") % "test")
      val scalacheck = Def.setting("org.scalacheck" %%% "scalacheck" % V.scalacheck.value % "test")

    }

  }

  import Compile._

  val l = libraryDependencies

  val default = l ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((3, _)) =>
        Seq.empty
      case _ =>
        Seq(
          Compiler.betterMonadicFor,
          Compiler.kindProjector,
          Compiler.semanticdb,
        )
    }
  }

  val core = l ++= Seq()

}
