/* Initial beliefs and rules */

last_dir(null). // the last movement I did
~resetloc.

/* Initial goals */
!wait.

/* Plans */
+!wait : true <- !!wait.

+!reset<-
    ?station(_,X,Y);
    .print(X, " ", Y);
    !poz(X, Y);
    -resetloc;
    .print("sending done");
    .broadcast(tell,mehetTovabb).



/* karbantartasra van sz ks g */
+repairsignal(X,Y)[source(drone1)] : ~resetloc
    <-!poz(X,Y);
    .print("starting repair");
    jia.repair(X,Y);
    .print("finished repair");
    -repair(X,Y)[source(drone1)];
    +resetloc;
    !!reset.


/* Moving */
+!poz(X,Y) : poz(X,Y) <- true.

+!poz(X,Y) : poz(X,Y) <- .print("I've reached ",X,"x",Y).

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
    .print("Failed next_step to ", X, "x", Y, " fixing and trying again!");
    -+last_dir(null);
    !next_step(X,Y).

+!next_step(X,Y): poz(AgX,AgY) <-
    jia.get_direction(AgX, AgY, X, Y, D);
    -+last_dir(D);
    do(D).

// I still do not know my position
+!next_step(X,Y) : not poz(_,_) <-
    !next_step(X,Y);
    .print("nextStepnotpos").

// failure handling -> start again!
-!next_step(X,Y) : true <-
    .print("Failed next_step to ", X,"x",Y," fixing and trying again!");
    -+last_dir(null);
    !next_step(X,Y).