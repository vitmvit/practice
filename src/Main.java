import exchange.ExchangeOperator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите выражение: ");
        String line = bufferedReader.readLine();
        String result = new ExchangeOperator().exchangeOperation(line);
        System.out.println(result);
    }
}