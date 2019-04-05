name := "The Type Astronaut's Guide to Shapeless"

scalaVersion := "2.12.8"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies +=
  "com.chuusai" %% "shapeless" % "2.3.3"

scalacOptions ++= Seq(
  "-Xfatal-warnings"
)
