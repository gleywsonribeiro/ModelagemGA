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
    private int bit;
    
    public Gene() {
        this.bit = new Random().nextInt(2);
    }

    public int getBit() {
        return bit;
    }

    
    public void setBit(int bit) {
        if (bit > 1) {
            this.bit = 1;
        } else if (bit < 0) {
            this.bit = 0;
        } else {
            this.bit = bit;
        }
    }
    
    public boolean mutacao(float taxa) {
        float referencia = new Random().nextFloat();
        
        if(referencia < taxa) {
            if(this.bit == 0) {
                this.setBit(1);
            } else {
                this.setBit(0);
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
        return this.getBit() == gene.getBit();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.bit;
        return hash;
    }

    
    @Override
    public String toString() {
        return String.valueOf(this.bit);
    }
    /**
     * Talvez haja a necessidade de criar o metodo compareTo (implementar Comparable)
     */
}
