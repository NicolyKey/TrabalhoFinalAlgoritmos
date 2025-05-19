
package trabalhoFinal;

public class OrdenacaoQuickSort <T extends Comparable<T>> extends OrdenacaoAbstract<T> {


    public void ordernar(){
        int n = getInfo().length - 1;
        quickSort(0,n);
    }

    private void quickSort(int inicio, int fim){
        if(inicio < fim){
            int idxPivo = particionar(inicio, fim);
            quickSort(inicio, idxPivo - 1);
            quickSort(idxPivo + 1, fim);
        }
    }

    private int particionar(int inicio, int fim) {
        T[] info = getInfo();
        int a = inicio;
        int b = fim + 1;
        T pivo = info[inicio];

        while (true) {
            do {
                a++;
            } while (a <= fim && info[a].compareTo(pivo) < 0);

            do {
                b--;
            } while (b >= inicio && info[b].compareTo(pivo) > 0);

            if (a >= b) {
                break;
            }

            trocar(a, b);
        }

        trocar(inicio, b);

        return b;
    }
}
