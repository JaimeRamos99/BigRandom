package diferidolab1complejidad;

import java.awt.Event;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Scanner;

/**
 *
 * @author Jaime
 */
public class Diferidolab1complejidad {

    public static void merge(Object arr[][], int l, int m, int r, int c) {
        // Find sizes of two subarrays to be merged 
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        Object L[][] = new Object[n1][c];
        Object R[][] = new Object[n2][c];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i) {
            for (int x = 0; x < c; ++x) {
                L[i][x] = arr[l + i][x];

            }
        }
        for (int j = 0; j < n2; ++j) {
            for (int z = 0; z < c; ++z) {
                R[j][z] = arr[m + 1 + j][z];
            }
        }
        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays 
        int i = 0, j = 0;

        // Initial index of merged subarry array 
        int k = l;
        BigInteger e;
        while (i < n1 && j < n2) {
            e = (BigInteger) L[i][0];
            if (e.compareTo((BigInteger) R[j][0]) <= 0) {
                for (int z = 0; z < c; ++z) {
                    arr[k][z] = L[i][z];
                }
                i++;
            } else {
                for (int z = 0; z < c; ++z) {
                    arr[k][z] = R[j][z];
                }
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            for (int z = 0; z < c; ++z) {
                arr[k][z] = L[i][z];
            }
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            for (int z = 0; z < c; ++z) {
                arr[k][z] = R[j][z];
            }
            j++;
            k++;
        }
    }

    public static void sort(Object arr[][], int l, int r, int c) {
        if (l < r) {
            // Find the middle point 
            int m = (l + r) / 2;

            // Sort first and second halves 
            sort(arr, l, m, c);
            sort(arr, m + 1, r, c);

            // Merge the sorted halves 
            merge(arr, l, m, r, c);
        }
    }

    public static Object[][]generador() throws FileNotFoundException, IOException {
        System.out.println("Digite el valor de k");
        int k = sc.nextInt();
        int i = (int) (Math.random() * 50) + 1;//cantidad de columnas
        int vector[] = tipo(i);//para saber el tipo de cada columna
        System.out.println("Hay " + i + " columnas.");
        System.out.print("numerico |");
        for (int j = 1; j < i; j++) {
            if (vector[j] == 0) {
                System.out.print("alfabetico | ");
            } else {
                System.out.print("numerico | ");
            }

        }
        System.out.println(" ");
        Object m1000[][] = nuevoarchivo(1000, i, vector, k, "t");
        Object m2000[][] = nuevoarchivo(2000, i, vector, k, "n");
        Object mm[][] = new Object[3000][i];
       long stat=System.nanoTime();
        for (int p = 0; p < 1000; p++) {
            for (int j = 0; j < i; j++) {
                mm[p][j] = m1000[p][j];
            }
        }
        for (int p = 0; p < 2000; p++) {
            for (int j = 0; j < i; j++) {
                mm[p + 1000][j] = m2000[p][j];
            }
        }
        sort(mm, 0, mm.length-1, mm[0].length);
        long ed=System.nanoTime();
         System.out.println("El tiempo para la mezcla fue :"+(ed-stat)+" nanosegundos");
        long start=System.nanoTime();
        creararchivo(mm, "Mezcla_Ordenada_n_t.txt");
        long end=System.nanoTime();
        System.out.println("El tiempo de direccionamiento para la mezcla fue: "+(end-start)+" nanosegundos");
        System.out.println(" ");
        System.out.println("El archivo de 1000 registros se encuentra en t.txt y está ordenado en Ordenado_t.txt");
        System.out.println("El archivo de 2000 registros se encuentra en n.txt y está ordenado en Ordenado_n.txt");
        System.out.println("Y el archivo mezclado y ordenado de estos 2 se encuentra en Mezcla_Ordenada_n_t.txt");
        return mm;
    }

