/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package trabalhoFinal;

/**
 *
 * @author Premiersoft
 */
public interface Pilha<T> {
    
    public void push(T info);
    public T pop();
    public T peek();
    public boolean estaVazia();
    public void liberar();
}
