package com.pl.serializable;

public class NoRecord extends Exception
{
    public NoRecord  ()
    {
        super();
    }

    public NoRecord  (String error)
    {
        super(error);
    }
}
