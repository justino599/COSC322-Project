package State;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

import static State.State.*;

public class BitBoard {
    // TODO: Need two longs for each item
    long whiteQueensTop;
    long whiteQueensBottom;
    long blackQueensTop;
    long blackQueensBottom;
    long arrowTop;
    long arrowBottom;

    public BitBoard() {
        whiteQueensTop = 0L;
        whiteQueensBottom = 0L;
        blackQueensTop = 0L;
        blackQueensBottom = 0L;
        arrowTop = 0L;
        arrowBottom = 0L;
    }

    public void setPiece(int x, int y, int pieceType) {
        int index = y * 10 + x;
        boolean top = false;
        if (index > 49) {
            index -= 50;
            top = true;
        }
        long mask = 1L << index;
        switch (pieceType) {
            case BLACK_QUEEN:
                if (top)
                    blackQueensTop |= mask;
                else
                    blackQueensBottom |= mask;
                break;
            case WHITE_QUEEN:
                if (top)
                    whiteQueensTop |= mask;
                else
                    whiteQueensBottom |= mask;
                break;
            case ARROW:
                if (top)
                    arrowTop |= mask;
                else
                    arrowBottom |= mask;
                break;
            case 0:
                break;
        }
    }

    public void clearPiece(int x, int y, int pieceType) {
        int index = y * 10 + x;
        boolean top = false;
        if (index > 49) {
            index -= 50;
            top = true;
        }
        long mask = ~(1L << index);
        switch (pieceType) {
            case BLACK_QUEEN:
                if (top)
                    blackQueensTop &= mask;
                else
                    blackQueensBottom &= mask;
                break;
            case WHITE_QUEEN:
                if (top)
                    whiteQueensTop &= mask;
                else
                    whiteQueensBottom &= mask;
                break;
            case ARROW:
                if (top)
                    arrowTop &= mask;
                else
                    arrowBottom &= mask;
                break;
        }
    }

    public boolean isPiece(int x, int y, int pieceType) {
        int index = y * 10 + x;
        boolean top = false;
        if (index > 49) {
            index -= 50;
            top = true;
        }
        long mask = 1L << index;
        switch (pieceType) {
            case BLACK_QUEEN:
                if (top)
                    return (blackQueensTop & mask) != 0L;
                else
                    return (blackQueensBottom & mask) != 0L;
            case WHITE_QUEEN:
                if (top)
                    return (whiteQueensTop & mask) != 0L;
                else
                    return (whiteQueensBottom & mask) != 0L;
            case ARROW:
                if (top)
                    return (arrowTop & mask) != 0L;
                else
                    return (arrowBottom & mask) != 0L;
            default:
                return false;
        }
    }

    public int getPiece(int x, int y) {
        int index = y * 10 + x;
        boolean top = false;
        if (index > 49) {
            index -= 50;
            top = true;
        }
        long mask = 1L << index;
        if (top && (blackQueensTop & mask) != 0L)
            return BLACK_QUEEN;
        else if (top && (whiteQueensTop & mask) != 0L)
            return WHITE_QUEEN;
        else if (top && (arrowTop & mask) != 0L)
            return ARROW;
        else if ((blackQueensBottom & mask) != 0L)
            return BLACK_QUEEN;
        else if ((whiteQueensBottom & mask) != 0L)
            return WHITE_QUEEN;
        else if ((arrowBottom & mask) != 0L)
            return ARROW;
        else
            return 0;
    }

    public void boardToBitMap(byte[][] board) {
        System.out.println(Arrays.toString(board));
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != 0)
                    setPiece(i, j, board[i][j]);
            }
        }
    }

    public String toBoardString() {
        System.out.println(Long.toBinaryString(blackQueensTop) + Long.toBinaryString(blackQueensBottom));
        System.out.println(Long.toBinaryString(whiteQueensTop) + Long.toBinaryString(whiteQueensBottom));
        System.out.println(Long.toBinaryString(arrowTop) + Long.toBinaryString(arrowBottom));
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.repeat("-", 20)).append(System.lineSeparator());
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                int pieceType = getPiece(i, j);
                switch (pieceType) {
                    case BLACK_QUEEN:
                        sb.append("B");
                        break;
                    case WHITE_QUEEN:
                        sb.append("W");
                        break;
                    case ARROW:
                        sb.append("X");
                        break;
                    default:
                        sb.append("-");
                }
                sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public String boardToString() {
        StringBuilder sb = new StringBuilder();
        for (int y = BOARD_SIZE - 1; y >= 0; y--) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (x == 0)
                    sb.append(String.format("%2d ", y + 1));
                int pieceType = getPiece(x, y);
                switch (pieceType) {
                    case BLACK_QUEEN:
                        sb.append("B ");
                        break;
                    case WHITE_QUEEN:
                        sb.append("W ");
                        break;
                    case ARROW:
                        sb.append("X ");
                        break;
                    case 0:
                        sb.append("- ");
                        break;
                }
            }
            sb.append("\n");
        }
        sb.append("   a b c d e f g h i j");
        return sb.toString();
    }
}
