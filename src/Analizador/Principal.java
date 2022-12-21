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
	int cont = 1,col=0, ren=0, contTipo=0,conttTipo=0, doos=0,swi=0,cas=0,lee,imp=0,br=0;
	Boolean ban=true, errorS=false, banTipo=false, doo=false,pc=false,whi=false;
	

	String [][] ope = {};
	String [][] asig = {};
	String ids[][];
	String tabla[][];
	
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
		
		
		tabla = new String[214][48];
		this.tabla1();
		this.tabla2();
		this.tabla3();
		this.tabla4();
		
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
					linea.setColumns((cont + "").length());
					repaint();
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
				p.push("s0");
				error.setText("");
				cod.setText("");
				cod.append("#include <stdio.h> \n");
				cod.append("int main()\n");
				cod.append("{ \n");
				
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
		                    	if (elemento.equals("int")|elemento.equals("float")|elemento.equals("char"))	
		                    		tipo = lexer.lexeme;
		                    	if(elemento.equals("do"))
		                    	{
		                    		doo=true;
		                    	}
		                    	if(elemento.equals("while"))
		                    	{
		                    		doo=false;
		                    		whi=true;
		                    	}
		                    	if(elemento.equals("switch"))
		                    		pc=true;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico(lexer.lexeme,con);
		                    	break;
		                    case id:
		                    	ids [ind][0]= lexer.lexeme;
		                    	ids [ind][1]= tipo;
		                    	elemento = lexer.lexeme;
		                    	ele=lexer.lexeme;
		                    	if(tipo.equals(""))
		                    		banTipo=true;
		                    	ind++;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico("id",con);
		                    	break;
		                    case num:
		                    	elemento = lexer.lexeme;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico("num",con);
		                    	break;
		                    case numf:
		                    	elemento = lexer.lexeme;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico("numf",con);
		                    	break;
		                    case litcar:
		                    	elemento = lexer.lexeme;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico("litcar",con);
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
		                    	pc=false;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico(";",con);
		                    	break;
		                    case dospuntos:	
		                    	elemento = lexer.lexeme;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico(":",con);
		                    	break;
		                    case leeri:
		                    	pc=true;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + "%i" + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico("%i",con);
		                    	break;
		                    case leerf:
		                    	pc=true;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + "%f" + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico("%f",con);
		                    	break;
		                    case relacional:
		                    	elemento = lexer.lexeme;
		                    	resultado += "No. Linea: " + con  + "\t ---> \t " + lexer.lexeme + "\t ---> \t " + tokens +  "\n";
		                    	Sintactico(lexer.lexeme,con);
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
			
			if(!pc)
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
	                		
	                		if(banTipo) 
	            			{
	                			System.out.println(elemento + " TIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPOOOOOOOOOOOOOOOOOOOSSSSSSSSSSSS");
	            				if(!whi)
	            				{
	            					contTipo++;
		            				conttTipo++;
		            				cod.append(pS.peek()+" v"+ (conttTipo)+";\n");
		            				cod.append("v"+ conttTipo +" = "+ elemento+";\n");
	            				}
	            			}
	                		break;
	            			
	            		}
	            		else
	            			tipo = true;
	            	}
	            }
			}
			
			if(tipo)
				errorS = false;
			
			if(!this.tipo.equals("")) 
			{
				cod.append(pS.peek()+ " "+ elemento +";\n");
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
			
			if(banTipo && !pc && !i.equals("$")) 
			{
				contTipo++;
				conttTipo++;
				cod.append("int v"+ conttTipo+";\n");
				cod.append("v"+ conttTipo +" = "+ elemento+";\n");
			}
		}
		
		if (i.equals("numf"))
		{
			pS.push("float");
			
			if(banTipo && !pc) 
			{
				contTipo++;
				conttTipo++;
				cod.append("float v"+ conttTipo+";\n");
				cod.append("v"+ conttTipo +" = "+ elemento+";\n");
			}
		}
		
