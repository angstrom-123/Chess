package com.ang.Util;

public class MoveList {
    private int origin;

    private Move[] elements;
    private Move doublePush = Move.invalid();
    private Move castleShort = Move.invalid();
    private Move castleLong = Move.invalid();
    private Move enPassant = Move.invalid();
    private Move promotion = Move.invalid();

    private int end = 0;

    public MoveList() {};

    public MoveList(int maxElements) {
        elements = new Move[maxElements];
        for (int i = 0; i < maxElements; i++) {
            elements[i] = Move.invalid();
        }
    }

    public int getFrom() {
        return origin;
    }

    public void add(MoveList list) {
        for (int i = 0; i < list.length() - 1; i++) {
            if (list.at(i).equals(Move.invalid())) {
                continue;
            }
            elements[end] = list.at(i);
            end++;
        }
    }

    public void add(Move move) {
        if (move.equals(Move.invalid())) {
            return;
        }
        elements[end] = move;
        end++;
    }

    public void addSpec(Move move, SpecialMove spec) {
        if (move.equals(Move.invalid())) {
            return;
        }
        switch (spec) {
            case DOUBLE_PUSH:
                doublePush = move;
                break;
            case CASTLE_LONG:
                castleLong = move;
                break;
            case CASTLE_SHORT:
                castleShort = move;
                break;
            case EN_PASSANT:
                enPassant = move;
                break;
            case PROMOTION:
                promotion = move;
                break;
            default:
                break;
        }
        add(move);
    }

    public Move getSpecialMove(SpecialMove move) {
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
                return Move.invalid();
        }
    }

    public int length() {
        return end + 1;
    }

    public Move at(int index) {
        if (end == 0) {
            return Move.invalid();
        }
        return elements[index];
    }

    public boolean contains(int pos) {
        if (end == 0) {
            return false;
        }
        
        for (Move element : elements) {
            if (element.isInvalid()) {
                return false;
            }
            if (element.to() == pos) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(Move move) {
        for (int i = 0; i < length() - 1; i++) {
            if (elements[i].equals(move)) {
                return i;
            }
        }
        return -1;
    }
}
