name := "svas-service"
scalaOrganization := "org.typelevel"
scalaVersion := "2.12.3-bin-typelevel-4"
scalacOptions += "-Ypartial-unification"

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "com.github.etaty" %% "rediscala" % "1.8.0",
  "com.trueaccord.scalapb" %% "scalapb-json4s" % "0.3.3",
  "com.trueaccord.scalapb" %% "scalapb-runtime" % com.trueaccord.scalapb.compiler.Version.scalapbVersion % "protobuf",
  "com.twitter" %% "finatra-http" % "17.10.0",
  "io.monix" %% "monix" % "3.0.0-M2",
  "org.typelevel" %% "cats-core" % "1.0.0-RC1"
)

PB.protoSources in Compile := Seq(
  file("src/main/proto")
)

PB.targets in Compile := Seq(
  scalapb.gen(
    flatPackage = true,
    grpc = true
  ) -> (sourceManaged in Compile).value
)
