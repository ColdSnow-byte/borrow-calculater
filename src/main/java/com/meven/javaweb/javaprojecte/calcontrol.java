package com.meven.javaweb.javaprojecte;
import java.lang.Math;
import java.text.NumberFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class calcontrol {
    @RequestMapping("/cal")
    @ResponseBody
        String[] cal(double p, double m, double yr,int type) {
        double mr = yr / 12 / 100.0;
        if(type==0){//等额本息
            double pow = Math.pow(1 + mr, m);
            double payment = p * mr * pow / (pow - 1);
            String arr[] = new String[]{
                    NumberFormat.getCurrencyInstance().format(payment * m),
                    NumberFormat.getCurrencyInstance().format(payment * m - p)
            };
            return arr;
        }else{//等额本金
            double payprinciple=p/m;//还的本金
            double backup =p;
            double payInteresttotal=0.0;
            for(int i=0;i<m;i++){
                double payInterest=p*mr;//还的利息
                p-=payprinciple;//剩余本金
                payInteresttotal+=payInterest;
            }
            return new String[]{
                    NumberFormat.getCurrencyInstance().format(backup+payInteresttotal),
                    NumberFormat.getCurrencyInstance().format(payInteresttotal)
            };
        }
    }

    @RequestMapping("/details")
    @ResponseBody
    String[][] details(double p, int m, double yr,int type) {
        double mr = yr / 12 / 100.0;
        if(type==0){
            double pow = Math.pow(1 + mr, m);
            double payment = p * mr * pow / (pow - 1);
            String a2[][]=new String[m][];
            for (int i=0;i<m;i++){
                double payInterest=p*mr;
                double payprinciple=payment-payInterest;
                p-=payprinciple;
                String row []=new String[]{
                        i+1+"",
                        NumberFormat.getCurrencyInstance().format(payment),
                        NumberFormat.getCurrencyInstance().format(payprinciple),
                        NumberFormat.getCurrencyInstance().format(payInterest),
                        NumberFormat.getCurrencyInstance().format(p)
                };
                a2[i]=row;
            }
            return a2;
        }else{
            double payprinciple=p/m;//还的本金
            String a2[][]=new String[m][];
            for (int i = 0; i < m; i++) {
                double payInterest=p*mr;//还的利息
                p-=payInterest;//剩下的本金
                double payment=payInterest+payprinciple;//月供
                String row[]=new String[]{
                        i+1+"",
                        NumberFormat.getCurrencyInstance().format(payment),
                        NumberFormat.getCurrencyInstance().format(payprinciple),
                        NumberFormat.getCurrencyInstance().format(payInterest),
                        NumberFormat.getCurrencyInstance().format(p)
                };
                a2[i]=row;
            }
            return a2;
        }
    }
}
/*
等额本息还款：每月还款金额相同，由固定利率、固定还款期限和借款额计算得出。
每月还款金额包括本金和利息，但随着还款期限的推移，每月还款中本金和利息的比例会逐渐变化。
在最初的几个月里，还款主要是支付利息，后面的几个月才开始偿还本金。等额本息还款方式可以使每月还款
金额相对较稳定，便于预算管理。
等额本息还款的计算公式如下：
每月还款金额 = (贷款本金 × 月利率 × (1 + 月利率)^还款期数) / ((1 + 月利率)^还款期数 - 1)
等额本金还款：每月还款本金相同，但利息金额逐月递减。由于每月偿还的本金数额固定，因此利息是基于
剩余本金计算的，随着本金的减少，利息支出也逐渐减少。等额本金还款方式可以使总利息支付额度更低，
但初始阶段的还款压力较大。
等额本金还款的计算公式如下：
每月还款金额 = 贷款本金 / 还款期数 + 剩余本金 × 月利率
区别：
还款金额：等额本息还款的每月还款金额相同，等额本金还款的每月还款本金相同；
利息支付：等额本息还款方式下，每月支付的利息逐渐减少；等额本金还款方式下，每月支付的利息保持恒定且稍有减少；
总利息支出：等额本息还款方式下，由于还款金额固定，总利息支付额度相对较高；等额本金还款方式下，
由于每月还款本金相同，总利息支付额度相对较低；
*/
