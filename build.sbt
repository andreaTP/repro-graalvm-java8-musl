
val javaVersion = settingKey[String]("The java version to be used")

javaVersion := "8"

scalaVersion := "2.12.11"

libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.2"

javacOptions ++= Seq("-source", javaVersion.value, "-target", javaVersion.value)

enablePlugins(GraalVMNativeImagePlugin)
graalVMNativeImageGraalVersion := Some(s"20.0.0-java${javaVersion.value}")

graalVMNativeImageOptions := Seq(
  "--verbose",
  "--no-server",
  "--enable-http",
  "--enable-https",
  "--enable-url-protocols=http,https,file,jar",
  "--enable-all-security-services",
  "-H:+JNI",
  "--static",
  "-H:IncludeResourceBundles=com.sun.org.apache.xerces.internal.impl.msg.XMLMessages",
  "-H:+ReportExceptionStackTraces",
  "--no-fallback",
  "--initialize-at-build-time",
  "--report-unsupported-elements-at-runtime",
  // Without this line you obtaine java.net.UnknownHostException on alpine
  "-H:UseMuslC=/opt/graalvm/stage/resources/bundle/"
)

val getMuslBundle = taskKey[Unit]("Fetch Musl bundle")

getMuslBundle := {
  import scala.sys.process._

  // this has to be done in Java
  if (!(baseDirectory.value / "src" / "graal" / "bundle").exists) {
    "curl -L -o musl.tar.gz https://github.com/gradinac/musl-bundle-example/releases/download/v1.0/musl.tar.gz".!
    "tar -xvzf musl.tar.gz --directory src/graal".!
  }
}

GraalVMNativeImage / packageBin := (GraalVMNativeImage / packageBin).dependsOn(getMuslBundle).value
