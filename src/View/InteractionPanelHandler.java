package View;

import Control.MainController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jean-Pierre on 12.01.2017.
 */
public class InteractionPanelHandler {
    private JPanel panel;
    private JTextField textFieldLength;
    private JTextField textFieldKey;
    private JButton buttonGenerate;
    private JButton linSearchButton;
    private JLabel labelLinSearchTime;
    private JLabel labelLinSearchLoops;
    private JButton binSearchButton;
    private JButton bubbleSortButton;
    private JButton insertionSortButton;
    private JButton selectionsortButton;
    private JButton quicksortButton;
    private JLabel labelBinSearchTime;
    private JLabel labelBinSearchLoops;
    private JLabel labelBubblesortTime;
    private JLabel labelBubblesortLoops;
    private JLabel labelBubblesortSwitch;
    private JLabel labelInsertionsortTime;
    private JLabel labelInsertionsortLoops;
    private JLabel labelInsertionsortSwitch;
    private JLabel labelSelectionsortTime;
    private JLabel labelSelectionsortLoops;
    private JLabel labelSelectionsortSwitch;
    private JLabel labelQuicksortTime;
    private JLabel labelQuicksortLoops;
    private JLabel labelQuicksortSwitch;
    private JButton hashSearchButton;
    private JButton hashItButton;
    private JLabel labelHashSearchTime;
    private JLabel labelHashSearchLoops;
    private JButton selectionsortButton1;
    private JLabel labelSelectTime;
    private JLabel labelSelectLoop;
    private JLabel labelSelectSwitch;

    private MainController mainController;
    private DrawingPanel hashPanel, originalPanel, moddedPanel;

    public InteractionPanelHandler(MainController mainController, DrawingPanel hashPanel, DrawingPanel originalPanel, DrawingPanel moddedPanel) {
        this.mainController = mainController;
        this.hashPanel = hashPanel;
        this.originalPanel = originalPanel;
        this.moddedPanel = moddedPanel;

        System.out.println(hashPanel);
        createButtons();

    }

    private void createButtons(){
        hashItButton.setEnabled(false);
        hashSearchButton.setEnabled(false);
        linSearchButton.setEnabled(false);
        binSearchButton.setEnabled(false);
        bubbleSortButton.setEnabled(false);
        insertionSortButton.setEnabled(false);
        selectionsortButton.setEnabled(false);
        quicksortButton.setEnabled(false);
        selectionsortButton1.setEnabled(false);

        buttonGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hashPanel.removeAllObjects();
                originalPanel.removeAllObjects();
                moddedPanel.removeAllObjects();
                mainController.generateArray(Integer.parseInt(textFieldLength.getText()), originalPanel, moddedPanel);
                hashItButton.setEnabled(true);
                hashSearchButton.setEnabled(false);
                linSearchButton.setEnabled(true);
                binSearchButton.setEnabled(false);
                bubbleSortButton.setEnabled(true);
                insertionSortButton.setEnabled(true);
                selectionsortButton.setEnabled(true);
                quicksortButton.setEnabled(true);
                selectionsortButton1.setEnabled(true);
                clearLabels();
            }
        });
        linSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.linSearchArray(Integer.parseInt(textFieldKey.getText()));
                labelLinSearchLoops.setText(String.valueOf(mainController.getLoops()));
                labelLinSearchTime.setText(String.valueOf(mainController.getTime()));
            }
        });
        binSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.binSearchArray(Integer.parseInt(textFieldKey.getText()));
                labelBinSearchLoops.setText(String.valueOf(mainController.getLoops()));
                labelBinSearchTime.setText(String.valueOf(mainController.getTime()));
            }
        });
        bubbleSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.recopy(moddedPanel);
                mainController.bubbleSortArray();
                labelBubblesortLoops.setText(String.valueOf(mainController.getLoops()));
                labelBubblesortTime.setText(String.valueOf(mainController.getTime()));
                labelBubblesortSwitch.setText(String.valueOf(mainController.getSwitches()));
                binSearchButton.setEnabled(true);
            }
        });
        insertionSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.recopy(moddedPanel);
                mainController.insertionSortArray();
                labelInsertionsortLoops.setText(String.valueOf(mainController.getLoops()));
                labelInsertionsortTime.setText(String.valueOf(mainController.getTime()));
                labelInsertionsortSwitch.setText(String.valueOf(mainController.getSwitches()));
                binSearchButton.setEnabled(true);
            }
        });
        selectionsortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.recopy(moddedPanel);
                mainController.OSSA();
                labelSelectionsortLoops.setText(String.valueOf(mainController.getLoops()));
                labelSelectionsortTime.setText(String.valueOf(mainController.getTime()));
                labelSelectionsortSwitch.setText(String.valueOf(mainController.getSwitches()));
                binSearchButton.setEnabled(true);
            }
        });
        selectionsortButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.recopy(moddedPanel);
                mainController.selectionSort();
                labelSelectLoop.setText(String.valueOf(mainController.getLoops()));
                labelSelectTime.setText(String.valueOf(mainController.getTime()));
                labelSelectSwitch.setText(String.valueOf(mainController.getSwitches()));
                binSearchButton.setEnabled(true);
            }
        });
        quicksortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.recopy(moddedPanel);
                mainController.quickSortArray();
                labelQuicksortLoops.setText(String.valueOf(mainController.getLoops()));
                labelQuicksortTime.setText(String.valueOf(mainController.getTime()));
                labelQuicksortSwitch.setText(String.valueOf(mainController.getSwitches()));
                binSearchButton.setEnabled(true);
            }
        });
        hashItButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.hashIt(hashPanel);
                hashSearchButton.setEnabled(true);
            }
        });
        hashSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.hashSearch(Integer.parseInt(textFieldKey.getText()));
                labelHashSearchLoops.setText(String.valueOf(mainController.getLoops()));
                labelHashSearchTime.setText(String.valueOf(mainController.getTime()));
            }
        });
    }

    public JPanel getPanel(){
        return panel;
    }

    private void clearLabels(){
        labelLinSearchLoops.setText("0");
        labelLinSearchTime.setText("0");

        labelBinSearchLoops.setText("0");
        labelBinSearchTime.setText("0");

        labelBubblesortLoops.setText("0");
        labelBubblesortSwitch.setText("0");
        labelBubblesortTime.setText("0");

        labelInsertionsortLoops.setText("0");
        labelInsertionsortSwitch.setText("0");
        labelInsertionsortTime.setText("0");

        labelSelectionsortLoops.setText("0");
        labelSelectionsortSwitch.setText("0");
        labelSelectionsortTime.setText("0");

        labelQuicksortLoops.setText("0");
        labelQuicksortSwitch.setText("0");
        labelQuicksortTime.setText("0");
    }
}
