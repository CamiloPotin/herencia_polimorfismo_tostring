package com.techlab.articulo.modelo;

public class ArticuloElectronico extends Articulo{

    private int garantiaMeses;

    public ArticuloElectronico(int codigo, String nombre, double precio, Categoria categoria, int garantiaMeses) {
        super(codigo, nombre, precio, categoria);
        this.garantiaMeses = garantiaMeses;
    }

    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    public void setGarantiaMeses(int garantiaMeses) {
        this.garantiaMeses = garantiaMeses;
    }

    @Override
    public String getDetalleEspecifico() {
        return "Garantia en meses:"+garantiaMeses;
    }

    @Override
    public String getTipoArticulo() {
        return "Electronico";
    }

    public String nroTelMesaDeAyudaParaReclamos(){
        return "0800-123-4567";
    }

    @Override
    public String toString() {
        return super.toString()+" [subtipo electrónico]";
    }
    
    

}
