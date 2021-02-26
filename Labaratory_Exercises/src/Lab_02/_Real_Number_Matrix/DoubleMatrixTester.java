package Lab_02._Real_Number_Matrix;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

class InsufficientElementsException extends RuntimeException {
    public InsufficientElementsException() {
        super("Insufficient number of elements");
    }
}

class InvalidRowNumberException extends RuntimeException {
    public InvalidRowNumberException() {
        super("Invalid row number");
    }
}

class InvalidColumnNumberException extends RuntimeException {
    public InvalidColumnNumberException() {
        super("Invalid column number");
    }
}

class DoubleMatrix {
    double[][] matrix;

    public DoubleMatrix(double a[], int m, int n) {

        int brojac = 0;
        boolean reverse = false;

        if((m*n) > a.length)
            throw new InsufficientElementsException();

        if((m*n) < a.length) {
            brojac = a.length-1;
            reverse = true;
        }

        this.matrix = new double[m][n];

        if (reverse) {
            for(int i=m-1;i>=0;i--)
                for(int j=n-1;j>=0;j--)
                    matrix[i][j] = a[brojac--];
        }
        else {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = a[brojac++];
                }
            }
        }

    }

    public String getDimensions() {
        return "[" + matrix.length + " x " + matrix[0].length + "]";
    }

    public int rows() {
        return matrix.length;
    }

    public int columns() {
        return matrix[0].length;
    }

    public double maxElementAtRow(int row) {

        if(row <= 0 || matrix.length < (row))
            throw new InvalidRowNumberException();

        double max = matrix[row-1][0];
        for(int i=1;i<matrix[row-1].length;i++) {
            if(matrix[row-1][i] >= max)
                max = matrix[row-1][i];
        }
        return max;
    }

    public double maxElementAtColumn(int column) {
        if(column <= 0 || matrix[0].length < (column))
            throw new InvalidColumnNumberException();

        double max = matrix[0][column-1];
        for(int i=1;i<matrix.length;i++) {
            if(matrix[i][column-1] >= max)
                max = matrix[i][column-1];
        }
        return max;
    }

    public double sum() {
        double sum=0.0;
        for (double[] doubles : matrix)
            for (double aDouble : doubles) sum += aDouble;
        return sum;
    }

    public double[] toSortedArray() {
        double[] tmp = new double[matrix.length * matrix[0].length];
        int brojac = 0;
        for (double[] doubles : matrix)
            for (int j = 0; j < matrix[0].length; j++)
                tmp[brojac++] = doubles[j];

        int n = tmp.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (tmp[j] < tmp[j + 1]) {
                    double temp = tmp[j];
                    tmp[j] = tmp[j + 1];
                    tmp[j + 1] = temp;
                }

        return tmp;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    @Override
    public boolean equals(Object o) {
        double[][] toCompareMatrix = ((DoubleMatrix) o).getMatrix();
        if((toCompareMatrix.length != matrix.length) ||
                (toCompareMatrix[0].length!=matrix[0].length))
            return false;

        for(int i=0;i<matrix.length;i++)
            for (int j=0;j<matrix[0].length;j++)
                if(matrix[i][j] != toCompareMatrix[i][j])
                    return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (matrix.length*30) % 2;
    }

    @Override
    public String toString() {
        StringBuilder fullString = new StringBuilder();
        for(int i=0;i<matrix.length;i++)
        {
            for(int j=0;j<matrix[i].length;j++) {
                if(j == matrix[i].length-1)
                    fullString.append(String.format("%.2f", matrix[i][j]));
                else
                    fullString.append(String.format("%.2f\t", matrix[i][j]));
            }
            if(matrix.length-1 != i)
                fullString.append("\n");
        }
        return fullString.toString();
    }
}

