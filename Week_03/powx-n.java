// pow(x,n)
import java.util.*;

class powx-n {
    public double powx-n(double x, int n) {
        if (x == 1 || n == 0) return 1.0;
        double res = 1.0;
        long N = n;
        if (N < 0) N = -N;
        while (N > 0) {
            if (N % 2 == 1) {
                res *= x;
            }
            x *= x;
            N >>= 1;
        }
        if (n > 0) {
            return res;
        } else {
            return 1.0 / res;
        }
    }
}