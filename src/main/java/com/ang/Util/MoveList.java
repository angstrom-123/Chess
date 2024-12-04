package com.ang.Util;

public class MoveList {
    private int origin;

    private int[] elements; // regular moves
    private int doublePush = -1;
    private int castleShort = -1;
    private int castleLong = -1;
    private int enPassant = -1;
    private int promotion = -1;

    private int end = 0;

    public MoveList() {};

    public MoveList(int maxElements, int origin) {
        this.origin = origin;

        elements = new int[maxElements];
        for (int i = 0; i < maxElements; i++) {
            elements[i] = -1;
        }
    }

    public int getFrom() {
        return origin;
    }

    public void add(MoveList list) {
        for (int i = end; i < list.length() - 1; i++) {
            elements[i] = list.at(i);
        }
    }

    public void add(int pos) {
        elements[end] = pos;
        end++;
    }

    public void addSpec(int pos, SpecialMove spec) {
        switch (spec) {
            case DOUBLE_PUSH:
                doublePush = pos;
                break;
            case CASTLE_LONG:
                castleLong = pos;
                break;
            case CASTLE_SHORT:
                castleShort = pos;
                break;
            case EN_PASSANT:
                enPassant = pos;
                break;
            case PROMOTION:
                promotion = pos;
                break;
            default:
                break;
        }
        add(pos);
    }

    public int getSpecialMove(SpecialMove move) {
        switch (move) {
            case DOUBLE_PUSH:
                return doublePush;
            case CASTLE_LONG:
                return castleLong;
            case CASTLE_SHORT:
                return castleShort;
            case EN_PASSANT:
                return enPassant;
            case PROMOTION:
                return promotion;
            default:
                return -1;
        }
    }

    public int length() {
        return end + 1;
    }

    public int at(int index) {
        return elements[index];
    }

    public boolean contains(int val) {
        if (end == 0) {
            return false;
        }
        
        for (int element : elements) {
            if (element == val) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(int val) {
        for (int i = 0; i < length() - 1; i++) {
            if (elements[i] == val) {
                return i;
            }
        }
        return -1;
    }
}
