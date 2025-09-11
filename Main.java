import java.util.Scanner;

/**
 * Головний клас програми.
 * Дає змогу ввести N та обчислити:
 * - N-те число Люка
 * - квадрат цього числа
 * - суму квадратів перших N чисел Люка
 */
public class Main {
    public static void main(String[] args) {
        long N;

        if (args.length > 0) {
            N = Long.parseLong(args[0]);
        } else {
            System.out.println("No arguments provided.");
            System.out.print("Enter a number N as an argument to compute: ");
            Scanner sc = new Scanner(System.in);
            N = sc.nextLong();
            sc.close();
        }

        // створення обєкта
        Luke luke = new Luke(N);

        System.out.println("\nВхідні дані:");
        System.out.println("N = " + N);

        System.out.println("\nРезультати обчислень:");
        System.out.println("Basic: " + (N >= 92 ? "Overflow! N <= 91" : luke.computeBasic()));
        System.out.println("Square: " + (N >= 46 ? "Overflow! N <= 45" : luke.computeSquare()));
        System.out.println("Sum of Squares: " + (N >= 46 ? "Overflow! N <= 45" : luke.computeSumSquared()));

        // додаткова перевірка: сума квадратів через цикл і через формулу
        if (N < 46) {
            long direct = luke.computeSumSquaredDirect();
            long formula = luke.computeSumSquared();
            System.out.println("\nПеревірка формули:");
            System.out.println("Через прямий розрахунок: " + direct);
            System.out.println("Через формулу: " + formula);
            System.out.println("Результати " + (direct == formula ? "співпадають ✅" : "відрізняються ❌"));
        }
    }
}

/**
 * Клас для роботи з числами Люка.
 */
class Luke {

    private long index;  // номер N
    private long value;  // значення L_N

    /**
     * Конструктор, що приймає номер числа.
     * @param N номер числа
     */
    public Luke(long N) {
        this.index = N;
        this.value = computeBasic(N);
    }

    /**
     * Повертає номер числа.
     * @return index
     */
    public long getIndex() {
        return index;
    }

    /**
     * Повертає значення числа Люка.
     * @return value
     */
    public long getValue() {
        return value;
    }

    /**
     * Обчислює N-те число Люка.
     * @param N номер
     * @return L_N
     */
    public long computeBasic(long N) {
        if (N > 91) return -1; // overflow
        long a = 2, b = 1;
        if (N == 0) return a;
        if (N == 1) return b;
        long c = 0;
        for (long i = 2; i <= N; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }

    /**
     * Обчислює число Люка для index, заданого в конструкторі.
     * @return L_index
     */
    public long computeBasic() {
        return computeBasic(index);
    }

    /**
     * Обчислює квадрат числа Люка (за формулою).
     * @return L_N^2
     */
    public long computeSquare() {
        if (index > 45) return -1; // overflow
        long sign = (index % 2 == 0) ? 1 : -1;
        return computeBasic(2 * index) + 2 * sign;
    }

    /**
     * Обчислює суму квадратів перших N чисел Люка (за формулою).
     * @return сума квадратів
     */
    public long computeSumSquared() {
        if (index > 45) return -1; // overflow
        long sum = 0;
        for (long i = 0; i <= index; i++) {
            long sign = (i % 2 == 0) ? 1 : -1;
            sum += computeBasic(2 * i) + 2 * sign;
        }
        return sum;
    }

    /**
     * Обчислює суму квадратів перших N чисел Люка напряму (через цикл).
     * @return сума квадратів
     */
    public long computeSumSquaredDirect() {
        if (index > 45) return -1; // overflow
        long sum = 0;
        for (long i = 0; i <= index; i++) {
            long L = computeBasic(i);
            sum += L * L;
        }
        return sum;
    }
}
