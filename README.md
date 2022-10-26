# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Thanh Nguyen, s354587, s354587@oslomet.no


# Oppgavebeskrivelse

I oppgave 1 så gikk jeg frem ved å lage en public boolean leggInn (T verdi). startet med ha p som starter, deretter så bruker jeg en hjelpevariabel int compr=0;. Starter med while løkke der p != null sørger for at q blir foreldrer til p. Bruker komparator og flytter p. P er null altså ute av treet og q er siste vi passerte.

I oppgave 2 så gikk jeg frem ved å å lage en public int antall(T verdi). Starter med å initialisere p. Lager en hjelpevariabel  int antVal= 0; for antall verdier. Lager en while-løkke der p != null deretter sammenligner verdier . Oppdaterer venstre barn, finner like verdier og oppdaterer høyrebarn.
returnerer antall.

I Oppgave 3 så gikk jeg frem ved å lage en  private static <T> Node<T> førstePostorden(Node<T> p) og private
private static <T> Node<T> nestePostorden(Node<T> p). På førstePostorden sjekker jeg om p ikke er nullverdi. Lager while-løkke(true) sjekker om p er null. sjekker høyre og venstrebarn til p. På nestePostorden initaliserer jeg f som p.forelder. Hvis f er null returner null som betyr dersom foreldren er null så er det ikke nestePostorden. Derreter sjekker om f.høyre er p eller f.høyre er null. Returnerer foreldrer dersom høyrebarn er verdien p, eller om den er tom. Derretter kaller på førstePostorden.

I Oppgave 4 lager jeg public void postorden(Oppgave<? super T> oppgave) starter med p som rot. finner første node av metoden førstePostorden av p.
While-løkke somm går gjennom treet og oppdaterer neste verdi i postorden.

