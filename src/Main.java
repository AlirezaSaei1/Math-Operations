import java.util.ArrayList;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Polynomial p = new Polynomial();
        System.out.println("Enter Number Of Terms: ");
        int x = sc.nextInt();
        for (int i = 0; i < x; i++) {
            System.out.println("coefficient " + (i + 1));
            double co = sc.nextDouble();
            System.out.println("Power " + (i + 1));
            int pow = sc.nextInt();
            p.addTerm(new Term(co, pow));
        }
        while (true) {
            System.out.println("1: Show Polynomial");
            System.out.println("2: Show Derivative");
            System.out.println("3: Show Integral");
            System.out.println("4: Find Value At");
            System.out.println("5: Minus Integral And Derivation");
            System.out.println("6: Quit");
            int command = sc.nextInt();
            if (command == 1) {
                System.out.println("Polynomial : " + p.simplification().simplification().show());
            }
            if (command == 2) {
                Polynomial b = p.derivative();
                System.out.println("Derivative : " + b.simplification().simplification().show());
            }
            if (command == 3) {
                Polynomial b = p.integrate();
                System.out.println("Integrate : " + b.simplification().simplification().show());
            }
            if (command == 4) {
                System.out.println("Enter A Number:");
                int valueAt = sc.nextInt();
                System.out.println("Polynomial at " + valueAt + " : " + p.valueAt(valueAt));
                System.out.println("Integral at " + valueAt + " : "  + p.integrate().valueAt(valueAt));
                System.out.println("Derivation at " + valueAt + " : " + p.derivative().valueAt(valueAt));
            }
            if (command == 5) {
                Polynomial min = p.minus();
                System.out.println(min.simplification().simplification().show());
            }
            if (command == 6) {
                break;
            }
        }
    }
}

class Term {
    double coefficient;
    int power;

    Term(double coefficient, int power) {
        this.coefficient = coefficient;
        this.power = power;
    }

    double valueAt(double x0) {
        return Math.pow(x0, power) * coefficient;
    }

    Term derivative() {
        int p = this.power - 1;
        double co = this.coefficient * this.power;
        return new Term(co, p);
    }

    Term integrate() {
        int p = (this.power + 1);
        double c = (this.coefficient / p);
        return new Term(c, p);
    }
}

class Polynomial {
    ArrayList<Term> terms = new ArrayList<>();

    void addTerm(Term term) {
        terms.add(term);
    }

    double valueAt(int x0) {
        double answer = 0;
        for (int i = 0; i < terms.size(); i++) {
            answer += terms.get(i).valueAt(x0);
        }
        return answer;
    }

    Polynomial derivative() {
        Polynomial answer = new Polynomial();
        for (int i = 0; i < terms.size(); i++) {
            answer.addTerm(terms.get(i).derivative());
        }
        return answer;
    }

    Polynomial integrate() {
        Polynomial answer = new Polynomial();
        for (int i = 0; i < terms.size(); i++) {
            answer.addTerm(terms.get(i).integrate());
        }
        return answer;
    }

    Polynomial minus(){
        Polynomial answer = new Polynomial();
        Polynomial integral = this.integrate();
        Polynomial dev = this.derivative();
        answer.terms = integral.terms;
        for(int i=0; i<dev.terms.size(); i++){
            answer.terms.add(new Term((-1*dev.terms.get(i).coefficient), dev.terms.get(i).power));
        }
        return answer;
    }

    Polynomial simplification() {
        for (int i = 0; i < terms.size(); i++) {
            Term zero = terms.get(i);
            if (zero.coefficient == 0) {
                terms.remove(zero);
            }
        }
        ArrayList<Term> a = this.terms;
        Polynomial answer = new Polynomial();
        for(int i=a.size()-1; i>=0;i--){
            for(int j=0; j<i; j++){
                if(a.get(i).power == a.get(j).power){
                    a.get(i).coefficient += a.get(j).coefficient;
                    a.get(j).coefficient = 0;
                    break;
                }
            }
        }
        answer.terms = a;
        return answer;
    }

    public StringBuilder show() {
        int counter = 0;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            if (terms.get(i).power == 0) {
                if (terms.get(i).coefficient > 0) {
                    if(counter == 0){
                        str.append(terms.get(i).coefficient + " ");
                    }else {
                        str.append(" +" + terms.get(i).coefficient + " ");
                    }
                } else {
                    if(counter == 0){
                        str.append(terms.get(i).coefficient + " ");
                    }else {
                        str.append(terms.get(i).coefficient + " ");
                    }
                }
            } else {
                if (terms.get(i).coefficient > 0) {
                    if(counter == 0){
                        str.append(terms.get(i).coefficient + "x^" + terms.get(i).power + " ");
                    }else {
                        str.append(" +" + terms.get(i).coefficient + "x^" + terms.get(i).power + " ");
                    }
                } else {
                    if(counter == 0){
                        str.append(terms.get(i).coefficient + "x^" + terms.get(i).power + " ");
                    }else {
                       str.append(terms.get(i).coefficient + "x^" + terms.get(i).power + " ");
                    }
                }
            }
            counter++;
        }
        return str;
    }
}
