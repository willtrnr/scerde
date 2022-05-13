import sbt._
import sbt.Keys._
import sbt.librarymanagement.{MavenRepository, ModuleFilter, URLRepository}

import ch.epfl.scala.sbtmissinglink.MissingLinkPlugin.autoImport.{IgnoredPackage, missinglinkCheck, missinglinkIgnoreDestinationPackages, missinglinkIgnoreSourcePackages}
import explicitdeps.ExplicitDepsPlugin.autoImport.{undeclaredCompileDependencies, unusedCompileDependencies, unusedCompileDependenciesFilter}
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import org.scalajs.linker.interface.ModuleKind
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport.scalaJSLinkerConfig
import scalafix.sbt.ScalafixPlugin.autoImport.scalafixResolvers
import scoverage.ScoverageKeys.{coverageEnabled, coverageScalacPluginVersion}
import scoverage.ScoverageSbtPlugin.{OrgScoverage => ScoverageOrg, ScalacRuntimeArtifact => ScoverageRuntimeArtifact}

object ScerdeBuild {

  val defaultSettings = Def.settings(
    organization := "net.archwill.scerde",
    version := (ThisBuild / version).value,

    homepage := Some(url("https://github.com/willtrnr/scerde")),

    description := "Generic serialization/deserialization framework for Scala",

    scmInfo := Some(ScmInfo(
      url("https://github.com/willtrnr/scerde"),
      "scm:git:https://github.com/willtrnr/scerde.git",
      "scm:git:git@github.com:willtrnr/scerde.git"
    )),

    Dependencies.default,
  )

  val defaultJvmSettings = Def.settings(
    Dependencies.versionsJvm,

    Compile / compile / scalacOptions ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((3, _)) =>
          Seq.empty
        case _ =>
          Seq(
            "-target:jvm-1.8",
          )
      }
    },

    Compile / compile / javacOptions ++= Seq(
      "-source", "1.8",
      "-target", "1.8",
      "-Xlint:all",
      "-Werror",
    ),

    Test / fork := true,

    // Last release with 2.11 support
    coverageScalacPluginVersion := "1.4.1",
  )

  val defaultJsSettings = Def.settings(
    Dependencies.versionsJs,

    // Output CommonJS modules instead of global scope scripts
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },

    // HACK: We need to fix the ScalaJS scoverage plugin dependency
    libraryDependencies := {
      val version = coverageScalacPluginVersion.value
      libraryDependencies.value.map { d =>
        if (d.organization == ScoverageOrg && d.name.startsWith(ScoverageRuntimeArtifact))
          ScoverageOrg %%% ScoverageRuntimeArtifact % version
        else
          d
      }
    },

    // FIXME: Tests fail on JS platform with coverage enabled
    Test / test := {
      if (!coverageEnabled.value) {
          (Test / test).value
      }
    },

    // Release with SJS 1.x support
    coverageScalacPluginVersion := "1.4.2",

    // Explicit deps also does not work correctly
    unusedCompileDependencies := Set.empty,
    undeclaredCompileDependencies := Set.empty,

    // And neither does missinglink
    missinglinkCheck := {},
  )

  val defaultNativeSettings = Def.settings(
    Dependencies.versionsNative,

    // Explicit deps also does not work correctly
    unusedCompileDependencies := Set.empty,
    undeclaredCompileDependencies := Set.empty,

    // And neither does missinglink
    missinglinkCheck := {},
  )

  val rootSettings = Def.settings(
    publish / skip := true,

    Compile / doc / sources := Seq.empty,
  )

  val no211Settings = Def.settings(
    crossScalaVersions -= Dependencies.V.scala211,
  )

}
