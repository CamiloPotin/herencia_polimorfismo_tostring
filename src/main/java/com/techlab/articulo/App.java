package com.techlab.articulo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.techlab.articulo.modelo.Articulo;
import com.techlab.articulo.modelo.ArticuloAlimenticio;
import com.techlab.articulo.modelo.ArticuloElectronico;
import com.techlab.articulo.modelo.Categoria;

public class App {

	public static void main(String[] args) {
		
        Scanner scanner = new Scanner(System.in);
		ArrayList<Articulo> listadoArticulos = new ArrayList<>();
		ArrayList<Categoria> categorias = new ArrayList<>();
		precargarCategorias(categorias);

		int opcion=-1;
		
		
		do{
			mostrarMenu();
				opcion = leerEntero(scanner, "Ingrese una opción: ");
				switch (opcion) {
					
					case 1:
						ingresarArticulo(scanner, listadoArticulos, categorias);
						break;
					
					case 2:
						listarArticulos(listadoArticulos);
						break;
					
					case 3:
						consultarArticulo(scanner,listadoArticulos);
						break;
					case 4:
						modificarArticulo(scanner,listadoArticulos,categorias);
						break;
					case 5:
						eliminarArticulo(scanner, listadoArticulos);
						break;
					case 6:
						listarCategorias(categorias);
						break;
					case 0:
						System.out.print("Finalizando");
						break;
					default:
						System.out.print("Error: Valor invalido");
						break;
				}
				if(opcion !=0){
				System.out.println("Presione enter para continuar");	
				scanner.nextLine();
				}
			
			
		} while(opcion != 0);
		
			
		scanner.close();
		}





	/*
	Metodo para precargar categorias */
	public static void precargarCategorias(ArrayList<Categoria> categorias) {

        categorias.add( new Categoria(1, "Electronica", "Productos tecnológicos y electrónicos"));
		categorias.add( new Categoria(2, "Periféricos", "Accesorios para computadora"));
		categorias.add( new Categoria(3, "Alimentos", "Productos alimenticios"));
		categorias.add( new Categoria(4, "Limpieza", "Artículos de limpieza del hogar"));

	}

	/*
	Metodo para mostrar el menu
	*/
	public static void mostrarMenu(){
			System.out.println("======================================================");
			System.out.println("SISTEMA DE ARTÍCULOS - CLASE 4 (HERENCIA Y TO_STRING)");
			System.out.println("======================================================");
			System.out.println("1 - Ingresar artículo");
			System.out.println("2 - Listar artículos");
			System.out.println("3 - Consultar un artículo");
			System.out.println("4 - Modificar un artículo");
			System.out.println("5 - Eliminar un artículo");
			System.out.println("6 - Listar categorías");
			System.out.println("0 - Salir");
			System.out.println("======================================================");

	};

// Opciones del menu

	public static void ingresarArticulo(
	Scanner scanner,
	ArrayList<Articulo> articulos,
	ArrayList<Categoria> categorias){


		System.out.println("--- INGRESAR ARTÍCULO ---");
		System.out.println("1 - Artículo electrónico");
		System.out.println("2 - Artículo alimenticio");
		
		int tipo;
        do {
            tipo = leerEntero(scanner, "Seleccione el tipo de artículo: ");

            if (tipo != 1 && tipo != 2) {
                System.out.println("Error: debe elegir 1 o 2.");
            }

        } while (tipo != 1 && tipo != 2);

		int codigo=  leerEnteroNoNegativo(scanner, "Ingrese el código del artículo: ");;

		if (buscarArticuloPorCodigo(articulos, codigo) != null) {
            System.out.println("Error: ya existe un artículo con ese código.");
            return;
        }


		String nombre = leerTextoNoVacio(scanner, "Ingrese el nombre del artículo: ");
        double precio = leerDoubleNoNegativo(scanner, "Ingrese el precio del artículo: ");

 		listarCategorias(categorias);
        Categoria categoria = pedirCategoriaExistente(scanner, categorias);

		Articulo articulo;
		  if (tipo == 1) {
            int garantiaMeses = leerEnteroNoNegativo(scanner, "Ingrese la garantía en meses: ");
            articulo = new ArticuloElectronico(codigo, nombre, precio, categoria, garantiaMeses);
        } else {
           
			int diasParaVencimiento = leerEnteroNoNegativo(scanner, "Ingrese los días para vencimiento: ");
            articulo = new ArticuloAlimenticio(codigo, nombre, precio, categoria, diasParaVencimiento);
        }

        articulos.add(articulo);

        System.out.println("Artículo ingresado correctamente.");
        System.out.println("Resumen del objeto creado:");
        System.out.println(articulo);

	}

	public static void listarArticulos(
	ArrayList<Articulo> articulos
	)
	{
		if(articulos.isEmpty()){
			System.out.println("No hay articulos cargados");
			return;
		}

		for(Articulo articulo : articulos)
			System.out.println(articulo);
		
	}

