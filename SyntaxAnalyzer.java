/*
Name: Miguel Martinez
CSCE 4430
Syntax Analyzer for MicroScala
*/

public class SyntaxAnalyzer {

  protected mScalaLexer lexer;	// lexical analyzer
  protected Token token;	// current token

  public SyntaxAnalyzer () throws java.io.IOException 
  {
    lexer = new mScalaLexer (System . in);
    getToken ();
  }

  private void getToken () throws java.io.IOException 
  { 
    token = lexer . nextToken (); 
  }
  
  public void object () throws java.io.IOException 
  {
		if(token . symbol () != TokenClass . OBJECT)
		  ErrorMessage . print (lexer . position (), "object EXPECTED");
    getToken();
		if(token . symbol () != TokenClass . ID)
		  ErrorMessage . print (lexer . position (), "identifier EXPECTED");
    getToken();
		if (token . symbol () != TokenClass . LEFTBRACE) 
			ErrorMessage . print (lexer . position (), "{ EXPECTED");
		getToken ();
		def();
		mainDef();
		//statements();
		if (token . symbol () != TokenClass . RIGHTBRACE) 
			ErrorMessage . print (lexer . position (), "}1 EXPECTED");
		getToken();
		if (token . symbol () != TokenClass . EOF)
			ErrorMessage . print (lexer . position (), "END OF PROGRAM EXPECTED");
  }
  public void def () throws java.io.IOException
  {
		varDef();
		//getToken();
		while (token . symbol () == TokenClass . DEF)
		{
			getToken();
			if(token . symbol () == TokenClass . MAIN)
			{
				//mainDef();
				break;
				//this test is here because we don't want to process main def the same
				//as  a regular def
			}
			if (token . symbol () != TokenClass . ID) 
				ErrorMessage . print (lexer . position (), "id EXPECTED");
			getToken ();
			if (token . symbol () != TokenClass . LEFTPAREN) 
				ErrorMessage . print (lexer . position (), "( EXPECTED");
			getToken ();
			if (token . symbol () != TokenClass . RIGHTPAREN) //testing for id : Type {, id : Type}
			{
				//getToken();
				do{
					if(token . symbol () == TokenClass . COMMA)
						getToken();
					if(token . symbol () != TokenClass . ID)
						ErrorMessage . print (lexer . position(), "id EXPECTED");
					getToken();
					if(token . symbol () != TokenClass . COLON)
						ErrorMessage . print (lexer . position(), ": EXPECTED");
					getToken();
					if(token . symbol () == TokenClass . INT 
					|| token . symbol () == TokenClass . LIST)
						typeDef();
					//getToken();
				}while(token . symbol () == TokenClass . COMMA);
			}
			getToken ();
			if(token . symbol () != TokenClass . COLON)
				ErrorMessage . print (lexer . position() , ": EXPECTED");
			getToken();
			if(token . symbol () == TokenClass . INT 
			|| token . symbol () == TokenClass . LIST)
				typeDef();
			//getToken();
			if(token . symbol () != TokenClass . ASSIGN)
				ErrorMessage . print (lexer . position() , "= EXPECTED");
			getToken();
			if (token . symbol () != TokenClass . LEFTBRACE) 
				ErrorMessage . print (lexer . position (), "{ EXPECTED");
			getToken ();
			varDef();
			statements();
			if(token . symbol () != TokenClass . RETURN)
				ErrorMessage . print (lexer . position (), "return EXPECTED");
			getToken();
			if(token . symbol () != TokenClass . ID)
				ErrorMessage . print (lexer . position (), "id EXPECTED");
			getToken();
			if(token . symbol () != TokenClass . SEMICOLON)
				ErrorMessage . print (lexer . position (), "; EXPECTED");
			getToken();
			if(token . symbol () != TokenClass . RIGHTBRACE)
				ErrorMessage . print (lexer . position (), "} 2EXPECTED");
			getToken();
		}
  }
  public void mainDef () throws java.io.IOException 
  {
		if(token . symbol () != TokenClass . MAIN)
			ErrorMessage . print (lexer . position (), "main EXPECTED");
		getToken();
		if(token . symbol () != TokenClass . LEFTPAREN)
			ErrorMessage . print (lexer . position (), "( EXPECTED");
		getToken();
		if(token . symbol () != TokenClass . ARGS)
			ErrorMessage . print (lexer . position (), "args EXPECTED");
		getToken();
		if(token . symbol () != TokenClass . COLON)
			ErrorMessage . print (lexer . position (), ": EXPECTED");
		getToken();
		if(token . symbol () != TokenClass . ARRAY)
			ErrorMessage . print (lexer . position (), "Array EXPECTED");
		getToken();
		if(token . symbol () != TokenClass . LEFTBRACKET)
			ErrorMessage . print (lexer . position (), "[ EXPECTED");
		getToken();
		if(token . symbol () != TokenClass . STRING)
			ErrorMessage . print (lexer . position (), "string EXPECTED");
		getToken();
		if(token . symbol () != TokenClass . RIGHTBRACKET)
			ErrorMessage . print (lexer . position (), "] EXPECTED");
		getToken();
		if(token . symbol () != TokenClass . RIGHTPAREN)
			ErrorMessage . print (lexer . position (), ") EXPECTED");
		getToken();
		if(token . symbol () != TokenClass . LEFTBRACE)
			ErrorMessage . print (lexer . position (), "{ EXPECTED");
		getToken();
		varDef();
		statement();
		statements();
		if(token . symbol () != TokenClass . RIGHTBRACE)
			ErrorMessage . print (lexer . position (), "} EXPECTED");
		getToken();
  }
  
