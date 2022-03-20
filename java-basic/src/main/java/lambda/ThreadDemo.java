package lambda;

public class ThreadDemo {

    public static void main (String[] args) {

        Runnable runnable = new Runnable() {
            @Override
            public void run () {
                System.out.println("ok");
            }
        };
        new Thread(runnable).start();

        // lambda
        new Thread(()-> System.out.println("ok")).start();
    }
}
