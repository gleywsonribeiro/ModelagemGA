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

    
    public void setAlelo(int alelo) {
        if (alelo > 1) {
            this.alelo = 1;
        } else if (alelo < 0) {
            this.alelo = 0;
        } else {
            this.alelo = alelo;
        }
    }
    
    public boolean mutacao(float taxa) {
        float referencia = new Random().nextFloat();
        
        if(referencia < taxa) {
            if(this.alelo == 0) {
                this.setAlelo(1);
            } else {
                this.setAlelo(0);
            }
            
            return true;
        }
        return false;
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