  public void typeDef() throws java.io.IOException
  {
	  if(token . symbol () == TokenClass . INT)
	  {
		getToken();
	  }
	  else //only other type is a List[int]
	  {
		getToken();
		if(token . symbol () != TokenClass . LEFTBRACKET)
			ErrorMessage . print (lexer . position (), "[ EXPECTED");
		getToken();
		typeDef();
		if(token . symbol () != TokenClass . RIGHTBRACKET)
			ErrorMessage . print (lexer . position (), "] EXPECTED");
		getToken();
	  }
  }
  
  public void varDef () throws java.io.IOException
  {
		while(token . symbol () == TokenClass . VAR)
		{
			getToken();
			if(token . symbol () != TokenClass . ID)
				ErrorMessage . print (lexer . position (), "id EXPECTED");
			getToken();
			if(token . symbol () != TokenClass . COLON)
				ErrorMessage . print (lexer . position (), ": EXPECTED");
			getToken();
			typeDef();
			//getToken();
			if(token . symbol () != TokenClass . ASSIGN)
				ErrorMessage . print (lexer . position (), "= EXPECTED");
			getToken();
			if((token . symbol () != TokenClass . INTEGER) 
			&& (token . symbol () != TokenClass . NIL))
				ErrorMessage . print (lexer . position (), "literal EXPECTED");
			getToken();
			if(token . symbol () != TokenClass . SEMICOLON)
				ErrorMessage . print (lexer . position (), "; EXPECTED");
			getToken();
		}
  }
  
  public void statements () throws java.io.IOException 
  {
    while (token . symbol () == TokenClass . SEMICOLON
        || token . symbol () == TokenClass . LEFTBRACE
        || token . symbol () == TokenClass . ID
        || token . symbol () == TokenClass . IF
        || token . symbol () == TokenClass . WHILE
		|| token . symbol () == TokenClass . PRINTLN)
      statement ();
  }
  public void statement () throws java.io.IOException 
  {
	  switch (token . symbol())
	  {
		case SEMICOLON : //gets ; token, returns to statements while
			getToken();
		break;
		
		case LEFTBRACE : //processing blocks of statements, whiles, ifs, etc.
			getToken();
			do{
				statement();
			}while(token . symbol () != TokenClass . RIGHTBRACE);
			getToken();
		break;
			
		case ID : //assignment case
			getToken();
			if(token . symbol () != TokenClass . ASSIGN)
				ErrorMessage . print (lexer . position (), "= EXPECTED");
			getToken();
			listExpr();
			if(token . symbol () != TokenClass . SEMICOLON)
				ErrorMessage . print (lexer . position (), "; EXPECTED");
			getToken();
		break;

		case IF : //ifthenelse
			getToken();
			if(token . symbol () != TokenClass . LEFTPAREN)
				ErrorMessage . print (lexer . position (), "( EXPECTED");
			getToken();
			expression();
			if(token . symbol () != TokenClass . RIGHTPAREN)
				ErrorMessage . print (lexer . position (), ") EXPECTED");
			getToken();
			statement();
			if(token . symbol () == TokenClass . ELSE)
			{
				getToken();
				statement();
			}
		break;
		
		case PRINTLN : //println function
			getToken();
			if(token . symbol () != TokenClass . LEFTPAREN)
				ErrorMessage . print (lexer . position (), "( EXPECTED");
			getToken();
			listExpr();
			if(token . symbol () != TokenClass . RIGHTPAREN)
				ErrorMessage . print (lexer . position (), ") EXPECTED");
			getToken();
			if(token . symbol () != TokenClass . SEMICOLON)
				ErrorMessage . print (lexer . position (), "; EXPECTED");
			getToken();
		break;
		
		case WHILE : //while
			getToken();
			if(token . symbol () != TokenClass . LEFTPAREN)
				ErrorMessage . print (lexer . position (), "( EXPECTED");
			getToken();
			expression();
			if(token . symbol () != TokenClass . RIGHTPAREN)
				ErrorMessage . print (lexer . position (), ") EXPECTED");
			getToken();
			statement();
		break;
		
		default:
			ErrorMessage . print (lexer . position(), "UNRECOGNIZABLE SYMBOL");
	  }
  }
  
