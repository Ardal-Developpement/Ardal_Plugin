package org.ardal.inventories;

public enum CISize {
    CIS_9x1(9),
    CIS_9x2(18),
    CIS_9x3(27),
    CIS_9x4(36),
    CIS_9x5(45),
    CIS_9x6(54);

    private final int size;

    CISize(int size) {
        this.size = size;
    }

    public int toInt() {
        return this.size;
    }
}
