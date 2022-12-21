package Analizador;
import static Analizador.Tokens.*;
%%
%class Lexer
%type Tokens
L=[a-zA-Z_]+
D=[0-9]+
id={L}({L}|{D})*
Identifier  = [a-zA-Z][a-zA-Z0-9_]*
espacio=[ ]+
tab=[\t]+
saltoln=[\r]+
%{
    public String lexeme;
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}
{tab} {/*Ignore*/}
{saltoln} {/*Ignore*/}

/* Comentarios */
( "//"(.)* ) {/*Ignore*/}

/* Salto de linea */
( "\n" ) {return Linea;}

/* Palabra reservada */
( int | char | float | do | while | switch | case | break | imprimir | leer | endS) {lexeme=yytext(); return reservada;}


/*Litcar*/
(['][a-zA-Z]['] | ['][0-9][']) {lexeme=yytext(); return litcar;}


/* Error Lexico */
(-?[0][0]+[0-9]* )  {lexeme=yytext(); return Error_Lexico;}

(-?[0][0-9]+)  {lexeme=yytext(); return Error_Lexico;}

(-[0])  {lexeme=yytext(); return Error_Lexico;}

/* Numero */
(([1-9]([0-9])*)|([0]|[1-9]([0-9])*)) {lexeme=yytext(); return num;}

/* Numero Flotantes */
(-?([0]|([1-9][0-9]*))\.[0-9]*[1-9]([eE][+-][1-9][0-9]*)? ) {lexeme=yytext(); return numf;}


/* Símbolos de Agrupación */
( "(" | ")" ) {lexeme=yytext(); return parentesis;}

/* Operadores aritmeticos */
( "=" | "+" | "-" | "*" | "/" ) {lexeme=yytext(); return operador;}

/* Coma */
"," {return coma;}

/* leer */
([\"]"%i"[\"]) {return leeri;}

/* imprimir */
([\"]"%f"[\"]) {return leerf;}

/*Operadores Relacionales */
( ">" | "<" | "==") {lexeme = yytext(); return relacional;}

(\'.+\') {lexeme=yytext(); return Error_Lexico;}

/* Operadores de Referencia */
( ";" ) {lexeme=yytext(); return puntocoma;}

/* Operadores de Referencia */
( ":" ) {lexeme=yytext(); return dospuntos;}

/* Identificador */
{L}({L}|{D})* {lexeme=yytext(); return id;}


/* Error de analisis */
 ("@" |"$" | "`") {lexeme=yytext(); return ERROR;}