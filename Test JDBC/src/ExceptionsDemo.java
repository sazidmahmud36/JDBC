

public class ExceptionsDemo {
    public static void divisionDemo(int divided, int divisor) throws ArithmeticException{
        System.out.println("The result is : "+ divided/divisor);
    }
    public static void main(String[] args) {
        divisionDemo(10,0);



    }
}
