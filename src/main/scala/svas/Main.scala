package svas

object Main {

  def main(args: Array[String]): Unit = {
    val server = new Server()

    // Call Finatra HttpServer's main
    server.main(Array())
  }

}