//		if (i.equals("litcar"))
//		{
//			pS.push("char");
//			
//			if(banTipo && !pc) 
//			{
//				contTipo++;
//				conttTipo++;
//				cod.append("char v"+ conttTipo+";\n");
//				cod.append("v"+ conttTipo +" = "+ elemento+";\n");
//			}
//		}
		
		if(i.equals("do") && !pc)
		{
			doos++;
			cod.append("do"+doos+":\n");
		}
		
		if(i.equals("while") && !pc)
		{
			int iiind=0;
			iiind= at.getText().indexOf("while");
			cod.append("if "+at.getText().subSequence((iiind)+5, (iiind)+15)+" goto do"+doos+";\n");
			cod.append("return 0;\n");
			cod.append("} \n");
			
		}
		
		if(i.equals("switch"))
		{
			swi++;	
		}
		
		if(i.equals("case"))
		{
			int iiind=0;
			
			cas++;
			if(cas==2)
			{
				cod.append("case"+(cas-1)+":\n");
				iiind= at.getText().indexOf("switch");
				cod.append("if ( !(" + at.getText().substring((iiind)+8, (iiind)+10)+" == ");
				iiind= at.getText().indexOf("case");
				
				cod.append(" '2' )) goto case"+cas+";\n");
				
			}
			else
			{
				iiind= at.getText().indexOf("switch");
				cod.append("if ( !(" + at.getText().substring((iiind)+8, (iiind)+10)+" == ");
				iiind= at.getText().indexOf("case");
				cod.append(at.getText().substring((iiind)+4, (iiind)+8)+" )) goto case"+(cas)+";\n");
				
			}
		}
		
		if(i.equals("leer"))
		{
			lee++;
			int iiind=0;
			cod.append("scanf ");
			iiind= at.getText().indexOf("leer");
			cod.append(at.getText().substring((iiind)+4, (iiind)+14)+" &"+at.getText().substring((iiind)+14, (iiind)+17)+";\n");
//			at.getText().replace(at.getText().charAt((iiind)), 'x');
//			repaint();
		}
		
		if(i.equals("imprimir"))
		{
			imp++;
			int iiind=0;
			cod.append("printf ");
			iiind= at.getText().indexOf("imprimir");
			cod.append(at.getText().substring((iiind)+8, (iiind)+22)+"\n");
			
		}
		
		if(i.equals("break"))
		{
			int iiind=0;
			br++;
			iiind= at.getText().indexOf("break");
			cod.append("goto endswitch"+swi+";\n");
			
		}
		
		if(i.equals("endS"))
		{
			cod.append("case"+(cas)+":\n");
			cod.append("endswitch"+swi+":\n");
		}
			
        if(!(tabla[ren][col].equals(""))) {
        	
        	do {
        		
        		this.Buscar(i);
        		
        		ind = tabla[ren][col].indexOf('>');
        		
        		if(ind != -1) {
        			prod = tabla[ren][col].substring(ind+1);
            		System.out.println("COMPLETA "+tabla[ren][col]);
            		System.out.println(ren);
            		System.out.println(col);
            		System.out.println("PRODUCCIÓN "+prod);  
            		StringTokenizer st = new StringTokenizer(prod,"_");  
            	     while (st.hasMoreTokens()) {
            	    	 token = st.nextToken();
            	    	 System.out.println(token);
            	    	 if(token.equals("@"))            	    	 
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
            	    		return;            	    	 
            	    	}
            	     }
            	     
            	     
            	     red = tabla[ren][col].substring(0, ind);
            	     System.out.println(red);
            	     
            	     
            	     for (int j = 1; j < 215; j++) {
            		     
            	        	if (tabla[j-1][0].equals(p.peek()))
            	        	{
            	        		ren = j-1;
            	        	}
            	        	
            	        }
            			
            	      for (int j = 1; j < 49; j++) {
            	     
            	       	if (tabla[0][j-1].equals(red))
            	       	{
            	       		col = j-1;
            	       		p.push(red); 
            	       		p.push(tabla[ren][col]);
            	       		
            	       		System.out.println(p);
            	            System.out.println(ren);
            	            System.out.println(col);
            	            System.out.println(prod);
            	            if(prod.contains("+") && !errorS && !i.equals("$"))
            	            {
            	            	this.pilaSemantica();
            	            	cod.append("v"+ (conttTipo-2) +" = " + "v"+ (conttTipo-1) + " + v"+ (conttTipo)+";\n");
            	            	contTipo--;
            	            	if(errorS) {
            	            		error.append("Linea de Error   -->   "+errorLine);
            	            		error.append("\nNo es posible sumar los tipos de datos\t" + izq + " + "+ der + " ");
            	            	}
            	            }
            	            else if (prod.contains("-") && !errorS && !i.equals("$"))
            	            {
            	            	this.pilaSemantica();
            	            	cod.append("v"+ (conttTipo-2) +" = " + "v"+ (conttTipo-1) + " - v"+ (conttTipo)+";\n");
            	            	contTipo--;
            	            	if(errorS) {
            	            		error.append("Linea de Error   -->   "+errorLine);
            	            		error.append("\nNo es posible restar los tipos de datos\t" + izq + " - "+ der + " ");
            	            	}
            	            }
            	            else if(prod.contains("*") && !errorS && !i.equals("$"))
            	            {
            	            	this.pilaSemantica();
            	            	cod.append("v"+ (conttTipo-2) +" = " + "v"+ (conttTipo-1) + " * v"+ (conttTipo)+";\n");
            	            	contTipo--;
            	            	if(errorS) {
            	            		error.append("Linea de Error   -->   "+errorLine);
            	            		error.append("\nNo es posible multiplicar los tipos de datos\t" + izq + " * "+ der + " ");
            	            	}
            	            }
            	            else if (prod.contains("/") && !errorS && !i.equals("$")) 
            	            {
            	            	this.pilaSemantica();
            	            	cod.append("v"+ (conttTipo-2) +" = " + "v"+ (conttTipo-1) + " / v"+ (conttTipo)+";\n");
            	            	contTipo--;
            	            	if(errorS) {
            	            		error.append("Linea de Error   -->   "+errorLine);
            	            		error.append("\nNo es posible dividir los tipos de datos\t" + izq + " / "+ der + " ");
            	            	}
            	            }
//            	            else if (prod.contains("<") && !errorS && !i.equals("$")) 
//            	            {
//            	            	this.pilaSemantica();
//            	            	cod.append("v"+ (conttTipo-1) +" = " + "v"+ (conttTipo-1) + " < v"+ (conttTipo)+";\n");
//            	            	contTipo--;
//            	            	if(errorS) {
//            	            		error.append("Linea de Error   -->   "+errorLine);
//            	            		error.append("\nNo es posible dividir los tipos de datos\t" + izq + " / "+ der + " ");
//            	            	}
//            	            }
//            	            else if (prod.contentEquals("==") && !errorS && !i.equals("$")) 
//            	            {
//            	            	this.pilaSemantica();
//            	            	cod.append("v"+ (conttTipo-1) +" = " + "v"+ (conttTipo-1) + " == v"+ (conttTipo)+";\n");
//            	            	contTipo--;
//            	            	if(errorS) {
//            	            		error.append("Linea de Error   -->   "+errorLine);
//            	            		error.append("\nNo es posible dividir los tipos de datos\t" + izq + " / "+ der + " ");
//            	            	}
//            	            }
            	            else if (prod.contains("=") && !errorS && !i.equals("$"))
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
            	            	
            	            	cod.append(asign +" = " + "v"+ (contTipo)+";\n");
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
		for (int j = 1; j < 215; j++) {
		     
        	if (tabla[j-1][0].equals(p.peek()))
        	{
        		ren = j-1;
        	}
        	
        }
		
        for (int j = 1; j < 49; j++) {
     
        	if (tabla[0][j-1].equals(i))
        	{
        		col = j-1;
        	}
        	
        }
	}
	
	private String Esperaba (int ren) {
		String esp="";
	
		for (int j = 1; j < 49; j++) {
	         
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
	
	public void tabla1(){
		tabla[0][0]="";	    tabla[0][1]="id";	        tabla[0][2]=";";	tabla[0][3]="int";	tabla[0][4]="float";	tabla[0][5]="char";	tabla[0][6]=",";	tabla[0][7]="=";	tabla[0][8]="do";	tabla[0][9]="while";	tabla[0][10]="(";	tabla[0][11]=")";	tabla[0][12]="switch";	tabla[0][13]="endS";	tabla[0][14]="imprimir";	tabla[0][15]="leer";	tabla[0][16]="%i";	tabla[0][17]="%f";	tabla[0][18]="case";	tabla[0][19]="litcar";	tabla[0][20]=":";	tabla[0][21]="break";	tabla[0][22]="==";	tabla[0][23]="<";	tabla[0][24]=">";	tabla[0][25]="+";	tabla[0][26]="-";	tabla[0][27]="*";	tabla[0][28]="/";	tabla[0][29]="num";	tabla[0][30]="numf";	tabla[0][31]="$";	tabla[0][32]="P'";	tabla[0][33]="P";	tabla[0][34]="D";	tabla[0][35]="Tipo";	tabla[0][36]="Sd";	tabla[0][37]="S";	tabla[0][38]="Dt";	tabla[0][39]="Caso";	tabla[0][40]="CasoS";	tabla[0][41]="Cond";	tabla[0][42]="Comp";	tabla[0][43]="Exp";	tabla[0][44]="E";	tabla[0][45]="Term";	tabla[0][46]="T";	tabla[0][47]="F";
		tabla[1][0]="s0";	tabla[1][1]="D>@";	        tabla[1][2]="";	tabla[1][3]="s4";	tabla[1][4]="s5";	tabla[1][5]="s6";	tabla[1][6]="";	tabla[1][7]="";	tabla[1][8]="D>@";	tabla[1][9]="";	tabla[1][10]="";	tabla[1][11]="";	tabla[1][12]="D>@";	tabla[1][13]="";	tabla[1][14]="D>@";	tabla[1][15]="D>@";	tabla[1][16]="";	tabla[1][17]="";	tabla[1][18]="";	tabla[1][19]="";	tabla[1][20]="";	tabla[1][21]="";	tabla[1][22]="";	tabla[1][23]="";	tabla[1][24]="";	tabla[1][25]="";	tabla[1][26]="";	tabla[1][27]="";	tabla[1][28]="";	tabla[1][29]="";	tabla[1][30]="";	tabla[1][31]="D>@";	tabla[1][32]="";	tabla[1][33]="s1";	tabla[1][34]="s2";	tabla[1][35]="s3";	tabla[1][36]="";	tabla[1][37]="";	tabla[1][38]="";	tabla[1][39]="";	tabla[1][40]="";	tabla[1][41]="";	tabla[1][42]="";	tabla[1][43]="";	tabla[1][44]="";	tabla[1][45]="";	tabla[1][46]="";	tabla[1][47]="";
		tabla[2][0]="s1";	tabla[2][1]="";	            tabla[2][2]="";	tabla[2][3]="";	tabla[2][4]="";	tabla[2][5]="";	tabla[2][6]="";	tabla[2][7]="";	tabla[2][8]="";	tabla[2][9]="";	tabla[2][10]="";	tabla[2][11]="";	tabla[2][12]="";	tabla[2][13]="";	tabla[2][14]="";	tabla[2][15]="";	tabla[2][16]="";	tabla[2][17]="";	tabla[2][18]="";	tabla[2][19]="";	tabla[2][20]="";	tabla[2][21]="";	tabla[2][22]="";	tabla[2][23]="";	tabla[2][24]="";	tabla[2][25]="";	tabla[2][26]="";	tabla[2][27]="";	tabla[2][28]="";	tabla[2][29]="";	tabla[2][30]="";	tabla[2][31]="SE ACEPTA";	tabla[2][32]="";	tabla[2][33]="";	tabla[2][34]="";	tabla[2][35]="";	tabla[2][36]="";	tabla[2][37]="";	tabla[2][38]="";	tabla[2][39]="";	tabla[2][40]="";	tabla[2][41]="";	tabla[2][42]="";	tabla[2][43]="";	tabla[2][44]="";	tabla[2][45]="";	tabla[2][46]="";	tabla[2][47]="";
		tabla[3][0]="s2";	tabla[3][1]="s8";	        tabla[3][2]="";	tabla[3][3]="";	tabla[3][4]="";	tabla[3][5]="";	tabla[3][6]="";	tabla[3][7]="";	tabla[3][8]="s9";	tabla[3][9]="";	tabla[3][10]="";	tabla[3][11]="";	tabla[3][12]="s10";	tabla[3][13]="";	tabla[3][14]="s11";	tabla[3][15]="s12";	tabla[3][16]="";	tabla[3][17]="";	tabla[3][18]="";	tabla[3][19]="";	tabla[3][20]="";	tabla[3][21]="";	tabla[3][22]="";	tabla[3][23]="";	tabla[3][24]="";	tabla[3][25]="";	tabla[3][26]="";	tabla[3][27]="";	tabla[3][28]="";	tabla[3][29]="";	tabla[3][30]="";	tabla[3][31]="S>@";	tabla[3][32]="";	tabla[3][33]="";	tabla[3][34]="";	tabla[3][35]="";	tabla[3][36]="";	tabla[3][37]="s7";	tabla[3][38]="";	tabla[3][39]="";	tabla[3][40]="";	tabla[3][41]="";	tabla[3][42]="";	tabla[3][43]="";	tabla[3][44]="";	tabla[3][45]="";	tabla[3][46]="";	tabla[3][47]="";
		tabla[4][0]="s3";	tabla[4][1]="s13";	        tabla[4][2]="";	tabla[4][3]="";	tabla[4][4]="";	tabla[4][5]="";	tabla[4][6]="";	tabla[4][7]="";	tabla[4][8]="";	tabla[4][9]="";	tabla[4][10]="";	tabla[4][11]="";	tabla[4][12]="";	tabla[4][13]="";	tabla[4][14]="";	tabla[4][15]="";	tabla[4][16]="";	tabla[4][17]="";	tabla[4][18]="";	tabla[4][19]="";	tabla[4][20]="";	tabla[4][21]="";	tabla[4][22]="";	tabla[4][23]="";	tabla[4][24]="";	tabla[4][25]="";	tabla[4][26]="";	tabla[4][27]="";	tabla[4][28]="";	tabla[4][29]="";	tabla[4][30]="";	tabla[4][31]="";	tabla[4][32]="";	tabla[4][33]="";	tabla[4][34]="";	tabla[4][35]="";	tabla[4][36]="";	tabla[4][37]="";	tabla[4][38]="";	tabla[4][39]="";	tabla[4][40]="";	tabla[4][41]="";	tabla[4][42]="";	tabla[4][43]="";	tabla[4][44]="";	tabla[4][45]="";	tabla[4][46]="";	tabla[4][47]="";
		tabla[5][0]="s4";	tabla[5][1]="Tipo>int";	    tabla[5][2]="";	tabla[5][3]="";	tabla[5][4]="";	tabla[5][5]="";	tabla[5][6]="";	tabla[5][7]="";	tabla[5][8]="";	tabla[5][9]="";	tabla[5][10]="";	tabla[5][11]="";	tabla[5][12]="";	tabla[5][13]="";	tabla[5][14]="";	tabla[5][15]="";	tabla[5][16]="";	tabla[5][17]="";	tabla[5][18]="";	tabla[5][19]="";	tabla[5][20]="";	tabla[5][21]="";	tabla[5][22]="";	tabla[5][23]="";	tabla[5][24]="";	tabla[5][25]="";	tabla[5][26]="";	tabla[5][27]="";	tabla[5][28]="";	tabla[5][29]="";	tabla[5][30]="";	tabla[5][31]="";	tabla[5][32]="";	tabla[5][33]="";	tabla[5][34]="";	tabla[5][35]="";	tabla[5][36]="";	tabla[5][37]="";	tabla[5][38]="";	tabla[5][39]="";	tabla[5][40]="";	tabla[5][41]="";	tabla[5][42]="";	tabla[5][43]="";	tabla[5][44]="";	tabla[5][45]="";	tabla[5][46]="";	tabla[5][47]="";
		tabla[6][0]="s5";	tabla[6][1]="Tipo>float";	tabla[6][2]="";	tabla[6][3]="";	tabla[6][4]="";	tabla[6][5]="";	tabla[6][6]="";	tabla[6][7]="";	tabla[6][8]="";	tabla[6][9]="";	tabla[6][10]="";	tabla[6][11]="";	tabla[6][12]="";	tabla[6][13]="";	tabla[6][14]="";	tabla[6][15]="";	tabla[6][16]="";	tabla[6][17]="";	tabla[6][18]="";	tabla[6][19]="";	tabla[6][20]="";	tabla[6][21]="";	tabla[6][22]="";	tabla[6][23]="";	tabla[6][24]="";	tabla[6][25]="";	tabla[6][26]="";	tabla[6][27]="";	tabla[6][28]="";	tabla[6][29]="";	tabla[6][30]="";	tabla[6][31]="";	tabla[6][32]="";	tabla[6][33]="";	tabla[6][34]="";	tabla[6][35]="";	tabla[6][36]="";	tabla[6][37]="";	tabla[6][38]="";	tabla[6][39]="";	tabla[6][40]="";	tabla[6][41]="";	tabla[6][42]="";	tabla[6][43]="";	tabla[6][44]="";	tabla[6][45]="";	tabla[6][46]="";	tabla[6][47]="";
		tabla[7][0]="s6";	tabla[7][1]="Tipo>char";	tabla[7][2]="";	tabla[7][3]="";	tabla[7][4]="";	tabla[7][5]="";	tabla[7][6]="";	tabla[7][7]="";	tabla[7][8]="";	tabla[7][9]="";	tabla[7][10]="";	tabla[7][11]="";	tabla[7][12]="";	tabla[7][13]="";	tabla[7][14]="";	tabla[7][15]="";	tabla[7][16]="";	tabla[7][17]="";	tabla[7][18]="";	tabla[7][19]="";	tabla[7][20]="";	tabla[7][21]="";	tabla[7][22]="";	tabla[7][23]="";	tabla[7][24]="";	tabla[7][25]="";	tabla[7][26]="";	tabla[7][27]="";	tabla[7][28]="";	tabla[7][29]="";	tabla[7][30]="";	tabla[7][31]="";	tabla[7][32]="";	tabla[7][33]="";	tabla[7][34]="";	tabla[7][35]="";	tabla[7][36]="";	tabla[7][37]="";	tabla[7][38]="";	tabla[7][39]="";	tabla[7][40]="";	tabla[7][41]="";	tabla[7][42]="";	tabla[7][43]="";	tabla[7][44]="";	tabla[7][45]="";	tabla[7][46]="";	tabla[7][47]="";
		tabla[8][0]="s7";	tabla[8][1]="";         	tabla[8][2]="";	tabla[8][3]="";	tabla[8][4]="";	tabla[8][5]="";	tabla[8][6]="";	tabla[8][7]="";	tabla[8][8]="";	tabla[8][9]="";	tabla[8][10]="";	tabla[8][11]="";	tabla[8][12]="";	tabla[8][13]="";	tabla[8][14]="";	tabla[8][15]="";	tabla[8][16]="";	tabla[8][17]="";	tabla[8][18]="";	tabla[8][19]="";	tabla[8][20]="";	tabla[8][21]="";	tabla[8][22]="";	tabla[8][23]="";	tabla[8][24]="";	tabla[8][25]="";	tabla[8][26]="";	tabla[8][27]="";	tabla[8][28]="";	tabla[8][29]="";	tabla[8][30]="";	tabla[8][31]="P>S_D";	tabla[8][32]="";	tabla[8][33]="";	tabla[8][34]="";	tabla[8][35]="";	tabla[8][36]="";	tabla[8][37]="";	tabla[8][38]="";	tabla[8][39]="";	tabla[8][40]="";	tabla[8][41]="";	tabla[8][42]="";	tabla[8][43]="";	tabla[8][44]="";	tabla[8][45]="";	tabla[8][46]="";	tabla[8][47]="";
		tabla[9][0]="s8";	tabla[9][1]="";	            tabla[9][2]="";	tabla[9][3]="";	tabla[9][4]="";	tabla[9][5]="";	tabla[9][6]="";	tabla[9][7]="s14";	tabla[9][8]="";	tabla[9][9]="";	tabla[9][10]="";	tabla[9][11]="";	tabla[9][12]="";	tabla[9][13]="";	tabla[9][14]="";	tabla[9][15]="";	tabla[9][16]="";	tabla[9][17]="";	tabla[9][18]="";	tabla[9][19]="";	tabla[9][20]="";	tabla[9][21]="";	tabla[9][22]="";	tabla[9][23]="";	tabla[9][24]="";	tabla[9][25]="";	tabla[9][26]="";	tabla[9][27]="";	tabla[9][28]="";	tabla[9][29]="";	tabla[9][30]="";	tabla[9][31]="";	tabla[9][32]="";	tabla[9][33]="";	tabla[9][34]="";	tabla[9][35]="";	tabla[9][36]="";	tabla[9][37]="";	tabla[9][38]="";	tabla[9][39]="";	tabla[9][40]="";	tabla[9][41]="";	tabla[9][42]="";	tabla[9][43]="";	tabla[9][44]="";	tabla[9][45]="";	tabla[9][46]="";	tabla[9][47]="";
		tabla[10][0]="s9";	tabla[10][1]="D>@";	        tabla[10][2]="";	tabla[10][3]="s4";	tabla[10][4]="s5";	tabla[10][5]="s6";	tabla[10][6]="";	tabla[10][7]="";	tabla[10][8]="D>@";	tabla[10][9]="D>@";	tabla[10][10]="";	tabla[10][11]="";	tabla[10][12]="D>@";	tabla[10][13]="";	tabla[10][14]="D>@";	tabla[10][15]="D>@";	tabla[10][16]="";	tabla[10][17]="";	tabla[10][18]="";	tabla[10][19]="";	tabla[10][20]="";	tabla[10][21]="";	tabla[10][22]="";	tabla[10][23]="";	tabla[10][24]="";	tabla[10][25]="";	tabla[10][26]="";	tabla[10][27]="";	tabla[10][28]="";	tabla[10][29]="";	tabla[10][30]="";	tabla[10][31]="";	tabla[10][32]="";	tabla[10][33]="s15";	tabla[10][34]="s16";	tabla[10][35]="s17";	tabla[10][36]="";	tabla[10][37]="";	tabla[10][38]="";	tabla[10][39]="";	tabla[10][40]="";	tabla[10][41]="";	tabla[10][42]="";	tabla[10][43]="";	tabla[10][44]="";	tabla[10][45]="";	tabla[10][46]="";	tabla[10][47]="";
		tabla[11][0]="s10";	tabla[11][1]="";	        tabla[11][2]="";	tabla[11][3]="";	tabla[11][4]="";	tabla[11][5]="";	tabla[11][6]="";	tabla[11][7]="";	tabla[11][8]="";	tabla[11][9]="";	tabla[11][10]="s18";	tabla[11][11]="";	tabla[11][12]="";	tabla[11][13]="";	tabla[11][14]="";	tabla[11][15]="";	tabla[11][16]="";	tabla[11][17]="";	tabla[11][18]="";	tabla[11][19]="";	tabla[11][20]="";	tabla[11][21]="";	tabla[11][22]="";	tabla[11][23]="";	tabla[11][24]="";	tabla[11][25]="";	tabla[11][26]="";	tabla[11][27]="";	tabla[11][28]="";	tabla[11][29]="";	tabla[11][30]="";	tabla[11][31]="";	tabla[11][32]="";	tabla[11][33]="";	tabla[11][34]="";	tabla[11][35]="";	tabla[11][36]="";	tabla[11][37]="";	tabla[11][38]="";	tabla[11][39]="";	tabla[11][40]="";	tabla[11][41]="";	tabla[11][42]="";	tabla[11][43]="";	tabla[11][44]="";	tabla[11][45]="";	tabla[11][46]="";	tabla[11][47]="";
		tabla[12][0]="s11";	tabla[12][1]="";	        tabla[12][2]="";	tabla[12][3]="";	tabla[12][4]="";	tabla[12][5]="";	tabla[12][6]="";	tabla[12][7]="";	tabla[12][8]="";	tabla[12][9]="";	tabla[12][10]="s19";	tabla[12][11]="";	tabla[12][12]="";	tabla[12][13]="";	tabla[12][14]="";	tabla[12][15]="";	tabla[12][16]="";	tabla[12][17]="";	tabla[12][18]="";	tabla[12][19]="";	tabla[12][20]="";	tabla[12][21]="";	tabla[12][22]="";	tabla[12][23]="";	tabla[12][24]="";	tabla[12][25]="";	tabla[12][26]="";	tabla[12][27]="";	tabla[12][28]="";	tabla[12][29]="";	tabla[12][30]="";	tabla[12][31]="";	tabla[12][32]="";	tabla[12][33]="";	tabla[12][34]="";	tabla[12][35]="";	tabla[12][36]="";	tabla[12][37]="";	tabla[12][38]="";	tabla[12][39]="";	tabla[12][40]="";	tabla[12][41]="";	tabla[12][42]="";	tabla[12][43]="";	tabla[12][44]="";	tabla[12][45]="";	tabla[12][46]="";	tabla[12][47]="";
		tabla[13][0]="s12";	tabla[13][1]="";	        tabla[13][2]="";	tabla[13][3]="";	tabla[13][4]="";	tabla[13][5]="";	tabla[13][6]="";	tabla[13][7]="";	tabla[13][8]="";	tabla[13][9]="";	tabla[13][10]="s20";	tabla[13][11]="";	tabla[13][12]="";	tabla[13][13]="";	tabla[13][14]="";	tabla[13][15]="";	tabla[13][16]="";	tabla[13][17]="";	tabla[13][18]="";	tabla[13][19]="";	tabla[13][20]="";	tabla[13][21]="";	tabla[13][22]="";	tabla[13][23]="";	tabla[13][24]="";	tabla[13][25]="";	tabla[13][26]="";	tabla[13][27]="";	tabla[13][28]="";	tabla[13][29]="";	tabla[13][30]="";	tabla[13][31]="";	tabla[13][32]="";	tabla[13][33]="";	tabla[13][34]="";	tabla[13][35]="";	tabla[13][36]="";	tabla[13][37]="";	tabla[13][38]="";	tabla[13][39]="";	tabla[13][40]="";	tabla[13][41]="";	tabla[13][42]="";	tabla[13][43]="";	tabla[13][44]="";	tabla[13][45]="";	tabla[13][46]="";	tabla[13][47]="";
		tabla[14][0]="s13";	tabla[14][1]="";	        tabla[14][2]="Sd>@";	tabla[14][3]="";	tabla[14][4]="";	tabla[14][5]="";	tabla[14][6]="s22";	tabla[14][7]="";	tabla[14][8]="";	tabla[14][9]="";	tabla[14][10]="";	tabla[14][11]="";	tabla[14][12]="";	tabla[14][13]="";	tabla[14][14]="";	tabla[14][15]="";	tabla[14][16]="";	tabla[14][17]="";	tabla[14][18]="";	tabla[14][19]="";	tabla[14][20]="";	tabla[14][21]="";	tabla[14][22]="";	tabla[14][23]="";	tabla[14][24]="";	tabla[14][25]="";	tabla[14][26]="";	tabla[14][27]="";	tabla[14][28]="";	tabla[14][29]="";	tabla[14][30]="";	tabla[14][31]="";	tabla[14][32]="";	tabla[14][33]="";	tabla[14][34]="";	tabla[14][35]="";	tabla[14][36]="s21";	tabla[14][37]="";	tabla[14][38]="";	tabla[14][39]="";	tabla[14][40]="";	tabla[14][41]="";	tabla[14][42]="";	tabla[14][43]="";	tabla[14][44]="";	tabla[14][45]="";	tabla[14][46]="";	tabla[14][47]="";
		tabla[15][0]="s14";	tabla[15][1]="s26";	        tabla[15][2]="";	tabla[15][3]="";	tabla[15][4]="";	tabla[15][5]="";	tabla[15][6]="";	tabla[15][7]="";	tabla[15][8]="";	tabla[15][9]="";	tabla[15][10]="s27";	tabla[15][11]="";	tabla[15][12]="";	tabla[15][13]="";	tabla[15][14]="";	tabla[15][15]="";	tabla[15][16]="";	tabla[15][17]="";	tabla[15][18]="";	tabla[15][19]="s30";	tabla[15][20]="";	tabla[15][21]="";	tabla[15][22]="";	tabla[15][23]="";	tabla[15][24]="";	tabla[15][25]="";	tabla[15][26]="";	tabla[15][27]="";	tabla[15][28]="";	tabla[15][29]="s28";	tabla[15][30]="s29";	tabla[15][31]="";	tabla[15][32]="";	tabla[15][33]="";	tabla[15][34]="";	tabla[15][35]="";	tabla[15][36]="";	tabla[15][37]="";	tabla[15][38]="";	tabla[15][39]="";	tabla[15][40]="";	tabla[15][41]="";	tabla[15][42]="";	tabla[15][43]="s23";	tabla[15][44]="";	tabla[15][45]="s24";	tabla[15][46]="";	tabla[15][47]="s25";
		tabla[16][0]="s15";	tabla[16][1]="";	        tabla[16][2]="";	tabla[16][3]="";	tabla[16][4]="";	tabla[16][5]="";	tabla[16][6]="";	tabla[16][7]="";	tabla[16][8]="";	tabla[16][9]="s31";	tabla[16][10]="";	tabla[16][11]="";	tabla[16][12]="";	tabla[16][13]="";	tabla[16][14]="";	tabla[16][15]="";	tabla[16][16]="";	tabla[16][17]="";	tabla[16][18]="";	tabla[16][19]="";	tabla[16][20]="";	tabla[16][21]="";	tabla[16][22]="";	tabla[16][23]="";	tabla[16][24]="";	tabla[16][25]="";	tabla[16][26]="";	tabla[16][27]="";	tabla[16][28]="";	tabla[16][29]="";	tabla[16][30]="";	tabla[16][31]="";	tabla[16][32]="";	tabla[16][33]="";	tabla[16][34]="";	tabla[16][35]="";	tabla[16][36]="";	tabla[16][37]="";	tabla[16][38]="";	tabla[16][39]="";	tabla[16][40]="";	tabla[16][41]="";	tabla[16][42]="";	tabla[16][43]="";	tabla[16][44]="";	tabla[16][45]="";	tabla[16][46]="";	tabla[16][47]="";
		tabla[17][0]="s16";	tabla[17][1]="s33";	        tabla[17][2]="";	tabla[17][3]="";	tabla[17][4]="";	tabla[17][5]="";	tabla[17][6]="";	tabla[17][7]="";	tabla[17][8]="s34";	tabla[17][9]="S>@";	tabla[17][10]="";	tabla[17][11]="";	tabla[17][12]="s35";	tabla[17][13]="";	tabla[17][14]="s36";	tabla[17][15]="s37";	tabla[17][16]="";	tabla[17][17]="";	tabla[17][18]="";	tabla[17][19]="";	tabla[17][20]="";	tabla[17][21]="";	tabla[17][22]="";	tabla[17][23]="";	tabla[17][24]="";	tabla[17][25]="";	tabla[17][26]="";	tabla[17][27]="";	tabla[17][28]="";	tabla[17][29]="";	tabla[17][30]="";	tabla[17][31]="";	tabla[17][32]="";	tabla[17][33]="";	tabla[17][34]="";	tabla[17][35]="";	tabla[17][36]="";	tabla[17][37]="s32";	tabla[17][38]="";	tabla[17][39]="";	tabla[17][40]="";	tabla[17][41]="";	tabla[17][42]="";	tabla[17][43]="";	tabla[17][44]="";	tabla[17][45]="";	tabla[17][46]="";	tabla[17][47]="";
		tabla[18][0]="s17";	tabla[18][1]="s38";     	tabla[18][2]="";	tabla[18][3]="";	tabla[18][4]="";	tabla[18][5]="";	tabla[18][6]="";	tabla[18][7]="";	tabla[18][8]="";	tabla[18][9]="";	tabla[18][10]="";	tabla[18][11]="";	tabla[18][12]="";	tabla[18][13]="";	tabla[18][14]="";	tabla[18][15]="";	tabla[18][16]="";	tabla[18][17]="";	tabla[18][18]="";	tabla[18][19]="";	tabla[18][20]="";	tabla[18][21]="";	tabla[18][22]="";	tabla[18][23]="";	tabla[18][24]="";	tabla[18][25]="";	tabla[18][26]="";	tabla[18][27]="";	tabla[18][28]="";	tabla[18][29]="";	tabla[18][30]="";	tabla[18][31]="";	tabla[18][32]="";	tabla[18][33]="";	tabla[18][34]="";	tabla[18][35]="";	tabla[18][36]="";	tabla[18][37]="";	tabla[18][38]="";	tabla[18][39]="";	tabla[18][40]="";	tabla[18][41]="";	tabla[18][42]="";	tabla[18][43]="";	tabla[18][44]="";	tabla[18][45]="";	tabla[18][46]="";	tabla[18][47]="";
		tabla[19][0]="s18";	tabla[19][1]="s39";	        tabla[19][2]="";	tabla[19][3]="";	tabla[19][4]="";	tabla[19][5]="";	tabla[19][6]="";	tabla[19][7]="";	tabla[19][8]="";	tabla[19][9]="";	tabla[19][10]="";	tabla[19][11]="";	tabla[19][12]="";	tabla[19][13]="";	tabla[19][14]="";	tabla[19][15]="";	tabla[19][16]="";	tabla[19][17]="";	tabla[19][18]="";	tabla[19][19]="";	tabla[19][20]="";	tabla[19][21]="";	tabla[19][22]="";	tabla[19][23]="";	tabla[19][24]="";	tabla[19][25]="";	tabla[19][26]="";	tabla[19][27]="";	tabla[19][28]="";	tabla[19][29]="";	tabla[19][30]="";	tabla[19][31]="";	tabla[19][32]="";	tabla[19][33]="";	tabla[19][34]="";	tabla[19][35]="";	tabla[19][36]="";	tabla[19][37]="";	tabla[19][38]="";	tabla[19][39]="";	tabla[19][40]="";	tabla[19][41]="";	tabla[19][42]="";	tabla[19][43]="";	tabla[19][44]="";	tabla[19][45]="";	tabla[19][46]="";	tabla[19][47]="";
		tabla[20][0]="s19";	tabla[20][1]="";	        tabla[20][2]="";	tabla[20][3]="";	tabla[20][4]="";	tabla[20][5]="";	tabla[20][6]="";	tabla[20][7]="";	tabla[20][8]="";	tabla[20][9]="";	tabla[20][10]="";	tabla[20][11]="";	tabla[20][12]="";	tabla[20][13]="";	tabla[20][14]="";	tabla[20][15]="";	tabla[20][16]="s41";	tabla[20][17]="s42";	tabla[20][18]="";	tabla[20][19]="";	tabla[20][20]="";	tabla[20][21]="";	tabla[20][22]="";	tabla[20][23]="";	tabla[20][24]="";	tabla[20][25]="";	tabla[20][26]="";	tabla[20][27]="";	tabla[20][28]="";	tabla[20][29]="";	tabla[20][30]="";	tabla[20][31]="";	tabla[20][32]="";	tabla[20][33]="";	tabla[20][34]="";	tabla[20][35]="";	tabla[20][36]="";	tabla[20][37]="";	tabla[20][38]="s40";	tabla[20][39]="";	tabla[20][40]="";	tabla[20][41]="";	tabla[20][42]="";	tabla[20][43]="";	tabla[20][44]="";	tabla[20][45]="";	tabla[20][46]="";	tabla[20][47]="";
		tabla[21][0]="s20";	tabla[21][1]="";	        tabla[21][2]="";	tabla[21][3]="";	tabla[21][4]="";	tabla[21][5]="";	tabla[21][6]="";	tabla[21][7]="";	tabla[21][8]="";	tabla[21][9]="";	tabla[21][10]="";	tabla[21][11]="";	tabla[21][12]="";	tabla[21][13]="";	tabla[21][14]="";	tabla[21][15]="";	tabla[21][16]="s41";	tabla[21][17]="s42";	tabla[21][18]="";	tabla[21][19]="";	tabla[21][20]="";	tabla[21][21]="";	tabla[21][22]="";	tabla[21][23]="";	tabla[21][24]="";	tabla[21][25]="";	tabla[21][26]="";	tabla[21][27]="";	tabla[21][28]="";	tabla[21][29]="";	tabla[21][30]="";	tabla[21][31]="";	tabla[21][32]="";	tabla[21][33]="";	tabla[21][34]="";	tabla[21][35]="";	tabla[21][36]="";	tabla[21][37]="";	tabla[21][38]="s43";	tabla[21][39]="";	tabla[21][40]="";	tabla[21][41]="";	tabla[21][42]="";	tabla[21][43]="";	tabla[21][44]="";	tabla[21][45]="";	tabla[21][46]="";	tabla[21][47]="";
		tabla[22][0]="s21";	tabla[22][1]="";	        tabla[22][2]="s44";	tabla[22][3]="";	tabla[22][4]="";	tabla[22][5]="";	tabla[22][6]="";	tabla[22][7]="";	tabla[22][8]="";	tabla[22][9]="";	tabla[22][10]="";	tabla[22][11]="";	tabla[22][12]="";	tabla[22][13]="";	tabla[22][14]="";	tabla[22][15]="";	tabla[22][16]="";	tabla[22][17]="";	tabla[22][18]="";	tabla[22][19]="";	tabla[22][20]="";	tabla[22][21]="";	tabla[22][22]="";	tabla[22][23]="";	tabla[22][24]="";	tabla[22][25]="";	tabla[22][26]="";	tabla[22][27]="";	tabla[22][28]="";	tabla[22][29]="";	tabla[22][30]="";	tabla[22][31]="";	tabla[22][32]="";	tabla[22][33]="";	tabla[22][34]="";	tabla[22][35]="";	tabla[22][36]="";	tabla[22][37]="";	tabla[22][38]="";	tabla[22][39]="";	tabla[22][40]="";	tabla[22][41]="";	tabla[22][42]="";	tabla[22][43]="";	tabla[22][44]="";	tabla[22][45]="";	tabla[22][46]="";	tabla[22][47]="";
		tabla[23][0]="s22";	tabla[23][1]="s45";	        tabla[23][2]="";	tabla[23][3]="";	tabla[23][4]="";	tabla[23][5]="";	tabla[23][6]="";	tabla[23][7]="";	tabla[23][8]="";	tabla[23][9]="";	tabla[23][10]="";	tabla[23][11]="";	tabla[23][12]="";	tabla[23][13]="";	tabla[23][14]="";	tabla[23][15]="";	tabla[23][16]="";	tabla[23][17]="";	tabla[23][18]="";	tabla[23][19]="";	tabla[23][20]="";	tabla[23][21]="";	tabla[23][22]="";	tabla[23][23]="";	tabla[23][24]="";	tabla[23][25]="";	tabla[23][26]="";	tabla[23][27]="";	tabla[23][28]="";	tabla[23][29]="";	tabla[23][30]="";	tabla[23][31]="";	tabla[23][32]="";	tabla[23][33]="";	tabla[23][34]="";	tabla[23][35]="";	tabla[23][36]="";	tabla[23][37]="";	tabla[23][38]="";	tabla[23][39]="";	tabla[23][40]="";	tabla[23][41]="";	tabla[23][42]="";	tabla[23][43]="";	tabla[23][44]="";	tabla[23][45]="";	tabla[23][46]="";	tabla[23][47]="";
		tabla[24][0]="s23";	tabla[24][1]="";	        tabla[24][2]="s46";	tabla[24][3]="";	tabla[24][4]="";	tabla[24][5]="";	tabla[24][6]="";	tabla[24][7]="";	tabla[24][8]="";	tabla[24][9]="";	tabla[24][10]="";	tabla[24][11]="";	tabla[24][12]="";	tabla[24][13]="";	tabla[24][14]="";	tabla[24][15]="";	tabla[24][16]="";	tabla[24][17]="";	tabla[24][18]="";	tabla[24][19]="";	tabla[24][20]="";	tabla[24][21]="";	tabla[24][22]="";	tabla[24][23]="";	tabla[24][24]="";	tabla[24][25]="";	tabla[24][26]="";	tabla[24][27]="";	tabla[24][28]="";	tabla[24][29]="";	tabla[24][30]="";	tabla[24][31]="";	tabla[24][32]="";	tabla[24][33]="";	tabla[24][34]="";	tabla[24][35]="";	tabla[24][36]="";	tabla[24][37]="";	tabla[24][38]="";	tabla[24][39]="";	tabla[24][40]="";	tabla[24][41]="";	tabla[24][42]="";	tabla[24][43]="";	tabla[24][44]="";	tabla[24][45]="";	tabla[24][46]="";	tabla[24][47]="";
		tabla[25][0]="s24";	tabla[25][1]="";	        tabla[25][2]="E>@";	tabla[25][3]="";	tabla[25][4]="";	tabla[25][5]="";	tabla[25][6]="";	tabla[25][7]="";	tabla[25][8]="";	tabla[25][9]="";	tabla[25][10]="";	tabla[25][11]="";	tabla[25][12]="";	tabla[25][13]="";	tabla[25][14]="";	tabla[25][15]="";	tabla[25][16]="";	tabla[25][17]="";	tabla[25][18]="";	tabla[25][19]="";	tabla[25][20]="";	tabla[25][21]="";	tabla[25][22]="";	tabla[25][23]="";	tabla[25][24]="";	tabla[25][25]="s48";	tabla[25][26]="s49";	tabla[25][27]="";	tabla[25][28]="";	tabla[25][29]="";	tabla[25][30]="";	tabla[25][31]="";	tabla[25][32]="";	tabla[25][33]="";	tabla[25][34]="";	tabla[25][35]="";	tabla[25][36]="";	tabla[25][37]="";	tabla[25][38]="";	tabla[25][39]="";	tabla[25][40]="";	tabla[25][41]="";	tabla[25][42]="";	tabla[25][43]="";	tabla[25][44]="s47";	tabla[25][45]="";	tabla[25][46]="";	tabla[25][47]="";
		tabla[26][0]="s25";	tabla[26][1]="";	        tabla[26][2]="T>@";	tabla[26][3]="";	tabla[26][4]="";	tabla[26][5]="";	tabla[26][6]="";	tabla[26][7]="";	tabla[26][8]="";	tabla[26][9]="";	tabla[26][10]="";	tabla[26][11]="";	tabla[26][12]="";	tabla[26][13]="";	tabla[26][14]="";	tabla[26][15]="";	tabla[26][16]="";	tabla[26][17]="";	tabla[26][18]="";	tabla[26][19]="";	tabla[26][20]="";	tabla[26][21]="";	tabla[26][22]="";	tabla[26][23]="";	tabla[26][24]="";	tabla[26][25]="T>@";	tabla[26][26]="T>@";	tabla[26][27]="s51";	tabla[26][28]="s52";	tabla[26][29]="";	tabla[26][30]="";	tabla[26][31]="";	tabla[26][32]="";	tabla[26][33]="";	tabla[26][34]="";	tabla[26][35]="";	tabla[26][36]="";	tabla[26][37]="";	tabla[26][38]="";	tabla[26][39]="";	tabla[26][40]="";	tabla[26][41]="";	tabla[26][42]="";	tabla[26][43]="";	tabla[26][44]="";	tabla[26][45]="";	tabla[26][46]="s50";	tabla[26][47]="";
		tabla[27][0]="s26";	tabla[27][1]="";	        tabla[27][2]="F>id";	tabla[27][3]="";	tabla[27][4]="";	tabla[27][5]="";	tabla[27][6]="";	tabla[27][7]="";	tabla[27][8]="";	tabla[27][9]="";	tabla[27][10]="";	tabla[27][11]="";	tabla[27][12]="";	tabla[27][13]="";	tabla[27][14]="";	tabla[27][15]="";	tabla[27][16]="";	tabla[27][17]="";	tabla[27][18]="";	tabla[27][19]="";	tabla[27][20]="";	tabla[27][21]="";	tabla[27][22]="";	tabla[27][23]="";	tabla[27][24]="";	tabla[27][25]="F>id";	tabla[27][26]="F>id";	tabla[27][27]="F>id";	tabla[27][28]="F>id";	tabla[27][29]="";	tabla[27][30]="";	tabla[27][31]="";	tabla[27][32]="";	tabla[27][33]="";	tabla[27][34]="";	tabla[27][35]="";	tabla[27][36]="";	tabla[27][37]="";	tabla[27][38]="";	tabla[27][39]="";	tabla[27][40]="";	tabla[27][41]="";	tabla[27][42]="";	tabla[27][43]="";	tabla[27][44]="";	tabla[27][45]="";	tabla[27][46]="";	tabla[27][47]="";
		tabla[28][0]="s27";	tabla[28][1]="s56";	        tabla[28][2]="";	tabla[28][3]="";	tabla[28][4]="";	tabla[28][5]="";	tabla[28][6]="";	tabla[28][7]="";	tabla[28][8]="";	tabla[28][9]="";	tabla[28][10]="s57";	tabla[28][11]="";	tabla[28][12]="";	tabla[28][13]="";	tabla[28][14]="";	tabla[28][15]="";	tabla[28][16]="";	tabla[28][17]="";	tabla[28][18]="";	tabla[28][19]="s60";	tabla[28][20]="";	tabla[28][21]="";	tabla[28][22]="";	tabla[28][23]="";	tabla[28][24]="";	tabla[28][25]="";	tabla[28][26]="";	tabla[28][27]="";	tabla[28][28]="";	tabla[28][29]="s58";	tabla[28][30]="s59";	tabla[28][31]="";	tabla[28][32]="";	tabla[28][33]="";	tabla[28][34]="";	tabla[28][35]="";	tabla[28][36]="";	tabla[28][37]="";	tabla[28][38]="";	tabla[28][39]="";	tabla[28][40]="";	tabla[28][41]="";	tabla[28][42]="";	tabla[28][43]="s53";	tabla[28][44]="";	tabla[28][45]="s54";	tabla[28][46]="";	tabla[28][47]="s55";
		tabla[29][0]="s28";	tabla[29][1]="";	        tabla[29][2]="F>num";	tabla[29][3]="";	tabla[29][4]="";	tabla[29][5]="";	tabla[29][6]="";	tabla[29][7]="";	tabla[29][8]="";	tabla[29][9]="";	tabla[29][10]="";	tabla[29][11]="";	tabla[29][12]="";	tabla[29][13]="";	tabla[29][14]="";	tabla[29][15]="";	tabla[29][16]="";	tabla[29][17]="";	tabla[29][18]="";	tabla[29][19]="";	tabla[29][20]="";	tabla[29][21]="";	tabla[29][22]="";	tabla[29][23]="";	tabla[29][24]="";	tabla[29][25]="F>num";	tabla[29][26]="F>num";	tabla[29][27]="F>num";	tabla[29][28]="F>num";	tabla[29][29]="";	tabla[29][30]="";	tabla[29][31]="";	tabla[29][32]="";	tabla[29][33]="";	tabla[29][34]="";	tabla[29][35]="";	tabla[29][36]="";	tabla[29][37]="";	tabla[29][38]="";	tabla[29][39]="";	tabla[29][40]="";	tabla[29][41]="";	tabla[29][42]="";	tabla[29][43]="";	tabla[29][44]="";	tabla[29][45]="";	tabla[29][46]="";	tabla[29][47]="";
		tabla[30][0]="s29";	tabla[30][1]="";	        tabla[30][2]="F>numf";	tabla[30][3]="";	tabla[30][4]="";	tabla[30][5]="";	tabla[30][6]="";	tabla[30][7]="";	tabla[30][8]="";	tabla[30][9]="";	tabla[30][10]="";	tabla[30][11]="";	tabla[30][12]="";	tabla[30][13]="";	tabla[30][14]="";	tabla[30][15]="";	tabla[30][16]="";	tabla[30][17]="";	tabla[30][18]="";	tabla[30][19]="";	tabla[30][20]="";	tabla[30][21]="";	tabla[30][22]="";	tabla[30][23]="";	tabla[30][24]="";	tabla[30][25]="F>numf";	tabla[30][26]="F>numf";	tabla[30][27]="F>numf";	tabla[30][28]="F>numf";	tabla[30][29]="";	tabla[30][30]="";	tabla[30][31]="";	tabla[30][32]="";	tabla[30][33]="";	tabla[30][34]="";	tabla[30][35]="";	tabla[30][36]="";	tabla[30][37]="";	tabla[30][38]="";	tabla[30][39]="";	tabla[30][40]="";	tabla[30][41]="";	tabla[30][42]="";	tabla[30][43]="";	tabla[30][44]="";	tabla[30][45]="";	tabla[30][46]="";	tabla[30][47]="";
		tabla[31][0]="s30";	tabla[31][1]="";	        tabla[31][2]="F>litcar";	tabla[31][3]="";	tabla[31][4]="";	tabla[31][5]="";	tabla[31][6]="";	tabla[31][7]="";	tabla[31][8]="";	tabla[31][9]="";	tabla[31][10]="";	tabla[31][11]="";	tabla[31][12]="";	tabla[31][13]="";	tabla[31][14]="";	tabla[31][15]="";	tabla[31][16]="";	tabla[31][17]="";	tabla[31][18]="";	tabla[31][19]="";	tabla[31][20]="";	tabla[31][21]="";	tabla[31][22]="";	tabla[31][23]="";	tabla[31][24]="";	tabla[31][25]="F>litcar";	tabla[31][26]="F>litcar";	tabla[31][27]="F>litcar";	tabla[31][28]="F>litcar";	tabla[31][29]="";	tabla[31][30]="";	tabla[31][31]="";	tabla[31][32]="";	tabla[31][33]="";	tabla[31][34]="";	tabla[31][35]="";	tabla[31][36]="";	tabla[31][37]="";	tabla[31][38]="";	tabla[31][39]="";	tabla[31][40]="";	tabla[31][41]="";	tabla[31][42]="";	tabla[31][43]="";	tabla[31][44]="";	tabla[31][45]="";	tabla[31][46]="";	tabla[31][47]="";
		tabla[32][0]="s31";	tabla[32][1]="";	        tabla[32][2]="";	tabla[32][3]="";	tabla[32][4]="";	tabla[32][5]="";	tabla[32][6]="";	tabla[32][7]="";	tabla[32][8]="";	tabla[32][9]="";	tabla[32][10]="s61";	tabla[32][11]="";	tabla[32][12]="";	tabla[32][13]="";	tabla[32][14]="";	tabla[32][15]="";	tabla[32][16]="";	tabla[32][17]="";	tabla[32][18]="";	tabla[32][19]="";	tabla[32][20]="";	tabla[32][21]="";	tabla[32][22]="";	tabla[32][23]="";	tabla[32][24]="";	tabla[32][25]="";	tabla[32][26]="";	tabla[32][27]="";	tabla[32][28]="";	tabla[32][29]="";	tabla[32][30]="";	tabla[32][31]="";	tabla[32][32]="";	tabla[32][33]="";	tabla[32][34]="";	tabla[32][35]="";	tabla[32][36]="";	tabla[32][37]="";	tabla[32][38]="";	tabla[32][39]="";	tabla[32][40]="";	tabla[32][41]="";	tabla[32][42]="";	tabla[32][43]="";	tabla[32][44]="";	tabla[32][45]="";	tabla[32][46]="";	tabla[32][47]="";
		tabla[33][0]="s32";	tabla[33][1]="";	        tabla[33][2]="";	tabla[33][3]="";	tabla[33][4]="";	tabla[33][5]="";	tabla[33][6]="";	tabla[33][7]="";	tabla[33][8]="";	tabla[33][9]="P>S_D";	tabla[33][10]="";	tabla[33][11]="";	tabla[33][12]="";	tabla[33][13]="";	tabla[33][14]="";	tabla[33][15]="";	tabla[33][16]="";	tabla[33][17]="";	tabla[33][18]="";	tabla[33][19]="";	tabla[33][20]="";	tabla[33][21]="";	tabla[33][22]="";	tabla[33][23]="";	tabla[33][24]="";	tabla[33][25]="";	tabla[33][26]="";	tabla[33][27]="";	tabla[33][28]="";	tabla[33][29]="";	tabla[33][30]="";	tabla[33][31]="";	tabla[33][32]="";	tabla[33][33]="";	tabla[33][34]="";	tabla[33][35]="";	tabla[33][36]="";	tabla[33][37]="";	tabla[33][38]="";	tabla[33][39]="";	tabla[33][40]="";	tabla[33][41]="";	tabla[33][42]="";	tabla[33][43]="";	tabla[33][44]="";	tabla[33][45]="";	tabla[33][46]="";	tabla[33][47]="";
		tabla[34][0]="s33";	tabla[34][1]="";	        tabla[34][2]="";	tabla[34][3]="";	tabla[34][4]="";	tabla[34][5]="";	tabla[34][6]="";	tabla[34][7]="s62";	tabla[34][8]="";	tabla[34][9]="";	tabla[34][10]="";	tabla[34][11]="";	tabla[34][12]="";	tabla[34][13]="";	tabla[34][14]="";	tabla[34][15]="";	tabla[34][16]="";	tabla[34][17]="";	tabla[34][18]="";	tabla[34][19]="";	tabla[34][20]="";	tabla[34][21]="";	tabla[34][22]="";	tabla[34][23]="";	tabla[34][24]="";	tabla[34][25]="";	tabla[34][26]="";	tabla[34][27]="";	tabla[34][28]="";	tabla[34][29]="";	tabla[34][30]="";	tabla[34][31]="";	tabla[34][32]="";	tabla[34][33]="";	tabla[34][34]="";	tabla[34][35]="";	tabla[34][36]="";	tabla[34][37]="";	tabla[34][38]="";	tabla[34][39]="";	tabla[34][40]="";	tabla[34][41]="";	tabla[34][42]="";	tabla[34][43]="";	tabla[34][44]="";	tabla[34][45]="";	tabla[34][46]="";	tabla[34][47]="";
		tabla[35][0]="s34";	tabla[35][1]="D>@";	        tabla[35][2]="";	tabla[35][3]="s4";	tabla[35][4]="s5";	tabla[35][5]="s6";	tabla[35][6]="";	tabla[35][7]="";	tabla[35][8]="D>@";	tabla[35][9]="D>@";	tabla[35][10]="";	tabla[35][11]="";	tabla[35][12]="D>@";	tabla[35][13]="";	tabla[35][14]="D>@";	tabla[35][15]="D>@";	tabla[35][16]="";	tabla[35][17]="";	tabla[35][18]="";	tabla[35][19]="";	tabla[35][20]="";	tabla[35][21]="";	tabla[35][22]="";	tabla[35][23]="";	tabla[35][24]="";	tabla[35][25]="";	tabla[35][26]="";	tabla[35][27]="";	tabla[35][28]="";	tabla[35][29]="";	tabla[35][30]="";	tabla[35][31]="";	tabla[35][32]="";	tabla[35][33]="s63";	tabla[35][34]="s16";	tabla[35][35]="s17";	tabla[35][36]="";	tabla[35][37]="";	tabla[35][38]="";	tabla[35][39]="";	tabla[35][40]="";	tabla[35][41]="";	tabla[35][42]="";	tabla[35][43]="";	tabla[35][44]="";	tabla[35][45]="";	tabla[35][46]="";	tabla[35][47]="";
		tabla[36][0]="s35";	tabla[36][1]="";	        tabla[36][2]="";	tabla[36][3]="";	tabla[36][4]="";	tabla[36][5]="";	tabla[36][6]="";	tabla[36][7]="";	tabla[36][8]="";	tabla[36][9]="";	tabla[36][10]="s64";	tabla[36][11]="";	tabla[36][12]="";	tabla[36][13]="";	tabla[36][14]="";	tabla[36][15]="";	tabla[36][16]="";	tabla[36][17]="";	tabla[36][18]="";	tabla[36][19]="";	tabla[36][20]="";	tabla[36][21]="";	tabla[36][22]="";	tabla[36][23]="";	tabla[36][24]="";	tabla[36][25]="";	tabla[36][26]="";	tabla[36][27]="";	tabla[36][28]="";	tabla[36][29]="";	tabla[36][30]="";	tabla[36][31]="";	tabla[36][32]="";	tabla[36][33]="";	tabla[36][34]="";	tabla[36][35]="";	tabla[36][36]="";	tabla[36][37]="";	tabla[36][38]="";	tabla[36][39]="";	tabla[36][40]="";	tabla[36][41]="";	tabla[36][42]="";	tabla[36][43]="";	tabla[36][44]="";	tabla[36][45]="";	tabla[36][46]="";	tabla[36][47]="";
		tabla[37][0]="s36";	tabla[37][1]="";	        tabla[37][2]="";	tabla[37][3]="";	tabla[37][4]="";	tabla[37][5]="";	tabla[37][6]="";	tabla[37][7]="";	tabla[37][8]="";	tabla[37][9]="";	tabla[37][10]="s65";	tabla[37][11]="";	tabla[37][12]="";	tabla[37][13]="";	tabla[37][14]="";	tabla[37][15]="";	tabla[37][16]="";	tabla[37][17]="";	tabla[37][18]="";	tabla[37][19]="";	tabla[37][20]="";	tabla[37][21]="";	tabla[37][22]="";	tabla[37][23]="";	tabla[37][24]="";	tabla[37][25]="";	tabla[37][26]="";	tabla[37][27]="";	tabla[37][28]="";	tabla[37][29]="";	tabla[37][30]="";	tabla[37][31]="";	tabla[37][32]="";	tabla[37][33]="";	tabla[37][34]="";	tabla[37][35]="";	tabla[37][36]="";	tabla[37][37]="";	tabla[37][38]="";	tabla[37][39]="";	tabla[37][40]="";	tabla[37][41]="";	tabla[37][42]="";	tabla[37][43]="";	tabla[37][44]="";	tabla[37][45]="";	tabla[37][46]="";	tabla[37][47]="";
		tabla[38][0]="s37";	tabla[38][1]="";	        tabla[38][2]="";	tabla[38][3]="";	tabla[38][4]="";	tabla[38][5]="";	tabla[38][6]="";	tabla[38][7]="";	tabla[38][8]="";	tabla[38][9]="";	tabla[38][10]="s66";	tabla[38][11]="";	tabla[38][12]="";	tabla[38][13]="";	tabla[38][14]="";	tabla[38][15]="";	tabla[38][16]="";	tabla[38][17]="";	tabla[38][18]="";	tabla[38][19]="";	tabla[38][20]="";	tabla[38][21]="";	tabla[38][22]="";	tabla[38][23]="";	tabla[38][24]="";	tabla[38][25]="";	tabla[38][26]="";	tabla[38][27]="";	tabla[38][28]="";	tabla[38][29]="";	tabla[38][30]="";	tabla[38][31]="";	tabla[38][32]="";	tabla[38][33]="";	tabla[38][34]="";	tabla[38][35]="";	tabla[38][36]="";	tabla[38][37]="";	tabla[38][38]="";	tabla[38][39]="";	tabla[38][40]="";	tabla[38][41]="";	tabla[38][42]="";	tabla[38][43]="";	tabla[38][44]="";	tabla[38][45]="";	tabla[38][46]="";	tabla[38][47]="";
		tabla[39][0]="s38";	tabla[39][1]="";	        tabla[39][2]="Sd>@";	tabla[39][3]="";	tabla[39][4]="";	tabla[39][5]="";	tabla[39][6]="s22";	tabla[39][7]="";	tabla[39][8]="";	tabla[39][9]="";	tabla[39][10]="";	tabla[39][11]="";	tabla[39][12]="";	tabla[39][13]="";	tabla[39][14]="";	tabla[39][15]="";	tabla[39][16]="";	tabla[39][17]="";	tabla[39][18]="";	tabla[39][19]="";	tabla[39][20]="";	tabla[39][21]="";	tabla[39][22]="";	tabla[39][23]="";	tabla[39][24]="";	tabla[39][25]="";	tabla[39][26]="";	tabla[39][27]="";	tabla[39][28]="";	tabla[39][29]="";	tabla[39][30]="";	tabla[39][31]="";	tabla[39][32]="";	tabla[39][33]="";	tabla[39][34]="";	tabla[39][35]="";	tabla[39][36]="s67";	tabla[39][37]="";	tabla[39][38]="";	tabla[39][39]="";	tabla[39][40]="";	tabla[39][41]="";	tabla[39][42]="";	tabla[39][43]="";	tabla[39][44]="";	tabla[39][45]="";	tabla[39][46]="";	tabla[39][47]="";
		tabla[40][0]="s39";	tabla[40][1]="";	        tabla[40][2]="";	tabla[40][3]="";	tabla[40][4]="";	tabla[40][5]="";	tabla[40][6]="";	tabla[40][7]="";	tabla[40][8]="";	tabla[40][9]="";	tabla[40][10]="";	tabla[40][11]="s68";	tabla[40][12]="";	tabla[40][13]="";	tabla[40][14]="";	tabla[40][15]="";	tabla[40][16]="";	tabla[40][17]="";	tabla[40][18]="";	tabla[40][19]="";	tabla[40][20]="";	tabla[40][21]="";	tabla[40][22]="";	tabla[40][23]="";	tabla[40][24]="";	tabla[40][25]="";	tabla[40][26]="";	tabla[40][27]="";	tabla[40][28]="";	tabla[40][29]="";	tabla[40][30]="";	tabla[40][31]="";	tabla[40][32]="";	tabla[40][33]="";	tabla[40][34]="";	tabla[40][35]="";	tabla[40][36]="";	tabla[40][37]="";	tabla[40][38]="";	tabla[40][39]="";	tabla[40][40]="";	tabla[40][41]="";	tabla[40][42]="";	tabla[40][43]="";	tabla[40][44]="";	tabla[40][45]="";	tabla[40][46]="";	tabla[40][47]="";
		tabla[41][0]="s40";	tabla[41][1]="";	        tabla[41][2]="";	tabla[41][3]="";	tabla[41][4]="";	tabla[41][5]="";	tabla[41][6]="s69";	tabla[41][7]="";	tabla[41][8]="";	tabla[41][9]="";	tabla[41][10]="";	tabla[41][11]="";	tabla[41][12]="";	tabla[41][13]="";	tabla[41][14]="";	tabla[41][15]="";	tabla[41][16]="";	tabla[41][17]="";	tabla[41][18]="";	tabla[41][19]="";	tabla[41][20]="";	tabla[41][21]="";	tabla[41][22]="";	tabla[41][23]="";	tabla[41][24]="";	tabla[41][25]="";	tabla[41][26]="";	tabla[41][27]="";	tabla[41][28]="";	tabla[41][29]="";	tabla[41][30]="";	tabla[41][31]="";	tabla[41][32]="";	tabla[41][33]="";	tabla[41][34]="";	tabla[41][35]="";	tabla[41][36]="";	tabla[41][37]="";	tabla[41][38]="";	tabla[41][39]="";	tabla[41][40]="";	tabla[41][41]="";	tabla[41][42]="";	tabla[41][43]="";	tabla[41][44]="";	tabla[41][45]="";	tabla[41][46]="";	tabla[41][47]="";
		tabla[42][0]="s41";	tabla[42][1]="";	        tabla[42][2]="";	tabla[42][3]="";	tabla[42][4]="";	tabla[42][5]="";	tabla[42][6]="Dt>%i";	tabla[42][7]="";	tabla[42][8]="";	tabla[42][9]="";	tabla[42][10]="";	tabla[42][11]="";	tabla[42][12]="";	tabla[42][13]="";	tabla[42][14]="";	tabla[42][15]="";	tabla[42][16]="";	tabla[42][17]="";	tabla[42][18]="";	tabla[42][19]="";	tabla[42][20]="";	tabla[42][21]="";	tabla[42][22]="";	tabla[42][23]="";	tabla[42][24]="";	tabla[42][25]="";	tabla[42][26]="";	tabla[42][27]="";	tabla[42][28]="";	tabla[42][29]="";	tabla[42][30]="";	tabla[42][31]="";	tabla[42][32]="";	tabla[42][33]="";	tabla[42][34]="";	tabla[42][35]="";	tabla[42][36]="";	tabla[42][37]="";	tabla[42][38]="";	tabla[42][39]="";	tabla[42][40]="";	tabla[42][41]="";	tabla[42][42]="";	tabla[42][43]="";	tabla[42][44]="";	tabla[42][45]="";	tabla[42][46]="";	tabla[42][47]="";
		tabla[43][0]="s42";	tabla[43][1]="";	        tabla[43][2]="";	tabla[43][3]="";	tabla[43][4]="";	tabla[43][5]="";	tabla[43][6]="Dt>%f";	tabla[43][7]="";	tabla[43][8]="";	tabla[43][9]="";	tabla[43][10]="";	tabla[43][11]="";	tabla[43][12]="";	tabla[43][13]="";	tabla[43][14]="";	tabla[43][15]="";	tabla[43][16]="";	tabla[43][17]="";	tabla[43][18]="";	tabla[43][19]="";	tabla[43][20]="";	tabla[43][21]="";	tabla[43][22]="";	tabla[43][23]="";	tabla[43][24]="";	tabla[43][25]="";	tabla[43][26]="";	tabla[43][27]="";	tabla[43][28]="";	tabla[43][29]="";	tabla[43][30]="";	tabla[43][31]="";	tabla[43][32]="";	tabla[43][33]="";	tabla[43][34]="";	tabla[43][35]="";	tabla[43][36]="";	tabla[43][37]="";	tabla[43][38]="";	tabla[43][39]="";	tabla[43][40]="";	tabla[43][41]="";	tabla[43][42]="";	tabla[43][43]="";	tabla[43][44]="";	tabla[43][45]="";	tabla[43][46]="";	tabla[43][47]="";
		tabla[44][0]="s43";	tabla[44][1]="";	        tabla[44][2]="";	tabla[44][3]="";	tabla[44][4]="";	tabla[44][5]="";	tabla[44][6]="s70";	tabla[44][7]="";	tabla[44][8]="";	tabla[44][9]="";	tabla[44][10]="";	tabla[44][11]="";	tabla[44][12]="";	tabla[44][13]="";	tabla[44][14]="";	tabla[44][15]="";	tabla[44][16]="";	tabla[44][17]="";	tabla[44][18]="";	tabla[44][19]="";	tabla[44][20]="";	tabla[44][21]="";	tabla[44][22]="";	tabla[44][23]="";	tabla[44][24]="";	tabla[44][25]="";	tabla[44][26]="";	tabla[44][27]="";	tabla[44][28]="";	tabla[44][29]="";	tabla[44][30]="";	tabla[44][31]="";	tabla[44][32]="";	tabla[44][33]="";	tabla[44][34]="";	tabla[44][35]="";	tabla[44][36]="";	tabla[44][37]="";	tabla[44][38]="";	tabla[44][39]="";	tabla[44][40]="";	tabla[44][41]="";	tabla[44][42]="";	tabla[44][43]="";	tabla[44][44]="";	tabla[44][45]="";	tabla[44][46]="";	tabla[44][47]="";
		tabla[45][0]="s44";	tabla[45][1]="D>@";	        tabla[45][2]="";	tabla[45][3]="s4";	tabla[45][4]="s5";	tabla[45][5]="s6";	tabla[45][6]="";	tabla[45][7]="";	tabla[45][8]="D>@";	tabla[45][9]="";	tabla[45][10]="";	tabla[45][11]="";	tabla[45][12]="D>@";	tabla[45][13]="";	tabla[45][14]="D>@";	tabla[45][15]="D>@";	tabla[45][16]="";	tabla[45][17]="";	tabla[45][18]="";	tabla[45][19]="";	tabla[45][20]="";	tabla[45][21]="";	tabla[45][22]="";	tabla[45][23]="";	tabla[45][24]="";	tabla[45][25]="";	tabla[45][26]="";	tabla[45][27]="";	tabla[45][28]="";	tabla[45][29]="";	tabla[45][30]="";	tabla[45][31]="D>@";	tabla[45][32]="";	tabla[45][33]="";	tabla[45][34]="s71";	tabla[45][35]="s3";	tabla[45][36]="";	tabla[45][37]="";	tabla[45][38]="";	tabla[45][39]="";	tabla[45][40]="";	tabla[45][41]="";	tabla[45][42]="";	tabla[45][43]="";	tabla[45][44]="";	tabla[45][45]="";	tabla[45][46]="";	tabla[45][47]="";
		tabla[46][0]="s45";	tabla[46][1]="";	        tabla[46][2]="Sd>@";	tabla[46][3]="";	tabla[46][4]="";	tabla[46][5]="";	tabla[46][6]="s22";	tabla[46][7]="";	tabla[46][8]="";	tabla[46][9]="";	tabla[46][10]="";	tabla[46][11]="";	tabla[46][12]="";	tabla[46][13]="";	tabla[46][14]="";	tabla[46][15]="";	tabla[46][16]="";	tabla[46][17]="";	tabla[46][18]="";	tabla[46][19]="";	tabla[46][20]="";	tabla[46][21]="";	tabla[46][22]="";	tabla[46][23]="";	tabla[46][24]="";	tabla[46][25]="";	tabla[46][26]="";	tabla[46][27]="";	tabla[46][28]="";	tabla[46][29]="";	tabla[46][30]="";	tabla[46][31]="";	tabla[46][32]="";	tabla[46][33]="";	tabla[46][34]="";	tabla[46][35]="";	tabla[46][36]="s72";	tabla[46][37]="";	tabla[46][38]="";	tabla[46][39]="";	tabla[46][40]="";	tabla[46][41]="";	tabla[46][42]="";	tabla[46][43]="";	tabla[46][44]="";	tabla[46][45]="";	tabla[46][46]="";	tabla[46][47]="";
		tabla[47][0]="s46";	tabla[47][1]="s8";	        tabla[47][2]="";	tabla[47][3]="";	tabla[47][4]="";	tabla[47][5]="";	tabla[47][6]="";	tabla[47][7]="";	tabla[47][8]="s9";	tabla[47][9]="";	tabla[47][10]="";	tabla[47][11]="";	tabla[47][12]="s10";	tabla[47][13]="";	tabla[47][14]="s11";	tabla[47][15]="s12";	tabla[47][16]="";	tabla[47][17]="";	tabla[47][18]="";	tabla[47][19]="";	tabla[47][20]="";	tabla[47][21]="";	tabla[47][22]="";	tabla[47][23]="";	tabla[47][24]="";	tabla[47][25]="";	tabla[47][26]="";	tabla[47][27]="";	tabla[47][28]="";	tabla[47][29]="";	tabla[47][30]="";	tabla[47][31]="S>@";	tabla[47][32]="";	tabla[47][33]="";	tabla[47][34]="";	tabla[47][35]="";	tabla[47][36]="";	tabla[47][37]="s73";	tabla[47][38]="";	tabla[47][39]="";	tabla[47][40]="";	tabla[47][41]="";	tabla[47][42]="";	tabla[47][43]="";	tabla[47][44]="";	tabla[47][45]="";	tabla[47][46]="";	tabla[47][47]="";
		tabla[48][0]="s47";	tabla[48][1]="";	        tabla[48][2]="Exp>E_Term";	tabla[48][3]="";	tabla[48][4]="";	tabla[48][5]="";	tabla[48][6]="";	tabla[48][7]="";	tabla[48][8]="";	tabla[48][9]="";	tabla[48][10]="";	tabla[48][11]="";	tabla[48][12]="";	tabla[48][13]="";	tabla[48][14]="";	tabla[48][15]="";	tabla[48][16]="";	tabla[48][17]="";	tabla[48][18]="";	tabla[48][19]="";	tabla[48][20]="";	tabla[48][21]="";	tabla[48][22]="";	tabla[48][23]="";	tabla[48][24]="";	tabla[48][25]="";	tabla[48][26]="";	tabla[48][27]="";	tabla[48][28]="";	tabla[48][29]="";	tabla[48][30]="";	tabla[48][31]="";	tabla[48][32]="";	tabla[48][33]="";	tabla[48][34]="";	tabla[48][35]="";	tabla[48][36]="";	tabla[48][37]="";	tabla[48][38]="";	tabla[48][39]="";	tabla[48][40]="";	tabla[48][41]="";	tabla[48][42]="";	tabla[48][43]="";	tabla[48][44]="";	tabla[48][45]="";	tabla[48][46]="";	tabla[48][47]="";
		tabla[49][0]="s48";	tabla[49][1]="s26";	        tabla[49][2]="";	tabla[49][3]="";	tabla[49][4]="";	tabla[49][5]="";	tabla[49][6]="";	tabla[49][7]="";	tabla[49][8]="";	tabla[49][9]="";	tabla[49][10]="s27";	tabla[49][11]="";	tabla[49][12]="";	tabla[49][13]="";	tabla[49][14]="";	tabla[49][15]="";	tabla[49][16]="";	tabla[49][17]="";	tabla[49][18]="";	tabla[49][19]="s30";	tabla[49][20]="";	tabla[49][21]="";	tabla[49][22]="";	tabla[49][23]="";	tabla[49][24]="";	tabla[49][25]="";	tabla[49][26]="";	tabla[49][27]="";	tabla[49][28]="";	tabla[49][29]="s28";	tabla[49][30]="s29";	tabla[49][31]="";	tabla[49][32]="";	tabla[49][33]="";	tabla[49][34]="";	tabla[49][35]="";	tabla[49][36]="";	tabla[49][37]="";	tabla[49][38]="";	tabla[49][39]="";	tabla[49][40]="";	tabla[49][41]="";	tabla[49][42]="";	tabla[49][43]="";	tabla[49][44]="";	tabla[49][45]="s74";	tabla[49][46]="";	tabla[49][47]="s25";
		tabla[50][0]="s49";	tabla[50][1]="s26";	        tabla[50][2]="";	tabla[50][3]="";	tabla[50][4]="";	tabla[50][5]="";	tabla[50][6]="";	tabla[50][7]="";	tabla[50][8]="";	tabla[50][9]="";	tabla[50][10]="s27";	tabla[50][11]="";	tabla[50][12]="";	tabla[50][13]="";	tabla[50][14]="";	tabla[50][15]="";	tabla[50][16]="";	tabla[50][17]="";	tabla[50][18]="";	tabla[50][19]="s30";	tabla[50][20]="";	tabla[50][21]="";	tabla[50][22]="";	tabla[50][23]="";	tabla[50][24]="";	tabla[50][25]="";	tabla[50][26]="";	tabla[50][27]="";	tabla[50][28]="";	tabla[50][29]="s28";	tabla[50][30]="s29";	tabla[50][31]="";	tabla[50][32]="";	tabla[50][33]="";	tabla[50][34]="";	tabla[50][35]="";	tabla[50][36]="";	tabla[50][37]="";	tabla[50][38]="";	tabla[50][39]="";	tabla[50][40]="";	tabla[50][41]="";	tabla[50][42]="";	tabla[50][43]="";	tabla[50][44]="";	tabla[50][45]="s75";	tabla[50][46]="";	tabla[50][47]="s25";
		
	}
	
	public void tabla2() {
		tabla[51][0]="s50";	tabla[51][1]="";	        tabla[51][2]="Term>T_F";	tabla[51][3]="";	tabla[51][4]="";	tabla[51][5]="";	tabla[51][6]="";	tabla[51][7]="";	tabla[51][8]="";	tabla[51][9]="";	tabla[51][10]="";	tabla[51][11]="";	tabla[51][12]="";	tabla[51][13]="";	tabla[51][14]="";	tabla[51][15]="";	tabla[51][16]="";	tabla[51][17]="";	tabla[51][18]="";	tabla[51][19]="";	tabla[51][20]="";	tabla[51][21]="";	tabla[51][22]="";	tabla[51][23]="";	tabla[51][24]="";	tabla[51][25]="Term>T_F";	tabla[51][26]="Term>T_F";	tabla[51][27]="";	tabla[51][28]="";	tabla[51][29]="";	tabla[51][30]="";	tabla[51][31]="";	tabla[51][32]="";	tabla[51][33]="";	tabla[51][34]="";	tabla[51][35]="";	tabla[51][36]="";	tabla[51][37]="";	tabla[51][38]="";	tabla[51][39]="";	tabla[51][40]="";	tabla[51][41]="";	tabla[51][42]="";	tabla[51][43]="";	tabla[51][44]="";	tabla[51][45]="";	tabla[51][46]="";	tabla[51][47]="";
		tabla[52][0]="s51";	tabla[52][1]="s26";	        tabla[52][2]="";	tabla[52][3]="";	tabla[52][4]="";	tabla[52][5]="";	tabla[52][6]="";	tabla[52][7]="";	tabla[52][8]="";	tabla[52][9]="";	tabla[52][10]="s27";	tabla[52][11]="";	tabla[52][12]="";	tabla[52][13]="";	tabla[52][14]="";	tabla[52][15]="";	tabla[52][16]="";	tabla[52][17]="";	tabla[52][18]="";	tabla[52][19]="s30";	tabla[52][20]="";	tabla[52][21]="";	tabla[52][22]="";	tabla[52][23]="";	tabla[52][24]="";	tabla[52][25]="";	tabla[52][26]="";	tabla[52][27]="";	tabla[52][28]="";	tabla[52][29]="s28";	tabla[52][30]="s29";	tabla[52][31]="";	tabla[52][32]="";	tabla[52][33]="";	tabla[52][34]="";	tabla[52][35]="";	tabla[52][36]="";	tabla[52][37]="";	tabla[52][38]="";	tabla[52][39]="";	tabla[52][40]="";	tabla[52][41]="";	tabla[52][42]="";	tabla[52][43]="";	tabla[52][44]="";	tabla[52][45]="";	tabla[52][46]="";	tabla[52][47]="s76";
		tabla[53][0]="s52";	tabla[53][1]="s26";	        tabla[53][2]="";	tabla[53][3]="";	tabla[53][4]="";	tabla[53][5]="";	tabla[53][6]="";	tabla[53][7]="";	tabla[53][8]="";	tabla[53][9]="";	tabla[53][10]="s27";	tabla[53][11]="";	tabla[53][12]="";	tabla[53][13]="";	tabla[53][14]="";	tabla[53][15]="";	tabla[53][16]="";	tabla[53][17]="";	tabla[53][18]="";	tabla[53][19]="s30";	tabla[53][20]="";	tabla[53][21]="";	tabla[53][22]="";	tabla[53][23]="";	tabla[53][24]="";	tabla[53][25]="";	tabla[53][26]="";	tabla[53][27]="";	tabla[53][28]="";	tabla[53][29]="s28";	tabla[53][30]="s29";	tabla[53][31]="";	tabla[53][32]="";	tabla[53][33]="";	tabla[53][34]="";	tabla[53][35]="";	tabla[53][36]="";	tabla[53][37]="";	tabla[53][38]="";	tabla[53][39]="";	tabla[53][40]="";	tabla[53][41]="";	tabla[53][42]="";	tabla[53][43]="";	tabla[53][44]="";	tabla[53][45]="";	tabla[53][46]="";	tabla[53][47]="s77";
		tabla[54][0]="s53";	tabla[54][1]="";	        tabla[54][2]="";	tabla[54][3]="";	tabla[54][4]="";	tabla[54][5]="";	tabla[54][6]="";	tabla[54][7]="";	tabla[54][8]="";	tabla[54][9]="";	tabla[54][10]="";	tabla[54][11]="s78";	tabla[54][12]="";	tabla[54][13]="";	tabla[54][14]="";	tabla[54][15]="";	tabla[54][16]="";	tabla[54][17]="";	tabla[54][18]="";	tabla[54][19]="";	tabla[54][20]="";	tabla[54][21]="";	tabla[54][22]="";	tabla[54][23]="";	tabla[54][24]="";	tabla[54][25]="";	tabla[54][26]="";	tabla[54][27]="";	tabla[54][28]="";	tabla[54][29]="";	tabla[54][30]="";	tabla[54][31]="";	tabla[54][32]="";	tabla[54][33]="";	tabla[54][34]="";	tabla[54][35]="";	tabla[54][36]="";	tabla[54][37]="";	tabla[54][38]="";	tabla[54][39]="";	tabla[54][40]="";	tabla[54][41]="";	tabla[54][42]="";	tabla[54][43]="";	tabla[54][44]="";	tabla[54][45]="";	tabla[54][46]="";	tabla[54][47]="";
		tabla[55][0]="s54";	tabla[55][1]="";	        tabla[55][2]="";	tabla[55][3]="";	tabla[55][4]="";	tabla[55][5]="";	tabla[55][6]="";	tabla[55][7]="";	tabla[55][8]="";	tabla[55][9]="";	tabla[55][10]="";	tabla[55][11]="E>@";	tabla[55][12]="";	tabla[55][13]="";	tabla[55][14]="";	tabla[55][15]="";	tabla[55][16]="";	tabla[55][17]="";	tabla[55][18]="";	tabla[55][19]="";	tabla[55][20]="";	tabla[55][21]="";	tabla[55][22]="";	tabla[55][23]="";	tabla[55][24]="";	tabla[55][25]="s80";	tabla[55][26]="s81";	tabla[55][27]="";	tabla[55][28]="";	tabla[55][29]="";	tabla[55][30]="";	tabla[55][31]="";	tabla[55][32]="";	tabla[55][33]="";	tabla[55][34]="";	tabla[55][35]="";	tabla[55][36]="";	tabla[55][37]="";	tabla[55][38]="";	tabla[55][39]="";	tabla[55][40]="";	tabla[55][41]="";	tabla[55][42]="";	tabla[55][43]="";	tabla[55][44]="s79";	tabla[55][45]="";	tabla[55][46]="";	tabla[55][47]="";
		tabla[56][0]="s55";	tabla[56][1]="";	        tabla[56][2]="";	tabla[56][3]="";	tabla[56][4]="";	tabla[56][5]="";	tabla[56][6]="";	tabla[56][7]="";	tabla[56][8]="";	tabla[56][9]="";	tabla[56][10]="";	tabla[56][11]="T>@";	tabla[56][12]="";	tabla[56][13]="";	tabla[56][14]="";	tabla[56][15]="";	tabla[56][16]="";	tabla[56][17]="";	tabla[56][18]="";	tabla[56][19]="";	tabla[56][20]="";	tabla[56][21]="";	tabla[56][22]="";	tabla[56][23]="";	tabla[56][24]="";	tabla[56][25]="T>@";	tabla[56][26]="T>@";	tabla[56][27]="s83";	tabla[56][28]="s84";	tabla[56][29]="";	tabla[56][30]="";	tabla[56][31]="";	tabla[56][32]="";	tabla[56][33]="";	tabla[56][34]="";	tabla[56][35]="";	tabla[56][36]="";	tabla[56][37]="";	tabla[56][38]="";	tabla[56][39]="";	tabla[56][40]="";	tabla[56][41]="";	tabla[56][42]="";	tabla[56][43]="";	tabla[56][44]="";	tabla[56][45]="";	tabla[56][46]="s82";	tabla[56][47]="";
		tabla[57][0]="s56";	tabla[57][1]="";	tabla[57][2]="";	tabla[57][3]="";	tabla[57][4]="";	tabla[57][5]="";	tabla[57][6]="";	tabla[57][7]="";	tabla[57][8]="";	tabla[57][9]="";	tabla[57][10]="";	tabla[57][11]="F>id";	tabla[57][12]="";	tabla[57][13]="";	tabla[57][14]="";	tabla[57][15]="";	tabla[57][16]="";	tabla[57][17]="";	tabla[57][18]="";	tabla[57][19]="";	tabla[57][20]="";	tabla[57][21]="";	tabla[57][22]="";	tabla[57][23]="";	tabla[57][24]="";	tabla[57][25]="F>id";	tabla[57][26]="F>id";	tabla[57][27]="F>id";	tabla[57][28]="F>id";	tabla[57][29]="";	tabla[57][30]="";	tabla[57][31]="";	tabla[57][32]="";	tabla[57][33]="";	tabla[57][34]="";	tabla[57][35]="";	tabla[57][36]="";	tabla[57][37]="";	tabla[57][38]="";	tabla[57][39]="";	tabla[57][40]="";	tabla[57][41]="";	tabla[57][42]="";	tabla[57][43]="";	tabla[57][44]="";	tabla[57][45]="";	tabla[57][46]="";	tabla[57][47]="";
		tabla[58][0]="s57";	tabla[58][1]="s56";	tabla[58][2]="";	tabla[58][3]="";	tabla[58][4]="";	tabla[58][5]="";	tabla[58][6]="";	tabla[58][7]="";	tabla[58][8]="";	tabla[58][9]="";	tabla[58][10]="s57";	tabla[58][11]="";	tabla[58][12]="";	tabla[58][13]="";	tabla[58][14]="";	tabla[58][15]="";	tabla[58][16]="";	tabla[58][17]="";	tabla[58][18]="";	tabla[58][19]="s60";	tabla[58][20]="";	tabla[58][21]="";	tabla[58][22]="";	tabla[58][23]="";	tabla[58][24]="";	tabla[58][25]="";	tabla[58][26]="";	tabla[58][27]="";	tabla[58][28]="";	tabla[58][29]="s58";	tabla[58][30]="s59";	tabla[58][31]="";	tabla[58][32]="";	tabla[58][33]="";	tabla[58][34]="";	tabla[58][35]="";	tabla[58][36]="";	tabla[58][37]="";	tabla[58][38]="";	tabla[58][39]="";	tabla[58][40]="";	tabla[58][41]="";	tabla[58][42]="";	tabla[58][43]="s85";	tabla[58][44]="";	tabla[58][45]="s54";	tabla[58][46]="";	tabla[58][47]="s55";
		tabla[59][0]="s58";	tabla[59][1]="";	tabla[59][2]="";	tabla[59][3]="";	tabla[59][4]="";	tabla[59][5]="";	tabla[59][6]="";	tabla[59][7]="";	tabla[59][8]="";	tabla[59][9]="";	tabla[59][10]="";	tabla[59][11]="F>num";	tabla[59][12]="";	tabla[59][13]="";	tabla[59][14]="";	tabla[59][15]="";	tabla[59][16]="";	tabla[59][17]="";	tabla[59][18]="";	tabla[59][19]="";	tabla[59][20]="";	tabla[59][21]="";	tabla[59][22]="";	tabla[59][23]="";	tabla[59][24]="";	tabla[59][25]="F>num";	tabla[59][26]="F>num";	tabla[59][27]="F>num";	tabla[59][28]="F>num";	tabla[59][29]="";	tabla[59][30]="";	tabla[59][31]="";	tabla[59][32]="";	tabla[59][33]="";	tabla[59][34]="";	tabla[59][35]="";	tabla[59][36]="";	tabla[59][37]="";	tabla[59][38]="";	tabla[59][39]="";	tabla[59][40]="";	tabla[59][41]="";	tabla[59][42]="";	tabla[59][43]="";	tabla[59][44]="";	tabla[59][45]="";	tabla[59][46]="";	tabla[59][47]="";
		tabla[60][0]="s59";	tabla[60][1]="";	tabla[60][2]="";	tabla[60][3]="";	tabla[60][4]="";	tabla[60][5]="";	tabla[60][6]="";	tabla[60][7]="";	tabla[60][8]="";	tabla[60][9]="";	tabla[60][10]="";	tabla[60][11]="F>numf";	tabla[60][12]="";	tabla[60][13]="";	tabla[60][14]="";	tabla[60][15]="";	tabla[60][16]="";	tabla[60][17]="";	tabla[60][18]="";	tabla[60][19]="";	tabla[60][20]="";	tabla[60][21]="";	tabla[60][22]="";	tabla[60][23]="";	tabla[60][24]="";	tabla[60][25]="F>numf";	tabla[60][26]="F>numf";	tabla[60][27]="F>numf";	tabla[60][28]="F>numf";	tabla[60][29]="";	tabla[60][30]="";	tabla[60][31]="";	tabla[60][32]="";	tabla[60][33]="";	tabla[60][34]="";	tabla[60][35]="";	tabla[60][36]="";	tabla[60][37]="";	tabla[60][38]="";	tabla[60][39]="";	tabla[60][40]="";	tabla[60][41]="";	tabla[60][42]="";	tabla[60][43]="";	tabla[60][44]="";	tabla[60][45]="";	tabla[60][46]="";	tabla[60][47]="";
		tabla[61][0]="s60";	tabla[61][1]="";	tabla[61][2]="";	tabla[61][3]="";	tabla[61][4]="";	tabla[61][5]="";	tabla[61][6]="";	tabla[61][7]="";	tabla[61][8]="";	tabla[61][9]="";	tabla[61][10]="";	tabla[61][11]="F>litcar";	tabla[61][12]="";	tabla[61][13]="";	tabla[61][14]="";	tabla[61][15]="";	tabla[61][16]="";	tabla[61][17]="";	tabla[61][18]="";	tabla[61][19]="";	tabla[61][20]="";	tabla[61][21]="";	tabla[61][22]="";	tabla[61][23]="";	tabla[61][24]="";	tabla[61][25]="F>litcar";	tabla[61][26]="F>litcar";	tabla[61][27]="F>litcar";	tabla[61][28]="F>litcar";	tabla[61][29]="";	tabla[61][30]="";	tabla[61][31]="";	tabla[61][32]="";	tabla[61][33]="";	tabla[61][34]="";	tabla[61][35]="";	tabla[61][36]="";	tabla[61][37]="";	tabla[61][38]="";	tabla[61][39]="";	tabla[61][40]="";	tabla[61][41]="";	tabla[61][42]="";	tabla[61][43]="";	tabla[61][44]="";	tabla[61][45]="";	tabla[61][46]="";	tabla[61][47]="";
		tabla[62][0]="s61";	tabla[62][1]="s88";	tabla[62][2]="";	tabla[62][3]="";	tabla[62][4]="";	tabla[62][5]="";	tabla[62][6]="";	tabla[62][7]="";	tabla[62][8]="";	tabla[62][9]="";	tabla[62][10]="s89";	tabla[62][11]="";	tabla[62][12]="";	tabla[62][13]="";	tabla[62][14]="";	tabla[62][15]="";	tabla[62][16]="";	tabla[62][17]="";	tabla[62][18]="";	tabla[62][19]="s92";	tabla[62][20]="";	tabla[62][21]="";	tabla[62][22]="";	tabla[62][23]="";	tabla[62][24]="";	tabla[62][25]="";	tabla[62][26]="";	tabla[62][27]="";	tabla[62][28]="";	tabla[62][29]="s90";	tabla[62][30]="s91";	tabla[62][31]="";	tabla[62][32]="";	tabla[62][33]="";	tabla[62][34]="";	tabla[62][35]="";	tabla[62][36]="";	tabla[62][37]="";	tabla[62][38]="";	tabla[62][39]="";	tabla[62][40]="";	tabla[62][41]="s86";	tabla[62][42]="";	tabla[62][43]="";	tabla[62][44]="";	tabla[62][45]="";	tabla[62][46]="";	tabla[62][47]="s87";
		tabla[63][0]="s62";	tabla[63][1]="s26";	tabla[63][2]="";	tabla[63][3]="";	tabla[63][4]="";	tabla[63][5]="";	tabla[63][6]="";	tabla[63][7]="";	tabla[63][8]="";	tabla[63][9]="";	tabla[63][10]="s27";	tabla[63][11]="";	tabla[63][12]="";	tabla[63][13]="";	tabla[63][14]="";	tabla[63][15]="";	tabla[63][16]="";	tabla[63][17]="";	tabla[63][18]="";	tabla[63][19]="s30";	tabla[63][20]="";	tabla[63][21]="";	tabla[63][22]="";	tabla[63][23]="";	tabla[63][24]="";	tabla[63][25]="";	tabla[63][26]="";	tabla[63][27]="";	tabla[63][28]="";	tabla[63][29]="s28";	tabla[63][30]="s29";	tabla[63][31]="";	tabla[63][32]="";	tabla[63][33]="";	tabla[63][34]="";	tabla[63][35]="";	tabla[63][36]="";	tabla[63][37]="";	tabla[63][38]="";	tabla[63][39]="";	tabla[63][40]="";	tabla[63][41]="";	tabla[63][42]="";	tabla[63][43]="s93";	tabla[63][44]="";	tabla[63][45]="s24";	tabla[63][46]="";	tabla[63][47]="s25";
		tabla[64][0]="s63";	tabla[64][1]="";	tabla[64][2]="";	tabla[64][3]="";	tabla[64][4]="";	tabla[64][5]="";	tabla[64][6]="";	tabla[64][7]="";	tabla[64][8]="";	tabla[64][9]="s94";	tabla[64][10]="";	tabla[64][11]="";	tabla[64][12]="";	tabla[64][13]="";	tabla[64][14]="";	tabla[64][15]="";	tabla[64][16]="";	tabla[64][17]="";	tabla[64][18]="";	tabla[64][19]="";	tabla[64][20]="";	tabla[64][21]="";	tabla[64][22]="";	tabla[64][23]="";	tabla[64][24]="";	tabla[64][25]="";	tabla[64][26]="";	tabla[64][27]="";	tabla[64][28]="";	tabla[64][29]="";	tabla[64][30]="";	tabla[64][31]="";	tabla[64][32]="";	tabla[64][33]="";	tabla[64][34]="";	tabla[64][35]="";	tabla[64][36]="";	tabla[64][37]="";	tabla[64][38]="";	tabla[64][39]="";	tabla[64][40]="";	tabla[64][41]="";	tabla[64][42]="";	tabla[64][43]="";	tabla[64][44]="";	tabla[64][45]="";	tabla[64][46]="";	tabla[64][47]="";
		tabla[65][0]="s64";	tabla[65][1]="s95";	tabla[65][2]="";	tabla[65][3]="";	tabla[65][4]="";	tabla[65][5]="";	tabla[65][6]="";	tabla[65][7]="";	tabla[65][8]="";	tabla[65][9]="";	tabla[65][10]="";	tabla[65][11]="";	tabla[65][12]="";	tabla[65][13]="";	tabla[65][14]="";	tabla[65][15]="";	tabla[65][16]="";	tabla[65][17]="";	tabla[65][18]="";	tabla[65][19]="";	tabla[65][20]="";	tabla[65][21]="";	tabla[65][22]="";	tabla[65][23]="";	tabla[65][24]="";	tabla[65][25]="";	tabla[65][26]="";	tabla[65][27]="";	tabla[65][28]="";	tabla[65][29]="";	tabla[65][30]="";	tabla[65][31]="";	tabla[65][32]="";	tabla[65][33]="";	tabla[65][34]="";	tabla[65][35]="";	tabla[65][36]="";	tabla[65][37]="";	tabla[65][38]="";	tabla[65][39]="";	tabla[65][40]="";	tabla[65][41]="";	tabla[65][42]="";	tabla[65][43]="";	tabla[65][44]="";	tabla[65][45]="";	tabla[65][46]="";	tabla[65][47]="";
		tabla[66][0]="s65";	tabla[66][1]="";	tabla[66][2]="";	tabla[66][3]="";	tabla[66][4]="";	tabla[66][5]="";	tabla[66][6]="";	tabla[66][7]="";	tabla[66][8]="";	tabla[66][9]="";	tabla[66][10]="";	tabla[66][11]="";	tabla[66][12]="";	tabla[66][13]="";	tabla[66][14]="";	tabla[66][15]="";	tabla[66][16]="s41";	tabla[66][17]="s42";	tabla[66][18]="";	tabla[66][19]="";	tabla[66][20]="";	tabla[66][21]="";	tabla[66][22]="";	tabla[66][23]="";	tabla[66][24]="";	tabla[66][25]="";	tabla[66][26]="";	tabla[66][27]="";	tabla[66][28]="";	tabla[66][29]="";	tabla[66][30]="";	tabla[66][31]="";	tabla[66][32]="";	tabla[66][33]="";	tabla[66][34]="";	tabla[66][35]="";	tabla[66][36]="";	tabla[66][37]="";	tabla[66][38]="s96";	tabla[66][39]="";	tabla[66][40]="";	tabla[66][41]="";	tabla[66][42]="";	tabla[66][43]="";	tabla[66][44]="";	tabla[66][45]="";	tabla[66][46]="";	tabla[66][47]="";
		tabla[67][0]="s66";	tabla[67][1]="";	tabla[67][2]="";	tabla[67][3]="";	tabla[67][4]="";	tabla[67][5]="";	tabla[67][6]="";	tabla[67][7]="";	tabla[67][8]="";	tabla[67][9]="";	tabla[67][10]="";	tabla[67][11]="";	tabla[67][12]="";	tabla[67][13]="";	tabla[67][14]="";	tabla[67][15]="";	tabla[67][16]="s41";	tabla[67][17]="s42";	tabla[67][18]="";	tabla[67][19]="";	tabla[67][20]="";	tabla[67][21]="";	tabla[67][22]="";	tabla[67][23]="";	tabla[67][24]="";	tabla[67][25]="";	tabla[67][26]="";	tabla[67][27]="";	tabla[67][28]="";	tabla[67][29]="";	tabla[67][30]="";	tabla[67][31]="";	tabla[67][32]="";	tabla[67][33]="";	tabla[67][34]="";	tabla[67][35]="";	tabla[67][36]="";	tabla[67][37]="";	tabla[67][38]="s97";	tabla[67][39]="";	tabla[67][40]="";	tabla[67][41]="";	tabla[67][42]="";	tabla[67][43]="";	tabla[67][44]="";	tabla[67][45]="";	tabla[67][46]="";	tabla[67][47]="";
		tabla[68][0]="s67";	tabla[68][1]="";	tabla[68][2]="s98";	tabla[68][3]="";	tabla[68][4]="";	tabla[68][5]="";	tabla[68][6]="";	tabla[68][7]="";	tabla[68][8]="";	tabla[68][9]="";	tabla[68][10]="";	tabla[68][11]="";	tabla[68][12]="";	tabla[68][13]="";	tabla[68][14]="";	tabla[68][15]="";	tabla[68][16]="";	tabla[68][17]="";	tabla[68][18]="";	tabla[68][19]="";	tabla[68][20]="";	tabla[68][21]="";	tabla[68][22]="";	tabla[68][23]="";	tabla[68][24]="";	tabla[68][25]="";	tabla[68][26]="";	tabla[68][27]="";	tabla[68][28]="";	tabla[68][29]="";	tabla[68][30]="";	tabla[68][31]="";	tabla[68][32]="";	tabla[68][33]="";	tabla[68][34]="";	tabla[68][35]="";	tabla[68][36]="";	tabla[68][37]="";	tabla[68][38]="";	tabla[68][39]="";	tabla[68][40]="";	tabla[68][41]="";	tabla[68][42]="";	tabla[68][43]="";	tabla[68][44]="";	tabla[68][45]="";	tabla[68][46]="";	tabla[68][47]="";
		tabla[69][0]="s68";	tabla[69][1]="";	tabla[69][2]="";	tabla[69][3]="";	tabla[69][4]="";	tabla[69][5]="";	tabla[69][6]="";	tabla[69][7]="";	tabla[69][8]="";	tabla[69][9]="";	tabla[69][10]="";	tabla[69][11]="";	tabla[69][12]="";	tabla[69][13]="";	tabla[69][14]="";	tabla[69][15]="";	tabla[69][16]="";	tabla[69][17]="";	tabla[69][18]="s100";	tabla[69][19]="";	tabla[69][20]="";	tabla[69][21]="";	tabla[69][22]="";	tabla[69][23]="";	tabla[69][24]="";	tabla[69][25]="";	tabla[69][26]="";	tabla[69][27]="";	tabla[69][28]="";	tabla[69][29]="";	tabla[69][30]="";	tabla[69][31]="";	tabla[69][32]="";	tabla[69][33]="";	tabla[69][34]="";	tabla[69][35]="";	tabla[69][36]="";	tabla[69][37]="";	tabla[69][38]="";	tabla[69][39]="s99";	tabla[69][40]="";	tabla[69][41]="";	tabla[69][42]="";	tabla[69][43]="";	tabla[69][44]="";	tabla[69][45]="";	tabla[69][46]="";	tabla[69][47]="";
		tabla[70][0]="s69";	tabla[70][1]="s102";	tabla[70][2]="";	tabla[70][3]="";	tabla[70][4]="";	tabla[70][5]="";	tabla[70][6]="";	tabla[70][7]="";	tabla[70][8]="";	tabla[70][9]="";	tabla[70][10]="s103";	tabla[70][11]="";	tabla[70][12]="";	tabla[70][13]="";	tabla[70][14]="";	tabla[70][15]="";	tabla[70][16]="";	tabla[70][17]="";	tabla[70][18]="";	tabla[70][19]="s106";	tabla[70][20]="";	tabla[70][21]="";	tabla[70][22]="";	tabla[70][23]="";	tabla[70][24]="";	tabla[70][25]="";	tabla[70][26]="";	tabla[70][27]="";	tabla[70][28]="";	tabla[70][29]="s104";	tabla[70][30]="s105";	tabla[70][31]="";	tabla[70][32]="";	tabla[70][33]="";	tabla[70][34]="";	tabla[70][35]="";	tabla[70][36]="";	tabla[70][37]="";	tabla[70][38]="";	tabla[70][39]="";	tabla[70][40]="";	tabla[70][41]="";	tabla[70][42]="";	tabla[70][43]="";	tabla[70][44]="";	tabla[70][45]="";	tabla[70][46]="";	tabla[70][47]="s101";
		tabla[71][0]="s70";	tabla[71][1]="s102";	tabla[71][2]="";	tabla[71][3]="";	tabla[71][4]="";	tabla[71][5]="";	tabla[71][6]="";	tabla[71][7]="";	tabla[71][8]="";	tabla[71][9]="";	tabla[71][10]="s103";	tabla[71][11]="";	tabla[71][12]="";	tabla[71][13]="";	tabla[71][14]="";	tabla[71][15]="";	tabla[71][16]="";	tabla[71][17]="";	tabla[71][18]="";	tabla[71][19]="s106";	tabla[71][20]="";	tabla[71][21]="";	tabla[71][22]="";	tabla[71][23]="";	tabla[71][24]="";	tabla[71][25]="";	tabla[71][26]="";	tabla[71][27]="";	tabla[71][28]="";	tabla[71][29]="s104";	tabla[71][30]="s105";	tabla[71][31]="";	tabla[71][32]="";	tabla[71][33]="";	tabla[71][34]="";	tabla[71][35]="";	tabla[71][36]="";	tabla[71][37]="";	tabla[71][38]="";	tabla[71][39]="";	tabla[71][40]="";	tabla[71][41]="";	tabla[71][42]="";	tabla[71][43]="";	tabla[71][44]="";	tabla[71][45]="";	tabla[71][46]="";	tabla[71][47]="s107";
		tabla[72][0]="s71";	tabla[72][1]="D>D_;_Sd_id_Tipo";	tabla[72][2]="";	tabla[72][3]="";	tabla[72][4]="";	tabla[72][5]="";	tabla[72][6]="";	tabla[72][7]="";	tabla[72][8]="D>D_;_Sd_id_Tipo";	tabla[72][9]="";	tabla[72][10]="";	tabla[72][11]="";	tabla[72][12]="D>D_;_Sd_id_Tipo";	tabla[72][13]="";	tabla[72][14]="D>D_;_Sd_id_Tipo";	tabla[72][15]="D>D_;_Sd_id_Tipo";	tabla[72][16]="";	tabla[72][17]="";	tabla[72][18]="";	tabla[72][19]="";	tabla[72][20]="";	tabla[72][21]="";	tabla[72][22]="";	tabla[72][23]="";	tabla[72][24]="";	tabla[72][25]="";	tabla[72][26]="";	tabla[72][27]="";	tabla[72][28]="";	tabla[72][29]="";	tabla[72][30]="";	tabla[72][31]="D>D_;_Sd_id_Tipo";	tabla[72][32]="";	tabla[72][33]="";	tabla[72][34]="";	tabla[72][35]="";	tabla[72][36]="";	tabla[72][37]="";	tabla[72][38]="";	tabla[72][39]="";	tabla[72][40]="";	tabla[72][41]="";	tabla[72][42]="";	tabla[72][43]="";	tabla[72][44]="";	tabla[72][45]="";	tabla[72][46]="";	tabla[72][47]="";
		tabla[73][0]="s72";	tabla[73][1]="";	tabla[73][2]="Sd>Sd_id_,";	tabla[73][3]="";	tabla[73][4]="";	tabla[73][5]="";	tabla[73][6]="";	tabla[73][7]="";	tabla[73][8]="";	tabla[73][9]="";	tabla[73][10]="";	tabla[73][11]="";	tabla[73][12]="";	tabla[73][13]="";	tabla[73][14]="";	tabla[73][15]="";	tabla[73][16]="";	tabla[73][17]="";	tabla[73][18]="";	tabla[73][19]="";	tabla[73][20]="";	tabla[73][21]="";	tabla[73][22]="";	tabla[73][23]="";	tabla[73][24]="";	tabla[73][25]="";	tabla[73][26]="";	tabla[73][27]="";	tabla[73][28]="";	tabla[73][29]="";	tabla[73][30]="";	tabla[73][31]="";	tabla[73][32]="";	tabla[73][33]="";	tabla[73][34]="";	tabla[73][35]="";	tabla[73][36]="";	tabla[73][37]="";	tabla[73][38]="";	tabla[73][39]="";	tabla[73][40]="";	tabla[73][41]="";	tabla[73][42]="";	tabla[73][43]="";	tabla[73][44]="";	tabla[73][45]="";	tabla[73][46]="";	tabla[73][47]="";
		tabla[74][0]="s73";	tabla[74][1]="";	tabla[74][2]="";	tabla[74][3]="";	tabla[74][4]="";	tabla[74][5]="";	tabla[74][6]="";	tabla[74][7]="";	tabla[74][8]="";	tabla[74][9]="";	tabla[74][10]="";	tabla[74][11]="";	tabla[74][12]="";	tabla[74][13]="";	tabla[74][14]="";	tabla[74][15]="";	tabla[74][16]="";	tabla[74][17]="";	tabla[74][18]="";	tabla[74][19]="";	tabla[74][20]="";	tabla[74][21]="";	tabla[74][22]="";	tabla[74][23]="";	tabla[74][24]="";	tabla[74][25]="";	tabla[74][26]="";	tabla[74][27]="";	tabla[74][28]="";	tabla[74][29]="";	tabla[74][30]="";	tabla[74][31]="S>S_;_Exp_=_id";	tabla[74][32]="";	tabla[74][33]="";	tabla[74][34]="";	tabla[74][35]="";	tabla[74][36]="";	tabla[74][37]="";	tabla[74][38]="";	tabla[74][39]="";	tabla[74][40]="";	tabla[74][41]="";	tabla[74][42]="";	tabla[74][43]="";	tabla[74][44]="";	tabla[74][45]="";	tabla[74][46]="";	tabla[74][47]="";
		tabla[75][0]="s74";	tabla[75][1]="";	tabla[75][2]="E>@";	tabla[75][3]="";	tabla[75][4]="";	tabla[75][5]="";	tabla[75][6]="";	tabla[75][7]="";	tabla[75][8]="";	tabla[75][9]="";	tabla[75][10]="";	tabla[75][11]="";	tabla[75][12]="";	tabla[75][13]="";	tabla[75][14]="";	tabla[75][15]="";	tabla[75][16]="";	tabla[75][17]="";	tabla[75][18]="";	tabla[75][19]="";	tabla[75][20]="";	tabla[75][21]="";	tabla[75][22]="";	tabla[75][23]="";	tabla[75][24]="";	tabla[75][25]="s48";	tabla[75][26]="s49";	tabla[75][27]="";	tabla[75][28]="";	tabla[75][29]="";	tabla[75][30]="";	tabla[75][31]="";	tabla[75][32]="";	tabla[75][33]="";	tabla[75][34]="";	tabla[75][35]="";	tabla[75][36]="";	tabla[75][37]="";	tabla[75][38]="";	tabla[75][39]="";	tabla[75][40]="";	tabla[75][41]="";	tabla[75][42]="";	tabla[75][43]="";	tabla[75][44]="s108";	tabla[75][45]="";	tabla[75][46]="";	tabla[75][47]="";
		tabla[76][0]="s75";	tabla[76][1]="";	tabla[76][2]="E>@";	tabla[76][3]="";	tabla[76][4]="";	tabla[76][5]="";	tabla[76][6]="";	tabla[76][7]="";	tabla[76][8]="";	tabla[76][9]="";	tabla[76][10]="";	tabla[76][11]="";	tabla[76][12]="";	tabla[76][13]="";	tabla[76][14]="";	tabla[76][15]="";	tabla[76][16]="";	tabla[76][17]="";	tabla[76][18]="";	tabla[76][19]="";	tabla[76][20]="";	tabla[76][21]="";	tabla[76][22]="";	tabla[76][23]="";	tabla[76][24]="";	tabla[76][25]="s48";	tabla[76][26]="s49";	tabla[76][27]="";	tabla[76][28]="";	tabla[76][29]="";	tabla[76][30]="";	tabla[76][31]="";	tabla[76][32]="";	tabla[76][33]="";	tabla[76][34]="";	tabla[76][35]="";	tabla[76][36]="";	tabla[76][37]="";	tabla[76][38]="";	tabla[76][39]="";	tabla[76][40]="";	tabla[76][41]="";	tabla[76][42]="";	tabla[76][43]="";	tabla[76][44]="s79";	tabla[76][45]="";	tabla[76][46]="";	tabla[76][47]="";
		tabla[77][0]="s76";	tabla[77][1]="";	tabla[77][2]="T>@";	tabla[77][3]="";	tabla[77][4]="";	tabla[77][5]="";	tabla[77][6]="";	tabla[77][7]="";	tabla[77][8]="";	tabla[77][9]="";	tabla[77][10]="";	tabla[77][11]="";	tabla[77][12]="";	tabla[77][13]="";	tabla[77][14]="";	tabla[77][15]="";	tabla[77][16]="";	tabla[77][17]="";	tabla[77][18]="";	tabla[77][19]="";	tabla[77][20]="";	tabla[77][21]="";	tabla[77][22]="";	tabla[77][23]="";	tabla[77][24]="";	tabla[77][25]="T>@";	tabla[77][26]="T>@";	tabla[77][27]="s51";	tabla[77][28]="s52";	tabla[77][29]="";	tabla[77][30]="";	tabla[77][31]="";	tabla[77][32]="";	tabla[77][33]="";	tabla[77][34]="";	tabla[77][35]="";	tabla[77][36]="";	tabla[77][37]="";	tabla[77][38]="";	tabla[77][39]="";	tabla[77][40]="";	tabla[77][41]="";	tabla[77][42]="";	tabla[77][43]="";	tabla[77][44]="";	tabla[77][45]="";	tabla[77][46]="s110";	tabla[77][47]="";
		tabla[78][0]="s77";	tabla[78][1]="";	tabla[78][2]="T>@";	tabla[78][3]="";	tabla[78][4]="";	tabla[78][5]="";	tabla[78][6]="";	tabla[78][7]="";	tabla[78][8]="";	tabla[78][9]="";	tabla[78][10]="";	tabla[78][11]="";	tabla[78][12]="";	tabla[78][13]="";	tabla[78][14]="";	tabla[78][15]="";	tabla[78][16]="";	tabla[78][17]="";	tabla[78][18]="";	tabla[78][19]="";	tabla[78][20]="";	tabla[78][21]="";	tabla[78][22]="";	tabla[78][23]="";	tabla[78][24]="";	tabla[78][25]="T>@";	tabla[78][26]="T>@";	tabla[78][27]="s51";	tabla[78][28]="s52";	tabla[78][29]="";	tabla[78][30]="";	tabla[78][31]="";	tabla[78][32]="";	tabla[78][33]="";	tabla[78][34]="";	tabla[78][35]="";	tabla[78][36]="";	tabla[78][37]="";	tabla[78][38]="";	tabla[78][39]="";	tabla[78][40]="";	tabla[78][41]="";	tabla[78][42]="";	tabla[78][43]="";	tabla[78][44]="";	tabla[78][45]="";	tabla[78][46]="s111";	tabla[78][47]="";
		tabla[79][0]="s78";	tabla[79][1]="";	tabla[79][2]="F>)_Exp_(";	tabla[79][3]="";	tabla[79][4]="";	tabla[79][5]="";	tabla[79][6]="";	tabla[79][7]="";	tabla[79][8]="";	tabla[79][9]="";	tabla[79][10]="";	tabla[79][11]="";	tabla[79][12]="";	tabla[79][13]="";	tabla[79][14]="";	tabla[79][15]="";	tabla[79][16]="";	tabla[79][17]="";	tabla[79][18]="";	tabla[79][19]="";	tabla[79][20]="";	tabla[79][21]="";	tabla[79][22]="";	tabla[79][23]="";	tabla[79][24]="";	tabla[79][25]="F>)_Exp_(";	tabla[79][26]="F>)_Exp_(";	tabla[79][27]="F>)_Exp_(";	tabla[79][28]="F>)_Exp_(";	tabla[79][29]="";	tabla[79][30]="";	tabla[79][31]="";	tabla[79][32]="";	tabla[79][33]="";	tabla[79][34]="";	tabla[79][35]="";	tabla[79][36]="";	tabla[79][37]="";	tabla[79][38]="";	tabla[79][39]="";	tabla[79][40]="";	tabla[79][41]="";	tabla[79][42]="";	tabla[79][43]="";	tabla[79][44]="";	tabla[79][45]="";	tabla[79][46]="";	tabla[79][47]="";
		tabla[80][0]="s79";	tabla[80][1]="";	tabla[80][2]="";	tabla[80][3]="";	tabla[80][4]="";	tabla[80][5]="";	tabla[80][6]="";	tabla[80][7]="";	tabla[80][8]="";	tabla[80][9]="";	tabla[80][10]="";	tabla[80][11]="Exp>E_Term";	tabla[80][12]="";	tabla[80][13]="";	tabla[80][14]="";	tabla[80][15]="";	tabla[80][16]="";	tabla[80][17]="";	tabla[80][18]="";	tabla[80][19]="";	tabla[80][20]="";	tabla[80][21]="";	tabla[80][22]="";	tabla[80][23]="";	tabla[80][24]="";	tabla[80][25]="";	tabla[80][26]="";	tabla[80][27]="";	tabla[80][28]="";	tabla[80][29]="";	tabla[80][30]="";	tabla[80][31]="";	tabla[80][32]="";	tabla[80][33]="";	tabla[80][34]="";	tabla[80][35]="";	tabla[80][36]="";	tabla[80][37]="";	tabla[80][38]="";	tabla[80][39]="";	tabla[80][40]="";	tabla[80][41]="";	tabla[80][42]="";	tabla[80][43]="";	tabla[80][44]="";	tabla[80][45]="";	tabla[80][46]="";	tabla[80][47]="";
		tabla[81][0]="s80";	tabla[81][1]="s56";	tabla[81][2]="";	tabla[81][3]="";	tabla[81][4]="";	tabla[81][5]="";	tabla[81][6]="";	tabla[81][7]="";	tabla[81][8]="";	tabla[81][9]="";	tabla[81][10]="s57";	tabla[81][11]="";	tabla[81][12]="";	tabla[81][13]="";	tabla[81][14]="";	tabla[81][15]="";	tabla[81][16]="";	tabla[81][17]="";	tabla[81][18]="";	tabla[81][19]="s60";	tabla[81][20]="";	tabla[81][21]="";	tabla[81][22]="";	tabla[81][23]="";	tabla[81][24]="";	tabla[81][25]="";	tabla[81][26]="";	tabla[81][27]="";	tabla[81][28]="";	tabla[81][29]="s58";	tabla[81][30]="s59";	tabla[81][31]="";	tabla[81][32]="";	tabla[81][33]="";	tabla[81][34]="";	tabla[81][35]="";	tabla[81][36]="";	tabla[81][37]="";	tabla[81][38]="";	tabla[81][39]="";	tabla[81][40]="";	tabla[81][41]="";	tabla[81][42]="";	tabla[81][43]="";	tabla[81][44]="";	tabla[81][45]="s112";	tabla[81][46]="";	tabla[81][47]="s55";
		tabla[82][0]="s81";	tabla[82][1]="s56";	tabla[82][2]="";	tabla[82][3]="";	tabla[82][4]="";	tabla[82][5]="";	tabla[82][6]="";	tabla[82][7]="";	tabla[82][8]="";	tabla[82][9]="";	tabla[82][10]="s57";	tabla[82][11]="";	tabla[82][12]="";	tabla[82][13]="";	tabla[82][14]="";	tabla[82][15]="";	tabla[82][16]="";	tabla[82][17]="";	tabla[82][18]="";	tabla[82][19]="s60";	tabla[82][20]="";	tabla[82][21]="";	tabla[82][22]="";	tabla[82][23]="";	tabla[82][24]="";	tabla[82][25]="";	tabla[82][26]="";	tabla[82][27]="";	tabla[82][28]="";	tabla[82][29]="s58";	tabla[82][30]="s59";	tabla[82][31]="";	tabla[82][32]="";	tabla[82][33]="";	tabla[82][34]="";	tabla[82][35]="";	tabla[82][36]="";	tabla[82][37]="";	tabla[82][38]="";	tabla[82][39]="";	tabla[82][40]="";	tabla[82][41]="";	tabla[82][42]="";	tabla[82][43]="";	tabla[82][44]="";	tabla[82][45]="s113";	tabla[82][46]="";	tabla[82][47]="s55";
		tabla[83][0]="s82";	tabla[83][1]="";	tabla[83][2]="";	tabla[83][3]="";	tabla[83][4]="";	tabla[83][5]="";	tabla[83][6]="";	tabla[83][7]="";	tabla[83][8]="";	tabla[83][9]="";	tabla[83][10]="";	tabla[83][11]="Term>T_F";	tabla[83][12]="";	tabla[83][13]="";	tabla[83][14]="";	tabla[83][15]="";	tabla[83][16]="";	tabla[83][17]="";	tabla[83][18]="";	tabla[83][19]="";	tabla[83][20]="";	tabla[83][21]="";	tabla[83][22]="";	tabla[83][23]="";	tabla[83][24]="";	tabla[83][25]="Term>T_F";	tabla[83][26]="Term>T_F";	tabla[83][27]="";	tabla[83][28]="";	tabla[83][29]="";	tabla[83][30]="";	tabla[83][31]="";	tabla[83][32]="";	tabla[83][33]="";	tabla[83][34]="";	tabla[83][35]="";	tabla[83][36]="";	tabla[83][37]="";	tabla[83][38]="";	tabla[83][39]="";	tabla[83][40]="";	tabla[83][41]="";	tabla[83][42]="";	tabla[83][43]="";	tabla[83][44]="";	tabla[83][45]="";	tabla[83][46]="";	tabla[83][47]="";
		tabla[84][0]="s83";	tabla[84][1]="s56";	tabla[84][2]="";	tabla[84][3]="";	tabla[84][4]="";	tabla[84][5]="";	tabla[84][6]="";	tabla[84][7]="";	tabla[84][8]="";	tabla[84][9]="";	tabla[84][10]="s57";	tabla[84][11]="";	tabla[84][12]="";	tabla[84][13]="";	tabla[84][14]="";	tabla[84][15]="";	tabla[84][16]="";	tabla[84][17]="";	tabla[84][18]="";	tabla[84][19]="s60";	tabla[84][20]="";	tabla[84][21]="";	tabla[84][22]="";	tabla[84][23]="";	tabla[84][24]="";	tabla[84][25]="";	tabla[84][26]="";	tabla[84][27]="";	tabla[84][28]="";	tabla[84][29]="s58";	tabla[84][30]="s59";	tabla[84][31]="";	tabla[84][32]="";	tabla[84][33]="";	tabla[84][34]="";	tabla[84][35]="";	tabla[84][36]="";	tabla[84][37]="";	tabla[84][38]="";	tabla[84][39]="";	tabla[84][40]="";	tabla[84][41]="";	tabla[84][42]="";	tabla[84][43]="";	tabla[84][44]="";	tabla[84][45]="";	tabla[84][46]="";	tabla[84][47]="s114";
		tabla[85][0]="s84";	tabla[85][1]="s56";	tabla[85][2]="";	tabla[85][3]="";	tabla[85][4]="";	tabla[85][5]="";	tabla[85][6]="";	tabla[85][7]="";	tabla[85][8]="";	tabla[85][9]="";	tabla[85][10]="s57";	tabla[85][11]="";	tabla[85][12]="";	tabla[85][13]="";	tabla[85][14]="";	tabla[85][15]="";	tabla[85][16]="";	tabla[85][17]="";	tabla[85][18]="";	tabla[85][19]="s60";	tabla[85][20]="";	tabla[85][21]="";	tabla[85][22]="";	tabla[85][23]="";	tabla[85][24]="";	tabla[85][25]="";	tabla[85][26]="";	tabla[85][27]="";	tabla[85][28]="";	tabla[85][29]="s58";	tabla[85][30]="s59";	tabla[85][31]="";	tabla[85][32]="";	tabla[85][33]="";	tabla[85][34]="";	tabla[85][35]="";	tabla[85][36]="";	tabla[85][37]="";	tabla[85][38]="";	tabla[85][39]="";	tabla[85][40]="";	tabla[85][41]="";	tabla[85][42]="";	tabla[85][43]="";	tabla[85][44]="";	tabla[85][45]="";	tabla[85][46]="";	tabla[85][47]="s115";
		tabla[86][0]="s85";	tabla[86][1]="";	tabla[86][2]="";	tabla[86][3]="";	tabla[86][4]="";	tabla[86][5]="";	tabla[86][6]="";	tabla[86][7]="";	tabla[86][8]="";	tabla[86][9]="";	tabla[86][10]="";	tabla[86][11]="s116";	tabla[86][12]="";	tabla[86][13]="";	tabla[86][14]="";	tabla[86][15]="";	tabla[86][16]="";	tabla[86][17]="";	tabla[86][18]="";	tabla[86][19]="";	tabla[86][20]="";	tabla[86][21]="";	tabla[86][22]="";	tabla[86][23]="";	tabla[86][24]="";	tabla[86][25]="";	tabla[86][26]="";	tabla[86][27]="";	tabla[86][28]="";	tabla[86][29]="";	tabla[86][30]="";	tabla[86][31]="";	tabla[86][32]="";	tabla[86][33]="";	tabla[86][34]="";	tabla[86][35]="";	tabla[86][36]="";	tabla[86][37]="";	tabla[86][38]="";	tabla[86][39]="";	tabla[86][40]="";	tabla[86][41]="";	tabla[86][42]="";	tabla[86][43]="";	tabla[86][44]="";	tabla[86][45]="";	tabla[86][46]="";	tabla[86][47]="";
		tabla[87][0]="s86";	tabla[87][1]="";	tabla[87][2]="";	tabla[87][3]="";	tabla[87][4]="";	tabla[87][5]="";	tabla[87][6]="";	tabla[87][7]="";	tabla[87][8]="";	tabla[87][9]="";	tabla[87][10]="";	tabla[87][11]="s117";	tabla[87][12]="";	tabla[87][13]="";	tabla[87][14]="";	tabla[87][15]="";	tabla[87][16]="";	tabla[87][17]="";	tabla[87][18]="";	tabla[87][19]="";	tabla[87][20]="";	tabla[87][21]="";	tabla[87][22]="";	tabla[87][23]="";	tabla[87][24]="";	tabla[87][25]="";	tabla[87][26]="";	tabla[87][27]="";	tabla[87][28]="";	tabla[87][29]="";	tabla[87][30]="";	tabla[87][31]="";	tabla[87][32]="";	tabla[87][33]="";	tabla[87][34]="";	tabla[87][35]="";	tabla[87][36]="";	tabla[87][37]="";	tabla[87][38]="";	tabla[87][39]="";	tabla[87][40]="";	tabla[87][41]="";	tabla[87][42]="";	tabla[87][43]="";	tabla[87][44]="";	tabla[87][45]="";	tabla[87][46]="";	tabla[87][47]="";
		tabla[88][0]="s87";	tabla[88][1]="";	tabla[88][2]="";	tabla[88][3]="";	tabla[88][4]="";	tabla[88][5]="";	tabla[88][6]="";	tabla[88][7]="";	tabla[88][8]="";	tabla[88][9]="";	tabla[88][10]="";	tabla[88][11]="";	tabla[88][12]="";	tabla[88][13]="";	tabla[88][14]="";	tabla[88][15]="";	tabla[88][16]="";	tabla[88][17]="";	tabla[88][18]="";	tabla[88][19]="";	tabla[88][20]="";	tabla[88][21]="";	tabla[88][22]="s119";	tabla[88][23]="s120";	tabla[88][24]="s121";	tabla[88][25]="";	tabla[88][26]="";	tabla[88][27]="";	tabla[88][28]="";	tabla[88][29]="";	tabla[88][30]="";	tabla[88][31]="";	tabla[88][32]="";	tabla[88][33]="";	tabla[88][34]="";	tabla[88][35]="";	tabla[88][36]="";	tabla[88][37]="";	tabla[88][38]="";	tabla[88][39]="";	tabla[88][40]="";	tabla[88][41]="";	tabla[88][42]="s118";	tabla[88][43]="";	tabla[88][44]="";	tabla[88][45]="";	tabla[88][46]="";	tabla[88][47]="";
		tabla[89][0]="s88";	tabla[89][1]="";	tabla[89][2]="";	tabla[89][3]="";	tabla[89][4]="";	tabla[89][5]="";	tabla[89][6]="";	tabla[89][7]="";	tabla[89][8]="";	tabla[89][9]="";	tabla[89][10]="";	tabla[89][11]="";	tabla[89][12]="";	tabla[89][13]="";	tabla[89][14]="";	tabla[89][15]="";	tabla[89][16]="";	tabla[89][17]="";	tabla[89][18]="";	tabla[89][19]="";	tabla[89][20]="";	tabla[89][21]="";	tabla[89][22]="F>id";	tabla[89][23]="F>id";	tabla[89][24]="F>id";	tabla[89][25]="";	tabla[89][26]="";	tabla[89][27]="";	tabla[89][28]="";	tabla[89][29]="";	tabla[89][30]="";	tabla[89][31]="";	tabla[89][32]="";	tabla[89][33]="";	tabla[89][34]="";	tabla[89][35]="";	tabla[89][36]="";	tabla[89][37]="";	tabla[89][38]="";	tabla[89][39]="";	tabla[89][40]="";	tabla[89][41]="";	tabla[89][42]="";	tabla[89][43]="";	tabla[89][44]="";	tabla[89][45]="";	tabla[89][46]="";	tabla[89][47]="";
		tabla[90][0]="s89";	tabla[90][1]="s56";	tabla[90][2]="";	tabla[90][3]="";	tabla[90][4]="";	tabla[90][5]="";	tabla[90][6]="";	tabla[90][7]="";	tabla[90][8]="";	tabla[90][9]="";	tabla[90][10]="s57";	tabla[90][11]="";	tabla[90][12]="";	tabla[90][13]="";	tabla[90][14]="";	tabla[90][15]="";	tabla[90][16]="";	tabla[90][17]="";	tabla[90][18]="";	tabla[90][19]="s60";	tabla[90][20]="";	tabla[90][21]="";	tabla[90][22]="";	tabla[90][23]="";	tabla[90][24]="";	tabla[90][25]="";	tabla[90][26]="";	tabla[90][27]="";	tabla[90][28]="";	tabla[90][29]="s58";	tabla[90][30]="s59";	tabla[90][31]="";	tabla[90][32]="";	tabla[90][33]="";	tabla[90][34]="";	tabla[90][35]="";	tabla[90][36]="";	tabla[90][37]="";	tabla[90][38]="";	tabla[90][39]="";	tabla[90][40]="";	tabla[90][41]="";	tabla[90][42]="";	tabla[90][43]="s122";	tabla[90][44]="";	tabla[90][45]="s54";	tabla[90][46]="";	tabla[90][47]="s55";
		tabla[91][0]="s90";	tabla[91][1]="";	tabla[91][2]="";	tabla[91][3]="";	tabla[91][4]="";	tabla[91][5]="";	tabla[91][6]="";	tabla[91][7]="";	tabla[91][8]="";	tabla[91][9]="";	tabla[91][10]="";	tabla[91][11]="";	tabla[91][12]="";	tabla[91][13]="";	tabla[91][14]="";	tabla[91][15]="";	tabla[91][16]="";	tabla[91][17]="";	tabla[91][18]="";	tabla[91][19]="";	tabla[91][20]="";	tabla[91][21]="";	tabla[91][22]="F>num";	tabla[91][23]="F>num";	tabla[91][24]="F>num";	tabla[91][25]="";	tabla[91][26]="";	tabla[91][27]="";	tabla[91][28]="";	tabla[91][29]="";	tabla[91][30]="";	tabla[91][31]="";	tabla[91][32]="";	tabla[91][33]="";	tabla[91][34]="";	tabla[91][35]="";	tabla[91][36]="";	tabla[91][37]="";	tabla[91][38]="";	tabla[91][39]="";	tabla[91][40]="";	tabla[91][41]="";	tabla[91][42]="";	tabla[91][43]="";	tabla[91][44]="";	tabla[91][45]="";	tabla[91][46]="";	tabla[91][47]="";
		tabla[92][0]="s91";	tabla[92][1]="";	tabla[92][2]="";	tabla[92][3]="";	tabla[92][4]="";	tabla[92][5]="";	tabla[92][6]="";	tabla[92][7]="";	tabla[92][8]="";	tabla[92][9]="";	tabla[92][10]="";	tabla[92][11]="";	tabla[92][12]="";	tabla[92][13]="";	tabla[92][14]="";	tabla[92][15]="";	tabla[92][16]="";	tabla[92][17]="";	tabla[92][18]="";	tabla[92][19]="";	tabla[92][20]="";	tabla[92][21]="";	tabla[92][22]="F>numf";	tabla[92][23]="F>numf";	tabla[92][24]="F>numf";	tabla[92][25]="";	tabla[92][26]="";	tabla[92][27]="";	tabla[92][28]="";	tabla[92][29]="";	tabla[92][30]="";	tabla[92][31]="";	tabla[92][32]="";	tabla[92][33]="";	tabla[92][34]="";	tabla[92][35]="";	tabla[92][36]="";	tabla[92][37]="";	tabla[92][38]="";	tabla[92][39]="";	tabla[92][40]="";	tabla[92][41]="";	tabla[92][42]="";	tabla[92][43]="";	tabla[92][44]="";	tabla[92][45]="";	tabla[92][46]="";	tabla[92][47]="";
		tabla[93][0]="s92";	tabla[93][1]="";	tabla[93][2]="";	tabla[93][3]="";	tabla[93][4]="";	tabla[93][5]="";	tabla[93][6]="";	tabla[93][7]="";	tabla[93][8]="";	tabla[93][9]="";	tabla[93][10]="";	tabla[93][11]="";	tabla[93][12]="";	tabla[93][13]="";	tabla[93][14]="";	tabla[93][15]="";	tabla[93][16]="";	tabla[93][17]="";	tabla[93][18]="";	tabla[93][19]="";	tabla[93][20]="";	tabla[93][21]="";	tabla[93][22]="F>litcar";	tabla[93][23]="F>litcar";	tabla[93][24]="F>litcar";	tabla[93][25]="";	tabla[93][26]="";	tabla[93][27]="";	tabla[93][28]="";	tabla[93][29]="";	tabla[93][30]="";	tabla[93][31]="";	tabla[93][32]="";	tabla[93][33]="";	tabla[93][34]="";	tabla[93][35]="";	tabla[93][36]="";	tabla[93][37]="";	tabla[93][38]="";	tabla[93][39]="";	tabla[93][40]="";	tabla[93][41]="";	tabla[93][42]="";	tabla[93][43]="";	tabla[93][44]="";	tabla[93][45]="";	tabla[93][46]="";	tabla[93][47]="";
		tabla[94][0]="s93";	tabla[94][1]="";	tabla[94][2]="s123";	tabla[94][3]="";	tabla[94][4]="";	tabla[94][5]="";	tabla[94][6]="";	tabla[94][7]="";	tabla[94][8]="";	tabla[94][9]="";	tabla[94][10]="";	tabla[94][11]="";	tabla[94][12]="";	tabla[94][13]="";	tabla[94][14]="";	tabla[94][15]="";	tabla[94][16]="";	tabla[94][17]="";	tabla[94][18]="";	tabla[94][19]="";	tabla[94][20]="";	tabla[94][21]="";	tabla[94][22]="";	tabla[94][23]="";	tabla[94][24]="";	tabla[94][25]="";	tabla[94][26]="";	tabla[94][27]="";	tabla[94][28]="";	tabla[94][29]="";	tabla[94][30]="";	tabla[94][31]="";	tabla[94][32]="";	tabla[94][33]="";	tabla[94][34]="";	tabla[94][35]="";	tabla[94][36]="";	tabla[94][37]="";	tabla[94][38]="";	tabla[94][39]="";	tabla[94][40]="";	tabla[94][41]="";	tabla[94][42]="";	tabla[94][43]="";	tabla[94][44]="";	tabla[94][45]="";	tabla[94][46]="";	tabla[94][47]="";
		tabla[95][0]="s94";	tabla[95][1]="";	tabla[95][2]="";	tabla[95][3]="";	tabla[95][4]="";	tabla[95][5]="";	tabla[95][6]="";	tabla[95][7]="";	tabla[95][8]="";	tabla[95][9]="";	tabla[95][10]="s124";	tabla[95][11]="";	tabla[95][12]="";	tabla[95][13]="";	tabla[95][14]="";	tabla[95][15]="";	tabla[95][16]="";	tabla[95][17]="";	tabla[95][18]="";	tabla[95][19]="";	tabla[95][20]="";	tabla[95][21]="";	tabla[95][22]="";	tabla[95][23]="";	tabla[95][24]="";	tabla[95][25]="";	tabla[95][26]="";	tabla[95][27]="";	tabla[95][28]="";	tabla[95][29]="";	tabla[95][30]="";	tabla[95][31]="";	tabla[95][32]="";	tabla[95][33]="";	tabla[95][34]="";	tabla[95][35]="";	tabla[95][36]="";	tabla[95][37]="";	tabla[95][38]="";	tabla[95][39]="";	tabla[95][40]="";	tabla[95][41]="";	tabla[95][42]="";	tabla[95][43]="";	tabla[95][44]="";	tabla[95][45]="";	tabla[95][46]="";	tabla[95][47]="";
		tabla[96][0]="s95";	tabla[96][1]="";	tabla[96][2]="";	tabla[96][3]="";	tabla[96][4]="";	tabla[96][5]="";	tabla[96][6]="";	tabla[96][7]="";	tabla[96][8]="";	tabla[96][9]="";	tabla[96][10]="";	tabla[96][11]="s125";	tabla[96][12]="";	tabla[96][13]="";	tabla[96][14]="";	tabla[96][15]="";	tabla[96][16]="";	tabla[96][17]="";	tabla[96][18]="";	tabla[96][19]="";	tabla[96][20]="";	tabla[96][21]="";	tabla[96][22]="";	tabla[96][23]="";	tabla[96][24]="";	tabla[96][25]="";	tabla[96][26]="";	tabla[96][27]="";	tabla[96][28]="";	tabla[96][29]="";	tabla[96][30]="";	tabla[96][31]="";	tabla[96][32]="";	tabla[96][33]="";	tabla[96][34]="";	tabla[96][35]="";	tabla[96][36]="";	tabla[96][37]="";	tabla[96][38]="";	tabla[96][39]="";	tabla[96][40]="";	tabla[96][41]="";	tabla[96][42]="";	tabla[96][43]="";	tabla[96][44]="";	tabla[96][45]="";	tabla[96][46]="";	tabla[96][47]="";
		tabla[97][0]="s96";	tabla[97][1]="";	tabla[97][2]="";	tabla[97][3]="";	tabla[97][4]="";	tabla[97][5]="";	tabla[97][6]="s126";	tabla[97][7]="";	tabla[97][8]="";	tabla[97][9]="";	tabla[97][10]="";	tabla[97][11]="";	tabla[97][12]="";	tabla[97][13]="";	tabla[97][14]="";	tabla[97][15]="";	tabla[97][16]="";	tabla[97][17]="";	tabla[97][18]="";	tabla[97][19]="";	tabla[97][20]="";	tabla[97][21]="";	tabla[97][22]="";	tabla[97][23]="";	tabla[97][24]="";	tabla[97][25]="";	tabla[97][26]="";	tabla[97][27]="";	tabla[97][28]="";	tabla[97][29]="";	tabla[97][30]="";	tabla[97][31]="";	tabla[97][32]="";	tabla[97][33]="";	tabla[97][34]="";	tabla[97][35]="";	tabla[97][36]="";	tabla[97][37]="";	tabla[97][38]="";	tabla[97][39]="";	tabla[97][40]="";	tabla[97][41]="";	tabla[97][42]="";	tabla[97][43]="";	tabla[97][44]="";	tabla[97][45]="";	tabla[97][46]="";	tabla[97][47]="";
		tabla[98][0]="s97";	tabla[98][1]="";	tabla[98][2]="";	tabla[98][3]="";	tabla[98][4]="";	tabla[98][5]="";	tabla[98][6]="s127";	tabla[98][7]="";	tabla[98][8]="";	tabla[98][9]="";	tabla[98][10]="";	tabla[98][11]="";	tabla[98][12]="";	tabla[98][13]="";	tabla[98][14]="";	tabla[98][15]="";	tabla[98][16]="";	tabla[98][17]="";	tabla[98][18]="";	tabla[98][19]="";	tabla[98][20]="";	tabla[98][21]="";	tabla[98][22]="";	tabla[98][23]="";	tabla[98][24]="";	tabla[98][25]="";	tabla[98][26]="";	tabla[98][27]="";	tabla[98][28]="";	tabla[98][29]="";	tabla[98][30]="";	tabla[98][31]="";	tabla[98][32]="";	tabla[98][33]="";	tabla[98][34]="";	tabla[98][35]="";	tabla[98][36]="";	tabla[98][37]="";	tabla[98][38]="";	tabla[98][39]="";	tabla[98][40]="";	tabla[98][41]="";	tabla[98][42]="";	tabla[98][43]="";	tabla[98][44]="";	tabla[98][45]="";	tabla[98][46]="";	tabla[98][47]="";
		tabla[99][0]="s98";	tabla[99][1]="D>@";	tabla[99][2]="";	tabla[99][3]="s4";	tabla[99][4]="s5";	tabla[99][5]="s6";	tabla[99][6]="";	tabla[99][7]="";	tabla[99][8]="D>@";	tabla[99][9]="D>@";	tabla[99][10]="";	tabla[99][11]="";	tabla[99][12]="D>@";	tabla[99][13]="";	tabla[99][14]="D>@";	tabla[99][15]="D>@";	tabla[99][16]="";	tabla[99][17]="";	tabla[99][18]="";	tabla[99][19]="";	tabla[99][20]="";	tabla[99][21]="";	tabla[99][22]="";	tabla[99][23]="";	tabla[99][24]="";	tabla[99][25]="";	tabla[99][26]="";	tabla[99][27]="";	tabla[99][28]="";	tabla[99][29]="";	tabla[99][30]="";	tabla[99][31]="";	tabla[99][32]="";	tabla[99][33]="";	tabla[99][34]="s128";	tabla[99][35]="s17";	tabla[99][36]="";	tabla[99][37]="";	tabla[99][38]="";	tabla[99][39]="";	tabla[99][40]="";	tabla[99][41]="";	tabla[99][42]="";	tabla[99][43]="";	tabla[99][44]="";	tabla[99][45]="";	tabla[99][46]="";	tabla[99][47]="";
		tabla[100][0]="s99";	tabla[100][1]="";	tabla[100][2]="";	tabla[100][3]="";	tabla[100][4]="";	tabla[100][5]="";	tabla[100][6]="";	tabla[100][7]="";	tabla[100][8]="";	tabla[100][9]="";	tabla[100][10]="";	tabla[100][11]="";	tabla[100][12]="";	tabla[100][13]="s129";	tabla[100][14]="";	tabla[100][15]="";	tabla[100][16]="";	tabla[100][17]="";	tabla[100][18]="";	tabla[100][19]="";	tabla[100][20]="";	tabla[100][21]="";	tabla[100][22]="";	tabla[100][23]="";	tabla[100][24]="";	tabla[100][25]="";	tabla[100][26]="";	tabla[100][27]="";	tabla[100][28]="";	tabla[100][29]="";	tabla[100][30]="";	tabla[100][31]="";	tabla[100][32]="";	tabla[100][33]="";	tabla[100][34]="";	tabla[100][35]="";	tabla[100][36]="";	tabla[100][37]="";	tabla[100][38]="";	tabla[100][39]="";	tabla[100][40]="";	tabla[100][41]="";	tabla[100][42]="";	tabla[100][43]="";	tabla[100][44]="";	tabla[100][45]="";	tabla[100][46]="";	tabla[100][47]="";
		
	}
	
	public void tabla3() {
		tabla[101][0]="s100";	tabla[101][1]="";	tabla[101][2]="";	tabla[101][3]="";	tabla[101][4]="";	tabla[101][5]="";	tabla[101][6]="";	tabla[101][7]="";	tabla[101][8]="";	tabla[101][9]="";	tabla[101][10]="";	tabla[101][11]="";	tabla[101][12]="";	tabla[101][13]="";	tabla[101][14]="";	tabla[101][15]="";	tabla[101][16]="";	tabla[101][17]="";	tabla[101][18]="";	tabla[101][19]="s130";	tabla[101][20]="";	tabla[101][21]="";	tabla[101][22]="";	tabla[101][23]="";	tabla[101][24]="";	tabla[101][25]="";	tabla[101][26]="";	tabla[101][27]="";	tabla[101][28]="";	tabla[101][29]="";	tabla[101][30]="";	tabla[101][31]="";	tabla[101][32]="";	tabla[101][33]="";	tabla[101][34]="";	tabla[101][35]="";	tabla[101][36]="";	tabla[101][37]="";	tabla[101][38]="";	tabla[101][39]="";	tabla[101][40]="";	tabla[101][41]="";	tabla[101][42]="";	tabla[101][43]="";	tabla[101][44]="";	tabla[101][45]="";	tabla[101][46]="";	tabla[101][47]="";
		tabla[102][0]="s101";	tabla[102][1]="";	tabla[102][2]="";	tabla[102][3]="";	tabla[102][4]="";	tabla[102][5]="";	tabla[102][6]="";	tabla[102][7]="";	tabla[102][8]="";	tabla[102][9]="";	tabla[102][10]="";	tabla[102][11]="s131";	tabla[102][12]="";	tabla[102][13]="";	tabla[102][14]="";	tabla[102][15]="";	tabla[102][16]="";	tabla[102][17]="";	tabla[102][18]="";	tabla[102][19]="";	tabla[102][20]="";	tabla[102][21]="";	tabla[102][22]="";	tabla[102][23]="";	tabla[102][24]="";	tabla[102][25]="";	tabla[102][26]="";	tabla[102][27]="";	tabla[102][28]="";	tabla[102][29]="";	tabla[102][30]="";	tabla[102][31]="";	tabla[102][32]="";	tabla[102][33]="";	tabla[102][34]="";	tabla[102][35]="";	tabla[102][36]="";	tabla[102][37]="";	tabla[102][38]="";	tabla[102][39]="";	tabla[102][40]="";	tabla[102][41]="";	tabla[102][42]="";	tabla[102][43]="";	tabla[102][44]="";	tabla[102][45]="";	tabla[102][46]="";	tabla[102][47]="";
		tabla[103][0]="s102";	tabla[103][1]="";	tabla[103][2]="";	tabla[103][3]="";	tabla[103][4]="";	tabla[103][5]="";	tabla[103][6]="";	tabla[103][7]="";	tabla[103][8]="";	tabla[103][9]="";	tabla[103][10]="";	tabla[103][11]="F>id";	tabla[103][12]="";	tabla[103][13]="";	tabla[103][14]="";	tabla[103][15]="";	tabla[103][16]="";	tabla[103][17]="";	tabla[103][18]="";	tabla[103][19]="";	tabla[103][20]="";	tabla[103][21]="";	tabla[103][22]="";	tabla[103][23]="";	tabla[103][24]="";	tabla[103][25]="";	tabla[103][26]="";	tabla[103][27]="";	tabla[103][28]="";	tabla[103][29]="";	tabla[103][30]="";	tabla[103][31]="";	tabla[103][32]="";	tabla[103][33]="";	tabla[103][34]="";	tabla[103][35]="";	tabla[103][36]="";	tabla[103][37]="";	tabla[103][38]="";	tabla[103][39]="";	tabla[103][40]="";	tabla[103][41]="";	tabla[103][42]="";	tabla[103][43]="";	tabla[103][44]="";	tabla[103][45]="";	tabla[103][46]="";	tabla[103][47]="";
		tabla[104][0]="s103";	tabla[104][1]="s56";	tabla[104][2]="";	tabla[104][3]="";	tabla[104][4]="";	tabla[104][5]="";	tabla[104][6]="";	tabla[104][7]="";	tabla[104][8]="";	tabla[104][9]="";	tabla[104][10]="s57";	tabla[104][11]="";	tabla[104][12]="";	tabla[104][13]="";	tabla[104][14]="";	tabla[104][15]="";	tabla[104][16]="";	tabla[104][17]="";	tabla[104][18]="";	tabla[104][19]="s60";	tabla[104][20]="";	tabla[104][21]="";	tabla[104][22]="";	tabla[104][23]="";	tabla[104][24]="";	tabla[104][25]="";	tabla[104][26]="";	tabla[104][27]="";	tabla[104][28]="";	tabla[104][29]="s58";	tabla[104][30]="s59";	tabla[104][31]="";	tabla[104][32]="";	tabla[104][33]="";	tabla[104][34]="";	tabla[104][35]="";	tabla[104][36]="";	tabla[104][37]="";	tabla[104][38]="";	tabla[104][39]="";	tabla[104][40]="";	tabla[104][41]="";	tabla[104][42]="";	tabla[104][43]="s132";	tabla[104][44]="";	tabla[104][45]="s54";	tabla[104][46]="";	tabla[104][47]="s55";
		tabla[105][0]="s104";	tabla[105][1]="";	tabla[105][2]="";	tabla[105][3]="";	tabla[105][4]="";	tabla[105][5]="";	tabla[105][6]="";	tabla[105][7]="";	tabla[105][8]="";	tabla[105][9]="";	tabla[105][10]="";	tabla[105][11]="F>num";	tabla[105][12]="";	tabla[105][13]="";	tabla[105][14]="";	tabla[105][15]="";	tabla[105][16]="";	tabla[105][17]="";	tabla[105][18]="";	tabla[105][19]="";	tabla[105][20]="";	tabla[105][21]="";	tabla[105][22]="";	tabla[105][23]="";	tabla[105][24]="";	tabla[105][25]="";	tabla[105][26]="";	tabla[105][27]="";	tabla[105][28]="";	tabla[105][29]="";	tabla[105][30]="";	tabla[105][31]="";	tabla[105][32]="";	tabla[105][33]="";	tabla[105][34]="";	tabla[105][35]="";	tabla[105][36]="";	tabla[105][37]="";	tabla[105][38]="";	tabla[105][39]="";	tabla[105][40]="";	tabla[105][41]="";	tabla[105][42]="";	tabla[105][43]="";	tabla[105][44]="";	tabla[105][45]="";	tabla[105][46]="";	tabla[105][47]="";
		tabla[106][0]="s105";	tabla[106][1]="";	tabla[106][2]="";	tabla[106][3]="";	tabla[106][4]="";	tabla[106][5]="";	tabla[106][6]="";	tabla[106][7]="";	tabla[106][8]="";	tabla[106][9]="";	tabla[106][10]="";	tabla[106][11]="F>numf";	tabla[106][12]="";	tabla[106][13]="";	tabla[106][14]="";	tabla[106][15]="";	tabla[106][16]="";	tabla[106][17]="";	tabla[106][18]="";	tabla[106][19]="";	tabla[106][20]="";	tabla[106][21]="";	tabla[106][22]="";	tabla[106][23]="";	tabla[106][24]="";	tabla[106][25]="";	tabla[106][26]="";	tabla[106][27]="";	tabla[106][28]="";	tabla[106][29]="";	tabla[106][30]="";	tabla[106][31]="";	tabla[106][32]="";	tabla[106][33]="";	tabla[106][34]="";	tabla[106][35]="";	tabla[106][36]="";	tabla[106][37]="";	tabla[106][38]="";	tabla[106][39]="";	tabla[106][40]="";	tabla[106][41]="";	tabla[106][42]="";	tabla[106][43]="";	tabla[106][44]="";	tabla[106][45]="";	tabla[106][46]="";	tabla[106][47]="";
		tabla[107][0]="s106";	tabla[107][1]="";	tabla[107][2]="";	tabla[107][3]="";	tabla[107][4]="";	tabla[107][5]="";	tabla[107][6]="";	tabla[107][7]="";	tabla[107][8]="";	tabla[107][9]="";	tabla[107][10]="";	tabla[107][11]="F>litcar";	tabla[107][12]="";	tabla[107][13]="";	tabla[107][14]="";	tabla[107][15]="";	tabla[107][16]="";	tabla[107][17]="";	tabla[107][18]="";	tabla[107][19]="";	tabla[107][20]="";	tabla[107][21]="";	tabla[107][22]="";	tabla[107][23]="";	tabla[107][24]="";	tabla[107][25]="";	tabla[107][26]="";	tabla[107][27]="";	tabla[107][28]="";	tabla[107][29]="";	tabla[107][30]="";	tabla[107][31]="";	tabla[107][32]="";	tabla[107][33]="";	tabla[107][34]="";	tabla[107][35]="";	tabla[107][36]="";	tabla[107][37]="";	tabla[107][38]="";	tabla[107][39]="";	tabla[107][40]="";	tabla[107][41]="";	tabla[107][42]="";	tabla[107][43]="";	tabla[107][44]="";	tabla[107][45]="";	tabla[107][46]="";	tabla[107][47]="";
		tabla[108][0]="s107";	tabla[108][1]="";	tabla[108][2]="";	tabla[108][3]="";	tabla[108][4]="";	tabla[108][5]="";	tabla[108][6]="";	tabla[108][7]="";	tabla[108][8]="";	tabla[108][9]="";	tabla[108][10]="";	tabla[108][11]="s133";	tabla[108][12]="";	tabla[108][13]="";	tabla[108][14]="";	tabla[108][15]="";	tabla[108][16]="";	tabla[108][17]="";	tabla[108][18]="";	tabla[108][19]="";	tabla[108][20]="";	tabla[108][21]="";	tabla[108][22]="";	tabla[108][23]="";	tabla[108][24]="";	tabla[108][25]="";	tabla[108][26]="";	tabla[108][27]="";	tabla[108][28]="";	tabla[108][29]="";	tabla[108][30]="";	tabla[108][31]="";	tabla[108][32]="";	tabla[108][33]="";	tabla[108][34]="";	tabla[108][35]="";	tabla[108][36]="";	tabla[108][37]="";	tabla[108][38]="";	tabla[108][39]="";	tabla[108][40]="";	tabla[108][41]="";	tabla[108][42]="";	tabla[108][43]="";	tabla[108][44]="";	tabla[108][45]="";	tabla[108][46]="";	tabla[108][47]="";
		tabla[109][0]="s108";	tabla[109][1]="";	tabla[109][2]="E>E_Term_+";	tabla[109][3]="";	tabla[109][4]="";	tabla[109][5]="";	tabla[109][6]="";	tabla[109][7]="";	tabla[109][8]="";	tabla[109][9]="";	tabla[109][10]="";	tabla[109][11]="";	tabla[109][12]="";	tabla[109][13]="";	tabla[109][14]="";	tabla[109][15]="";	tabla[109][16]="";	tabla[109][17]="";	tabla[109][18]="";	tabla[109][19]="";	tabla[109][20]="";	tabla[109][21]="";	tabla[109][22]="";	tabla[109][23]="";	tabla[109][24]="";	tabla[109][25]="";	tabla[109][26]="";	tabla[109][27]="";	tabla[109][28]="";	tabla[109][29]="";	tabla[109][30]="";	tabla[109][31]="";	tabla[109][32]="";	tabla[109][33]="";	tabla[109][34]="";	tabla[109][35]="";	tabla[109][36]="";	tabla[109][37]="";	tabla[109][38]="";	tabla[109][39]="";	tabla[109][40]="";	tabla[109][41]="";	tabla[109][42]="";	tabla[109][43]="";	tabla[109][44]="";	tabla[109][45]="";	tabla[109][46]="";	tabla[109][47]="";
		tabla[110][0]="s109";	tabla[110][1]="";	tabla[110][2]="E>E_Term_-";	tabla[110][3]="";	tabla[110][4]="";	tabla[110][5]="";	tabla[110][6]="";	tabla[110][7]="";	tabla[110][8]="";	tabla[110][9]="";	tabla[110][10]="";	tabla[110][11]="";	tabla[110][12]="";	tabla[110][13]="";	tabla[110][14]="";	tabla[110][15]="";	tabla[110][16]="";	tabla[110][17]="";	tabla[110][18]="";	tabla[110][19]="";	tabla[110][20]="";	tabla[110][21]="";	tabla[110][22]="";	tabla[110][23]="";	tabla[110][24]="";	tabla[110][25]="";	tabla[110][26]="";	tabla[110][27]="";	tabla[110][28]="";	tabla[110][29]="";	tabla[110][30]="";	tabla[110][31]="";	tabla[110][32]="";	tabla[110][33]="";	tabla[110][34]="";	tabla[110][35]="";	tabla[110][36]="";	tabla[110][37]="";	tabla[110][38]="";	tabla[110][39]="";	tabla[110][40]="";	tabla[110][41]="";	tabla[110][42]="";	tabla[110][43]="";	tabla[110][44]="";	tabla[110][45]="";	tabla[110][46]="";	tabla[110][47]="";
		tabla[111][0]="s110";	tabla[111][1]="";	tabla[111][2]="T>T_F_*";	tabla[111][3]="";	tabla[111][4]="";	tabla[111][5]="";	tabla[111][6]="";	tabla[111][7]="";	tabla[111][8]="";	tabla[111][9]="";	tabla[111][10]="";	tabla[111][11]="";	tabla[111][12]="";	tabla[111][13]="";	tabla[111][14]="";	tabla[111][15]="";	tabla[111][16]="";	tabla[111][17]="";	tabla[111][18]="";	tabla[111][19]="";	tabla[111][20]="";	tabla[111][21]="";	tabla[111][22]="";	tabla[111][23]="";	tabla[111][24]="";	tabla[111][25]="T>T_F_*";	tabla[111][26]="T>T_F_*";	tabla[111][27]="";	tabla[111][28]="";	tabla[111][29]="";	tabla[111][30]="";	tabla[111][31]="";	tabla[111][32]="";	tabla[111][33]="";	tabla[111][34]="";	tabla[111][35]="";	tabla[111][36]="";	tabla[111][37]="";	tabla[111][38]="";	tabla[111][39]="";	tabla[111][40]="";	tabla[111][41]="";	tabla[111][42]="";	tabla[111][43]="";	tabla[111][44]="";	tabla[111][45]="";	tabla[111][46]="";	tabla[111][47]="";
		tabla[112][0]="s111";	tabla[112][1]="";	tabla[112][2]="T>T_F_/";	tabla[112][3]="";	tabla[112][4]="";	tabla[112][5]="";	tabla[112][6]="";	tabla[112][7]="";	tabla[112][8]="";	tabla[112][9]="";	tabla[112][10]="";	tabla[112][11]="";	tabla[112][12]="";	tabla[112][13]="";	tabla[112][14]="";	tabla[112][15]="";	tabla[112][16]="";	tabla[112][17]="";	tabla[112][18]="";	tabla[112][19]="";	tabla[112][20]="";	tabla[112][21]="";	tabla[112][22]="";	tabla[112][23]="";	tabla[112][24]="";	tabla[112][25]="T>T_F_/";	tabla[112][26]="T>T_F_/";	tabla[112][27]="";	tabla[112][28]="";	tabla[112][29]="";	tabla[112][30]="";	tabla[112][31]="";	tabla[112][32]="";	tabla[112][33]="";	tabla[112][34]="";	tabla[112][35]="";	tabla[112][36]="";	tabla[112][37]="";	tabla[112][38]="";	tabla[112][39]="";	tabla[112][40]="";	tabla[112][41]="";	tabla[112][42]="";	tabla[112][43]="";	tabla[112][44]="";	tabla[112][45]="";	tabla[112][46]="";	tabla[112][47]="";
		tabla[113][0]="s112";	tabla[113][1]="";	tabla[113][2]="";	tabla[113][3]="";	tabla[113][4]="";	tabla[113][5]="";	tabla[113][6]="";	tabla[113][7]="";	tabla[113][8]="";	tabla[113][9]="";	tabla[113][10]="";	tabla[113][11]="E>@";	tabla[113][12]="";	tabla[113][13]="";	tabla[113][14]="";	tabla[113][15]="";	tabla[113][16]="";	tabla[113][17]="";	tabla[113][18]="";	tabla[113][19]="";	tabla[113][20]="";	tabla[113][21]="";	tabla[113][22]="";	tabla[113][23]="";	tabla[113][24]="";	tabla[113][25]="s80";	tabla[113][26]="s81";	tabla[113][27]="";	tabla[113][28]="";	tabla[113][29]="";	tabla[113][30]="";	tabla[113][31]="";	tabla[113][32]="";	tabla[113][33]="";	tabla[113][34]="";	tabla[113][35]="";	tabla[113][36]="";	tabla[113][37]="";	tabla[113][38]="";	tabla[113][39]="";	tabla[113][40]="";	tabla[113][41]="";	tabla[113][42]="";	tabla[113][43]="";	tabla[113][44]="s134";	tabla[113][45]="";	tabla[113][46]="";	tabla[113][47]="";
		tabla[114][0]="s113";	tabla[114][1]="";	tabla[114][2]="";	tabla[114][3]="";	tabla[114][4]="";	tabla[114][5]="";	tabla[114][6]="";	tabla[114][7]="";	tabla[114][8]="";	tabla[114][9]="";	tabla[114][10]="";	tabla[114][11]="E>@";	tabla[114][12]="";	tabla[114][13]="";	tabla[114][14]="";	tabla[114][15]="";	tabla[114][16]="";	tabla[114][17]="";	tabla[114][18]="";	tabla[114][19]="";	tabla[114][20]="";	tabla[114][21]="";	tabla[114][22]="";	tabla[114][23]="";	tabla[114][24]="";	tabla[114][25]="s80";	tabla[114][26]="s81";	tabla[114][27]="";	tabla[114][28]="";	tabla[114][29]="";	tabla[114][30]="";	tabla[114][31]="";	tabla[114][32]="";	tabla[114][33]="";	tabla[114][34]="";	tabla[114][35]="";	tabla[114][36]="";	tabla[114][37]="";	tabla[114][38]="";	tabla[114][39]="";	tabla[114][40]="";	tabla[114][41]="";	tabla[114][42]="";	tabla[114][43]="";	tabla[114][44]="s135";	tabla[114][45]="";	tabla[114][46]="";	tabla[114][47]="";
		tabla[115][0]="s114";	tabla[115][1]="";	tabla[115][2]="";	tabla[115][3]="";	tabla[115][4]="";	tabla[115][5]="";	tabla[115][6]="";	tabla[115][7]="";	tabla[115][8]="";	tabla[115][9]="";	tabla[115][10]="";	tabla[115][11]="T>@";	tabla[115][12]="";	tabla[115][13]="";	tabla[115][14]="";	tabla[115][15]="";	tabla[115][16]="";	tabla[115][17]="";	tabla[115][18]="";	tabla[115][19]="";	tabla[115][20]="";	tabla[115][21]="";	tabla[115][22]="";	tabla[115][23]="";	tabla[115][24]="";	tabla[115][25]="T>@";	tabla[115][26]="T>@";	tabla[115][27]="s83";	tabla[115][28]="s84";	tabla[115][29]="";	tabla[115][30]="";	tabla[115][31]="";	tabla[115][32]="";	tabla[115][33]="";	tabla[115][34]="";	tabla[115][35]="";	tabla[115][36]="";	tabla[115][37]="";	tabla[115][38]="";	tabla[115][39]="";	tabla[115][40]="";	tabla[115][41]="";	tabla[115][42]="";	tabla[115][43]="";	tabla[115][44]="";	tabla[115][45]="";	tabla[115][46]="s136";	tabla[115][47]="";
		tabla[116][0]="s115";	tabla[116][1]="";	tabla[116][2]="";	tabla[116][3]="";	tabla[116][4]="";	tabla[116][5]="";	tabla[116][6]="";	tabla[116][7]="";	tabla[116][8]="";	tabla[116][9]="";	tabla[116][10]="";	tabla[116][11]="T>@";	tabla[116][12]="";	tabla[116][13]="";	tabla[116][14]="";	tabla[116][15]="";	tabla[116][16]="";	tabla[116][17]="";	tabla[116][18]="";	tabla[116][19]="";	tabla[116][20]="";	tabla[116][21]="";	tabla[116][22]="";	tabla[116][23]="";	tabla[116][24]="";	tabla[116][25]="T>@";	tabla[116][26]="T>@";	tabla[116][27]="s83";	tabla[116][28]="s84";	tabla[116][29]="";	tabla[116][30]="";	tabla[116][31]="";	tabla[116][32]="";	tabla[116][33]="";	tabla[116][34]="";	tabla[116][35]="";	tabla[116][36]="";	tabla[116][37]="";	tabla[116][38]="";	tabla[116][39]="";	tabla[116][40]="";	tabla[116][41]="";	tabla[116][42]="";	tabla[116][43]="";	tabla[116][44]="";	tabla[116][45]="";	tabla[116][46]="s137";	tabla[116][47]="";
		tabla[117][0]="s116";	tabla[117][1]="";	tabla[117][2]="";	tabla[117][3]="";	tabla[117][4]="";	tabla[117][5]="";	tabla[117][6]="";	tabla[117][7]="";	tabla[117][8]="";	tabla[117][9]="";	tabla[117][10]="";	tabla[117][11]="F>)_Exp_(";	tabla[117][12]="";	tabla[117][13]="";	tabla[117][14]="";	tabla[117][15]="";	tabla[117][16]="";	tabla[117][17]="";	tabla[117][18]="";	tabla[117][19]="";	tabla[117][20]="";	tabla[117][21]="";	tabla[117][22]="";	tabla[117][23]="";	tabla[117][24]="";	tabla[117][25]="F>)_Exp_(";	tabla[117][26]="F>)_Exp_(";	tabla[117][27]="F>)_Exp_(";	tabla[117][28]="F>)_Exp_(";	tabla[117][29]="";	tabla[117][30]="";	tabla[117][31]="";	tabla[117][32]="";	tabla[117][33]="";	tabla[117][34]="";	tabla[117][35]="";	tabla[117][36]="";	tabla[117][37]="";	tabla[117][38]="";	tabla[117][39]="";	tabla[117][40]="";	tabla[117][41]="";	tabla[117][42]="";	tabla[117][43]="";	tabla[117][44]="";	tabla[117][45]="";	tabla[117][46]="";	tabla[117][47]="";
		tabla[118][0]="s117";	tabla[118][1]="";	tabla[118][2]="s138";	tabla[118][3]="";	tabla[118][4]="";	tabla[118][5]="";	tabla[118][6]="";	tabla[118][7]="";	tabla[118][8]="";	tabla[118][9]="";	tabla[118][10]="";	tabla[118][11]="";	tabla[118][12]="";	tabla[118][13]="";	tabla[118][14]="";	tabla[118][15]="";	tabla[118][16]="";	tabla[118][17]="";	tabla[118][18]="";	tabla[118][19]="";	tabla[118][20]="";	tabla[118][21]="";	tabla[118][22]="";	tabla[118][23]="";	tabla[118][24]="";	tabla[118][25]="";	tabla[118][26]="";	tabla[118][27]="";	tabla[118][28]="";	tabla[118][29]="";	tabla[118][30]="";	tabla[118][31]="";	tabla[118][32]="";	tabla[118][33]="";	tabla[118][34]="";	tabla[118][35]="";	tabla[118][36]="";	tabla[118][37]="";	tabla[118][38]="";	tabla[118][39]="";	tabla[118][40]="";	tabla[118][41]="";	tabla[118][42]="";	tabla[118][43]="";	tabla[118][44]="";	tabla[118][45]="";	tabla[118][46]="";	tabla[118][47]="";
		tabla[119][0]="s118";	tabla[119][1]="s102";	tabla[119][2]="";	tabla[119][3]="";	tabla[119][4]="";	tabla[119][5]="";	tabla[119][6]="";	tabla[119][7]="";	tabla[119][8]="";	tabla[119][9]="";	tabla[119][10]="s103";	tabla[119][11]="";	tabla[119][12]="";	tabla[119][13]="";	tabla[119][14]="";	tabla[119][15]="";	tabla[119][16]="";	tabla[119][17]="";	tabla[119][18]="";	tabla[119][19]="s106";	tabla[119][20]="";	tabla[119][21]="";	tabla[119][22]="";	tabla[119][23]="";	tabla[119][24]="";	tabla[119][25]="";	tabla[119][26]="";	tabla[119][27]="";	tabla[119][28]="";	tabla[119][29]="s104";	tabla[119][30]="s105";	tabla[119][31]="";	tabla[119][32]="";	tabla[119][33]="";	tabla[119][34]="";	tabla[119][35]="";	tabla[119][36]="";	tabla[119][37]="";	tabla[119][38]="";	tabla[119][39]="";	tabla[119][40]="";	tabla[119][41]="";	tabla[119][42]="";	tabla[119][43]="";	tabla[119][44]="";	tabla[119][45]="";	tabla[119][46]="";	tabla[119][47]="s139";
		tabla[120][0]="s119";	tabla[120][1]="Comp>_==_";	tabla[120][2]="";	tabla[120][3]="";	tabla[120][4]="";	tabla[120][5]="";	tabla[120][6]="";	tabla[120][7]="";	tabla[120][8]="";	tabla[120][9]="";	tabla[120][10]="Comp>_==_";	tabla[120][11]="";	tabla[120][12]="";	tabla[120][13]="";	tabla[120][14]="";	tabla[120][15]="";	tabla[120][16]="";	tabla[120][17]="";	tabla[120][18]="";	tabla[120][19]="Comp>_==_";	tabla[120][20]="";	tabla[120][21]="";	tabla[120][22]="";	tabla[120][23]="";	tabla[120][24]="";	tabla[120][25]="";	tabla[120][26]="";	tabla[120][27]="";	tabla[120][28]="";	tabla[120][29]="Comp>_==_";	tabla[120][30]="Comp>_==_";	tabla[120][31]="";	tabla[120][32]="";	tabla[120][33]="";	tabla[120][34]="";	tabla[120][35]="";	tabla[120][36]="";	tabla[120][37]="";	tabla[120][38]="";	tabla[120][39]="";	tabla[120][40]="";	tabla[120][41]="";	tabla[120][42]="";	tabla[120][43]="";	tabla[120][44]="";	tabla[120][45]="";	tabla[120][46]="";	tabla[120][47]="";
		tabla[121][0]="s120";	tabla[121][1]="Comp>_<_";	tabla[121][2]="";	tabla[121][3]="";	tabla[121][4]="";	tabla[121][5]="";	tabla[121][6]="";	tabla[121][7]="";	tabla[121][8]="";	tabla[121][9]="";	tabla[121][10]="Comp>_<_";	tabla[121][11]="";	tabla[121][12]="";	tabla[121][13]="";	tabla[121][14]="";	tabla[121][15]="";	tabla[121][16]="";	tabla[121][17]="";	tabla[121][18]="";	tabla[121][19]="Comp>_<_";	tabla[121][20]="";	tabla[121][21]="";	tabla[121][22]="";	tabla[121][23]="";	tabla[121][24]="";	tabla[121][25]="";	tabla[121][26]="";	tabla[121][27]="";	tabla[121][28]="";	tabla[121][29]="Comp>_<_";	tabla[121][30]="Comp>_<_";	tabla[121][31]="";	tabla[121][32]="";	tabla[121][33]="";	tabla[121][34]="";	tabla[121][35]="";	tabla[121][36]="";	tabla[121][37]="";	tabla[121][38]="";	tabla[121][39]="";	tabla[121][40]="";	tabla[121][41]="";	tabla[121][42]="";	tabla[121][43]="";	tabla[121][44]="";	tabla[121][45]="";	tabla[121][46]="";	tabla[121][47]="";
		tabla[122][0]="s121";	tabla[122][1]="Comp> _>_";	tabla[122][2]="";	tabla[122][3]="";	tabla[122][4]="";	tabla[122][5]="";	tabla[122][6]="";	tabla[122][7]="";	tabla[122][8]="";	tabla[122][9]="";	tabla[122][10]="Comp> _>_";	tabla[122][11]="";	tabla[122][12]="";	tabla[122][13]="";	tabla[122][14]="";	tabla[122][15]="";	tabla[122][16]="";	tabla[122][17]="";	tabla[122][18]="";	tabla[122][19]="Comp> _>_";	tabla[122][20]="";	tabla[122][21]="";	tabla[122][22]="";	tabla[122][23]="";	tabla[122][24]="";	tabla[122][25]="";	tabla[122][26]="";	tabla[122][27]="";	tabla[122][28]="";	tabla[122][29]="Comp> _>_";	tabla[122][30]="Comp> _>_";	tabla[122][31]="";	tabla[122][32]="";	tabla[122][33]="";	tabla[122][34]="";	tabla[122][35]="";	tabla[122][36]="";	tabla[122][37]="";	tabla[122][38]="";	tabla[122][39]="";	tabla[122][40]="";	tabla[122][41]="";	tabla[122][42]="";	tabla[122][43]="";	tabla[122][44]="";	tabla[122][45]="";	tabla[122][46]="";	tabla[122][47]="";
		tabla[123][0]="s122";	tabla[123][1]="";	tabla[123][2]="";	tabla[123][3]="";	tabla[123][4]="";	tabla[123][5]="";	tabla[123][6]="";	tabla[123][7]="";	tabla[123][8]="";	tabla[123][9]="";	tabla[123][10]="";	tabla[123][11]="s140";	tabla[123][12]="";	tabla[123][13]="";	tabla[123][14]="";	tabla[123][15]="";	tabla[123][16]="";	tabla[123][17]="";	tabla[123][18]="";	tabla[123][19]="";	tabla[123][20]="";	tabla[123][21]="";	tabla[123][22]="";	tabla[123][23]="";	tabla[123][24]="";	tabla[123][25]="";	tabla[123][26]="";	tabla[123][27]="";	tabla[123][28]="";	tabla[123][29]="";	tabla[123][30]="";	tabla[123][31]="";	tabla[123][32]="";	tabla[123][33]="";	tabla[123][34]="";	tabla[123][35]="";	tabla[123][36]="";	tabla[123][37]="";	tabla[123][38]="";	tabla[123][39]="";	tabla[123][40]="";	tabla[123][41]="";	tabla[123][42]="";	tabla[123][43]="";	tabla[123][44]="";	tabla[123][45]="";	tabla[123][46]="";	tabla[123][47]="";
		tabla[124][0]="s123";	tabla[124][1]="s33";	tabla[124][2]="";	tabla[124][3]="";	tabla[124][4]="";	tabla[124][5]="";	tabla[124][6]="";	tabla[124][7]="";	tabla[124][8]="s34";	tabla[124][9]="S>@";	tabla[124][10]="";	tabla[124][11]="";	tabla[124][12]="s35";	tabla[124][13]="";	tabla[124][14]="s36";	tabla[124][15]="s37";	tabla[124][16]="";	tabla[124][17]="";	tabla[124][18]="";	tabla[124][19]="";	tabla[124][20]="";	tabla[124][21]="";	tabla[124][22]="";	tabla[124][23]="";	tabla[124][24]="";	tabla[124][25]="";	tabla[124][26]="";	tabla[124][27]="";	tabla[124][28]="";	tabla[124][29]="";	tabla[124][30]="";	tabla[124][31]="";	tabla[124][32]="";	tabla[124][33]="";	tabla[124][34]="";	tabla[124][35]="";	tabla[124][36]="";	tabla[124][37]="s141";	tabla[124][38]="";	tabla[124][39]="";	tabla[124][40]="";	tabla[124][41]="";	tabla[124][42]="";	tabla[124][43]="";	tabla[124][44]="";	tabla[124][45]="";	tabla[124][46]="";	tabla[124][47]="";
		tabla[125][0]="s124";	tabla[125][1]="s88";	tabla[125][2]="";	tabla[125][3]="";	tabla[125][4]="";	tabla[125][5]="";	tabla[125][6]="";	tabla[125][7]="";	tabla[125][8]="";	tabla[125][9]="";	tabla[125][10]="s89";	tabla[125][11]="";	tabla[125][12]="";	tabla[125][13]="";	tabla[125][14]="";	tabla[125][15]="";	tabla[125][16]="";	tabla[125][17]="";	tabla[125][18]="";	tabla[125][19]="s92";	tabla[125][20]="";	tabla[125][21]="";	tabla[125][22]="";	tabla[125][23]="";	tabla[125][24]="";	tabla[125][25]="";	tabla[125][26]="";	tabla[125][27]="";	tabla[125][28]="";	tabla[125][29]="s90";	tabla[125][30]="s91";	tabla[125][31]="";	tabla[125][32]="";	tabla[125][33]="";	tabla[125][34]="";	tabla[125][35]="";	tabla[125][36]="";	tabla[125][37]="";	tabla[125][38]="";	tabla[125][39]="";	tabla[125][40]="";	tabla[125][41]="s142";	tabla[125][42]="";	tabla[125][43]="";	tabla[125][44]="";	tabla[125][45]="";	tabla[125][46]="";	tabla[125][47]="s87";
		tabla[126][0]="s125";	tabla[126][1]="";	tabla[126][2]="";	tabla[126][3]="";	tabla[126][4]="";	tabla[126][5]="";	tabla[126][6]="";	tabla[126][7]="";	tabla[126][8]="";	tabla[126][9]="";	tabla[126][10]="";	tabla[126][11]="";	tabla[126][12]="";	tabla[126][13]="";	tabla[126][14]="";	tabla[126][15]="";	tabla[126][16]="";	tabla[126][17]="";	tabla[126][18]="s100";	tabla[126][19]="";	tabla[126][20]="";	tabla[126][21]="";	tabla[126][22]="";	tabla[126][23]="";	tabla[126][24]="";	tabla[126][25]="";	tabla[126][26]="";	tabla[126][27]="";	tabla[126][28]="";	tabla[126][29]="";	tabla[126][30]="";	tabla[126][31]="";	tabla[126][32]="";	tabla[126][33]="";	tabla[126][34]="";	tabla[126][35]="";	tabla[126][36]="";	tabla[126][37]="";	tabla[126][38]="";	tabla[126][39]="s143";	tabla[126][40]="";	tabla[126][41]="";	tabla[126][42]="";	tabla[126][43]="";	tabla[126][44]="";	tabla[126][45]="";	tabla[126][46]="";	tabla[126][47]="";
		tabla[127][0]="s126";	tabla[127][1]="s102";	tabla[127][2]="";	tabla[127][3]="";	tabla[127][4]="";	tabla[127][5]="";	tabla[127][6]="";	tabla[127][7]="";	tabla[127][8]="";	tabla[127][9]="";	tabla[127][10]="s103";	tabla[127][11]="";	tabla[127][12]="";	tabla[127][13]="";	tabla[127][14]="";	tabla[127][15]="";	tabla[127][16]="";	tabla[127][17]="";	tabla[127][18]="";	tabla[127][19]="s106";	tabla[127][20]="";	tabla[127][21]="";	tabla[127][22]="";	tabla[127][23]="";	tabla[127][24]="";	tabla[127][25]="";	tabla[127][26]="";	tabla[127][27]="";	tabla[127][28]="";	tabla[127][29]="s104";	tabla[127][30]="s105";	tabla[127][31]="";	tabla[127][32]="";	tabla[127][33]="";	tabla[127][34]="";	tabla[127][35]="";	tabla[127][36]="";	tabla[127][37]="";	tabla[127][38]="";	tabla[127][39]="";	tabla[127][40]="";	tabla[127][41]="";	tabla[127][42]="";	tabla[127][43]="";	tabla[127][44]="";	tabla[127][45]="";	tabla[127][46]="";	tabla[127][47]="s144";
		tabla[128][0]="s127";	tabla[128][1]="s102";	tabla[128][2]="";	tabla[128][3]="";	tabla[128][4]="";	tabla[128][5]="";	tabla[128][6]="";	tabla[128][7]="";	tabla[128][8]="";	tabla[128][9]="";	tabla[128][10]="s103";	tabla[128][11]="";	tabla[128][12]="";	tabla[128][13]="";	tabla[128][14]="";	tabla[128][15]="";	tabla[128][16]="";	tabla[128][17]="";	tabla[128][18]="";	tabla[128][19]="s106";	tabla[128][20]="";	tabla[128][21]="";	tabla[128][22]="";	tabla[128][23]="";	tabla[128][24]="";	tabla[128][25]="";	tabla[128][26]="";	tabla[128][27]="";	tabla[128][28]="";	tabla[128][29]="s104";	tabla[128][30]="s105";	tabla[128][31]="";	tabla[128][32]="";	tabla[128][33]="";	tabla[128][34]="";	tabla[128][35]="";	tabla[128][36]="";	tabla[128][37]="";	tabla[128][38]="";	tabla[128][39]="";	tabla[128][40]="";	tabla[128][41]="";	tabla[128][42]="";	tabla[128][43]="";	tabla[128][44]="";	tabla[128][45]="";	tabla[128][46]="";	tabla[128][47]="s145";
		tabla[129][0]="s128";	tabla[129][1]="D>D_;_Sd_id_Tipo";	tabla[129][2]="";	tabla[129][3]="";	tabla[129][4]="";	tabla[129][5]="";	tabla[129][6]="";	tabla[129][7]="";	tabla[129][8]="D>D_;_Sd_id_Tipo";	tabla[129][9]="D>D_;_Sd_id_Tipo";	tabla[129][10]="";	tabla[129][11]="";	tabla[129][12]="D>D_;_Sd_id_Tipo";	tabla[129][13]="";	tabla[129][14]="D>D_;_Sd_id_Tipo";	tabla[129][15]="D>D_;_Sd_id_Tipo";	tabla[129][16]="";	tabla[129][17]="";	tabla[129][18]="";	tabla[129][19]="";	tabla[129][20]="";	tabla[129][21]="";	tabla[129][22]="";	tabla[129][23]="";	tabla[129][24]="";	tabla[129][25]="";	tabla[129][26]="";	tabla[129][27]="";	tabla[129][28]="";	tabla[129][29]="";	tabla[129][30]="";	tabla[129][31]="";	tabla[129][32]="";	tabla[129][33]="";	tabla[129][34]="";	tabla[129][35]="";	tabla[129][36]="";	tabla[129][37]="";	tabla[129][38]="";	tabla[129][39]="";	tabla[129][40]="";	tabla[129][41]="";	tabla[129][42]="";	tabla[129][43]="";	tabla[129][44]="";	tabla[129][45]="";	tabla[129][46]="";	tabla[129][47]="";
		tabla[130][0]="s129";	tabla[130][1]="s8";	tabla[130][2]="";	tabla[130][3]="";	tabla[130][4]="";	tabla[130][5]="";	tabla[130][6]="";	tabla[130][7]="";	tabla[130][8]="s9";	tabla[130][9]="";	tabla[130][10]="";	tabla[130][11]="";	tabla[130][12]="s10";	tabla[130][13]="";	tabla[130][14]="s11";	tabla[130][15]="s12";	tabla[130][16]="";	tabla[130][17]="";	tabla[130][18]="";	tabla[130][19]="";	tabla[130][20]="";	tabla[130][21]="";	tabla[130][22]="";	tabla[130][23]="";	tabla[130][24]="";	tabla[130][25]="";	tabla[130][26]="";	tabla[130][27]="";	tabla[130][28]="";	tabla[130][29]="";	tabla[130][30]="";	tabla[130][31]="S>@";	tabla[130][32]="";	tabla[130][33]="";	tabla[130][34]="";	tabla[130][35]="";	tabla[130][36]="";	tabla[130][37]="s146";	tabla[130][38]="";	tabla[130][39]="";	tabla[130][40]="";	tabla[130][41]="";	tabla[130][42]="";	tabla[130][43]="";	tabla[130][44]="";	tabla[130][45]="";	tabla[130][46]="";	tabla[130][47]="";
		tabla[131][0]="s130";	tabla[131][1]="";	tabla[131][2]="";	tabla[131][3]="";	tabla[131][4]="";	tabla[131][5]="";	tabla[131][6]="";	tabla[131][7]="";	tabla[131][8]="";	tabla[131][9]="";	tabla[131][10]="";	tabla[131][11]="";	tabla[131][12]="";	tabla[131][13]="";	tabla[131][14]="";	tabla[131][15]="";	tabla[131][16]="";	tabla[131][17]="";	tabla[131][18]="";	tabla[131][19]="";	tabla[131][20]="s147";	tabla[131][21]="";	tabla[131][22]="";	tabla[131][23]="";	tabla[131][24]="";	tabla[131][25]="";	tabla[131][26]="";	tabla[131][27]="";	tabla[131][28]="";	tabla[131][29]="";	tabla[131][30]="";	tabla[131][31]="";	tabla[131][32]="";	tabla[131][33]="";	tabla[131][34]="";	tabla[131][35]="";	tabla[131][36]="";	tabla[131][37]="";	tabla[131][38]="";	tabla[131][39]="";	tabla[131][40]="";	tabla[131][41]="";	tabla[131][42]="";	tabla[131][43]="";	tabla[131][44]="";	tabla[131][45]="";	tabla[131][46]="";	tabla[131][47]="";
		tabla[132][0]="s131";	tabla[132][1]="";	tabla[132][2]="s148";	tabla[132][3]="";	tabla[132][4]="";	tabla[132][5]="";	tabla[132][6]="";	tabla[132][7]="";	tabla[132][8]="";	tabla[132][9]="";	tabla[132][10]="";	tabla[132][11]="";	tabla[132][12]="";	tabla[132][13]="";	tabla[132][14]="";	tabla[132][15]="";	tabla[132][16]="";	tabla[132][17]="";	tabla[132][18]="";	tabla[132][19]="";	tabla[132][20]="";	tabla[132][21]="";	tabla[132][22]="";	tabla[132][23]="";	tabla[132][24]="";	tabla[132][25]="";	tabla[132][26]="";	tabla[132][27]="";	tabla[132][28]="";	tabla[132][29]="";	tabla[132][30]="";	tabla[132][31]="";	tabla[132][32]="";	tabla[132][33]="";	tabla[132][34]="";	tabla[132][35]="";	tabla[132][36]="";	tabla[132][37]="";	tabla[132][38]="";	tabla[132][39]="";	tabla[132][40]="";	tabla[132][41]="";	tabla[132][42]="";	tabla[132][43]="";	tabla[132][44]="";	tabla[132][45]="";	tabla[132][46]="";	tabla[132][47]="";
		tabla[133][0]="s132";	tabla[133][1]="";	tabla[133][2]="";	tabla[133][3]="";	tabla[133][4]="";	tabla[133][5]="";	tabla[133][6]="";	tabla[133][7]="";	tabla[133][8]="";	tabla[133][9]="";	tabla[133][10]="";	tabla[133][11]="s149";	tabla[133][12]="";	tabla[133][13]="";	tabla[133][14]="";	tabla[133][15]="";	tabla[133][16]="";	tabla[133][17]="";	tabla[133][18]="";	tabla[133][19]="";	tabla[133][20]="";	tabla[133][21]="";	tabla[133][22]="";	tabla[133][23]="";	tabla[133][24]="";	tabla[133][25]="";	tabla[133][26]="";	tabla[133][27]="";	tabla[133][28]="";	tabla[133][29]="";	tabla[133][30]="";	tabla[133][31]="";	tabla[133][32]="";	tabla[133][33]="";	tabla[133][34]="";	tabla[133][35]="";	tabla[133][36]="";	tabla[133][37]="";	tabla[133][38]="";	tabla[133][39]="";	tabla[133][40]="";	tabla[133][41]="";	tabla[133][42]="";	tabla[133][43]="";	tabla[133][44]="";	tabla[133][45]="";	tabla[133][46]="";	tabla[133][47]="";
		tabla[134][0]="s133";	tabla[134][1]="";	tabla[134][2]="s150";	tabla[134][3]="";	tabla[134][4]="";	tabla[134][5]="";	tabla[134][6]="";	tabla[134][7]="";	tabla[134][8]="";	tabla[134][9]="";	tabla[134][10]="";	tabla[134][11]="";	tabla[134][12]="";	tabla[134][13]="";	tabla[134][14]="";	tabla[134][15]="";	tabla[134][16]="";	tabla[134][17]="";	tabla[134][18]="";	tabla[134][19]="";	tabla[134][20]="";	tabla[134][21]="";	tabla[134][22]="";	tabla[134][23]="";	tabla[134][24]="";	tabla[134][25]="";	tabla[134][26]="";	tabla[134][27]="";	tabla[134][28]="";	tabla[134][29]="";	tabla[134][30]="";	tabla[134][31]="";	tabla[134][32]="";	tabla[134][33]="";	tabla[134][34]="";	tabla[134][35]="";	tabla[134][36]="";	tabla[134][37]="";	tabla[134][38]="";	tabla[134][39]="";	tabla[134][40]="";	tabla[134][41]="";	tabla[134][42]="";	tabla[134][43]="";	tabla[134][44]="";	tabla[134][45]="";	tabla[134][46]="";	tabla[134][47]="";
		tabla[135][0]="s134";	tabla[135][1]="";	tabla[135][2]="";	tabla[135][3]="";	tabla[135][4]="";	tabla[135][5]="";	tabla[135][6]="";	tabla[135][7]="";	tabla[135][8]="";	tabla[135][9]="";	tabla[135][10]="";	tabla[135][11]="E>E_Term_+";	tabla[135][12]="";	tabla[135][13]="";	tabla[135][14]="";	tabla[135][15]="";	tabla[135][16]="";	tabla[135][17]="";	tabla[135][18]="";	tabla[135][19]="";	tabla[135][20]="";	tabla[135][21]="";	tabla[135][22]="";	tabla[135][23]="";	tabla[135][24]="";	tabla[135][25]="";	tabla[135][26]="";	tabla[135][27]="";	tabla[135][28]="";	tabla[135][29]="";	tabla[135][30]="";	tabla[135][31]="";	tabla[135][32]="";	tabla[135][33]="";	tabla[135][34]="";	tabla[135][35]="";	tabla[135][36]="";	tabla[135][37]="";	tabla[135][38]="";	tabla[135][39]="";	tabla[135][40]="";	tabla[135][41]="";	tabla[135][42]="";	tabla[135][43]="";	tabla[135][44]="";	tabla[135][45]="";	tabla[135][46]="";	tabla[135][47]="";
		tabla[136][0]="s135";	tabla[136][1]="";	tabla[136][2]="";	tabla[136][3]="";	tabla[136][4]="";	tabla[136][5]="";	tabla[136][6]="";	tabla[136][7]="";	tabla[136][8]="";	tabla[136][9]="";	tabla[136][10]="";	tabla[136][11]="E>E_Term_-";	tabla[136][12]="";	tabla[136][13]="";	tabla[136][14]="";	tabla[136][15]="";	tabla[136][16]="";	tabla[136][17]="";	tabla[136][18]="";	tabla[136][19]="";	tabla[136][20]="";	tabla[136][21]="";	tabla[136][22]="";	tabla[136][23]="";	tabla[136][24]="";	tabla[136][25]="";	tabla[136][26]="";	tabla[136][27]="";	tabla[136][28]="";	tabla[136][29]="";	tabla[136][30]="";	tabla[136][31]="";	tabla[136][32]="";	tabla[136][33]="";	tabla[136][34]="";	tabla[136][35]="";	tabla[136][36]="";	tabla[136][37]="";	tabla[136][38]="";	tabla[136][39]="";	tabla[136][40]="";	tabla[136][41]="";	tabla[136][42]="";	tabla[136][43]="";	tabla[136][44]="";	tabla[136][45]="";	tabla[136][46]="";	tabla[136][47]="";
		tabla[137][0]="s136";	tabla[137][1]="";	tabla[137][2]="";	tabla[137][3]="";	tabla[137][4]="";	tabla[137][5]="";	tabla[137][6]="";	tabla[137][7]="";	tabla[137][8]="";	tabla[137][9]="";	tabla[137][10]="";	tabla[137][11]="T>T_F_*";	tabla[137][12]="";	tabla[137][13]="";	tabla[137][14]="";	tabla[137][15]="";	tabla[137][16]="";	tabla[137][17]="";	tabla[137][18]="";	tabla[137][19]="";	tabla[137][20]="";	tabla[137][21]="";	tabla[137][22]="";	tabla[137][23]="";	tabla[137][24]="";	tabla[137][25]="T>T_F_*";	tabla[137][26]="T>T_F_*";	tabla[137][27]="";	tabla[137][28]="";	tabla[137][29]="";	tabla[137][30]="";	tabla[137][31]="";	tabla[137][32]="";	tabla[137][33]="";	tabla[137][34]="";	tabla[137][35]="";	tabla[137][36]="";	tabla[137][37]="";	tabla[137][38]="";	tabla[137][39]="";	tabla[137][40]="";	tabla[137][41]="";	tabla[137][42]="";	tabla[137][43]="";	tabla[137][44]="";	tabla[137][45]="";	tabla[137][46]="";	tabla[137][47]="";
		tabla[138][0]="s137";	tabla[138][1]="";	tabla[138][2]="";	tabla[138][3]="";	tabla[138][4]="";	tabla[138][5]="";	tabla[138][6]="";	tabla[138][7]="";	tabla[138][8]="";	tabla[138][9]="";	tabla[138][10]="";	tabla[138][11]="T>T_F_/";	tabla[138][12]="";	tabla[138][13]="";	tabla[138][14]="";	tabla[138][15]="";	tabla[138][16]="";	tabla[138][17]="";	tabla[138][18]="";	tabla[138][19]="";	tabla[138][20]="";	tabla[138][21]="";	tabla[138][22]="";	tabla[138][23]="";	tabla[138][24]="";	tabla[138][25]="T>T_F_/";	tabla[138][26]="T>T_F_/";	tabla[138][27]="";	tabla[138][28]="";	tabla[138][29]="";	tabla[138][30]="";	tabla[138][31]="";	tabla[138][32]="";	tabla[138][33]="";	tabla[138][34]="";	tabla[138][35]="";	tabla[138][36]="";	tabla[138][37]="";	tabla[138][38]="";	tabla[138][39]="";	tabla[138][40]="";	tabla[138][41]="";	tabla[138][42]="";	tabla[138][43]="";	tabla[138][44]="";	tabla[138][45]="";	tabla[138][46]="";	tabla[138][47]="";
		tabla[139][0]="s138";	tabla[139][1]="s8";	tabla[139][2]="";	tabla[139][3]="";	tabla[139][4]="";	tabla[139][5]="";	tabla[139][6]="";	tabla[139][7]="";	tabla[139][8]="s9";	tabla[139][9]="";	tabla[139][10]="";	tabla[139][11]="";	tabla[139][12]="s10";	tabla[139][13]="";	tabla[139][14]="s11";	tabla[139][15]="s12";	tabla[139][16]="";	tabla[139][17]="";	tabla[139][18]="";	tabla[139][19]="";	tabla[139][20]="";	tabla[139][21]="";	tabla[139][22]="";	tabla[139][23]="";	tabla[139][24]="";	tabla[139][25]="";	tabla[139][26]="";	tabla[139][27]="";	tabla[139][28]="";	tabla[139][29]="";	tabla[139][30]="";	tabla[139][31]="S>@";	tabla[139][32]="";	tabla[139][33]="";	tabla[139][34]="";	tabla[139][35]="";	tabla[139][36]="";	tabla[139][37]="s151";	tabla[139][38]="";	tabla[139][39]="";	tabla[139][40]="";	tabla[139][41]="";	tabla[139][42]="";	tabla[139][43]="";	tabla[139][44]="";	tabla[139][45]="";	tabla[139][46]="";	tabla[139][47]="";
		tabla[140][0]="s139";	tabla[140][1]="";	tabla[140][2]="";	tabla[140][3]="";	tabla[140][4]="";	tabla[140][5]="";	tabla[140][6]="";	tabla[140][7]="";	tabla[140][8]="";	tabla[140][9]="";	tabla[140][10]="";	tabla[140][11]="Cond>_F_Comp_F";	tabla[140][12]="";	tabla[140][13]="";	tabla[140][14]="";	tabla[140][15]="";	tabla[140][16]="";	tabla[140][17]="";	tabla[140][18]="";	tabla[140][19]="";	tabla[140][20]="";	tabla[140][21]="";	tabla[140][22]="";	tabla[140][23]="";	tabla[140][24]="";	tabla[140][25]="";	tabla[140][26]="";	tabla[140][27]="";	tabla[140][28]="";	tabla[140][29]="";	tabla[140][30]="";	tabla[140][31]="";	tabla[140][32]="";	tabla[140][33]="";	tabla[140][34]="";	tabla[140][35]="";	tabla[140][36]="";	tabla[140][37]="";	tabla[140][38]="";	tabla[140][39]="";	tabla[140][40]="";	tabla[140][41]="";	tabla[140][42]="";	tabla[140][43]="";	tabla[140][44]="";	tabla[140][45]="";	tabla[140][46]="";	tabla[140][47]="";
		tabla[141][0]="s140";	tabla[141][1]="";	tabla[141][2]="";	tabla[141][3]="";	tabla[141][4]="";	tabla[141][5]="";	tabla[141][6]="";	tabla[141][7]="";	tabla[141][8]="";	tabla[141][9]="";	tabla[141][10]="";	tabla[141][11]="";	tabla[141][12]="";	tabla[141][13]="";	tabla[141][14]="";	tabla[141][15]="";	tabla[141][16]="";	tabla[141][17]="";	tabla[141][18]="";	tabla[141][19]="";	tabla[141][20]="";	tabla[141][21]="";	tabla[141][22]="F>)_Exp_(";	tabla[141][23]="F>)_Exp_(";	tabla[141][24]="F>)_Exp_(";	tabla[141][25]="";	tabla[141][26]="";	tabla[141][27]="";	tabla[141][28]="";	tabla[141][29]="";	tabla[141][30]="";	tabla[141][31]="";	tabla[141][32]="";	tabla[141][33]="";	tabla[141][34]="";	tabla[141][35]="";	tabla[141][36]="";	tabla[141][37]="";	tabla[141][38]="";	tabla[141][39]="";	tabla[141][40]="";	tabla[141][41]="";	tabla[141][42]="";	tabla[141][43]="";	tabla[141][44]="";	tabla[141][45]="";	tabla[141][46]="";	tabla[141][47]="";
		tabla[142][0]="s141";	tabla[142][1]="";	tabla[142][2]="";	tabla[142][3]="";	tabla[142][4]="";	tabla[142][5]="";	tabla[142][6]="";	tabla[142][7]="";	tabla[142][8]="";	tabla[142][9]="S>S_;_Exp_=_id";	tabla[142][10]="";	tabla[142][11]="";	tabla[142][12]="";	tabla[142][13]="";	tabla[142][14]="";	tabla[142][15]="";	tabla[142][16]="";	tabla[142][17]="";	tabla[142][18]="";	tabla[142][19]="";	tabla[142][20]="";	tabla[142][21]="";	tabla[142][22]="";	tabla[142][23]="";	tabla[142][24]="";	tabla[142][25]="";	tabla[142][26]="";	tabla[142][27]="";	tabla[142][28]="";	tabla[142][29]="";	tabla[142][30]="";	tabla[142][31]="";	tabla[142][32]="";	tabla[142][33]="";	tabla[142][34]="";	tabla[142][35]="";	tabla[142][36]="";	tabla[142][37]="";	tabla[142][38]="";	tabla[142][39]="";	tabla[142][40]="";	tabla[142][41]="";	tabla[142][42]="";	tabla[142][43]="";	tabla[142][44]="";	tabla[142][45]="";	tabla[142][46]="";	tabla[142][47]="";
		tabla[143][0]="s142";	tabla[143][1]="";	tabla[143][2]="";	tabla[143][3]="";	tabla[143][4]="";	tabla[143][5]="";	tabla[143][6]="";	tabla[143][7]="";	tabla[143][8]="";	tabla[143][9]="";	tabla[143][10]="";	tabla[143][11]="s152";	tabla[143][12]="";	tabla[143][13]="";	tabla[143][14]="";	tabla[143][15]="";	tabla[143][16]="";	tabla[143][17]="";	tabla[143][18]="";	tabla[143][19]="";	tabla[143][20]="";	tabla[143][21]="";	tabla[143][22]="";	tabla[143][23]="";	tabla[143][24]="";	tabla[143][25]="";	tabla[143][26]="";	tabla[143][27]="";	tabla[143][28]="";	tabla[143][29]="";	tabla[143][30]="";	tabla[143][31]="";	tabla[143][32]="";	tabla[143][33]="";	tabla[143][34]="";	tabla[143][35]="";	tabla[143][36]="";	tabla[143][37]="";	tabla[143][38]="";	tabla[143][39]="";	tabla[143][40]="";	tabla[143][41]="";	tabla[143][42]="";	tabla[143][43]="";	tabla[143][44]="";	tabla[143][45]="";	tabla[143][46]="";	tabla[143][47]="";
		tabla[144][0]="s143";	tabla[144][1]="";	tabla[144][2]="";	tabla[144][3]="";	tabla[144][4]="";	tabla[144][5]="";	tabla[144][6]="";	tabla[144][7]="";	tabla[144][8]="";	tabla[144][9]="";	tabla[144][10]="";	tabla[144][11]="";	tabla[144][12]="";	tabla[144][13]="s153";	tabla[144][14]="";	tabla[144][15]="";	tabla[144][16]="";	tabla[144][17]="";	tabla[144][18]="";	tabla[144][19]="";	tabla[144][20]="";	tabla[144][21]="";	tabla[144][22]="";	tabla[144][23]="";	tabla[144][24]="";	tabla[144][25]="";	tabla[144][26]="";	tabla[144][27]="";	tabla[144][28]="";	tabla[144][29]="";	tabla[144][30]="";	tabla[144][31]="";	tabla[144][32]="";	tabla[144][33]="";	tabla[144][34]="";	tabla[144][35]="";	tabla[144][36]="";	tabla[144][37]="";	tabla[144][38]="";	tabla[144][39]="";	tabla[144][40]="";	tabla[144][41]="";	tabla[144][42]="";	tabla[144][43]="";	tabla[144][44]="";	tabla[144][45]="";	tabla[144][46]="";	tabla[144][47]="";
		tabla[145][0]="s144";	tabla[145][1]="";	tabla[145][2]="";	tabla[145][3]="";	tabla[145][4]="";	tabla[145][5]="";	tabla[145][6]="";	tabla[145][7]="";	tabla[145][8]="";	tabla[145][9]="";	tabla[145][10]="";	tabla[145][11]="s154";	tabla[145][12]="";	tabla[145][13]="";	tabla[145][14]="";	tabla[145][15]="";	tabla[145][16]="";	tabla[145][17]="";	tabla[145][18]="";	tabla[145][19]="";	tabla[145][20]="";	tabla[145][21]="";	tabla[145][22]="";	tabla[145][23]="";	tabla[145][24]="";	tabla[145][25]="";	tabla[145][26]="";	tabla[145][27]="";	tabla[145][28]="";	tabla[145][29]="";	tabla[145][30]="";	tabla[145][31]="";	tabla[145][32]="";	tabla[145][33]="";	tabla[145][34]="";	tabla[145][35]="";	tabla[145][36]="";	tabla[145][37]="";	tabla[145][38]="";	tabla[145][39]="";	tabla[145][40]="";	tabla[145][41]="";	tabla[145][42]="";	tabla[145][43]="";	tabla[145][44]="";	tabla[145][45]="";	tabla[145][46]="";	tabla[145][47]="";
		tabla[146][0]="s145";	tabla[146][1]="";	tabla[146][2]="";	tabla[146][3]="";	tabla[146][4]="";	tabla[146][5]="";	tabla[146][6]="";	tabla[146][7]="";	tabla[146][8]="";	tabla[146][9]="";	tabla[146][10]="";	tabla[146][11]="s155";	tabla[146][12]="";	tabla[146][13]="";	tabla[146][14]="";	tabla[146][15]="";	tabla[146][16]="";	tabla[146][17]="";	tabla[146][18]="";	tabla[146][19]="";	tabla[146][20]="";	tabla[146][21]="";	tabla[146][22]="";	tabla[146][23]="";	tabla[146][24]="";	tabla[146][25]="";	tabla[146][26]="";	tabla[146][27]="";	tabla[146][28]="";	tabla[146][29]="";	tabla[146][30]="";	tabla[146][31]="";	tabla[146][32]="";	tabla[146][33]="";	tabla[146][34]="";	tabla[146][35]="";	tabla[146][36]="";	tabla[146][37]="";	tabla[146][38]="";	tabla[146][39]="";	tabla[146][40]="";	tabla[146][41]="";	tabla[146][42]="";	tabla[146][43]="";	tabla[146][44]="";	tabla[146][45]="";	tabla[146][46]="";	tabla[146][47]="";
		tabla[147][0]="s146";	tabla[147][1]="";	tabla[147][2]="";	tabla[147][3]="";	tabla[147][4]="";	tabla[147][5]="";	tabla[147][6]="";	tabla[147][7]="";	tabla[147][8]="";	tabla[147][9]="";	tabla[147][10]="";	tabla[147][11]="";	tabla[147][12]="";	tabla[147][13]="";	tabla[147][14]="";	tabla[147][15]="";	tabla[147][16]="";	tabla[147][17]="";	tabla[147][18]="";	tabla[147][19]="";	tabla[147][20]="";	tabla[147][21]="";	tabla[147][22]="";	tabla[147][23]="";	tabla[147][24]="";	tabla[147][25]="";	tabla[147][26]="";	tabla[147][27]="";	tabla[147][28]="";	tabla[147][29]="";	tabla[147][30]="";	tabla[147][31]="S>_S_endS_Caso_)_id_(_switch";	tabla[147][32]="";	tabla[147][33]="";	tabla[147][34]="";	tabla[147][35]="";	tabla[147][36]="";	tabla[147][37]="";	tabla[147][38]="";	tabla[147][39]="";	tabla[147][40]="";	tabla[147][41]="";	tabla[147][42]="";	tabla[147][43]="";	tabla[147][44]="";	tabla[147][45]="";	tabla[147][46]="";	tabla[147][47]="";
		tabla[148][0]="s147";	tabla[148][1]="D>@";	tabla[148][2]="";	tabla[148][3]="s4";	tabla[148][4]="s5";	tabla[148][5]="s6";	tabla[148][6]="";	tabla[148][7]="";	tabla[148][8]="D>@";	tabla[148][9]="";	tabla[148][10]="";	tabla[148][11]="";	tabla[148][12]="D>@";	tabla[148][13]="";	tabla[148][14]="D>@";	tabla[148][15]="D>@";	tabla[148][16]="";	tabla[148][17]="";	tabla[148][18]="";	tabla[148][19]="";	tabla[148][20]="";	tabla[148][21]="D>@";	tabla[148][22]="";	tabla[148][23]="";	tabla[148][24]="";	tabla[148][25]="";	tabla[148][26]="";	tabla[148][27]="";	tabla[148][28]="";	tabla[148][29]="";	tabla[148][30]="";	tabla[148][31]="";	tabla[148][32]="";	tabla[148][33]="s156";	tabla[148][34]="s157";	tabla[148][35]="s158";	tabla[148][36]="";	tabla[148][37]="";	tabla[148][38]="";	tabla[148][39]="";	tabla[148][40]="";	tabla[148][41]="";	tabla[148][42]="";	tabla[148][43]="";	tabla[148][44]="";	tabla[148][45]="";	tabla[148][46]="";	tabla[148][47]="";
		tabla[149][0]="s148";	tabla[149][1]="s8";	tabla[149][2]="";	tabla[149][3]="";	tabla[149][4]="";	tabla[149][5]="";	tabla[149][6]="";	tabla[149][7]="";	tabla[149][8]="s9";	tabla[149][9]="";	tabla[149][10]="";	tabla[149][11]="";	tabla[149][12]="s10";	tabla[149][13]="";	tabla[149][14]="s11";	tabla[149][15]="s12";	tabla[149][16]="";	tabla[149][17]="";	tabla[149][18]="";	tabla[149][19]="";	tabla[149][20]="";	tabla[149][21]="";	tabla[149][22]="";	tabla[149][23]="";	tabla[149][24]="";	tabla[149][25]="";	tabla[149][26]="";	tabla[149][27]="";	tabla[149][28]="";	tabla[149][29]="";	tabla[149][30]="";	tabla[149][31]="S>@";	tabla[149][32]="";	tabla[149][33]="";	tabla[149][34]="";	tabla[149][35]="";	tabla[149][36]="";	tabla[149][37]="s159";	tabla[149][38]="";	tabla[149][39]="";	tabla[149][40]="";	tabla[149][41]="";	tabla[149][42]="";	tabla[149][43]="";	tabla[149][44]="";	tabla[149][45]="";	tabla[149][46]="";	tabla[149][47]="";
		tabla[150][0]="s149";	tabla[150][1]="";	tabla[150][2]="";	tabla[150][3]="";	tabla[150][4]="";	tabla[150][5]="";	tabla[150][6]="";	tabla[150][7]="";	tabla[150][8]="";	tabla[150][9]="";	tabla[150][10]="";	tabla[150][11]="F>)_Exp_(";	tabla[150][12]="";	tabla[150][13]="";	tabla[150][14]="";	tabla[150][15]="";	tabla[150][16]="";	tabla[150][17]="";	tabla[150][18]="";	tabla[150][19]="";	tabla[150][20]="";	tabla[150][21]="";	tabla[150][22]="";	tabla[150][23]="";	tabla[150][24]="";	tabla[150][25]="";	tabla[150][26]="";	tabla[150][27]="";	tabla[150][28]="";	tabla[150][29]="";	tabla[150][30]="";	tabla[150][31]="";	tabla[150][32]="";	tabla[150][33]="";	tabla[150][34]="";	tabla[150][35]="";	tabla[150][36]="";	tabla[150][37]="";	tabla[150][38]="";	tabla[150][39]="";	tabla[150][40]="";	tabla[150][41]="";	tabla[150][42]="";	tabla[150][43]="";	tabla[150][44]="";	tabla[150][45]="";	tabla[150][46]="";	tabla[150][47]="";
		
	}
	
	public void tabla4() {
		tabla[151][0]="s150";	tabla[151][1]="s8";	tabla[151][2]="";	tabla[151][3]="";	tabla[151][4]="";	tabla[151][5]="";	tabla[151][6]="";	tabla[151][7]="";	tabla[151][8]="s9";	tabla[151][9]="";	tabla[151][10]="";	tabla[151][11]="";	tabla[151][12]="s10";	tabla[151][13]="";	tabla[151][14]="s11";	tabla[151][15]="s12";	tabla[151][16]="";	tabla[151][17]="";	tabla[151][18]="";	tabla[151][19]="";	tabla[151][20]="";	tabla[151][21]="";	tabla[151][22]="";	tabla[151][23]="";	tabla[151][24]="";	tabla[151][25]="";	tabla[151][26]="";	tabla[151][27]="";	tabla[151][28]="";	tabla[151][29]="";	tabla[151][30]="";	tabla[151][31]="S>@";	tabla[151][32]="";	tabla[151][33]="";	tabla[151][34]="";	tabla[151][35]="";	tabla[151][36]="";	tabla[151][37]="s160";	tabla[151][38]="";	tabla[151][39]="";	tabla[151][40]="";	tabla[151][41]="";	tabla[151][42]="";	tabla[151][43]="";	tabla[151][44]="";	tabla[151][45]="";	tabla[151][46]="";	tabla[151][47]="";
		tabla[152][0]="s151";	tabla[152][1]="";	tabla[152][2]="";	tabla[152][3]="";	tabla[152][4]="";	tabla[152][5]="";	tabla[152][6]="";	tabla[152][7]="";	tabla[152][8]="";	tabla[152][9]="";	tabla[152][10]="";	tabla[152][11]="";	tabla[152][12]="";	tabla[152][13]="";	tabla[152][14]="";	tabla[152][15]="";	tabla[152][16]="";	tabla[152][17]="";	tabla[152][18]="";	tabla[152][19]="";	tabla[152][20]="";	tabla[152][21]="";	tabla[152][22]="";	tabla[152][23]="";	tabla[152][24]="";	tabla[152][25]="";	tabla[152][26]="";	tabla[152][27]="";	tabla[152][28]="";	tabla[152][29]="";	tabla[152][30]="";	tabla[152][31]="S>S_;_)_Cond_(_while_P_do";	tabla[152][32]="";	tabla[152][33]="";	tabla[152][34]="";	tabla[152][35]="";	tabla[152][36]="";	tabla[152][37]="";	tabla[152][38]="";	tabla[152][39]="";	tabla[152][40]="";	tabla[152][41]="";	tabla[152][42]="";	tabla[152][43]="";	tabla[152][44]="";	tabla[152][45]="";	tabla[152][46]="";	tabla[152][47]="";
		tabla[153][0]="s152";	tabla[153][1]="";	tabla[153][2]="s161";	tabla[153][3]="";	tabla[153][4]="";	tabla[153][5]="";	tabla[153][6]="";	tabla[153][7]="";	tabla[153][8]="";	tabla[153][9]="";	tabla[153][10]="";	tabla[153][11]="";	tabla[153][12]="";	tabla[153][13]="";	tabla[153][14]="";	tabla[153][15]="";	tabla[153][16]="";	tabla[153][17]="";	tabla[153][18]="";	tabla[153][19]="";	tabla[153][20]="";	tabla[153][21]="";	tabla[153][22]="";	tabla[153][23]="";	tabla[153][24]="";	tabla[153][25]="";	tabla[153][26]="";	tabla[153][27]="";	tabla[153][28]="";	tabla[153][29]="";	tabla[153][30]="";	tabla[153][31]="";	tabla[153][32]="";	tabla[153][33]="";	tabla[153][34]="";	tabla[153][35]="";	tabla[153][36]="";	tabla[153][37]="";	tabla[153][38]="";	tabla[153][39]="";	tabla[153][40]="";	tabla[153][41]="";	tabla[153][42]="";	tabla[153][43]="";	tabla[153][44]="";	tabla[153][45]="";	tabla[153][46]="";	tabla[153][47]="";
		tabla[154][0]="s153";	tabla[154][1]="s33";	tabla[154][2]="";	tabla[154][3]="";	tabla[154][4]="";	tabla[154][5]="";	tabla[154][6]="";	tabla[154][7]="";	tabla[154][8]="s34";	tabla[154][9]="S>@";	tabla[154][10]="";	tabla[154][11]="";	tabla[154][12]="s35";	tabla[154][13]="";	tabla[154][14]="s36";	tabla[154][15]="s37";	tabla[154][16]="";	tabla[154][17]="";	tabla[154][18]="";	tabla[154][19]="";	tabla[154][20]="";	tabla[154][21]="";	tabla[154][22]="";	tabla[154][23]="";	tabla[154][24]="";	tabla[154][25]="";	tabla[154][26]="";	tabla[154][27]="";	tabla[154][28]="";	tabla[154][29]="";	tabla[154][30]="";	tabla[154][31]="";	tabla[154][32]="";	tabla[154][33]="";	tabla[154][34]="";	tabla[154][35]="";	tabla[154][36]="";	tabla[154][37]="s162";	tabla[154][38]="";	tabla[154][39]="";	tabla[154][40]="";	tabla[154][41]="";	tabla[154][42]="";	tabla[154][43]="";	tabla[154][44]="";	tabla[154][45]="";	tabla[154][46]="";	tabla[154][47]="";
		tabla[155][0]="s154";	tabla[155][1]="";	tabla[155][2]="s163";	tabla[155][3]="";	tabla[155][4]="";	tabla[155][5]="";	tabla[155][6]="";	tabla[155][7]="";	tabla[155][8]="";	tabla[155][9]="";	tabla[155][10]="";	tabla[155][11]="";	tabla[155][12]="";	tabla[155][13]="";	tabla[155][14]="";	tabla[155][15]="";	tabla[155][16]="";	tabla[155][17]="";	tabla[155][18]="";	tabla[155][19]="";	tabla[155][20]="";	tabla[155][21]="";	tabla[155][22]="";	tabla[155][23]="";	tabla[155][24]="";	tabla[155][25]="";	tabla[155][26]="";	tabla[155][27]="";	tabla[155][28]="";	tabla[155][29]="";	tabla[155][30]="";	tabla[155][31]="";	tabla[155][32]="";	tabla[155][33]="";	tabla[155][34]="";	tabla[155][35]="";	tabla[155][36]="";	tabla[155][37]="";	tabla[155][38]="";	tabla[155][39]="";	tabla[155][40]="";	tabla[155][41]="";	tabla[155][42]="";	tabla[155][43]="";	tabla[155][44]="";	tabla[155][45]="";	tabla[155][46]="";	tabla[155][47]="";
		tabla[156][0]="s155";	tabla[156][1]="";	tabla[156][2]="s164";	tabla[156][3]="";	tabla[156][4]="";	tabla[156][5]="";	tabla[156][6]="";	tabla[156][7]="";	tabla[156][8]="";	tabla[156][9]="";	tabla[156][10]="";	tabla[156][11]="";	tabla[156][12]="";	tabla[156][13]="";	tabla[156][14]="";	tabla[156][15]="";	tabla[156][16]="";	tabla[156][17]="";	tabla[156][18]="";	tabla[156][19]="";	tabla[156][20]="";	tabla[156][21]="";	tabla[156][22]="";	tabla[156][23]="";	tabla[156][24]="";	tabla[156][25]="";	tabla[156][26]="";	tabla[156][27]="";	tabla[156][28]="";	tabla[156][29]="";	tabla[156][30]="";	tabla[156][31]="";	tabla[156][32]="";	tabla[156][33]="";	tabla[156][34]="";	tabla[156][35]="";	tabla[156][36]="";	tabla[156][37]="";	tabla[156][38]="";	tabla[156][39]="";	tabla[156][40]="";	tabla[156][41]="";	tabla[156][42]="";	tabla[156][43]="";	tabla[156][44]="";	tabla[156][45]="";	tabla[156][46]="";	tabla[156][47]="";
		tabla[157][0]="s156";	tabla[157][1]="";	tabla[157][2]="";	tabla[157][3]="";	tabla[157][4]="";	tabla[157][5]="";	tabla[157][6]="";	tabla[157][7]="";	tabla[157][8]="";	tabla[157][9]="";	tabla[157][10]="";	tabla[157][11]="";	tabla[157][12]="";	tabla[157][13]="";	tabla[157][14]="";	tabla[157][15]="";	tabla[157][16]="";	tabla[157][17]="";	tabla[157][18]="";	tabla[157][19]="";	tabla[157][20]="";	tabla[157][21]="s165";	tabla[157][22]="";	tabla[157][23]="";	tabla[157][24]="";	tabla[157][25]="";	tabla[157][26]="";	tabla[157][27]="";	tabla[157][28]="";	tabla[157][29]="";	tabla[157][30]="";	tabla[157][31]="";	tabla[157][32]="";	tabla[157][33]="";	tabla[157][34]="";	tabla[157][35]="";	tabla[157][36]="";	tabla[157][37]="";	tabla[157][38]="";	tabla[157][39]="";	tabla[157][40]="";	tabla[157][41]="";	tabla[157][42]="";	tabla[157][43]="";	tabla[157][44]="";	tabla[157][45]="";	tabla[157][46]="";	tabla[157][47]="";
		tabla[158][0]="s157";	tabla[158][1]="s167";	tabla[158][2]="";	tabla[158][3]="";	tabla[158][4]="";	tabla[158][5]="";	tabla[158][6]="";	tabla[158][7]="";	tabla[158][8]="s168";	tabla[158][9]="";	tabla[158][10]="";	tabla[158][11]="";	tabla[158][12]="s169";	tabla[158][13]="";	tabla[158][14]="s170";	tabla[158][15]="s171";	tabla[158][16]="";	tabla[158][17]="";	tabla[158][18]="";	tabla[158][19]="";	tabla[158][20]="";	tabla[158][21]="S>@";	tabla[158][22]="";	tabla[158][23]="";	tabla[158][24]="";	tabla[158][25]="";	tabla[158][26]="";	tabla[158][27]="";	tabla[158][28]="";	tabla[158][29]="";	tabla[158][30]="";	tabla[158][31]="";	tabla[158][32]="";	tabla[158][33]="";	tabla[158][34]="";	tabla[158][35]="";	tabla[158][36]="";	tabla[158][37]="s166";	tabla[158][38]="";	tabla[158][39]="";	tabla[158][40]="";	tabla[158][41]="";	tabla[158][42]="";	tabla[158][43]="";	tabla[158][44]="";	tabla[158][45]="";	tabla[158][46]="";	tabla[158][47]="";
		tabla[159][0]="s158";	tabla[159][1]="s172";	tabla[159][2]="";	tabla[159][3]="";	tabla[159][4]="";	tabla[159][5]="";	tabla[159][6]="";	tabla[159][7]="";	tabla[159][8]="";	tabla[159][9]="";	tabla[159][10]="";	tabla[159][11]="";	tabla[159][12]="";	tabla[159][13]="";	tabla[159][14]="";	tabla[159][15]="";	tabla[159][16]="";	tabla[159][17]="";	tabla[159][18]="";	tabla[159][19]="";	tabla[159][20]="";	tabla[159][21]="";	tabla[159][22]="";	tabla[159][23]="";	tabla[159][24]="";	tabla[159][25]="";	tabla[159][26]="";	tabla[159][27]="";	tabla[159][28]="";	tabla[159][29]="";	tabla[159][30]="";	tabla[159][31]="";	tabla[159][32]="";	tabla[159][33]="";	tabla[159][34]="";	tabla[159][35]="";	tabla[159][36]="";	tabla[159][37]="";	tabla[159][38]="";	tabla[159][39]="";	tabla[159][40]="";	tabla[159][41]="";	tabla[159][42]="";	tabla[159][43]="";	tabla[159][44]="";	tabla[159][45]="";	tabla[159][46]="";	tabla[159][47]="";
		tabla[160][0]="s159";	tabla[160][1]="";	tabla[160][2]="";	tabla[160][3]="";	tabla[160][4]="";	tabla[160][5]="";	tabla[160][6]="";	tabla[160][7]="";	tabla[160][8]="";	tabla[160][9]="";	tabla[160][10]="";	tabla[160][11]="";	tabla[160][12]="";	tabla[160][13]="";	tabla[160][14]="";	tabla[160][15]="";	tabla[160][16]="";	tabla[160][17]="";	tabla[160][18]="";	tabla[160][19]="";	tabla[160][20]="";	tabla[160][21]="";	tabla[160][22]="";	tabla[160][23]="";	tabla[160][24]="";	tabla[160][25]="";	tabla[160][26]="";	tabla[160][27]="";	tabla[160][28]="";	tabla[160][29]="";	tabla[160][30]="";	tabla[160][31]="S>S_;_)_F_,_Dt_(_imprimir";	tabla[160][32]="";	tabla[160][33]="";	tabla[160][34]="";	tabla[160][35]="";	tabla[160][36]="";	tabla[160][37]="";	tabla[160][38]="";	tabla[160][39]="";	tabla[160][40]="";	tabla[160][41]="";	tabla[160][42]="";	tabla[160][43]="";	tabla[160][44]="";	tabla[160][45]="";	tabla[160][46]="";	tabla[160][47]="";
		tabla[161][0]="s160";	tabla[161][1]="";	tabla[161][2]="";	tabla[161][3]="";	tabla[161][4]="";	tabla[161][5]="";	tabla[161][6]="";	tabla[161][7]="";	tabla[161][8]="";	tabla[161][9]="";	tabla[161][10]="";	tabla[161][11]="";	tabla[161][12]="";	tabla[161][13]="";	tabla[161][14]="";	tabla[161][15]="";	tabla[161][16]="";	tabla[161][17]="";	tabla[161][18]="";	tabla[161][19]="";	tabla[161][20]="";	tabla[161][21]="";	tabla[161][22]="";	tabla[161][23]="";	tabla[161][24]="";	tabla[161][25]="";	tabla[161][26]="";	tabla[161][27]="";	tabla[161][28]="";	tabla[161][29]="";	tabla[161][30]="";	tabla[161][31]="S>S_;_)_F_,_Dt_(_leer";	tabla[161][32]="";	tabla[161][33]="";	tabla[161][34]="";	tabla[161][35]="";	tabla[161][36]="";	tabla[161][37]="";	tabla[161][38]="";	tabla[161][39]="";	tabla[161][40]="";	tabla[161][41]="";	tabla[161][42]="";	tabla[161][43]="";	tabla[161][44]="";	tabla[161][45]="";	tabla[161][46]="";	tabla[161][47]="";
		tabla[162][0]="s161";	tabla[162][1]="s33";	tabla[162][2]="";	tabla[162][3]="";	tabla[162][4]="";	tabla[162][5]="";	tabla[162][6]="";	tabla[162][7]="";	tabla[162][8]="s34";	tabla[162][9]="S>@";	tabla[162][10]="";	tabla[162][11]="";	tabla[162][12]="s35";	tabla[162][13]="";	tabla[162][14]="s36";	tabla[162][15]="s37";	tabla[162][16]="";	tabla[162][17]="";	tabla[162][18]="";	tabla[162][19]="";	tabla[162][20]="";	tabla[162][21]="";	tabla[162][22]="";	tabla[162][23]="";	tabla[162][24]="";	tabla[162][25]="";	tabla[162][26]="";	tabla[162][27]="";	tabla[162][28]="";	tabla[162][29]="";	tabla[162][30]="";	tabla[162][31]="";	tabla[162][32]="";	tabla[162][33]="";	tabla[162][34]="";	tabla[162][35]="";	tabla[162][36]="";	tabla[162][37]="s173";	tabla[162][38]="";	tabla[162][39]="";	tabla[162][40]="";	tabla[162][41]="";	tabla[162][42]="";	tabla[162][43]="";	tabla[162][44]="";	tabla[162][45]="";	tabla[162][46]="";	tabla[162][47]="";
		tabla[163][0]="s162";	tabla[163][1]="";	tabla[163][2]="";	tabla[163][3]="";	tabla[163][4]="";	tabla[163][5]="";	tabla[163][6]="";	tabla[163][7]="";	tabla[163][8]="";	tabla[163][9]="S>_S_endS_Caso_)_id_(_switch";	tabla[163][10]="";	tabla[163][11]="";	tabla[163][12]="";	tabla[163][13]="";	tabla[163][14]="";	tabla[163][15]="";	tabla[163][16]="";	tabla[163][17]="";	tabla[163][18]="";	tabla[163][19]="";	tabla[163][20]="";	tabla[163][21]="";	tabla[163][22]="";	tabla[163][23]="";	tabla[163][24]="";	tabla[163][25]="";	tabla[163][26]="";	tabla[163][27]="";	tabla[163][28]="";	tabla[163][29]="";	tabla[163][30]="";	tabla[163][31]="";	tabla[163][32]="";	tabla[163][33]="";	tabla[163][34]="";	tabla[163][35]="";	tabla[163][36]="";	tabla[163][37]="";	tabla[163][38]="";	tabla[163][39]="";	tabla[163][40]="";	tabla[163][41]="";	tabla[163][42]="";	tabla[163][43]="";	tabla[163][44]="";	tabla[163][45]="";	tabla[163][46]="";	tabla[163][47]="";
		tabla[164][0]="s163";	tabla[164][1]="s33";	tabla[164][2]="";	tabla[164][3]="";	tabla[164][4]="";	tabla[164][5]="";	tabla[164][6]="";	tabla[164][7]="";	tabla[164][8]="s34";	tabla[164][9]="S>@";	tabla[164][10]="";	tabla[164][11]="";	tabla[164][12]="s35";	tabla[164][13]="";	tabla[164][14]="s36";	tabla[164][15]="s37";	tabla[164][16]="";	tabla[164][17]="";	tabla[164][18]="";	tabla[164][19]="";	tabla[164][20]="";	tabla[164][21]="";	tabla[164][22]="";	tabla[164][23]="";	tabla[164][24]="";	tabla[164][25]="";	tabla[164][26]="";	tabla[164][27]="";	tabla[164][28]="";	tabla[164][29]="";	tabla[164][30]="";	tabla[164][31]="";	tabla[164][32]="";	tabla[164][33]="";	tabla[164][34]="";	tabla[164][35]="";	tabla[164][36]="";	tabla[164][37]="s174";	tabla[164][38]="";	tabla[164][39]="";	tabla[164][40]="";	tabla[164][41]="";	tabla[164][42]="";	tabla[164][43]="";	tabla[164][44]="";	tabla[164][45]="";	tabla[164][46]="";	tabla[164][47]="";
		tabla[165][0]="s164";	tabla[165][1]="s33";	tabla[165][2]="";	tabla[165][3]="";	tabla[165][4]="";	tabla[165][5]="";	tabla[165][6]="";	tabla[165][7]="";	tabla[165][8]="s34";	tabla[165][9]="S>@";	tabla[165][10]="";	tabla[165][11]="";	tabla[165][12]="s35";	tabla[165][13]="";	tabla[165][14]="s36";	tabla[165][15]="s37";	tabla[165][16]="";	tabla[165][17]="";	tabla[165][18]="";	tabla[165][19]="";	tabla[165][20]="";	tabla[165][21]="";	tabla[165][22]="";	tabla[165][23]="";	tabla[165][24]="";	tabla[165][25]="";	tabla[165][26]="";	tabla[165][27]="";	tabla[165][28]="";	tabla[165][29]="";	tabla[165][30]="";	tabla[165][31]="";	tabla[165][32]="";	tabla[165][33]="";	tabla[165][34]="";	tabla[165][35]="";	tabla[165][36]="";	tabla[165][37]="s175";	tabla[165][38]="";	tabla[165][39]="";	tabla[165][40]="";	tabla[165][41]="";	tabla[165][42]="";	tabla[165][43]="";	tabla[165][44]="";	tabla[165][45]="";	tabla[165][46]="";	tabla[165][47]="";
		tabla[166][0]="s165";	tabla[166][1]="";	tabla[166][2]="s176";	tabla[166][3]="";	tabla[166][4]="";	tabla[166][5]="";	tabla[166][6]="";	tabla[166][7]="";	tabla[166][8]="";	tabla[166][9]="";	tabla[166][10]="";	tabla[166][11]="";	tabla[166][12]="";	tabla[166][13]="";	tabla[166][14]="";	tabla[166][15]="";	tabla[166][16]="";	tabla[166][17]="";	tabla[166][18]="";	tabla[166][19]="";	tabla[166][20]="";	tabla[166][21]="";	tabla[166][22]="";	tabla[166][23]="";	tabla[166][24]="";	tabla[166][25]="";	tabla[166][26]="";	tabla[166][27]="";	tabla[166][28]="";	tabla[166][29]="";	tabla[166][30]="";	tabla[166][31]="";	tabla[166][32]="";	tabla[166][33]="";	tabla[166][34]="";	tabla[166][35]="";	tabla[166][36]="";	tabla[166][37]="";	tabla[166][38]="";	tabla[166][39]="";	tabla[166][40]="";	tabla[166][41]="";	tabla[166][42]="";	tabla[166][43]="";	tabla[166][44]="";	tabla[166][45]="";	tabla[166][46]="";	tabla[166][47]="";
		tabla[167][0]="s166";	tabla[167][1]="";	tabla[167][2]="";	tabla[167][3]="";	tabla[167][4]="";	tabla[167][5]="";	tabla[167][6]="";	tabla[167][7]="";	tabla[167][8]="";	tabla[167][9]="";	tabla[167][10]="";	tabla[167][11]="";	tabla[167][12]="";	tabla[167][13]="";	tabla[167][14]="";	tabla[167][15]="";	tabla[167][16]="";	tabla[167][17]="";	tabla[167][18]="";	tabla[167][19]="";	tabla[167][20]="";	tabla[167][21]="P>S_D";	tabla[167][22]="";	tabla[167][23]="";	tabla[167][24]="";	tabla[167][25]="";	tabla[167][26]="";	tabla[167][27]="";	tabla[167][28]="";	tabla[167][29]="";	tabla[167][30]="";	tabla[167][31]="";	tabla[167][32]="";	tabla[167][33]="";	tabla[167][34]="";	tabla[167][35]="";	tabla[167][36]="";	tabla[167][37]="";	tabla[167][38]="";	tabla[167][39]="";	tabla[167][40]="";	tabla[167][41]="";	tabla[167][42]="";	tabla[167][43]="";	tabla[167][44]="";	tabla[167][45]="";	tabla[167][46]="";	tabla[167][47]="";
		tabla[168][0]="s167";	tabla[168][1]="";	tabla[168][2]="";	tabla[168][3]="";	tabla[168][4]="";	tabla[168][5]="";	tabla[168][6]="";	tabla[168][7]="s177";	tabla[168][8]="";	tabla[168][9]="";	tabla[168][10]="";	tabla[168][11]="";	tabla[168][12]="";	tabla[168][13]="";	tabla[168][14]="";	tabla[168][15]="";	tabla[168][16]="";	tabla[168][17]="";	tabla[168][18]="";	tabla[168][19]="";	tabla[168][20]="";	tabla[168][21]="";	tabla[168][22]="";	tabla[168][23]="";	tabla[168][24]="";	tabla[168][25]="";	tabla[168][26]="";	tabla[168][27]="";	tabla[168][28]="";	tabla[168][29]="";	tabla[168][30]="";	tabla[168][31]="";	tabla[168][32]="";	tabla[168][33]="";	tabla[168][34]="";	tabla[168][35]="";	tabla[168][36]="";	tabla[168][37]="";	tabla[168][38]="";	tabla[168][39]="";	tabla[168][40]="";	tabla[168][41]="";	tabla[168][42]="";	tabla[168][43]="";	tabla[168][44]="";	tabla[168][45]="";	tabla[168][46]="";	tabla[168][47]="";
		tabla[169][0]="s168";	tabla[169][1]="D>@";	tabla[169][2]="";	tabla[169][3]="s4";	tabla[169][4]="s5";	tabla[169][5]="s6";	tabla[169][6]="";	tabla[169][7]="";	tabla[169][8]="D>@";	tabla[169][9]="D>@";	tabla[169][10]="";	tabla[169][11]="";	tabla[169][12]="D>@";	tabla[169][13]="";	tabla[169][14]="D>@";	tabla[169][15]="D>@";	tabla[169][16]="";	tabla[169][17]="";	tabla[169][18]="";	tabla[169][19]="";	tabla[169][20]="";	tabla[169][21]="";	tabla[169][22]="";	tabla[169][23]="";	tabla[169][24]="";	tabla[169][25]="";	tabla[169][26]="";	tabla[169][27]="";	tabla[169][28]="";	tabla[169][29]="";	tabla[169][30]="";	tabla[169][31]="";	tabla[169][32]="";	tabla[169][33]="s178";	tabla[169][34]="s16";	tabla[169][35]="s17";	tabla[169][36]="";	tabla[169][37]="";	tabla[169][38]="";	tabla[169][39]="";	tabla[169][40]="";	tabla[169][41]="";	tabla[169][42]="";	tabla[169][43]="";	tabla[169][44]="";	tabla[169][45]="";	tabla[169][46]="";	tabla[169][47]="";
		tabla[170][0]="s169";	tabla[170][1]="";	tabla[170][2]="";	tabla[170][3]="";	tabla[170][4]="";	tabla[170][5]="";	tabla[170][6]="";	tabla[170][7]="";	tabla[170][8]="";	tabla[170][9]="";	tabla[170][10]="s179";	tabla[170][11]="";	tabla[170][12]="";	tabla[170][13]="";	tabla[170][14]="";	tabla[170][15]="";	tabla[170][16]="";	tabla[170][17]="";	tabla[170][18]="";	tabla[170][19]="";	tabla[170][20]="";	tabla[170][21]="";	tabla[170][22]="";	tabla[170][23]="";	tabla[170][24]="";	tabla[170][25]="";	tabla[170][26]="";	tabla[170][27]="";	tabla[170][28]="";	tabla[170][29]="";	tabla[170][30]="";	tabla[170][31]="";	tabla[170][32]="";	tabla[170][33]="";	tabla[170][34]="";	tabla[170][35]="";	tabla[170][36]="";	tabla[170][37]="";	tabla[170][38]="";	tabla[170][39]="";	tabla[170][40]="";	tabla[170][41]="";	tabla[170][42]="";	tabla[170][43]="";	tabla[170][44]="";	tabla[170][45]="";	tabla[170][46]="";	tabla[170][47]="";
		tabla[171][0]="s170";	tabla[171][1]="";	tabla[171][2]="";	tabla[171][3]="";	tabla[171][4]="";	tabla[171][5]="";	tabla[171][6]="";	tabla[171][7]="";	tabla[171][8]="";	tabla[171][9]="";	tabla[171][10]="s180";	tabla[171][11]="";	tabla[171][12]="";	tabla[171][13]="";	tabla[171][14]="";	tabla[171][15]="";	tabla[171][16]="";	tabla[171][17]="";	tabla[171][18]="";	tabla[171][19]="";	tabla[171][20]="";	tabla[171][21]="";	tabla[171][22]="";	tabla[171][23]="";	tabla[171][24]="";	tabla[171][25]="";	tabla[171][26]="";	tabla[171][27]="";	tabla[171][28]="";	tabla[171][29]="";	tabla[171][30]="";	tabla[171][31]="";	tabla[171][32]="";	tabla[171][33]="";	tabla[171][34]="";	tabla[171][35]="";	tabla[171][36]="";	tabla[171][37]="";	tabla[171][38]="";	tabla[171][39]="";	tabla[171][40]="";	tabla[171][41]="";	tabla[171][42]="";	tabla[171][43]="";	tabla[171][44]="";	tabla[171][45]="";	tabla[171][46]="";	tabla[171][47]="";
		tabla[172][0]="s171";	tabla[172][1]="";	tabla[172][2]="";	tabla[172][3]="";	tabla[172][4]="";	tabla[172][5]="";	tabla[172][6]="";	tabla[172][7]="";	tabla[172][8]="";	tabla[172][9]="";	tabla[172][10]="s181";	tabla[172][11]="";	tabla[172][12]="";	tabla[172][13]="";	tabla[172][14]="";	tabla[172][15]="";	tabla[172][16]="";	tabla[172][17]="";	tabla[172][18]="";	tabla[172][19]="";	tabla[172][20]="";	tabla[172][21]="";	tabla[172][22]="";	tabla[172][23]="";	tabla[172][24]="";	tabla[172][25]="";	tabla[172][26]="";	tabla[172][27]="";	tabla[172][28]="";	tabla[172][29]="";	tabla[172][30]="";	tabla[172][31]="";	tabla[172][32]="";	tabla[172][33]="";	tabla[172][34]="";	tabla[172][35]="";	tabla[172][36]="";	tabla[172][37]="";	tabla[172][38]="";	tabla[172][39]="";	tabla[172][40]="";	tabla[172][41]="";	tabla[172][42]="";	tabla[172][43]="";	tabla[172][44]="";	tabla[172][45]="";	tabla[172][46]="";	tabla[172][47]="";
		tabla[173][0]="s172";	tabla[173][1]="";	tabla[173][2]="Sd>@";	tabla[173][3]="";	tabla[173][4]="";	tabla[173][5]="";	tabla[173][6]="s22";	tabla[173][7]="";	tabla[173][8]="";	tabla[173][9]="";	tabla[173][10]="";	tabla[173][11]="";	tabla[173][12]="";	tabla[173][13]="";	tabla[173][14]="";	tabla[173][15]="";	tabla[173][16]="";	tabla[173][17]="";	tabla[173][18]="";	tabla[173][19]="";	tabla[173][20]="";	tabla[173][21]="";	tabla[173][22]="";	tabla[173][23]="";	tabla[173][24]="";	tabla[173][25]="";	tabla[173][26]="";	tabla[173][27]="";	tabla[173][28]="";	tabla[173][29]="";	tabla[173][30]="";	tabla[173][31]="";	tabla[173][32]="";	tabla[173][33]="";	tabla[173][34]="";	tabla[173][35]="";	tabla[173][36]="s182";	tabla[173][37]="";	tabla[173][38]="";	tabla[173][39]="";	tabla[173][40]="";	tabla[173][41]="";	tabla[173][42]="";	tabla[173][43]="";	tabla[173][44]="";	tabla[173][45]="";	tabla[173][46]="";	tabla[173][47]="";
		tabla[174][0]="s173";	tabla[174][1]="";	tabla[174][2]="";	tabla[174][3]="";	tabla[174][4]="";	tabla[174][5]="";	tabla[174][6]="";	tabla[174][7]="";	tabla[174][8]="";	tabla[174][9]="S>S_;_)_Cond_(_while_P_do";	tabla[174][10]="";	tabla[174][11]="";	tabla[174][12]="";	tabla[174][13]="";	tabla[174][14]="";	tabla[174][15]="";	tabla[174][16]="";	tabla[174][17]="";	tabla[174][18]="";	tabla[174][19]="";	tabla[174][20]="";	tabla[174][21]="";	tabla[174][22]="";	tabla[174][23]="";	tabla[174][24]="";	tabla[174][25]="";	tabla[174][26]="";	tabla[174][27]="";	tabla[174][28]="";	tabla[174][29]="";	tabla[174][30]="";	tabla[174][31]="";	tabla[174][32]="";	tabla[174][33]="";	tabla[174][34]="";	tabla[174][35]="";	tabla[174][36]="";	tabla[174][37]="";	tabla[174][38]="";	tabla[174][39]="";	tabla[174][40]="";	tabla[174][41]="";	tabla[174][42]="";	tabla[174][43]="";	tabla[174][44]="";	tabla[174][45]="";	tabla[174][46]="";	tabla[174][47]="";
		tabla[175][0]="s174";	tabla[175][1]="";	tabla[175][2]="";	tabla[175][3]="";	tabla[175][4]="";	tabla[175][5]="";	tabla[175][6]="";	tabla[175][7]="";	tabla[175][8]="";	tabla[175][9]="S>S_;_)_F_,_Dt_(_imprimir";	tabla[175][10]="";	tabla[175][11]="";	tabla[175][12]="";	tabla[175][13]="";	tabla[175][14]="";	tabla[175][15]="";	tabla[175][16]="";	tabla[175][17]="";	tabla[175][18]="";	tabla[175][19]="";	tabla[175][20]="";	tabla[175][21]="";	tabla[175][22]="";	tabla[175][23]="";	tabla[175][24]="";	tabla[175][25]="";	tabla[175][26]="";	tabla[175][27]="";	tabla[175][28]="";	tabla[175][29]="";	tabla[175][30]="";	tabla[175][31]="";	tabla[175][32]="";	tabla[175][33]="";	tabla[175][34]="";	tabla[175][35]="";	tabla[175][36]="";	tabla[175][37]="";	tabla[175][38]="";	tabla[175][39]="";	tabla[175][40]="";	tabla[175][41]="";	tabla[175][42]="";	tabla[175][43]="";	tabla[175][44]="";	tabla[175][45]="";	tabla[175][46]="";	tabla[175][47]="";
		tabla[176][0]="s175";	tabla[176][1]="";	tabla[176][2]="";	tabla[176][3]="";	tabla[176][4]="";	tabla[176][5]="";	tabla[176][6]="";	tabla[176][7]="";	tabla[176][8]="";	tabla[176][9]="S>S_;_)_F_,_Dt_(_leer";	tabla[176][10]="";	tabla[176][11]="";	tabla[176][12]="";	tabla[176][13]="";	tabla[176][14]="";	tabla[176][15]="";	tabla[176][16]="";	tabla[176][17]="";	tabla[176][18]="";	tabla[176][19]="";	tabla[176][20]="";	tabla[176][21]="";	tabla[176][22]="";	tabla[176][23]="";	tabla[176][24]="";	tabla[176][25]="";	tabla[176][26]="";	tabla[176][27]="";	tabla[176][28]="";	tabla[176][29]="";	tabla[176][30]="";	tabla[176][31]="";	tabla[176][32]="";	tabla[176][33]="";	tabla[176][34]="";	tabla[176][35]="";	tabla[176][36]="";	tabla[176][37]="";	tabla[176][38]="";	tabla[176][39]="";	tabla[176][40]="";	tabla[176][41]="";	tabla[176][42]="";	tabla[176][43]="";	tabla[176][44]="";	tabla[176][45]="";	tabla[176][46]="";	tabla[176][47]="";
		tabla[177][0]="s176";	tabla[177][1]="";	tabla[177][2]="";	tabla[177][3]="";	tabla[177][4]="";	tabla[177][5]="";	tabla[177][6]="";	tabla[177][7]="";	tabla[177][8]="";	tabla[177][9]="";	tabla[177][10]="";	tabla[177][11]="";	tabla[177][12]="";	tabla[177][13]="CasoS>@";	tabla[177][14]="";	tabla[177][15]="";	tabla[177][16]="";	tabla[177][17]="";	tabla[177][18]="s100";	tabla[177][19]="";	tabla[177][20]="";	tabla[177][21]="";	tabla[177][22]="";	tabla[177][23]="";	tabla[177][24]="";	tabla[177][25]="";	tabla[177][26]="";	tabla[177][27]="";	tabla[177][28]="";	tabla[177][29]="";	tabla[177][30]="";	tabla[177][31]="";	tabla[177][32]="";	tabla[177][33]="";	tabla[177][34]="";	tabla[177][35]="";	tabla[177][36]="";	tabla[177][37]="";	tabla[177][38]="";	tabla[177][39]="s184";	tabla[177][40]="s183";	tabla[177][41]="";	tabla[177][42]="";	tabla[177][43]="";	tabla[177][44]="";	tabla[177][45]="";	tabla[177][46]="";	tabla[177][47]="";
		tabla[178][0]="s177";	tabla[178][1]="s26";	tabla[178][2]="";	tabla[178][3]="";	tabla[178][4]="";	tabla[178][5]="";	tabla[178][6]="";	tabla[178][7]="";	tabla[178][8]="";	tabla[178][9]="";	tabla[178][10]="s27";	tabla[178][11]="";	tabla[178][12]="";	tabla[178][13]="";	tabla[178][14]="";	tabla[178][15]="";	tabla[178][16]="";	tabla[178][17]="";	tabla[178][18]="";	tabla[178][19]="s30";	tabla[178][20]="";	tabla[178][21]="";	tabla[178][22]="";	tabla[178][23]="";	tabla[178][24]="";	tabla[178][25]="";	tabla[178][26]="";	tabla[178][27]="";	tabla[178][28]="";	tabla[178][29]="s28";	tabla[178][30]="s29";	tabla[178][31]="";	tabla[178][32]="";	tabla[178][33]="";	tabla[178][34]="";	tabla[178][35]="";	tabla[178][36]="";	tabla[178][37]="";	tabla[178][38]="";	tabla[178][39]="";	tabla[178][40]="";	tabla[178][41]="";	tabla[178][42]="";	tabla[178][43]="s185";	tabla[178][44]="";	tabla[178][45]="s24";	tabla[178][46]="";	tabla[178][47]="s25";
		tabla[179][0]="s178";	tabla[179][1]="";	tabla[179][2]="";	tabla[179][3]="";	tabla[179][4]="";	tabla[179][5]="";	tabla[179][6]="";	tabla[179][7]="";	tabla[179][8]="";	tabla[179][9]="s186";	tabla[179][10]="";	tabla[179][11]="";	tabla[179][12]="";	tabla[179][13]="";	tabla[179][14]="";	tabla[179][15]="";	tabla[179][16]="";	tabla[179][17]="";	tabla[179][18]="";	tabla[179][19]="";	tabla[179][20]="";	tabla[179][21]="";	tabla[179][22]="";	tabla[179][23]="";	tabla[179][24]="";	tabla[179][25]="";	tabla[179][26]="";	tabla[179][27]="";	tabla[179][28]="";	tabla[179][29]="";	tabla[179][30]="";	tabla[179][31]="";	tabla[179][32]="";	tabla[179][33]="";	tabla[179][34]="";	tabla[179][35]="";	tabla[179][36]="";	tabla[179][37]="";	tabla[179][38]="";	tabla[179][39]="";	tabla[179][40]="";	tabla[179][41]="";	tabla[179][42]="";	tabla[179][43]="";	tabla[179][44]="";	tabla[179][45]="";	tabla[179][46]="";	tabla[179][47]="";
		tabla[180][0]="s179";	tabla[180][1]="s187";	tabla[180][2]="";	tabla[180][3]="";	tabla[180][4]="";	tabla[180][5]="";	tabla[180][6]="";	tabla[180][7]="";	tabla[180][8]="";	tabla[180][9]="";	tabla[180][10]="";	tabla[180][11]="";	tabla[180][12]="";	tabla[180][13]="";	tabla[180][14]="";	tabla[180][15]="";	tabla[180][16]="";	tabla[180][17]="";	tabla[180][18]="";	tabla[180][19]="";	tabla[180][20]="";	tabla[180][21]="";	tabla[180][22]="";	tabla[180][23]="";	tabla[180][24]="";	tabla[180][25]="";	tabla[180][26]="";	tabla[180][27]="";	tabla[180][28]="";	tabla[180][29]="";	tabla[180][30]="";	tabla[180][31]="";	tabla[180][32]="";	tabla[180][33]="";	tabla[180][34]="";	tabla[180][35]="";	tabla[180][36]="";	tabla[180][37]="";	tabla[180][38]="";	tabla[180][39]="";	tabla[180][40]="";	tabla[180][41]="";	tabla[180][42]="";	tabla[180][43]="";	tabla[180][44]="";	tabla[180][45]="";	tabla[180][46]="";	tabla[180][47]="";
		tabla[181][0]="s180";	tabla[181][1]="";	tabla[181][2]="";	tabla[181][3]="";	tabla[181][4]="";	tabla[181][5]="";	tabla[181][6]="";	tabla[181][7]="";	tabla[181][8]="";	tabla[181][9]="";	tabla[181][10]="";	tabla[181][11]="";	tabla[181][12]="";	tabla[181][13]="";	tabla[181][14]="";	tabla[181][15]="";	tabla[181][16]="s41";	tabla[181][17]="s42";	tabla[181][18]="";	tabla[181][19]="";	tabla[181][20]="";	tabla[181][21]="";	tabla[181][22]="";	tabla[181][23]="";	tabla[181][24]="";	tabla[181][25]="";	tabla[181][26]="";	tabla[181][27]="";	tabla[181][28]="";	tabla[181][29]="";	tabla[181][30]="";	tabla[181][31]="";	tabla[181][32]="";	tabla[181][33]="";	tabla[181][34]="";	tabla[181][35]="";	tabla[181][36]="";	tabla[181][37]="";	tabla[181][38]="s188";	tabla[181][39]="";	tabla[181][40]="";	tabla[181][41]="";	tabla[181][42]="";	tabla[181][43]="";	tabla[181][44]="";	tabla[181][45]="";	tabla[181][46]="";	tabla[181][47]="";
		tabla[182][0]="s181";	tabla[182][1]="";	tabla[182][2]="";	tabla[182][3]="";	tabla[182][4]="";	tabla[182][5]="";	tabla[182][6]="";	tabla[182][7]="";	tabla[182][8]="";	tabla[182][9]="";	tabla[182][10]="";	tabla[182][11]="";	tabla[182][12]="";	tabla[182][13]="";	tabla[182][14]="";	tabla[182][15]="";	tabla[182][16]="s41";	tabla[182][17]="s42";	tabla[182][18]="";	tabla[182][19]="";	tabla[182][20]="";	tabla[182][21]="";	tabla[182][22]="";	tabla[182][23]="";	tabla[182][24]="";	tabla[182][25]="";	tabla[182][26]="";	tabla[182][27]="";	tabla[182][28]="";	tabla[182][29]="";	tabla[182][30]="";	tabla[182][31]="";	tabla[182][32]="";	tabla[182][33]="";	tabla[182][34]="";	tabla[182][35]="";	tabla[182][36]="";	tabla[182][37]="";	tabla[182][38]="s189";	tabla[182][39]="";	tabla[182][40]="";	tabla[182][41]="";	tabla[182][42]="";	tabla[182][43]="";	tabla[182][44]="";	tabla[182][45]="";	tabla[182][46]="";	tabla[182][47]="";
		tabla[183][0]="s182";	tabla[183][1]="";	tabla[183][2]="s190";	tabla[183][3]="";	tabla[183][4]="";	tabla[183][5]="";	tabla[183][6]="";	tabla[183][7]="";	tabla[183][8]="";	tabla[183][9]="";	tabla[183][10]="";	tabla[183][11]="";	tabla[183][12]="";	tabla[183][13]="";	tabla[183][14]="";	tabla[183][15]="";	tabla[183][16]="";	tabla[183][17]="";	tabla[183][18]="";	tabla[183][19]="";	tabla[183][20]="";	tabla[183][21]="";	tabla[183][22]="";	tabla[183][23]="";	tabla[183][24]="";	tabla[183][25]="";	tabla[183][26]="";	tabla[183][27]="";	tabla[183][28]="";	tabla[183][29]="";	tabla[183][30]="";	tabla[183][31]="";	tabla[183][32]="";	tabla[183][33]="";	tabla[183][34]="";	tabla[183][35]="";	tabla[183][36]="";	tabla[183][37]="";	tabla[183][38]="";	tabla[183][39]="";	tabla[183][40]="";	tabla[183][41]="";	tabla[183][42]="";	tabla[183][43]="";	tabla[183][44]="";	tabla[183][45]="";	tabla[183][46]="";	tabla[183][47]="";
		tabla[184][0]="s183";	tabla[184][1]="";	tabla[184][2]="";	tabla[184][3]="";	tabla[184][4]="";	tabla[184][5]="";	tabla[184][6]="";	tabla[184][7]="";	tabla[184][8]="";	tabla[184][9]="";	tabla[184][10]="";	tabla[184][11]="";	tabla[184][12]="";	tabla[184][13]="Caso>CasoS_;_break_P_:_litcar_case";	tabla[184][14]="";	tabla[184][15]="";	tabla[184][16]="";	tabla[184][17]="";	tabla[184][18]="";	tabla[184][19]="";	tabla[184][20]="";	tabla[184][21]="";	tabla[184][22]="";	tabla[184][23]="";	tabla[184][24]="";	tabla[184][25]="";	tabla[184][26]="";	tabla[184][27]="";	tabla[184][28]="";	tabla[184][29]="";	tabla[184][30]="";	tabla[184][31]="";	tabla[184][32]="";	tabla[184][33]="";	tabla[184][34]="";	tabla[184][35]="";	tabla[184][36]="";	tabla[184][37]="";	tabla[184][38]="";	tabla[184][39]="";	tabla[184][40]="";	tabla[184][41]="";	tabla[184][42]="";	tabla[184][43]="";	tabla[184][44]="";	tabla[184][45]="";	tabla[184][46]="";	tabla[184][47]="";
		tabla[185][0]="s184";	tabla[185][1]="";	tabla[185][2]="";	tabla[185][3]="";	tabla[185][4]="";	tabla[185][5]="";	tabla[185][6]="";	tabla[185][7]="";	tabla[185][8]="";	tabla[185][9]="";	tabla[185][10]="";	tabla[185][11]="";	tabla[185][12]="";	tabla[185][13]="CasoS>Caso";	tabla[185][14]="";	tabla[185][15]="";	tabla[185][16]="";	tabla[185][17]="";	tabla[185][18]="";	tabla[185][19]="";	tabla[185][20]="";	tabla[185][21]="";	tabla[185][22]="";	tabla[185][23]="";	tabla[185][24]="";	tabla[185][25]="";	tabla[185][26]="";	tabla[185][27]="";	tabla[185][28]="";	tabla[185][29]="";	tabla[185][30]="";	tabla[185][31]="";	tabla[185][32]="";	tabla[185][33]="";	tabla[185][34]="";	tabla[185][35]="";	tabla[185][36]="";	tabla[185][37]="";	tabla[185][38]="";	tabla[185][39]="";	tabla[185][40]="";	tabla[185][41]="";	tabla[185][42]="";	tabla[185][43]="";	tabla[185][44]="";	tabla[185][45]="";	tabla[185][46]="";	tabla[185][47]="";
		tabla[186][0]="s185";	tabla[186][1]="";	tabla[186][2]="s191";	tabla[186][3]="";	tabla[186][4]="";	tabla[186][5]="";	tabla[186][6]="";	tabla[186][7]="";	tabla[186][8]="";	tabla[186][9]="";	tabla[186][10]="";	tabla[186][11]="";	tabla[186][12]="";	tabla[186][13]="";	tabla[186][14]="";	tabla[186][15]="";	tabla[186][16]="";	tabla[186][17]="";	tabla[186][18]="";	tabla[186][19]="";	tabla[186][20]="";	tabla[186][21]="";	tabla[186][22]="";	tabla[186][23]="";	tabla[186][24]="";	tabla[186][25]="";	tabla[186][26]="";	tabla[186][27]="";	tabla[186][28]="";	tabla[186][29]="";	tabla[186][30]="";	tabla[186][31]="";	tabla[186][32]="";	tabla[186][33]="";	tabla[186][34]="";	tabla[186][35]="";	tabla[186][36]="";	tabla[186][37]="";	tabla[186][38]="";	tabla[186][39]="";	tabla[186][40]="";	tabla[186][41]="";	tabla[186][42]="";	tabla[186][43]="";	tabla[186][44]="";	tabla[186][45]="";	tabla[186][46]="";	tabla[186][47]="";
		tabla[187][0]="s186";	tabla[187][1]="";	tabla[187][2]="";	tabla[187][3]="";	tabla[187][4]="";	tabla[187][5]="";	tabla[187][6]="";	tabla[187][7]="";	tabla[187][8]="";	tabla[187][9]="";	tabla[187][10]="s192";	tabla[187][11]="";	tabla[187][12]="";	tabla[187][13]="";	tabla[187][14]="";	tabla[187][15]="";	tabla[187][16]="";	tabla[187][17]="";	tabla[187][18]="";	tabla[187][19]="";	tabla[187][20]="";	tabla[187][21]="";	tabla[187][22]="";	tabla[187][23]="";	tabla[187][24]="";	tabla[187][25]="";	tabla[187][26]="";	tabla[187][27]="";	tabla[187][28]="";	tabla[187][29]="";	tabla[187][30]="";	tabla[187][31]="";	tabla[187][32]="";	tabla[187][33]="";	tabla[187][34]="";	tabla[187][35]="";	tabla[187][36]="";	tabla[187][37]="";	tabla[187][38]="";	tabla[187][39]="";	tabla[187][40]="";	tabla[187][41]="";	tabla[187][42]="";	tabla[187][43]="";	tabla[187][44]="";	tabla[187][45]="";	tabla[187][46]="";	tabla[187][47]="";
		tabla[188][0]="s187";	tabla[188][1]="";	tabla[188][2]="";	tabla[188][3]="";	tabla[188][4]="";	tabla[188][5]="";	tabla[188][6]="";	tabla[188][7]="";	tabla[188][8]="";	tabla[188][9]="";	tabla[188][10]="";	tabla[188][11]="s193";	tabla[188][12]="";	tabla[188][13]="";	tabla[188][14]="";	tabla[188][15]="";	tabla[188][16]="";	tabla[188][17]="";	tabla[188][18]="";	tabla[188][19]="";	tabla[188][20]="";	tabla[188][21]="";	tabla[188][22]="";	tabla[188][23]="";	tabla[188][24]="";	tabla[188][25]="";	tabla[188][26]="";	tabla[188][27]="";	tabla[188][28]="";	tabla[188][29]="";	tabla[188][30]="";	tabla[188][31]="";	tabla[188][32]="";	tabla[188][33]="";	tabla[188][34]="";	tabla[188][35]="";	tabla[188][36]="";	tabla[188][37]="";	tabla[188][38]="";	tabla[188][39]="";	tabla[188][40]="";	tabla[188][41]="";	tabla[188][42]="";	tabla[188][43]="";	tabla[188][44]="";	tabla[188][45]="";	tabla[188][46]="";	tabla[188][47]="";
		tabla[189][0]="s188";	tabla[189][1]="";	tabla[189][2]="";	tabla[189][3]="";	tabla[189][4]="";	tabla[189][5]="";	tabla[189][6]="s194";	tabla[189][7]="";	tabla[189][8]="";	tabla[189][9]="";	tabla[189][10]="";	tabla[189][11]="";	tabla[189][12]="";	tabla[189][13]="";	tabla[189][14]="";	tabla[189][15]="";	tabla[189][16]="";	tabla[189][17]="";	tabla[189][18]="";	tabla[189][19]="";	tabla[189][20]="";	tabla[189][21]="";	tabla[189][22]="";	tabla[189][23]="";	tabla[189][24]="";	tabla[189][25]="";	tabla[189][26]="";	tabla[189][27]="";	tabla[189][28]="";	tabla[189][29]="";	tabla[189][30]="";	tabla[189][31]="";	tabla[189][32]="";	tabla[189][33]="";	tabla[189][34]="";	tabla[189][35]="";	tabla[189][36]="";	tabla[189][37]="";	tabla[189][38]="";	tabla[189][39]="";	tabla[189][40]="";	tabla[189][41]="";	tabla[189][42]="";	tabla[189][43]="";	tabla[189][44]="";	tabla[189][45]="";	tabla[189][46]="";	tabla[189][47]="";
		tabla[190][0]="s189";	tabla[190][1]="";	tabla[190][2]="";	tabla[190][3]="";	tabla[190][4]="";	tabla[190][5]="";	tabla[190][6]="s195";	tabla[190][7]="";	tabla[190][8]="";	tabla[190][9]="";	tabla[190][10]="";	tabla[190][11]="";	tabla[190][12]="";	tabla[190][13]="";	tabla[190][14]="";	tabla[190][15]="";	tabla[190][16]="";	tabla[190][17]="";	tabla[190][18]="";	tabla[190][19]="";	tabla[190][20]="";	tabla[190][21]="";	tabla[190][22]="";	tabla[190][23]="";	tabla[190][24]="";	tabla[190][25]="";	tabla[190][26]="";	tabla[190][27]="";	tabla[190][28]="";	tabla[190][29]="";	tabla[190][30]="";	tabla[190][31]="";	tabla[190][32]="";	tabla[190][33]="";	tabla[190][34]="";	tabla[190][35]="";	tabla[190][36]="";	tabla[190][37]="";	tabla[190][38]="";	tabla[190][39]="";	tabla[190][40]="";	tabla[190][41]="";	tabla[190][42]="";	tabla[190][43]="";	tabla[190][44]="";	tabla[190][45]="";	tabla[190][46]="";	tabla[190][47]="";
		tabla[191][0]="s190";	tabla[191][1]="D>@";	tabla[191][2]="";	tabla[191][3]="s4";	tabla[191][4]="s5";	tabla[191][5]="s6";	tabla[191][6]="";	tabla[191][7]="";	tabla[191][8]="D>@";	tabla[191][9]="";	tabla[191][10]="";	tabla[191][11]="";	tabla[191][12]="D>@";	tabla[191][13]="";	tabla[191][14]="D>@";	tabla[191][15]="D>@";	tabla[191][16]="";	tabla[191][17]="";	tabla[191][18]="";	tabla[191][19]="";	tabla[191][20]="";	tabla[191][21]="D>@";	tabla[191][22]="";	tabla[191][23]="";	tabla[191][24]="";	tabla[191][25]="";	tabla[191][26]="";	tabla[191][27]="";	tabla[191][28]="";	tabla[191][29]="";	tabla[191][30]="";	tabla[191][31]="";	tabla[191][32]="";	tabla[191][33]="";	tabla[191][34]="s196";	tabla[191][35]="s158";	tabla[191][36]="";	tabla[191][37]="";	tabla[191][38]="";	tabla[191][39]="";	tabla[191][40]="";	tabla[191][41]="";	tabla[191][42]="";	tabla[191][43]="";	tabla[191][44]="";	tabla[191][45]="";	tabla[191][46]="";	tabla[191][47]="";
		tabla[192][0]="s191";	tabla[192][1]="s167";	tabla[192][2]="";	tabla[192][3]="";	tabla[192][4]="";	tabla[192][5]="";	tabla[192][6]="";	tabla[192][7]="";	tabla[192][8]="s168";	tabla[192][9]="";	tabla[192][10]="";	tabla[192][11]="";	tabla[192][12]="s169";	tabla[192][13]="";	tabla[192][14]="s170";	tabla[192][15]="s171";	tabla[192][16]="";	tabla[192][17]="";	tabla[192][18]="";	tabla[192][19]="";	tabla[192][20]="";	tabla[192][21]="S>@";	tabla[192][22]="";	tabla[192][23]="";	tabla[192][24]="";	tabla[192][25]="";	tabla[192][26]="";	tabla[192][27]="";	tabla[192][28]="";	tabla[192][29]="";	tabla[192][30]="";	tabla[192][31]="";	tabla[192][32]="";	tabla[192][33]="";	tabla[192][34]="";	tabla[192][35]="";	tabla[192][36]="";	tabla[192][37]="s197";	tabla[192][38]="";	tabla[192][39]="";	tabla[192][40]="";	tabla[192][41]="";	tabla[192][42]="";	tabla[192][43]="";	tabla[192][44]="";	tabla[192][45]="";	tabla[192][46]="";	tabla[192][47]="";
		tabla[193][0]="s192";	tabla[193][1]="s88";	tabla[193][2]="";	tabla[193][3]="";	tabla[193][4]="";	tabla[193][5]="";	tabla[193][6]="";	tabla[193][7]="";	tabla[193][8]="";	tabla[193][9]="";	tabla[193][10]="s89";	tabla[193][11]="";	tabla[193][12]="";	tabla[193][13]="";	tabla[193][14]="";	tabla[193][15]="";	tabla[193][16]="";	tabla[193][17]="";	tabla[193][18]="";	tabla[193][19]="s92";	tabla[193][20]="";	tabla[193][21]="";	tabla[193][22]="";	tabla[193][23]="";	tabla[193][24]="";	tabla[193][25]="";	tabla[193][26]="";	tabla[193][27]="";	tabla[193][28]="";	tabla[193][29]="s90";	tabla[193][30]="s91";	tabla[193][31]="";	tabla[193][32]="";	tabla[193][33]="";	tabla[193][34]="";	tabla[193][35]="";	tabla[193][36]="";	tabla[193][37]="";	tabla[193][38]="";	tabla[193][39]="";	tabla[193][40]="";	tabla[193][41]="s198";	tabla[193][42]="";	tabla[193][43]="";	tabla[193][44]="";	tabla[193][45]="";	tabla[193][46]="";	tabla[193][47]="s87";
		tabla[194][0]="s193";	tabla[194][1]="";	tabla[194][2]="";	tabla[194][3]="";	tabla[194][4]="";	tabla[194][5]="";	tabla[194][6]="";	tabla[194][7]="";	tabla[194][8]="";	tabla[194][9]="";	tabla[194][10]="";	tabla[194][11]="";	tabla[194][12]="";	tabla[194][13]="";	tabla[194][14]="";	tabla[194][15]="";	tabla[194][16]="";	tabla[194][17]="";	tabla[194][18]="s100";	tabla[194][19]="";	tabla[194][20]="";	tabla[194][21]="";	tabla[194][22]="";	tabla[194][23]="";	tabla[194][24]="";	tabla[194][25]="";	tabla[194][26]="";	tabla[194][27]="";	tabla[194][28]="";	tabla[194][29]="";	tabla[194][30]="";	tabla[194][31]="";	tabla[194][32]="";	tabla[194][33]="";	tabla[194][34]="";	tabla[194][35]="";	tabla[194][36]="";	tabla[194][37]="";	tabla[194][38]="";	tabla[194][39]="s199";	tabla[194][40]="";	tabla[194][41]="";	tabla[194][42]="";	tabla[194][43]="";	tabla[194][44]="";	tabla[194][45]="";	tabla[194][46]="";	tabla[194][47]="";
		tabla[195][0]="s194";	tabla[195][1]="s102";	tabla[195][2]="";	tabla[195][3]="";	tabla[195][4]="";	tabla[195][5]="";	tabla[195][6]="";	tabla[195][7]="";	tabla[195][8]="";	tabla[195][9]="";	tabla[195][10]="s103";	tabla[195][11]="";	tabla[195][12]="";	tabla[195][13]="";	tabla[195][14]="";	tabla[195][15]="";	tabla[195][16]="";	tabla[195][17]="";	tabla[195][18]="";	tabla[195][19]="s106";	tabla[195][20]="";	tabla[195][21]="";	tabla[195][22]="";	tabla[195][23]="";	tabla[195][24]="";	tabla[195][25]="";	tabla[195][26]="";	tabla[195][27]="";	tabla[195][28]="";	tabla[195][29]="s104";	tabla[195][30]="s105";	tabla[195][31]="";	tabla[195][32]="";	tabla[195][33]="";	tabla[195][34]="";	tabla[195][35]="";	tabla[195][36]="";	tabla[195][37]="";	tabla[195][38]="";	tabla[195][39]="";	tabla[195][40]="";	tabla[195][41]="";	tabla[195][42]="";	tabla[195][43]="";	tabla[195][44]="";	tabla[195][45]="";	tabla[195][46]="";	tabla[195][47]="s200";
		tabla[196][0]="s195";	tabla[196][1]="s102";	tabla[196][2]="";	tabla[196][3]="";	tabla[196][4]="";	tabla[196][5]="";	tabla[196][6]="";	tabla[196][7]="";	tabla[196][8]="";	tabla[196][9]="";	tabla[196][10]="s103";	tabla[196][11]="";	tabla[196][12]="";	tabla[196][13]="";	tabla[196][14]="";	tabla[196][15]="";	tabla[196][16]="";	tabla[196][17]="";	tabla[196][18]="";	tabla[196][19]="s106";	tabla[196][20]="";	tabla[196][21]="";	tabla[196][22]="";	tabla[196][23]="";	tabla[196][24]="";	tabla[196][25]="";	tabla[196][26]="";	tabla[196][27]="";	tabla[196][28]="";	tabla[196][29]="s104";	tabla[196][30]="s105";	tabla[196][31]="";	tabla[196][32]="";	tabla[196][33]="";	tabla[196][34]="";	tabla[196][35]="";	tabla[196][36]="";	tabla[196][37]="";	tabla[196][38]="";	tabla[196][39]="";	tabla[196][40]="";	tabla[196][41]="";	tabla[196][42]="";	tabla[196][43]="";	tabla[196][44]="";	tabla[196][45]="";	tabla[196][46]="";	tabla[196][47]="s201";
		tabla[197][0]="s196";	tabla[197][1]="D>D_;_Sd_id_Tipo";	tabla[197][2]="";	tabla[197][3]="";	tabla[197][4]="";	tabla[197][5]="";	tabla[197][6]="";	tabla[197][7]="";	tabla[197][8]="D>D_;_Sd_id_Tipo";	tabla[197][9]="";	tabla[197][10]="";	tabla[197][11]="";	tabla[197][12]="D>D_;_Sd_id_Tipo";	tabla[197][13]="";	tabla[197][14]="D>D_;_Sd_id_Tipo";	tabla[197][15]="D>D_;_Sd_id_Tipo";	tabla[197][16]="";	tabla[197][17]="";	tabla[197][18]="";	tabla[197][19]="";	tabla[197][20]="";	tabla[197][21]="D>D_;_Sd_id_Tipo";	tabla[197][22]="";	tabla[197][23]="";	tabla[197][24]="";	tabla[197][25]="";	tabla[197][26]="";	tabla[197][27]="";	tabla[197][28]="";	tabla[197][29]="";	tabla[197][30]="";	tabla[197][31]="";	tabla[197][32]="";	tabla[197][33]="";	tabla[197][34]="";	tabla[197][35]="";	tabla[197][36]="";	tabla[197][37]="";	tabla[197][38]="";	tabla[197][39]="";	tabla[197][40]="";	tabla[197][41]="";	tabla[197][42]="";	tabla[197][43]="";	tabla[197][44]="";	tabla[197][45]="";	tabla[197][46]="";	tabla[197][47]="";
		tabla[198][0]="s197";	tabla[198][1]="";	tabla[198][2]="";	tabla[198][3]="";	tabla[198][4]="";	tabla[198][5]="";	tabla[198][6]="";	tabla[198][7]="";	tabla[198][8]="";	tabla[198][9]="";	tabla[198][10]="";	tabla[198][11]="";	tabla[198][12]="";	tabla[198][13]="";	tabla[198][14]="";	tabla[198][15]="";	tabla[198][16]="";	tabla[198][17]="";	tabla[198][18]="";	tabla[198][19]="";	tabla[198][20]="";	tabla[198][21]="S>S_;_Exp_=_id";	tabla[198][22]="";	tabla[198][23]="";	tabla[198][24]="";	tabla[198][25]="";	tabla[198][26]="";	tabla[198][27]="";	tabla[198][28]="";	tabla[198][29]="";	tabla[198][30]="";	tabla[198][31]="";	tabla[198][32]="";	tabla[198][33]="";	tabla[198][34]="";	tabla[198][35]="";	tabla[198][36]="";	tabla[198][37]="";	tabla[198][38]="";	tabla[198][39]="";	tabla[198][40]="";	tabla[198][41]="";	tabla[198][42]="";	tabla[198][43]="";	tabla[198][44]="";	tabla[198][45]="";	tabla[198][46]="";	tabla[198][47]="";
		tabla[199][0]="s198";	tabla[199][1]="";	tabla[199][2]="";	tabla[199][3]="";	tabla[199][4]="";	tabla[199][5]="";	tabla[199][6]="";	tabla[199][7]="";	tabla[199][8]="";	tabla[199][9]="";	tabla[199][10]="";	tabla[199][11]="s202";	tabla[199][12]="";	tabla[199][13]="";	tabla[199][14]="";	tabla[199][15]="";	tabla[199][16]="";	tabla[199][17]="";	tabla[199][18]="";	tabla[199][19]="";	tabla[199][20]="";	tabla[199][21]="";	tabla[199][22]="";	tabla[199][23]="";	tabla[199][24]="";	tabla[199][25]="";	tabla[199][26]="";	tabla[199][27]="";	tabla[199][28]="";	tabla[199][29]="";	tabla[199][30]="";	tabla[199][31]="";	tabla[199][32]="";	tabla[199][33]="";	tabla[199][34]="";	tabla[199][35]="";	tabla[199][36]="";	tabla[199][37]="";	tabla[199][38]="";	tabla[199][39]="";	tabla[199][40]="";	tabla[199][41]="";	tabla[199][42]="";	tabla[199][43]="";	tabla[199][44]="";	tabla[199][45]="";	tabla[199][46]="";	tabla[199][47]="";
		tabla[200][0]="s199";	tabla[200][1]="";	tabla[200][2]="";	tabla[200][3]="";	tabla[200][4]="";	tabla[200][5]="";	tabla[200][6]="";	tabla[200][7]="";	tabla[200][8]="";	tabla[200][9]="";	tabla[200][10]="";	tabla[200][11]="";	tabla[200][12]="";	tabla[200][13]="s203";	tabla[200][14]="";	tabla[200][15]="";	tabla[200][16]="";	tabla[200][17]="";	tabla[200][18]="";	tabla[200][19]="";	tabla[200][20]="";	tabla[200][21]="";	tabla[200][22]="";	tabla[200][23]="";	tabla[200][24]="";	tabla[200][25]="";	tabla[200][26]="";	tabla[200][27]="";	tabla[200][28]="";	tabla[200][29]="";	tabla[200][30]="";	tabla[200][31]="";	tabla[200][32]="";	tabla[200][33]="";	tabla[200][34]="";	tabla[200][35]="";	tabla[200][36]="";	tabla[200][37]="";	tabla[200][38]="";	tabla[200][39]="";	tabla[200][40]="";	tabla[200][41]="";	tabla[200][42]="";	tabla[200][43]="";	tabla[200][44]="";	tabla[200][45]="";	tabla[200][46]="";	tabla[200][47]="";
		tabla[201][0]="s200";	tabla[201][1]="";	tabla[201][2]="";	tabla[201][3]="";	tabla[201][4]="";	tabla[201][5]="";	tabla[201][6]="";	tabla[201][7]="";	tabla[201][8]="";	tabla[201][9]="";	tabla[201][10]="";	tabla[201][11]="s204";	tabla[201][12]="";	tabla[201][13]="";	tabla[201][14]="";	tabla[201][15]="";	tabla[201][16]="";	tabla[201][17]="";	tabla[201][18]="";	tabla[201][19]="";	tabla[201][20]="";	tabla[201][21]="";	tabla[201][22]="";	tabla[201][23]="";	tabla[201][24]="";	tabla[201][25]="";	tabla[201][26]="";	tabla[201][27]="";	tabla[201][28]="";	tabla[201][29]="";	tabla[201][30]="";	tabla[201][31]="";	tabla[201][32]="";	tabla[201][33]="";	tabla[201][34]="";	tabla[201][35]="";	tabla[201][36]="";	tabla[201][37]="";	tabla[201][38]="";	tabla[201][39]="";	tabla[201][40]="";	tabla[201][41]="";	tabla[201][42]="";	tabla[201][43]="";	tabla[201][44]="";	tabla[201][45]="";	tabla[201][46]="";	tabla[201][47]="";
		tabla[202][0]="s201";	tabla[202][1]="";	tabla[202][2]="";	tabla[202][3]="";	tabla[202][4]="";	tabla[202][5]="";	tabla[202][6]="";	tabla[202][7]="";	tabla[202][8]="";	tabla[202][9]="";	tabla[202][10]="";	tabla[202][11]="s205";	tabla[202][12]="";	tabla[202][13]="";	tabla[202][14]="";	tabla[202][15]="";	tabla[202][16]="";	tabla[202][17]="";	tabla[202][18]="";	tabla[202][19]="";	tabla[202][20]="";	tabla[202][21]="";	tabla[202][22]="";	tabla[202][23]="";	tabla[202][24]="";	tabla[202][25]="";	tabla[202][26]="";	tabla[202][27]="";	tabla[202][28]="";	tabla[202][29]="";	tabla[202][30]="";	tabla[202][31]="";	tabla[202][32]="";	tabla[202][33]="";	tabla[202][34]="";	tabla[202][35]="";	tabla[202][36]="";	tabla[202][37]="";	tabla[202][38]="";	tabla[202][39]="";	tabla[202][40]="";	tabla[202][41]="";	tabla[202][42]="";	tabla[202][43]="";	tabla[202][44]="";	tabla[202][45]="";	tabla[202][46]="";	tabla[202][47]="";
		tabla[203][0]="s202";	tabla[203][1]="";	tabla[203][2]="s206";	tabla[203][3]="";	tabla[203][4]="";	tabla[203][5]="";	tabla[203][6]="";	tabla[203][7]="";	tabla[203][8]="";	tabla[203][9]="";	tabla[203][10]="";	tabla[203][11]="";	tabla[203][12]="";	tabla[203][13]="";	tabla[203][14]="";	tabla[203][15]="";	tabla[203][16]="";	tabla[203][17]="";	tabla[203][18]="";	tabla[203][19]="";	tabla[203][20]="";	tabla[203][21]="";	tabla[203][22]="";	tabla[203][23]="";	tabla[203][24]="";	tabla[203][25]="";	tabla[203][26]="";	tabla[203][27]="";	tabla[203][28]="";	tabla[203][29]="";	tabla[203][30]="";	tabla[203][31]="";	tabla[203][32]="";	tabla[203][33]="";	tabla[203][34]="";	tabla[203][35]="";	tabla[203][36]="";	tabla[203][37]="";	tabla[203][38]="";	tabla[203][39]="";	tabla[203][40]="";	tabla[203][41]="";	tabla[203][42]="";	tabla[203][43]="";	tabla[203][44]="";	tabla[203][45]="";	tabla[203][46]="";	tabla[203][47]="";
		tabla[204][0]="s203";	tabla[204][1]="s167";	tabla[204][2]="";	tabla[204][3]="";	tabla[204][4]="";	tabla[204][5]="";	tabla[204][6]="";	tabla[204][7]="";	tabla[204][8]="s168";	tabla[204][9]="";	tabla[204][10]="";	tabla[204][11]="";	tabla[204][12]="s169";	tabla[204][13]="";	tabla[204][14]="s170";	tabla[204][15]="s171";	tabla[204][16]="";	tabla[204][17]="";	tabla[204][18]="";	tabla[204][19]="";	tabla[204][20]="";	tabla[204][21]="S>@";	tabla[204][22]="";	tabla[204][23]="";	tabla[204][24]="";	tabla[204][25]="";	tabla[204][26]="";	tabla[204][27]="";	tabla[204][28]="";	tabla[204][29]="";	tabla[204][30]="";	tabla[204][31]="";	tabla[204][32]="";	tabla[204][33]="";	tabla[204][34]="";	tabla[204][35]="";	tabla[204][36]="";	tabla[204][37]="s207";	tabla[204][38]="";	tabla[204][39]="";	tabla[204][40]="";	tabla[204][41]="";	tabla[204][42]="";	tabla[204][43]="";	tabla[204][44]="";	tabla[204][45]="";	tabla[204][46]="";	tabla[204][47]="";
		tabla[205][0]="s204";	tabla[205][1]="";	tabla[205][2]="s208";	tabla[205][3]="";	tabla[205][4]="";	tabla[205][5]="";	tabla[205][6]="";	tabla[205][7]="";	tabla[205][8]="";	tabla[205][9]="";	tabla[205][10]="";	tabla[205][11]="";	tabla[205][12]="";	tabla[205][13]="";	tabla[205][14]="";	tabla[205][15]="";	tabla[205][16]="";	tabla[205][17]="";	tabla[205][18]="";	tabla[205][19]="";	tabla[205][20]="";	tabla[205][21]="";	tabla[205][22]="";	tabla[205][23]="";	tabla[205][24]="";	tabla[205][25]="";	tabla[205][26]="";	tabla[205][27]="";	tabla[205][28]="";	tabla[205][29]="";	tabla[205][30]="";	tabla[205][31]="";	tabla[205][32]="";	tabla[205][33]="";	tabla[205][34]="";	tabla[205][35]="";	tabla[205][36]="";	tabla[205][37]="";	tabla[205][38]="";	tabla[205][39]="";	tabla[205][40]="";	tabla[205][41]="";	tabla[205][42]="";	tabla[205][43]="";	tabla[205][44]="";	tabla[205][45]="";	tabla[205][46]="";	tabla[205][47]="";
		tabla[206][0]="s205";	tabla[206][1]="";	tabla[206][2]="s209";	tabla[206][3]="";	tabla[206][4]="";	tabla[206][5]="";	tabla[206][6]="";	tabla[206][7]="";	tabla[206][8]="";	tabla[206][9]="";	tabla[206][10]="";	tabla[206][11]="";	tabla[206][12]="";	tabla[206][13]="";	tabla[206][14]="";	tabla[206][15]="";	tabla[206][16]="";	tabla[206][17]="";	tabla[206][18]="";	tabla[206][19]="";	tabla[206][20]="";	tabla[206][21]="";	tabla[206][22]="";	tabla[206][23]="";	tabla[206][24]="";	tabla[206][25]="";	tabla[206][26]="";	tabla[206][27]="";	tabla[206][28]="";	tabla[206][29]="";	tabla[206][30]="";	tabla[206][31]="";	tabla[206][32]="";	tabla[206][33]="";	tabla[206][34]="";	tabla[206][35]="";	tabla[206][36]="";	tabla[206][37]="";	tabla[206][38]="";	tabla[206][39]="";	tabla[206][40]="";	tabla[206][41]="";	tabla[206][42]="";	tabla[206][43]="";	tabla[206][44]="";	tabla[206][45]="";	tabla[206][46]="";	tabla[206][47]="";
		tabla[207][0]="s206";	tabla[207][1]="s167";	tabla[207][2]="";	tabla[207][3]="";	tabla[207][4]="";	tabla[207][5]="";	tabla[207][6]="";	tabla[207][7]="";	tabla[207][8]="s168";	tabla[207][9]="";	tabla[207][10]="";	tabla[207][11]="";	tabla[207][12]="s169";	tabla[207][13]="";	tabla[207][14]="s170";	tabla[207][15]="s171";	tabla[207][16]="";	tabla[207][17]="";	tabla[207][18]="";	tabla[207][19]="";	tabla[207][20]="";	tabla[207][21]="S>@";	tabla[207][22]="";	tabla[207][23]="";	tabla[207][24]="";	tabla[207][25]="";	tabla[207][26]="";	tabla[207][27]="";	tabla[207][28]="";	tabla[207][29]="";	tabla[207][30]="";	tabla[207][31]="";	tabla[207][32]="";	tabla[207][33]="";	tabla[207][34]="";	tabla[207][35]="";	tabla[207][36]="";	tabla[207][37]="s210";	tabla[207][38]="";	tabla[207][39]="";	tabla[207][40]="";	tabla[207][41]="";	tabla[207][42]="";	tabla[207][43]="";	tabla[207][44]="";	tabla[207][45]="";	tabla[207][46]="";	tabla[207][47]="";
		tabla[208][0]="s207";	tabla[208][1]="";	tabla[208][2]="";	tabla[208][3]="";	tabla[208][4]="";	tabla[208][5]="";	tabla[208][6]="";	tabla[208][7]="";	tabla[208][8]="";	tabla[208][9]="";	tabla[208][10]="";	tabla[208][11]="";	tabla[208][12]="";	tabla[208][13]="";	tabla[208][14]="";	tabla[208][15]="";	tabla[208][16]="";	tabla[208][17]="";	tabla[208][18]="";	tabla[208][19]="";	tabla[208][20]="";	tabla[208][21]="S>S_endS_Caso_)_id_(_switch";	tabla[208][22]="";	tabla[208][23]="";	tabla[208][24]="";	tabla[208][25]="";	tabla[208][26]="";	tabla[208][27]="";	tabla[208][28]="";	tabla[208][29]="";	tabla[208][30]="";	tabla[208][31]="";	tabla[208][32]="";	tabla[208][33]="";	tabla[208][34]="";	tabla[208][35]="";	tabla[208][36]="";	tabla[208][37]="";	tabla[208][38]="";	tabla[208][39]="";	tabla[208][40]="";	tabla[208][41]="";	tabla[208][42]="";	tabla[208][43]="";	tabla[208][44]="";	tabla[208][45]="";	tabla[208][46]="";	tabla[208][47]="";
		tabla[209][0]="s208";	tabla[209][1]="s167";	tabla[209][2]="";	tabla[209][3]="";	tabla[209][4]="";	tabla[209][5]="";	tabla[209][6]="";	tabla[209][7]="";	tabla[209][8]="s168";	tabla[209][9]="";	tabla[209][10]="";	tabla[209][11]="";	tabla[209][12]="s169";	tabla[209][13]="";	tabla[209][14]="s170";	tabla[209][15]="s171";	tabla[209][16]="";	tabla[209][17]="";	tabla[209][18]="";	tabla[209][19]="";	tabla[209][20]="";	tabla[209][21]="S>_@_";	tabla[209][22]="";	tabla[209][23]="";	tabla[209][24]="";	tabla[209][25]="";	tabla[209][26]="";	tabla[209][27]="";	tabla[209][28]="";	tabla[209][29]="";	tabla[209][30]="";	tabla[209][31]="";	tabla[209][32]="";	tabla[209][33]="";	tabla[209][34]="";	tabla[209][35]="";	tabla[209][36]="";	tabla[209][37]="s211";	tabla[209][38]="";	tabla[209][39]="";	tabla[209][40]="";	tabla[209][41]="";	tabla[209][42]="";	tabla[209][43]="";	tabla[209][44]="";	tabla[209][45]="";	tabla[209][46]="";	tabla[209][47]="";
		tabla[210][0]="s209";	tabla[210][1]="s167";	tabla[210][2]="";	tabla[210][3]="";	tabla[210][4]="";	tabla[210][5]="";	tabla[210][6]="";	tabla[210][7]="";	tabla[210][8]="s168";	tabla[210][9]="";	tabla[210][10]="";	tabla[210][11]="";	tabla[210][12]="s169";	tabla[210][13]="";	tabla[210][14]="s170";	tabla[210][15]="s171";	tabla[210][16]="";	tabla[210][17]="";	tabla[210][18]="";	tabla[210][19]="";	tabla[210][20]="";	tabla[210][21]="S>@";	tabla[210][22]="";	tabla[210][23]="";	tabla[210][24]="";	tabla[210][25]="";	tabla[210][26]="";	tabla[210][27]="";	tabla[210][28]="";	tabla[210][29]="";	tabla[210][30]="";	tabla[210][31]="";	tabla[210][32]="";	tabla[210][33]="";	tabla[210][34]="";	tabla[210][35]="";	tabla[210][36]="";	tabla[210][37]="s212";	tabla[210][38]="";	tabla[210][39]="";	tabla[210][40]="";	tabla[210][41]="";	tabla[210][42]="";	tabla[210][43]="";	tabla[210][44]="";	tabla[210][45]="";	tabla[210][46]="";	tabla[210][47]="";
		tabla[211][0]="s210";	tabla[211][1]="";	tabla[211][2]="";	tabla[211][3]="";	tabla[211][4]="";	tabla[211][5]="";	tabla[211][6]="";	tabla[211][7]="";	tabla[211][8]="";	tabla[211][9]="";	tabla[211][10]="";	tabla[211][11]="";	tabla[211][12]="";	tabla[211][13]="";	tabla[211][14]="";	tabla[211][15]="";	tabla[211][16]="";	tabla[211][17]="";	tabla[211][18]="";	tabla[211][19]="";	tabla[211][20]="";	tabla[211][21]="S>_S_;_)_Cond_(_while_P_do";	tabla[211][22]="";	tabla[211][23]="";	tabla[211][24]="";	tabla[211][25]="";	tabla[211][26]="";	tabla[211][27]="";	tabla[211][28]="";	tabla[211][29]="";	tabla[211][30]="";	tabla[211][31]="";	tabla[211][32]="";	tabla[211][33]="";	tabla[211][34]="";	tabla[211][35]="";	tabla[211][36]="";	tabla[211][37]="";	tabla[211][38]="";	tabla[211][39]="";	tabla[211][40]="";	tabla[211][41]="";	tabla[211][42]="";	tabla[211][43]="";	tabla[211][44]="";	tabla[211][45]="";	tabla[211][46]="";	tabla[211][47]="";
		tabla[212][0]="s211";	tabla[212][1]="";	tabla[212][2]="";	tabla[212][3]="";	tabla[212][4]="";	tabla[212][5]="";	tabla[212][6]="";	tabla[212][7]="";	tabla[212][8]="";	tabla[212][9]="";	tabla[212][10]="";	tabla[212][11]="";	tabla[212][12]="";	tabla[212][13]="";	tabla[212][14]="";	tabla[212][15]="";	tabla[212][16]="";	tabla[212][17]="";	tabla[212][18]="";	tabla[212][19]="";	tabla[212][20]="";	tabla[212][21]="S>_S_;_)_F_,_Dt_(_imprimir";	tabla[212][22]="";	tabla[212][23]="";	tabla[212][24]="";	tabla[212][25]="";	tabla[212][26]="";	tabla[212][27]="";	tabla[212][28]="";	tabla[212][29]="";	tabla[212][30]="";	tabla[212][31]="";	tabla[212][32]="";	tabla[212][33]="";	tabla[212][34]="";	tabla[212][35]="";	tabla[212][36]="";	tabla[212][37]="";	tabla[212][38]="";	tabla[212][39]="";	tabla[212][40]="";	tabla[212][41]="";	tabla[212][42]="";	tabla[212][43]="";	tabla[212][44]="";	tabla[212][45]="";	tabla[212][46]="";	tabla[212][47]="";
		tabla[213][0]="s212";	tabla[213][1]="";	tabla[213][2]="";	tabla[213][3]="";	tabla[213][4]="";	tabla[213][5]="";	tabla[213][6]="";	tabla[213][7]="";	tabla[213][8]="";	tabla[213][9]="";	tabla[213][10]="";	tabla[213][11]="";	tabla[213][12]="";	tabla[213][13]="";	tabla[213][14]="";	tabla[213][15]="";	tabla[213][16]="";	tabla[213][17]="";	tabla[213][18]="";	tabla[213][19]="";	tabla[213][20]="";	tabla[213][21]="S>_S_;_)_F_,_Dt_(_leer";	tabla[213][22]="";	tabla[213][23]="";	tabla[213][24]="";	tabla[213][25]="";	tabla[213][26]="";	tabla[213][27]="";	tabla[213][28]="";	tabla[213][29]="";	tabla[213][30]="";	tabla[213][31]="";	tabla[213][32]="";	tabla[213][33]="";	tabla[213][34]="";	tabla[213][35]="";	tabla[213][36]="";	tabla[213][37]="";	tabla[213][38]="";	tabla[213][39]="";	tabla[213][40]="";	tabla[213][41]="";	tabla[213][42]="";	tabla[213][43]="";	tabla[213][44]="";	tabla[213][45]="";	tabla[213][46]="";	tabla[213][47]="";
	}
	
	private void fillTable(String p, String ent, String ac, String sem)
	{
		String[] row = {p,ent,ac,sem};
		modelo.addRow(row);
		l.updateUI();
		return;
	}
	
}