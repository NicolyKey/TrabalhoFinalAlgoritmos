/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhoFinal;

/**
 *
 * @author Premiersoft
 */
public class PilhaLista<T> implements Pilha<T> {

     private ListaEncadeada<T> lista = new ListaEncadeada<>();
    @Override
    public void push(T info) {
        lista.inserir(info);
    }

    @Override
    public T peek() {
    if(estaVazia()){
      throw new PilhaVaziaException();
    }
      return  lista.getPrimeiro().getInfo(); 
    }
    
    @Override
    public T pop() {
      T valor = peek();
      lista.retirar(valor);
      
      return valor;
    }

    @Override
    public boolean estaVazia() {
      return lista.estaVazia();
    }

    @Override
    public void liberar() {
     // lista = new ListaEncadeada();
     
      while(!estaVazia()){
         try{
         while(true){
            pop(); 
           }
         }catch(PilhaVaziaException e){
         
         }
      }
     
    }
    
    public String toString(){
       return lista.toString();
    }
}