  public void expression () throws java.io.IOException
  {
	  andExpr();
	  while(token . symbol () == TokenClass . OR) //for || in a while or if
	  {
		  getToken();
		  andExpr();
	  }
  }
  
  public void andExpr () throws java.io.IOException
  {
	  relExpr();
	  while(token . symbol () == TokenClass . AND) //for && in while or if
	  {
		  getToken();
		  relExpr();
	  }
  }
  
  public void relExpr () throws java.io.IOException
  {
	  if(token . symbol () == TokenClass . NOT)
		  getToken();
	  listExpr();
	  if(token . symbol () == TokenClass . RELOP)
	  {
		  getToken();
		  listExpr();
	  }
  }
  
  public void listExpr () throws java.io.IOException
  {
	  addExpr();
	  if(token . symbol () == TokenClass . CONS)
	  {
		  getToken();
		  listExpr();
	  }
  }
  
  public void addExpr () throws java.io.IOException
  {
	  mulExpr();
	  while(token . symbol () == TokenClass . ADDOP)
	  {
		  getToken();
		  mulExpr();
	  }
  }
  
  public void mulExpr () throws java.io.IOException
  {
	  preFixExpr();
	  //possible getToken here
	  while(token . symbol () == TokenClass . MULTOP)
	  {
		  getToken();
		  preFixExpr();
		  //another possible getToken
	  }
  }
  
  public void preFixExpr () throws java.io.IOException
  {
	  if(token . symbol () == TokenClass . ADDOP)
		  getToken(); //possible
	  simpleExpr();
	  while(token . symbol () == TokenClass . PERIOD)
	  {
		  getToken();
		  listMethodCall();
	  }
  }
  
  public void listMethodCall () throws java.io.IOException
  {
	  // check for head, tail, empty
	    /*if(token . symbol () != TokenClass . PERIOD)
		  ErrorMessage . print (lexer . position (), ". EXPECTED");
		getToken();*/
		if(token . symbol () != TokenClass . LISTOP)
			ErrorMessage . print (lexer . position (), "listOp EXPECTED");
		getToken();
  }
  
  public void simpleExpr () throws java.io.IOException
  {
	  literal();
	  if(token . symbol () == TokenClass . LEFTPAREN)
	  {
		  getToken();
		  expression();
		  if(token . symbol () != TokenClass . RIGHTPAREN)
			  ErrorMessage . print (lexer . position (), ") EXPECTED");
		  getToken();
	  }
	  if(token . symbol () == TokenClass . ID)
	  {
		  getToken();
		  if(token . symbol () == TokenClass . LEFTPAREN)
		  {
				getToken();
				do{
					if(token . symbol () == TokenClass . COMMA)
						getToken();
					listExpr();
				}while (token . symbol () == TokenClass . COMMA);  
				if(token . symbol () != TokenClass . RIGHTPAREN)
					ErrorMessage . print (lexer . position (), ") EXPECTED");
				getToken();
		  }
	  }
	  
  }
  
  public void literal () throws java.io.IOException
  {
	  if(token . symbol () == TokenClass . INTEGER || token . symbol () == TokenClass . NIL)
		getToken();
  }
}