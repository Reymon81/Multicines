import java.util.ArrayList;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * <p><h1>GESTION MULTICINES</h1></p>
 * 
 * <p>EL PROGRAMA PUEDE CREAR SALAS PARA LOS MULTICINES INSERTANDO SU AFORO Y LA PELICULA EN PROYECCION</p>
 * <p>TAMBIEN LAS PUEDE ELIMINAR O MOSTRAR TODAS LAS SALAS PARA SABER LAS PELICULAS EN PROYECCION, EL AFORO
 * Y LOS ASIENTOS QUE HAY LIBRES PARA COMPRAR UNA ENTRADA</p>
 * <p>CUANDO SELECCIONAMOS UNA SALA PARA COMPRAR LA ENTRADA NOS MUESTRA COMO ESTAN LOS ASIENTOS PARA SELECCIONAR
 * LOS QUE NO ESTAN RESERVADOS</p><br>
 * 
 * @author RAMON TORRES
 * @since 19/11/2020
 * @version 1.0
 *
 */

public class Multicines {
	
	/**
	 * FUNCION PARA FORMATEAR LA INFORMACION DE LAS SALAS EN COLUMNAS
	 * @param cadena ES LA INFORMACION DE TIPO STRING QUE QUEREMOS GUARDAR EN UNA COLUMNA
	 * @param digitos ES LA CANTIDAD DE ESPACIOS DE TIPO ENTERO QUE QUEREMOS QUE OCUPE LA CADENA RESULTANTE
	 * @return DEVUELVE LA CADENA CON LA INFORMACION FORMATEADA
	 */	
	public static String formateaTexto(String cadena, int digitos) {

		int ocupados = cadena.length();
		int resto = digitos - ocupados;
		for (int i = 0; i < resto; i++) {
			cadena += " ";
		}
		return cadena;
	}
	
