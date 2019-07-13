scalaVersion := "2.12.8"
version := "0.1.0"
organization := "com.example"
organizationName := "example"
name := "article-viewer"

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val CatsVersion = "1.6.1"
lazy val SttpVersion = "1.5.11"
lazy val CirceVersion = "0.11.1"
lazy val CirceOpticsVersion = "0.10.0"
lazy val CirceConfigVersion = "0.6.1"
lazy val LogbackVersion = "1.2.3"
lazy val ScalaTestVersion = "3.0.5"
lazy val KindProjectorVersion = "0.9.9"

lazy val catsDeps = Seq(
  "org.typelevel" %% "cats-core" % CatsVersion,
  "org.typelevel" %% "cats-effect" % "1.2.0",
  "io.chrisdavenport" %% "log4cats-slf4j" % "0.3.0"
)

lazy val sttpDeps = Seq(
  "com.softwaremill.sttp" %% "core" % SttpVersion,
  "com.softwaremill.sttp" %% "async-http-client-backend-cats" % SttpVersion
)


lazy val circeDeps = Seq(
  "io.circe" %% "circe-core" % CirceVersion,
  "io.circe" %% "circe-generic" % CirceVersion,
  "io.circe" %% "circe-optics" % CirceOpticsVersion,
  "io.circe" %% "circe-config" % CirceConfigVersion,
)

lazy val otherDeps = Seq(
  "ch.qos.logback" % "logback-classic" % LogbackVersion,
  "org.scalatest" %% "scalatest" % ScalaTestVersion % Test
)

libraryDependencies ++= catsDeps ++ sttpDeps ++ circeDeps ++ otherDeps

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-unchecked",
  "-Xfuture",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ypartial-unification",
  "-Xfatal-warnings"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % KindProjectorVersion cross CrossVersion.binary)
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0-M4")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.0.0")
