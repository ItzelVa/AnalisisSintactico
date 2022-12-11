package Analizador;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;
import java.awt.event.*;

public class Principal extends JFrame{
	JMenuBar barraM;
	JMenuItem op1, op2;
	JMenuItem abrir, cerrar, guardar, salir, lex, sin;
	JTextArea at, linea;
	FileDialog fd;
	String nl;
	JScrollPane sp, sp1, sp2;
	JTabbedPane tabs;
	JTable table;
	final DefaultTableModel modelo;
	Vector<String> titulos;
	Vector<Vector> datos;
	int cont = 1,col=0, ren=0, contTipo=0;
	Boolean ban=true, errorS=false, banTipo=false;
	

	String [][] tabla = {};
	String [][] ope = {};
	String [][] asig = {};
	String ids[][];
	
	String tipo="", elemento="",tipos="", idD="", der="", izq="", ele="", asign="";
	final JPanel l;
	Vector<Vector> data;
	final Vector<String> info;
	
	JTable pila = new JTable();
	JTextArea components = new JTextArea();
	JTextArea error = new JTextArea();
	JTextArea cod = new JTextArea();
	Color gr = new Color(246, 189, 96);
	
	Stack<String> p = new Stack();
	Stack<String> pS = new Stack();
	
	

	public Principal() {
		setTitle("Analizador Sintáctico");
		setSize(1100, 600);
		setLocationRelativeTo(this);
		this.getContentPane().setBackground(new Color(245, 202, 195));
		
		ope = new String[4][4];
		ope[0][0]="";		ope[0][1]="int";ope[0][2]="float";	ope[0][3]="char";
		ope[1][0]="int";	ope[1][1]="0";	ope[1][2]="-1";		ope[1][3]="-1";
		ope[2][0]="float";	ope[2][1]="1";	ope[2][2]="1";		ope[2][3]="-1";
		ope[3][0]="char";	ope[3][1]="-1";	ope[3][2]="-1";		ope[3][3]="2";
		
		asig = new String[4][4];
		asig[0][0]="";		asig[0][1]="int";	asig[0][2]="float";	asig[0][3]="char";
		asig[1][0]="int";	asig[1][1]="t";		asig[1][2]="f";		asig[1][3]="f";
		asig[2][0]="float";	asig[2][1]="t";		asig[2][2]="t";		asig[2][3]="f";
		asig[3][0]="char";	asig[3][1]="t";		asig[3][2]="t";		asig[3][3]="t";
		
		tabla = new String[40][25];
		
		tabla[0][0]="";			tabla[0][1]="id";			tabla[0][2]="num";			tabla[0][3]="int";			tabla[0][4]="float";		tabla[0][5]="char";			tabla[0][6]=",";			tabla[0][7]=";";			tabla[0][8]="+";			tabla[0][9]="-";			tabla[0][10]="*";			tabla[0][11]="/";			tabla[0][12]="=";			tabla[0][13]="(";			tabla[0][14]=")";			tabla[0][15]="$";			tabla[0][16]="P";			tabla[0][17]="Tipo";			tabla[0][18]="V";			tabla[0][19]="A";			tabla[0][20]="Exp";			tabla[0][21]="E";			tabla[0][22]="Term";			tabla[0][23]="T";			tabla[0][24]="F";
		tabla[1][0]="q0";		tabla[1][1]="q7";			tabla[1][2]="";				tabla[1][3]="q4";			tabla[1][4]="q5";			tabla[1][5]="q6";			tabla[1][6]="";				tabla[1][7]="";				tabla[1][8]="";				tabla[1][9]="";				tabla[1][10]="";			tabla[1][11]="";			tabla[1][12]="";			tabla[1][13]="";			tabla[1][14]="";			tabla[1][15]="";			tabla[1][16]="q1";			tabla[1][17]="q2";				tabla[1][18]="";			tabla[1][19]="q3";			tabla[1][20]="";			tabla[1][21]="";			tabla[1][22]="";				tabla[1][23]="";			tabla[1][24]="";
		tabla[2][0]="q1";		tabla[2][1]="";				tabla[2][2]="";				tabla[2][3]="";				tabla[2][4]="";				tabla[2][5]="";				tabla[2][6]="";				tabla[2][7]="";				tabla[2][8]="";				tabla[2][9]="";				tabla[2][10]="";			tabla[2][11]="";			tabla[2][12]="";			tabla[2][13]="";			tabla[2][14]="";			tabla[2][15]="SE ACEPTA";	tabla[2][16]="";			tabla[2][17]="";				tabla[2][18]="";			tabla[2][19]="";			tabla[2][20]="";			tabla[2][21]="";			tabla[2][22]="";				tabla[2][23]="";			tabla[2][24]="";
		tabla[3][0]="q2";		tabla[3][1]="q8";			tabla[3][2]="";				tabla[3][3]="";				tabla[3][4]="";				tabla[3][5]="";				tabla[3][6]="";				tabla[3][7]="";				tabla[3][8]="";				tabla[3][9]="";				tabla[3][10]="";			tabla[3][11]="";			tabla[3][12]="";			tabla[3][13]="";			tabla[3][14]="";			tabla[3][15]="";			tabla[3][16]="";			tabla[3][17]="";				tabla[3][18]="";			tabla[3][19]="";			tabla[3][20]="";			tabla[3][21]="";			tabla[3][22]="";				tabla[3][23]="";			tabla[3][24]="";
		tabla[4][0]="q3";		tabla[4][1]="";				tabla[4][2]="";				tabla[4][3]="";				tabla[4][4]="";				tabla[4][5]="";				tabla[4][6]="";				tabla[4][7]="";				tabla[4][8]="";				tabla[4][9]="";				tabla[4][10]="";			tabla[4][11]="";			tabla[4][12]="";			tabla[4][13]="";			tabla[4][14]="";			tabla[4][15]="P>A";			tabla[4][16]="";			tabla[4][17]="";				tabla[4][18]="";			tabla[4][19]="";			tabla[4][20]="";			tabla[4][21]="";			tabla[4][22]="";				tabla[4][23]="";			tabla[4][24]="";
		tabla[5][0]="q4";		tabla[5][1]="Tipo>int";		tabla[5][2]="";				tabla[5][3]="";				tabla[5][4]="";				tabla[5][5]="";				tabla[5][6]="";				tabla[5][7]="";				tabla[5][8]="";				tabla[5][9]="";				tabla[5][10]="";			tabla[5][11]="";			tabla[5][12]="";			tabla[5][13]="";			tabla[5][14]="";			tabla[5][15]="";			tabla[5][16]="";			tabla[5][17]="";				tabla[5][18]="";			tabla[5][19]="";			tabla[5][20]="";			tabla[5][21]="";			tabla[5][22]="";				tabla[5][23]="";			tabla[5][24]="";
		tabla[6][0]="q5";		tabla[6][1]="Tipo>float";	tabla[6][2]="";				tabla[6][3]="";				tabla[6][4]="";				tabla[6][5]="";				tabla[6][6]="";				tabla[6][7]="";				tabla[6][8]="";				tabla[6][9]="";				tabla[6][10]="";			tabla[6][11]="";			tabla[6][12]="";			tabla[6][13]="";			tabla[6][14]="";			tabla[6][15]="";			tabla[6][16]="";			tabla[6][17]="";				tabla[6][18]="";			tabla[6][19]="";			tabla[6][20]="";			tabla[6][21]="";			tabla[6][22]="";				tabla[6][23]="";			tabla[6][24]="";
		tabla[7][0]="q6";		tabla[7][1]="Tipo>char";	tabla[7][2]="";				tabla[7][3]="";				tabla[7][4]="";				tabla[7][5]="";				tabla[7][6]="";				tabla[7][7]="";				tabla[7][8]="";				tabla[7][9]="";				tabla[7][10]="";			tabla[7][11]="";			tabla[7][12]="";			tabla[7][13]="";			tabla[7][14]="";			tabla[7][15]="";			tabla[7][16]="";			tabla[7][17]="";				tabla[7][18]="";			tabla[7][19]="";			tabla[7][20]="";			tabla[7][21]="";			tabla[7][22]="";				tabla[7][23]="";			tabla[7][24]="";
		tabla[8][0]="q7";		tabla[8][1]="";				tabla[8][2]="";				tabla[8][3]="";				tabla[8][4]="";				tabla[8][5]="";				tabla[8][6]="";				tabla[8][7]="";				tabla[8][8]="";				tabla[8][9]="";				tabla[8][10]="";			tabla[8][11]="";			tabla[8][12]="q9";			tabla[8][13]="";			tabla[8][14]="";			tabla[8][15]="";			tabla[8][16]="";			tabla[8][17]="";				tabla[8][18]="";			tabla[8][19]="";			tabla[8][20]="";			tabla[8][21]="";			tabla[8][22]="";				tabla[8][23]="";			tabla[8][24]="";
		tabla[9][0]="q8";		tabla[9][1]="";				tabla[9][2]="";				tabla[9][3]="";				tabla[9][4]="";				tabla[9][5]="";				tabla[9][6]="q11";			tabla[9][7]="q12";			tabla[9][8]="";				tabla[9][9]="";				tabla[9][10]="";			tabla[9][11]="";			tabla[9][12]="";			tabla[9][13]="";			tabla[9][14]="";			tabla[9][15]="";			tabla[9][16]="";			tabla[9][17]="";				tabla[9][18]="q10";			tabla[9][19]="";			tabla[9][20]="";			tabla[9][21]="";			tabla[9][22]="";				tabla[9][23]="";			tabla[9][24]="";
		tabla[10][0]="q9";		tabla[10][1]="q16";			tabla[10][2]="q18";			tabla[10][3]="";			tabla[10][4]="";			tabla[10][5]="";			tabla[10][6]="";			tabla[10][7]="";			tabla[10][8]="";			tabla[10][9]="";			tabla[10][10]="";			tabla[10][11]="";			tabla[10][12]="";			tabla[10][13]="q17";		tabla[10][14]="";			tabla[10][15]="";			tabla[10][16]="";			tabla[10][17]="";				tabla[10][18]="";			tabla[10][19]="";			tabla[10][20]="q13";		tabla[10][21]="";			tabla[10][22]="q14";			tabla[10][23]="";			tabla[10][24]="q15";
		tabla[11][0]="q10";		tabla[11][1]="";			tabla[11][2]="";			tabla[11][3]="";			tabla[11][4]="";			tabla[11][5]="";			tabla[11][6]="";			tabla[11][7]="";			tabla[11][8]="";			tabla[11][9]="";			tabla[11][10]="";			tabla[11][11]="";			tabla[11][12]="";			tabla[11][13]="";			tabla[11][14]="";			tabla[11][15]="P>V_id_Tipo";tabla[11][16]="";			tabla[11][17]="";				tabla[11][18]="";			tabla[11][19]="";			tabla[11][20]="";			tabla[11][21]="";			tabla[11][22]="";				tabla[11][23]="";			tabla[11][24]="";
		tabla[12][0]="q11";		tabla[12][1]="q19";			tabla[12][2]="";			tabla[12][3]="";			tabla[12][4]="";			tabla[12][5]="";			tabla[12][6]="";			tabla[12][7]="";			tabla[12][8]="";			tabla[12][9]="";			tabla[12][10]="";			tabla[12][11]="";			tabla[12][12]="";			tabla[12][13]="";			tabla[12][14]="";			tabla[12][15]="";			tabla[12][16]="";			tabla[12][17]="";				tabla[12][18]="";			tabla[12][19]="";			tabla[12][20]="";			tabla[12][21]="";			tabla[12][22]="";				tabla[12][23]="";			tabla[12][24]="";
		tabla[13][0]="q12";		tabla[13][1]="q7";			tabla[13][2]="";			tabla[13][3]="q4";			tabla[13][4]="q5";			tabla[13][5]="q6";			tabla[13][6]="";			tabla[13][7]="";			tabla[13][8]="";			tabla[13][9]="";			tabla[13][10]="";			tabla[13][11]="";			tabla[13][12]="";			tabla[13][13]="";			tabla[13][14]="";			tabla[13][15]="";			tabla[13][16]="q20";		tabla[13][17]="q2";				tabla[13][18]="";			tabla[13][19]="q3";			tabla[13][20]="";			tabla[13][21]="";			tabla[13][22]="";				tabla[13][23]="";			tabla[13][24]="";
		tabla[14][0]="q13";		tabla[14][1]="";			tabla[14][2]="";			tabla[14][3]="";			tabla[14][4]="";			tabla[14][5]="";			tabla[14][6]="";			tabla[14][7]="q21";			tabla[14][8]="";			tabla[14][9]="";			tabla[14][10]="";			tabla[14][11]="";			tabla[14][12]="";			tabla[14][13]="";			tabla[14][14]="";			tabla[14][15]="";			tabla[14][16]="";			tabla[14][17]="";				tabla[14][18]="";			tabla[14][19]="";			tabla[14][20]="";			tabla[14][21]="";			tabla[14][22]="";				tabla[14][23]="";			tabla[14][24]="";
		tabla[15][0]="q14";		tabla[15][1]="";			tabla[15][2]="";			tabla[15][3]="";			tabla[15][4]="";			tabla[15][5]="";			tabla[15][6]="";			tabla[15][7]="E>ç";			tabla[15][8]="q23";			tabla[15][9]="q24";			tabla[15][10]="";			tabla[15][11]="";			tabla[15][12]="";			tabla[15][13]="";			tabla[15][14]="E>ç";		tabla[15][15]="";			tabla[15][16]="";			tabla[15][17]="";				tabla[15][18]="";			tabla[15][19]="";			tabla[15][20]="";			tabla[15][21]="q22";		tabla[15][22]="";				tabla[15][23]="";			tabla[15][24]="";
		tabla[16][0]="q15";		tabla[16][1]="";			tabla[16][2]="";			tabla[16][3]="";			tabla[16][4]="";			tabla[16][5]="";			tabla[16][6]="";			tabla[16][7]="T>ç";			tabla[16][8]="T>ç";			tabla[16][9]="T>ç";			tabla[16][10]="q26";		tabla[16][11]="q27";		tabla[16][12]="";			tabla[16][13]="";			tabla[16][14]="T>ç";		tabla[16][15]="";			tabla[16][16]="";			tabla[16][17]="";				tabla[16][18]="";			tabla[16][19]="";			tabla[16][20]="";			tabla[16][21]="";			tabla[16][22]="";				tabla[16][23]="q25";		tabla[16][24]="";
		tabla[17][0]="q16";		tabla[17][1]="";			tabla[17][2]="";			tabla[17][3]="";			tabla[17][4]="";			tabla[17][5]="";			tabla[17][6]="";			tabla[17][7]="F>id";		tabla[17][8]="F>id";		tabla[17][9]="F>id";		tabla[17][10]="F>id";		tabla[17][11]="F>id";		tabla[17][12]="";			tabla[17][13]="";			tabla[17][14]="F>id";		tabla[17][15]="";			tabla[17][16]="";			tabla[17][17]="";				tabla[17][18]="";			tabla[17][19]="";			tabla[17][20]="";			tabla[17][21]="";			tabla[17][22]="";				tabla[17][23]="";			tabla[17][24]="";
		tabla[18][0]="q17";		tabla[18][1]="q16";			tabla[18][2]="q18";			tabla[18][3]="";			tabla[18][4]="";			tabla[18][5]="";			tabla[18][6]="";			tabla[18][7]="";			tabla[18][8]="";			tabla[18][9]="";			tabla[18][10]="";			tabla[18][11]="";			tabla[18][12]="";			tabla[18][13]="q17";		tabla[18][14]="";			tabla[18][15]="";			tabla[18][16]="";			tabla[18][17]="";				tabla[18][18]="";			tabla[18][19]="";			tabla[18][20]="q28";		tabla[18][21]="";			tabla[18][22]="q14";			tabla[18][23]="";			tabla[18][24]="q15";
		tabla[19][0]="q18";		tabla[19][1]="";			tabla[19][2]="";			tabla[19][3]="";			tabla[19][4]="";			tabla[19][5]="";			tabla[19][6]="";			tabla[19][7]="F>num";		tabla[19][8]="F>num";		tabla[19][9]="F>num";		tabla[19][10]="F>num";		tabla[19][11]="F>num";		tabla[19][12]="";			tabla[19][13]="";			tabla[19][14]="F>num";		tabla[19][15]="";			tabla[19][16]="";			tabla[19][17]="";				tabla[19][18]="";			tabla[19][19]="";			tabla[19][20]="";			tabla[19][21]="";			tabla[19][22]="";				tabla[19][23]="";			tabla[19][24]="";
		tabla[20][0]="q19";		tabla[20][1]="";			tabla[20][2]="";			tabla[20][3]="";			tabla[20][4]="";			tabla[20][5]="";			tabla[20][6]="q11";			tabla[20][7]="q12";			tabla[20][8]="";			tabla[20][9]="";			tabla[20][10]="";			tabla[20][11]="";			tabla[20][12]="";			tabla[20][13]="";			tabla[20][14]="";			tabla[20][15]="";			tabla[20][16]="";			tabla[20][17]="";				tabla[20][18]="q29";		tabla[20][19]="";			tabla[20][20]="";			tabla[20][21]="";			tabla[20][22]="";				tabla[20][23]="";			tabla[20][24]="";
		tabla[21][0]="q20";		tabla[21][1]="";			tabla[21][2]="";			tabla[21][3]="";			tabla[21][4]="";			tabla[21][5]="";			tabla[21][6]="";			tabla[21][7]="";			tabla[21][8]="";			tabla[21][9]="";			tabla[21][10]="";			tabla[21][11]="";			tabla[21][12]="";			tabla[21][13]="";			tabla[21][14]="";			tabla[21][15]="V>P_;";		tabla[21][16]="";			tabla[21][17]="";				tabla[21][18]="";			tabla[21][19]="";			tabla[21][20]="";			tabla[21][21]="";			tabla[21][22]="";				tabla[21][23]="";			tabla[21][24]="";
		tabla[22][0]="q21";		tabla[22][1]="";			tabla[22][2]="";			tabla[22][3]="";			tabla[22][4]="";			tabla[22][5]="";			tabla[22][6]="";			tabla[22][7]="";			tabla[22][8]="";			tabla[22][9]="";			tabla[22][10]="";			tabla[22][11]="";			tabla[22][12]="";			tabla[22][13]="";			tabla[22][14]="";			tabla[22][15]="A>;_Exp_=_id";tabla[22][16]="";			tabla[22][17]="";				tabla[22][18]="";			tabla[22][19]="";			tabla[22][20]="";			tabla[22][21]="";			tabla[22][22]="";				tabla[22][23]="";			tabla[22][24]="";
		tabla[23][0]="q22";		tabla[23][1]="";			tabla[23][2]="";			tabla[23][3]="";			tabla[23][4]="";			tabla[23][5]="";			tabla[23][6]="";			tabla[23][7]="Exp>E_Term";	tabla[23][8]="";			tabla[23][9]="";			tabla[23][10]="";			tabla[23][11]="";			tabla[23][12]="";			tabla[23][13]="";			tabla[23][14]="Exp>E_Term";	tabla[23][15]="";			tabla[23][16]="";			tabla[23][17]="";				tabla[23][18]="";			tabla[23][19]="";			tabla[23][20]="";			tabla[23][21]="";			tabla[23][22]="";				tabla[23][23]="";			tabla[23][24]="";
		tabla[24][0]="q23";		tabla[24][1]="q16";			tabla[24][2]="q18";			tabla[24][3]="";			tabla[24][4]="";			tabla[24][5]="";			tabla[24][6]="";			tabla[24][7]="";			tabla[24][8]="";			tabla[24][9]="";			tabla[24][10]="";			tabla[24][11]="";			tabla[24][12]="";			tabla[24][13]="q17";		tabla[24][14]="";			tabla[24][15]="";			tabla[24][16]="";			tabla[24][17]="";				tabla[24][18]="";			tabla[24][19]="";			tabla[24][20]="";			tabla[24][21]="";			tabla[24][22]="q30";			tabla[24][23]="";			tabla[24][24]="q15";
		tabla[25][0]="q24";		tabla[25][1]="q16";			tabla[25][2]="q18";			tabla[25][3]="";			tabla[25][4]="";			tabla[25][5]="";			tabla[25][6]="";			tabla[25][7]="";			tabla[25][8]="";			tabla[25][9]="";			tabla[25][10]="";			tabla[25][11]="";			tabla[25][12]="";			tabla[25][13]="q17";		tabla[25][14]="";			tabla[25][15]="";			tabla[25][16]="";			tabla[25][17]="";				tabla[25][18]="";			tabla[25][19]="";			tabla[25][20]="";			tabla[25][21]="";			tabla[25][22]="q31";				tabla[25][23]="";			tabla[25][24]="q15";
		tabla[26][0]="q25";		tabla[26][1]="";			tabla[26][2]="";			tabla[26][3]="";			tabla[26][4]="";			tabla[26][5]="";			tabla[26][6]="";			tabla[26][7]="Term>T_F";	tabla[26][8]="Term>T_F";	tabla[26][9]="Term>T_F";	tabla[26][10]="";			tabla[26][11]="";			tabla[26][12]="";			tabla[26][13]="";			tabla[26][14]="Term>T_F";	tabla[26][15]="";			tabla[26][16]="";			tabla[26][17]="";				tabla[26][18]="";			tabla[26][19]="";			tabla[26][20]="";			tabla[26][21]="";			tabla[26][22]="";				tabla[26][23]="";			tabla[26][24]="";
		tabla[27][0]="q26";		tabla[27][1]="q16";			tabla[27][2]="q18";			tabla[27][3]="";			tabla[27][4]="";			tabla[27][5]="";			tabla[27][6]="";			tabla[27][7]="";			tabla[27][8]="";			tabla[27][9]="";			tabla[27][10]="";			tabla[27][11]="";			tabla[27][12]="";			tabla[27][13]="q17";		tabla[27][14]="";			tabla[27][15]="";			tabla[27][16]="";			tabla[27][17]="";				tabla[27][18]="";			tabla[27][19]="";			tabla[27][20]="";			tabla[27][21]="";			tabla[27][22]="";				tabla[27][23]="";			tabla[27][24]="q32";
		tabla[28][0]="q27";		tabla[28][1]="q16";			tabla[28][2]="q18";			tabla[28][3]="";			tabla[28][4]="";			tabla[28][5]="";			tabla[28][6]="";			tabla[28][7]="";			tabla[28][8]="";			tabla[28][9]="";			tabla[28][10]="";			tabla[28][11]="";			tabla[28][12]="";			tabla[28][13]="q17";		tabla[28][14]="";			tabla[28][15]="";			tabla[28][16]="";			tabla[28][17]="";				tabla[28][18]="";			tabla[28][19]="";			tabla[28][20]="";			tabla[28][21]="";			tabla[28][22]="";				tabla[28][23]="";			tabla[28][24]="q33";
		tabla[29][0]="q28";		tabla[29][1]="";			tabla[29][2]="";			tabla[29][3]="";			tabla[29][4]="";			tabla[29][5]="";			tabla[29][6]="";			tabla[29][7]="";			tabla[29][8]="";			tabla[29][9]="";			tabla[29][10]="";			tabla[29][11]="";			tabla[29][12]="";			tabla[29][13]="";			tabla[29][14]="q34";		tabla[29][15]="";			tabla[29][16]="";			tabla[29][17]="";				tabla[29][18]="";			tabla[29][19]="";			tabla[29][20]="";			tabla[29][21]="";			tabla[29][22]="";				tabla[29][23]="";			tabla[29][24]="";
		tabla[30][0]="q29";		tabla[30][1]="";			tabla[30][2]="";			tabla[30][3]="";			tabla[30][4]="";			tabla[30][5]="";			tabla[30][6]="";			tabla[30][7]="";			tabla[30][8]="";			tabla[30][9]="";			tabla[30][10]="";			tabla[30][11]="";			tabla[30][12]="";			tabla[30][13]="";			tabla[30][14]="";			tabla[30][15]="V>V_id_,";	tabla[30][16]="";			tabla[30][17]="";				tabla[30][18]="";			tabla[30][19]="";			tabla[30][20]="";			tabla[30][21]="";			tabla[30][22]="";				tabla[30][23]="";			tabla[30][24]="";
		tabla[31][0]="q30";		tabla[31][1]="";			tabla[31][2]="";			tabla[31][3]="";			tabla[31][4]="";			tabla[31][5]="";			tabla[31][6]="";			tabla[31][7]="E>ç";			tabla[31][8]="q23";			tabla[31][9]="q24";			tabla[31][10]="";			tabla[31][11]="";			tabla[31][12]="";			tabla[31][13]="";			tabla[31][14]="E>ç";		tabla[31][15]="";			tabla[31][16]="";			tabla[31][17]="";				tabla[31][18]="";			tabla[31][19]="";			tabla[31][20]="";			tabla[31][21]="q35";		tabla[31][22]="";				tabla[31][23]="";			tabla[31][24]="";
		tabla[32][0]="q31";		tabla[32][1]="";			tabla[32][2]="";			tabla[32][3]="";			tabla[32][4]="";			tabla[32][5]="";			tabla[32][6]="";			tabla[32][7]="";			tabla[32][8]="q23";			tabla[32][9]="q24";			tabla[32][10]="";			tabla[32][11]="";			tabla[32][12]="";			tabla[32][13]="";			tabla[32][14]="E>ç";		tabla[32][15]="";			tabla[32][16]="";			tabla[32][17]="";				tabla[32][18]="";			tabla[32][19]="";			tabla[32][20]="";			tabla[32][21]="q36";		tabla[32][22]="";				tabla[32][23]="";			tabla[32][24]="";
		tabla[33][0]="q32";		tabla[33][1]="";			tabla[33][2]="";			tabla[33][3]="";			tabla[33][4]="";			tabla[33][5]="";			tabla[33][6]="";			tabla[33][7]="T>ç";			tabla[33][8]="T>ç";			tabla[33][9]="T>ç";			tabla[33][10]="q26";		tabla[33][11]="q27";		tabla[33][12]="";			tabla[33][13]="";			tabla[33][14]="T>ç";		tabla[33][15]="";			tabla[33][16]="";			tabla[33][17]="";				tabla[33][18]="";			tabla[33][19]="";			tabla[33][20]="";			tabla[33][21]="";			tabla[33][22]="";				tabla[33][23]="q37";		tabla[33][24]="";
		tabla[34][0]="q33";		tabla[34][1]="";			tabla[34][2]="";			tabla[34][3]="";			tabla[34][4]="";			tabla[34][5]="";			tabla[34][6]="";			tabla[34][7]="T>ç";			tabla[34][8]="T>ç";			tabla[34][9]="T>ç";			tabla[34][10]="q26";		tabla[34][11]="q27";		tabla[34][12]="";			tabla[34][13]="";			tabla[34][14]="T>ç";		tabla[34][15]="";			tabla[34][16]="";			tabla[34][17]="";				tabla[34][18]="";			tabla[34][19]="";			tabla[34][20]="";			tabla[34][21]="";			tabla[34][22]="";				tabla[34][23]="q38";		tabla[34][24]="";
		tabla[35][0]="q34";		tabla[35][1]="";			tabla[35][2]="";			tabla[35][3]="";			tabla[35][4]="";			tabla[35][5]="";			tabla[35][6]="";			tabla[35][7]="F>)_Exp_(";	tabla[35][8]="F>)_Exp_(";	tabla[35][9]="F>)_Exp_(";	tabla[35][10]="F>)_Exp_(";	tabla[35][11]="F>)_Exp_(";	tabla[35][12]="";			tabla[35][13]="";			tabla[35][14]="F>)_Exp_(";	tabla[35][15]="";			tabla[35][16]="";			tabla[35][17]="";				tabla[35][18]="";			tabla[35][19]="";			tabla[35][20]="";			tabla[35][21]="";			tabla[35][22]="";				tabla[35][23]="";			tabla[35][24]="";
		tabla[36][0]="q35";		tabla[36][1]="";			tabla[36][2]="";			tabla[36][3]="";			tabla[36][4]="";			tabla[36][5]="";			tabla[36][6]="";			tabla[36][7]="E>E_Term_+";	tabla[36][8]="";			tabla[36][9]="";			tabla[36][10]="";			tabla[36][11]="";			tabla[36][12]="";			tabla[36][13]="";			tabla[36][14]="E>E_Term_+";	tabla[36][15]="";			tabla[36][16]="";			tabla[36][17]="";				tabla[35][18]="";			tabla[36][19]="";			tabla[36][20]="";			tabla[36][21]="";			tabla[36][22]="";				tabla[36][23]="";			tabla[36][24]="";
		tabla[37][0]="q36";		tabla[37][1]="";			tabla[37][2]="";			tabla[37][3]="";			tabla[37][4]="";			tabla[37][5]="";			tabla[37][6]="";			tabla[37][7]="E>E_Term_-";	tabla[37][8]="";			tabla[37][9]="";			tabla[37][10]="";			tabla[37][11]="";			tabla[37][12]="";			tabla[37][13]="";			tabla[37][14]="E>E_Term_-";	tabla[37][15]="";			tabla[37][16]="";			tabla[37][17]="";				tabla[36][18]="";			tabla[37][19]="";			tabla[37][20]="";			tabla[37][21]="";			tabla[37][22]="";				tabla[37][23]="";			tabla[37][24]="";
		tabla[38][0]="q37";		tabla[38][1]="";			tabla[38][2]="";			tabla[38][3]="";			tabla[38][4]="";			tabla[38][5]="";			tabla[38][6]="";			tabla[38][7]="T>T_F_*";		tabla[38][8]="T>T_F_*";		tabla[38][9]="T>T_F_*";		tabla[38][10]="";			tabla[38][11]="";			tabla[38][12]="";			tabla[38][13]="";			tabla[38][14]="T>T_F_*";	tabla[38][15]="";			tabla[38][16]="";			tabla[38][17]="";				tabla[37][18]="";			tabla[38][19]="";			tabla[38][20]="";			tabla[38][21]="";			tabla[38][22]="";				tabla[38][23]="";			tabla[38][24]="";
		tabla[39][0]="q38";		tabla[39][1]="";			tabla[39][2]="";			tabla[39][3]="";			tabla[39][4]="";			tabla[39][5]="";			tabla[39][6]="";			tabla[39][7]="T>T_F_/";		tabla[39][8]="T>T_F_/";		tabla[39][9]="T>T_F_/";		tabla[39][10]="";			tabla[39][11]="";			tabla[39][12]="";			tabla[39][13]="";			tabla[39][14]="T>T_F_/";	tabla[39][15]="";			tabla[39][16]="";			tabla[39][17]="";				tabla[38][18]="";			tabla[39][19]="";			tabla[39][20]="";			tabla[39][21]="";			tabla[39][22]="";				tabla[39][23]="";			tabla[39][24]="";
		
		Font f = new Font(Font.MONOSPACED, Font.BOLD, 16);
		Font f2 = new Font(Font.MONOSPACED, Font.PLAIN, 16);
		barraM = new JMenuBar();
		setJMenuBar(barraM);
		barraM.setBackground(new Color(247, 237, 226));
		op1 = new JMenu("Archivo");
		op2 = new JMenu("Analizar");

		URL ruta = getClass().getResource("/img/share.png");
		abrir = new JMenuItem("Abrir", new ImageIcon(ruta));
		abrir.setMnemonic('O');
		abrir.setToolTipText("Abre un documento");
		abrir.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
		abrir.setBackground(new Color(247, 237, 226));

		ruta = getClass().getResource("/img/cancel.png");
		cerrar = new JMenuItem("Cerrar", new ImageIcon(ruta));
		cerrar.setMnemonic('E');
		cerrar.setToolTipText("Cierra un documento");
		cerrar.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_MASK));
		cerrar.setBackground(new Color(247, 237, 226));

		ruta = getClass().getResource("/img/diskette.png");
		guardar = new JMenuItem("Guardar", new ImageIcon(ruta));
		guardar.setMnemonic('G');
		guardar.setToolTipText("Guarda un documento");
		guardar.setAccelerator(KeyStroke.getKeyStroke('G', InputEvent.CTRL_MASK));
		guardar.setBackground(new Color(247, 237, 226));
		ruta = getClass().getResource("/img/exit.png");

		salir = new JMenuItem("Salir", new ImageIcon(ruta));
		salir.setMnemonic('S');
		salir.setToolTipText("Salir del programa");
		salir.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
		salir.setBackground(new Color(247, 237, 226));
		
		sin = new JMenuItem("Sintáctico");

		op1.add(abrir);
		op1.add(cerrar);
		op1.add(guardar);
		op1.add(salir);
		op2.add(sin);
		barraM.add(op1);
		barraM.add(op2);

		linea = new JTextArea("1", 50, cont);
		linea.setFont(f);
		linea.setEditable(false);
		linea.setBackground(new Color(245, 202, 195));
		at = new JTextArea("", 50, 50);
		at.setFont(f2);
		sp = new JScrollPane(at, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp2 = new JScrollPane(linea, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp2.getHorizontalScrollBar().setModel(sp.getHorizontalScrollBar().getModel());
		sp.getVerticalScrollBar().setModel(sp2.getVerticalScrollBar().getModel());
		fd = new FileDialog(this, "Selecciona un archivo", FileDialog.LOAD);
		nl = at.getText();

		titulos = new Vector<String>();
		titulos.add("Entrada");
		titulos.add("Pila");
		titulos.add("Acción");
		titulos.add("Semántico");
		data = new Vector<Vector>();
		info = new Vector<String>();
		data.add(info);
		modelo = new DefaultTableModel(datos,titulos);
		table = new JTable(modelo);
		final JScrollPane sp3 = new JScrollPane(table);
		
		
		tabs = new JTabbedPane();

		tabs = new JTabbedPane();
		tabs.addTab("Componentes", components);
		tabs.addTab("Pila", sp3);
		tabs.addTab("Errores", error);
		tabs.addTab("Código Intermedio", cod);
		tabs.setBackground(gr);
		tabs.setFont(f);
		
		sp1 = new JScrollPane(tabs);
		
		l = new JPanel();
		l.add(sp2);
		l.add(sp);
		add(l, BorderLayout.WEST);
		add(sp1);

		JPanel l = new JPanel();
		l.add(sp2);
		l.add(sp);
		add(l, BorderLayout.WEST);
		add(sp1);

		at.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				String lineas = "";
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cont = at.getLineCount();
				}
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					cont = at.getLineCount();
				}
				for (int i = 1; i <= cont; i++)
					lineas += i + "\n";
				linea.setText(lineas);
				linea.setColumns((cont + "").length());
				repaint();

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		abrir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String lineas = "";
				fd.setVisible(true);
				String ruta = fd.getDirectory() + fd.getFile();
				String contenido = LeerArch(ruta);
				if (ruta.indexOf(".dif") != -1) {
					at.setText(contenido);
					setTitle("Analizador Sintáctico -  " + ruta);
					nl = at.getText();
					cont = at.getLineCount();
					for (int i = 1; i <= cont; i++)
						lineas += i + "\n";
					linea.setText(lineas);
				} else
					JOptionPane.showMessageDialog(null, "No se puede leer el archivo con esa extensión");
			}
		});

		cerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.clear();
				String rutag = "";
				String contGrabar = at.getText();
				if (!nl.equals(at.getText()) && !nl.equals("")) {
					int opc = JOptionPane.showConfirmDialog(null, "¿Desea cerrar el archivo sin guardar los cambios?");
					rutag = fd.getDirectory() + fd.getFile();
					if (JOptionPane.NO_OPTION == opc) {
						if (rutag.equals("nullnull") | rutag.equals("null")) {
							fd.setVisible(true);
							rutag = fd.getDirectory() + fd.getFile() + ".dif";
						}
						Grabar(rutag, contGrabar);
						JOptionPane.showMessageDialog(null, "Archivo guardado con Éxito");
						at.setText("");
						fd.setDirectory("null");
						fd.setFile("null");
					} else if (JOptionPane.CANCEL_OPTION == opc) {
						at.setText(contGrabar);
					} else {
						at.setText("");
						fd.setDirectory("null");
						fd.setFile("null");
					}
				} else
					at.setText("");
				setTitle("Analizador Sintáctico");

			}
		});

		salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rutag = "";
				String contGrabar = at.getText();
				if (!nl.equals(at.getText()) && !nl.equals("")) {
					int opc = JOptionPane.showConfirmDialog(null, "¿Desea salir sin guardar?");
					rutag = fd.getDirectory() + fd.getFile();
					if (JOptionPane.NO_OPTION == opc) {
						if (rutag.equals("nullnull") | rutag.equals("null")) {
							fd.setVisible(true);
							rutag = fd.getDirectory() + fd.getFile() + ".dif";
						}
						Grabar(rutag, contGrabar);
						JOptionPane.showMessageDialog(null, "Archivo guardado con Éxito");
						at.setText("");
					} else
						at.setText("");
				} else
					at.setText("");
				setTitle("Analizador Sintáctico");
				fd.setDirectory("null");
				fd.setFile("null");
				System.exit(0);
			}
		});

		guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nl = at.getText();
				String rutag = fd.getDirectory() + fd.getFile();
				String contGrabar = at.getText();
				if (rutag.equals("nullnull")) {
					fd.setVisible(true);
					rutag = fd.getDirectory() + fd.getFile();
					contGrabar = at.getText();
					Grabar(rutag + ".dif", contGrabar);
				} else {
					if (rutag.indexOf(".dif") != -1)
						Grabar(rutag, contGrabar);
					else
						Grabar(rutag + ".dif", contGrabar);
				}
				setTitle("Analizador Sintáctico  -  " + rutag);
				nl = at.getText();
			}
		});
		
		sin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ids = new String[100][2];
				idD="";
				tipos="";
				ban=true;
				errorS=false;
				banTipo=false;
				contTipo=0;
				elemento="";
				int mod=0, ind=0;
				mod = modelo.getRowCount();
				System.out.println(mod+"rows");
				pS.clear();
				p.push("$");
				p.push("q0");
				error.setText("");
				cod.setText("");
				
				while(mod!=0)
				{
					modelo.removeRow(mod-1);
					mod = modelo.getRowCount();	
				}
				
				tabs.setSelectedComponent(components);
				int con=1;
				nl = at.getText();
				String rutag = fd.getDirectory() + fd.getFile();
				String contGrabar = at.getText();
				if(rutag.equals("nullnull"))
				{
					fd.setVisible(true);
					rutag = fd.getDirectory() + fd.getFile();
					contGrabar = at.getText();
					Grabar(rutag+".dif", contGrabar);
				}
				else
				{
					if(rutag.indexOf(".dif")!=-1)
						Grabar(rutag, contGrabar);
					else
						Grabar(rutag+".dif", contGrabar);
				}
				setTitle("Analizador Sintáctico  -  " + rutag);
				nl = at.getText();
				
				try {
		            Reader lector = new BufferedReader(new FileReader(rutag));
		            Lexer lexer = new Lexer(lector);
		            String resultado = "";
		            int tip=0;
		            while (true) {
		                Tokens tokens = lexer.yylex();
		                if (tokens == null) {
		                    resultado += "$";
		                    if (ban)
		                    	Sintactico("$",con);
		                    
		                    if(p.peek().equals("SE ACEPTA"))
		                    {
		                    	components.setText(resultado+"\n\n"+ p.peek());
		                    	JOptionPane.showMessageDialog(null, "SE ACEPTA SINTÁCTICAMENTE");
		                    }
		                    else
		                    {
		                    	components.setText(resultado+"\n\n"+ "NO SE ACEPTA");
		                    	JOptionPane.showMessageDialog(null, "NO SE ACEPTA");
		                    	tabs.setSelectedComponent(error);
		                    }
		                    p.clear();
		                    for (int i=0; i<ids.length; i++)
		                    {	
		                    	if(!(ids[i][1]+"").equals("") && !(ids[i][1]+"").equals("null"))
		                    		if(!(ids[i][0]+"").equals(""))
		                    		{
		                    			if(idD.contains(ids[i][0]))
		                    				{
		                    					while(ids[tip][0].equals(ids[i][0]))
		                    						tip++;
		                    					error.append("\nLa variable " + ids[i][0]+ " ya ha sido declarada de tipo "+ ids[tip][1]);
		                    					ids[i][1]=ids[tip][1];
		                    					errorS=true;
		                    				}
		                    			idD+= ids[i][0]+"";
		                    		}
		                    }
		                    for (int i=0; i<ids.length; i++)
		                    {	
		                    	if((ids[i][1]+"").equals("") && !(ids[i][1]+"").equals("null"))
		                    	{
		                    		if(!idD.contains(ids[i][0]))
		                    		{
		                    			error.append("\nLa variable " + ids[i][0]+ " no ha sido declarada");
		                    			tabs.setSelectedComponent(error);
		                    			errorS=true;
		                    		}
		                    	}
		                    }
		                    if(errorS)
		                    	JOptionPane.showMessageDialog(null, "NO SE ACEPTA SEMÁNTICAMENTE");
		                    else
		                    	JOptionPane.showMessageDialog(null, "SE ACEPTA SEMÁNTICAMENTE");
		                    System.out.println("VARIABLES"+idD);
		                    System.out.println(pS);
		                    return;
		                    
		                }
		                if(ban) {
		                	switch (tokens) {
		                    case ERROR:
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t Error_Lexico \n";
		                    	error.append("Linea de Error --> "+con+ "\tError Lexico \t \t"+lexer.lexeme+"\t Descripcion: Símbolo Inaceptable \n");
		                        break;
		                    case Linea: 
		                    	con ++;
		                    	break;
		                    case reservada:
		                    	elemento = lexer.lexeme;
		                    	tipo = lexer.lexeme;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico(lexer.lexeme,con);
		                    	break;
		                    case id:
		                    	ids [ind][0]= lexer.lexeme;
		                    	ids [ind][1]= tipo;
		                    	elemento = lexer.lexeme;
		                    	ele=lexer.lexeme;
		                    	ind++;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico("id",con);
		                    	break;
		                    case num:
		                    	elemento = lexer.lexeme;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico("num",con);
		                    	break;
		                    case Error_Lexico:
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	error.append("Linea de Error --> "+con+ "\tError Lexico \t \t"+lexer.lexeme+"\t Descripción: Número Inaceptable \n");
		                    	break;
		                    case operador:
		                    	elemento = lexer.lexeme;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico(lexer.lexeme,con);
		                        break;
		                    case coma:
		                    	elemento = lexer.lexeme;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + "," + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico(",",con);
		                    	break;
		                    case puntocoma:	
		                    	elemento = lexer.lexeme;
		                    	tipo="";
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico(";",con);
		                    	break;
		                    case parentesis:
		                    	elemento = lexer.lexeme;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico(lexer.lexeme,con);
		                    	break;
		                    default:
		                        resultado += "Token: " + tokens + "\n";
		                        break;
		                    
		                }
		                }
		                
		            }
		            
		            
		        } catch (FileNotFoundException ex) {
		            
		        } catch (IOException ex) {
		            
		        }
				
			}
			
		});

		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}
	
	public void Sintactico(String i,int errorLine) {
		System.out.println("ENTRA "+i);
		int ind=0;
		boolean tipo=false;
		String prod="", red="", token="";
		System.out.println(p.peek());
		
		this.Buscar(i);
		if (i.equals("id"))
		{
			
			for (int ii=0; ii<ids.length; ii++)
            {	
            	if(!(ids[ii][1]+"").equals("") && !(ids[ii][1]+"").equals("null"))
            	{
            		if(elemento.equals(ids[ii][0]) && !errorS)
            		{
            			tipos+=" "+ids[ii][1];
            			pS.push(ids[ii][1]);
            			tipo = false;
            			break;
            		}
            		else
            			tipo = true;
            	}
            }
			
			if(tipo)
				errorS = false;
			
			if(!this.tipo.equals("")) 
			{
				cod.append(pS.peek()+ " "+ elemento +"\n");
			}
			
			if(banTipo) 
			{
				contTipo++;
				cod.append("v"+ contTipo +" = "+ elemento+"\n");
			}
		}
		
		if(elemento.equals("="))
		{
			banTipo = true;
			asign=ele;
		}
		
		if (i.equals("num"))
		{
			pS.push("int");
			
			if(banTipo) 
			{
				contTipo++;
				cod.append("v"+ contTipo +" = "+ elemento+"\n");
			}
		}
			
        if(!(tabla[ren][col].equals(""))) {
        	do {
        		
        		this.Buscar(i);
        		
        		ind = tabla[ren][col].indexOf('>');
        		
        		if(ind != -1) {
        			prod = tabla[ren][col].substring(ind+1);
            		
            		System.out.println("PRODUCCIÓN "+prod);  
            		StringTokenizer st = new StringTokenizer(prod,"_");  
            	     while (st.hasMoreTokens()) { 
            	    	 token = st.nextToken();
            	    	 if(token.equals("ç"))            	    	 
            	    		 break;
            	    	 p.pop();
            	    	 if(token.equals(p.peek())) {
            	    		 p.pop();
            	    	 }
            	    	 else
            	    	 {
            	    		error.append("Linea de Error   -->   "+errorLine+ "\tError Sintáctico en el elemento   -->   |  " + elemento +
            	    				 "  |\nSe esperaba   -->   " + token);;
            	    		ban = false;
            	    		return;            	    	 }
            	     }
            	     
            	     
            	     red = tabla[ren][col].substring(0, ind);
            	     
            	     for (int j = 1; j < 41; j++) {
            		     
            	        	if (tabla[j-1][0].equals(p.peek()))
            	        	{
            	        		ren = j-1;
            	        	}
            	        	
            	        }
            			
            	      for (int j = 1; j < 26; j++) {
            	     
            	       	if (tabla[0][j-1].equals(red))
            	       	{
            	       		col = j-1;
            	       		p.push(red); 
            	       		p.push(tabla[ren][col]);
            	       		
            	       		System.out.println(p);
            	            System.out.println(ren);
            	            System.out.println(col);
            	            System.out.println(prod);
            	            if(prod.contains("+") && !errorS)
            	            {
            	            	this.pilaSemantica();
            	            	cod.append("v"+ (contTipo-1) +" = " + "v"+ (contTipo-1) + " + v"+ contTipo+"\n");
            	            	contTipo--;
            	            	if(errorS) {
            	            		error.append("Linea de Error   -->   "+errorLine);
            	            		error.append("\nNo es posible sumar los tipos de datos\t" + izq + " + "+ der + " ");
            	            	}
            	            }
            	            else if (prod.contains("-") && !errorS)
            	            {
            	            	this.pilaSemantica();
            	            	cod.append("v"+ (contTipo-1) +" = " + "v"+ (contTipo-1) + " - v"+ contTipo+"\n");
            	            	contTipo--;
            	            	if(errorS) {
            	            		error.append("Linea de Error   -->   "+errorLine);
            	            		error.append("\nNo es posible restar los tipos de datos\t" + izq + " - "+ der + " ");
            	            	}
            	            }
            	            else if(prod.contains("*") && !errorS)
            	            {
            	            	this.pilaSemantica();
            	            	cod.append("v"+ (contTipo-1) +" = " + "v"+ (contTipo-1) + " * v"+ contTipo+"\n");
            	            	contTipo--;
            	            	if(errorS) {
            	            		error.append("Linea de Error   -->   "+errorLine);
            	            		error.append("\nNo es posible multiplicar los tipos de datos\t" + izq + " * "+ der + " ");
            	            	}
            	            }
            	            else if (prod.contains("/") && !errorS) 
            	            {
            	            	this.pilaSemantica();
            	            	cod.append("v"+ (contTipo-1) +" = " + "v"+ (contTipo-1) + " / v"+ contTipo+"\n");
            	            	contTipo--;
            	            	if(errorS) {
            	            		error.append("Linea de Error   -->   "+errorLine);
            	            		error.append("\nNo es posible dividir los tipos de datos\t" + izq + " / "+ der + " ");
            	            	}
            	            }
            	            else if (prod.contains("=") && !errorS)
            	            {
            	            	der="";
            	        		izq="";
            	        		der = pS.peek();
            	        		pS.pop();
            	            	izq = pS.peek();
            	            	pS.pop();
            	            	for(int r=1; r<asig.length; r++)
            	            	{
            	            		if(izq.equals(asig[r][0]))
            	            			{for(int c=1; c<asig.length; c++)
            	            				if (der.equals(asig[0][c]))
            	            				{
            	            					if(!(asig[r][c].equals("f")))
            	            					{
            	            						pS.push(izq);
            	            						break;
            	            					}
            	            					else
            	            						errorS=true;
            	            				}
            	            			}
            	            	}
            	            	
            	            	cod.append(asign +" = " + "v"+ (contTipo)+"\n");
            	            	contTipo--;
            	            	
            	            	if(errorS)
            	            	{	
            	            		error.append("Linea de Error   -->   "+errorLine);
            	            		error.append("\nNo es posible asignar los tipos de datos\t" + izq + " = "+ der + " ");
            	            	}
            	            }
            	         
            	            this.fillTable(i , p+"" , "Producción " + red + " -> "+ prod, pS+"");
            	       	}
                    	
           	         }
        		
        		}
        		else if(tabla[ren][col].equals(""))
        		{
        			
        			error.append("Linea de Error   -->   "+errorLine+ "\tError Sintáctico en el elemento   -->   |  " + elemento + "  |\nSe esperaba   -->   " + this.Esperaba(ren));;
        			
        	    	ban = false;
        	    	
        	    	return;
        		}
        		else
        		{
        			this.Buscar(i);
        			
	        		p.push(i);
	            	p.push(tabla[ren][col]);
	            	System.out.println(p);
	                System.out.println(ren);
	                System.out.println(col);
	                System.out.println(prod);
	                
	                this.fillTable(i , p+"" , "Desplaza " + i + " a "+ tabla[ren][col],pS+"");
	                return;
	        	}
        	}while(!(i.equals(tabla[0][col])));
        	
        }
        else
        {
        	p.pop();
        	
        	error.append("Linea de Error   -->   "+errorLine+ "\tError Sintáctico inesperado símbolo   -->   |  " + elemento + "  |\nSe esperaba   -->   " + this.Esperaba(ren));;
        	 	
	    	ban = false;
	    	return;	
        }
       
	}
	
	private void Buscar(String i){
		for (int j = 1; j < 41; j++) {
		     
        	if (tabla[j-1][0].equals(p.peek()))
        	{
        		ren = j-1;
        	}
        	
        }
		
        for (int j = 1; j < 26; j++) {
     
        	if (tabla[0][j-1].equals(i))
        	{
        		col = j-1;
        	}
        	
        }
	}
	
	private String Esperaba (int ren) {
		String esp="";
	
		for (int j = 1; j < 26; j++) {
	         
        	if (!(tabla[ren][j-1].equals("")))
        	{
        		esp += tabla[0][j-1] + "  |  ";
        	}
		}
		return esp;
	}

	public static void main(String[] args) {
		new Principal();
	}

	public void Grabar(String ruta, String contenido) {
		FileOutputStream arche = null;
		try {
			arche = new FileOutputStream(ruta);
			arche.write(contenido.getBytes());
			arche.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error no se pudo abrir el archivo");
		}
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}

	public String LeerArch(String ruta) {
		FileInputStream archl = null;
		String contenido = "";
		byte datos[] = new byte[30];
		int leidos = 0;
		try {
			archl = new FileInputStream(ruta);
			do {
				leidos = archl.read(datos);
				if (leidos != -1)
					contenido += new String(datos, 0, leidos);
			} while (leidos != -1);
			archl.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error no se pudo abrir el archivo");
		}
		return contenido;
	}
	
	private void pilaSemantica() {
		der="";
		izq="";
		der = pS.peek();
		pS.pop();
    	izq = pS.peek();
    	pS.pop();
    	for(int r=1; r<ope.length; r++)
    	{
    		if(izq.equals(ope[r][0]))
    			{for(int c=1; c<ope.length; c++)
    				if (der.equals(ope[0][c]))
    				{
    					if(!(ope[r][c].equals("-1")))
    					{
    						if(ope[r][c].equals("0"))
    							pS.push("int");
    						else if(ope[r][c].equals("1"))
    							pS.push("float");
    						else if(ope[r][c].equals("2"))
    							pS.push("char");
    						break;
    					}
    					else
    						errorS=true;
    				}
    			}
    	}
	}
	
	private void fillTable(String p, String ent, String ac, String sem)
	{
		String[] row = {p,ent,ac,sem};
		modelo.addRow(row);
		l.updateUI();
		return;
	}
	
}