    public static Object [][]generador2() throws FileNotFoundException, IOException {
        try {
            System.out.println("Digite el nombre del primer archivo a leer, recuerde que dicho archivo debe estar situado en la carpeta de este proyecto");
            name = sc.nextLine();
            File lectura = new File(name + ".txt");
            FileReader fr = new FileReader(lectura);
            BufferedReader br = new BufferedReader(fr);
            String Line;
            Object m[][] = new Object[cantidadelineas(lectura)][cantidadecolumnas(lectura)];
            int counter = 0;
            long start = System.nanoTime();
            while (br.ready()) {
                Line = br.readLine();
                if (Line.contains(";")) {
                    m = direccionamientoarchivo(m, Line, counter);
                    counter++;
                }
            }
            long c=System.nanoTime();
            sort(m, 0, m.length - 1, m[0].length);
            long f=System.nanoTime();
            System.out.println("el tiempo de ordenamiento de n fue: "+(f-c)+" nanosegundos");
            creararchivo(m, "Ordenado_n");
            long end = System.nanoTime();
            tiempodireccionamiento = end - start;
            System.out.println("El tiempo de direccionamiento del primer archivo fue: " + tiempodireccionamiento);
            System.out.println("Digite el nombre del segundo archivo a leer, recuerde que dicho archivo debe estar situado en la carpeta de este proyecto");
            name2 = sc.nextLine();
            File lectura2 = new File(name2 + ".txt");
            FileReader fr2 = new FileReader(lectura2);
            BufferedReader br2 = new BufferedReader(fr2);
            Line = "";
            Object m2[][] = new Object[cantidadelineas(lectura2)][cantidadecolumnas(lectura2)];
            counter = 0;
            start = System.nanoTime();
            while (br2.ready()) {
                Line = br2.readLine();
                if (Line.contains(";")) {
                    m2 = direccionamientoarchivo(m2, Line, counter);
                    counter++;
                }
            }
            long c2=System.nanoTime();
            sort(m2, 0, m2.length - 1, m2[0].length);
            long f2=System.nanoTime();
            System.out.println("el tiempo de ordenamiento de t fue: "+(f2-c2)+" nanosegundos");
            creararchivo(m2, "Ordenado_t");
            end = System.nanoTime();
            tiempodireccionamiento = end - start;
            System.out.println("El tiempo de direccionamiento del segundo archivo fue: " + tiempodireccionamiento);
            Object m3[][] = new Object[cantidadelineas(lectura2) + cantidadelineas(lectura)][cantidadecolumnas(lectura2)];
            
            for (int i = 0; i < m2.length; i++) {
                for (int j = 0; j < m2[0].length; j++) {
                    m3[i][j] = m2[i][j];
                }
            }
            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m[0].length; j++) {
                    m3[i + m2.length][j] = m2[i][j];
                }
            }
            long st=System.nanoTime();
            sort(m3, 0, m3.length - 1, m3[0].length);
            long en=System.nanoTime();
            System.out.println("El tiempo de mezcla fue: "+(en-st)+" nanosegundos");
            long s=System.nanoTime();
            creararchivo(m3, "Mezcla_Ordenada_n_t.txt");
            long e=System.nanoTime();
            System.out.println("El tiempo de direccionamiento de la mezcla fue: "+(e-s)+" nanosegundos");
            System.out.println("El archivo " + name + " está organizado en el archivo " + name + "Ordenado_n.txt");
            System.out.println("El archivo " + name2 + " está organizado en el archivo " + name2 + "Ordenado_t.txt");
            System.out.println("Los archivos están mezclados y organizados en el archivo Mezcla_Ordenada_n_t.txt ");
            return m3;
        } catch (FileNotFoundException ex) {
            System.out.println("No se puede encontrar el archivo especificado.");
        }
       return null;
    }

    public static int cantidadelineas(File f) throws IOException {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        int counter = 0;
        while (br.ready()) {
            if (br.readLine().contains(";")) {
                counter++;
            }
        }
        return counter;
    }

    public static int cantidadecolumnas(File f) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        if (br.ready()) {
            return conteo(br.readLine());
        }
        return 0;
    }

    public static int conteo(String linea) {
        int c = 0;
        for (int i = 0; i < linea.length(); i++) {
            if (linea.substring(i, i + 1).equals(";")) {
                c++;
            }
        }
        return c;
    }

    public static Object[][] direccionamientoarchivo(Object[][] m, String l, int i) {
        int columna = 0;
        Object o = "";
        for (int j = 0; j < l.length(); j++) {
            if (l.substring(j, j + 1).equals(";")) {
                try {
                    BigInteger b = new BigInteger("" + o);
                    m[i][columna] = b;
                } catch (NumberFormatException ex) {
                    m[i][columna] = o;
                }
                o = "";
                columna++;
            } else {
                o = o + l.substring(j, j + 1);
            }

        }
        return m;
    }

    public static void creararchivo(Object[][] matriz, String nomb) throws IOException {
        File f = new File(nomb + ".txt");
        FileWriter fw = new FileWriter(f, false);
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    bw.write(matriz[i][j] + ";");
                }
                bw.newLine();
                bw.newLine();
            }
        }
    }

    public static int[] tipo(int col) {//genera un vector de i casillas las cuales contendrán 0 o 1, donde 0 significa alfabetico 
        int vector[] = new int[col];
        for (int j = 1; j < col; j++) {
            vector[j] = (int) (Math.random() * 2);
        }
        return vector;
    }

    public static String numeros(int k) {
        String result = "";
        for (int i = 0; i < k; i++) {
            result = result + (int) (Math.random() * 10);
        }
        BigInteger ss = new BigInteger("" + result);
        return ss + "";
    }

    public static String letra(int k) {
        String result = "";
        for (int i = 0; i < k; i++) {
            result = result + letras[(int) (Math.random() * 26) + 1];
        }
        return result;
    }

    public static Object[][] nuevoarchivo(int filas, int columnas, int[] vector, int k, String nombre) throws IOException {
        File f = new File(nombre + ".txt");
        FileWriter fw = new FileWriter(f, false);
        String todo;
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            for (int j = 0; j < filas; j++) {
                todo = "";
                for (int l = 0; l < columnas; l++) {
                    if (l == 0) {
                        todo = todo + ((int) (Math.random() * 999) + 1) + ";";
                    } else {
                        if (vector[l] == 0) {

                            todo = todo + letra(k) + ";";

                        } else {

                            todo = todo + numeros(k) + ";";

                        }
                    }
                }
                bw.write(todo);
                if (j < filas - 1) {
                    bw.newLine();
                    bw.newLine();
                }

            }
            bw.close();
        }
        fw.close();
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String Line;
        Object m[][] = new Object[cantidadelineas(f)][cantidadecolumnas(f)];
        int counter = 0;
        long start = System.nanoTime();
        while (br.ready()) {
            Line = br.readLine();
            if (Line.contains(";")) {
                m = direccionamientoarchivo(m, Line, counter);
                counter++;
            }
        }
         long end = System.nanoTime();
         System.out.println("El tiempo de direccionamiento de "+nombre+" fue: "+(end -start)+" nanosegundos");
         start = System.nanoTime();
        sort(m, 0, m.length - 1, m[0].length);
        end = System.nanoTime();
        System.out.println("El tiempo de ordenamiento de "+nombre +" fue: "+(end -start)+ " nanosegundos");
        creararchivo(m, "Ordenado_" + nombre);
        return m;
    }
    public static void extremo(Object[][] matriz) {//donde es 1 si es el maximo y 0 si es el minimo
        long start, end;
        sc.nextLine();
        System.out.println("Digite 1 si desea buscar el valor máximo de un conjunto de registros, si desea buscar el mínimo digite 0");
        int binario = sc.nextInt();
        sc.nextLine();
        System.out.println("Digite el numero del campo");
        BigInteger extremo = new BigInteger("" + BigInteger.ONE);
        String ex = "";
        int camp = sc.nextInt();

        if (camp <= matriz[0].length && camp > 0) {
            start = System.nanoTime();
            if (binario == 1) {
                if (matriz[0][camp - 1].getClass().getSimpleName().equals("BigInteger")) {
                    for (int j = 0; j < matriz.length; j++) {

                        if (j == 0) {
                            extremo = (BigInteger) matriz[j][camp - 1];

                        } else {

                            if ((extremo.compareTo((BigInteger) matriz[j][camp - 1])) < 0) {
                                extremo = (BigInteger) matriz[j][camp - 1];
                            }
                        }
                    }
                    System.out.println("El mayor registro es: " + extremo);
                } else {
                    for (int j = 0; j < matriz.length; j++) {
                        if (j == 0) {
                            ex = (String) matriz[j][camp - 1];
                        } else {
                            if ((ex.compareTo((String) matriz[j][camp - 1])) == -1) {
                                ex = (String) matriz[j][camp - 1];
                            }
                        }
                    }
                    System.out.println("El mayor registro es: " + ex);
                }

            } else {
                if (matriz[0][camp - 1].getClass().getSimpleName().equals("BigInteger")) {
                    for (int j = 0; j < matriz.length; j++) {
                        if (j == 0) {
                            extremo = (BigInteger) matriz[j][camp - 1];
                        } else {
                            if ((extremo.compareTo((BigInteger) matriz[j][camp - 1])) == 1) {
                                extremo = (BigInteger) matriz[j][camp - 1];
                            }
                        }
                    }
                    System.out.println("El menor registro es: " + extremo);
                } else {
                    for (int j = 0; j < matriz.length; j++) {
                        if (j == 0) {
                            ex = (String) matriz[j][camp - 1];
                        } else {
                            if ((ex.compareTo((String) matriz[j][camp - 1])) == 1) {
                                ex = (String) matriz[j][camp - 1];
                            }
                        }
                    }
                    System.out.println("El menor registro es: " + ex);
                }
            }
            end = System.nanoTime();
            System.out.println("El tiempo de para buscar un extremo fue: "+(end-start)+" nanosegundos");
        } else {
            System.out.println("No existe ese número de columna");
        }

    }
    public static void promedio(Object[][] matriz) {
        long start, end;
        start = System.nanoTime();
        sc.nextLine();
        System.out.println("Digite el numero del campo al cual se le calculará el promedio");
        int colum = sc.nextInt();
        if (colum <= matriz[0].length && colum > 0) {
            BigInteger prom = new BigInteger("" + BigInteger.ZERO);
            if (matriz[0][colum - 1].getClass().getSimpleName().equals("BigInteger")) {
                for (int i = 0; i < matriz.length; i++) {
                    prom = prom.add((BigInteger) matriz[i][colum - 1]);
                }
                BigDecimal a = new BigDecimal("" + prom);
                BigDecimal b = new BigDecimal(new BigInteger("" + matriz.length));
                BigDecimal c = new BigDecimal("" + BigDecimal.ONE);
                c = a.divide(b,MathContext.DECIMAL128);
                System.out.println("El promedio del campo " + colum + " es:" + c);
            } else {
                System.out.println("El campo no es numerico");
            }
        } else {
            System.out.println("No existe ese número de columna");
        }
        end = System.nanoTime();
        System.out.println("El tiempo para sacar el promdeio fue "+(end-start)+" nanosegundos");
    }
    public static void moda(Object[][] matriz) {
        long s, e;
        s = System.nanoTime();
        sc.nextLine();
        System.out.println("Digite el numero del campo para el cual desea hallar la moda");
        int co = sc.nextInt();
        int repeticiones = 0, max = 0, posmax = 0;
        String elemento;
        for (int j = 0; j < matriz.length; j++) {
            repeticiones = 0;
            elemento = matriz[j][co - 1].toString();
            for (int i = 0; i < matriz.length; i++) {
                if (elemento.equals(matriz[i][co - 1].toString())) {
                    repeticiones++;
                }
            }
            if (j == 0) {
                max = repeticiones;
                posmax = 0;
            } else {
                if (max < repeticiones) {
                    max = repeticiones;
                    posmax = j;
                }
            }
        }
        e = System.nanoTime();
        System.out.println("El tiempo para sacara la moda fue "+(e -s)+" nanosegundos");
        System.out.println("El elemento con más repeticiones del campo " + co + " es: " + matriz[posmax][co - 1] + " con " + max + " repeticiones.");
    }
    public static void main(String[] args) throws IOException {
        Object matriz[][];
        Object mama3[][]=null;
        System.out.println("Laboratorio 1 de algoritmos y complejidad" + newline + "Nombre:Jaime Ramos" + newline + "Código:200104583" + newline + "Fecha:01/04/2019");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Digite 1 para trabajar con archivos predeterminados o 2 para generarlos aleatoriamente.");
        int op = sc.nextInt();
        if (op == 1) {
            sc.nextLine();
           mama3= generador2();
        } else {
            if (op == 2) {
                mama3= generador();
            } else {
                System.out.println("digite una opción válida");
            }
        }
        System.out.println("Digite 1 para hallar máximos o minimos"+ newline+" Digite 2 para ver la moda"+newline+"Digite 3 para ver el promedio");
        int respuestaa=sc.nextInt();
        if(respuestaa==1){
            extremo(mama3);
        }else{
            if(respuestaa==2){
                moda(mama3);
            }else{
                promedio(mama3);
            }
        }
        
    }
    static long tiempodireccionamiento = -1;
    static Scanner sc = new Scanner(System.in);
    static String newline = System.lineSeparator(), name, name2;
    static int arr[][] = new int[4][2];
    static String[] letras = {" ", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
}
