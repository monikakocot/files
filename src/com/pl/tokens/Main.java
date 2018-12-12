package com.pl.tokens;

import java.io.*;

public class Main
{
    public static void main(String[] args)
    {
        Product[] products = new Product[3];

        products[0] = new Product();
        products[1] = new Product(29.0, "Video Kurs Java");
        products[2] = new Product(39.0, "Video Kurs C++", 2008, 11, 21);

        try
        {
            PrintWriter writer = new PrintWriter(new FileWriter("token.txt"));

            Product.saveToFile(products, writer);

            writer.close();

            BufferedReader reader = new BufferedReader(new FileReader("token.txt"));

            Product[] towar2 = Product.readFromFile(reader);

            for (int i = 0; i < towar2.length; i++)
                System.out.println(towar2[i]);

            reader.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
