import sbtcrossproject.CrossProject

lazy val root =
  Project(id = "scerde", base = file("."))
    .settings(ScerdeBuild.defaultSettings)
    .settings(ScerdeBuild.rootSettings)
    .aggregate(
      coreJVM,
      coreJS,
      coreNative,
    )

lazy val core =
  scerdeCrossModule("core")
    .settings(Dependencies.core)

lazy val coreJVM = core.jvm
lazy val coreJS = core.js
lazy val coreNative = core.native

def scerdeProject(module: String)(project: Project): Project =
  project
    .settings(ScerdeBuild.defaultSettings)
    .settings(
      name := module,
      moduleName := s"scerde-$module",
    )

def scerdeModule(module: String): Project =
  Project(module, file(module))
    .configure(scerdeProject(module))
    .settings(ScerdeBuild.defaultJvmSettings)

def scerdeCrossModule(module: String): CrossProject =
  CrossProject(module, file(module))(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Full)
    .configure(scerdeProject(module))
    .jvmSettings(ScerdeBuild.defaultJvmSettings)
    .jsSettings(ScerdeBuild.defaultJsSettings)
    .nativeSettings(ScerdeBuild.defaultNativeSettings)
