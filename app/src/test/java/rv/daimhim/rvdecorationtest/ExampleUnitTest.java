package rv.daimhim.rvdecorationtest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        int spanCount = 8;
        int cun = 0;
        StringBuilder lStringBuilder = new StringBuilder();
        for (int i = 0; i < 50; i++) {

            if (i % spanCount == 0){
                System.out.println("i:"+i+"  "+i % spanCount);
            }else if (((i - (spanCount-1)) % spanCount) == 0) {
                System.out.println("i:"+i+"  "+((i - (spanCount-1)) % spanCount));
            }
            lStringBuilder.append(i).append("  ");
            if (cun == spanCount-1){
                System.out.println("-------"+lStringBuilder.toString());
                lStringBuilder.delete(0,lStringBuilder.length());
                cun = 0;
                continue;
            }
            cun ++;
        }
        assertEquals(4, 2 + 2);
    }
}