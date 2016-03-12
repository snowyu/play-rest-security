name         := "play2.4-rest-security"
version      := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayScala, PlayEbean)


scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
//  javaCore,
  javaJdbc,
  "io.swagger" %% "swagger-play2" % "1.5.1",
  "org.webjars" % "swagger-ui" % "2.1.4"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
