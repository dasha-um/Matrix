package main.java.edu.spbu;
import java.io.*;
import java.util.ArrayList;


public class DMat implements Matrix {

    public DMat(String fileName) throws IOException {
        if (fileName != null) {
            this.list = new ArrayList();
            this.list = this.getMat(fileName);
            this.arr = this.toArray(this.list);
            this.list.clear();
            this.list = null;
        }
    }

    @Override
    public Matrix mul(Matrix bb) throws IOException {
        DMat resD;
        SMat resS;
        DMat a = this;

        if (bb instanceof DMat) {
            DMat b = (DMat) bb;
            resD = a.mulDD(b);
            return resD;
        }
        if (bb instanceof SMat) {
            SMat b = (SMat) bb;
            resS = (SMat) a.mulDS(b);
            return resS;
        } else return null;


    }


    @Override
    public void saveToFile(String nameOfFile) throws IOException {
        DMat c = this;
        PrintWriter printWriter = new PrintWriter(new FileWriter(nameOfFile));
        for (int i = 0; i < c.arr.length; i++) {
            for (int j = 0; j < c.arr.length; j++) {
                printWriter.print(c.arr[i][j] + " ");
            }
            printWriter.println();
        }
        printWriter.close();
    }


    static BufferedReader r;
    static ArrayList<ArrayList> arrayList;
    static ArrayList<Double> row;
    private String fileName;
    public double[][] arr;
    public ArrayList list;

    private DMat mulDD(DMat b) throws IOException {
        DMat a = this;
        DMat res = new DMat(null);
        b=b.transpose(b);
        res.arr = new double[a.arr.length][a.arr.length];
        for (int i = 0; i < a.arr.length; i++) {
            for (int j = 0; j < a.arr.length; j++) {
                for (int k = 0; k < a.arr.length; k++) {
                    res.arr[i][j] = res.arr[i][j] + a.arr[i][k] * b.arr[j][k];
                }
            }
        }
        return res;
    }

    private Matrix mulDS(SMat b) throws IOException {
        SMat res;
        DMat a = this;
        SMat bT = b.transpose(b);
        res = (SMat) bT.mul(transpose(a));
        res = res.transpose(res);
        res.sizeOfMatrix = b.sizeOfMatrix;
        return res;
    }

    private DMat transpose(DMat a) throws IOException {
        DMat newA = new DMat(null);
        newA.arr = new double[a.arr.length][a.arr.length];
        for (int i = 0; i < a.arr.length; i++) {
            for (int j = 0; j < a.arr.length; j++) {
                newA.arr[i][j] = a.arr[j][i];
            }
        }
        return newA;
    }

    public static void printf(DMat a) {
        for (int i = 0; i < a.arr.length; i++) {
            for (int j = 0; j < a.arr.length; j++) {
                System.out.print(a.arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void openFile(String fileName) {
        try {
            r = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (Exception e) {
            System.out.println("not found");
        }
    }

    private void readFile() throws IOException {

        String s = r.readLine();

        while (s != null) {
            for (String val : s.split(" ")) {
                row.add(Double.parseDouble(val));
            }
            arrayList.add((ArrayList) row.clone());
            row.clear();
            s = r.readLine();
        }

    }

    public ArrayList<ArrayList> getMat(String fileName) throws IOException {
        arrayList = new ArrayList<ArrayList>();
        row = new ArrayList<Double>();
        this.fileName = fileName;
        openFile(fileName);
        readFile();
        return arrayList;
    }

    private double[][] toArray(ArrayList<ArrayList> arrayList) {
        double[][] res = new double[arrayList.size()][arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                res[i][j] = (double) arrayList.get(i).get(j);
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object bm) {
        boolean ans;
        if (bm instanceof DMat) {
            boolean ans1=true;
            DMat a = this;
            DMat b = (DMat) bm;
            for (int i = 0; i < a.arr.length; i++) {
                for (int j = 0; j < b.arr.length; j++) {
                    if (a.arr[i][j] != b.arr[i][j]) ans1 = false;
                }
            }
            ans=ans1;
        }
        else ans=false;
        return ans;
    }
}