	public static void consultarArticulo(
	Scanner scanner,
	ArrayList<Articulo> articulos
	){
		if(articulos.isEmpty()){
			System.out.println("No hay articulos cargados");
			return;
		} 
			
		int codigo= leerEntero(scanner, "Ingrese el código del artículo: ");
		
		Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);
		if(articulo== null){
			System.out.println("El articulo no existe.");
		}
		else System.out.println(articulo);

	}

	public static void modificarArticulo(
	Scanner scanner,
	ArrayList<Articulo> articulos,
	ArrayList<Categoria> categorias
	){
		if(articulos.isEmpty()){
			System.out.println("No hay articulos cargados");
			return;
		}

		int codigo= leerEntero(scanner, "Ingrese el código del artículo: ");

		Articulo articulo = buscarArticuloPorCodigo(articulos,codigo);
		if(articulo== null){
			System.out.println("El articulo no existe.");
			return;
		}
		String nombre = leerTextoNoVacio(scanner, "Ingrese el nombre del artículo: ");
		double precio = leerDoubleNoNegativo(scanner, "Ingrese el precio del artículo: ");

		listarCategorias(categorias);
		Categoria categoria = pedirCategoriaExistente(scanner, categorias);
		
		articulo.setNombre(nombre);
		articulo.setPrecio(precio);
		articulo.setCategoria(categoria);

		if(articulo instanceof ArticuloAlimenticio){
			int diasParaVencimiento= leerEntero(scanner, "Ingrese los meses de garantia: ");
			ArticuloAlimenticio alimenticio = (ArticuloAlimenticio) articulo;
			alimenticio.setDiasParaVencimiento(diasParaVencimiento);
		}

		if (articulo instanceof ArticuloElectronico) {
            ArticuloElectronico electronico = (ArticuloElectronico) articulo;
            int garantia = leerEnteroNoNegativo(scanner, "Ingrese la nueva garantía en meses: ");
            electronico.setGarantiaMeses(garantia);
        }
		System.out.println("Artículo modificado correctamente.");

	}


	public static void eliminarArticulo(
	Scanner scanner,
	ArrayList<Articulo> articulos
	){ 
		if(articulos.isEmpty()){
			System.out.println("No hay articulos cargados");
			return;
		}
		int codigo= leerEntero(scanner, "Ingrese el código del artículo: ");
		Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        articulos.remove(articulo);
        System.out.println("Artículo eliminado correctamente.");
		
	}


	public static void listarCategorias(
		ArrayList<Categoria> categorias) {
        System.out.println("\n--- CATEGORÍAS DISPONIBLES ---");

        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }


// Metodos de Articulo


	public static Articulo buscarArticuloPorCodigo(
		ArrayList<Articulo> articulos, 
		int codigo) {
        for (Articulo articulo : articulos) {
            if (articulo.getCodigo() == codigo) {
                return articulo;
            }
        }
        return null;
    }

	// Metodos de categorias

	public static Categoria buscarCategoriaPorCodigo(
		ArrayList<Categoria> categorias, 
		int codigo) {
        for (Categoria categoria : categorias) {
            if (categoria.getCodigo() == codigo) {
                return categoria;
            }
        }
        return null;
    }

	public static Categoria pedirCategoriaExistente(
		Scanner scanner, 
		ArrayList<Categoria> categorias) {
        while (true) {
            int codigoCategoria = leerEntero(scanner, "Ingrese el código de la categoría: ");

            Categoria categoria = buscarCategoriaPorCodigo(categorias, codigoCategoria);

            if (categoria != null) {
                return categoria;
            }

            System.out.println("Error: la categoría no existe.");
        }
    }

//Metodos de lectura

    public static int leerEntero(Scanner scanner, String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número entero válido.");
            }
        }
    }

	public static int leerEnteroNoNegativo(Scanner scanner, String mensaje) {
        while (true) {
            int valor = leerEntero(scanner, mensaje);

            if (valor < 0) {
                System.out.println("Error: el valor no puede ser negativo.");
                continue;
            }

            return valor;
		}
	}

  
/* Metodos de lectura  */
	public static double leerDoubleNoNegativo(Scanner scanner, String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);
                double valor = Double.parseDouble(scanner.nextLine());

                if (valor < 0) {
                    System.out.println("Error: el precio no puede ser negativo.");
                    continue;
                }
				if (!Double.isFinite(valor)) {
    				System.out.println("Error: el valor es invalido");
					continue;
				}
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número decimal válido.");
            }
        }
    }


   public static String leerTextoNoVacio(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();

            if (!texto.trim().isEmpty()) {
                return texto.trim();
            }

            System.out.println("Error: el texto no puede estar vacío.");
        }
	}

	



}
