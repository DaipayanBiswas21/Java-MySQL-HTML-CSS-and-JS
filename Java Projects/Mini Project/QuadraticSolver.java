import java.util.Scanner;

public class QuadraticSolver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Input coefficients
        System.out.print("Enter coefficient a: ");
        double a = in.nextDouble();
        System.out.print("Enter coefficient b: ");
        double b = in.nextDouble();
        System.out.print("Enter coefficient c: ");
        double c = in.nextDouble();

        // Calculate discriminant
        double discriminant = b * b - 4 * a * c;

        System.out.println("Equation: " + a + "x² + " + b + "x + " + c + " = 0");

        if (a == 0) {
            System.out.println("Not a quadratic equation (a cannot be 0).");
        } else if (discriminant > 0) {
            // Two real and distinct roots
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            System.out.println("Roots are real and distinct:");
            System.out.println("Root 1: " + root1);
            System.out.println("Root 2: " + root2);
        } else if (discriminant == 0) {
            // One real and equal root
            double root = -b / (2 * a);
            System.out.println("Roots are real and equal:");
            System.out.println("Root: " + root);
        } else {
            // Complex roots
            double realPart = -b / (2 * a);
            double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);
            System.out.println("Roots are complex and imaginary:");
            System.out.println("Root 1: " + realPart + " + " + imaginaryPart + "i");
            System.out.println("Root 2: " + realPart + " - " + imaginaryPart + "i");
        }
    }
}
