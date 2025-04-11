/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhoFinal;

/**
 *
 * @author Premiersoft
 */
public class ListaEncadeada<T> {
    private NoLista<T> primeiro;
    private int qteNos;
    
    public ListaEncadeada(){
       this.primeiro = null;
    }

    public NoLista<T> getPrimeiro() {
        return primeiro;
    }
  
    public void inserir(T info){;
       NoLista<T> novo = new NoLista();
       novo.setInfo(info);
       novo.setProximo(primeiro);
       this.primeiro = novo;
       qteNos ++;
    }
    
    public boolean estaVazia(){
        return primeiro == null;
    }

    public T buscar(T info){
       NoLista<T> p = new NoLista<T>();
       
       while(p != null){
         if(p.getInfo().equals(info)){
            return (T) p;
         }
         p.getProximo();
       }
      return null;
    }

    public void retirar(T info){
      NoLista<T> anterior = new NoLista<T>();
      NoLista<T> p = new NoLista<T>();
      
      while(p != null && (p.getInfo() != info)){
          anterior = p;
          p.getProximo();
      }
      
      if(p != null){
        if(p == primeiro){
          this.primeiro = p;
        }
        anterior.setProximo(p.getProximo());
        qteNos --;
      }
    }

    public int obterComprimento(){
        NoLista<T> p = new NoLista<T>();
        int comprimento = 0;
        p = primeiro;
      
      while(p != null){
        comprimento ++;
        p.getProximo();
      }
      
      return comprimento;
    }

    public NoLista<T> obterNo(int idx){
        int comprimento = obterComprimento();
        
        if(idx < 0){
          throw new IndexOutOfBoundsException();
        }        

       NoLista<T> p = primeiro;
       
       while((p != null) && (idx > 0)){
          idx --;
          p = p.getProximo();
       }

       if(p == null){
             throw new IndexOutOfBoundsException();
       }
      return p;
      
    }
    
}
