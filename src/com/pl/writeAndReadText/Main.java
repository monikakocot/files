package com.pl.writeAndReadText;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        try {
//WRITTING
            PrintWriter printer = new PrintWriter(new FileWriter("dane.txt"));

//new PrintWriter - only saving
//new FileWriter - read save and create new .txt

            printer.println(1234);
            // printer.flush(); // // emptying the buffer and throwing it to a file
            printer.close();
// true -  adding and not overwriting
            printer = new PrintWriter(new FileWriter("dane.txt", true));

            printer.append("lalalalala");
            printer.println();
//printf - print formated text
            printer.printf("She has %d kg and %.2f %s height", 50, 165.456, "cm");

            printer.close();
//READING
            BufferedReader reader = new BufferedReader(new FileReader("dane.txt"));

// System.out.println((char)reader.read());
// System.out.println(reader.readLine());

            System.out.println(reader.readLine());
            System.out.println(reader.readLine());
            System.out.println(reader.readLine());

// 'Cleaning' dane.txt
            BufferedWriter writer = new BufferedWriter(new FileWriter("dane.txt"));

            String content = "";
            while((content = reader.readLine()) != null){

                writer.write(content);
                writer.newLine();
                System.out.println(content);
            }

            writer.close();
            reader.close();
        }

        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }

        //Main.test(1, 2, 4, 125, "lal", 4);
    }

    static void test(Object... a)
    {

        for (int i = 0; i < a.length; i++)
        {
            System.out.println(a[i]);
        }

    }
}

