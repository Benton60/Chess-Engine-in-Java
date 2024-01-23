public class Main{
    public static void main(String[] Args){
        API api = new API();
        Thread run = new Thread(api);
        run.start();
    }
}