package com.anta1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MyShop {
    static final int WIDTHLIST = 160;
    static final int HEIGHTLIST = 200;
    static final int LEFTLIST = 20;
    static final int HEIGHTBUTTON = 30;
    private final String shop_name; // заголовок магазина
    private final ArrayList<Product> storeProduct = new ArrayList<>(); // склад продуктов, перечень
    private final ArrayList<Product> basketProduct = new ArrayList<>(); // корзина продуктов, перечень

    MyShop(String name) {
        this.shop_name = name;
        (new ShopWindow()).setVisible(true);
//        JOptionPane.showMessageDialog(null, "Name of shop : \"" + shop_name + "\"", "Shop initializition.", JOptionPane.INFORMATION_MESSAGE);
    }

    private class ShopWindow extends JFrame {
        JButton btn_LoadProduct = new JButton("Заполнение магазина");
        JButton btn_AddProduct = new JButton("Добавить продукт");
        JButton btn_DelProduct = new JButton("Удалить продукт");
        DefaultListModel<String> dlm_product = new DefaultListModel<>();
        JList<String> list_product = new JList<>(dlm_product);
        DefaultListModel<String> dlm_basket = new DefaultListModel<>();
        JList<String> list_basket = new JList<>(dlm_basket);
        JLabel lb_itogo = new JLabel("Сумма итого");
        Loader loader = new Loader();

        private void calcItogo() {
            Float sum = 0.0f;
            for (Product bp : basketProduct) sum += bp.getPrice();
            lb_itogo.setText(String.format("Сумма итого : %.2f грн", sum));
        }

        ShopWindow() {
            super(shop_name);
            this.setSize(4 * LEFTLIST + 2 * WIDTHLIST, 6 * LEFTLIST + 2 * HEIGHTBUTTON + HEIGHTLIST);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLayout(null);
            this.add(btn_LoadProduct);
            btn_LoadProduct.setBounds(LEFTLIST, LEFTLIST, WIDTHLIST, HEIGHTBUTTON);
            btn_LoadProduct.addActionListener(new btn_LoadProduct_ActionListener());
            this.add(list_product);
            list_product.setBounds(LEFTLIST, 2 * LEFTLIST + HEIGHTBUTTON, WIDTHLIST, HEIGHTLIST);
            list_product.setLayoutOrientation(JList.VERTICAL);
            this.add(btn_AddProduct);
            btn_AddProduct.setBounds(2 * LEFTLIST + WIDTHLIST, LEFTLIST, WIDTHLIST, HEIGHTBUTTON);
            btn_AddProduct.addActionListener(new btn_AddProduct_ActionListener());
            this.add(btn_DelProduct);
            btn_DelProduct.setBounds(2 * LEFTLIST + WIDTHLIST, 3 * LEFTLIST + HEIGHTBUTTON + HEIGHTLIST, WIDTHLIST, HEIGHTBUTTON);
            btn_DelProduct.addActionListener(new btn_DelProduct_ActionListener());
            JScrollPane scrollPane = new JScrollPane(list_basket);
            scrollPane.setBounds(2 * LEFTLIST + WIDTHLIST, 2 * LEFTLIST + HEIGHTBUTTON, WIDTHLIST, HEIGHTLIST);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            this.add(scrollPane);
            this.add(lb_itogo);
            lb_itogo.setBounds(LEFTLIST, 3 * LEFTLIST + HEIGHTBUTTON + HEIGHTLIST, WIDTHLIST, HEIGHTBUTTON);
        }

        class btn_LoadProduct_ActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (storeProduct.size() == 0) {
                    try {
                        loader.loaderProduct(storeProduct, "shop_fruits");
                    } catch (FileNotFoundException e) {
                        JOptionPane.showMessageDialog(null, "Ошбка загрузки.", shop_name, JOptionPane.WARNING_MESSAGE);
                    }
                    for (Product p : storeProduct) {
                        dlm_product.addElement(p.getName());
                    }
                    JOptionPane.showMessageDialog(null, String.format("Магазин успешно загружен.\nДобавлено %d элементов.", storeProduct.size()), shop_name, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Магазин уже загружен.", shop_name, JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        class btn_AddProduct_ActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int i = list_product.getSelectedIndex();
                if (i < 0) return;
                Product pr = storeProduct.get(i);
                String s = String.format("Выбор продукта...\n#%d. %s, Цена : %.2f грн", i+1, pr.getName(), pr.getPrice());
                JOptionPane.showMessageDialog(null, s, shop_name, JOptionPane.INFORMATION_MESSAGE);
                dlm_basket.addElement(pr.getName() + String.format("; %.2f грн", pr.getPrice()));
                basketProduct.add(pr);
                calcItogo();
            }
        }

        class btn_DelProduct_ActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int i = list_basket.getSelectedIndex();
                if (i < 0) return;
                Product pr = basketProduct.get(i);
                String s = "Удалить продукт...\n" + (i + 1) + ". " + pr.getName();
                JOptionPane.showMessageDialog(null, s, shop_name, JOptionPane.INFORMATION_MESSAGE);
                dlm_basket.remove(i);
                basketProduct.remove(i);
                calcItogo();
            }
        }
    }
}

