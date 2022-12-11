package Analizador;

import java.io.File;

public class Crear {
	
	public static void main(String[] args)
	{
	String ruta = "D:/eclipse-workspace/AnalisisSintactico/src/Analizador/Lexer.flex";
	generarLexer(ruta);
	}

	public static void generarLexer(String ruta){
	File archivo = new File(ruta);
	JFlex.Main.generate(archivo);
	}

}