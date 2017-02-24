fork in Test := true

javaOptions in Test := Seq("-ea")

libraryDependencies ++= Seq(
  "io.reactivex" %% "rxscala" % "0.26.5",
  "org.mockito" % "mockito-core" % "2.7.9" % Test,
  "org.scala-lang" % "scala-compiler" % scalaVersion.value,
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

name := "rx-game-of-life"

scalacOptions := Seq("-deprecation", "-feature", "-Xfuture")

scalaVersion := "2.11.8"

version := "0.1-SNAPSHOT"
