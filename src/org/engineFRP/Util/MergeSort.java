package org.engineFRP.Util;

import java.util.Arrays;

/**
 * Created by TekMaTek on 11/03/2015.
 */
public class MergeSort<A extends Sortable> {

    public A[] mergeSort(A[] A) {
        if(A.length > 1) {
            int q = A.length / 2;

            A[] leftArray = Arrays.copyOfRange(A, 0, q);
            A[] rightArray = Arrays.copyOfRange(A, q, A.length);

            mergeSort(leftArray);
            mergeSort(rightArray);

            A = merge(A, leftArray, rightArray);
        }
        return A;
    }

    private A[] merge(A[] a, A[] l, A[] r) {
        int totElem = l.length + r.length;
        //int[] a = new int[totElem];
        int i, li, ri;
        i = li = ri = 0;
        while(i < totElem) {
            if((li < l.length) && (ri < r.length)) {
                if(l[li].isHighOrderThan(r[ri])) {
                    a[i] = l[li];
                    i++;
                    li++;
                } else {
                    a[i] = r[ri];
                    i++;
                    ri++;
                }
            } else {
                if(li >= l.length) {
                    while(ri < r.length) {
                        a[i] = r[ri];
                        i++;
                        ri++;
                    }
                }
                if(ri >= r.length) {
                    while(li < l.length) {
                        a[i] = l[li];
                        li++;
                        i++;
                    }
                }
            }
        }
        return a;
    }

}