class MatrixReader {
    public static DoubleMatrix read(InputStream input) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String[] parts = br.readLine().split(" ");
        int m = Integer.parseInt(parts[0]);
        int n = Integer.parseInt(parts[1]);
        double[] matrix = new double[m*n];
        int brojac = 0;
        for(int i=0;i<m;i++) {
            String[] numbers = br.readLine().split("\\s+");
            for(int j=0;j<numbers.length;j++)
                matrix[brojac++] = Double.parseDouble(numbers[j]);
        }
        return new DoubleMatrix(matrix, m, n);
    }
}

public class DoubleMatrixTester {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        DoubleMatrix fm = null;

        double[] info = null;

        DecimalFormat format = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            String operation = scanner.next();

            switch (operation) {
                case "READ": {
                    int N = scanner.nextInt();
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    double[] f = new double[N];

                    for (int i = 0; i < f.length; i++)
                        f[i] = scanner.nextDouble();

                    try {
                        fm = new DoubleMatrix(f, R, C);
                        info = Arrays.copyOf(f, f.length);

                    } catch (InsufficientElementsException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }

                    break;
                }

                case "INPUT_TEST": {
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    StringBuilder sb = new StringBuilder();

                    sb.append(R + " " + C + "\n");

                    scanner.nextLine();

                    for (int i = 0; i < R; i++)
                        sb.append(scanner.nextLine() + "\n");

                    fm = MatrixReader.read(new ByteArrayInputStream(sb
                            .toString().getBytes()));

                    info = new double[R * C];
                    Scanner tempScanner = new Scanner(new ByteArrayInputStream(sb
                            .toString().getBytes()));
                    tempScanner.nextDouble();
                    tempScanner.nextDouble();
                    for (int z = 0; z < R * C; z++) {
                        info[z] = tempScanner.nextDouble();
                    }

                    tempScanner.close();

                    break;
                }

                case "PRINT": {
                    System.out.println(fm.toString());
                    break;
                }

                case "DIMENSION": {
                    System.out.println("Dimensions: " + fm.getDimensions());
                    break;
                }

                case "COUNT_ROWS": {
                    System.out.println("Rows: " + fm.rows());
                    break;
                }

                case "COUNT_COLUMNS": {
                    System.out.println("Columns: " + fm.columns());
                    break;
                }

                case "MAX_IN_ROW": {
                    int row = scanner.nextInt();
                    try {
                        System.out.println("Max in row: "
                                + format.format(fm.maxElementAtRow(row)));
                    } catch (InvalidRowNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "MAX_IN_COLUMN": {
                    int col = scanner.nextInt();
                    try {
                        System.out.println("Max in column: "
                                + format.format(fm.maxElementAtColumn(col)));
                    } catch (InvalidColumnNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "SUM": {
                    System.out.println("Sum: " + format.format(fm.sum()));
                    break;
                }

                case "CHECK_EQUALS": {
                    int val = scanner.nextInt();

                    int maxOps = val % 7;

                    for (int z = 0; z < maxOps; z++) {
                        double work[] = Arrays.copyOf(info, info.length);

                        int e1 = (31 * z + 7 * val + 3 * maxOps) % info.length;
                        int e2 = (17 * z + 3 * val + 7 * maxOps) % info.length;

                        if (e1 > e2) {
                            double temp = work[e1];
                            work[e1] = work[e2];
                            work[e2] = temp;
                        }

                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(work, fm.rows(),
                                fm.columns());
                        System.out
                                .println("Equals check 1: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    if (maxOps % 2 == 0) {
                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(new double[]{3.0, 5.0,
                                7.5}, 1, 1);

                        System.out
                                .println("Equals check 2: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    break;
                }

                case "SORTED_ARRAY": {
                    double[] arr = fm.toSortedArray();

                    String arrayString = "[";

                    if (arr.length > 0)
                        arrayString += format.format(arr[0]) + "";

                    for (int i = 1; i < arr.length; i++)
                        arrayString += ", " + format.format(arr[i]);

                    arrayString += "]";

                    System.out.println("Sorted array: " + arrayString);
                    break;
                }

            }

        }

        scanner.close();
    }
}