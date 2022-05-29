/* Initial beliefs and rules */

last_dir(null).
issafe.

/* Initial goals */
!checkExcavator1.

/* Plans */
+!checkExcavator1:  issafe  <-
    ?excavator1(_,X,Y);
    !pos(X,Y);
    !checkThis1(X,Y);
    !!checkExcavator2.


+!checkExcavator2:  issafe  <-
    ?excavator2(_,X,Y);
    !pos(X,Y);
    !checkThis2(X,Y);
    !!checkExcavator3.

+!checkExcavator3:  issafe  <-
    ?excavator3(_,X,Y);
    !pos(X,Y);
    !checkThis3(X,Y);
    !!checkExcavator4.

+!checkExcavator4:  issafe  <-
    ?excavator4(_,X,Y);
    !pos(X,Y);
    !checkThis4(X,Y);
    !!checkExcavator1.

+!checkExcavator1:  true <- .print("waiting").
+!checkExcavator2:  true <- .print("waiting").
+!checkExcavator3:  true <- .print("waiting").
+!checkExcavator4:  true <- .print("waiting").

+!checkThis1(X,Y) : jia.check(0) <- true.
+!checkThis1(X,Y) : true <- .print("Ég");
    .broadcast(tell, repair(X + 1,Y));
    !pos(X,Y);
    .print("Request sent to mechanic");
    -issafe.

+!checkThis2(X,Y) : jia.check(1) <- true.
+!checkThis2(X,Y) : true <- .print("Ég");
    .broadcast(tell, repair(X + 1,Y));
    !pos(X,Y);
    .print("Request sent to mechanic");
    -issafe.

+!checkThis3(X,Y) : jia.check(2) <- true.
+!checkThis3(X,Y) : true <- .print("Ég");
    .broadcast(tell, repair(X + 1,Y));
    !pos(X,Y);
    .print("Request sent to mechanic");
    -issafe.

+!checkThis4(X,Y) : jia.check(3) <- true.
+!checkThis4(X,Y) : true <- .print("Ég");
    .broadcast(tell, repair(X + 1,Y));
    !pos(X,Y);
    .print("Request sent to mechanic");
    -issafe.

+mehetTovabb[source(mecha2)] : true <-
    .print("karbantarto sent done");
    +issafe;
    -mehetTovabb[source(mecha2)];
    !checkExcavator1.

+!pos(X,Y) : pos(X,Y) <- true.

+!pos(X,Y) : pos(X,Y) <- .print("I've reached [",X,",",Y,"].").

+!pos(X,Y) : not pos(X,Y) & issafe <-
    !next_step(X,Y);
    !pos(X,Y).

+!next_step(X,Y):pos(AgX,AgY) <-
    jia.get_direction(AgX, AgY, X, Y, D);
    -+last_dir(D);
    do(D).

// I still do not know my position
+!next_step(X,Y) : not pos(_,_) <- !next_step(X,Y).

// failure handling -> start again!
-!next_step(X,Y) : true <-
    .print("Failed next_step to ", X, "x", Y, " fixing and trying again!");
    -+last_dir(null);
    !next_step(X,Y).

+!next_step(X,Y) : pos(AgX,AgY) <-
    jia.get_direction(AgX, AgY, X, Y, D);
    -+last_dir(D);
    do(D).

// I still do not know my position
+!next_step(X,Y) : not pos(_,_) <- !next_step(X,Y).

// failure handling -> start again!
-!next_step(X,Y) : true <-
    .print("Failed next_step to ", X, "x", Y, " fixing and trying again!");
    -+last_dir(null);
    !next_step(X,Y).