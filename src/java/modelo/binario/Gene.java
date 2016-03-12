/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.binario;

import java.util.Random;

/**
 *
 * @author Gleywson
 */
public class Gene {
    private int alelo;
    
    public Gene() {
        this.alelo = new Random().nextInt(2);
    }

    public int getAlelo() {
        return alelo;
    }

    
    public void setAlelo(int alelo) throws IllegalArgumentException{
        if(alelo == 1 || alelo==0) {
            this.alelo = alelo;
        } else {
            throw new IllegalArgumentException("Valor incompatível para bit");
        }
    }
    
    public void mutacao(double taxa) {
        double probabilidade = new Random().nextDouble();
        
        if(probabilidade < taxa) {
            if(this.alelo == 0) {
                this.setAlelo(1);
            } else {
                this.setAlelo(0);
            }
        }
    }
    
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Gene)) {
            return false;
        }

        Gene gene = (Gene) object;
        return this.getAlelo() == gene.getAlelo();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.alelo;
        return hash;
    }

    
    @Override
    public String toString() {
        return String.valueOf(this.alelo);
    }
    /**
     * Talvez haja a necessidade de criar o metodo compareTo (implementar Comparable)
     */
}
