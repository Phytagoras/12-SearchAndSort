package Control;

import Model.Ball;
import Model.List;
import View.DrawingPanel;

import java.util.Random;

/**
 * Idea by KNB,
 * Rework by AOS on 12.01.2017, 24.02.2018
 */
public class MainController {

    private final Random rng;
    List<Ball>[][][][][] arrayTree;
    private int maxWidth;
    private long time;
    private long loops;
    private long switches;
    private Ball lastFound;
    private Ball[] originalArray;
    private Ball[] moddedArray;

    public MainController(){
        rng = new Random();
        rng.setSeed(System.currentTimeMillis());
    }

    public static void main(String[] args){
        MainController mainController = new MainController();
        mainController.hashFunction(5);
    }

    /**
     * Erzeugt das Array originalArray aus zufällig gefüllten Kreisen mit sinnvollen Koordinaten. Dabei wird von jedem Ball-Objekt eine exakte Kopie erstellt und einem weiteren Array, dem moddedArray, hinzugefügt.
     * Die Methoden, die noch implementiert werden müssen, verändern das moddedArray, nicht das originalArray.
     *
     * @param amount        Anzahl der Bälle
     * @param originalPanel Panel zur Darstellung des Urpsrungsarrays.
     * @param moddedPanel   Panel zur Darstellung des abgeändereten (sortierten?) Arrays.
     */
    public void generateArray(int amount, DrawingPanel originalPanel, DrawingPanel moddedPanel){
        originalArray = new Ball[ amount ];
        moddedArray = new Ball[ amount ];
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int x = 10;
        int y = 10;
        int maxRandom = Integer.max(100, amount);
        this.maxWidth = originalPanel.getWidth();
        for (int i = 0; i < amount; i++) {
            Ball newBall = new Ball(x, y, rng.nextInt(maxRandom), alphabet.charAt(rng.nextInt(alphabet.length())));
            originalArray[ i ] = newBall;
            originalPanel.addObject(originalArray[ i ]);

            Ball copyBall = newBall.getCopy();
            moddedArray[ i ] = copyBall;
            moddedPanel.addObject(moddedArray[ i ]);

            x += 20;
            if (x > maxWidth - 10){
                x = 10;
                y += 20;
            }
        }
    }

    /**
     * Erzeugt eine frische, unsortierte Kopie des Original-Arrays.
     */
    public void recopy(DrawingPanel moddedPanel){
        moddedPanel.removeAllObjects();
        for (int i = 0; i < originalArray.length; i++) {
            Ball copyBall = originalArray[ i ].getCopy();
            moddedArray[ i ] = copyBall;
            moddedPanel.addObject(moddedArray[ i ]);
        }
    }

    /**
     * Setzt für alle Kugeln im sortierten Array neue Koordinaten gemäß der Reihenfolge im Array.
     * Muss nach dem Sortieren aufgerufen werden, damit die Sortierung sichtbar wird.
     */
    private void updateCoordinates(){
        int x = 10;
        int y = 10;
        for (int i = 0; i < moddedArray.length; i++) {
            moddedArray[ i ].setX(x);
            moddedArray[ i ].setY(y);

            x += 20;
            if (x > maxWidth - 10){
                x = 10;
                y += 20;
            }
        }
    }

    /**
     * Führt eine lineare Suche auf dem modded-Array durch.
     *
     * @param key Die gesuchte Zahl.
     */
    public void linSearchArray(int key){
        if (lastFound != null){
            lastFound.setMarked(false);
        }
        time = System.nanoTime();
        loops = 0;

        // Lineare Suche Start
        lastFound = null;
        int i = 0;
        while (lastFound == null && i < moddedArray.length) {
            loops++;
            if (moddedArray[ i ].getNumber() == key){
                lastFound = moddedArray[ i ];
            }
            i++;
        }
        // Lineare Suche Ende
        time = ( System.nanoTime() - time ) / 1000;
        if (lastFound != null){
            lastFound.setMarked(true);
        }
    }

    /**
     * Führt eine binäre Suche auf dem modded-Array durch.
     *
     * @param key Die gesuchte Zahl.
     */
    public void binSearchArray(int key){
        if (lastFound != null){
            lastFound.setMarked(false);
        }
        time = System.nanoTime();
        loops = 0;
        // Binäre Suche Start

        //TODO 01: Orientiere dich für die Messung der Schleifendurchgänge an der Linearen Suche und implementiere die Binäre Suche iterativ.

        // Binäre Suche Ende
        time = ( System.nanoTime() - time ) / 1000;
        if (lastFound != null){
            lastFound.setMarked(true);
        }
    }

