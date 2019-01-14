public class NotInAValidIntervalException extends Exception {

    public NotInAValidIntervalException(String message) {
        super(message);
    }

    public NotInAValidIntervalException() {
        System.out.println("Minimo de 2 caracteres e máximo de 30 necessarios");
    }
}
