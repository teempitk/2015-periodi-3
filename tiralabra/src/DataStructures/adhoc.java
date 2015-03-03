/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import java.util.Comparator;

/**
 *
 * @author teemupitkanen1
 */
public class adhoc {

    private static Comparator<Integer> comp = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    };

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList();
        Integer one = 1;
        Integer two = 2;
        Integer three = 3;
        Integer four = 4;
        Integer five = 5;
        list.addPreservingOrder(four, comp);
        list.addPreservingOrder(five, comp);
        list.addPreservingOrder(three, comp);
        list.addPreservingOrder(two, comp);
        list.addPreservingOrder(one, comp);
        while (list.size() > 0) {
            System.out.println(list.getAndRemoveFirst());
        }
    }
}