    /**
     * Sortiert das modded-Array gemäß dem Bubble-Sort-Algorithmus.
     */
    public void bubbleSortArray(){
        time = System.nanoTime();
        loops = 0;
        switches = 0;
        // Bubblesort Start
        for (int i = 0; i < moddedArray.length; i++) {
            loops++;
            boolean hasChanged = false;
            for (int j = 0; j < moddedArray.length - 1 - i; j++) {
                loops++;
                if (moddedArray[ j ].getNumber() > moddedArray[ j + 1 ].getNumber()){
                    switchBalls(j, j + 1);
                    hasChanged = true;
                }
            }
            if (!hasChanged)
                break;       //Mit der Zeile ist Bubblesort selten schneller als Optimized Selection Sort Algorithm - ohne die Zeile ist OSSA immer schneller
        }
        // Bubble Sort Ende
        time = ( System.nanoTime() - time ) / 1000;
        updateCoordinates();
        long ops = ( ( loops + switches ) + ( ( loops + switches ) % 100000 ) );
        System.out.println(ops - ( ops % 100000 ));
    }

    /**
     * Sortiert das modded-Array gemäß dem Selection-Sort-Algorithmus.
     */
    public void OSSA(){
        time = System.nanoTime();
        loops = 0;
        switches = 0;
        int min, max;
        // Selectionsort Start
        //OSSA - Optimized Selection Sort Algorithm - nicht stabil, weil wer braucht schon stabil...wenn du stabil willst, dann mach doch einfach RADIX SORT!!!!!!!!!
        for (int i = 0; i < ( moddedArray.length / 2 ); i++) {
            loops += 1;
            min = i;
            max = moddedArray.length - i - 1;
            for (int j = i; j < moddedArray.length - i; j++) {
                loops += 1;
                if (moddedArray[ j ].getNumber() < moddedArray[ min ].getNumber()){
                    min = j;
                }
                if (moddedArray[ j ].getNumber() > moddedArray[ max ].getNumber()){
                    max = j;
                }
            }
            if (max == i) max = min;
            switchBalls(min, i);
            switchBalls(max, moddedArray.length - i - 1);
        }


        // Selection Sort Ende
        time = ( System.nanoTime() - time ) / 1000;
        updateCoordinates();
    }

    public void selectionSort(){
        time = System.nanoTime();
        loops = 0;
        switches = 0;
        int min;
        // Selectionsort Start
        for (int i = 0; i < moddedArray.length; i++) {
            loops += 1;
            min = i;
            for (int j = i; j < moddedArray.length; j++) {
                loops += 1;
                if (moddedArray[ j ].getNumber() < moddedArray[ min ].getNumber()){
                    min = j;
                }
            }
            switchBalls(min, i);
        }


        // Selection Sort Ende
        time = ( System.nanoTime() - time ) / 1000;
        updateCoordinates();
    }

    /**
     * Sortiert das modded-Array gemäß dem Insertion-Sort-Algorithmus.
     */
    public void insertionSortArray(){
        time = System.nanoTime();
        loops = 0;
        switches = 0;
        // Insertionsort Start
        for (int i = 1; i < moddedArray.length; i++) {
            loops++;
            Ball tmp = moddedArray[ i ];
            int j = i;
            while (j > 0 && moddedArray[ j - 1 ].getNumber() > tmp.getNumber()) {
                loops++;
                switches++;
                moddedArray[ j ] = moddedArray[ j - 1 ];
                j--;
            }
            moddedArray[ j ] = tmp;
            switches++;
        }
        // Insertion Sort Ende
        time = ( System.nanoTime() - time ) / 1000;
        updateCoordinates();
    }

    public void useRadixSorting(){
        loops = 0;
        switches = 0;
        time = System.nanoTime();
        int maxLength = ( ( Math.max(100, moddedArray.length) ) + "" ).length();
        radixSortRecursive(maxLength, maxLength);
        time = ( System.nanoTime() - time ) / 1000;
        updateCoordinates();
    }

    public void radixSortRecursive(int depth, int maxLength){
        if (depth > 0){
            int dividend1 = (int) Math.pow(10, maxLength - depth);
            int dividend2 = 10;
            int[] countsArray = new int[ 10 ];
            for (Ball ball : moddedArray) {
                countsArray[ ( ball.getNumber() / dividend1 ) % dividend2 ]++;
                loops++;
            }
            for (int i = 1; i < 10; i++) {
                countsArray[ i ] = countsArray[ i ] + countsArray[ i - 1 ];
                loops++;
            }
            for (int i = 9; i > 0; i--) {
                countsArray[ i ] = countsArray[ i - 1 ];
                loops++;
            }
            countsArray[ 0 ] = 0;
            Ball[] sortedArray = new Ball[ moddedArray.length ];
            for (Ball ball : moddedArray) {
                int tmpCount = ( ball.getNumber() / dividend1 ) % dividend2;
                sortedArray[ countsArray[ tmpCount ] ] = ball;
                countsArray[ tmpCount ]++;
                loops++;
                switches++;
            }
            moddedArray = sortedArray;
            radixSortRecursive(depth - 1, maxLength);
        }

    }

