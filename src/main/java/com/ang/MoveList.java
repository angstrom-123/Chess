package com.ang;

public class MoveList {
    private int[] elements; // regular moves
    private boolean[] specElements; // special moves (castling, en-passant)
    private int end = 0;

    public MoveList(int maxElements) {
        elements = new int[maxElements];
        specElements = new boolean[maxElements];
        for (int i = 0; i < maxElements; i++) {
            elements[i] = -1;
            specElements[i] = false;
        }
    }

    public void add(MoveList list) {
        
    }

    public void add(int pos) {
        elements[end] = pos;
        end++;
    }

    public void addSpec(int pos) {
        specElements[end] = true;
        add(pos);
    }

    public int length() {
        return end + 1;
    }

    public int at(int index) {
        return elements[index];
    }

    public boolean contains(int val) {
        for (int element : elements) {
            if (element == val) {
                return true;
            }
        }
        return false;
    }

    public boolean isSpecial(int index) {
        return (specElements[index]);
    }
}
