import scalaj.http._

object Main extends App {

  val result = Http("http://google.com").asString
  
  println(result)

}