    /**
     * Sortiert das modded-Array gemäß dem Quick-Sort-Algorithmus.
     */
    public void quickSortArray(){
        time = System.nanoTime();
        loops = 0;
        switches = 0;
        // Quick Sort Start
        quicksortRecursive(0, moddedArray.length - 1);
        // Quick Sort Ende
        time = ( System.nanoTime() - time ) / 1000;
        updateCoordinates();
    }

    /**
     * Die eigentliche rekursive Quicksort-Methode.
     */
    private void quicksortRecursive(int start, int end){
        loops++;
        int i = start;
        int j = end;
        int middle = ( i + j ) / 2;
        int pivot = moddedArray[ middle ].getNumber();

        //Beginn des Zaubers
        //TODO 05: Programmiere den rekursiven Quicksortalgorithmus. Halte dich an den hier vorgegeben Rahmen.
        //Ende des Zaubers
    }

    /**
     * Die Bälle werden gemäß der Hashfunktion in der Hashtabelle gepspeichert.
     * Dazu werden alle Bälle zunächst kopiert und dann in die passenden Listen von hashArray übertragen.
     * Anschließend müsst ihr noch für die zeichnerische Darstellung der Bälle die jeweilige x- und y-Koordinate aktualisieren.
     *
     * @param hashPanel
     */
    public void hashIt(DrawingPanel hashPanel){
        hashPanel.removeAllObjects();
        arrayTree = new List[ 10 ][ 10 ][ 10 ][ 10 ][ 10 ];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    for (int l = 0; l < 10; l++) {
                        for (int m = 0; m < 10; m++) {
                            arrayTree[ i ][ j ][ k ][ l ][ m ] = new List<>();
                        }
                    }
                }
            }
        }
        for (Ball ball :
                originalArray) {
            int[] hash = hashFunction(ball.getNumber());
            arrayTree[ hash[ 0 ] ][ hash[ 1 ] ][ hash[ 2 ] ][ hash[ 3 ] ][ hash[ 4 ] ].append(ball.getCopy());
        }

        int x = 10; //Start-Koordinate des ersten anzuzeigenen Balls
        int y = 10; //Start-Koordinate des ersten anzuzeigenen Balls

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    for (int l = 0; l < 10; l++) {
                        for (int m = 0; m < 10; m++) {
                            List<Ball> tmp = arrayTree[ i ][ j ][ k ][ l ][ m ];
                            tmp.toFirst();
                            while (tmp.hasAccess()) {
                                tmp.getContent().setX(x);
                                tmp.getContent().setY(y);
                                hashPanel.addObject(tmp.getContent());
                                x += 20;
                                if (x > maxWidth - 10){
                                    x = 10;
                                    y += 20;
                                }
                                tmp.next();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Die Hashfunktion für die Methode hashIt(...)
     *
     * @param argument Das übergebene Funktionsargument
     * @return Funktionswert
     */
    private int[] hashFunction(int argument){
        int[] array = new int[ 5 ];
        for (int i = 0; i < array.length; i++) {
            loops++;
            int dividend1 = (int) Math.pow(10, array.length - i - 1);
            array[ i ] = ( argument / dividend1 ) % 10;
        }
        return array;
    }

    /**
     * Führt eine Hash-Suche auf dem Hash-Arrays durch.
     *
     * @param key
     */
    public void hashSearch(int key){
        loops = 0;
        int[] arrTmp = hashFunction(key);

        List<Ball> tmp = arrayTree[ arrTmp[ 0 ] ][ arrTmp[ 1 ] ][ arrTmp[ 2 ] ][ arrTmp[ 3 ] ][ arrTmp[ 4 ] ];
        tmp.toFirst();
        while (tmp.hasAccess()) {
            loops++;
            if (tmp.getContent().getNumber() == key){
                lastFound = tmp.getContent();
                lastFound.setMarked(true);
                break;
            }
            tmp.next();
        }

    }

    /**
     * Vertausch zwei Bälle innerhalb des Arrays, das verändert wird.
     * Bei jedem Aufruf dieser Methode wird das Attribut switches hochgezählt.
     *
     * @param a Indexposition des einen Balls
     * @param b Indexposition des anderen Balls
     */
    private void switchBalls(int a, int b){
        Ball temp = moddedArray[ a ];
        moddedArray[ a ] = moddedArray[ b ];
        moddedArray[ b ] = temp;

        switches++;
    }

    public long getTime(){
        return time;
    }

    public long getLoops(){
        return loops;
    }

    public long getSwitches(){
        return switches;
    }
}
