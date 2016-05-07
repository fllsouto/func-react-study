
import scala.io.Source

object TelephoneMnemonicParser {
  
  def main(args: Array[String]): Unit = {
    val in = Source.fromURL("http://lamp.epfl.ch/files/content/sites/lamp/files/teaching/progfun/linuxwords.txt")

    val words = in.getLines.toList filter (word => word forall (chr => chr.isLetter))

    val mnem = Map(
      '2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
      '6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ")

    val charCode: Map[Char, Char] =
      for((digit, str) <- mnem; ltr <- str) yield ltr -> digit

    def wordCode(word: String): String =
      word.toUpperCase map charCode

    val wordsForNum: Map[String, Seq[String]] =
      words groupBy wordCode withDefaultValue Seq()

    def encode(number: String): Set[List[String]] = {
      // println("Number : " + number)
      if (number.isEmpty) {
        val encoded: Set[List[String]] =  Set(List())
        // println("Encoded : " + encoded)
        return encoded
      } else {
        val encoded: Set[List[String]] = { 
          for {
            split <- 1 to number.length
            word <- wordsForNum(number take split)
            rest <- encode(number drop split)
          } yield word :: rest 
        }.toSet
        // println("Encoded : " + encoded)
        return encoded
      }
    }

    encode("7225247386")

    def translate(number: String): Set[String] =
      encode(number) map (_ mkString " ")
    }
    translate("7225247386")
}