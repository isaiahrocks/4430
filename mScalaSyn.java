//Miguel Martinez
//Syntax analyzer controller class
//CSCE 4430

public class mScalaSyn {

  public static void main (String args []) throws java.io.IOException {

    System . out . println ("Source Program");
    System . out . println ("--------------");
    System . out . println ();

    SyntaxAnalyzer mScala = new SyntaxAnalyzer ();
    mScala . object ();

    System . out . println ();
    System . out . println ("PARSE SUCCESSFUL");
  }

}