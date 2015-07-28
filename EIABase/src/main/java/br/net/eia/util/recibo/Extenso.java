package br.net.eia.util.recibo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Extenso {

    public static final String[][] UNIT_REAL=new String[][]{{"real","reais"},{"centavo","centavos"}};
    public static final String[][] UNIT_METER=new String[][]{{"Metro","Metros"},{"Centímetro","Centímetros"}};

    public static final String qualifiers[][] = {
            {"mil", "mil"}, {"milhão", "milhões"},
            {"bilião", "biliões"}, {"trilião", "triliões"},
            {"quatrilião", "quatriliões"}, {"quintilião", "quintiliões"},
            {"sextilião", "sextiliões"}, {"septilião", "septiliões"},
            {"octilião", "octiliões"}, {"nonilião", "noniliões"} };

    public static final String numerals[][] = {
            //0-19
            {"zero", "um", "dois", "três", "quatro", "cinco",
            "seis", "sete", "oito", "nove", "dez",
            "onze", "doze", "treze", "catorze", "quinze",
            "dezasseis", "dezassete", "dezoito", "dezanove"},
            //20-90
            {"vinte", "trinta", "quarenta", "cinquenta",
            "sessenta",     "setenta", "oitenta", "noventa"},
            //100-900
            {"cem", "cento",
            "duzentos", "trezentos", "quatrocentos", "quinhentos",
            "seiscentos", "setecentos", "oitocentos", "novecentos"}
    };

    public static final String conjunction_and = "e";
    public static final String conjunction_of = "de";

    //number of decimals
    private final int decimals;
    //unit label
    private String[][] unit=UNIT_REAL;

    public Extenso(int decimals, String[][] unit) {
            this(decimals);
            this.unit=unit;
    }

    public Extenso(int decimals) {
            this.decimals=decimals;
    }
    
    public Extenso() {
         this.decimals=2;
    }


    public String toString(BigDecimal value) {

            String s="";

            try{
                    value=value.abs().setScale(decimals, RoundingMode.HALF_EVEN);

                    //retrieves integer and decimal parts
                    BigInteger integer_part=value.abs().toBigInteger();
                    //(value-integer_part)*(10^decimais)
                    BigInteger decimal_part=((value.subtract(new BigDecimal(integer_part))).
                                    multiply(new BigDecimal(10).pow(decimals))).toBigInteger();

                    //converts integer part
                    if (integer_part.doubleValue()>0)
                            s+=ordersToString(getOrders(integer_part),unit[0]);

                    //converts decimal part
                    if (decimal_part.doubleValue()>0){
                            s+=s.length()>0 ? " "+conjunction_and+" " : "";
                            s+=ordersToString(getOrders(decimal_part),unit[1]);
                    }

            } catch (Exception e){
                    e.printStackTrace();
            }

            return s;

    }


    /*
     * Dismembers value into orders
     */
    private List<Integer> getOrders(BigInteger value) {

            List<Integer> orders=new ArrayList();

            try{
                    while(value.doubleValue()>=1000) {
                            orders.add(value.remainder(BigInteger.valueOf(1000)).intValue());
                            value=value.divide(BigInteger.valueOf(1000));
                    }
                    orders.add(value.intValue());

            } catch (Exception e){
                    e.printStackTrace();
            }

            return orders;

    }


    /*
     * Concatenates all the orders
     */
    private String ordersToString(List<Integer> orders, String[] unit) {

            String s="";

            try{
                    Integer order0=orders.get(0);

                    //processes below 999
                    if (order0>0) s+=orderToString(order0);

                    //last processed order
                    int last=0;

                    //processes above 999
                    for (int i=1; i<orders.size(); i++){

                            Integer value=orders.get(i);

                            if (value!=0){

                                    //singular and plural
                                    String q=qualifiers.length>=i ? qualifiers[i-1][value>1?1:0] : "?";
                                    /*
                                     * ordem actual >= MILHOES
                                     * ordem anterior = CENTENAS
                                     * nao existem centenas
                                     * (MILHOES DE; BILIOES DE; etc)
                                     */
                                    if (i>=2 && last==0 && order0==0) q+=" "+conjunction_of+" ";
                                    /*
                                     * ordem anterior = CENTENAS
                                     * existem centenas (EVITA MIL E ?)
                                     * centenas inferiores a 100 (E UM; E DOIS; etc)
                                     * centenas multiplas de 100 (E CEM; E DUZENTOS; etc)
                                     */
                                    else if (last==0 && order0>0 && (order0<100 || order0%100==0))
                                            q+=" "+conjunction_and+" ";
                                    /*
                                     * ordem actual >= MILHOES
                                     * (SEPARA MILHOES, BILIOES, etc)
                                     */
                                    else if (i>=2) q+=", ";

                                    else q+=" ";

                                    /*
                                     * ordem actual = MILHARES
                                     * milhares = 1
                                     * (EVITA "UM MIL")
                                     */
                                    if (i==1 && value==1)
                                            s=q+s;
                                    else
                                            s=orderToString(value)+q+s;

                                    last=i;

                            }

                    }

                    //para evitar "UM" + PLURAL da moeda
                    if (orders.size()==1 && order0==1)
                            s+=unit[0];
                    else
                            s+=unit[1];

            } catch (Exception e){
                    e.printStackTrace();
            }

            return s;

    }


    /*
     * Creates order string
     */
    private String orderToString(Integer value) {

            String s="";

            try{
                    //processa ate' 19
                    if (value<20){
                            s+=numerals[0][value]+" ";

                    //processa de 20 a 99
                    }else if (value<100){
                            s+=numerals[1][value/10-2]+" ";
                            if (value % 10!=0) s+=conjunction_and+" "+orderToString(value % 10);

                    //processa 100
                    }else if (value==100){
                            s+=numerals[2][0]+" ";

                    //processa de 101 a 999
                    }else if (value<1000){
                            s+=numerals[2][value/100]+" ";
                            if (value % 100!=0) s+=conjunction_and+" "+orderToString(value % 100);
                    }

            } catch (Exception e){
                    e.printStackTrace();
            }

            return s;
    }
}