	/**
	 * FUNCION QUE COMPRUEBA SI LO ESCRITO POR TECLADO ES UN NUMERO
	 * @param cadena ES UN STRING QUE RECOGE LO INSERTADO POR TECLADO
	 * @return <ul>
	 *		<li>true: LO INSERTADO POR TECLADO ES UN NUMERO
	 * 		<li>false: LO INSERTADO POR TECLADO NO ES UN NUMERO	
	 * 	   <ul>
	 */	
	public static boolean esNumero(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * METODO PARA MOSTRAR LA MATRIZ PATIOBUTACAS
	 * @param matriz ES LA MATRIZ QUE CONTIENE LOS ASIENTOS DE LA SALA DIVIDIDO EN FILAS Y COLUMNAS
	 * @param filas ES LA CANTIDAD DE FILAS QUE CONTIENE LA SALA DE CINE
	 * @param columnas ES LA CANTIDAD DE ASIENTOS POR FILA QUE CONTIENE LA SALA DE CINE
	 */
	public static void patioButacas(String[][] matriz, int filas, int columnas) {
		System.out.println();
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * METODO QUE IMPRIME LAS OPCIONES QUE TENEMOS EN EL MENU
	 */
	public static void printMenu() {
		System.out.println("\n****** MENU *******\n");
		System.out.println("1. Mostrar Salas\n" + "2. Añadir Sala\n" + "3. Eliminar Sala\n" + "4. Vender Entradas\n"
				+ "5. Salir\n");
	}
 
	/**
	 * METODO PARA AÑADIR UNA SALA EN LA HASHTABLE
	 * @param salas ES UNA HASHTABLE DONDE LAS KEYS SON EL NUMERO DE SALAS Y EL CONTENIDO ASOCIADO ES LA INFORMACION DE LA SALA
	 */
	public static void annadirSala(Hashtable<Integer, String> salas) {
		Scanner sc = new Scanner(System.in);
		int numero;

		System.out.println("introduce el numero de la sala: ");
		String cadena = sc.nextLine();
		while (!esNumero(cadena)) {
			System.out.println("no has insertado un numero, prueba de nuevo: ");
			cadena = sc.nextLine();
		}
		numero = Integer.parseInt(cadena);

		while (salas.containsKey(numero)) {
			System.out.println("la sala de cine introducida ya existe, prueba de nuevo: ");
			cadena = sc.nextLine();
			while (!esNumero(cadena)) {
				System.out.println("no has insertado un numero, prueba de nuevo: ");
				cadena = sc.nextLine();
			}
			numero = Integer.parseInt(cadena);
		}
		
		System.out.println("introduce el numero de filas que tiene la sala: ");
		String fila = sc.nextLine();
		while (!esNumero(fila)) {
			System.out.println("no has insertado un numero, prueba de nuevo: ");
			fila = sc.nextLine();
		}
		int numFilas = Integer.parseInt(fila);
		
		
		System.out.println("introduce el numero de columnas que tiene la sala: ");
		String columna = sc.nextLine();
		while (!esNumero(columna)) {
			System.out.println("no has insertado un numero, prueba de nuevo: ");
			columna = sc.nextLine();
		}
		int numColumnas = Integer.parseInt(columna);

		int aforo = numFilas * numColumnas;

		System.out.println("introduce el titulo de la pelicula en proyeccion: ");
		String titulo = sc.nextLine();
		String sala = titulo + ";" + fila + ";" + columna + ";";
		for (int i = 0; i < aforo; i++) {
			sala += "-:";
		}
		//COMO MINIMO LA SALA DEBE TENER UNA BUTACA
		if(numFilas>0 && numColumnas>0) {
		salas.put(numero, sala);
		}else {
			System.out.println("la sala no ha podido crearse por falta de butacas");
		}
	}

	/**
	 * METODO PARA MOSTRAR LAS SALAS
	 * @param salas ES UNA HASHTABLE DONDE LAS KEYS SON EL NUMERO DE SALAS Y EL CONTENIDO ASOCIADO ES LA INFORMACION DE LA SALA
	 * @param patio ES UN STRINGTOKENIZER QUE GUARDA LA INFORMACION DE UNA SALA (TITULO,FILAS,COLUMNAS Y ASIENTOS)
	 */
	public static void mostrarSala(Hashtable<Integer, String> salas, StringTokenizer patio) {
		int sala, filas = 0, columnas = 0;
		String informacion, titulo = null;
		int libres = 0;
		System.out.println();
		System.out.println("Sala    Pelicula                      Tamaño sala    Asientos libres");
		System.out.println("----    ---------------------------   -----------    ---------------");
		List<Integer> lista = new ArrayList<Integer>(salas.keySet());
		for (int i = lista.size()-1; i >= 0 ; i--) {
			// OBTENGO LA KEY DE SALAS
			sala = lista.get(i);;
			// OBTENGO LA INFORMACION ASOCIADA A ESA KEY
			informacion = salas.get(lista.get(i));
			// LOS DATOS DE LAS SALAS ESTAN SEPARADOS POR ;
			patio = new StringTokenizer(informacion, ";");
			if (patio.countTokens() == 4) {
				titulo = patio.nextToken();
				filas = Integer.parseInt(patio.nextToken());
				columnas = Integer.parseInt(patio.nextToken());
				// EL ULTIMO TOKEN SON LAS BUTACAS DE LA SALA, QUE ESTAN SEPARADAS POR :
				StringTokenizer stElementos = new StringTokenizer(patio.nextToken(), ":");
				while (stElementos.hasMoreTokens()) {
					if (stElementos.nextToken().equals("-"))
						libres++;
				}
			}
			String numeroSala = Integer.toString(sala);
			String capacidad = "(" + filas + "x" + columnas + ")";
			String aforo = Integer.toString(filas * columnas) + capacidad;
			String asientosLibres = Integer.toString(libres);
			// MUESTRO LA INFORMACION FORMATEADA POR COLUMNAS PARA QUE NO SE DESCUADRE
			System.out.println(formateaTexto(numeroSala, 8) + formateaTexto(titulo, 32) + formateaTexto(aforo, 19)
					+ formateaTexto(asientosLibres, 3));
			//PONGO LOS ASIENTOS LIBRES A 0 PARA QUE NO SE SUMEN A LOS DE LA SIGUIENTE SALA
			libres = 0;
		}

	}
	 
	/**
	 * METODO PARA ELIMINAR SALA
	 * @param salas ES UNA HASHTABLE DONDE LAS KEYS SON EL NUMERO DE SALAS Y EL CONTENIDO ASOCIADO ES LA INFORMACION DE LA SALA
	 * @param patio ES UN STRINGTOKENIZER QUE GUARDA LA INFORMACION DE UNA SALA (TITULO,FILAS,COLUMNAS Y ASIENTOS)
	 */
	public static void eliminarSala(Hashtable<Integer, String> salas, StringTokenizer patio) {
		Scanner sc = new Scanner(System.in);
		mostrarSala(salas, patio);
		System.out.println();
		System.out.println("inserta el numero de sala que quieres eliminar: ");
		String cadena = sc.nextLine();
		while (!esNumero(cadena)) {
			System.out.println("no has insertado un numero, prueba de nuevo: ");
			cadena = sc.nextLine();
		}
		int numero = Integer.parseInt(cadena);
		if (!salas.containsKey(numero)) {
			System.out.println("el numero de sala introducida no existe");
		} else {
			System.out.println("¿estas seguro de eliminar esta sala? s/n");
			String borrar = sc.nextLine();
			while (!borrar.equals("s") && !borrar.equals("n")) {
				System.out.println("la respuesta es incorrecta, prueba de nuevo s/n");
				borrar = sc.nextLine();
			}
			if (borrar.equals("s")) {
				salas.remove(numero);
			}
		}
	}

	/**
	 * METODO PARA COMPRAR ENTRADA
	 * @param salas ES UNA HASHTABLE DONDE LAS KEYS SON EL NUMERO DE SALAS Y EL CONTENIDO ASOCIADO ES LA INFORMACION DE LA SALA
	 * @param patio ES UN STRINGTOKENIZER QUE GUARDA LA INFORMACION DE UNA SALA (TITULO,FILAS,COLUMNAS Y ASIENTOS)
	 */
	public static void comprarEntrada(Hashtable<Integer, String> salas, StringTokenizer patio) {
		Scanner sc = new Scanner(System.in);
		String informacion, nuevaInformacion, titulo, nuevoPatio = "";
		int numero, filas, columnas, contador = 0;
		int fButaca;
		int cButaca;
		Vector<String> butacas = new Vector<>();
		String matriz[][] = null;
		mostrarSala(salas, patio);
		System.out.println();
		System.out.println("selecciona la sala de proyeccion: ");
		String seleccion = sc.nextLine();
		while (!esNumero(seleccion)) {
			System.out.println("no has insertado un numero, prueba de nuevo: ");
			seleccion = sc.nextLine();
		}
		numero = Integer.parseInt(seleccion);
		if (!salas.containsKey(numero)) {
			System.out.println("la sala introducida no existe");
		} else {
			try {

				informacion = salas.get(numero);
				patio = new StringTokenizer(informacion, ";");

				titulo = patio.nextToken();
				filas = Integer.parseInt(patio.nextToken());
				columnas = Integer.parseInt(patio.nextToken());
				//EL ULTIMO TOKEN CONTIENE TODOS LOS ASIENTOS DEL PATIO DE BUTACAS SEPARADAS POR :
				StringTokenizer stElementos = new StringTokenizer(patio.nextToken(), ":");
				matriz = new String[filas][columnas];
				while (stElementos.hasMoreTokens()) {
					//AÑADO AL VECTOR TODAS LAS BUTACAS
					butacas.add(stElementos.nextToken());
				}
				for (int i = 0; i < filas; i++) {
					for (int j = 0; j < columnas; j++) {
						//A CADA CELDA DE LA MATRIZ LE INSERTO LA BUTACA QUE LE PERTENECE SEGUN EL VECTOR
						matriz[i][j] = butacas.elementAt(contador);
						contador++;
					}
				}
				patioButacas(matriz, filas, columnas);
				while (true) {
					while (true) {
						System.out.println();
						System.out.println("inserta la fila de la butaca: ");
						String cadena = sc.nextLine();
						while (!esNumero(cadena)) {
							System.out.println("no has insertado un numero, prueba de nuevo: ");
							cadena = sc.nextLine();
						}
						fButaca = Integer.parseInt(cadena) - 1;

						System.out.println("inserta la columna de la butaca: ");
						cadena = sc.nextLine();
						while (!esNumero(cadena)) {
							System.out.println("no has insertado un numero, prueba de nuevo: ");
							cadena = sc.nextLine();
						}
						cButaca = Integer.parseInt(cadena) - 1;

						if (matriz[fButaca][cButaca].equals("O")) {
							System.out.println("la butaca ya esta ocupada, prueba de nuevo");
						} else {
							break;
						}
					}
					matriz[fButaca][cButaca] = "O";
					System.out.println();
					System.out.println("la sala ha quedado de la siguiente manera:\n");
					//MIENTRAS MUESTRO EL PATIO DE BUTACAS GUARDO EN UN STRING LOS CAMBIOS AL COMPRAR LAS ENTRADAS
					for (int i = 0; i < filas; i++) {
						for (int j = 0; j < columnas; j++) {
							System.out.print(matriz[i][j] + " ");
							nuevoPatio += matriz[i][j] + ":";
						}
						System.out.println();
					}
					nuevaInformacion = titulo + ";" + filas + ";" + columnas + ";" + nuevoPatio;
					salas.remove(numero);
					salas.put(numero, nuevaInformacion);
					//BORRO EL VALOR DEL PATIO DE BUTACAS PARA QUE NO SE SUMEN EN LA PROXIMA COMPRA
					nuevoPatio = "";
				}
			} catch (Exception e) {
				System.err.println("el numero de fila o de columna insertado no existe");
			}
		}
	}

	public static void main(String[] args) {
		Scanner sl = new Scanner(System.in);
		Scanner sc = new Scanner(System.in);
		String apagar = "";
		boolean salir = false;
		int opcion;
		StringTokenizer patio = null;
		Hashtable<Integer, String> salas = new Hashtable<Integer, String>();

		while (!salir) {

			printMenu();
			try {
				System.out.print("Elije una opcion: ");
				opcion = sc.nextInt();

				switch (opcion) {
				case 1:
					mostrarSala(salas, patio);
					break;
				case 2:
					annadirSala(salas);
					break;
				case 3:
					eliminarSala(salas, patio);
					break;
				case 4:
					comprarEntrada(salas, patio);
					break;
				case 5:
					System.out.println("¿Estas seguro de querer salir? s/n");
					apagar = sl.nextLine();
					while (!apagar.equals("s") && !apagar.equals("n")) {
						System.out.println("la respuesta es incorrecta, prueba de nuevo s/n");
						apagar = sl.nextLine();
					}
					if (apagar.equals("s")) {
						salir = true;
					}
					break;
				}
			} catch (InputMismatchException e) {
				System.err.println("Error, elije una opcion del 1 al 5");
				sc.next();
			}
		}
	}
}
