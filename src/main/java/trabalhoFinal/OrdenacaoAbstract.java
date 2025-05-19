package trabalhoFinal;

public abstract class OrdenacaoAbstract<T extends Comparable<T>> {
    private T[] info;

    public T[] getInfo() {
        return info;
    }

    public void setInfo(T[] info) {
        this.info = info;
    }
    public void trocar(int a, int b){
        T temp =  this.info[a];
        info[a] = info[b];
        info[b] = temp;
    }

    public void ordenar(){};
}
