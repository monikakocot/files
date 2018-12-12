package com.pl.tokens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class Product {
    private double price;
    private String name;
    private Date releaseDate;

    public Product(){
        this.price = 0.0;
        this.name = " ";
        this.releaseDate = new GregorianCalendar().getTime();
    }
    public Product(double price, String name)
    {
        this();
        this.price = price;
        this.name = name;
    }
    public Product(double price, String nazwa, int rok, int m, int dz)
    {
        this(price, nazwa);
        GregorianCalendar kalendarz = new GregorianCalendar(rok, m-1, dz);
        this.releaseDate = kalendarz.getTime();
    }

    public static void saveToFile(Product[] product, PrintWriter outS)
    {
        outS.println(product.length);
        GregorianCalendar calendar = new GregorianCalendar();
        for (int i = 0; i < product.length; i++)
        {
//setting the time for each product on a regular basis after each passing of the loop
            calendar.setTime(product[i].getReleaseDate());
            outS.println(product[i].getPrice()+"|"+product[i].getName()+"|"+calendar.get(Calendar.YEAR)+"|"+(calendar.get(Calendar.MONTH)+1)+"|"+calendar.get(Calendar.DAY_OF_MONTH));
        }
    }

    public static Product[] readFromFile(BufferedReader inS) throws IOException
    {
        int dl = Integer.parseInt(inS.readLine());
        Product[] product = new Product[dl];

        for (int i = 0; i < dl; i++)
        {
            String line = inS.readLine();
//tokens, broken down into tokens - token to Sting between "|" for each 'line'

            StringTokenizer tokens = new StringTokenizer(line, "|");

            double price = Double.parseDouble(tokens.nextToken());
            String name = tokens.nextToken();
            int rok = Integer.parseInt(tokens.nextToken());
            int m = Integer.parseInt(tokens.nextToken());
            int dz = Integer.parseInt(tokens.nextToken());

            product[i] = new Product(price, name, rok, m, dz);
        }
        return product;
    }

// GETTERY SETTERY TO STRING


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int r, int m, int dz)
    {
        GregorianCalendar calendar = new GregorianCalendar(r, m-1, dz);
        this.releaseDate = calendar.getTime();
    }
    public void setReleaseDate(Date data)
    {
        this.releaseDate = data;
    }
    @Override
    public String toString()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(this.releaseDate);
        return this.price+" zł; name: "+this.name+" "+calendar.get(Calendar.YEAR)+" year "+(calendar.get(Calendar.MONTH)+1)+" m "+calendar.get(Calendar.DAY_OF_MONTH)+" dz ";
    }

}
