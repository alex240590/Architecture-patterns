package ru.mvc;

public class Figures {
    public int size;

    public Figures(int size) {
        this.size = size;
    }

    public int countLength() {
        return 0;
    }
}

public class Round extends Figures {
    public int size;

    public Round(int size, int size1) {
        super(size);
        this.size = size1;
    }

    @Override
    public int countLength(){
        return (int) (3.14*size);
    }
}

public class Triangle extends Figures {
    public int size;

    public Triangle(int size, int size1) {
        super(size);
        this.size = size1;
    }

    @Override
    public int countLength(){
        return 3*size;
    }
}

public class Square extends Figures {
    public int size;

    public Square(int size, int size1) {
        super(size);
        this.size = size1;
    }

    @Override
    public int countLength(){
        return 4*size;
    }
}
