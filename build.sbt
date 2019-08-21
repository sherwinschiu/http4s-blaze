lazy val sl4jVersion = "1.7.26"
lazy val typeSafeLoggingVersion = "3.9.0"
lazy val logbackVersion = "1.0.13"
lazy val catsVersion = "1.6.1"
lazy val catsEffectVersion = "1.3.1"
lazy val monixVersion = "3.0.0-RC3"

lazy val root = (project in file("."))
  .settings(
    name := "logging",
    scalaVersion := "2.12.8",
  ).dependsOn(loggingpoc)

lazy val loggingpoc = (project in file("loggingpoc")).settings(
  libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % sl4jVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % typeSafeLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "org.typelevel" %% "cats-core" % catsVersion,
    "org.typelevel" %% "cats-effect" % catsEffectVersion,
    "io.monix" %% "monix-eval" % monixVersion,
    "io.monix" %% "monix-execution" % monixVersion
  )
)
