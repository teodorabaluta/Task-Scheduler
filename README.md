    Implementarea unui sistem de planificare a task-urilor intr-un datacenter, folosind
Java Threads.
    Sistemul este compus din doua elemente principale. Dispatcher-ul are rolul de a prelua 
task-uri care sosesc in sistem si de a le trimite catre nodurile din datacenter pe baza 
unor politici prestabilite. Nodurile au rolul de a executa task-urile pe care le primesc 
in functie de prioritati, de a le preempta pe cele care ruleaza pentru task-uri mai importante, 
etc. Fiecare nod din sistem are si cate o coada in care stocheaza task-urile care vor fi rulate 
mai tarziu. 
  Politici de planificare:
1. Round Robin (RR)
  In aceasta politica de planificare, task-urile sunt alocate, pe masura ce vin, nodului (i + 1)%n, 
unde i este ID-ul ultimului nod la care s-a alocat un task, iar n este numarul total de noduri. 
2. Shortest Queue (SQ)
  La Shortest Queue, dispatcher-ul aloca un task acelui nod care are coada de task-uri in asteptare si
executie de dimensiune minima. Pentru a calcula coada minima, se tine cont inclusiv de task-urile deja
aflate in rulare la un nod. Astfel, daca avem doua noduri, unul cu un task in executie si niciunul in
coada de asteptare, si altul cu niciun task in executie sau in asteptare, dispatcher-ul va trimite un
task nou la al doilea nod. Daca sunt mai multe noduri cu aceeasi dimensiune a cozii, se va trimite un
task catre nodul cu ID-ul mai mic.
3. Size Interval Task Assignment (SITA)
   In cadrul acestei politici de planificare, exista un numar fix de trei noduri, iar task-urile sunt grupate
in trei categorii: scurte, medii, lungi. Fiecare din cele trei noduri este specific unui tip de task asa ca
task-urile sunt asignate pe nodul corespunzator pe masura ce sosesc in sistem.
4. Least Work Left (LWL)
  Politica Least Work Left este similara cu SQ, cu diferenta ca nu se mai tine cont de dimensiunea cozilor de
la fiecare nod, ci de durata totala de calcule ramase de executat. Cantitatea de calcule se refera la timpul
ramas de procesat la fiecare nod, care se poate aproxima la granularitate de secunde.
