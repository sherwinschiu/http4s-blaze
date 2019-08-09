lazy val sl4jVersion = "1.7.26"
lazy val typeSafeLoggingVersion = "3.9.0"
lazy val logbackVersion = "1.0.13"
lazy val catsVersion = "1.6.1"
lazy val catsEffectVersion = "1.3.0"
lazy val circeVersion = "0.11.1"
lazy val monixVersion = "3.0.0-RC2"
lazy val http4sVersion = "0.20.3"

lazy val root = (project in file("."))
  .settings(
    name := "http4s-blaze",
    scalaVersion := "2.12.8",
  ).dependsOn(blazeError)

lazy val blazeError = (project in file("blaze-error")).settings(
  libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % sl4jVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % typeSafeLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "org.typelevel" %% "cats-core" % catsVersion,
    "org.typelevel" %% "cats-effect" % catsEffectVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.monix" %% "monix-eval" % monixVersion,
    "io.monix" %% "monix-execution" % monixVersion,
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-circe" % http4sVersion,
    "org.http4s" %% "http4s-blaze-client" % http4sVersion,
    "org.http4s" %% "http4s-async-http-client" % http4sVersion
  )
)
