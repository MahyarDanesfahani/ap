package project.Library3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * Shared look-and-feel helpers for the GUI : fonts , colors , tables ,
 * small form rows and right-to-left message dialogs .
 */
public final class Ui {

    public static final String FONT_NAME = "Segoe UI";
    public static final Font TITLE_FONT  = new Font(FONT_NAME, Font.BOLD, 24);
    public static final Font HEADER_FONT = new Font(FONT_NAME, Font.BOLD, 17);
    public static final Font NORMAL_FONT = new Font(FONT_NAME, Font.PLAIN, 14);

    public static final Color HEADER_BG = new Color(0x16324F);
    public static final Color PAGE_BG   = new Color(0xF4F6F9);

    private Ui(){
    }

    // Replaces every look-and-feel font so the whole app ( dialogs included )
    // uses one font that renders Persian properly .
    public static void applyGlobalFont(){
        javax.swing.UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        for (Object key : new ArrayList<>(defaults.keySet())){
            Object value = defaults.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource){
                javax.swing.plaf.FontUIResource f = (javax.swing.plaf.FontUIResource) value;
                UIManager.put(key, new javax.swing.plaf.FontUIResource(FONT_NAME, f.getStyle(), 14));
            }
        }
    }

    public static JLabel label(String text){
        JLabel l = new JLabel(text);
        l.setFont(NORMAL_FONT);
        return l;
    }

    public static JLabel headerLabel(String text){
        JLabel l = label(text);
        l.setFont(HEADER_FONT);
        return l;
    }

    public static JButton button(String text){
        JButton b = new JButton(text);
        b.setFont(NORMAL_FONT);
        b.setFocusPainted(false);
        return b;
    }

    // Colored strip shown at the top of every page .
    public static JPanel pageHeader(String title){
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER_BG);
        header.setBorder(new EmptyBorder(16, 24, 16, 24));
        JLabel t = new JLabel(title);
        t.setFont(TITLE_FONT);
        t.setForeground(Color.WHITE);
        header.add(t, BorderLayout.LINE_START);
        return header;
    }

    // A labeled row used inside forms : label on the right , field filling the rest .
    public static JPanel fieldRow(String labelText, JComponent field){
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        JLabel l = label(labelText);
        l.setPreferredSize(new Dimension(170, l.getPreferredSize().height));
        row.add(l, BorderLayout.LINE_START);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    // A bordered group of field rows with a title .
    public static JPanel formPanel(String title, JComponent... rows){
        JPanel form = new JPanel(new GridLayout(0, 1, 6, 8));
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(title),
                new EmptyBorder(4, 12, 12, 12)));
        for (JComponent row : rows){
            form.add(row);
        }
        return form;
    }

    public static DefaultTableModel model(String... columns){
        return new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
    }

    public static JTable table(DefaultTableModel model){
        JTable t = new JTable(model);
        t.setRowHeight(30);
        t.setFont(NORMAL_FONT);
        t.getTableHeader().setFont(HEADER_FONT);
        t.setRowSorter(new TableRowSorter<>(model));
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return t;
    }

    // Bottom bar with buttons , shared by every page that needs back / exit .
    public static JPanel bottomBar(JButton... buttons){
        JPanel bar = new JPanel();
        bar.setLayout(new javax.swing.BoxLayout(bar, javax.swing.BoxLayout.LINE_AXIS));
        bar.setOpaque(false);
        bar.setBorder(new EmptyBorder(8, 16, 12, 16));
        for (int i = 0 ; i < buttons.length ; i++){
            bar.add(buttons[i]);
            if (i < buttons.length - 1){
                bar.add(javax.swing.Box.createHorizontalStrut(10));
            }
        }
        return bar;
    }

    // ---------- dialogs ( right-to-left ) ----------

    public static JDialog dialog(JOptionPane pane, Component parent, String title){
        JDialog d = pane.createDialog(parent, title);
        d.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        return d;
    }

    public static void info(Component parent, String text){
        JOptionPane pane = new JOptionPane(text, JOptionPane.INFORMATION_MESSAGE);
        dialog(pane, parent, "پیام").setVisible(true);
    }

    public static void error(Component parent, String text){
        JOptionPane pane = new JOptionPane(text, JOptionPane.ERROR_MESSAGE);
        dialog(pane, parent, "خطا").setVisible(true);
    }

    public static boolean confirm(Component parent, String text){
        JOptionPane pane = new JOptionPane(text, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        dialog(pane, parent, "تایید").setVisible(true);
        return Integer.valueOf(JOptionPane.YES_OPTION).equals(pane.getValue());
    }

    // Shows a form inside an OK/Cancel dialog ; true only when OK was pressed .
    public static boolean showForm(Component parent, String title, JComponent form){
        JOptionPane pane = new JOptionPane(form, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        dialog(pane, parent, title).setVisible(true);
        return Integer.valueOf(JOptionPane.OK_OPTION).equals(pane.getValue());
    }
}
