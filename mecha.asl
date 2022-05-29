last_dir(null).
~resetloc.

!wait.

+!wait : true <- !!wait.

+!reset<-
    ?station(_,X,Y);
    .print(X, " ", Y);
    !poz(X, Y);
    -resetloc;
    .print("Mechanic is back at the depot.");
    .broadcast(tell,mehetTovabb).

+repair(X,Y)[source(drone1)] : ~resetloc
    <-!poz(X,Y);
    .print("Mechanic started extingushing the fire.");
    jia.repair(X,Y);
    .print("Mechanic extinguished the fire.");
    -repair(X,Y)[source(drone1)];
    +resetloc;
    !!reset.


/* Moving */
+!poz(X,Y) : poz(X,Y) <- true.

+!poz(X,Y) : poz(X,Y) <- .print("Mechanic reached position: ",X,", ",Y,".").

+!poz(X,Y) : not poz(X,Y) <-
    !next_step(X,Y);
    !poz(X,Y).

+!next_step(X,Y):poz(AgX,AgY) <-
    jia.get_direction(AgX, AgY, X, Y, D);
    -+last_dir(D);
    do(D).

// I still do not know my position
+!next_step(X,Y) : not poz(_,_) <- !next_step(X,Y).

// failure handling -> start again!
-!next_step(X,Y) : true <-
    .print("Mechanic failed moving to ", X, ", ", Y, ", trying again!");
    -+last_dir(null);
    !next_step(X,Y).

+!next_step(X,Y): poz(AgX,AgY) <-
    jia.get_direction(AgX, AgY, X, Y, D);
    -+last_dir(D);
    do(D).

// I still do not know my position
+!next_step(X,Y) : not poz(_,_) <-
    !next_step(X,Y);
