package no.oslomet.cs.algdat.Oblig3;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Node<T> p = rot, q= null; // p som starter i rot.
        int cmp = 0; //hjelpe variabel

        while (p !=null){
            q=p; //q blir foreldre til p
            cmp =comp.compare(verdi,p.verdi); //bruker komperator

            p=cmp <0 ? p.venstre: p.høyre; //flytter p
        }

        //p er null, ute av treet. q er siste som er passert.
        p=new Node<>(verdi,q); //Oppretter ny node.

        if(q==null){
            rot=p;
        } else if(cmp<0) {
            q.venstre=p;
        }else{
            q.høyre=p;
            }
        antall++;
        return true;

    }
    public boolean fjern(T verdi) {

        if(verdi==null) return false; //treet har ingen nullverdier
        Node<T> p =rot, q=null; //q skal være forelder til p

       while(p !=null){
           int cmp = comp.compare(verdi,p.verdi); //sammenligner
           if(cmp<0){
               q=p; p=p.venstre; //venstre
           }else if(cmp>0){
               q=p; p=p.høyre;//høyre
           }
           else break; //verdien ligger i p
       }

       if(p==null) return false; //finner ikke verdi

        if(p.venstre ==null || p.høyre == null){
            Node<T> b=p.venstre !=null ? p.venstre : p.høyre; //b er barnet//hvis noden har 0 eller 1 barn
            if(p==rot){
                rot=b;
            } else if (p==q.venstre){
                q.venstre = b;
                if(b !=null){
                    b.forelder =q;
                }
            }
            else{
                q.høyre =b;
            if(b !=null){
                b.forelder =q;
            }
            }
        }
        else  {
            Node<T> s=p, r = p.høyre;

            while(r.venstre !=null){
                s=r;
                r=r.venstre;
            }

            p.verdi = r.verdi;

            if(s != p){
                s.venstre =r.høyre;
            }else {
                s.høyre = r.høyre;
            }
            if(r.høyre !=null){
                r.høyre.forelder =s;
            }


        }

        antall--;
        endringer++;
        return true;

    }

    public int fjernAlle(T verdi) {
         if(tom()){
             return 0;
         }

         int verdiAntall=0;

         while(fjern(verdi)){
             verdiAntall++;
         }
         return verdiAntall;
    }

    public int antall(T verdi) {
        Node<T> p = rot;
        int antValue = 0;

        while(p != null){
            int cmp = comp.compare(verdi,p.verdi);
            if(cmp<0){
                p=p.venstre;
            }else{
                if(cmp==0) antValue++;
                p=p.høyre;
            }
        }
        return antValue;
    }

    public void nullstill() {
        if(antall==0){
            return;
        }

        Node<T> p = rot;

        int stop =antall;

        p= førstePostorden(p);

        while(stop !=0){
            if(p != null){
                fjern(p.verdi);
            }
            if(p !=null){
                p.verdi=null;
            }
            if(p != null){
                p =nestePostorden(p);
            }
            stop--;
        }

    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        Objects.requireNonNull(p);
        while(true){
            if(p.venstre != null){
                p =p.venstre;
            }else if(p.høyre !=null){
                p=p.høyre;
            }else{
                return p;
            }
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        Node<T> f = p.forelder; //Initialiserer f som p.forelder.
        if (f == null) {
            return null; //Dersom forelderen er null, altså det ikke er noenestePostorden.
        }
        if (f.høyre == p || f.høyre == null) {
            return f; //Returnerer forelder dersom høyrebarn er verdien p,eller at det er tomt.
        } else {
            return førstePostorden(f.høyre); //kaller på førstePostordensom ordner det ellers
        }


    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = rot;
        Node<T> først = førstePostorden(p);
        oppgave.utførOppgave(først.verdi);

        while (først.forelder != null){
            først=nestePostorden(først);
            oppgave.utførOppgave(Objects.requireNonNull(først).verdi);
        }

    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        if (!tom()){
            postordenRecursive(rot,oppgave);
        }
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if(p==null){
            return;
        }

        postordenRecursive(p.venstre,oppgave);
        postordenRecursive(p.høyre,oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
