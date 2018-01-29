import java.io.*;

public class Akari {
    public static void main(String[] m) {
        int n = m.length;
        File p, q; // here
        int A, k, a, r, i;
        char[] d = "P%d\n%d\40%d\n%d\n\00wb+".toCharArray();
        char[] b = new char[1024];
        char[] y = ("yuriyurarararayuruyuri*daijiken**akkari~n**" +
        "/y*u*k/riin<ty(uyr)g,aur,arr[a1r2a82*y2*/" +
        "u*r{uyu}riOcyurhiyua**rrar+*arayra*=" +
        "yuruyurwiyuriyurara'rariayuruyuriyuriyu>" +
        "rarararayuruy9uriyu3riyurar_aBrMaPrOaWy^?" +
        "*]/f]`;hvroai<dp/f*i*s/<ii(f)a{tpguat<cahfaurh(+uf)a;f}vivn+tf/" +
        "g*`*w/jmaa+i`ni(" +
        "i+k[>+b+i>++b++>l[rb").toCharArray();

        int u;

        int my_tmp;

        for (i = 0; i < 101; i++) {
            y[i * 2] ^= (
                    "~hktrvg~dmG*eoa+%squ#l2:(wn\"1l))v?wM353{/" +
                    "Y;lgcGp`vedllwudvOK`cct~[|ju " +
                    "{stkjalor(stwvne\"gt\"yogYURUYURI").toCharArray()[i] ^ y[i * 2 + 1] ^ 4;
        }

        if (n > 1 && (m[1].charAt(0) - '-' != 0 || m[1].charAt(1) != '\0'))
            p = new FileInputStream(m[1]); // fopen(m[1], "rb"); // here
        else
            p = stdin; // here

        if (n < 3 || !(m[2][0] - '-' || m[2][1]))
            q = new PrintStream(out); // stdout; // here
        else
            q = new FileOutputStream(m[2], "wb+"); // fopen(m[2], "wb+"); // here

        /* temporary commented
        if (!p || !q) {
            return +printf("Can not\x20open\40%s\40for\40%sing\n", m[!p ? 1 : 2],
                    !p ? "read" : "writ"); // here
        }
        */

        for (a = k = u = 0; y[u]; u = 2 + u) {
            y[k++] = y[u];
        }

        a = fread(b, 1, 1024, p);              // here
        my_tmp = sscanf(b, d, k, A, i, r); // here
        if (a > 2 && b[0] == 'P' && my_tmp == 4 && !(k - 6 && k - 5) && r == 255) {
            u = A;
            if (n > 3) {
                u++;
                i++;
            }
            fprintf(q, d, k, u >> 1, i >> 1, r); // here
            u = k - 5 ? 8 : 4;
            k = 3;
        } else {
            u = +(n + 14 > 17) ? 8 / 4 : 8 * 5 / 4;
        }

        r = i = 0;
        for (;;) {
            u *= 6;
            u += (n > 3 ? 1 : 0);
            if (y[u] & 01)
                fputc(1 * r, q); // here
            if (y[u] & 16)
                k = A;
            if (y[u] & 2)
                k--;
            if (i == a) {
                i = a = (u)*11 & 255;
                a = fread(b, 1, 1024, p); // here
                if (a <= 0)
                    break;
                i = 0;
            }
            r = b[i++];
            u += (+8 & (y[u])) ? (10 - r ? 4 : 2) : (y[u] & 4) ? (k ? 2 : 4) : 2;
            u = y[u] - 96;
        }

        fclose(p);     // here
        k = fclose(q); // here
        return k;
    }
}
