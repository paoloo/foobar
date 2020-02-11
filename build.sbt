name := "test-api"

version := "0.1"

scalaVersion := "2.12.6"

maintainer := "paolo@firstfoundry.co"

val akkaVersion = "2.5.13"
val akkaHttpVersion = "10.1.3"
val circeVersion = "0.9.3"

enablePlugins(UniversalPlugin)
enablePlugins(JavaAppPackaging)
packageName in Universal:= "deployment"
publishArtifact in (Compile, packageDoc) := false

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "io.spray" %%  "spray-json" % "1.3.4",

  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "de.heikoseeberger" %% "akka-http-circe" % "1.21.0",

  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)
